/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.vldap.server.handler;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.vldap.server.directory.DirectoryTree;
import com.liferay.vldap.server.directory.SearchBase;
import com.liferay.vldap.server.directory.ldap.Attribute;
import com.liferay.vldap.server.directory.ldap.LdapDirectory;
import com.liferay.vldap.server.handler.util.LdapHandlerContext;
import com.liferay.vldap.util.PortletPropsValues;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.StopWatch;
import org.apache.directory.shared.ldap.model.entry.Entry;
import org.apache.directory.shared.ldap.model.filter.AndNode;
import org.apache.directory.shared.ldap.model.filter.BranchNode;
import org.apache.directory.shared.ldap.model.filter.EqualityNode;
import org.apache.directory.shared.ldap.model.filter.ExprNode;
import org.apache.directory.shared.ldap.model.filter.GreaterEqNode;
import org.apache.directory.shared.ldap.model.filter.LessEqNode;
import org.apache.directory.shared.ldap.model.filter.NotNode;
import org.apache.directory.shared.ldap.model.filter.OrNode;
import org.apache.directory.shared.ldap.model.filter.PresenceNode;
import org.apache.directory.shared.ldap.model.filter.SubstringNode;
import org.apache.directory.shared.ldap.model.message.Request;
import org.apache.directory.shared.ldap.model.message.Response;
import org.apache.directory.shared.ldap.model.message.ResultCodeEnum;
import org.apache.directory.shared.ldap.model.message.SearchRequest;
import org.apache.directory.shared.ldap.model.message.SearchResultEntry;
import org.apache.directory.shared.ldap.model.message.SearchResultEntryImpl;
import org.apache.directory.shared.ldap.model.message.SearchScope;
import org.apache.directory.shared.ldap.model.name.Dn;
import org.apache.mina.core.session.IoSession;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class SearchLdapHandler extends BaseLdapHandler {

	public List<Response> messageReceived(Request request, IoSession ioSession,
		LdapHandlerContext ldapHandlerContext) {

		SearchRequest searchRequest = (SearchRequest) request;

		List<Response> responses = new ArrayList<Response>();

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		try {
			Dn baseDn = searchRequest.getBase();
			SearchScope searchScope = searchRequest.getScope();
			ExprNode filter = searchRequest.getFilter();

			DirectoryTree directoryTree = new DirectoryTree();
			SearchBase base =
				directoryTree.findBase(baseDn, getSizeLimit(searchRequest));

			if (base != null) {
				long sizeLimit = getSizeLimit(searchRequest);
				base.setSizeLimit(sizeLimit);

				LdapDirectory baseDirectory = base.getLdapDirectory();

				if (baseDirectory != null) {
					if (searchScope.equals(SearchScope.OBJECT) ||
						searchScope.equals(SearchScope.SUBTREE)) {

						if (isMatch(filter, baseDirectory)) {
							addObjectEntry(
								searchRequest, responses,
								ldapHandlerContext, baseDirectory, stopWatch);

							// Decrease size limit by one for the rest of the
							// search because we've already added the base
							// directory
							base.setSizeLimit(base.getSizeLimit() - 1);
						}
					}

					if (searchScope.equals(SearchScope.ONELEVEL) ||
						searchScope.equals(SearchScope.SUBTREE)) {

						List<LdapDirectory> rest =
							directoryTree.findRest(base, filter, searchScope);

						for (LdapDirectory d : rest) {
							addObjectEntry(
								searchRequest, responses,
								ldapHandlerContext, d, stopWatch);
						}
					}
				}
			}

			responses.add(getResponse(searchRequest));
		}
		catch (SearchSizeLimitException ssle) {
			responses.add(
				getResponse(
					searchRequest, ResultCodeEnum.SIZE_LIMIT_EXCEEDED));
		}
		catch (SearchTimeLimitException ssle) {
			responses.add(
				getResponse(
					searchRequest, ResultCodeEnum.TIME_LIMIT_EXCEEDED));
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return responses;
	}

	protected void addObjectEntry(SearchRequest searchRequest,
			List<Response> responses, LdapHandlerContext ldapHandlerContext,
			LdapDirectory directory, StopWatch stopWatch)
		throws Exception {

		SearchResultEntry searchResponseEntry =
			new SearchResultEntryImpl(searchRequest.getMessageId());

		Entry entry = directory.toEntry(searchRequest.getAttributes());

		searchResponseEntry.setEntry(entry);

		// Even if we only have the same amount of results as the limit, we
		// assume that we've exceeded the limit because there's really no way
		// for us to know if we could have gotten more results with a higher
		// limit.
		if (responses.size() >= getSizeLimit(searchRequest)) {
			throw new SearchSizeLimitException();
		}

		if ((stopWatch.getTime() / Time.SECOND) >
				getTimeLimit(searchRequest)) {

			throw new SearchTimeLimitException();
		}

		// These are commented out because we probably don't need to filter
		// the nodes that we got back from the search, because the search
		// is now efficient enough to return only nodes that would have matched
		// the filter. However we could still filter them here also just to
		// make sure. I haven't tried every query, so in some cases we may have
		// nodes that need to be filtered here.

		//ExprNode filter = searchRequest.getFilter();

		//if (isMatch(filter, directory)) {
			responses.add(searchResponseEntry);
		//}
	}

	protected long getSizeLimit(SearchRequest searchRequest) {
		long sizeLimit = searchRequest.getSizeLimit();

		if ((sizeLimit == 0) ||
			(sizeLimit > PortletPropsValues.SEARCH_MAX_SIZE)) {

			sizeLimit = PortletPropsValues.SEARCH_MAX_SIZE;
		}

		return sizeLimit;
	}

	protected int getTimeLimit(SearchRequest searchRequest) {
		int timeLimit = searchRequest.getTimeLimit();

		if ((timeLimit == 0) ||
			(timeLimit > PortletPropsValues.SEARCH_MAX_TIME)) {

			timeLimit = PortletPropsValues.SEARCH_MAX_TIME;
		}

		return timeLimit;
	}

	private boolean isMatch(ExprNode exprNode, LdapDirectory directory) {
		if (exprNode.isLeaf()) {
			if (exprNode instanceof EqualityNode<?>) {
				EqualityNode<?> equalityNode = (EqualityNode<?>)exprNode;

				String attributeId = equalityNode.getAttribute();
				String value = equalityNode.getValue().getString();

				Attribute attribute = directory.getAttribute(
					attributeId, value);

				if (attribute != null) {
					return true;
				}
				else {
					return false;
				}
			}
			else if (exprNode instanceof GreaterEqNode<?>) {
			}
			else if (exprNode instanceof LessEqNode<?>) {
			}
			else if (exprNode instanceof PresenceNode) {
				PresenceNode specificFilter = (PresenceNode)exprNode;

				String attributeId = specificFilter.getAttribute();

				Attribute attribute = directory.getAttribute(attributeId);

				if (attribute != null) {
					return true;
				}
				else {
					return false;
				}
			}
			else if (exprNode instanceof SubstringNode) {
			}
			else {
				_log.error("Unsupported expression " + exprNode);
			}
		}
		else {
			BranchNode branchNode = (BranchNode)exprNode;

			if (exprNode instanceof AndNode) {
				for (ExprNode childBranchNode : branchNode.getChildren()) {
					if (!isMatch(childBranchNode, directory)) {
						return false;
					}
				}

				return true;
			}
			else if (exprNode instanceof NotNode) {
				for (ExprNode childBranchNode : branchNode.getChildren()) {
					if (!isMatch(childBranchNode, directory)) {
						return true;
					}
					else {
						return false;
					}
				}

				return false;
			}
			else if (exprNode instanceof OrNode) {
				for (ExprNode childBranchNode : branchNode.getChildren()) {
					if (isMatch(childBranchNode, directory)) {
						return true;
					}
				}

				return false;
			}
		}

		return true;
	}

	private static Log _log = LogFactoryUtil.getLog(SearchLdapHandler.class);

}
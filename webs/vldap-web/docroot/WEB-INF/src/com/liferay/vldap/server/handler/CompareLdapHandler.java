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

import java.util.List;

import org.apache.directory.shared.ldap.model.entry.Value;
import org.apache.directory.shared.ldap.model.message.CompareRequest;
import org.apache.directory.shared.ldap.model.message.Request;
import org.apache.directory.shared.ldap.model.message.Response;
import org.apache.directory.shared.ldap.model.message.ResultCodeEnum;
import org.apache.directory.shared.ldap.model.name.Dn;
import org.apache.mina.core.session.IoSession;

import com.liferay.vldap.server.directory.DirectoryTree;
import com.liferay.vldap.server.directory.SearchBase;
import com.liferay.vldap.server.directory.ldap.Attribute;
import com.liferay.vldap.server.directory.ldap.LdapDirectory;
import com.liferay.vldap.server.handler.util.LdapHandlerContext;
import com.liferay.vldap.util.PortletPropsValues;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class CompareLdapHandler extends BaseLdapHandler {

	public List<Response> messageReceived(
			Request request, IoSession ioSession,
			LdapHandlerContext ldapHandlerContext)
		throws Exception {

		CompareRequest compareRequest = (CompareRequest) request;

		String attributeId = compareRequest.getAttributeId();
		Value<?> value = compareRequest.getAssertionValue();

		Dn dn = compareRequest.getName();
		SearchBase base =
			new DirectoryTree().findBase(
				dn, PortletPropsValues.SEARCH_MAX_SIZE);
		
		if (base != null) {
			LdapDirectory directory = base.getLdapDirectory();
			if (directory != null) {
				Attribute attribute = directory.getAttribute(
					attributeId, value.getString());
	
				if (attribute != null) {
					return toList(
						getResponse(
							compareRequest, ResultCodeEnum.COMPARE_TRUE));
				}
			}
		}

		return toList(
			getResponse(compareRequest, ResultCodeEnum.COMPARE_FALSE));
	}

}

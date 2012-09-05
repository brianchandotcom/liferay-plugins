/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.socialcoding.svn.portlet;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.socialcoding.model.JIRAIssue;
import com.liferay.socialcoding.model.SVNRepository;
import com.liferay.socialcoding.model.SVNRevision;
import com.liferay.socialcoding.service.SVNRepositoryLocalServiceUtil;
import com.liferay.socialcoding.service.SVNRevisionLocalServiceUtil;
import com.liferay.util.RSSUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Eduardo Garcia
 */
public class SVNPortlet extends MVCPortlet {

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws IOException, PortletException {

		try {
			String resourceID = resourceRequest.getResourceID();

			if (resourceID.equals("rss")) {
				serveRSS(resourceRequest, resourceResponse);
			}
		}
		catch (IOException ioe) {
			throw ioe;
		}
		catch (PortletException pe) {
			throw pe;
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	protected void serveRSS(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {

		if (!PortalUtil.isRSSFeedsEnabled()) {
			PortalUtil.sendRSSFeedsDisabledError(
				resourceRequest, resourceResponse);

			return;
		}

		String rss = getRSS(resourceRequest, 100);

		PortletResponseUtil.sendFile(
			resourceRequest, resourceResponse, null,
			rss.getBytes(StringPool.UTF8), ContentTypes.TEXT_XML_UTF8);
	}
	
	protected String getRSS(ResourceRequest resourceRequest, int rssDelta)
		throws Exception {

		String url = ParamUtil.getString(resourceRequest, "url");
		boolean all = ParamUtil.getBoolean(resourceRequest, "all");

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long scopeGroupId = themeDisplay.getScopeGroupId();
		Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);

		User user2 = null;

		if (group.isUser()) {
			user2 = UserLocalServiceUtil.getUserById(group.getClassPK());
		}

		String svnURL = "svn://svn.liferay.com/repos/public" + url;

		SVNRepository svnRepository = 
			SVNRepositoryLocalServiceUtil.getSVNRepository(svnURL);

		List<SVNRevision> svnRevisions = null;

		if (all) {
			svnRevisions = SVNRevisionLocalServiceUtil.getSVNRevisions(
				svnRepository.getSvnRepositoryId(), 0, rssDelta);
		}
		else {
			String svnUserId = user2.getScreenName();

			svnRevisions = SVNRevisionLocalServiceUtil.getSVNRevisions(
				svnUserId, svnRepository.getSvnRepositoryId(), 0, rssDelta);
		}

		Locale locale = themeDisplay.getLocale();

		String title = null;

		if (all) {
			title = LanguageUtil.format(locale, "all-commits-on-x", svnURL);
		}
		else {
			title = LanguageUtil.format(locale, "x's-commits-on-x",
				new Object[] {HtmlUtil.escape(user2.getFullName()), svnURL});
		}

		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setFeedType(RSSUtil.FEED_TYPE_DEFAULT);
		syndFeed.setLink(PortalUtil.getCurrentURL(resourceRequest));
		syndFeed.setTitle(title);
		syndFeed.setDescription(title);

		List<SyndEntry> entries = new ArrayList<SyndEntry>();

		syndFeed.setEntries(entries);

		for (SVNRevision svnRevision : svnRevisions) {
			String link = svnRevision.getWebRevisionNumberURL();

			SyndEntry syndEntry = new SyndEntryImpl();

			syndEntry.setAuthor(HtmlUtil.escape(user2.getFullName()));
			syndEntry.setTitle(LanguageUtil.get(locale, "revision") +
				StringPool.SPACE + svnRevision.getRevisionNumber());
			syndEntry.setLink(link);
			syndEntry.setPublishedDate(svnRevision.getCreateDate());

			Object[] jiraIssueAndComments = 
				svnRevision.getJIRAIssueAndComments();

			JIRAIssue jiraIssue = null;
			String comments = svnRevision.getComments();

			if (jiraIssueAndComments != null) {
				jiraIssue = (JIRAIssue)jiraIssueAndComments[0];
				comments = (String)jiraIssueAndComments[1];

				StringBuilder sb = new StringBuilder();

				sb.append(comments);
				sb.append("<br />");

				sb.append(jiraIssue.getSummary());
				sb.append("<br />");

				sb.append("<a href=\"");
				sb.append(link);
				sb.append("\"><img border=\"0\" src=\"");
				sb.append(resourceRequest.getContextPath());
				sb.append("/icons/svn.png\" />SVN</a><br />");

				sb.append("<a href=\"http://issues.liferay.com/browse/");
				sb.append(jiraIssue.getKey());
				sb.append("\"><img border=\"0\" src=\"");
				sb.append(resourceRequest.getContextPath());
				sb.append("/icons/jira.png\" />JIRA</a>");

				comments = sb.toString();
			}

			SyndContent syndContent = new SyndContentImpl();

			syndContent.setType(RSSUtil.ENTRY_TYPE_DEFAULT);
			syndContent.setValue(comments);

			syndEntry.setDescription(syndContent);

			entries.add(syndEntry);
		}

		String feedXML = StringPool.BLANK;

		try {
			feedXML = RSSUtil.export(syndFeed);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return feedXML;
	}

	private static Log _log = LogFactoryUtil.getLog(SVNPortlet.class);

}

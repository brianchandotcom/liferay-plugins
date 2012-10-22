<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath","/view.jsp");
%>

<%
long groupId = TestResourcesImporterUtil.getGroupId();

Group group = GroupLocalServiceUtil.fetchGroup(groupId);
%>

<liferay-ui:header
	backURL="<%= portletURL.toString() %>"
	title="<%= (group != null) ? group.getName() : StringPool.BLANK %>"
/>

<h3>Group</h3>

<p>
	group=<%= (group != null) ? "PASSED" : "FAILED" %><br />
	groupId=<%= groupId %>
</p>

<h3>Layout</h3>

<%
LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(groupId, false);
%>

<p>
	count=<%= (layoutSet.getPageCount() == 5) ? "PASSED" : "FAILED" %><br />
</p>

<h3>DLFileEntry</h3>

<%
DLFileEntry entry = DLFileEntryLocalServiceUtil.fetchFileEntry(groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "company_logo");

String[] tags = AssetTagLocalServiceUtil.getTagNames(DLFileEntry.class.getName(), entry.getFileEntryId());
%>

<p>
	count=<%= (DLFileEntryLocalServiceUtil.getFileEntriesCount(groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) == 4) ? "PASSED" : "FAILED" %><br />
	company_logo.png=<%= (entry != null) ? "PASSED" : "FAILED" %><br />
	tags=<%= ArrayUtil.contains(tags, "logo") ? "PASSED" : "FAILED" %>
</p>

<h3>Journal Article</h3>

<%
JournalArticle journalArticle = JournalArticleLocalServiceUtil.getArticle(groupId, "CHILD-1-WEB-CONTENT");
%>

<p>
	count=<%= (JournalArticleLocalServiceUtil.getArticlesCount(groupId) == 5) ? "PASSED" : "FAILED" %><br />
	isTemplateDriven=<%= (journalArticle.isTemplateDriven()) ? "PASSED" : "FAILED" %>
</p>

<h3>Journal Structure</h3>

<%
JournalStructure journalStructure = JournalStructureLocalServiceUtil.getStructure(groupId, "CHILD-STRUCTURE-1");
%>

<p>
	count=<%= (JournalStructureLocalServiceUtil.getStructuresCount(groupId) == 3) ? "PASSED" : "FAILED" %><br />
	parent/child=<%= journalStructure.getParentStructureId().equals("PARENT") ? "PASSED" : "FAILED" %>
</p>

<h3>Journal Template</h3>

<%
JournalTemplate journalTemplate = JournalTemplateLocalServiceUtil.getTemplate(groupId, "CHILD-1");
%>

<p>
	count=<%= (JournalTemplateLocalServiceUtil.getTemplatesCount(groupId) == 2) ? "PASSED" : "FAILED" %><br />
	structure/template=<%= journalTemplate.getStructureId().equals("CHILD-STRUCTURE-1") ? "PASSED" : "FAILED" %>
</p>
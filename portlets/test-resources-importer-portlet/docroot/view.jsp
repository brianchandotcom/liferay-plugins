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
if (!themeDisplay.isSignedIn()) {
%>

	Please sign in to run the test.

<%
	return;
}

ServletContext servletContext = getServletContext();

String[] resourcesImporters = {"filesystem", "lar", "resource"};

for (String importer : resourcesImporters) {
	String resourcesPath = servletContext.getRealPath("/WEB-INF/classes/resources-importer");

	if (FileUtil.exists(resourcesPath)) {
		FileUtil.delete(resourcesPath);
	}

	long companyId = CompanyThreadLocal.getCompanyId();

	Group group = GroupLocalServiceUtil.fetchGroup(companyId, "Test Resources Importer Portlet");

	long groupId = 0;

	if (group != null) {
		groupId = group.getGroupId();

		GroupLocalServiceUtil.deleteGroup(groupId);
	}

	String importerPath = servletContext.getRealPath("WEB-INF/classes/test/" + importer);

	File resourcesDir = new File(resourcesPath);

	resourcesDir.mkdir();

	if (!importer.equals("filesystem")) {
		FileUtil.copyDirectory(importerPath, resourcesPath);
	}

	Message message = new Message();

	message.put("command", "deploy");
	message.put("servletContextName", "test-resources-importer-portlet");

	message.setResponseDestinationName("liferay/resources_importer");

	MessageBusUtil.sendSynchronousMessage(DestinationNames.HOT_DEPLOY, message);

	group = GroupLocalServiceUtil.fetchGroup(companyId, "Test Resources Importer Portlet");

	if (group != null) {
		groupId = group.getGroupId();
	}

	LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(groupId, false);

	DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.fetchFileEntry(groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "company_logo");

	String[] tags = AssetTagLocalServiceUtil.getTagNames(DLFileEntry.class.getName(), dlFileEntry.getFileEntryId());

	JournalArticle journalArticle = JournalArticleLocalServiceUtil.getArticle(groupId, "CHILD-WEB-CONTENT-1");

	JournalStructure journalStructure = JournalStructureLocalServiceUtil.getStructure(groupId, "CHILD-STRUCTURE-1");

	JournalTemplate journalTemplate = JournalTemplateLocalServiceUtil.getTemplate(groupId, "CHILD-TEMPLATE-1");
%>

	<h3><%= importer %></h3>

	<p>
		group=<%= (group != null) ? "PASSED" : "FAILED" %><br />
		groupId=<%= groupId %><br />

		layoutSet#getPageCount=<%= (layoutSet.getPageCount() == 5) ? "PASSED" : "FAILED" %><br />

		dlFileEntryLocalService#getFileEntriesCount=<%= (DLFileEntryLocalServiceUtil.getFileEntriesCount(groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) == 1) ? "PASSED" : "FAILED" %><br />
		dlFileEntryLocalService#fetchFileEntry=<%= (dlFileEntry != null) ? "PASSED" : "FAILED" %><br />
		assetTagLocalService#getTagNames=<%= ArrayUtil.contains(tags, "logo") ? "PASSED" : "FAILED" %><br />

		jouranlArticleLocalService#getArticlesCount=<%= (JournalArticleLocalServiceUtil.getArticlesCount(groupId) == 5) ? "PASSED" : "FAILED" %><br />
		journalArticle#isTemplateDriven=<%= (journalArticle.isTemplateDriven()) ? "PASSED" : "FAILED" %><br />

		journalStructureLocalService#getStructuresCount=<%= (JournalStructureLocalServiceUtil.getStructuresCount(groupId) == 3) ? "PASSED" : "FAILED" %><br />
		journalStructure#getParentStructureId=<%= journalStructure.getParentStructureId().equals("PARENT-STRUCTURE") ? "PASSED" : "FAILED" %><br />

		journalTemplateLocalService#getTemplatesCount=<%= (JournalTemplateLocalServiceUtil.getTemplatesCount(groupId) == 2) ? "PASSED" : "FAILED" %><br />
		journalTemplate#getStructureId=<%= journalTemplate.getStructureId().equals("CHILD-STRUCTURE-1") ? "PASSED" : "FAILED" %>
	</p>

<%
}
%>
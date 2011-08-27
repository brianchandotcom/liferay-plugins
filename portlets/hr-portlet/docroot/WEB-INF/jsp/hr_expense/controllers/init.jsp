<%--
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
--%>

<%@ page import="com.liferay.counter.service.CounterLocalServiceUtil" %>
<%@ page import="com.liferay.hr.model.HRExpense" %>
<%@ page import="com.liferay.hr.model.HRExpenseAccount" %>
<%@ page import="com.liferay.hr.model.HRExpenseCurrency" %>
<%@ page import="com.liferay.hr.model.HRExpenseCurrencyConversion" %>
<%@ page import="com.liferay.hr.model.HRExpenseType" %>
<%@ page import="com.liferay.hr.model.impl.HRExpenseAccountImpl" %>
<%@ page import="com.liferay.hr.model.impl.HRExpenseCurrencyImpl" %>
<%@ page import="com.liferay.hr.model.impl.HRExpenseCurrencyConversionImpl" %>
<%@ page import="com.liferay.hr.model.impl.HRExpenseImpl" %>
<%@ page import="com.liferay.hr.model.impl.HRExpenseTypeImpl" %>
<%@ page import="com.liferay.hr.service.HRExpenseAccountLocalServiceUtil" %>
<%@ page import="com.liferay.hr.service.HRExpenseCurrencyLocalServiceUtil" %>
<%@ page import="com.liferay.hr.service.HRExpenseCurrencyConversionLocalServiceUtil" %>
<%@ page import="com.liferay.hr.service.HRExpenseLocalServiceUtil" %>
<%@ page import="com.liferay.hr.service.HRExpenseServiceUtil" %>
<%@ page import="com.liferay.hr.service.HRExpenseTypeLocalServiceUtil" %>
<%@ page import="com.liferay.hr.service.persistence.HRExpenseAccountUtil" %>
<%@ page import="com.liferay.hr.service.persistence.HRExpenseTypeUtil" %>
<%@ page import="com.liferay.portal.kernel.bean.BeanPropertiesUtil" %>
<%@ page import="com.liferay.portal.kernel.dao.orm.QueryUtil" %>
<%@ page import="com.liferay.portal.kernel.json.JSONArray" %>
<%@ page import="com.liferay.portal.kernel.json.JSONFactoryUtil" %>
<%@ page import="com.liferay.portal.kernel.json.JSONObject" %>
<%@ page import="com.liferay.portal.kernel.search.Indexer" %>
<%@ page import="com.liferay.portal.kernel.search.IndexerRegistryUtil" %>
<%@ page import="com.liferay.portal.kernel.servlet.ServletResponseUtil" %>
<%@ page import="com.liferay.portal.kernel.upload.UploadPortletRequest" %>
<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="com.liferay.portal.kernel.util.ContentTypes" %>
<%@ page import="com.liferay.portal.kernel.util.FileUtil" %>
<%@ page import="com.liferay.portal.kernel.util.KeyValuePair" %>
<%@ page import="com.liferay.portal.kernel.util.ListUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ObjectValuePair" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.StringUtil" %>
<%@ page import="com.liferay.portal.kernel.util.TempFileUtil" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %>
<%@ page import="com.liferay.portal.model.AttachedModel" %>
<%@ page import="com.liferay.portal.model.AuditedModel" %>
<%@ page import="com.liferay.portal.model.BaseModel" %>
<%@ page import="com.liferay.portal.model.ClassedModel" %>
<%@ page import="com.liferay.portal.model.CompanyConstants" %>
<%@ page import="com.liferay.portal.model.GroupConstants" %>
<%@ page import="com.liferay.portal.model.GroupedModel" %>
<%@ page import="com.liferay.portal.model.User" %>
<%@ page import="com.liferay.portal.NoSuchUserException" %>
<%@ page import="com.liferay.portal.security.auth.PrincipalException" %>
<%@ page import="com.liferay.portal.security.auth.PrincipalThreadLocal" %>
<%@ page import="com.liferay.portal.service.ServiceContext" %>
<%@ page import="com.liferay.portal.service.ServiceContextFactory" %>
<%@ page import="com.liferay.portal.service.SubscriptionLocalServiceUtil" %>
<%@ page import="com.liferay.portal.service.UserLocalServiceUtil" %>
<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>
<%@ page import="com.liferay.portlet.documentlibrary.DuplicateDirectoryException" %>
<%@ page import="com.liferay.portlet.documentlibrary.DuplicateFileException" %>
<%@ page import="com.liferay.portlet.documentlibrary.NoSuchDirectoryException" %>
<%@ page import="com.liferay.portlet.documentlibrary.store.DLStoreUtil" %>
<%@ page import="com.liferay.portlet.messageboards.MessageBodyException" %>
<%@ page import="com.liferay.portlet.messageboards.model.MBMessage" %>
<%@ page import="com.liferay.portlet.messageboards.NoSuchMessageException" %>
<%@ page import="com.liferay.portlet.messageboards.RequiredMessageException" %>
<%@ page import="com.liferay.portlet.messageboards.service.MBMessageServiceUtil" %>
<%@ page import="com.liferay.util.bridges.alloy.AlloyController" %>
<%@ page import="com.liferay.util.bridges.alloy.BaseAlloyControllerImpl" %>

<%@ page import="java.io.File" %>
<%@ page import="java.io.IOException" %>

<%@ page import="java.text.DateFormat" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>

<%@ page import="javax.portlet.ActionRequest" %>
<%@ page import="javax.portlet.ActionResponse" %>
<%@ page import="javax.portlet.EventRequest" %>
<%@ page import="javax.portlet.EventResponse" %>
<%@ page import="javax.portlet.PortletRequest" %>
<%@ page import="javax.portlet.PortletURL" %>
<%@ page import="javax.portlet.RenderRequest" %>
<%@ page import="javax.portlet.RenderResponse" %>
<%@ page import="javax.portlet.ResourceRequest" %>
<%@ page import="javax.portlet.ResourceResponse" %>

<%@ page import="javax.servlet.jsp.PageContext" %>

<%
AlloyController alloyController = new AlloyControllerImpl();

alloyController.setPageContext(pageContext);

alloyController.afterPropertiesSet();

alloyController.execute();
%>

<%!
protected MBMessage updateMessage(ActionRequest actionRequest)
	throws Exception {

	ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
		WebKeys.THEME_DISPLAY);

	String className = ParamUtil.getString(actionRequest, "className");
	long classPK = ParamUtil.getLong(actionRequest, "classPK");
	String permissionClassName = ParamUtil.getString(
		actionRequest, "permissionClassName");
	long permissionClassPK = ParamUtil.getLong(
		actionRequest, "permissionClassPK");
	long permissionOwnerId = ParamUtil.getLong(
		actionRequest, "permissionOwnerId");

	long messageId = ParamUtil.getLong(actionRequest, "messageId");

	long threadId = ParamUtil.getLong(actionRequest, "threadId");
	long parentMessageId = ParamUtil.getLong(
		actionRequest, "parentMessageId");
	String subject = ParamUtil.getString(actionRequest, "subject");
	String body = ParamUtil.getString(actionRequest, "body");

	ServiceContext serviceContext = ServiceContextFactory.getInstance(
		className, actionRequest);

	MBMessage message = null;

	if (messageId <= 0) {

		// Add message

		User user = null;

		if (themeDisplay.isSignedIn()) {
			user = themeDisplay.getUser();
		}
		else {
			String emailAddress = ParamUtil.getString(
				actionRequest, "emailAddress");

			try {
				user = UserLocalServiceUtil.getUserByEmailAddress(
					themeDisplay.getCompanyId(), emailAddress);
			}
			catch (NoSuchUserException nsue) {
				return null;
			}

			if (user.getStatus() != WorkflowConstants.STATUS_INCOMPLETE) {
				return  null;
			}
		}

		String name = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(user.getUserId());

		try {
			message = MBMessageServiceUtil.addDiscussionMessage(
				themeDisplay.getScopeGroupId(), className, classPK,
				permissionClassName, permissionClassPK, permissionOwnerId,
				threadId, parentMessageId, subject, body, serviceContext);
		}
		finally {
			PrincipalThreadLocal.setName(name);
		}
	}
	else {

		// Update message

		message = MBMessageServiceUtil.updateDiscussionMessage(
			className, classPK, permissionClassName, permissionClassPK,
			permissionOwnerId, messageId, subject, body, serviceContext);
	}

	// Subscription

	boolean subscribe = ParamUtil.getBoolean(actionRequest, "subscribe");

	if (subscribe) {
		SubscriptionLocalServiceUtil.addSubscription(
			themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
			className, classPK);
	}

	return message;
}

protected void deleteMessage(ActionRequest actionRequest) throws Exception {
	long groupId = PortalUtil.getScopeGroupId(actionRequest);

	String className = ParamUtil.getString(actionRequest, "className");
	long classPK = ParamUtil.getLong(actionRequest, "classPK");
	String permissionClassName = ParamUtil.getString(
		actionRequest, "permissionClassName");
	long permissionClassPK = ParamUtil.getLong(
		actionRequest, "permissionClassPK");
	long permissionOwnerId = ParamUtil.getLong(
		actionRequest, "permissionOwnerId");

	long messageId = ParamUtil.getLong(actionRequest, "messageId");

	MBMessageServiceUtil.deleteDiscussionMessage(
		groupId, className, classPK, permissionClassName, permissionClassPK,
		permissionOwnerId, messageId);
}

protected void subscribeToComments(
		ActionRequest actionRequest, boolean subscribe)
	throws Exception {

	ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
		WebKeys.THEME_DISPLAY);

	String className = ParamUtil.getString(actionRequest, "className");
	long classPK = ParamUtil.getLong(actionRequest, "classPK");

	if (subscribe) {
		SubscriptionLocalServiceUtil.addSubscription(
			themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
			className, classPK);
	}
	else {
		SubscriptionLocalServiceUtil.deleteSubscription(
			themeDisplay.getUserId(), className, classPK);
	}
}

protected void writeJSON(
		PortletRequest portletRequest, ActionResponse actionResponse,
		Object json)
	throws IOException {

	HttpServletResponse jsonResponse = PortalUtil.getHttpServletResponse(
		actionResponse);

	jsonResponse.setContentType(ContentTypes.TEXT_JAVASCRIPT);

	ServletResponseUtil.write(jsonResponse, json.toString());
}

protected void registerIndex(String className, Indexer indexer, HttpServletRequest request) throws Exception {
	Indexer classIndexer = IndexerRegistryUtil.getIndexer(className);

	if (classIndexer == null) {
		IndexerRegistryUtil.register(indexer);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);

		long companyId = themeDisplay.getCompanyId();

		String[] companyIds = new String[] {String.valueOf(companyId)};

		reindex(className, companyIds);
	}
}

protected void reindex(String className, String[] ids) throws Exception {
	Indexer indexer = IndexerRegistryUtil.getIndexer(className);

	indexer.reindex(ids);
}

protected void reindex(String className, Object obj) throws Exception {
	Indexer indexer = IndexerRegistryUtil.getIndexer(className);

	indexer.reindex(obj);
}

protected void updateAttachedModel(BaseModel baseModel, HttpServletRequest request) throws Exception {
	if (baseModel instanceof AttachedModel) {
		String className = ParamUtil.getString(request, "className");
		long classNameId = 0;
		long classPK = ParamUtil.getLong(request, "classPK");

		if (Validator.isNotNull(className)) {
			classNameId = PortalUtil.getClassNameId(className);
		}

		AttachedModel attachedModel = (AttachedModel)baseModel;

		if (classNameId > 0) {
			attachedModel.setClassNameId(classNameId);
		}

		attachedModel.setClassPK(classPK);
	}
}

protected void updateAuditedModel(BaseModel baseModel, ThemeDisplay themeDisplay) throws Exception {
	if (baseModel instanceof AuditedModel) {
		Date now = new Date();

		AuditedModel auditedModel = (AuditedModel)baseModel;

		auditedModel.setModifiedDate(now);

		if (baseModel.isNew()) {
			User user = UserLocalServiceUtil.getUser(themeDisplay.getUserId());

			auditedModel.setCompanyId(themeDisplay.getCompanyId());
			auditedModel.setUserId(themeDisplay.getUserId());
			auditedModel.setUserName(user.getFullName());
			auditedModel.setCreateDate(now);
		}
	}
}

protected void updateGroupedModel(BaseModel baseModel, ThemeDisplay themeDisplay) throws Exception {
	if ((baseModel instanceof GroupedModel) && baseModel.isNew()) {
		GroupedModel groupedModel = (GroupedModel)baseModel;

		groupedModel.setGroupId(themeDisplay.getScopeGroupId());
	}
}

protected void updateModel(BaseModel baseModel, HttpServletRequest request) throws Exception {
	BeanPropertiesUtil.setProperties(baseModel, request);

	if (baseModel.isNew()) {
		baseModel.setPrimaryKeyObj(CounterLocalServiceUtil.increment());
	}

	ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);

	updateAuditedModel(baseModel, themeDisplay);
	updateGroupedModel(baseModel, themeDisplay);
	updateAttachedModel(baseModel, request);
}

%>
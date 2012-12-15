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

<%@ include file="/html/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");

Application app = (Application)request.getAttribute(OAuthConstants.BEAN_ID);

String actionName = "addApplication";

boolean isNew = true;

if ((null != app) && (0L != app.getApplicationId())) {
	actionName = "updateApplication";
	isNew = false;
}
%>

<liferay-portlet:actionURL name="<%= actionName %>" var="addApplicationURL">
	<portlet:param name="mvcPath" value="/html/admin/edit.jsp" />
</liferay-portlet:actionURL>

<aui:form action="<%= addApplicationURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="applicationId" type="hidden" value='<%= isNew ? "" : app.getApplicationId() %>'/>

	<liferay-ui:header
		backURL="<%= backURL %>"
		localizeTitle="<%= (app == null) %>"
		title='<%= (app == null) ? "new-application" : app.getName() %>'
	/>

	<liferay-ui:error exception="<%= MalformedURLException.class %>" message="please-enter-a-valid-url" />
	<liferay-ui:error exception="<%= RequiredFieldException.class %>" message="this-field-is-required" />

	<aui:model-context bean="<%= app %>" model="<%= Application.class %>" />

	<aui:fieldset>
		<aui:input label="name" name="<%= OAuthConstants.NAME %>">
			<aui:validator name="required" />
		</aui:input>

		<aui:input cols="65" label="description" name="<%= OAuthConstants.DESCRIPTION %>" rows="5" type="textarea" />

		<aui:input label="website" name="<%= OAuthConstants.WEBSITE %>">
			<aui:validator name="required" />
			<aui:validator name="url" />
		</aui:input>

		<aui:input label="callback-url" name="<%= OAuthConstants.CALLBACK_URL %>">
			<aui:validator name="required" />
			<aui:validator name="url" />
		</aui:input>

		<c:if test="<%= isNew %>">
			<aui:select helpMessage="access-type-description" label="access-type" name="<%= OAuthConstants.ACCESS_TYPE %>">
				<aui:option label="<%= OAuthConstants.ACCESS_TYPE_OPTION.concat(Integer.toString(OAuthConstants.ACCESS_TYPE_READ)) %>" value="<%= OAuthConstants.ACCESS_TYPE_READ %>"></aui:option>
				<aui:option label="<%= OAuthConstants.ACCESS_TYPE_OPTION.concat(Integer.toString(OAuthConstants.ACCESS_TYPE_WRITE)) %>" value="<%= OAuthConstants.ACCESS_TYPE_WRITE %>"></aui:option>
			</aui:select>
		</c:if>

		<c:if test="<%= !isNew %>">
			<aui:field-wrapper helpMessage="application-credentials-description" label="application-credentials">
				<liferay-ui:message key="consumer-key" />: <%= app.getConsumerKey() %> <br />
				<liferay-ui:message key="consumer-secret" />: <%= app.getConsumerSecret() %>
			</aui:field-wrapper>

			<portlet:renderURL var="editApplicationLogoURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="mvcPath" value="/html/admin/edit_application_logo.jsp" />
				<portlet:param name="applicationId" value="<%= StringUtil.valueOf(app.getApplicationId()) %>" />
			</portlet:renderURL>

			<h3>
				<liferay-ui:message key="logo" />
			</h3>

			<liferay-ui:logo-selector
				defaultLogoURL='<%= themeDisplay.getPathImage() + "/logo?img_id=0" %>'
				editLogoURL="<%= editApplicationLogoURL %>"
				imageId="<%= app.getLogoId() %>"
				logoDisplaySelector=".application-logo" />
		</c:if>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<aui:script>
	function <portlet:namespace />saveApplication() {
		submitForm(document.<portlet:namespace />fm, "<%= addApplicationURL %>");
	}
</aui:script>
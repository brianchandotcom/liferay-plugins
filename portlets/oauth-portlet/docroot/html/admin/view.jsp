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

<c:if test="<%= SessionMessages.contains(request, OAuthConstants.REQUEST_PROCESSED) %>">
	<liferay-ui:success key="<%= OAuthConstants.REQUEST_PROCESSED %>" message="your-request-completed-successfully"></liferay-ui:success>
</c:if>

<portlet:actionURL var="searchActionURL" />

<aui:form action="<%= searchActionURL %>" name="fm">

<liferay-util:include page="/html/admin/toolbar.jsp" servletContext="<%= application %>">
	<liferay-util:param name="toolbarItem" value="view-all" />
</liferay-util:include>

<liferay-ui:search-container
	delta="5"
	searchContainer="<%= new OAuthApplicationSearch(renderRequest, currentURLObj) %>"
>

	<liferay-ui:search-form
		page="/html/admin/search.jsp"
		servletContext="<%= application %>"
	/>

	<%
	OAuthApplicationSearchTerms searchTerms = (OAuthApplicationSearchTerms)searchContainer.getSearchTerms();

	LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();

	String rowMvcPath = "/html/admin/edit.jsp";

	if (!adminUser) {
		params.put("userName", new Long(themeDisplay.getUserId()));
		rowMvcPath = "/html/admin/view_application.jsp";
	}
	%>

	<liferay-ui:search-container-results
		results="<%= ApplicationLocalServiceUtil.search(themeDisplay.getCompanyId(), searchTerms.getName(), params, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
		total="<%= ApplicationLocalServiceUtil.searchCount(themeDisplay.getCompanyId(), searchTerms.getName(), params) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.oauth.model.Application"
		keyProperty="applicationId"
		modelVar="app"
	>

		<liferay-portlet:renderURL var="rowHREF">
			<portlet:param name="mvcPath" value="<%= rowMvcPath %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="applicationId" value="<%= String.valueOf(app.getApplicationId()) %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:search-container-column-text
			href="<%= rowHREF %>"
			orderable="<%= true %>"
			property="applicationId"
		/>

		<liferay-ui:search-container-column-text
			href="<%= rowHREF %>"
			orderable="<%= true %>"
			property="name"
		/>

		<liferay-ui:search-container-column-text
			href="<%= rowHREF %>"
			property="website"
		/>

		<liferay-ui:search-container-column-text
			href="<%= rowHREF %>"
			name="access-type"
		>

			<liferay-ui:message key='<%= OAuthConstants.ACCESS_TYPE_SHORT.replace("{0}", String.valueOf(app.getAccessLevel())) %>' />

		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			href="<%= rowHREF %>"
			name="authorizations-count-short"
		>

			<%= ApplicationUserLocalServiceUtil.getApplicationUsersCount(app.getApplicationId()) %>

		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-jsp
			align="right"
			path="/html/admin/actions.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />

</liferay-ui:search-container>
</aui:form>
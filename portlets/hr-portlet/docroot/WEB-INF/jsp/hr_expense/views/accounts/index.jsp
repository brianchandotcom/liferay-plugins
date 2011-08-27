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

<%@ include file="/WEB-INF/jsp/hr_expense/views/init.jsp" %>

<liferay-util:include page="/WEB-INF/jsp/hr_expense/views/page_tabs.jsp" servletContext="<%= application %>">
	<liferay-util:param name="tabs1" value="accounts" />
</liferay-util:include>

<portlet:renderURL var="addHRExpenseAccountURL">
	<portlet:param name="controller" value="accounts" />
	<portlet:param name="action" value="edit" />
	<portlet:param name="format" value="html" />
</portlet:renderURL>

<aui:button-row>
	<aui:button onClick="${addHRExpenseAccountURL}" value="add-expense-account" />
</aui:button-row>

<liferay-ui:search-container
	emptyResultsMessage="there-are-no-expenses"
	headerNames="id,name,description"
>

	<liferay-ui:search-container-results
		results="<%= HRExpenseAccountLocalServiceUtil.getHRExpenseAccounts(searchContainer.getStart(), searchContainer.getEnd()) %>"
		total="<%= HRExpenseAccountLocalServiceUtil.getHRExpenseAccountsCount() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.hr.model.HRExpenseAccount"
		escapedModel="<%= true %>"
		keyProperty="hrExpenseAccountId"
		modelVar="curHRExpenseAccount"
	>

		<portlet:renderURL var="viewHRExpenseAccountURL">
			<portlet:param name="controller" value="accounts" />
			<portlet:param name="action" value="view" />
			<portlet:param name="id" value="${curHRExpenseAccount.hrExpenseAccountId}" />
			<portlet:param name="format" value="html" />
		</portlet:renderURL>

		<liferay-ui:search-container-column-text
			href="${viewHRExpenseAccountURL}"
			name="id"
			property="hrExpenseAccountId"
		/>

		<liferay-ui:search-container-column-text
			href="${viewHRExpenseAccountURL}"
			name="name"
			property="name"
		/>

		<liferay-ui:search-container-column-text
			href="${viewHRExpenseAccountURL}"
			name="description"
			property="description"
		/>

		<portlet:renderURL var="editHRExpenseAccountURL">
			<portlet:param name="controller" value="accounts" />
			<portlet:param name="action" value="edit" />
			<portlet:param name="id" value="${curHRExpenseAccount.hrExpenseAccountId}" />
			<portlet:param name="format" value="html" />
		</portlet:renderURL>

		<liferay-ui:search-container-column-text
			href="${editHRExpenseAccountURL}"
			value="edit"
		/>

		<portlet:actionURL var="deleteHRExpenseAccountURL">
			<portlet:param name="controller" value="accounts" />
			<portlet:param name="action" value="delete" />
			<portlet:param name="id" value="${curHRExpenseAccount.hrExpenseAccountId}" />
			<portlet:param name="format" value="html" />
		</portlet:actionURL>

		<liferay-ui:search-container-column-text
			href="javascript:submitForm(document.hrefFm, '${deleteHRExpenseAccountURL}&p_p_state=normal');"
			value="x"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>
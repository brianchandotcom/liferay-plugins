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

<portlet:renderURL var="addHRExpenseTypeURL">
	<portlet:param name="controller" value="types" />
	<portlet:param name="action" value="edit" />
	<portlet:param name="format" value="html" />
</portlet:renderURL>

<aui:button-row>
	<aui:button onClick="${addHRExpenseTypeURL}" value="add-expense-type" />
</aui:button-row>

<liferay-ui:search-container
	emptyResultsMessage="there-are-no-expenses"
	headerNames="id,name,description"
>

	<liferay-ui:search-container-results
		results="<%= HRExpenseTypeLocalServiceUtil.getHRExpenseTypes(searchContainer.getStart(), searchContainer.getEnd()) %>"
		total="<%= HRExpenseTypeLocalServiceUtil.getHRExpenseTypesCount() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.hr.model.HRExpenseType"
		escapedModel="<%= true %>"
		keyProperty="hrExpenseTypeId"
		modelVar="curHRExpenseType"
	>

		<portlet:renderURL var="viewHRExpenseTypeURL">
			<portlet:param name="controller" value="types" />
			<portlet:param name="action" value="view" />
			<portlet:param name="id" value="${curHRExpenseType.hrExpenseTypeId}" />
			<portlet:param name="format" value="html" />
		</portlet:renderURL>

		<liferay-ui:search-container-column-text
			href="${viewHRExpenseTypeURL}"
			name="id"
			property="hrExpenseTypeId"
		/>

		<liferay-ui:search-container-column-text
			href="${viewHRExpenseTypeURL}"
			name="name"
			property="name"
		/>

		<liferay-ui:search-container-column-text
			href="${viewHRExpenseTypeURL}"
			name="description"
			property="description"
		/>

		<portlet:renderURL var="editHRExpenseTypeURL">
			<portlet:param name="controller" value="types" />
			<portlet:param name="action" value="edit" />
			<portlet:param name="id" value="${curHRExpenseType.hrExpenseTypeId}" />
			<portlet:param name="format" value="html" />
		</portlet:renderURL>

		<liferay-ui:search-container-column-text
			href="${editHRExpenseTypeURL}"
			value="edit"
		/>

		<portlet:actionURL var="deleteHRExpenseTypeURL">
			<portlet:param name="controller" value="types" />
			<portlet:param name="action" value="delete" />
			<portlet:param name="id" value="${curHRExpenseType.hrExpenseTypeId}" />
			<portlet:param name="format" value="html" />
		</portlet:actionURL>

		<liferay-ui:search-container-column-text
			href="javascript:submitForm(document.hrefFm, '${deleteHRExpenseTypeURL}&p_p_state=normal');"
			value="x"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>
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

<portlet:renderURL var="viewHRExpenseTypesURL">
	<portlet:param name="controller" value="types" />
	<portlet:param name="action" value="index" />
	<portlet:param name="format" value="html" />
</portlet:renderURL>

<portlet:renderURL var="editHRExpenseTypeURL">
	<portlet:param name="controller" value="types" />
	<portlet:param name="action" value="edit" />
	<portlet:param name="id" value="${hrExpenseType.hrExpenseTypeId}" />
	<portlet:param name="format" value="html" />
</portlet:renderURL>

<aui:a href="${viewHRExpenseTypesURL}">View Expense Types</aui:a>

<br />

<aui:a href="${editHRExpenseTypeURL}">Edit Expense Type</aui:a>

<br />

<aui:fieldset>
	<aui:field-wrapper name="hrExpenseTypeId">${hrExpenseType.hrExpenseTypeId}</aui:field-wrapper>
	<aui:field-wrapper name="name">${hrExpenseType.name}</aui:field-wrapper>
	<aui:field-wrapper name="description">${hrExpenseType.description}</aui:field-wrapper>
</aui:fieldset>
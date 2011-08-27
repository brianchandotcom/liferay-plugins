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

<portlet:renderURL var="viewHRExpenseCurrenciesURL">
	<portlet:param name="controller" value="currencies" />
	<portlet:param name="action" value="index" />
	<portlet:param name="format" value="html" />
</portlet:renderURL>

<portlet:renderURL var="editHRExpenseCurrencyURL">
	<portlet:param name="controller" value="currencies" />
	<portlet:param name="action" value="edit" />
	<portlet:param name="id" value="${hrExpenseCurrency.hrExpenseCurrencyId}" />
	<portlet:param name="format" value="html" />
</portlet:renderURL>

<aui:a href="${viewHRExpenseCurrenciesURL}">View Expense Currencies</aui:a>

<br />

<aui:a href="${editHRExpenseCurrencyURL}">Edit Expense Currency</aui:a>

<br />

<aui:fieldset>
	<aui:field-wrapper name="hrExpenseCurrencyId">${hrExpenseCurrency.hrExpenseCurrencyId}</aui:field-wrapper>
	<aui:field-wrapper name="code">${hrExpenseCurrency.code}</aui:field-wrapper>
	<aui:field-wrapper name="name">${hrExpenseCurrency.name}</aui:field-wrapper>
	<aui:field-wrapper name="description">${hrExpenseCurrency.description}</aui:field-wrapper>
</aui:fieldset>
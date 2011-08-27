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

<portlet:renderURL var="viewHRExpensesURLTab">
	<portlet:param name="controller" value="expenses" />
	<portlet:param name="action" value="index" />
	<portlet:param name="format" value="html" />
</portlet:renderURL>

<portlet:renderURL var="viewHRExpenseAccountsURLTab">
	<portlet:param name="controller" value="accounts" />
	<portlet:param name="action" value="index" />
	<portlet:param name="format" value="html" />
</portlet:renderURL>

<portlet:renderURL var="viewHRExpenseTypesURLTab">
	<portlet:param name="controller" value="types" />
	<portlet:param name="action" value="index" />
	<portlet:param name="format" value="html" />
</portlet:renderURL>

<portlet:renderURL var="viewHRExpenseCurrenciesURLTab">
	<portlet:param name="controller" value="currencies" />
	<portlet:param name="action" value="index" />
	<portlet:param name="format" value="html" />
</portlet:renderURL>

<liferay-ui:tabs
	names="expenses,accounts,types,currencies"
	url0="${viewHRExpensesURLTab}"
	url1="${viewHRExpenseAccountsURLTab}"
	url2="${viewHRExpenseTypesURLTab}"
	url3="${viewHRExpenseCurrenciesURLTab}"
/>
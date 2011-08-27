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

<portlet:renderURL var="addHRExpenseCurrencyURL">
	<portlet:param name="controller" value="currencies" />
	<portlet:param name="action" value="edit" />
	<portlet:param name="format" value="html" />
</portlet:renderURL>

<aui:button-row>
	<aui:button onClick="${addHRExpenseCurrencyURL}" value="add-expense-currency" />
</aui:button-row>

<liferay-ui:search-container
	emptyResultsMessage="there-are-no-expenses"
	headerNames="id,code,name,description"
>

	<liferay-ui:search-container-results
		results="<%= HRExpenseCurrencyLocalServiceUtil.getHRExpenseCurrencies(searchContainer.getStart(), searchContainer.getEnd()) %>"
		total="<%= HRExpenseCurrencyLocalServiceUtil.getHRExpenseCurrenciesCount() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.hr.model.HRExpenseCurrency"
		escapedModel="<%= true %>"
		keyProperty="hrExpenseCurrencyId"
		modelVar="curHRExpenseCurrency"
	>

		<portlet:renderURL var="viewHRExpenseCurrencyURL">
			<portlet:param name="controller" value="currencies" />
			<portlet:param name="action" value="view" />
			<portlet:param name="id" value="${curHRExpenseCurrency.hrExpenseCurrencyId}" />
			<portlet:param name="format" value="html" />
		</portlet:renderURL>

		<liferay-ui:search-container-column-text
			href="${viewHRExpenseCurrencyURL}"
			name="id"
			property="hrExpenseCurrencyId"
		/>

		<liferay-ui:search-container-column-text
			href="${viewHRExpenseCurrencyURL}"
			name="code"
			property="code"
		/>

		<liferay-ui:search-container-column-text
			href="${viewHRExpenseCurrencyURL}"
			name="name"
			property="name"
		/>

		<liferay-ui:search-container-column-text
			href="${viewHRExpenseCurrencyURL}"
			name="description"
			property="description"
		/>

		<portlet:renderURL var="editHRExpenseCurrencyURL">
			<portlet:param name="controller" value="currencies" />
			<portlet:param name="action" value="edit" />
			<portlet:param name="id" value="${curHRExpenseCurrency.hrExpenseCurrencyId}" />
			<portlet:param name="format" value="html" />
		</portlet:renderURL>

		<liferay-ui:search-container-column-text
			href="${editHRExpenseCurrencyURL}"
			value="edit"
		/>

		<portlet:actionURL var="deleteHRExpenseCurrencyURL">
			<portlet:param name="controller" value="currencies" />
			<portlet:param name="action" value="delete" />
			<portlet:param name="id" value="${curHRExpenseCurrency.hrExpenseCurrencyId}" />
			<portlet:param name="format" value="html" />
		</portlet:actionURL>

		<liferay-ui:search-container-column-text
			href="javascript:submitForm(document.hrefFm, '${deleteHRExpenseCurrencyURL}&p_p_state=normal');"
			value="x"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>
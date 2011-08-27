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

<portlet:renderURL var="viewHRExpenseCurrencyConversionsURL">
	<portlet:param name="controller" value="currency_conversions" />
	<portlet:param name="action" value="index" />
	<portlet:param name="format" value="html" />
</portlet:renderURL>

<portlet:renderURL var="editHRExpenseCurrencyConversionURL">
	<portlet:param name="controller" value="currency_conversions" />
	<portlet:param name="action" value="edit" />
	<portlet:param name="id" value="${hrExpenseCurrencyConversion.hrExpenseCurrencyConversionId}" />
	<portlet:param name="format" value="html" />
</portlet:renderURL>

<aui:a href="${viewHRExpenseCurrencyConversionsURL}">View Expense Currency Conversions</aui:a>

<br />

<aui:a href="${editHRExpenseCurrencyConversionURL}">Edit Expense Currency</aui:a>

<br />

<aui:fieldset label="currency">
	<aui:layout>
		<aui:column>
			<aui:field-wrapper name="hrExpenseCurrencyConversionId">${hrExpenseCurrencyConversion.hrExpenseCurrencyConversionId}</aui:field-wrapper>
		</aui:column>
		<aui:column>
			<aui:field-wrapper name="conversionDate">${conversionDate}</aui:field-wrapper>
		</aui:column>
		<aui:column>
			<aui:field-wrapper name="fromCurrencyCode">${fromHRExpenseCurrency.code}</aui:field-wrapper>
		</aui:column>
		<aui:column>
			<aui:field-wrapper name="toCurrencyCode">${fromHRExpenseCurrency.code}</aui:field-wrapper>
		</aui:column>
		<aui:column>
			<aui:field-wrapper name="conversionValue">${hrExpenseCurrencyConversion.conversionValue}</aui:field-wrapper>
		</aui:column>
	</aui:layout>
</aui:fieldset>
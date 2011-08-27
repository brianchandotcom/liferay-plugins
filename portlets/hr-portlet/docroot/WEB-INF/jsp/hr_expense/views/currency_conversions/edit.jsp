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

<aui:a href="${viewHRExpenseCurrencyConversionsURL}">View Expense Currencies</aui:a>

<br />

<portlet:actionURL var="saveHRExpenseCurrencyConversionURL">
	<portlet:param name="controller" value="currency_conversions" />
	<portlet:param name="action" value="${cmd}" />
	<portlet:param name="format" value="html" />
</portlet:actionURL>

<aui:model-context bean="${hrExpenseCurrencyConversion}" model="<%= HRExpenseCurrencyConversion.class %>" />

<aui:form action='<%= saveHRExpenseCurrencyConversionURL + "&p_p_state=normal" %>' method="post">
	<aui:input name="redirect" type="hidden" value="${viewHRExpenseCurrencyConversionsURL}" />

	<c:choose>
		<c:when test="${hrExpenseCurrencyConversion.hrExpenseCurrencyConversionId > 0}">
			<aui:input name="hrExpenseCurrencyConversionId" type="hidden" value="${hrExpenseCurrencyConversion.hrExpenseCurrencyConversionId}" />
		</c:when>
		<c:otherwise>
			<aui:input name="new" type="hidden" value="1" />
		</c:otherwise>
	</c:choose>
	<aui:fieldset label="currency">
		<aui:layout>
			<aui:column>
				<aui:input name="conversionDate" />
			</aui:column>
			<aui:column>
				<aui:select label="from-currency-code" name="fromHRExpenseCurrencyId" showEmptyOption="<%= true %>">
					<c:forEach items="${hrExpenseCurrencies}" var="hrExpenseCurrency">
						<aui:option label="${hrExpenseCurrency.code}" selected="${hrExpense.fromHRExpenseCurrencyId == hrExpenseCurrency.hrExpenseCurrencyId}" value="${hrExpenseCurrency.hrExpenseCurrencyId}" />
					</c:forEach>
				</aui:select>
			</aui:column>
			<aui:column>
				<aui:select label="to-currency-code" name="toHRExpenseCurrencyId" showEmptyOption="<%= true %>">
					<c:forEach items="${hrExpenseCurrencies}" var="hrExpenseCurrency">
						<aui:option label="${hrExpenseCurrency.code}" selected="${hrExpense.toHRExpenseCurrencyId == hrExpenseCurrency.hrExpenseCurrencyId}" value="${hrExpenseCurrency.hrExpenseCurrencyId}" />
					</c:forEach>
				</aui:select>
			</aui:column>
			<aui:column>
				<aui:input name="conversionValue" />
			</aui:column>
		</aui:layout>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />
		<aui:button href="${viewHRExpenseCurrencyConversionsURL}" type="cancel" />
	</aui:button-row>
</aui:form>
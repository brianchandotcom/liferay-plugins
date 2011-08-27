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

<aui:a href="${viewHRExpenseCurrenciesURL}">View Expense Currencies</aui:a>

<br />

<portlet:actionURL var="saveHRExpenseCurrencyURL">
	<portlet:param name="controller" value="currencies" />
	<portlet:param name="action" value="${cmd}" />
	<portlet:param name="format" value="html" />
</portlet:actionURL>

<aui:model-context bean="${hrExpenseCurrency}" model="<%= HRExpenseCurrency.class %>" />

<aui:form action='<%= saveHRExpenseCurrencyURL + "&p_p_state=normal" %>' method="post">
	<aui:input name="redirect" type="hidden" value="${viewHRExpenseCurrenciesURL}" />

	<c:choose>
		<c:when test="${hrExpenseCurrency.hrExpenseCurrencyId > 0}">
			<aui:input name="hrExpenseCurrencyId" type="hidden" value="${hrExpenseCurrency.hrExpenseCurrencyId}" />
		</c:when>
		<c:otherwise>
			<aui:input name="new" type="hidden" value="1" />
		</c:otherwise>
	</c:choose>

	<aui:input name="code" />
	<aui:input name="name" />
	<aui:input name="description" />

	<aui:button-row>
		<aui:button type="submit" />
		<aui:button href="${viewHRExpenseCurrenciesURL}" type="cancel" />
	</aui:button-row>
</aui:form>
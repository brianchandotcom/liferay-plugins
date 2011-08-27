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

<aui:a href="${viewHRExpenseTypesURL}">View Expense Types</aui:a>

<br />

<portlet:actionURL var="saveHRExpenseTypeURL">
	<portlet:param name="controller" value="types" />
	<portlet:param name="action" value="${cmd}" />
	<portlet:param name="format" value="html" />
</portlet:actionURL>

<aui:model-context bean="${hrExpenseType}" model="<%= HRExpenseType.class %>" />

<aui:form action='<%= saveHRExpenseTypeURL + "&p_p_state=normal" %>' method="post">
	<aui:input name="redirect" type="hidden" value="${viewHRExpenseTypesURL}" />

	<c:choose>
		<c:when test="${hrExpenseType.hrExpenseTypeId > 0}">
			<aui:input name="hrExpenseTypeId" type="hidden" value="${hrExpenseType.hrExpenseTypeId}" />
		</c:when>
		<c:otherwise>
			<aui:input name="new" type="hidden" value="1" />
		</c:otherwise>
	</c:choose>

	<aui:input name="name" />
	<aui:input name="description" />

	<aui:button-row>
		<aui:button type="submit" />
		<aui:button href="${viewHRExpenseTypesURL}" type="cancel" />
	</aui:button-row>
</aui:form>
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

<portlet:renderURL var="viewHRExpensesURL">
	<portlet:param name="controller" value="expenses" />
	<portlet:param name="action" value="index" />
	<portlet:param name="format" value="html" />
</portlet:renderURL>

<portlet:renderURL var="editHRExpenseURL">
	<portlet:param name="controller" value="expenses" />
	<portlet:param name="action" value="edit" />
	<portlet:param name="id" value="${hrExpense.hrExpenseId}" />
	<portlet:param name="format" value="html" />
</portlet:renderURL>

<aui:a href="${viewHRExpensesURL}">View Expenses</aui:a>

<br />

<aui:a href="${editHRExpenseURL}">Edit Expense</aui:a>

<br />

<aui:fieldset label="details">
	<aui:layout>
		<aui:column>
			<aui:field-wrapper name="hrExpenseId">${hrExpense.hrExpenseId}</aui:field-wrapper>
		</aui:column>
		<aui:column>
			<aui:field-wrapper name="user">${hrUserFullName}</aui:field-wrapper>
		</aui:column>
		<aui:column>
			<aui:field-wrapper name="account">${hrExpenseAccount.name}</aui:field-wrapper>
		</aui:column>
		<aui:column>
			<aui:field-wrapper name="type">${hrExpenseType.name}</aui:field-wrapper>
		</aui:column>
	</aui:layout>
</aui:fieldset>

<aui:fieldset label="expense">
	<aui:layout>
		<aui:column>
			<aui:field-wrapper name="expenseDate">${expenseDate}</aui:field-wrapper>
		</aui:column>
		<aui:column>
			<aui:field-wrapper name="expenseAmount">${hrExpense.expenseAmount}</aui:field-wrapper>
		</aui:column>
		<aui:column>
			<aui:field-wrapper name="expenseCurrencyCode">${expenseHRExpenseCurrency.code}</aui:field-wrapper>
		</aui:column>
		<aui:column>
			<aui:field-wrapper name="reimbursementAmount">${hrExpense.reimbursementAmount}</aui:field-wrapper>
		</aui:column>
		<aui:column>
			<aui:field-wrapper name="reimbursementCurrencyCode">${reimbursementHRExpenseCurrency.code}</aui:field-wrapper>
		</aui:column>
	</aui:layout>
</aui:fieldset>

<aui:fieldset label="comments">
	<portlet:actionURL var="saveCommentsURL">
		<portlet:param name="controller" value="expenses" />
		<portlet:param name="action" value="comment" />
		<portlet:param name="format" value="html" />
	</portlet:actionURL>

	<liferay-ui:discussion
		className="<%= HRExpense.class.getName() %>"
		classPK="${hrExpense.hrExpenseId}"
		formAction="${saveCommentsURL}"
		formName="fm"
		ratingsEnabled="<%= false %>"
		redirect="${editHRExpenseURL}"
		subject="${hrExpense.hrExpenseId}"
		userId="<%= themeDisplay.getUserId() %>"
	/>
</aui:fieldset>
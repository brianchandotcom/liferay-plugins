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

<aui:a href="${viewHRExpensesURL}">View Expenses</aui:a>

<br />

<portlet:actionURL var="saveHRExpenseURL">
	<portlet:param name="controller" value="expenses" />
	<portlet:param name="action" value="${cmd}" />
	<portlet:param name="format" value="html" />
</portlet:actionURL>

<aui:model-context bean="${hrExpense}" model="<%= HRExpense.class %>" />

<aui:form action='<%= saveHRExpenseURL + "&p_p_state=normal" %>' method="post" name="fm1">
	<aui:input name="redirect" type="hidden" value="${viewHRExpensesURL}" />
	<aui:input name="hrUserId" type="hidden" value="<%= themeDisplay.getUserId() %>" />
	<aui:input name="hrExpenseAccountId" type="hidden" value="${hrExpense.hrExpenseAccountId}" />
	<aui:input name="hrExpenseTypeId" type="hidden" value="${hrExpense.hrExpenseTypeId}" />
	<aui:input name="className" type="hidden" value="<%= HRExpense.class.getName() %>" />
	<aui:input name="classPK" type="hidden" value="${hrExpense.hrExpenseId}" />

	<c:choose>
		<c:when test="${hrExpense.hrExpenseId > 0}">
			<aui:input name="hrExpenseId" type="hidden" value="${hrExpense.hrExpenseId}" />
		</c:when>
		<c:otherwise>
			<aui:input name="new" type="hidden" value="1" />
		</c:otherwise>
	</c:choose>

	<aui:fieldset label="details">
		<aui:layout>
			<aui:column>
				<aui:input name="hrExpenseAccountName" type="text" value="${hrExpenseAccountName}" />
			</aui:column>
			<aui:column>
				<aui:input name="hrExpenseTypeName" type="text" value="${hrExpenseTypeName}" />
			</aui:column>
		</aui:layout>
	</aui:fieldset>

	<aui:fieldset label="expense">
		<aui:layout>
			<aui:column>
				<aui:input name="expenseDate" />
			</aui:column>
		</aui:layout>
		<aui:layout>
			<aui:column>
				<aui:select label="expense-currency-code" name="expenseHRExpenseCurrencyId" showEmptyOption="<%= true %>">
					<c:forEach items="${hrExpenseCurrencies}" var="hrExpenseCurrency">
						<aui:option label="${hrExpenseCurrency.code}" selected="${hrExpense.expenseHRExpenseCurrencyId == hrExpenseCurrency.hrExpenseCurrencyId}" value="${hrExpenseCurrency.hrExpenseCurrencyId}" />
					</c:forEach>
				</aui:select>
			</aui:column>
			<aui:column>
				<aui:input name="expenseAmount" />
			</aui:column>
			<aui:column>
				<aui:select label="reimbursement-currency-code" name="reimbursementHRExpenseCurrencyId" showEmptyOption="<%= true %>">
					<c:forEach items="${hrExpenseCurrencies}" var="hrExpenseCurrency">
						<aui:option label="${hrExpenseCurrency.code}" selected="${hrExpense.reimbursementHRExpenseCurrencyId == hrExpenseCurrency.hrExpenseCurrencyId}" value="${hrExpenseCurrency.hrExpenseCurrencyId}" />
					</c:forEach>
				</aui:select>
			</aui:column>
			<aui:column>
				<aui:input name="reimbursementAmount" disabled="<%= true %>" />
			</aui:column>
		</aui:layout>
		<aui:layout>
			<aui:column>
				<aui:button-row>
					<portlet:renderURL var="viewHRExpenseCurrencyConversionsURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
						<portlet:param name="controller" value="currency_conversions" />
						<portlet:param name="action" value="index" />
						<portlet:param name="format" value="html" />
					</portlet:renderURL>

					<aui:button name="viewCurrencyConversions" value="view-currency-conversions" />
				</aui:button-row>
			</aui:column>
		</aui:layout>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />
		<aui:button href="${viewHRExpensesURL}" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base,json-parse">
	var inputExpenseNode = A.one('#<portlet:namespace />expenseAmount');
	var selectExpenseNode = A.one('#<portlet:namespace />expenseHRExpenseCurrencyId');
	var selectReimbursementNode = A.one('#<portlet:namespace />reimbursementHRExpenseCurrencyId');
	var resultNode = A.one('#<portlet:namespace />reimbursementAmount');

	var onChangeFn = function(event) {
		var inputExpenseValue = inputExpenseNode.get('value');
		var selectExpenseId = selectExpenseNode.get('value');
		var selectReimbursementId = selectReimbursementNode.get('value');

		var data = A.JSON.parse('${hrExpenseCurrencyConversionsJSON}');

		for (var i in data) {
			var conversion = data[i];

			var fromCurrency = conversion.fromHRExpenseCurrencyId;
			var toCurrency = conversion.toHRExpenseCurrencyId;
			var conversionValue = conversion.conversionValue;

			if ((selectExpenseId == fromCurrency) && (selectReimbursementId == toCurrency)) {
				var resultValue = inputExpenseValue * conversionValue;

				resultNode.set('value', roundNumber(resultValue, 2));
			}
		}
	};

	inputExpenseNode.on('keyup', onChangeFn);
	selectExpenseNode.on('change', onChangeFn);
	selectReimbursementNode.on('change', onChangeFn);

	function roundNumber(num, dec) {
		var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
		return result;
	}
</aui:script>

<aui:script use="aui-base">
	var viewCurrencyConversionsButton = A.one('#<portlet:namespace />viewCurrencyConversions');

	viewCurrencyConversionsButton.on(
		'click',
		function(event) {
			Liferay.HR.displayPopup('${viewHRExpenseCurrencyConversionsURL}', '<liferay-ui:message key="view-currency-conversions" />');
		}
	);
</aui:script>
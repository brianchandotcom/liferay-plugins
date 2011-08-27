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

<liferay-util:include page="/WEB-INF/jsp/hr_expense/views/page_tabs.jsp" servletContext="<%= application %>">
	<liferay-util:param name="tabs1" value="expenses" />
</liferay-util:include>

<portlet:renderURL var="addHRExpenseURL">
	<portlet:param name="controller" value="expenses" />
	<portlet:param name="action" value="edit" />
	<portlet:param name="format" value="html" />
</portlet:renderURL>

<aui:button-row>
	<aui:button onClick="${addHRExpenseURL}" value="add-expense" />
</aui:button-row>

<portlet:renderURL var="searchURL">
	<portlet:param name="controller" value="expenses" />
	<portlet:param name="action" value="index" />
	<portlet:param name="format" value="html" />
</portlet:renderURL>

<aui:form action="${searchURL}" method="get" name="searchFm">
	<aui:fieldset>
		<aui:input inlineField="<%= true %>" label="" name="keywords" size="30" title="search-expenses" type="text" />

		<aui:button type="submit" value="search" />
	</aui:fieldset>
</aui:form>

<liferay-ui:search-container
	emptyResultsMessage="there-are-no-expenses"
	headerNames="id,account,type,user,expense-date,expense-amount,expense-currency,reimbursement-amount,reimbursement-currency,status"
>

	<liferay-ui:search-container-results>
		<%
		String keywords = ParamUtil.getString(request, "keywords");

		if (Validator.isNull(keywords)) {
			pageContext.setAttribute("results", HRExpenseLocalServiceUtil.getHRExpenses(searchContainer.getStart(), searchContainer.getEnd()));
			pageContext.setAttribute("total", HRExpenseLocalServiceUtil.getHRExpensesCount());
		}
		else {
			SearchContext searchContext = SearchContextFactory.getInstance(request);

			searchContext.setEnd(searchContainer.getEnd());
			searchContext.setKeywords(keywords);
			searchContext.setStart(searchContainer.getStart());

			Indexer indexer = IndexerRegistryUtil.getIndexer(HRExpense.class);

			Hits hits = indexer.search(searchContext);

			List<HRExpense> hrExpenses = new ArrayList<HRExpense>();

			for (int i = 0; i < hits.getDocs().length; i++) {
				Document doc = hits.doc(i);

				long hrExpenseId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

				HRExpense hrExpense = HRExpenseLocalServiceUtil.getHRExpense(hrExpenseId);

				hrExpenses.add(hrExpense);
			}

			pageContext.setAttribute("results", hrExpenses);
			pageContext.setAttribute("total", hits.getLength());
		}
		%>
	</liferay-ui:search-container-results>

	<liferay-ui:search-container-row
		className="com.liferay.hr.model.HRExpense"
		escapedModel="<%= true %>"
		keyProperty="hrExpenseId"
		modelVar="curHRExpense"
	>

		<portlet:renderURL var="viewHRExpenseURL">
			<portlet:param name="controller" value="expenses" />
			<portlet:param name="action" value="view" />
			<portlet:param name="id" value="${curHRExpense.hrExpenseId}" />
			<portlet:param name="format" value="html" />
		</portlet:renderURL>

		<liferay-ui:search-container-column-text
			href="${viewHRExpenseURL}"
			name="id"
			property="hrExpenseId"
		/>

		<liferay-ui:search-container-column-text
			buffer="buffer"
			href="${viewHRExpenseURL}"
			name="account"
		>
			<%
			long hrExpenseAccountId = curHRExpense.getHrExpenseAccountId();

			if (hrExpenseAccountId > 0) {
				HRExpenseAccount hrExpenseAccount = HRExpenseAccountLocalServiceUtil.getHRExpenseAccount(hrExpenseAccountId);

				buffer.append(hrExpenseAccount.getName());
			}
			%>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			buffer="buffer"
			href="${viewHRExpenseURL}"
			name="type"
		>
			<%
			long hrExpenseTypeId = curHRExpense.getHrExpenseTypeId();

			if (hrExpenseTypeId > 0) {
				HRExpenseType hrExpenseType = HRExpenseTypeLocalServiceUtil.getHRExpenseType(hrExpenseTypeId);

				buffer.append(hrExpenseType.getName());
			}
			%>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			buffer="buffer"
			href="${viewHRExpenseURL}"
			name="user"
		>
			<%
			long hrUserId = curHRExpense.getHrUserId();

			if (hrUserId > 0) {
				User user2 = UserLocalServiceUtil.getUser(hrUserId);

				buffer.append(user2.getFullName());
			}
			%>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			href="${viewHRExpenseURL}"
			name="expense-date"
			value="<%= dateFormat.format(curHRExpense.getExpenseDate()) %>"
		/>

		<liferay-ui:search-container-column-text
			href="${viewHRExpenseURL}"
			name="expense-amount"
			property="expenseAmount"
		/>

		<liferay-ui:search-container-column-text
			buffer="buffer"
			href="${viewHRExpenseURL}"
			name="expense-currency"
		>
			<%
			long expenseHRExpenseCurrencyId = curHRExpense.getExpenseHRExpenseCurrencyId();

			if (expenseHRExpenseCurrencyId > 0) {
				HRExpenseCurrency expenseHRExpenseCurrency = HRExpenseCurrencyLocalServiceUtil.getHRExpenseCurrency(expenseHRExpenseCurrencyId);

				buffer.append(expenseHRExpenseCurrency.getCode());
			}
			%>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			href="${viewHRExpenseURL}"
			name="reimbursement-amount"
			property="reimbursementAmount"
		/>

		<liferay-ui:search-container-column-text
			buffer="buffer"
			href="${viewHRExpenseURL}"
			name="reimbursement-currency"
		>
			<%
			long reimbursementHRExpenseCurrencyId = curHRExpense.getReimbursementHRExpenseCurrencyId();

			if (reimbursementHRExpenseCurrencyId > 0) {
				HRExpenseCurrency reimbursementHRExpenseCurrency = HRExpenseCurrencyLocalServiceUtil.getHRExpenseCurrency(reimbursementHRExpenseCurrencyId);

				buffer.append(reimbursementHRExpenseCurrency.getCode());
			}
			%>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			href="${viewHRExpenseURL}"
			name="status"
		>
			<aui:workflow-status status="${curHRExpense.status}" />
		</liferay-ui:search-container-column-text>

		<portlet:renderURL var="editHRExpenseURL">
			<portlet:param name="controller" value="expenses" />
			<portlet:param name="action" value="edit" />
			<portlet:param name="id" value="${curHRExpense.hrExpenseId}" />
			<portlet:param name="format" value="html" />
		</portlet:renderURL>

		<liferay-ui:search-container-column-text
			href="${editHRExpenseURL}"
			value="edit"
		/>

		<portlet:actionURL var="deleteHRExpenseURL">
			<portlet:param name="controller" value="expenses" />
			<portlet:param name="action" value="delete" />
			<portlet:param name="id" value="${curHRExpense.hrExpenseId}" />
			<portlet:param name="format" value="html" />
		</portlet:actionURL>

		<liferay-ui:search-container-column-text
			href="javascript:submitForm(document.hrefFm, '${deleteHRExpenseURL}&p_p_state=normal');"
			value="x"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>
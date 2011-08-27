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

<%@ page import="com.liferay.hr.model.HRExpense" %>
<%@ page import="com.liferay.hr.model.HRExpenseAccount" %>
<%@ page import="com.liferay.hr.model.HRExpenseType" %>
<%@ page import="com.liferay.hr.service.HRExpenseAccountLocalServiceUtil" %>
<%@ page import="com.liferay.hr.service.HRExpenseLocalServiceUtil" %>
<%@ page import="com.liferay.hr.service.HRExpenseTypeLocalServiceUtil" %>
<%@ page import="com.liferay.portal.kernel.search.BaseIndexer" %>
<%@ page import="com.liferay.portal.kernel.search.BooleanQuery" %>
<%@ page import="com.liferay.portal.kernel.search.Document" %>
<%@ page import="com.liferay.portal.kernel.search.DocumentImpl" %>
<%@ page import="com.liferay.portal.kernel.search.Field" %>
<%@ page import="com.liferay.portal.kernel.search.Indexer" %>
<%@ page import="com.liferay.portal.kernel.search.SearchContext" %>
<%@ page import="com.liferay.portal.kernel.search.SearchEngineUtil" %>
<%@ page import="com.liferay.portal.kernel.search.Summary" %>
<%@ page import="com.liferay.portal.kernel.util.GetterUtil" %>
<%@ page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>

<%!
public class HRExpenseIndexer extends BaseIndexer {

	private String[] CLASS_NAMES = {HRExpense.class.getName()};

	public static final String PORTLET_ID = "1_WAR_hrportlet";

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		int status = GetterUtil.getInteger(
			searchContext.getAttribute(Field.STATUS),
			WorkflowConstants.STATUS_ANY);

		if (status != WorkflowConstants.STATUS_ANY) {
			contextQuery.addRequiredTerm(Field.STATUS, status);
		}
	}

	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, "expenseDate", false);
		addSearchTerm(searchQuery, searchContext, "hrExpenseAccountId", false);
		addSearchTerm(searchQuery, searchContext, "hrExpenseAccountName", true);
		addSearchTerm(searchQuery, searchContext, "hrExpenseTypeId", false);
		addSearchTerm(searchQuery, searchContext, "hrExpenseTypeName", true);
		addSearchTerm(searchQuery, searchContext, "hrUserId", false);
	}

	protected void doDelete(Object obj) throws Exception {
		HRExpense hrExpense = (HRExpense)obj;

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, hrExpense.getHrExpenseId());

		SearchEngineUtil.deleteDocument(
			hrExpense.getCompanyId(), document.get(Field.UID));
	}

	protected Document doGetDocument(Object obj) throws Exception {
		HRExpense hrExpense = (HRExpense)obj;

		long hrExpenseAccountId = hrExpense.getHrExpenseAccountId();
		String hrExpenseAccountName = "";
		long hrExpenseTypeId = hrExpense.getHrExpenseTypeId();
		String hrExpenseTypeName = "";

		if (hrExpenseAccountId > 0) {
			HRExpenseAccount hrExpenseAccount =
				HRExpenseAccountLocalServiceUtil.getHRExpenseAccount(
					hrExpenseAccountId);

			hrExpenseAccountName = hrExpenseAccount.getName();
		}

		if (hrExpenseTypeId > 0) {
			HRExpenseType hrExpenseType =
				HRExpenseTypeLocalServiceUtil.getHRExpenseType(hrExpenseTypeId);

			hrExpenseTypeName = hrExpenseType.getName();
		}

		Document document = getBaseModelDocument(PORTLET_ID, hrExpense);

		document.addDate("expenseDate", hrExpense.getExpenseDate());
		document.addKeyword(
			"hrExpenseAccountId", hrExpense.getHrExpenseAccountId());
		document.addText("hrExpenseAccountName", hrExpenseAccountName);
		document.addKeyword("hrExpenseTypeId", hrExpense.getHrExpenseTypeId());
		document.addText("hrExpenseTypeName", hrExpenseTypeName);
		document.addKeyword("hrUserId", hrExpense.getHrUserId());

		return document;
	}

	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletURL portletURL) {

		String title = document.get(Field.ENTRY_CLASS_PK);

		String content = null;

		String hrExpenseId = document.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter(
			"jspPage", "/WEB-INF/jsp/hr_expense/views/expenses/view.jsp");
		portletURL.setParameter("hrExpenseId", hrExpenseId);

		return new Summary(title, content, portletURL);
	}

	protected void doReindex(Object obj) throws Exception {
		HRExpense hrExpense = (HRExpense)obj;

		Document document = getDocument(hrExpense);

		SearchEngineUtil.updateDocument(hrExpense.getCompanyId(), document);
	}

	protected void doReindex(String className, long classPK) throws Exception {
		HRExpense hrExpense = HRExpenseLocalServiceUtil.getHRExpense(classPK);

		doReindex(hrExpense);
	}

	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexHRExpenses(companyId);
	}

	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void reindexHRExpenses(long companyId) throws Exception {
		int count = HRExpenseLocalServiceUtil.getHRExpensesCount();

		int pages = count / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= pages; i++) {
			int start = (i * Indexer.DEFAULT_INTERVAL);
			int end = start + Indexer.DEFAULT_INTERVAL;

			reindexHRExpenses(companyId, start, end);
		}
	}

	protected void reindexHRExpenses(long companyId, int start, int end)
		throws Exception {

		List<HRExpense> hrExpenses = HRExpenseLocalServiceUtil.getHRExpenses(
			start, end);

		if (hrExpenses.isEmpty()) {
			return;
		}

		Collection<Document> documents = new ArrayList<Document>();

		for (HRExpense hrExpense : hrExpenses) {
			Document document = getDocument(hrExpense);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(companyId, documents);
	}

}
%>
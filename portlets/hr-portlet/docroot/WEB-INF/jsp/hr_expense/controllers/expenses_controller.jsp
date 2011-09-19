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

<%@ include file="/WEB-INF/jsp/hr_expense/controllers/init.jsp" %>
<%@ include file="/WEB-INF/jsp/hr_expense/controllers/expenses_indexer.jsp" %>

<%!
public class AlloyControllerImpl extends BaseAlloyControllerImpl {

	public void add() throws Exception {
		HRExpense hrExpense = new HRExpenseImpl();

		updateModel(hrExpense, request);

		hrExpense.setStatus(WorkflowConstants.STATUS_DRAFT);

		HRExpenseLocalServiceUtil.addHRExpense(hrExpense);

		reindex(_className, hrExpense);

		addSuccessMessage();

		String redirect = ParamUtil.getString(request, "redirect");

		redirectTo(redirect);
	}

	public void comment() throws Exception {
		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				MBMessage message = updateMessage(actionRequest);

				String randomNamespace = ParamUtil.getString(
					actionRequest, "randomNamespace");

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.put("messageId", message.getMessageId());
				jsonObject.put("randomNamespace", randomNamespace);

				writeJSON(actionRequest, actionResponse, jsonObject);

				return;
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteMessage(actionRequest);
			}
			else if (cmd.equals(Constants.SUBSCRIBE_TO_COMMENTS)) {
				subscribeToComments(actionRequest, true);
			}
			else if (cmd.equals(Constants.UNSUBSCRIBE_FROM_COMMENTS)) {
				subscribeToComments(actionRequest, false);
			}

			redirectTo(redirect);
		}
		catch (Exception e) {
			if (e instanceof MessageBodyException ||
				e instanceof NoSuchMessageException ||
				e instanceof PrincipalException ||
				e instanceof RequiredMessageException) {

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.putException(e);

				writeJSON(actionRequest, actionResponse, jsonObject);
			}
			else {
				throw e;
			}
		}
	}

	public void delete() throws Exception {
		long hrExpenseId = ParamUtil.getLong(request, "id");

		HRExpenseLocalServiceUtil.deleteHRExpense(hrExpenseId);

		addSuccessMessage();

		PortletURL redirectURL = liferayPortletResponse.createRenderURL();

		redirectTo(redirectURL);
	}

	public void docs() throws Exception {
		String uploadMethod = ParamUtil.getString(actionRequest, "uploadMethod");

		if (uploadMethod.equals(Constants.ADD_MULTIPLE)) {
			addMultipleDocuments(actionRequest, actionResponse, _className, _tempFolderName);
		}
		else if (uploadMethod.equals(Constants.ADD_TEMP)) {
			addTempDocument(actionRequest, _tempFolderName);
		}
		else if (uploadMethod.equals(Constants.DELETE_TEMP)) {
			deleteTempDocument(actionRequest, actionResponse, _tempFolderName);
		}
	}

	public void edit() throws Exception {
		String cmd = Constants.ADD;

		HRExpense hrExpense = new HRExpenseImpl();

		long hrExpenseId = ParamUtil.getLong(request, "id");

		List<String> existingFiles = new ArrayList<String>();

		if (hrExpenseId > 0) {
			hrExpense = HRExpenseLocalServiceUtil.getHRExpense(hrExpenseId);

			cmd = Constants.UPDATE;

			try {
				String[] fileNames = DLStoreUtil.getFileNames(hrExpense.getCompanyId(), CompanyConstants.SYSTEM, getDirectoryName(_className, hrExpenseId));

				for (String fileName : fileNames) {
					existingFiles.add(FileUtil.getShortFileName(fileName));
				}
			}
			catch (NoSuchDirectoryException nsde) {
			}
		}

		long hrExpenseAccountId = hrExpense.getHrExpenseAccountId();
		String hrExpenseAccountName = "";
		long hrExpenseTypeId = hrExpense.getHrExpenseTypeId();
		String hrExpenseTypeName = "";

		if (hrExpenseAccountId > 0) {
			HRExpenseAccount hrExpenseAccount = HRExpenseAccountLocalServiceUtil.getHRExpenseAccount(hrExpenseAccountId);

			hrExpenseAccountName = hrExpenseAccount.getName();
		}

		if (hrExpenseTypeId > 0) {
			HRExpenseType hrExpenseType = HRExpenseTypeLocalServiceUtil.getHRExpenseType(hrExpenseTypeId);

			hrExpenseTypeName = hrExpenseType.getName();
		}

		List<HRExpenseAccount> hrExpenseAccounts = HRExpenseAccountLocalServiceUtil.getHRExpenseAccounts(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		List<HRExpenseCurrency> hrExpenseCurrencies = HRExpenseCurrencyLocalServiceUtil.getHRExpenseCurrencies(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		List<HRExpenseCurrencyConversion> hrExpenseCurrencyConversions = HRExpenseCurrencyConversionLocalServiceUtil.getHRExpenseCurrencyConversions(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		List<HRExpenseType> hrExpenseTypes = HRExpenseTypeLocalServiceUtil.getHRExpenseTypes(QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		renderRequest.setAttribute("cmd", cmd);
		renderRequest.setAttribute("existingFiles", existingFiles);
		renderRequest.setAttribute("hrExpense", hrExpense);
		renderRequest.setAttribute("hrExpenseAccountName", hrExpenseAccountName);
		renderRequest.setAttribute("hrExpenseAccountsJSON", JSONFactoryUtil.looseSerialize(hrExpenseAccounts));
		renderRequest.setAttribute("hrExpenseCurrencies", hrExpenseCurrencies);
		renderRequest.setAttribute("hrExpenseCurrencyConversionsJSON", JSONFactoryUtil.looseSerialize(hrExpenseCurrencyConversions));
		renderRequest.setAttribute("hrExpenseTypeName", hrExpenseTypeName);
		renderRequest.setAttribute("hrExpenseTypesJSON", JSONFactoryUtil.looseSerialize(hrExpenseTypes));
	}

	public void index() throws Exception {
		registerIndex(_className, new HRExpenseIndexer(), request);
	}

	public void update() throws Exception {
		long hrExpenseId = ParamUtil.getLong(portletRequest, "hrExpenseId");

		HRExpense hrExpense = HRExpenseLocalServiceUtil.getHRExpense(hrExpenseId);

		updateModel(hrExpense, request);

		updateDocuments(request);

		HRExpenseLocalServiceUtil.updateHRExpense(hrExpense);

		reindex(_className, hrExpense);

		addSuccessMessage();

		String redirect = ParamUtil.getString(request, "redirect");

		redirectTo(redirect);
	}

	public void upload() throws Exception {
		long hrExpenseId = ParamUtil.getLong(request, "id");

		renderRequest.setAttribute("controller", "expenses");
		renderRequest.setAttribute("classPK", hrExpenseId);
		renderRequest.setAttribute("className", _className);
		renderRequest.setAttribute("uploadMethod", "Liferay.Service.HR.HRExpense.getTempDocumentNames");
		renderRequest.setAttribute("uploadTempFolderName", _tempFolderName);
	}

	public void view() throws Exception {
		long hrExpenseId = ParamUtil.getLong(request, "id");

		HRExpense hrExpense = new HRExpenseImpl();
		HRExpenseAccount hrExpenseAccount = new HRExpenseAccountImpl();
		HRExpenseType hrExpenseType = new HRExpenseTypeImpl();
		HRExpenseCurrency expenseHRExpenseCurrency = new HRExpenseCurrencyImpl();
		HRExpenseCurrency reimbursementHRExpenseCurrency = new HRExpenseCurrencyImpl();

		if (hrExpenseId > 0) {
			hrExpense = HRExpenseLocalServiceUtil.getHRExpense(hrExpenseId);
		}

		long hrExpenseAccountId = hrExpense.getHrExpenseAccountId();

		if (hrExpenseAccountId > 0) {
			hrExpenseAccount = HRExpenseAccountLocalServiceUtil.getHRExpenseAccount(hrExpenseAccountId);
		}

		long hrExpenseTypeId = hrExpense.getHrExpenseTypeId();

		if (hrExpenseTypeId > 0) {
			hrExpenseType = HRExpenseTypeLocalServiceUtil.getHRExpenseType(hrExpenseTypeId);
		}

		long expenseHRExpenseCurrencyId = hrExpense.getExpenseHRExpenseCurrencyId();

		if (expenseHRExpenseCurrencyId > 0) {
			expenseHRExpenseCurrency = HRExpenseCurrencyLocalServiceUtil.getHRExpenseCurrency(expenseHRExpenseCurrencyId);
		}

		long reimbursementHRExpenseCurrencyId = hrExpense.getReimbursementHRExpenseCurrencyId();

		if (reimbursementHRExpenseCurrencyId > 0) {
			reimbursementHRExpenseCurrency = HRExpenseCurrencyLocalServiceUtil.getHRExpenseCurrency(reimbursementHRExpenseCurrencyId);
		}

		User user = UserLocalServiceUtil.getUser(hrExpense.getHrUserId());

		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);

		renderRequest.setAttribute("hrExpense", hrExpense);
		renderRequest.setAttribute("hrExpenseAccount", hrExpenseAccount);
		renderRequest.setAttribute("hrExpenseType", hrExpenseType);
		renderRequest.setAttribute("hrUserFullName", user.getFullName());
		renderRequest.setAttribute("expenseDate", dateFormat.format(hrExpense.getExpenseDate()));
		renderRequest.setAttribute("expenseHRExpenseCurrency", expenseHRExpenseCurrency);
		renderRequest.setAttribute("reimbursementHRExpenseCurrency", reimbursementHRExpenseCurrency);
	}

	private String _className = HRExpense.class.getName();
	private String _tempFolderName = "com.liferay.portlet.hrexpense.action.docs";
}
%>
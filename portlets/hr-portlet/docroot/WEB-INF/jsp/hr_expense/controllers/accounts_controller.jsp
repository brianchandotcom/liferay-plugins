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

<%!
public class AlloyControllerImpl extends BaseAlloyControllerImpl {

	public void add() throws Exception {
		HRExpenseAccount hrExpenseAccount = new HRExpenseAccountImpl();

		updateModel(hrExpenseAccount, request);

		HRExpenseAccountLocalServiceUtil.addHRExpenseAccount(hrExpenseAccount);

		addSuccessMessage();

		String redirect = ParamUtil.getString(request, "redirect");

		redirectTo(redirect);
	}

	public void delete() throws Exception {
		long hrExpenseAccountId = ParamUtil.getLong(request, "id");

		HRExpenseAccountLocalServiceUtil.deleteHRExpenseAccount(hrExpenseAccountId);

		addSuccessMessage();

		PortletURL redirectURL = liferayPortletResponse.createRenderURL();

		redirectTo(redirectURL);
	}

	public void edit() throws Exception {
		String cmd = Constants.ADD;

		HRExpenseAccount hrExpenseAccount = new HRExpenseAccountImpl();

		long hrExpenseAccountId = ParamUtil.getLong(request, "id");

		if (hrExpenseAccountId > 0) {
			hrExpenseAccount = HRExpenseAccountLocalServiceUtil.getHRExpenseAccount(hrExpenseAccountId);

			cmd = Constants.UPDATE;
		}

		renderRequest.setAttribute("cmd", cmd);
		renderRequest.setAttribute("hrExpenseAccount", hrExpenseAccount);
	}

	public void update() throws Exception {
		long hrExpenseAccountId = ParamUtil.getLong(request, "hrExpenseAccountId");

		HRExpenseAccount hrExpenseAccount = HRExpenseAccountLocalServiceUtil.getHRExpenseAccount(hrExpenseAccountId);

		updateModel(hrExpenseAccount, request);

		HRExpenseAccountLocalServiceUtil.updateHRExpenseAccount(hrExpenseAccount);

		addSuccessMessage();

		String redirect = ParamUtil.getString(request, "redirect");

		redirectTo(redirect);
	}

	public void view() throws Exception {
		long hrExpenseAccountId = ParamUtil.getLong(request, "id");

		HRExpenseAccount hrExpenseAccount = new HRExpenseAccountImpl();

		if (hrExpenseAccountId > 0) {
			hrExpenseAccount = HRExpenseAccountLocalServiceUtil.getHRExpenseAccount(hrExpenseAccountId);
		}

		renderRequest.setAttribute("hrExpenseAccount", hrExpenseAccount);
	}

}
%>
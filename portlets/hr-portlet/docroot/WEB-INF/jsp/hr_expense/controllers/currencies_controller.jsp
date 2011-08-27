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
		HRExpenseCurrency hrExpenseCurrency = new HRExpenseCurrencyImpl();

		updateModel(hrExpenseCurrency, request);

		HRExpenseCurrencyLocalServiceUtil.addHRExpenseCurrency(hrExpenseCurrency);

		addSuccessMessage();

		String redirect = ParamUtil.getString(request, "redirect");

		redirectTo(redirect);
	}

	public void delete() throws Exception {
		long hrExpenseCurrencyId = ParamUtil.getLong(request, "id");

		HRExpenseCurrencyLocalServiceUtil.deleteHRExpenseCurrency(hrExpenseCurrencyId);

		addSuccessMessage();

		PortletURL redirectURL = liferayPortletResponse.createRenderURL();

		redirectTo(redirectURL);
	}

	public void edit() throws Exception {
		String cmd = Constants.ADD;

		HRExpenseCurrency hrExpenseCurrency = new HRExpenseCurrencyImpl();

		long hrExpenseCurrencyId = ParamUtil.getLong(request, "id");

		if (hrExpenseCurrencyId > 0) {
			hrExpenseCurrency = HRExpenseCurrencyLocalServiceUtil.getHRExpenseCurrency(hrExpenseCurrencyId);

			cmd = Constants.UPDATE;
		}

		renderRequest.setAttribute("cmd", cmd);
		renderRequest.setAttribute("hrExpenseCurrency", hrExpenseCurrency);
	}

	public void update() throws Exception {
		long hrExpenseCurrencyId = ParamUtil.getLong(request, "hrExpenseCurrencyId");

		HRExpenseCurrency hrExpenseCurrency = HRExpenseCurrencyLocalServiceUtil.getHRExpenseCurrency(hrExpenseCurrencyId);

		updateModel(hrExpenseCurrency, request);

		HRExpenseCurrencyLocalServiceUtil.updateHRExpenseCurrency(hrExpenseCurrency);

		addSuccessMessage();

		String redirect = ParamUtil.getString(request, "redirect");

		redirectTo(redirect);
	}

	public void view() throws Exception {
		long hrExpenseCurrencyId = ParamUtil.getLong(request, "id");

		HRExpenseCurrency hrExpenseCurrency = new HRExpenseCurrencyImpl();

		if (hrExpenseCurrencyId > 0) {
			hrExpenseCurrency = HRExpenseCurrencyLocalServiceUtil.getHRExpenseCurrency(hrExpenseCurrencyId);
		}

		renderRequest.setAttribute("hrExpenseCurrency", hrExpenseCurrency);
	}

}
%>
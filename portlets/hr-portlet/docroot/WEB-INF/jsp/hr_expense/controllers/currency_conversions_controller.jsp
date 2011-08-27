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
		HRExpenseCurrencyConversion hrExpenseCurrencyConversion = new HRExpenseCurrencyConversionImpl();

		updateModel(hrExpenseCurrencyConversion, request);

		HRExpenseCurrencyConversionLocalServiceUtil.addHRExpenseCurrencyConversion(hrExpenseCurrencyConversion);

		addSuccessMessage();

		String redirect = ParamUtil.getString(request, "redirect");

		redirectTo(redirect);
	}

	public void delete() throws Exception {
		long hrExpenseCurrencyConversionId = ParamUtil.getLong(request, "id");

		HRExpenseCurrencyConversionLocalServiceUtil.deleteHRExpenseCurrencyConversion(hrExpenseCurrencyConversionId);

		addSuccessMessage();

		PortletURL redirectURL = liferayPortletResponse.createRenderURL();

		redirectTo(redirectURL);
	}

	public void edit() throws Exception {
		String cmd = Constants.ADD;

		HRExpenseCurrencyConversion hrExpenseCurrencyConversion = new HRExpenseCurrencyConversionImpl();

		long hrExpenseCurrencyConversionId = ParamUtil.getLong(request, "id");

		if (hrExpenseCurrencyConversionId > 0) {
			hrExpenseCurrencyConversion = HRExpenseCurrencyConversionLocalServiceUtil.getHRExpenseCurrencyConversion(hrExpenseCurrencyConversionId);

			cmd = Constants.UPDATE;
		}

		List<HRExpenseCurrency> hrExpenseCurrencies = HRExpenseCurrencyLocalServiceUtil.getHRExpenseCurrencies(QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		renderRequest.setAttribute("cmd", cmd);
		renderRequest.setAttribute("hrExpenseCurrencies", hrExpenseCurrencies);
		renderRequest.setAttribute("hrExpenseCurrencyConversion", hrExpenseCurrencyConversion);
	}

	public void update() throws Exception {
		long hrExpenseCurrencyConversionId = ParamUtil.getLong(request, "hrExpenseCurrencyConversionId");

		HRExpenseCurrencyConversion hrExpenseCurrencyConversion = HRExpenseCurrencyConversionLocalServiceUtil.getHRExpenseCurrencyConversion(hrExpenseCurrencyConversionId);

		updateModel(hrExpenseCurrencyConversion, request);

		HRExpenseCurrencyConversionLocalServiceUtil.updateHRExpenseCurrencyConversion(hrExpenseCurrencyConversion);

		addSuccessMessage();

		String redirect = ParamUtil.getString(request, "redirect");

		redirectTo(redirect);
	}

	public void view() throws Exception {
		long hrExpenseCurrencyConversionId = ParamUtil.getLong(request, "id");

		HRExpenseCurrencyConversion hrExpenseCurrencyConversion = new HRExpenseCurrencyConversionImpl();
		HRExpenseCurrency fromHRExpenseCurrency = new HRExpenseCurrencyImpl();
		HRExpenseCurrency toHRExpenseCurrency = new HRExpenseCurrencyImpl();

		if (hrExpenseCurrencyConversionId > 0) {
			hrExpenseCurrencyConversion = HRExpenseCurrencyConversionLocalServiceUtil.getHRExpenseCurrencyConversion(hrExpenseCurrencyConversionId);
		}

		long fromHRExpenseCurrencyId = hrExpenseCurrencyConversion.getFromHRExpenseCurrencyId();

		if (fromHRExpenseCurrencyId > 0) {
			fromHRExpenseCurrency = HRExpenseCurrencyLocalServiceUtil.getHRExpenseCurrency(fromHRExpenseCurrencyId);
		}

		long toHRExpenseCurrencyId = hrExpenseCurrencyConversion.getToHRExpenseCurrencyId();

		if (toHRExpenseCurrencyId > 0) {
			toHRExpenseCurrency = HRExpenseCurrencyLocalServiceUtil.getHRExpenseCurrency(toHRExpenseCurrencyId);
		}

		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);

		renderRequest.setAttribute("hrExpenseCurrencyConversion", hrExpenseCurrencyConversion);
		renderRequest.setAttribute("conversionDate", dateFormat.format(hrExpenseCurrencyConversion.getConversionDate()));
		renderRequest.setAttribute("fromHRExpenseCurrency", fromHRExpenseCurrency);
		renderRequest.setAttribute("toHRExpenseCurrency", toHRExpenseCurrency);
	}

}
%>
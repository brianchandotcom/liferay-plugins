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
		HRExpenseType hrExpenseType = new HRExpenseTypeImpl();

		updateModel(hrExpenseType, request);

		HRExpenseTypeLocalServiceUtil.addHRExpenseType(hrExpenseType);

		addSuccessMessage();

		String redirect = ParamUtil.getString(request, "redirect");

		redirectTo(redirect);
	}

	public void delete() throws Exception {
		long hrExpenseTypeId = ParamUtil.getLong(request, "id");

		HRExpenseTypeLocalServiceUtil.deleteHRExpenseType(hrExpenseTypeId);

		addSuccessMessage();

		PortletURL redirectURL = liferayPortletResponse.createRenderURL();

		redirectTo(redirectURL);
	}

	public void edit() throws Exception {
		String cmd = Constants.ADD;

		HRExpenseType hrExpenseType = new HRExpenseTypeImpl();

		long hrExpenseTypeId = ParamUtil.getLong(request, "id");

		if (hrExpenseTypeId > 0) {
			hrExpenseType = HRExpenseTypeLocalServiceUtil.getHRExpenseType(hrExpenseTypeId);

			cmd = Constants.UPDATE;
		}

		renderRequest.setAttribute("cmd", cmd);
		renderRequest.setAttribute("hrExpenseType", hrExpenseType);
	}

	public void update() throws Exception {
		long hrExpenseTypeId = ParamUtil.getLong(request, "hrExpenseTypeId");

		HRExpenseType hrExpenseType = HRExpenseTypeLocalServiceUtil.getHRExpenseType(hrExpenseTypeId);

		updateModel(hrExpenseType, request);

		HRExpenseTypeLocalServiceUtil.updateHRExpenseType(hrExpenseType);

		addSuccessMessage();

		String redirect = ParamUtil.getString(request, "redirect");

		redirectTo(redirect);
	}

	public void view() throws Exception {
		long hrExpenseTypeId = ParamUtil.getLong(request, "id");

		HRExpenseType hrExpenseType = new HRExpenseTypeImpl();

		if (hrExpenseTypeId > 0) {
			hrExpenseType = HRExpenseTypeLocalServiceUtil.getHRExpenseType(hrExpenseTypeId);
		}

		renderRequest.setAttribute("hrExpenseType", hrExpenseType);
	}

}
%>
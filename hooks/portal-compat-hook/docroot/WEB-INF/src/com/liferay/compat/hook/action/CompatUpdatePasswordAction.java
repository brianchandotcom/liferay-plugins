/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.compat.hook.action;

import com.liferay.compat.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.servlet.NoRedirectServletResponse;
import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLFactoryUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jonathan Lee
 */
public class CompatUpdatePasswordAction extends BaseStrutsAction {

	@Override
	public String execute(
			StrutsAction originalStrutsAction, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		NoRedirectServletResponse noRedirectServletResponse =
			new NoRedirectServletResponse(response);

		String forward = originalStrutsAction.execute(
			request, noRedirectServletResponse);

		if (Validator.isNull(forward)) {
			String redirect = ParamUtil.getString(request, WebKeys.REFERER);

			if (Validator.isNull(redirect)) {
				PortletURL portletURL = PortletURLFactoryUtil.create(
					request, PortletKeys.LOGIN, themeDisplay.getPlid(),
					PortletRequest.RENDER_PHASE);

				redirect = portletURL.toString();
			}

			response.sendRedirect(redirect);
		}

		return forward;
	}

}
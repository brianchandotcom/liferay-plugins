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

package com.liferay.securitymanager.portlet;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.securitymanager.util.PortletKeys;
import com.liferay.securitymanager.util.PortletPropsKeys;
import com.liferay.util.bridges.mvc.MVCPortlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;

/**
 * @author Shinn Lok
 */
public class SecurityManagerPortlet extends MVCPortlet {

	public void updateConfiguration(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		boolean securityManagerEnabled = ParamUtil.getBoolean(
			actionRequest, "securityManagerEnabled");

		PortletPreferences preferences =
			PortletPreferencesLocalServiceUtil.getPreferences(
				themeDisplay.getCompanyId(), themeDisplay.getCompanyId(),
				PortletKeys.PREFS_OWNER_TYPE_COMPANY,
				PortletKeys.PREFS_PLID_SHARED, PortletKeys.SECURITY_MANAGER);

		preferences.setValue(
			PortletPropsKeys.SECURITY_MANAGER_ENABLED,
			String.valueOf(securityManagerEnabled));

		preferences.store();

		String redirect = PortalUtil.escapeRedirect(
			ParamUtil.getString(actionRequest, "redirect"));

		actionResponse.sendRedirect(redirect);
	}

}
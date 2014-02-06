/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.feedback.admin.portlet;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.PortletConfigurationLayoutUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ValidatorException;

/**
 * @author Lin Cui
 */
public class FeedbackAdminPortlet extends MVCPortlet {

	public static final String PREFERENCES_PREFIX = "preferences--";

	public void configFeedback(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		UnicodeProperties properties = PropertiesParamUtil.getProperties(
			actionRequest, PREFERENCES_PREFIX);

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		Layout layout = PortletConfigurationLayoutUtil.getLayout(themeDisplay);

		PortletPermissionUtil.check(
			themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroupId(),
			layout, portletResource, ActionKeys.CONFIGURATION);

		PortletPreferences portletPreferences = actionRequest.getPreferences();

		for (Map.Entry<String, String> entry : properties.entrySet()) {
			String name = entry.getKey();
			String value = entry.getValue();

			if (Long.parseLong(value) > 0) {
				portletPreferences.setValue(name, value);
			}
		}

		try {
			portletPreferences.store();
		}
		catch (ValidatorException ve) {
			SessionErrors.add(
				actionRequest, ValidatorException.class.getName(), ve);

			return;
		}
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		try {
			String resourceId = resourceRequest.getResourceID();

			if (resourceId.equals("groupCategory")) {
				serveGroupCategory(resourceRequest, resourceResponse);
			}
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	protected JSONArray getJsonArray(List<MBCategory> mbCategories) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (MBCategory mbCategory : mbCategories) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("value", mbCategory.getCategoryId());
			jsonObject.put("name", mbCategory.getName());

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	protected void serveGroupCategory(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long groupId = ParamUtil.getLong(resourceRequest, "groupId");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		List<MBCategory> mbCategories =
			MBCategoryLocalServiceUtil.getCategories(
				groupId, WorkflowConstants.STATUS_APPROVED);

		jsonObject.put("CATEGORY", getJsonArray(mbCategories));
		jsonObject.put("CATEGORY#key", mbCategories.hashCode());

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

}
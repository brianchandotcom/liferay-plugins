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

package com.liferay.larimporter.hook.events;

import com.liferay.larimporter.util.PortletPropsValues;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.io.InputStream;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ryan Park
 */
public class StartupAction extends SimpleAction {

	@Override
	public void run(String[] ids) throws ActionException {
		for (String id : ids) {
			try {
				doRun(GetterUtil.getLong(id));
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	protected void doRun(long companyId) throws Exception {
		int count = GroupLocalServiceUtil.searchCount(
			companyId, PortletPropsValues.SITE_NAME, StringPool.BLANK, null);

		if (count > 0) {
			return;
		}

		User user = UserLocalServiceUtil.getDefaultUser(companyId);

		Group group = GroupLocalServiceUtil.addGroup(
			user.getUserId(), null, 0, PortletPropsValues.SITE_NAME,
			StringPool.BLANK, GroupConstants.TYPE_SITE_OPEN, null, true, true,
			null);

		LayoutLocalServiceUtil.importLayouts(
			user.getUserId(), group.getGroupId(),
			PortletPropsValues.SITE_PRIVATE_LAYOUT, getParameterMap(),
			getInputStream(PortletPropsValues.LAR_PATH));
	}

	protected InputStream getInputStream(String path) throws Exception {
		return getClass().getResourceAsStream(path);
	}

	protected Map<String, String[]> getParameterMap() {
		Map<String, String[]> parameters = new HashMap();

		parameters.put(
			PortletDataHandlerKeys.CATEGORIES,
			new String[] {PortletPropsValues.LAR_IMPORT_CATEGORIES});
		parameters.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {
				PortletPropsValues.LAR_IMPORT_DELETE_MISSING_LAYOUTS});
		parameters.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[] {PortletPropsValues.LAR_IMPORT_DELETE_PORTLET_DATA});
		parameters.put(
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED,
			new String[] {
				PortletPropsValues.LAR_IMPORT_LAYOUT_SET_PROTOTYPE_LINK_ENABLED
			});
		parameters.put(
			PortletDataHandlerKeys.LAYOUT_SET_SETTINGS,
			new String[] {PortletPropsValues.LAR_IMPORT_LAYOUT_SET_SETTINGS});
		parameters.put(
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
			new String[] {PortletPropsValues.LAR_IMPORT_LAYOUTS_IMPORT_MODE});
		parameters.put(
			PortletDataHandlerKeys.LOGO,
			new String[] {PortletPropsValues.LAR_IMPORT_LOGO});
		parameters.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {PortletPropsValues.LAR_IMPORT_PERMISSIONS});
		parameters.put(
			PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS,
			new String[] {
				PortletPropsValues.LAR_IMPORT_PORTLET_ARCHIVED_SETUPS});
		parameters.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {PortletPropsValues.LAR_IMPORT_PORTLET_DATA});
		parameters.put(
			PortletDataHandlerKeys.PORTLET_SETUP,
			new String[] {PortletPropsValues.LAR_IMPORT_PORTLET_SETUP});
		parameters.put(
			PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
			new String[] {
				PortletPropsValues.LAR_IMPORT_PORTLET_USER_PREFERENCES});
		parameters.put(
			PortletDataHandlerKeys.PORTLETS_MERGE_MODE,
			new String[] {PortletPropsValues.LAR_IMPORT_PORTLETS_MERGE_MODE});
		parameters.put(
			PortletDataHandlerKeys.PUBLIC_LAYOUT_PERMISSIONS,
			new String[] {
				PortletPropsValues.LAR_IMPORT_PUBLIC_LAYOUT_PERMISSIONS});
		parameters.put(
			PortletDataHandlerKeys.PUBLISH_TO_REMOTE,
			new String[] {PortletPropsValues.LAR_IMPORT_PUBLISH_TO_REMOTE});
		parameters.put(
			PortletDataHandlerKeys.THEME,
			new String[] {PortletPropsValues.LAR_IMPORT_THEME});
		parameters.put(
			PortletDataHandlerKeys.THEME_REFERENCE,
			new String[] {PortletPropsValues.LAR_IMPORT_THEME_REFERENCE});
		parameters.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {PortletPropsValues.LAR_IMPORT_USER_ID_STRATEGY});
		parameters.put(
			PortletDataHandlerKeys.USER_PERMISSIONS,
			new String[] {PortletPropsValues.LAR_IMPORT_USER_PERMISSIONS});

		return parameters;
	}

	private static Log _log = LogFactoryUtil.getLog(StartupAction.class);

}
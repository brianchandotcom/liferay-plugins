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

package com.liferay.larimporter.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.util.portlet.PortletProps;

/**
 * @author Ryan Park
 */
public class PortletPropsValues {

	public static final String LAR_IMPORT_CATEGORIES = GetterUtil.getString(
		PortletProps.get(PortletPropsKeys.LAR_IMPORT_CATEGORIES));

	public static final String LAR_IMPORT_DELETE_MISSING_LAYOUTS =
		GetterUtil.getString(
			PortletProps.get(
				PortletPropsKeys.LAR_IMPORT_DELETE_MISSING_LAYOUTS));

	public static final String LAR_IMPORT_DELETE_PORTLET_DATA =
		GetterUtil.getString(
			PortletProps.get(PortletPropsKeys.LAR_IMPORT_DELETE_PORTLET_DATA));

	public static final String LAR_IMPORT_LAYOUT_SET_PROTOTYPE_LINK_ENABLED =
		GetterUtil.getString(
			PortletProps.get(
				PortletPropsKeys.LAR_IMPORT_LAYOUT_SET_PROTOTYPE_LINK_ENABLED));

	public static final String LAR_IMPORT_LAYOUT_SET_SETTINGS =
		GetterUtil.getString(
			PortletProps.get(PortletPropsKeys.LAR_IMPORT_LAYOUT_SET_SETTINGS));

	public static final String LAR_IMPORT_LAYOUTS_IMPORT_MODE =
		GetterUtil.getString(
			PortletProps.get(PortletPropsKeys.LAR_IMPORT_LAYOUTS_IMPORT_MODE));

	public static final String LAR_IMPORT_LOGO = GetterUtil.getString(
		PortletProps.get(PortletPropsKeys.LAR_IMPORT_LOGO));

	public static final String LAR_IMPORT_PERMISSIONS = GetterUtil.getString(
		PortletProps.get(PortletPropsKeys.LAR_IMPORT_PERMISSIONS));

	public static final String LAR_IMPORT_PORTLET_ARCHIVED_SETUPS =
		GetterUtil.getString(
			PortletProps.get(
				PortletPropsKeys.LAR_IMPORT_PORTLET_ARCHIVED_SETUPS));

	public static final String LAR_IMPORT_PORTLET_DATA = GetterUtil.getString(
		PortletProps.get(PortletPropsKeys.LAR_IMPORT_PORTLET_DATA));

	public static final String LAR_IMPORT_PORTLET_SETUP = GetterUtil.getString(
		PortletProps.get(PortletPropsKeys.LAR_IMPORT_PORTLET_SETUP));

	public static final String LAR_IMPORT_PORTLET_USER_PREFERENCES =
		GetterUtil.getString(
			PortletProps.get(
				PortletPropsKeys.LAR_IMPORT_PORTLET_USER_PREFERENCES));

	public static final String LAR_IMPORT_PORTLETS_MERGE_MODE =
		GetterUtil.getString(
			PortletProps.get(PortletPropsKeys.LAR_IMPORT_PORTLETS_MERGE_MODE));

	public static final String LAR_IMPORT_PUBLIC_LAYOUT_PERMISSIONS =
		GetterUtil.getString(
			PortletProps.get(
				PortletPropsKeys.LAR_IMPORT_PUBLIC_LAYOUT_PERMISSIONS));

	public static final String LAR_IMPORT_PUBLISH_TO_REMOTE =
		GetterUtil.getString(
			PortletProps.get(PortletPropsKeys.LAR_IMPORT_PUBLISH_TO_REMOTE));

	public static final String LAR_IMPORT_THEME = GetterUtil.getString(
		PortletProps.get(PortletPropsKeys.LAR_IMPORT_THEME));

	public static final String LAR_IMPORT_THEME_REFERENCE =
		GetterUtil.getString(
			PortletProps.get(PortletPropsKeys.LAR_IMPORT_THEME_REFERENCE));

	public static final String LAR_IMPORT_USER_ID_STRATEGY =
		GetterUtil.getString(
			PortletProps.get(PortletPropsKeys.LAR_IMPORT_USER_ID_STRATEGY));

	public static final String LAR_IMPORT_USER_PERMISSIONS =
		GetterUtil.getString(
			PortletProps.get(PortletPropsKeys.LAR_IMPORT_USER_PERMISSIONS));

	public static final String LAR_PATH = GetterUtil.getString(
		PortletProps.get(PortletPropsKeys.LAR_PATH));

	public static final String SITE_NAME = GetterUtil.getString(
		PortletProps.get(PortletPropsKeys.SITE_NAME));

	public static final boolean SITE_PRIVATE_LAYOUT = GetterUtil.getBoolean(
		PortletProps.get(PortletPropsKeys.SITE_PRIVATE_LAYOUT));

}
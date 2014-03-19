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

package com.liferay.mentions.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

/**
 * @author Sergio González
 */
public class MentionsUtil {

	public static Map<String, Integer> getAllSocialRelationTypes()
		throws ClassNotFoundException, IllegalAccessException {

		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		Class<?> clazz = classLoader.loadClass(
			"com.liferay.portlet.social.model.SocialRelationConstants");

		Field[] fields = clazz.getDeclaredFields();

		Map<String, Integer> socialRelationTypes =
			new HashMap<String, Integer>();

		for (Field field : fields) {
			int modifier = field.getModifiers();

			if (Modifier.isStatic(modifier) && Modifier.isPublic(modifier) &&
				Modifier.isFinal(modifier)) {

				socialRelationTypes.put(
					field.getName(), GetterUtil.getInteger(field.get(null)));
			}
		}

		return socialRelationTypes;
	}

	public static String[] getSocialRelationTypes(
			PortletPreferences portletPreferences)
		throws ClassNotFoundException, IllegalAccessException {

		String socialRelationTypes = portletPreferences.getValue(
			"socialRelationTypes", null);

		return _getSocialRelationTypes(socialRelationTypes);
	}

	public static String[] getSocialRelationTypes(
			PortletPreferences portletPreferences,
			PortletRequest portletRequest)
		throws ClassNotFoundException, IllegalAccessException {

		String socialRelationTypes = PrefsParamUtil.getString(
			portletPreferences, portletRequest, "socialRelationTypes", null);

		return _getSocialRelationTypes(socialRelationTypes);
	}

	public static boolean isMentionsEnabled(long siteGroupId)
		throws PortalException, SystemException {

		Group group = GroupLocalServiceUtil.getGroup(siteGroupId);

		PortletPreferences companyPortletPreferences =
			PrefsPropsUtil.getPreferences(group.getCompanyId(), true);

		boolean companyMentionsEnabled = GetterUtil.getBoolean(
			companyPortletPreferences.getValue("mentionsEnabled", null), true);

		if (!companyMentionsEnabled) {
			return false;
		}

		return GetterUtil.getBoolean(
			group.getLiveParentTypeSettingsProperty("mentionsEnabled"), true);
	}

	private static String[] _getSocialRelationTypes(String socialRelationTypes)
		throws ClassNotFoundException, IllegalAccessException {

		String[] socialRelationTypesArray = null;

		if (socialRelationTypes == null) {
			Map<String, Integer> allSocialRelationTypes =
				getAllSocialRelationTypes();

			Set<String> allSocialRelationTypesSet =
				allSocialRelationTypes.keySet();

			socialRelationTypesArray = allSocialRelationTypesSet.toArray(
				new String[0]);
		}
		else {
			socialRelationTypesArray = StringUtil.split(socialRelationTypes);
		}

		Arrays.sort(socialRelationTypesArray);

		return socialRelationTypesArray;
	}

}
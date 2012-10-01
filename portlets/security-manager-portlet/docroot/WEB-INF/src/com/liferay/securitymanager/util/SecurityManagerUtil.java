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

package com.liferay.securitymanager.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;

import java.lang.Class;
import java.lang.ClassLoader;
import java.lang.Exception;
import java.lang.Object;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Shinn Lok
 */
public class SecurityManagerUtil {

	public static void activatePACL() throws Exception {
		Map<ClassLoader, Object> paclPolicies = _paclPolicies;

		Class<?> clazz = getPACLPolicyManagerClass();

		for (Map.Entry<ClassLoader, Object> paclPolicy :
				paclPolicies.entrySet()) {

			Method method = clazz.getMethod(
				"register", ClassLoader.class, getPACLPolicyClass());

			method.invoke(clazz, paclPolicy.getKey(), paclPolicy.getValue());
		}
	}

	public static void deactivatePACL() throws Exception {
		Map<ClassLoader, Object> paclPolicies = getPACLPolicies();

		Class<?> clazz = getPACLPolicyManagerClass();

		for (ClassLoader classLoader : paclPolicies.keySet()) {
			Method method = clazz.getMethod("unregister", ClassLoader.class);

			method.invoke(clazz, classLoader);

			_paclPolicies.putAll(paclPolicies);
		}
	}

	public static Map<ClassLoader, Object> getPACLPolicies() throws Exception {
		Class<?> clazz = getPACLPolicyManagerClass();

		Field field = clazz.getDeclaredField("_paclPolicies");

		field.setAccessible(true);

		return new HashMap<ClassLoader, Object>(
			(Map<ClassLoader, Object>)field.get(clazz));
	}

	public static Class<?> getPACLPolicyClass() throws Exception {
		if (_paclPolicyClazz != null) {
			return _paclPolicyClazz;
		}

		ClassLoader portalClassLoader = PortalClassLoaderUtil.getClassLoader();

		Class<?> clazz = portalClassLoader.loadClass(
			"com.liferay.portal.security.pacl.PACLPolicy");

		_paclPolicyClazz = clazz;

		return clazz;
	}

	public static Class<?> getPACLPolicyManagerClass() throws Exception {
		if (_paclPolicyManagerClazz != null) {
			return _paclPolicyManagerClazz;
		}

		ClassLoader portalClassLoader = PortalClassLoaderUtil.getClassLoader();

		Class<?> clazz = portalClassLoader.loadClass(
			"com.liferay.portal.security.pacl.PACLPolicyManager");

		_paclPolicyManagerClazz = clazz;

		return clazz;
	}

	public static boolean isEnabled(long companyId) throws SystemException {
		PortletPreferences preferences =
			PortletPreferencesLocalServiceUtil.getPreferences(
				companyId, companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY,
				PortletKeys.PREFS_PLID_SHARED, PortletKeys.SECURITY_MANAGER);

		return GetterUtil.getBoolean(
			preferences.getValue(
				PortletPropsKeys.SECURITY_MANAGER_ENABLED,
				String.valueOf(Boolean.TRUE)));
	}

	public static boolean isPACLActive() throws Exception {
		Class<?> clazz = getPACLPolicyManagerClass();

		Method method = clazz.getMethod("isActive");

		return (Boolean)method.invoke(clazz);
	}

	private static Map<ClassLoader, Object> _paclPolicies =
		new HashMap<ClassLoader, Object>();

	private static Class<?> _paclPolicyClazz;
	private static Class<?> _paclPolicyManagerClazz;

}
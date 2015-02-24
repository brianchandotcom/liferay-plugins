/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.pushnotifications.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Bruno Farache
 * @generated
 */
@ProviderType
public class PushNotificationsDeviceFinderUtil {
	public static java.util.List<com.liferay.pushnotifications.model.PushNotificationsDevice> findByU_P(
		long[] userIds, java.lang.String platform, int start, int end) {
		return getFinder().findByU_P(userIds, platform, start, end);
	}

	public static PushNotificationsDeviceFinder getFinder() {
		if (_finder == null) {
			_finder = (PushNotificationsDeviceFinder)PortletBeanLocatorUtil.locate(com.liferay.pushnotifications.service.ClpSerializer.getServletContextName(),
					PushNotificationsDeviceFinder.class.getName());

			ReferenceRegistry.registerReference(PushNotificationsDeviceFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(PushNotificationsDeviceFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(PushNotificationsDeviceFinderUtil.class,
			"_finder");
	}

	private static PushNotificationsDeviceFinder _finder;
}
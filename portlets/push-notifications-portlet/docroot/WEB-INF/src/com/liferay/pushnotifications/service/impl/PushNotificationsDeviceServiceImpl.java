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

package com.liferay.pushnotifications.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.pushnotifications.NoSuchDeviceException;
import com.liferay.pushnotifications.model.PushNotificationsDevice;
import com.liferay.pushnotifications.service.base.PushNotificationsDeviceServiceBaseImpl;

/**
 * @author Silvio Santos
 * @author Bruno Farache
 */
public class PushNotificationsDeviceServiceImpl
	extends PushNotificationsDeviceServiceBaseImpl {

	@Override
	public PushNotificationsDevice addPushNotificationsDevice(
			String token, String platform)
		throws PortalException, SystemException {

		return pushNotificationsDeviceLocalService.addPushNotificationsDevice(
			getUserId(), token, platform);
	}

	@Override
	public PushNotificationsDevice deletePushNotificationsDevice(String token)
		throws PortalException, SystemException {

		PushNotificationsDevice pushNotificationsDevice = null;

		try {
			PushNotificationsDevice existingPushNotificationsDevice =
				pushNotificationsDeviceLocalService.getPushNotificationsDevices(
					token);

			if (getUserId() == existingPushNotificationsDevice.getUserId()) {
				pushNotificationsDevice =
					pushNotificationsDeviceLocalService.
						deletePushNotificationsDevice(token);
			}
		}
		catch (NoSuchDeviceException nsde) {
			if (_log.isInfoEnabled()) {
				_log.info("Device " + token + " does not exist");
			}
		}

		return pushNotificationsDevice;
	}

	private static Log _log = LogFactoryUtil.getLog(
		PushNotificationsDeviceServiceImpl.class);

}
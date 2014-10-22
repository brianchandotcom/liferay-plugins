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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.model.User;
import com.liferay.pushnotifications.model.PushNotificationsDevice;
import com.liferay.pushnotifications.model.PushNotificationsEntry;
import com.liferay.pushnotifications.sender.PushNotificationsSender;
import com.liferay.pushnotifications.service.base.PushNotificationsEntryLocalServiceBaseImpl;
import com.liferay.pushnotifications.util.PushNotificationsConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Bruno Farache
 */
@ProviderType
public class PushNotificationsEntryLocalServiceImpl
	extends PushNotificationsEntryLocalServiceBaseImpl {

	@Override
	public PushNotificationsEntry addPushNotificationsEntry(
		long userId, long parentEntryId, JSONObject payload) {

		long pushNotificationsEntryId = counterLocalService.increment();

		PushNotificationsEntry pushNotificationsEntry =
			pushNotificationsEntryPersistence.create(pushNotificationsEntryId);

		pushNotificationsEntry.setUserId(userId);
		pushNotificationsEntry.setCreateDate(new Date());
		pushNotificationsEntry.setParentEntryId(parentEntryId);

		pushNotificationsEntry.setPayload(payload.toString());

		pushNotificationsEntryPersistence.update(pushNotificationsEntry);

		return pushNotificationsEntry;
	}

	@Override
	public void sendPushNotification(JSONObject jsonObject, int start, int end)
		throws PortalException {

		sendPushNotification(0, jsonObject, start, end);
	}

	@Override
	public void sendPushNotification(
			long toUserId, JSONObject jsonObject, int start, int end)
		throws PortalException {

		long fromUserId = addFromUserDetails(jsonObject);
		JSONObject payload = jsonObject.getJSONObject(
			PushNotificationsConstants.PAYLOAD);
		long parentEntryId = jsonObject.getLong(
			PushNotificationsConstants.PARENT_ENTRY_ID,
			PushNotificationsConstants.DEFAULT_PARENT_ENTRY_ID);

		addPushNotificationsEntry(fromUserId, parentEntryId, payload);

		for (Map.Entry<String, PushNotificationsSender> entry :
				_pushNotificationsSenders.entrySet()) {

			List<String> tokens = new ArrayList<String>();

			List<PushNotificationsDevice> pushNotificationsDevices =
				pushNotificationsDeviceLocalService.getPushNotificationsDevices(
					toUserId, entry.getKey(), start, end);

			for (PushNotificationsDevice pushNotificationsDevice :
					pushNotificationsDevices) {

				tokens.add(pushNotificationsDevice.getToken());
			}

			if (tokens.isEmpty()) {
				continue;
			}

			PushNotificationsSender pushNotificationsSender = entry.getValue();

			try {
				pushNotificationsSender.send(tokens, jsonObject);
			}
			catch (PortalException pe) {
				throw pe;
			}
			catch (Exception e) {
				throw new PortalException(e);
			}
		}
	}

	protected long addFromUserDetails(JSONObject jsonObject)
		throws PortalException {

		JSONObject fromUserJSONObject = jsonObject.getJSONObject(
			PushNotificationsConstants.FROM_USER);

		long fromUserId = fromUserJSONObject.getLong(
			PushNotificationsConstants.USER_ID);

		User user = userLocalService.getUser(fromUserId);

		fromUserJSONObject.put(
			PushNotificationsConstants.FULL_NAME, user.getFullName());
		fromUserJSONObject.put(
			PushNotificationsConstants.PORTRAIT_ID, user.getPortraitId());
		fromUserJSONObject.put(PushNotificationsConstants.UUID, user.getUuid());

		return fromUserId;
	}

	@BeanReference(name = "pushNotificationsSenders")
	private Map<String, PushNotificationsSender> _pushNotificationsSenders;

}
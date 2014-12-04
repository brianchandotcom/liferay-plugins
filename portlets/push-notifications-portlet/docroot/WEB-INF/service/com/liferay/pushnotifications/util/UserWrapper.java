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

package com.liferay.pushnotifications.util;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.model.User;

/**
 * @author Bruno Farache
 */
public class UserWrapper {

	public UserWrapper(User user) {
		_fullName = user.getFullName();
		_portraitId = user.getPortraitId();
		_uuid = user.getUuid();
	}

	public String getFullName() {
		return _fullName;
	}

	public long getPortraitId() {
		return _portraitId;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setFullName(String fullName) {
		_fullName = fullName;
	}

	public void setPortraitId(long portraitId) {
		_portraitId = portraitId;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public JSONObject toJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(PushNotificationsConstants.KEY_FULL_NAME, _fullName);
		jsonObject.put(PushNotificationsConstants.KEY_PORTRAIT_ID, _portraitId);
		jsonObject.put(PushNotificationsConstants.KEY_UUID, _uuid);

		return jsonObject;
	}

	private String _fullName;
	private long _portraitId;
	private String _uuid;

}
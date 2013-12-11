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

package com.liferay.chat.video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Philippe Proulx
 */
public class WebRTCManager {

	public List<Long> getAvailableWebRTCClientIds() {
		ArrayList<Long> availableUserIds = new ArrayList<Long>();
		synchronized (_clients) {
			for (Long userId : _clients.keySet()) {
				if (isWebRTCClientAvailable(userId)) {
					availableUserIds.add(userId);
				}
			}
		}

		return availableUserIds;
	}

	public WebRTCClient getWebRTCClient(long userId) {
		synchronized (_clients) {
			return doGetWebRTCClient(userId);
		}
	}

	public boolean isWebRTCClientAvailable(long userId) {
		if (!_clients.containsKey(userId)) {
			return false;
		}

		return _clients.get(userId).isAvailable();
	}

	public void removeWebRTCClient(long userId) {
	}

	protected void addWebRTCClient(long userId) {
		if (doGetWebRTCClient(userId) == null) {
			_clients.put(userId, new WebRTCClient(userId));
		}
	}

	protected WebRTCClient doGetWebRTCClient(long userId) {
		if (_clients.containsKey(userId)) {
			return _clients.get(userId);
		} else {
			return null;
		}
	}

	private final HashMap<Long, WebRTCClient> _clients = new HashMap<Long, WebRTCClient>();

}
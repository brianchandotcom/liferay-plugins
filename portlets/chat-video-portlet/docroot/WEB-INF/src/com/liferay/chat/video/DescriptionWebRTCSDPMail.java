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

package com.liferay.chat.video;

import com.liferay.portal.kernel.json.JSONObject;

/**
 * Represents an SDP description WebRTC mail, used to provide a destination
 * WebRTC client an SDP description (either a call or an answer) from a source
 * WebRTC client.
 *
 * @author Philippe Proulx
 */
public class DescriptionWebRTCSDPMail extends WebRTCMail {

	public DescriptionWebRTCSDPMail(
		DescriptionWebRTCSDPMail descriptionWebRTCSDPMail) {

		super(descriptionWebRTCSDPMail);
	}

	public DescriptionWebRTCSDPMail(
		long sourceUserId, JSONObject messageJSONObject) {

		super(sourceUserId, messageJSONObject);
	}

	@Override
	public String getMessageType() {
		return _MESSAGE_TYPE;
	}

	private static final String _MESSAGE_TYPE = "sdp";

}
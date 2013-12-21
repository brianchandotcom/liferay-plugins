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
import java.util.List;

/**
 * @author Philippe Proulx
 */
public class WebRTCMailbox {

	public List<WebRTCMail> popAllMails() {
		List<WebRTCMail> allMails = new ArrayList<WebRTCMail>(_mails);

		_mails.clear();

		return allMails;
	}

	public void pushMail(WebRTCMail mail) {
		_mails.add(mail);
	}

	private List<WebRTCMail> _mails = new ArrayList<WebRTCMail>();

}
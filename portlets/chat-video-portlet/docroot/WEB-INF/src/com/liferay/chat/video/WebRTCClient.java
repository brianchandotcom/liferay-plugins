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
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Philippe Proulx
 */
public class WebRTCClient {

	public WebRTCClient(long userId) {
		_userId = userId;

		updatePresenceTime();
	}

	public void addWebRTCConnection(
		WebRTCClient webRTCClient, WebRTCConnection webRTCConnection) {

		_webRTCConnections.put(webRTCClient, webRTCConnection);
	}

	public long getPresenceTime() {
		return _presenceTime;
	}

	public long getUserId() {
		return _userId;
	}

	public Set<WebRTCClient> getWebRTCClients() {
		return _webRTCConnections.keySet();
	}

	public WebRTCConnection getWebRTCConnection(WebRTCClient webRTCClient) {
		return _webRTCConnections.get(webRTCClient);
	}

	public boolean isAvailable() {
		return _available;
	}

	public void removeBilateralWebRTCConnection(WebRTCClient webRTCClient) {
		webRTCClient.removeUnilateralWebRTCConnection(this);

		removeUnilateralWebRTCConnection(webRTCClient);
	}

	public void removeBilateralWebRTCConnections() {
		for (WebRTCClient webRTCClient : _webRTCConnections.keySet()) {
			webRTCClient.removeUnilateralWebRTCConnection(this);
		}

		_webRTCConnections.clear();
	}

	public void reset() {
		setAvailable(false);

		removeBilateralWebRTCConnections();
	}

	public void setAvailable(boolean available) {
		_available = available;
	}

	public void updatePresenceTime() {
		_presenceTime = System.currentTimeMillis();
	}

	protected void removeUnilateralWebRTCConnection(WebRTCClient webRTCClient) {
		_webRTCConnections.remove(webRTCClient);
	}

    public static class Mailbox {

        private List<Mailbox.Mail> _mails = new ArrayList<Mailbox.Mail>();

        public void pushMail(Mailbox.Mail mail) {
            _mails.add(mail);
        }

        public List<Mailbox.Mail> popAllMails() {
            List<Mailbox.Mail> allMails = new ArrayList<Mailbox.Mail>(_mails);
            _mails.clear();

            return allMails;
        }

        public static abstract class Mail {

            private long _fromUserId;
            private String _jsonMessage;

            public Mail(Mailbox.Mail mail) {
                _fromUserId = mail._fromUserId;
                _jsonMessage = mail._jsonMessage;
            }

            public Mail(long fromUserId, String jsonMessage) {
                _fromUserId = fromUserId;
                _jsonMessage = jsonMessage;
            }

            public long getFromUserId() {
                return _fromUserId;
            }

            public String getJsonMessage() {
                return _jsonMessage;
            }

            public abstract String getMsgType();

        }

    }

	private boolean _available;
	private long _presenceTime;
	private long _userId;
	private Map<WebRTCClient, WebRTCConnection> _webRTCConnections =
		new ConcurrentHashMap<WebRTCClient, WebRTCConnection>();

}
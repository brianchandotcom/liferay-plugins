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

package com.liferay.chat.video.poller;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.poller.BasePollerProcessor;
import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.poller.PollerResponse;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.ContactConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.chat.video.WebRtcClient;
import com.liferay.chat.video.WebRtcManager;
import com.liferay.chat.video.WebRtcClient.Mailbox;

import java.util.Collections;
import java.util.List;

/**
 * @author Philippe Proulx <philippe.proulx@savoirfairelinux.com>
 */
public class ChatVideoPollerProcessor extends BasePollerProcessor {
	private final WebRtcManager webRtcManager = new WebRtcManager();

    protected String getWebRtcMessageType(PollerRequest req) {
        return this.getString(req, "webrtcMsgType");
    }

    protected long getWebRtcDstUserId(PollerRequest req) {
        if (req.getParameterMap().containsKey("webrtcDstUserId")) {
            return this.getLong(req, "webrtcDstUserId");
        } else {
            return -1;
        }
    }

    protected void processWebRtcMessage(PollerRequest req) {
        long srcUserId = req.getUserId();
        String webRtcMsgType = this.getWebRtcMessageType(req);
        long dstUserId = this.getWebRtcDstUserId(req);

        // process message
        if (webRtcMsgType.equals("setAvailability")) {
            boolean isAvailable = getBoolean(req, "webrtcIsAvailable");
            this.webRtcManager.processMsgSetAvailability(srcUserId, isAvailable);
        } else if (webRtcMsgType.equals("call")) {
            this.webRtcManager.processMsgCall(srcUserId, dstUserId);
        } else if (webRtcMsgType.equals("answer")) {
            boolean acceptAnswer = getBoolean(req, "webrtcAccept");
            this.webRtcManager.processMsgAnswer(srcUserId, dstUserId, acceptAnswer);
        } else if (webRtcMsgType.equals("sdp")) {
            String jsonSdp = getString(req, "webrtcSdp");
            this.webRtcManager.processMsgShareSdp(srcUserId, dstUserId, jsonSdp);
        } else if (webRtcMsgType.equals("ice")) {
            String jsonIceCandidate = getString(req, "webrtcIce");
            this.webRtcManager.processMsgShareIceCandidate(srcUserId, dstUserId, jsonIceCandidate);
        } else if (webRtcMsgType.equals("hangUp")) {
            this.webRtcManager.processMsgHangUp(srcUserId, dstUserId);
        } else if (webRtcMsgType.equals("reset")) {
            this.webRtcManager.processMsgReset(srcUserId);
        } else if (webRtcMsgType.equals("updatePresence")) {
            this.webRtcManager.updatePresence(srcUserId);
        } else {
            // TODO: error
        }
    }

    protected void getWebRtcData(PollerRequest req, PollerResponse resp) throws Exception {
        // get WebRtc client
        WebRtcClient client = this.webRtcManager.getClient(req.getUserId());

        // initialize response
        JSONObject webrtcObj = JSONFactoryUtil.createJSONObject();
        JSONArray mailsObj = JSONFactoryUtil.createJSONArray();

        if (client != null) {
            List<WebRtcClient.Mailbox.Mail> clientMails = client.getOugoingMailbox().popAll();
            for (Mailbox.Mail mail : clientMails) {
                String type = mail.getMsgType();
                String jsonMessage = mail.getJsonMessage();
                JSONObject mailObj = JSONFactoryUtil.createJSONObject();
                mailObj.put("type", type);
                mailObj.put("msg", jsonMessage);
                mailObj.put("fromUserId", mail.getFromUserId());
                mailsObj.put(mailObj);
            }
        }

        // mails
        webrtcObj.put("mails", mailsObj);
        resp.setParameter("webrtc", webrtcObj);
    }

	@Override
	protected void doReceive(PollerRequest pollerRequest, PollerResponse pollerResponse) throws Exception {
        JSONObject yallObj = JSONFactoryUtil.createJSONObject();

        this.getWebRtcData(pollerRequest, pollerResponse);
	}

	@Override
	protected void doSend(PollerRequest pollerRequest) throws Exception {
        if (this.getBoolean(pollerRequest, "webrtc")) {
            this.processWebRtcMessage(pollerRequest);
        }
	}
}

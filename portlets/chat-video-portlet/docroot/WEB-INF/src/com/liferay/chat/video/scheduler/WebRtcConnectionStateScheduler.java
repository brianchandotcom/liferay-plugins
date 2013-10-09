package com.liferay.chat.video.scheduler;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.chat.video.WebRtcManager;

/**
 * WebRTC connection state scheduler
 *
 * This class is instantiated by Liferay and checks at a regular
 * interval all the client presences of all the WebRTC managers.
 *
 * @author  Philippe Proulx <philippe.proulx@savoirfairelinux.com>
 */
public class WebRtcConnectionStateScheduler extends BaseMessageListener {
    @Override
    protected void doReceive(Message message) throws Exception {
        WebRtcManager.checkAllManagersConnectionsStates();
    }
}

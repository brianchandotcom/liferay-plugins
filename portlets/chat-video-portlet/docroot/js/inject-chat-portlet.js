/**
 * inject.js - Injects chat-video-portlet code into chat-portlet
 *
 * Copyright (c) 2013 Savoir-faire Linux
 *     Philippe Proulx <philippe.proulx@savoirfairelinux.com>.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms are permitted
 * provided that the above copyright notice and this paragraph are
 * duplicated in all such forms and that any documentation,
 * advertising materials, and other materials related to such
 * distribution and use acknowledge that the software was developed
 * by Savoir-faire Linux.  The name of Savoir-faire Linux
 * may not be used to endorse or promote products derived
 * from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
;(function(A) {
	Liferay.namespace('Chat');

	Liferay.Chat.Video = {
		init: function(chatManager) {
			var instance = this;

			// save chat manager reference
			instance._chatManager = chatManager;

			// read our portlet ID (injected in view.jsp)
			instance._portletId = A.one('#chat-video-portlet-id').val();

			// video buddies list
			instance._buddies = [];

			// increased polling rate
			instance._increasedPollingRate = false;
			instance._increasedPollingRateDelayMs = 500;
			instance._increasedPollingCountMs = 0;

			// initialize master manager
			var ringingElemDom = A.one('#webrtc-ringtone').getDOM();
			var outRingingElemDom = A.one('#webrtc-out-ringtone').getDOM();
			var tryDisableAudio = function(domElem) {
				/* We do this here because when modifying the playback state
				 * of an HTMLMediaElement, it can throw some exceptions when
				 * not ready for some reason. Since it's not loaded anyway,
				 * we don't care about the raised exception since it's not
				 * playing and we want to disable it anyway.
				 */
				try {
					domElem.pause();
					domElem.currentTime = 0;
				} catch(e) {
					// probably not ready yet: not playing anyway
				}
			};

			// add poller listener for video chat
			Liferay.Poller.addListener(instance._portletId, instance._onPollerUpdate, instance);
			Liferay.bind(
				'sessionExpired',
				function(event) {
					Liferay.Poller.removeListener(instance._portletId);
					Liferay.Poller.cancelOverriddenDelay();
				}
			);

			// send reset message manually (testing)
			instance._send({
				'msgType': 'reset'
			});
			instance._send({
				'msgType': 'setAvailability',
				'isAvailable': true
			});
		},

		_onPollerUpdate: function(response, chunk) {
			console.log('poller');
			console.log(response);
		},

		_send: function(payload) {
			var instance = this;

			/* The provided payload is not specific to this chat portlet.
			 * We need to craft it a little more. To do so, we add
			 * the true 'webrtc' member so that the server knows it's a
			 * WebRTC message. We also prepend the 'webrtc' prefix to
			 * each member to avoid name collisions.
			 *
			 * Example: 'dstUserId' -> webrtcDstUserId'
			 */
			for (var paramName in payload) {
				var newParamName = 'webrtc' + paramName.charAt(0).toUpperCase() + paramName.slice(1);
				payload[newParamName] = payload[paramName];
				delete payload[paramName];
			}

			// send using poller
			Liferay.Poller.submitRequest(instance._portletId, payload);
		}
	};

	Liferay.on(
		'chatPortletReady',
		function(event) {
			console.log('chatPortletReady');

			A.on(
				function(options) {
					Liferay.Chat.Video.init(this);
				},
				Liferay.Chat.Manager,
				'init'
			);
			A.on(
				function(event) {
					console.log('Manager#_onPanelClose');
				},
				Liferay.Chat.Manager,
				'_onPanelClose'
			);

			var Chat = Liferay.Chat;
	 
			Chat.ConversationPanel = Chat.Conversation;
	 
			Chat.Conversation = function() {
				console.log('Conversation#ctor [before]');
				Chat.Conversation.superclass.constructor.apply(this, arguments);
				console.log('Conversation#ctor [after]');
			};
	 
			A.extend(
				Chat.Conversation,
				Chat.ConversationPanel,
				{
					setAsRead: function() ­{
						console.log('Conversation#setAsRead');
						Chat.Conversation.superclass.setAsRead.apply(this, arguments);
					},
					setAsUnread: function() {
						console.log('Conversation#setAsUnread');
						Chat.Conversation.superclass.setAsUnread.apply(this, arguments);
					},
					_setPanelHTML: function() {
						console.log('Conversation#_setPanelHTML');
						
						return Chat.Conversation.superclass._setPanelHTML.apply(this, arguments);
					}
				}
			);
		}
	);
}(AUI()));

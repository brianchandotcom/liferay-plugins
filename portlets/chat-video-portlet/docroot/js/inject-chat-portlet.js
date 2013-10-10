/**
 * inject-chat-portlet.js - Injects chat-video-portlet code into chat-portlet
 *
 * Copyright (c) 2013 Savoir-faire Linux Inc.
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

	Liferay.Chat.VideoManager = {
		init: function(chatManager) {
			var instance = this;

			// save chat manager reference and vice versa
			instance._chatManager = chatManager;
			chatManager._videoManager = instance;

			// read our portlet ID (injected in view.jsp)
			instance._portletId = A.one('#chat-video-portlet-id').val();

			// read some portal poller properties
			var pollerNotificationsTimeout = parseInt(A.one('#chat-video-portlet-poller-notifications-timeout').val());
			var pollerRequestTimeout = parseInt(A.one('#chat-video-portlet-poller-request-timeout').val());

			// video buddies list
			instance._buddies = {};

			// increased polling rate
			instance._increasedPollingRate = false;
			instance._increasedPollingRateDelayMs = pollerNotificationsTimeout + pollerRequestTimeout + 100;
			instance._increasedPollingCountMs = 0;
			instance._increasedPollingMaxCountMs = 30000;

			// add poller listener for video chat
			Liferay.Poller.addListener(instance._portletId, instance._onPollerUpdate, instance);
			Liferay.bind(
				'sessionExpired',
				function(event) {
					instance._stopIncreasedPollingRate();
					Liferay.Poller.removeListener(instance._portletId);
				}
			);

			// Initialize master manager
			instance._webRtcManager = Liferay.Chat.WebRtcManager;
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
			instance._webRtcManager.init({
				cb: {
					send: function(payload) {
						instance._send(payload);
					},
					ensurePanel: function(userId) {
						var buddy = instance._chatManager._buddies[userId];
						if (buddy) {
							var chat = instance._chatManager._chatSessions[userId];
							if (!chat) {
								chat = instance._chatManager._createChatSession({
									fullName: buddy.fullName,
									portraitId: buddy.portraitId,
									userId: buddy.userId,
									statusMessage: buddy.statusMessage
								});
							}
						}
					},
					isUserWebRtcAvailable: function(userId) {
						return instance.isUserWebRtcAvailable(userId);
					},
					onMediaEnabled: function() {
						instance.webRtcUnmute();
					},
					onMediaDisabled: function() {
						instance.webRtcUnmute();
					},
					enableRinging: function() {
						if (instance._chatManager._playSound) {
							ringingElemDom.play();
						}
					},
					disableRinging: function() {
						tryDisableAudio(ringingElemDom);
					},
					enableOutRinging: function() {
						if (instance._chatManager._playSound) {
							outRingingElemDom.play();
						}
					},
					disableOutRinging: function() {
						tryDisableAudio(outRingingElemDom);
					}
				}
			});
			instance._webrtcVideoOverlayElem = A.one('#webrtc-video-overlay');
			instance.hideWebRtcVideoOverlay();
		},

		_onPollerUpdate: function(response, chunk) {
			var instance = this;

			// update buddies
			instance._buddies = {};
			for (var i in response.webrtc.clients) {
				instance._buddies[response.webrtc.clients[i]] = true;
			}

			// process video messages
			instance._webRtcManager.processWebRtcMsg(response.webrtc);

			// stop rapid polling if it's been activated for too long
			if (instance._increasedPollingRate) {
				instance._increasedPollingCountMs += instance._increasedPollingRateDelayMs;
				if (instance._increasedPollingCountMs >= instance._increasedPollingMaxCountMs) {
					instance._stopIncreasedPollingRate();
				}
			}
		},

		_send: function(payload) {
			var instance = this;

			/* The provided payload is not specific to this chat portlet.
			 * We need to craft it a little more. To do so, we add
			 * the true 'webrtc' member so that the server knows it's a
			 * WebRTC message. We also prepend the 'webrtc' prefix to
			 * each member to avoid name collisions.
			 *
			 * Example: 'dstUserId' -> 'webrtcDstUserId'
			 */
			for (var paramName in payload) {
				var newParamName = 'webrtc' + paramName.charAt(0).toUpperCase() + paramName.slice(1);
				payload[newParamName] = payload[paramName];
				delete payload[paramName];
			}

			// send using poller
			Liferay.Poller.submitRequest(instance._portletId, payload);
		},

		webRtcMute: function() {
			var instance = this;

			instance._webRtcManager.muteMike();
			this._updateWebRtcMikeButtons('muted');
		},

		webRtcUnmute: function() {
			var instance = this;

			instance._webRtcManager.unmuteMike();
			this._updateWebRtcMikeButtons('unmuted');
		},

		_updateWebRtcMikeButtons: function(newClass) {
			var instance = this;

			for (id in instance._chatManager._chatSessions) {
				instance._chatManager._chatSessions[id].updateWebRtcMikeButton(newClass);
			}
		},

		showWebRtcVideoOverlay: function() {
			this._webrtcVideoOverlayElem.show();
		},

		hideWebRtcVideoOverlay: function() {
			this._webrtcVideoOverlayElem.hide();
		},

		isUserWebRtcAvailable: function(userId) {
			var instance = this;

			return (instance._buddies[userId] != undefined);
		},

		onWebRtcConversationStateChange: function() {
			var instance = this;
			var globalState = instance._webRtcManager.getConversationsGlobalState();

			if (globalState.communicationRequired) {
				instance._startIncreasedPollingRate();
			} else {
				instance._stopIncreasedPollingRate();
			}
		},

		_setIncreasedPollingRate: function(inc) {
			this._increasedPollingRate = inc;
		},
		_startIncreasedPollingRate: function() {
			var instance = this;

			instance._increasedPollingCountMs = 0;
			instance._setIncreasedPollingRate(true);
			Liferay.Poller.setOverriddenDelay(instance._increasedPollingRateDelayMs);
		},
		_stopIncreasedPollingRate: function() {
			var instance = this;

			instance._setIncreasedPollingRate(false);
			Liferay.Poller.cancelOverriddenDelay();
		},

		_onPanelClose: function(event) {
			var instance = this;

			var panel = event.target;
			var userId = panel._panelId;

			if (panel instanceof Liferay.Chat.Conversation) {
				instance._chatManager._chatSessions[userId].onDelete();
			}
		},

		_onAfterUpdateBuddies: function(buddies) {
			var instance = this;

			// verify if buddies support video and add icon if so
			var listItems = instance._chatManager._onlineBuddies.all('li.user');
			listItems.each(function(liNode) {
				var uid = liNode.getAttribute('userId');
				if (uid && instance.isUserWebRtcAvailable(uid)) {
					var iconNode = A.Node.create('<div class="webrtc-enabled-icon"></div>');
					iconNode.appendTo(liNode);
				}
			});
		}
	};

	Liferay.on(
		'chatPortletReady',
		function(event) {
			A.on(
				function(options) {
					Liferay.Chat.VideoManager.init(this);
				},
				Liferay.Chat.Manager,
				'init'
			);
			A.on(
				function(event) {
					Liferay.Chat.VideoManager._onPanelClose(event);
				},
				Liferay.Chat.Manager,
				'_onPanelClose'
			);
			A.after(
				function(buddies) {
					Liferay.Chat.VideoManager._onAfterUpdateBuddies(buddies);
				},
				Liferay.Chat.Manager,
				'_updateBuddies'
			);

			var Chat = Liferay.Chat;
			Chat.ConversationPanel = Chat.Conversation;
			Chat.Conversation = function() {
				var instance = this;

				Chat.Conversation.superclass.constructor.apply(instance, arguments);

				// unread video messages
				instance._unreadMessagesWebRtcContainer = instance._panel.one('.unread-webrtc');
				if (!instance._unreadMessagesWebRtcContainer) {
					instance._unreadMessagesWebRtcContainer = A.Node.create('<div class="unread-webrtc" />');
					instance._popupTrigger.append(instance._unreadMessagesWebRtcContainer);
				}

				// WebRTC conversation instance
				instance._webRtc = null;
				instance._webRtcAvailable = false;
				if (Liferay.Chat.WebRtcManager.isSupported()) {
					// WebRTC conversation
					var remoteVideoElem = instance._panel.one('div.webrtc-remote-video video.remote');
					var localVideoElem = instance._panel.one('div.panel-self-view video.local');
					var remoteVideoContainerElem = instance._panel.one('div.webrtc-remote-video-container');
					var remoteVideoOuterElem = instance._panel.one('div.webrtc-remote-video');
					var selfViewElem = instance._panel.one('div.panel-self-view');
					var selfViewImgElem = instance._panel.one('div.panel-self-view img');
					instance._ctrlContainerElem = instance._panel.one('div.webrtc-ctrl');
					var ctrlButtonsElem = instance._panel.one('div.webrtc-buttons');
					instance._webRtcMsgContainerElem = instance._panel.one('div.webrtc-msg');
					instance._webRtcMsgElem = instance._panel.one('div.webrtc-msg div.msg');
					instance._webRtcMsgWorkingElem = instance._panel.one('div.webrtc-msg div.working');
					instance._webRtcCtrlButtonsElems = {
						'accept': instance._panel.one('div.webrtc-ctrl a.accept'),
						'hangUp': instance._panel.one('div.webrtc-ctrl a.hangup'),
						'call': instance._panel.one('div.webrtc-ctrl a.call'),
						'mike': instance._panel.one('div.webrtc-ctrl a.mike'),
						'fullScreen': instance._panel.one('div.webrtc-ctrl a.fullscreen')
					};
					var origBackgroundColor = instance._ctrlContainerElem.getStyle('backgroundColor');
					var flickerControlsBackground = function() {
						/* Some weird rendering bug is happening in Chromium/Chrome. Even if the
						 * cache is completely disabled, the full screen and mute controls do not
						 * show when supposed to. Those tested actions can show them:
						 *
						 *   * minimizing/maximizing the panel
						 *   * going to another tab and coming back
						 *   * hovering where they are supposed to be
						 *   * going full screen (clicking on the remote video element) and going
						 *     back to normal
						 *
						 * It seems like a rendering bug in the browser itself that's somehow
						 * related to the context.
						 *
						 * The (really ugly) solution is to modify the background color of the
						 * controls container and then, after a few ms, reset it to its original
						 * value. The user doesn't see this flicker; it's too fast. However, it
						 * seems like the browser, when having to render again the controls images
						 * over the new background color, will do properly this time.
						 *
						 * This bug does not exist on Firefox.
						 */
						instance._ctrlContainerElem.setStyle('backgroundColor', '#20272c');
						setTimeout(function() {
							instance._ctrlContainerElem.setStyle('backgroundColor', origBackgroundColor);
						}, 2);
					};
					var playVideos = function() {
						remoteVideoElem.getDOM().play();
						localVideoElem.getDOM().play();
					};
					var enableVideoFullScreen = function() {
						remoteVideoOuterElem.appendTo('#webrtc-video-overlay');
						localVideoElem.appendTo(remoteVideoOuterElem);
						ctrlButtonsElem.appendTo('#webrtc-video-overlay');
						Liferay.Chat.VideoManager.showWebRtcVideoOverlay();
						instance._webRtcCtrlButtonsElems['fullScreen'].removeClass('off');
						instance._webRtcCtrlButtonsElems['fullScreen'].addClass('on');
						playVideos();
					};
					var disableVideoFullScreen = function() {
						Liferay.Chat.VideoManager.hideWebRtcVideoOverlay();
						remoteVideoOuterElem.appendTo(remoteVideoContainerElem);
						localVideoElem.appendTo(selfViewElem);
						ctrlButtonsElem.appendTo(instance._ctrlContainerElem);
						instance._webRtcCtrlButtonsElems['fullScreen'].removeClass('on');
						instance._webRtcCtrlButtonsElems['fullScreen'].addClass('off');
						playVideos();
					};
					var toggleFullScreen = function() {
						// toggle
						if (remoteVideoOuterElem.ancestor().attr('id') == 'webrtc-video-overlay') {
							disableVideoFullScreen();
						} else {
							enableVideoFullScreen();
						}
					};
					var showControlButton = function(btnId) {
						instance._webRtcCtrlButtonsElems[btnId].show();
						flickerControlsBackground();
					};
					var hideControlButton = function(btnId) {
						instance._webRtcCtrlButtonsElems[btnId].hide();
						flickerControlsBackground();
					};
					var hideLocalVideo = function() {
						localVideoElem.hide();
						selfViewImgElem.show();
					};
					var showLocalVideo = function() {
						selfViewImgElem.hide();
						localVideoElem.show();
						localVideoElem.getDOM().play();
					};
					var onRemoteStreamFlowing = function() {
						// controls
						hideControlButton('accept');
						showControlButton('hangUp');
						hideControlButton('call');
						showControlButton('mike');
						showControlButton('fullScreen');

						// status message
						instance._hideWebRtcMsg();

						// remote video
						disableVideoFullScreen();
						remoteVideoContainerElem.show();

						// local video
						showLocalVideo();
					};
					var connectedVerifyRemoteStreamFlowing = function() {
						// make sure we're at least connected
						if (instance._webRtc.getState() != Liferay.Chat.WebRtcConversation.State.CONNECTED) {
							return;
						}

						// verify if the remote stream is flowing
						if (!instance._webRtc.isRemoteStreamFlowing()) {
							// try again later if not
							setTimeout(connectedVerifyRemoteStreamFlowing, 250);
						} else {
							onRemoteStreamFlowing();
						}
					};
					instance._webRtc = new Liferay.Chat.WebRtcConversation({
						userId: instance._panelId,
						remoteVideoDomElem: remoteVideoElem.getDOM(),
						localVideoDomElem: localVideoElem.getDOM(),
						iceServers: [],
						cb: {
							onWebRtcEvent: function() {
								if (!instance.get('selected')) {
									if (instance._unreadMessagesWebRtcContainer) {
										instance._unreadMessagesWebRtcContainer.show();
									}
								}
							},
							onStateChange: function(state) {
								var State = Liferay.Chat.WebRtcConversation.State;

								// signal our manager that a WebRTC conversation state has changed
								Liferay.Chat.VideoManager.onWebRtcConversationStateChange();

								// control: accept
								switch (state) {
									case State.GOTCALL:
										showControlButton('accept');
										break;

									default:
										hideControlButton('accept');
										break;
								}

								// control: hang up
								switch (state) {
									case State.STOPPED:
									case State.STOPPING:
									case State.DELETED:
									case State.DELETING:
										hideControlButton('hangUp');
										break;

									default:
										showControlButton('hangUp');
										break;
								}

								// control: call
								switch (state) {
									case State.STOPPED:
										showControlButton('call');
										break;

									default:
										hideControlButton('call');
										break;
								}

								// control: microphone
								switch (state) {
									default:
										hideControlButton('mike');
										break;
								}

								// control: full screen
								switch (state) {
									default:
										hideControlButton('fullScreen');
										break;
								}

								// messages
								switch (state) {
									case State.DELETED:
									case State.STOPPED:
										instance._hideWebRtcMsg();
										break;

									case State.CALLINGWAITING:
									case State.GOTCALLWAITING:
										instance._showSetWebRtcMsg('Please share camera', true);
										break;

									case State.CALLING:
									case State.CALLED:
										instance._showSetWebRtcMsg('Calling remote peer...', true);
										break;

									case State.GOTCALL:
										instance._showSetWebRtcMsg('Incoming video call...', true);
										break;

									case State.GOTANSWER:
									case State.ANSWERED:
									case State.ACCEPTINGCALL:
									case State.DENYINGCALL:
										instance._showSetWebRtcMsg('Establishing connection...', true);
										break;

									case State.STOPPING:
									case State.DELETING:
										instance._showSetWebRtcMsg('Stopping video call...', true);
										break;
								}

								// remote video, full screen
								switch (state) {
									case State.DELETED:
									case State.DELETING:
									case State.STOPPED:
									case State.STOPPING:
										remoteVideoContainerElem.hide();
										disableVideoFullScreen();
										break;

									default:
										break;
								}

								// local video
								switch (state) {
									case State.CALLING:
									case State.CALLED:
									case State.GOTCALL:
									case State.ANSWERED:
									case State.GOTANSWER:
									case State.ACCEPTINGCALL:
									case State.DENYINGCALL:
									case State.CONNECTED:
										showLocalVideo();
										break;

									default:
										hideLocalVideo();
										break;
								}

								if (state == State.CONNECTED) {
									connectedVerifyRemoteStreamFlowing();
								}
							}
						}
					});

					// buttons actions
					instance._webRtcCtrlButtonsElems['accept'].on('click', function() {
						instance._webRtc.onPressAccept();
					});
					instance._webRtcCtrlButtonsElems['hangUp'].on('click', function() {
						disableVideoFullScreen();
						instance._webRtc.onPressHangUp();
					});
					instance._webRtcCtrlButtonsElems['call'].on('click', function() {
						if (Liferay.Chat.VideoManager.isUserWebRtcAvailable(instance._panelId)) {
							instance._webRtc.onPressCall();
						} else {
						}
					});
					instance._webRtcCtrlButtonsElems['fullScreen'].on('click', function() {
						toggleFullScreen();
					});
					instance._webRtcCtrlButtonsElems['mike'].on('click', function() {
						var self = this;

						if (self.hasClass('muted')) {
							Liferay.Chat.VideoManager.webRtcUnmute();
						} else {
							Liferay.Chat.VideoManager.webRtcMute();
						}
					});

					// remote video element action
					remoteVideoOuterElem.on('click', toggleFullScreen);

					// initially show WebRTC controls if remote peer supports it
					instance._setWebRtcAvailable(Liferay.Chat.VideoManager.isUserWebRtcAvailable(instance._panelId));
				}
			};

			A.extend(
				Chat.Conversation,
				Chat.ConversationPanel,
				{
					setAsRead: function() ­{
						var instance = this;

						Chat.Conversation.superclass.setAsRead.apply(instance, arguments);
						instance._unreadMessagesWebRtcContainer.hide();
					},
					setAsUnread: function() {
						var instance = this;

						Chat.Conversation.superclass.setAsUnread.apply(instance, arguments);
						instance._unreadMessagesWebRtcContainer.hide();
					},
					_setPanelHTML: function() {
						var instance = this;

						var userImagePath = Liferay.Chat.Util.getUserImagePath(instance._panelIcon);

						// send custom HTML
						var html = '<li class="user user_' + instance._panelId + '" panelId="' + instance._panelId + '">' +
										'<div class="panel-trigger">' +
											'<span class="trigger-name"></span>' +
											'<div class="typing-status"></div>' +
										'</div>' +
										'<div class="chat-panel">' +
											'<div class="hide webrtc-remote-video-container">' +
												'<div class="webrtc-remote-video">' +
													'<video class="remote" autoplay></video>' +
												'</div>' +
											'</div>' +
											'<div class="panel-window">' +
												'<div class="panel-button minimize"></div>' +
												'<div class="panel-button close"></div>' +
												'<div class="panel-self-view">' +
													'<img alt="" src="' + userImagePath + '" />' +
													'<video class="hide local" autoplay muted></video>' +
												'</div>' +
												'<div class="panel-title">' + instance._panelTitle + '</div>' +
												'<div class="panel-profile">...</div>' +
												'<div class="hide webrtc-ctrl">' +
													'<div class="hide webrtc-msg">' +
														'<div class="msg"></div>' +
														'<div class="working"></div>' +
													'</div>' +
													'<div class="webrtc-buttons">' +
														'<a href="javascript:void(0);" class="hide accept"></a>' +
														'<a href="javascript:void(0);" class="hide hangup"></a>' +
														'<a href="javascript:void(0);" class="hide call"></a>' +
														'<a href="javascript:void(0);" class="hide mike unmuted"></a>' +
														'<a href="javascript:void(0);" class="hide fullscreen off"></a>' +
														'<div style="clear: both;"></div>' +
													'</div>' +
												'</div>' +
												'<div class="panel-output"></div>' +
												'<div class="panel-input">' +
													'<textarea class="message-input"></textarea>' +
												'</div>' +
											'</div>' +
										'</div>' +
									'</li>';

						return html;
					},
					getWebRtc: function() {
						return this._webRtc;
					},

					close: function() {
						var instance = this;

						Liferay.Chat.Panel.prototype.close.call(instance);
					},

					onDelete: function() {
						var instance = this;

						if (instance.getWebRtc()) {
							instance.getWebRtc().onDelete();
						}
					},

					updateWebRtcMikeButton: function(newClass) {
						var instance = this;
						var elem = instance._webRtcCtrlButtonsElems['mike'];

						elem.removeClass('muted');
						elem.removeClass('unmuted');
						elem.addClass(newClass);
					},

					_showSetWebRtcMsg: function(htmlMsg, isWorking) {
						this._webRtcMsgElem.html(htmlMsg);
						this._webRtcMsgWorkingElem.hide();
						if (isWorking) {
							this._webRtcMsgWorkingElem.show();
						}
						this._webRtcMsgContainerElem.show();
					},

					_hideWebRtcMsg: function() {
						this._webRtcMsgContainerElem.hide();
					},

					_setWebRtcAvailable: function(available) {
						var instance = this;

						instance._webRtcAvailable = available;
						if (instance._webRtc) {
							if (available) {
								instance._ctrlContainerElem.show();
							} else {
								instance._ctrlContainerElem.hide();
							}
						}
					}
				}
			);
		}
	);
}(AUI()));

/**
 * webrtc.js - WebRTC management for any chat portlet
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
AUI().use('aui-base', function(A) {
	Liferay.namespace('Chat');

	/**
	 * WebRTC manager.
	 *
	 * This object is a singleton on every page and manages global WebRTC
	 * states and behaviours. It's responsible for user media acquisition
	 * and for reporting the global state of all conversations.
	 *
	 * Any system using this manager must initialize it by calling
	 *
	 *     Liferay.Chat.WebRtcManager.init()
	 *
	 * Browser support for WebRTC can be tested using
	 *
	 *     Liferay.Chat.WebRtcManager.isSupported()
	 */
	Liferay.Chat.WebRtcManager = {
		// all possible WebRTC user media acquisition states
		State: {
			INIT: 'init',           // not asked
			ACQUIRED: 'acquired',   // user media acquired
			ASKED: 'asked'          // browser asked for user media
		},

		/* Update presence period (ms). An "update presence" message
		 * is sent periodically so that other users know we're alive.
		 */
		_UPDATE_PRESENCE_PERIOD_MS: 10000,

		// RTC manager
		_webRtcCompat: setupRTC(),

		// connector callbacks
		_cb: {
			send: null,
			getConversation: null,
			isUserWebRtcAvailable: null
		},

		// current user media acquisition state
		_currentState: null,

		// local acquired stream
		_localStream: null,

		// update presence timer ID
		_updatePresenceTimerId: null,

		// all WebRTC conversations
		_conversations: [],

		/**
		 * Initializes the Liferay WebRTC manager.
		 *
		 * @param conf Configuration object
		 */
		init: function(conf) {
			var instance = Liferay.Chat.WebRtcManager;

			instance.debugMsg('initializing the WebRTC manager');

			// check if WebRTC is supported
			if (Liferay.Chat.WebRtcManager.getWebRtcCompat() == null) {
				instance.debugMsg('WebRTC is not supported');
				return;
			}
			instance.debugMsg('WebRTC is supported!');

			// keep user callbacks
			instance._cb.send = conf.cb.send;
			instance._cb.ensurePanel = conf.cb.ensurePanel;
			instance._cb.isUserWebRtcAvailable = conf.cb.isUserWebRtcAvailable;
			instance._cb.onMediaEnabled = conf.cb.onMediaEnabled;
			instance._cb.onMediaDisabled = conf.cb.onMediaDisabled
			instance._cb.enableRinging = conf.cb.enableRinging;
			instance._cb.disableRinging = conf.cb.disableRinging;
			instance._cb.enableOutRinging = conf.cb.enableOutRinging;
			instance._cb.disableOutRinging = conf.cb.disableOutRinging;

			// set initial WebRTC state
			instance._currentState = Liferay.Chat.WebRtcManager.State.INIT;

			// send reset message to server
			instance.sendResetMsg();

			// send availability message
			instance.sendSetAvailabilityMsg(true);

			// setup update presence timer
			instance._updatePresenceTimerId = setInterval(function() {
				instance._updatePresence();
			}, instance._UPDATE_PRESENCE_PERIOD_MS);
		},

		/**
		 * Gets WebRTC browser support.
		 *
		 * @return true if WebRTC is supported
		 */
		isSupported: function() {
			return Liferay.Chat.WebRtcManager.getWebRtcCompat() != null;
		},

		/**
		 * Outputs debug information about an object.
		 *
		 * @param obj Object to output
		 */
		debugObj: function(obj) {
			//console.log(obj);
		},

		/**
		 * Outputs a debug message.
		 *
		 * @param msg Message string to output
		 */
		debugMsg: function(msg) {
			console.log('WebRTC: ' + msg);
		},

		/**
		 * Outputs a network I/O debug message.
		 *
		 * @param msgType Message type (string)
		 * @param details Details string (short) about the message
		 * @param dir 'i' for input or 'o' for output
		 */
		debugIO: function(msgType, details, dir) {
			var msg = '';
			if (dir == 'i') {
				msg += '[S -> C]';
			} else if (dir == 'o') {
				msg += '[C -> S]';
			} else {
				return;
			}
			msg += ' (' + msgType + ') ' + details;
			Liferay.Chat.WebRtcManager.debugMsg(msg);
		},

		/**
		 * Outputs a network I/O debug message (JSON version).
		 *
		 * @param msgType Message type (string)
		 * @param msgObj Message object
		 * @param dir 'i' for input or 'o' for output
		 */
		debugJsonIO: function(msgType, msgObj, dir) {
			Liferay.Chat.WebRtcManager.debugIO(msgType, JSON.stringify(msgObj), dir);
		},

		/**
		 * Gets the WebRTC adapter
		 *
		 * @return WebRTC adapter or null if WebRTC is not supported
		 */
		getWebRtcCompat: function() {
			return Liferay.Chat.WebRtcManager._webRtcCompat;
		},

		/**
		 * Gets the current user media acquisition state.
		 *
		 * @return User media acquisition state
		 */
		getState: function() {
			var instance = Liferay.Chat.WebRtcManager;

			return instance._currentState;
		},

		/**
		 * Sets the current user media acquisition state.
		 *
		 * @param state New user media acquisition state
		 */
		setState: function(state) {
			var instance = Liferay.Chat.WebRtcManager;

			instance._currentState = state;
		},

		/**
		 * Gets the local stream.
		 *
		 * @return Local stream or null if not acquired
		 */
		getLocalStream: function() {
			var instance = Liferay.Chat.WebRtcManager;

			return instance._localStream;
		},

		/**
		 * Sets the local stream.
		 *
		 * @param Local stream
		 */
		_setLocalStream: function(stream) {
			var instance = Liferay.Chat.WebRtcManager;

			instance._localStream = stream;
		},

		/**
		 * Sends a reset message to the server.
		 */
		sendResetMsg: function() {
			var instance = Liferay.Chat.WebRtcManager;

			instance.sendMsg('reset', {});
		},

		/**
		 * Sends an availability message to the server.
		 *
		 * @param available true if user is available for WebRTC
		 */
		sendSetAvailabilityMsg: function(available) {
			var instance = Liferay.Chat.WebRtcManager;
			var msgType = 'setAvailability';
			var msg = {
				'isAvailable': available
			};

			instance.sendMsg(msgType, msg);
		},

		/**
		 * Sends a presence update message to the server.
		 */
		sendUpdatePresenceMsg: function() {
			var instance = Liferay.Chat.WebRtcManager;
			var msgType = 'updatePresence';

			instance.sendMsg(msgType, {});
		},

		/**
		 * Sends a message to the server.
		 *
		 * @param mstType Message type
		 * @param payload Payload
		 */
		sendMsg: function(msgType, payload) {
			var instance = Liferay.Chat.WebRtcManager;

			payload['msgType'] = msgType;
			instance._cb.send(payload);
			Liferay.Chat.WebRtcManager.debugJsonIO(msgType, payload, 'o');
		},

		/**
		 * Handles an important error.
		 *
		 * @param errMsg Error message
		 */
		doError: function(errMsg) {
			Liferay.Chat.WebRtcManager.debugMsg("error: " + errMsg);
		},

		/**
		 * Checks if a remote user is available for/supports WebRTC.
		 *
		 * @param userId Remote user ID
		 * @return true if available
		 */
		isUserWebRtcAvailable: function(userId) {
			var instance = Liferay.Chat.WebRtcManager;

			return instance._cb.isUserWebRtcAvailable(userId);
		},

		/**
		 * Registers a WebRTC conversation.
		 *
		 * @param conv WebRTC conversation instance
		 */
		registerConversation: function(conv) {
			var instance = Liferay.Chat.WebRtcManager;
			instance._conversations[conv.getToUserId()] = conv;
			instance.debugMsg('registering conversation ID ' + conv.getToUserId());
		},

		/**
		 * Updates the WebRTC presence (sends to server).
		 */
		_updatePresence: function() {
			var instance = Liferay.Chat.WebRtcManager;

			instance.sendUpdatePresenceMsg();
		},

		/**
		 * Gets the global state of conversations. It if at least one
		 * conversation amongst all registered ones has one of the following
		 * conditions:
		 *
		 *   * active: in the signalling process or connected
		 *   * outRinging: supposed to make the outgoing ringtone tone audible
		 *   * ringing: supposed to make the incoming ringtone tone audible
		 *   * waiting: waiting for user media acquisition
		 *   * communicationRequired: requires active communication with the server
		 *
		 * This can be very useful to do global actions based on the state of
		 * "at least one" conversation. For example, we don't want to turn off the
		 * incoming ringtone as soon as a conversation is connected because other
		 * conversations could still be calling.
		 *
		 * @return Global state of conversations
		 */
		getConversationsGlobalState: function() {
			var instance = this;
			var State = Liferay.Chat.WebRtcConversation.State;
			var ret = {
				active: false,
				outRinging: false,
				ringing: false,
				waiting: false,
				communicationRequired: false
			};

			for (uid in instance._conversations) {
				var conv = instance._conversations[uid];
				if (conv.getState() != State.STOPPED &&
					conv.getState() != State.DELETED &&
					conv.getState() != State.STOPPING &&
					conv.getState() != State.DELETING) {
					// latch active
					ret.active = true;
					if (conv.getState() != State.CONNECTED) {
						// latch communicationRequired
						ret.communicationRequired = true;
					}
				}
				if (conv.getState() == State.STOPPING ||
					conv.getState() == State.DELETING) {
					// latch communicationRequired
					ret.communicationRequired = true;
				}
				if (conv.getState() == State.CALLING ||
					conv.getState() == State.CALLED) {
					// latch outgoing ringing
					ret.outRinging = true;
				}
				if (conv.getState() == State.GOTCALL ||
					conv.getState() == State.GOTCALLWAITING) {
					// latch ringing
					ret.ringing = true;
				}
				if (conv.getState() == State.CALLINGWAITING ||
					conv.getState() == State.GOTCALLWAITING) {
					// latch waiting
					ret.waiting = true;
				}
			}

			return ret;
		},

		// event: a registered WebRTC conversation state just changed
		onConversationStateChange: function() {
			var instance = Liferay.Chat.WebRtcManager;

			/* Check all the registered WebRTC conversations: if at least one is
			 * active, do not close the local stream. this routine exists to stop
			 * using the camera if it's not needed (closes its LED). Also check if
			 * a ringing sound is supposed to play. */
			var globalState = instance.getConversationsGlobalState();
			if (!globalState.active) {
				/* If no conversation is active, we don't need the
				 * local stream anymore and may disable it.
				 */
				instance._disableMedia(true);
			}
			if (globalState.waiting) {
				/* If at least one conversation is waiting for the
				 * local stream, we need to enable it. The called function
				 * will ensure to ask the user to share its camera only
				 * if it's not already acquired nor asked for.
				 */
				instance._enableMedia();
			}
			if (globalState.ringing) {
				instance._cb.enableRinging();
			} else {
				instance._cb.disableRinging();
			}
			if (globalState.outRinging) {
				instance._cb.enableOutRinging();
			} else {
				instance._cb.disableOutRinging();
			}
		},

		/**
		 * Transitions from waiting to cancelled for all registerd conversations
		 * that are waiting.
		 */
		_updateWaitingConversationsCancel: function() {
			var instance = Liferay.Chat.WebRtcManager;

			for (var i in instance._conversations) {
				var conv = instance._conversations[i];

				if (conv.getState() == Liferay.Chat.WebRtcConversation.State.GOTCALLWAITING) {
					// need to deny the call first
					conv.setState(Liferay.Chat.WebRtcConversation.State.DENYINGCALL);
				}
				if (conv.getState() == Liferay.Chat.WebRtcConversation.State.CALLINGWAITING) {
					// nothing happened, so just stop
					conv.setState(Liferay.Chat.WebRtcConversation.State.STOPPED);
				}
			}
		},

		/**
		 * Transitions from waiting to next logical state for all registerd
		 * conversations that are waiting.
		 */
		_updateWaitingConversationsNextState: function() {
			var instance = Liferay.Chat.WebRtcManager;

			for (var i in instance._conversations) {
				var conv = instance._conversations[i];

				if (conv.getState() == Liferay.Chat.WebRtcConversation.State.GOTCALLWAITING) {
					conv.setState(Liferay.Chat.WebRtcConversation.State.GOTCALL);
				}
				if (conv.getState() == Liferay.Chat.WebRtcConversation.State.CALLINGWAITING) {
					conv.setState(Liferay.Chat.WebRtcConversation.State.CALLING);
				}
			}
		},

		/**
		 * Tries to acquire the user media. If it fails, it cancels all registered
		 * waiting conversations. If it succeeds, it updates all registerd waiting
		 * conversations to their next logical state. If, when acquired, no conversation
		 * is waiting, it releases it.
		 */
		_enableMedia: function() {
			var instance = Liferay.Chat.WebRtcManager;

			if (instance.getState() == instance.State.INIT) {
				instance.setState(instance.State.ASKED);
				instance.getWebRtcCompat().getUserMedia({
					'audio': true,
					'video': {
						'mandatory': {},
						'optional': []
					}
				}, function(stream) {
					instance.debugMsg('user media acquired');

					// set the local acquired stream so it can be reused everywhere
					instance._setLocalStream(stream);

					// local stream is now acquired
					instance.setState(instance.State.ACQUIRED);

					/* Let's first make sure that at least one conversation wants
					 * media access at the moment. If not, release it.
					 */
					var globalState = instance.getConversationsGlobalState();
					if (!globalState.active) {
						instance._disableMedia(false);
						return;
					}

					// tell our host that it's done (
					instance._cb.onMediaEnabled();

					/* We now look at each registered WebRTC conversation and, for
					 * each one that has a current state waiting for media
					 * acquisition, we make it progress to its next state.
					 */
					instance._updateWaitingConversationsNextState();
				}, function(e) {
					instance.doError('error while trying to acquired user media');

					// go back to the initial state (not asked, not acquired)
					instance.setState(instance.State.INIT);

					/* We now look at each registered WebRTC conversation and, for
					 * each one that has a current state waiting for media
					 * acquisition, we make it progress to its next state.
					 */
					instance._updateWaitingConversationsCancel();
				});
			} else if (instance.getState() == instance.State.ACQUIRED) {
				// already acquired: make sure no conversation is waiting anymore
				instance._updateWaitingConversationsNextState();
			} else {
				instance.debugMsg('local stream in progress of being acquired');
			}
		},

		/**
		 * Enables/disables all audio tracks of the local stream (this is for muting
		 * the local microphone).
		 *
		 * @param en true to enable all audio tracks, false to disable them
		 */
		_setAudioTracksEnabled: function(en) {
			var instance = this;

			if (instance.getState() == instance.State.ACQUIRED && instance.getLocalStream()) {
				for (i in instance.getLocalStream().getAudioTracks()) {
					var track = instance.getLocalStream().getAudioTracks()[i];
					track.enabled = en;
				}
			}
		},

		/**
		 * Mutes the local microphone.
		 */
		muteMike: function() {
			Liferay.Chat.WebRtcManager.debugMsg('muting microphone');
			this._setAudioTracksEnabled(false);
		},

		/**
		 * Unmutes the local microphone.
		 */
		unmuteMike: function() {
			Liferay.Chat.WebRtcManager.debugMsg('unmuting microphone');
			this._setAudioTracksEnabled(true);
		},

		/**
		 * Stops the local stream, which should turn off any light
		 * indicator on the camera being used if the browser/driver releases
		 * it properly.
		 */
		_stopLocalStream: function() {
			var instance = this;

			if (instance._localStream) {
				/* This is how we dispose of a local stream: we stop
				 * it and then set it to null so that there's no more
				 * reference to it.
				 * it.
				 */
				instance.debugMsg('stopping local stream');
				instance._localStream.stop();
				instance._localStream = null;
			}
		},

		/**
		 * Releases the acquired user media.
		 *
		 * @param notify true to notify the host that media is not disabled
		 */
		_disableMedia: function(notify) {
			var instance = Liferay.Chat.WebRtcManager;

			// only do this if we know the local stream is acquired
			if (instance.getState() == instance.State.ACQUIRED) {
				instance.setState(instance.State.INIT);
				instance._stopLocalStream();
				if (notify) {
					// tell our host that the local stream is now disabled
					instance._cb.onMediaDisabled();
				}
			}
		},

		/**
		 * Processes an incoming WebRTC message.
		 *
		 * @param obj Incoming message containing WebRTC mails
		 */
		processWebRtcMsg: function(obj) {
			var instance = Liferay.Chat.WebRtcManager;
			var mails = obj.mails;

			if (mails.length > 0) {
				instance.debugMsg('received ' + mails.length + ' mail' + (mails.length > 1 ? 's' : '') + ':');
			}
			for (var i in mails) {
				mail = mails[i];
				var msg = JSON.parse(mail.msg);

				// do not create a panel for a connection status message
				var ensurePanel = !(mail.type == 'conn' && msg.type == 'status');
				if (ensurePanel) {
					instance.debugMsg('asking host to ensure panel for user ID ' + mail.fromUserId);
					var webRtcConversation = instance._cb.ensurePanel(mail.fromUserId);
				}
				var webRtcConversation = instance._conversations[mail.fromUserId];
				if (!webRtcConversation) {
					instance.doError('got message for user ' + mail.fromUserId + ', but conversation not registered');
					continue;
				}
				Liferay.Chat.WebRtcManager.debugJsonIO(mail.type, msg, 'i');

				switch (mail.type) {
				case 'err':
					webRtcConversation.onError(msg);
					break;

				case 'conn':
					webRtcConversation._cb.onWebRtcEvent();
					switch (msg.type) {
					case 'call':
						webRtcConversation.onMsgGotCall();
						break;

					case 'answer':
						webRtcConversation.onMsgGotAnswer(msg);
						break;

					case 'status':
						webRtcConversation.onMsgGotStatus(msg);
						break;

					default:
						instance.doError('got "conn" message, but unknown connection message type "' + msg.type + '"');
					}
					break;

				case 'ice':
					webRtcConversation.onMsgNewIceCandidate(msg);
					break;

				case 'sdp':
					webRtcConversation.onMsgNewSdp(msg);
					break;

				default:
					instance.doError('got message, but unknown message type "' + mail.type + '"');
				}
			}
		}
	};

	/**
	 * WebRTC conversation.
	 *
	 * Each chat conversation on a WebRTC supported browser should instanciate
	 * an instance of this.
	 *
	 * A WebRTC conversation is just a big FSM. Event callbacks set a few things
	 * and switch a state. Switching to a specific state does something specific
	 * for that state and notifies the state transition to the WebRTC manager and
	 * others.
	 */
	Liferay.Chat.WebRtcConversation = function(conf) {
		var instance = this;

		// associated user ID
		instance._userId = conf.userId;
		Liferay.Chat.WebRtcManager.debugMsg('creating new WebRTC conversation (ID ' + instance._userId + ')');

		// connector callbacks
		instance._cb = {
			onWebRtcEvent: conf.cb.onWebRtcEvent,
			onStateChange: conf.cb.onStateChange
		};

		// remote/local video element
		instance._remoteVideoDomElem = conf.remoteVideoDomElem;
		instance._localVideoDomElem = conf.localVideoDomElem;

		// ICE servers
		instance._iceServers = conf.iceServers;

		// signal manager that I now exist
		Liferay.Chat.WebRtcManager.registerConversation(instance);

		// current state
		instance.setState(Liferay.Chat.WebRtcConversation.State.STOPPED);
	};

	// all the WebRTC conversation states:
	Liferay.Chat.WebRtcConversation.State = {
		STOPPED: 'stopped',					// nothing happening, ready to call or receive a call
		CALLINGWAITING: 'callingWaiting',   // user wants to call, but still needs to share its media
		CALLING: 'calling',					// user wants to call
		CALLED: 'called',					// call sent to the server
		GOTCALLWAITING: 'gotCallWaiting',	// got a call from the server, but user still needs to share its media
		GOTCALL: 'gotCall',					// got a call and user may accept/deny it
		ACCEPTINGCALL: 'acceptingCall',     // user is accepting the call
		DENYINGCALL: 'denyingCall',         // user is denying the call
		ANSWERED: 'answered',				// user answered (accepted) an incoming call
		GOTANSWER: 'gotAnswer',				// remote user answered (accepted) an outgoing call
		CONNECTED: 'connected',				// both users are WebRTC-connected
		STOPPING: 'stopping',               // in process of being stopped
		DELETING: 'deleting',               // in process of being deleted
		DELETED: 'deleted'                  // conversation was deleted (instance may still exist, but is dead in this state)
	};

	// WebRTC conversation methods
	Liferay.Chat.WebRtcConversation.prototype = {
		// state: CALLING
		_onStateCalling: function() {
			var instance = this;

			instance._sendCallMsg();
			instance._isCaller = true;
			instance.setState(Liferay.Chat.WebRtcConversation.State.CALLED);
		},

		// state: CALLED
		_onStateCalled: function() {
			var instance = this;

			instance._isCaller = true;

			// we have user media, so we can set the local video stream
			instance._setLocalVideoStream();
		},

		// state: GOTCALL
		_onStateGotCall: function() {
			var instance = this;

			instance._isCaller = false;

			// we have user media, so we can set the local video stream
			instance._setLocalVideoStream();
		},

		// state: GOTANSWER
		_onStateGotAnswer: function() {
			var instance = this;

			instance._doWebRtcCall();
		},

		// state: ACCEPTINGCALL
		_onStateAcceptingCall: function() {
			var instance = this;

			instance._sendAnswerMsg(true);
			instance.setState(Liferay.Chat.WebRtcConversation.State.ANSWERED);
		},

		// state: DENYINGCALL
		_onStateDenyingCall: function() {
			var instance = this;

			instance._sendAnswerMsg(false);
			instance.setState(Liferay.Chat.WebRtcConversation.State.STOPPED);
		},

		// state: STOPPING
		_onStateStopping: function() {
			var instance = this;
		},

		// state: DELETING
		_onStateDeleting: function() {
			var instance = this;
		},

		// state: STOPPED
		_onStateStopped: function() {
			var instance = this;

			instance._resetWebRtcState();
		},

		// state: DELETED
		_onStateDeleted: function() {
			var instance = this;
		},

		/**
		 * Gets the current conversation state.
		 *
		 * @return Current conversation state
		 */
		getState: function() {
			return this._currentState;
		},

		/**
		 * Sets the current conversation state.
		 *
		 * @param state New conversation state
		 */
		setState: function(state) {
			var instance = this;

			Liferay.Chat.WebRtcManager.debugMsg('{' + instance._currentState + ' -> ' + state + '}');

			// we first set it
			instance._currentState = state;

			// then we notify the host
			instance._cb.onStateChange(state);

			// then we notify the WebRTC manager
			Liferay.Chat.WebRtcManager.onConversationStateChange();

			// then we notify ourself
			instance._onStateChange();
		},

		// event: internal conversation state has changed
		_onStateChange: function() {
			var instance = this;
			var State = Liferay.Chat.WebRtcConversation.State;

			switch (instance.getState()) {
				case State.STOPPED:
					instance._onStateStopped();
					break;

				case State.DELETED:
					instance._onStateDeleted();
					break;

				case State.CALLING:
					instance._onStateCalling();
					break;

				case State.CALLED:
					instance._onStateCalled();
					break;

				case State.GOTCALL:
					instance._onStateGotCall();
					break;

				case State.ACCEPTINGCALL:
					instance._onStateAcceptingCall();
					break;

				case State.DENYINGCALL:
					instance._onStateDenyingCall();
					break;

				case State.GOTANSWER:
					instance._onStateGotAnswer();
					break;

				case State.STOPPING:
					instance._onStateStopping();
					break;

				case State.DELETING:
					instance._onStateDeleting();
					break;
			}
		},

		/**
		 * Resets the WebRTC state variables. This has nothing to do
		 * with the conversation state, although it's usually going to
		 * done when the conversation is STOPPED.
		 */
		_resetWebRtcState: function() {
			var instance = this;

			// peer connection for this conversation
			instance._pc = null;

			// client user is not the caller
			instance._isCaller = false;

			// ICE candidate buffer
			instance._iceCandidatesBuffer = [];

			// accept ICE candidates
			instance._acceptIceCandidates = false;
		},

		/**
		 * Attaches a stream to a media element.
		 *
		 * @param elem Media DOM element
		 * @param stream Stream
		 */
		_setVideoStream: function(elem, stream) {
			var instance = this;

			Liferay.Chat.WebRtcManager.getWebRtcCompat().attachMediaStream(elem, stream);
		},

		/**
		 * Sets the remote video element stream.
		 *
		 * @param stream Remote stream
		 */
		_setRemoteVideoStream: function(stream) {
			this._setVideoStream(this._remoteVideoDomElem, stream);
		},

		/**
		 * Sets the local video element stream.
		 */
		_setLocalVideoStream: function() {
			var instance = this;
			var localStream = Liferay.Chat.WebRtcManager.getLocalStream();

			if (localStream) {
				instance._setVideoStream(instance._localVideoDomElem, localStream);
			}
		},

		/**
		 * Gets the remote user ID.
		 *
		 * @return Remote user ID
		 */
		getToUserId: function() {
			return this._userId;
		},

		/**
		 * Checks if the remote user is available for WebRTC.
		 *
		 * @return true if remote user is available for WebRTC
		 */
		_isUserWebRtcAvailable: function() {
			return Liferay.Chat.WebRtcManager.isUserWebRtcAvailable(this.getToUserId());
		},

		// event: lost connection with other peer
		_onLostConnection: function() {
			var instance = this;

			Liferay.Chat.WebRtcManager.debugMsg('event: lost logical connection with remote peer');
			if (instance._pc) {
				instance._pc.close();
				instance._pc = null;
			}
			switch (instance.getState()) {
				case Liferay.Chat.WebRtcConversation.State.DELETING:
					instance.setState(Liferay.Chat.WebRtcConversation.State.DELETED);
					break;

				default:
					instance.setState(Liferay.Chat.WebRtcConversation.State.STOPPED);
					break;
			}
		},

		// event: error from server
		onError: function(msg) {
			var instance = this;

			instance._cb.onWebRtcEvent();
			switch (msg.id) {
				case 'existing_conn':
					break;

				case 'unavailable_user':
				case 'invalid_state':
				case 'cannot_answer':
					Liferay.Chat.WebRtcManager.doError('error from server: "' + msg.id + '"');
					instance.setState(Liferay.Chat.WebRtcConversation.State.STOPPED);
					break;
			}
		},

		// event: got new status from server
		onMsgGotStatus: function(msg) {
			switch (msg.status) {
			case 'lost':
				//this.chatMsg('<strong>lost connection</strong> with user #' + msg.remoteUserId);
				this._onLostConnection();
				break;

			default:
				//this.chatMsg('<strong>unknown connection status</strong>: ' + msg.status);
			}
		},

		// event: got a (human) call
		onMsgGotCall: function() {
			var instance = this;

			switch (instance.getState()) {
				case Liferay.Chat.WebRtcConversation.State.STOPPED:
				case Liferay.Chat.WebRtcConversation.State.STOPPING:
				case Liferay.Chat.WebRtcConversation.State.DELETED:
				case Liferay.Chat.WebRtcConversation.State.DELETING:
					break;

				case Liferay.Chat.WebRtcConversation.State.CALLED:
				case Liferay.Chat.WebRtcConversation.State.CALLING:
					instance.setState(Liferay.Chat.WebRtcConversation.State.GOTCALL);
					break;

				case Liferay.Chat.WebRtcConversation.State.CALLINGWAITING:
					instance.setState(Liferay.Chat.WebRtcConversation.State.GOTCALLWAITING);
					break;

				default:
					// error: wrong state for an incoming call, so deny it
					instance.setState(Liferay.Chat.WebRtcConversation.State.DENYINGCALL);
					return;
			}

			/* We're receiving a call from this user. Either we're already stopped,
			 * which is all good, or we already called the user ourselves. In this
			 * case, we switch to a callee instead of a caller.
			 */
			instance.setState(Liferay.Chat.WebRtcConversation.State.GOTCALLWAITING);
		},

		// event: got a (human) answer
		onMsgGotAnswer: function(msg) {
			var instance = this;

			if (instance.getState() != Liferay.Chat.WebRtcConversation.State.CALLED) {
				return;
			}
			if (msg.accept) {
				instance.setState(Liferay.Chat.WebRtcConversation.State.GOTANSWER);
			} else {
				instance.setState(Liferay.Chat.WebRtcConversation.State.STOPPED);
			}
		},

		// event: new ICE candidate from server
		onMsgNewIceCandidate: function(ice) {
			var instance = this;

			if (!instance._isWebRtcStarted()) {
				return;
			}
			var rtcIce = new RTCIceCandidate({
				sdpMLineIndex: ice.sdpMLineIndex,
				candidate: ice.candidate
			});
			if (!instance._pc || !instance._acceptIceCandidates) {
				instance._iceCandidatesBuffer.push(rtcIce);
			} else {
				instance._addIceCandidate(rtcIce);
			}
		},

		// event: new SDP description from server
		onMsgNewSdp: function(desc) {
			var instance = this;

			if (!instance._isWebRtcStarted()) {
				return;
			}
			if (instance._isCaller) {
				instance._webRtcCompleteOffer(desc);
			} else {
				instance._webRtcAnswer(desc);
			}
		},

		// event: conversation deleted
		onDelete: function() {
			var instance = this;
			var State = Liferay.Chat.WebRtcConversation.State;

			Liferay.Chat.WebRtcManager.debugMsg('event: conversation deleted');

			// so some appropriate action
			switch (instance.getState()) {
				case State.CALLED:
				case State.ACCEPTINGCALL:
				case State.ANSWERED:
				case State.GOTANSWER:
				case State.CONNECTED:
					// hang up current call
					instance.setState(Liferay.Chat.WebRtcConversation.State.DELETING);
					instance._sendHangUpMsg();
					break;

				case State.STOPPING:
					// transform STOPPING into DELETING
					instance.setState(Liferay.Chat.WebRtcConversation.State.DELETING);
					break;

				case State.GOTCALL:
				case State.GOTCALLWAITING:
				case State.DENYINGCALL:
					// deny current call
					instance.setState(Liferay.Chat.WebRtcConversation.State.DELETED);
					instance._sendAnswerMsg(false);
					break;

				default:
					instance.setState(Liferay.Chat.WebRtcConversation.State.DELETED);
					break;
			}
		},

		// event: user pressed "call" button
		onPressCall: function() {
			var instance = this;

			Liferay.Chat.WebRtcManager.debugMsg('event: user pressed "call"');
			if (instance.getState() != Liferay.Chat.WebRtcConversation.State.STOPPED) {
				Liferay.Chat.WebRtcManager.doError('wrong state "' + instance.getState() + ' to "call"');
				return;
			}
			if (!instance._isUserWebRtcAvailable()) {
				Liferay.Chat.WebRtcManager.doError('remote peer not available for WebRTC to "call"');
			}
			instance.setState(Liferay.Chat.WebRtcConversation.State.CALLINGWAITING);
		},

		// event: user pressed "accept" button
		onPressAccept: function() {
			var instance = this;

			Liferay.Chat.WebRtcManager.debugMsg('event: user pressed "accept"');
			if (instance.getState() != Liferay.Chat.WebRtcConversation.State.GOTCALL &&
				instance.getState() != Liferay.Chat.WebRtcConversation.State.GOTCALLWAITING) {
				Liferay.Chat.WebRtcManager.doError('wrong state "' + instance.getState() + ' to "accept"');
				return;
			}
			if (!instance._isUserWebRtcAvailable()) {
				instance.setState(Liferay.Chat.WebRtcConversation.State.DENYINGCALL);
				Liferay.Chat.WebRtcManager.doError('remote peer not available for WebRTC to "accept"');
				return;
			}
			instance.setState(Liferay.Chat.WebRtcConversation.State.ACCEPTINGCALL);
		},

		// event: user pressed "hang up" button
		onPressHangUp: function() {
			var instance = this;
			var State = Liferay.Chat.WebRtcConversation.State;

			Liferay.Chat.WebRtcManager.debugMsg('event: user pressed "hang up"');
			switch (instance.getState()) {
				case State.GOTCALL:
				case State.GOTCALLWAITING:
					instance.setState(Liferay.Chat.WebRtcConversation.State.DENYINGCALL);
					break;

				case State.CALLED:
				case State.CALLING:
				case State.ANSWERED:
				case State.ACCEPTINGCALL:
				case State.DENYINGCALL:
				case State.GOTANSWER:
				case State.CONNECTED:
					instance._sendHangUpMsg();
					instance.setState(Liferay.Chat.WebRtcConversation.State.STOPPING);
					break;

				case State.CALLINGWAITING:
					instance.setState(Liferay.Chat.WebRtcConversation.State.STOPPED);
					break;

				case State.STOPPING:
				case State.STOPPED:
				case State.DELETING:
				case State.DELETED:
					// DO NOTHING
					break;
			}
		},

		/**
		 * Sends a "hang up" message to remote peer.
		 */
		_sendHangUpMsg: function() {
			var instance = this;
			var userId = instance.getToUserId();
			var msgType = 'hangUp';
			var msg = {
				'dstUserId': userId
			};

			Liferay.Chat.WebRtcManager.sendMsg(msgType, msg);
		},

		/**
		 * Sends a "call" message to remote peer.
		 */
		_sendCallMsg: function() {
			var instance = this;
			var userId = instance.getToUserId();

			Liferay.Chat.WebRtcManager.sendMsg('call', {
				'dstUserId': userId
			});
		},

		/**
		 * Sends an "answer" message to remote peer.
		 *
		 * @param accept true to accept the call
		 */
		_sendAnswerMsg: function(accept) {
			var instance = this;
			var userId = instance.getToUserId();
			var msgType = 'answer';
			var msg = {
				'dstUserId': userId,
				'accept': accept
			};

			Liferay.Chat.WebRtcManager.sendMsg(msgType, msg);
		},

		/**
		 * Sends an SDP description message to remote peer.
		 *
		 * @param desc SDP description object
		 */
		_sendSdpMsg: function(desc) {
			var instance = this;
			var jsonDesc = JSON.stringify(desc);
			var msgType = 'sdp';
			var msg = {
				'dstUserId': instance.getToUserId(),
				'sdp': jsonDesc
			};

			Liferay.Chat.WebRtcManager.sendMsg(msgType, msg);
		},

		/**
		 * Sends a trickle ICE candidate message to remote peer.
		 *
		 * @param ice ICE candidate object
		 */
		_sendIceMsg: function(ice) {
			var instance = this;
			var jsonIce = JSON.stringify(ice);
			var msgType = 'ice';
			var msg = {
				'dstUserId': instance.getToUserId(),
				'ice': jsonIce
			};

			Liferay.Chat.WebRtcManager.sendMsg(msgType, msg);
		},

		/**
		 * Configures the conversation peer connection object.
		 */
		_configurePC: function() {
			var instance = this;
			var RTCPC = Liferay.Chat.WebRtcManager.getWebRtcCompat().peerconnection;

			instance._pc = new RTCPC({
				'iceServers': instance._iceServers
			}, Liferay.Chat.WebRtcManager.getWebRtcCompat().pc_constraints);
			instance._pc.addStream(Liferay.Chat.WebRtcManager.getLocalStream());
			instance._pc.onicecandidate = function(e) {
				instance._onIceCandidate(e);
			};
			instance._pc.onaddstream = function(e) {
				instance._onAddStream(e);
			}
		},

		// event: new local ICE candidate ready
		_onIceCandidate: function(e) {
			var instance = this;

			if (!instance._pc || !e || !e.candidate) {
				return;
			}
			Liferay.Chat.WebRtcManager.debugMsg('new local ICE candidate ready');
			instance._sendIceMsg(e.candidate);
		},

		// event: remote stream added to conversation peer connection
		_onAddStream: function(e) {
			var instance = this;

			if (!instance._isWebRtcStarted()) {
				return;
			}
			if (!e) {
				return;
			}

			Liferay.Chat.WebRtcManager.debugMsg('added remote stream');
			instance._setRemoteVideoStream(e.stream);
			instance.setState(Liferay.Chat.WebRtcConversation.State.CONNECTED);
		},

		/**
		 * Checks if the remote stream is flowing (playing).
		 *
		 * @return true if audio/video streams are flowing (visible and playing)
		 */
		isRemoteStreamFlowing: function() {
			var remoteVideo = this._remoteVideoDomElem;
			var flowing = !(remoteVideo.readyState <= HTMLMediaElement.HAVE_CURRENT_DATA ||
					remoteVideo.paused || remoteVideo.currentTime <= 0);

			return flowing;
		},

		/**
		 * Checks if the WebRTC signalling process is going started or done.
		 *
		 * @return true if WebRTC signalling process started or done
		 */
		_isWebRtcStarted: function() {
			var instance = this;

			return instance.getState() == Liferay.Chat.WebRtcConversation.State.ANSWERED ||
				instance.getState() == Liferay.Chat.WebRtcConversation.State.GOTANSWER ||
				instance.getState() == Liferay.Chat.WebRtcConversation.State.CONNECTED;
		},

		/**
		 * Creates a WebRTC offer.
		 */
		_webRtcOffer: function() {
			var instance = this;

			instance._configurePC();
			if (!instance._pc) {
				Liferay.Chat.WebRtcManager.doError('when trying to create WebRTC offer: no peer connection available');
				return;
			}
			this._pc.createOffer(function(desc) {
				desc.sdp = preferOpus(desc.sdp);
				Liferay.Chat.WebRtcManager.debugMsg('generated SDP offer');
				instance._pc.setLocalDescription(desc);
				instance._sendSdpMsg(desc);
				instance._acceptIceCandidates = true;
			}, null, {
				'mandatory': {
					'OfferToReceiveAudio': true,
					'OfferToReceiveVideo': true
				},
				'optional': []
			});
		},

		/**
		 * Completes a WebRTC offer after receiving a correct, positive answer.
		 *
		 * @param desc SDP description of answer
		 */
		_webRtcCompleteOffer: function(desc) {
			var instance = this;

			if (!instance._pc) {
				Liferay.Chat.WebRtcManager.doError('when trying to complete WebRTC offer: no peer connection available');
				return;
			}
			instance._flushIceCandidatesBuffer();
			instance._pc.setRemoteDescription(new RTCSessionDescription(desc));
		},

		/**
		 * Creates a WebRTC answer after receiving a correct offer.
		 *
		 * @param desc SDP description of offer
		 */
		_webRtcAnswer: function(desc) {
			var instance = this;

			instance._configurePC();
			if (!instance._pc) {
				Liferay.Chat.WebRtcManager.doError('when trying to create WebRTC answer: no peer connection available');
				return;
			}
			var remoteDesc = new RTCSessionDescription(desc);
			instance._pc.setRemoteDescription(remoteDesc);
			instance._pc.createAnswer(function(desc) {
				desc.sdp = preferOpus(desc.sdp);
				Liferay.Chat.WebRtcManager.debugMsg('generated SDP answer');
				instance._pc.setLocalDescription(desc);
				instance._sendSdpMsg(desc);
				instance._acceptIceCandidates = true;
				instance._flushIceCandidatesBuffer();
			}, null, {
				'mandatory': {
					'OfferToReceiveAudio': true,
					'OfferToReceiveVideo': true
				}
			});
		},

		/**
		 * Initializes the WebRTC calling process.
		 */
		_doWebRtcCall: function() {
			var instance = this;

			if (!instance._isWebRtcStarted()) {
				return;
			}
			if (!instance._isCaller) {
				Liferay.Chat.WebRtcManager.doError('cannot call with WebRTC because we\'re not the original caller');
				return;
			}
			instance._webRtcOffer();
		},

		/**
		 * Tries to add an ICE candidate to the conversation peer connection.
		 *
		 * @param ice ICE candidate to add
		 */
		_addIceCandidate: function(ice) {
			var instance = this;

			if (!instance._acceptIceCandidates) {
				Liferay.Chat.WebRtcManager.doError('cannot add following ICE candidate to peer connection');
				Liferay.Chat.WebRtcManager.debugObj(ice);
				return;
			}
			Liferay.Chat.WebRtcManager.debugMsg('adding remote ICE candidate to peer connection');
			instance._pc.addIceCandidate(ice);
		},

		/**
		 * Flushes the ICE candidates buffer (adds them all).
		 */
		_flushIceCandidatesBuffer: function() {
			var instance = this;

			if (!instance._isWebRtcStarted()) {
				return;
			}

			Liferay.Chat.WebRtcManager.debugMsg('flushing ICE candidates buffer (length=' + instance._iceCandidatesBuffer.length + ')');
			for (var i in instance._iceCandidatesBuffer) {
				instance._addIceCandidate(instance._iceCandidatesBuffer[i]);
			}
			instance._iceCandidatesBuffer.length = 0;
		}
	};
});

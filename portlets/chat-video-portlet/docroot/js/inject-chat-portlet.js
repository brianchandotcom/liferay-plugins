;(function(A) {
	Liferay.namespace('Chat');

	Liferay.Chat.Video = {
		init: function(chatManager) {
			var instance = this;

			// save chat manager reference
			instance._chatManager = chatManager;

			// read our portlet ID (injected in view.jsp)
			instance._portletId = A.one('#chat-video-portlet-id').val();

			// increased polling rate
			instance._increasedPollingRate = false;
			instance._increasedPollingRateDelayMs = 500;
			instance._increasedPollingCountMs = 0;

			// add poller listener for video chat
			Liferay.Poller.addListener(instance._portletId, instance._onPollerUpdate, instance);
			Liferay.bind(
				'sessionExpired',
				function(event) {
					Liferay.Poller.removeListener(instance._portletId);
					Liferay.Poller.cancelOverriddenDelay();
				}
			);
		},

		_onPollerUpdate: function(response, chunk) {
			console.log('poller');
			console.log(response);
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

;(function(A) {
	Liferay.on(
		'chatPortletReady',
		function(event) {
			console.log('chatPortletReady');

			A.on(
				function(options) {
					console.log('Manager#init');
				},
				Liferay.Chat.Manager,
				'init'
			);
			A.on(
				function(options) {
					console.log('Manager#send');
				},
				Liferay.Chat.Conversation,
				'send'
			);
			A.on(
				function(panelName, panel) {
					console.log('Manager#_addPanel');
				},
				Liferay.Chat.Manager,
				'_addPanel'
			);
			A.on(
				function(reponse, chunkId) {
					console.log('Manager#_onPollerUpdate');
				},
				Liferay.Chat.Manager,
				'_onPollerUpdate'
			);
			A.on(
				function(buddies) {
					console.log('Manager#_updateBuddies');
				},
				Liferay.Chat.Manager,
				'_updateBuddies'
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

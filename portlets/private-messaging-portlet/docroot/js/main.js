AUI.add(
	'liferay-plugin-privatemessaging',
	function(A) {
		var Lang = A.Lang;

		var APluginIO = A.Plugin.IO;

		var STR_CLICK = 'click';

		var STR_JSON = 'json';

		var STR_POST = 'POST';

		var PrivateMessaging = A.Component.create(
			{
				ATTRS: {
					baseActionURL: {
						validator: Lang.isString
					},

					baseRenderURL: {
						validator: Lang.isString
					},

					portletId: {
						validator: Lang.isString
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._deleteMessagesButton = instance.byId('deleteMessages');
						instance._markMessagesAsReadButton = instance.byId('markMessagesAsRead');
						instance._markMessagesAsUnreadButton = instance.byId('markMessagesAsUnread');
						instance._privateMessagingContainer = instance.byId('privateMessagingContainer');
						instance._userThreadsContainer = instance.byId('userThreadsSearchContainer');

						instance._eventHandles = [];

						instance._bindUI();

						instance._namespace = config.namespace;
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					deleteMessages: function(mbThreadIds) {
						var instance = this;

						instance._sendRequest(instance._getActionURL('deleteMessages').toString(), 'delete',  mbThreadIds);
					},

					markMessagesAsRead: function(mbThreadIds) {
						var instance = this;

						instance._sendRequest(instance._getActionURL('markMessagesAsRead').toString(), 'mark-as-read', mbThreadIds);
					},

					markMessagesAsUnread: function(mbThreadIds) {
						var instance = this;

						instance._sendRequest(instance._getActionURL('markMessagesAsUnread').toString(), 'mark-as-unread', mbThreadIds);
					},

					_bindCheckAllMessages: function() {
						var instance = this;

						var checkAllNode = instance._privateMessagingContainer.one('.check-all');

						if (checkAllNode) {
							instance._eventHandles.push(
								checkAllNode.on(
									STR_CLICK,
									function(event) {
										var checkBox = event.target;

										var privateMessages = instance._privateMessagingContainer.all('input[type=checkbox]');

										privateMessages.set('checked', checkBox.get('checked'));
									},
									instance
								)
							);
						}

						if (instance._userThreadsContainer) {
							instance._eventHandles.push(
								instance._userThreadsContainer.delegate(
									STR_CLICK,
									function(event) {
										var checkBox = event.target;

										Liferay.Util.updateCheckboxValue(checkBox);

										Liferay.Util.checkAllBox(
											instance._userThreadsContainer,
											instance.get('namespace') + 'mbThreadCheckbox',
											'.check-all'
										);
									},
									'input[type=checkbox]'
								)
							);
						}
					},

					_bindCreateMessage: function() {
						var instance = this;

						var newMessageNode = instance.byId('newMessage');

						if (newMessageNode) {
							instance._eventHandles.push(
								newMessageNode.on(
									STR_CLICK,
									function() {
										instance._newMessage();
									},
									instance
								)
							);
						}
					},

					_bindDeleteMessage: function() {
						var instance = this;

						var deleteMessageNode = instance.byId('deleteMessage');

						if (deleteMessageNode) {
							instance._eventHandles.push(
								deleteMessageNode.on(
									STR_CLICK,
									function(event) {
										if (confirm(Liferay.Language.get('are-your-sure-you-want-to-delete-the-message'))) {
											var currentTarget = event.currentTarget;

											instance._sendRequest(currentTarget.getAttribute('data-delete-message-url'), 'delete');
										}
									},
									instance
								)
							);
						}
					},

					_bindDeleteMessages: function() {
						var instance = this;

						var deleteMessagesNode = instance.byId('deleteMessages');

						if (deleteMessagesNode) {
							instance._eventHandles.push(
								deleteMessagesNode.on(
									STR_CLICK,
									function(event) {
										var mbThreadIds = instance._getSelectedMessageIds();

										if (mbThreadIds.length) {
											if (confirm(Liferay.Language.get('are-your-sure-you-want-to-delete-the-selected-messages'))) {
												instance.deleteMessages(mbThreadIds);
											}
										}
									},
									instance
								)
							);
						}
					},

					_bindMarkMessageRead: function() {
						var instance = this;

						var markMessageReadNode = instance.byId('markMessageAsRead');

						if (markMessageReadNode) {
							instance._eventHandles.push(
								markMessageReadNode.on(
									STR_CLICK,
									function(event) {
										if (confirm(Liferay.Language.get('are-your-sure-you-want-to-mark-the-message-as-read'))) {
											event.preventDefault();
										}
									},
									instance
								)
							);
						}
					},

					_bindMarkMessagesRead: function() {
						var instance = this;

						var markMessagesReadNode = instance.byId('markMessagesAsRead');

						if (markMessagesReadNode) {
							instance._eventHandles.push(
								markMessagesReadNode.on(
									STR_CLICK,
									function(event) {
										event.preventDefault();

										var mbThreadIds = instance._getSelectedMessageIds();

										if (mbThreadIds.length) {
											if (confirm(Liferay.Language.get('are-your-sure-you-want-to-mark-the-selected-messages-as-read'))) {
												instance.markMessagesAsRead(mbThreadIds);
											}
										}
									},
									instance
								)
							);
						}
					},

					_bindMarkMessageUnread: function() {
						var instance = this;

						var markMessageUnread = instance.byId('markMessageAsUnread');

						if (markMessageUnread) {
							instance._eventHandles.push(
								markMessageUnread.on(
									STR_CLICK,
									function(event) {
										if (confirm(Liferay.Language.get('are-your-sure-you-want-to-mark-the-message-as-unread'))) {
											var currentTarget = event.currentTarget;

											instance._sendRequest(currentTarget.getAttribute('data-mark-as-unread-url'), 'mark-as-unread');
										}
									},
									instance
								)
							);
						}
					},

					_bindMarkMessagesUnread: function() {
						var instance = this;

						var markMessagesUnread = instance.byId('markMessagesAsUnread');

						if (markMessagesUnread) {
							instance._eventHandles.push(
								markMessagesUnread.on(
									STR_CLICK,
									function(event) {
										event.preventDefault();

										var mbThreadIds = instance._getSelectedMessageIds();

										if (mbThreadIds.length) {
											if (confirm(Liferay.Language.get('are-your-sure-you-want-to-mark-the-selected-messages-as-unread'))) {
												instance.markMessagesAsUnread(mbThreadIds);
											}
										}
									},
									instance
								)
							);
						}
					},

					_bindToggleMessageButtons: function() {
						var instance = this;

						instance._privateMessagingContainer.delegate(
							STR_CLICK,
							function() {
								var messageIds = instance._getSelectedMessageIds();

								if (messageIds.length > 0 ) {
									instance._deleteMessagesButton.show();
									instance._markMessagesAsReadButton.show();
									instance._markMessagesAsUnreadButton.show();
								}
								else {
									instance._deleteMessagesButton.hide();
									instance._markMessagesAsReadButton.hide();
									instance._markMessagesAsUnreadButton.hide();
								}
							},
							'input[type=checkbox]'
						);
					},

					_bindUI: function() {
						var instance = this;

						if (instance._privateMessagingContainer) {
							instance._bindCreateMessage();

							instance._bindDeleteMessages();

							instance._bindDeleteMessage();

							instance._bindMarkMessageRead();

							instance._bindMarkMessagesRead();

							instance._bindMarkMessageUnread();

							instance._bindMarkMessagesUnread();

							instance._bindCheckAllMessages();

							instance._bindToggleMessageButtons();
						}
					},

					_displayMessage: function(action, success, selectedMessagesCount) {
						var instance = this;

						instance._hideButtons();

						var confirmationMessage = instance.byId('confirmationMessage');

						confirmationMessage.show();

						if (success) {
							confirmationMessage.addClass('alert-success');
						}
						else {
							confirmationMessage.addClass('alert-error');
						}

						confirmationMessage.setStyle('display', 'inline');

						confirmationMessage.setHTML(instance._getConfirmationMessage(action, success, selectedMessagesCount));

						setTimeout(
							function() {
								confirmationMessage.hide();
							},
							4000
						);
					},

					_getActionURL: function(name) {
						var instance = this;

						var windowState = 'NORMAL';

						if (themeDisplay.isStateMaximized()) {
							windowState = 'MAXIMIZED';
						}

						var portletURL = new Liferay.PortletURL.createURL(instance.get('baseActionURL'));

						portletURL.setParameter('javax.portlet.action', name);
						portletURL.setPortletId(instance.get('portletId'));
						portletURL.setWindowState(windowState);

						return portletURL;
					},

					_getConfirmationMessage: function(action, success, selectedMessagesCount) {
						var key;

						if (action == 'delete') {
							key = success ? 'delete-x-successfully' : 'unable-to-delete-x';
						}
						else if (action == 'mark-as-read') {
							key = success ? 'mark-x-as-read-successfully' : 'unable-to-mark-x-as-read';
						}
						else if (action == 'mark-as-unread') {
							key = success ? 'mark-x-as-unread-successfully' : 'unable-to-mark-x-as-unread';
						}

						var param = selectedMessagesCount + ' message' + (selectedMessagesCount > 1 ? 's' : '');

						return Lang.sub(Liferay.Language.get(key), [param]);
					},

					_getSelectedMessageIds: function() {
						var instance = this;

						var mbThreadIds = [];

						instance._userThreadsContainer.all('input[type=checkbox]:checked').each(
							function(item, index, collection) {
								var mbThreadId = item.getAttribute('data-mbThreadId');

								if (mbThreadId) {
									mbThreadIds.push(mbThreadId);
								}
							}
						);

						return mbThreadIds;
					},

					_hideButtons: function() {
						var instance = this;

						if (instance._deleteMessagesButton) {
							instance._deleteMessagesButton.hide();
							instance._markMessagesAsReadButton.hide();
							instance._markMessagesAsUnreadButton.hide();
						}

						instance._deleteMessageButton = instance.byId('deleteMessage');
						instance._markMessageAsUnreadButton = instance.byId('markMessageAsUnread');

						if (instance._deleteMessageButton) {
							instance._deleteMessageButton.hide();
							instance._markMessageAsUnreadButton.hide();
						}
					},

					_newMessage: function(mbThreadId) {
						var instance = this;

						var redirectURL = new Liferay.PortletURL.createURL(instance.get('baseRenderURL'));

						redirectURL.setWindowState('NORMAL');

						var portletURL =  new Liferay.PortletURL.createURL(instance.get('baseRenderURL'));

						portletURL.setPortletId(instance.get('portletId'));
						portletURL.setWindowState('POP_UP');

						portletURL.setParameter('mvcPath', '/new_message.jsp');
						portletURL.setParameter('redirect', redirectURL.toString());
						portletURL.setParameter('mbThreadId', mbThreadId);

						var messageDialog = Liferay.Util.openWindow(
							{
								dialog: {
									after: {
										destroy: function(event) {
											document.location.href = redirectURL.toString();
										}
									},
									centered: true,
									constrain: true,
									cssClass: 'private-messaging-portlet',
									destroyOnHide: true,
									height: 600,
									modal: true,
									plugins: [Liferay.WidgetZIndex],
									width: 600
								},
								id: instance._namespace + 'Dialog',
								title: Liferay.Language.get('new-message'),
								uri: portletURL.toString()
							}
						);
					},

					_sendRequest: function(request, action, mbThreadIds) {
						var instance = this;

						var request = mbThreadIds ? Liferay.Util.addParams(instance._namespace + 'mbThreadIds=' + mbThreadIds, request) : request;

						A.io.request(
							request,
							{
								after: {
									success: function() {
										var response = this.get('responseData');

										instance._displayMessage(action, response.success, ((mbThreadIds && mbThreadIds.length > 1) ? mbThreadIds.length : 1));

										A.config.win.location = themeDisplay.getLayoutURL();
									}
								},
								dataType: STR_JSON
							}
						);
					}
				}
			}
		);

		Liferay.PrivateMessaging = PrivateMessaging;
	},
	'',
	{
		requires: ['aui-base', 'aui-io-deprecated', 'aui-modal', 'liferay-node', 'liferay-portlet-base', 'liferay-portlet-url', 'liferay-widget-zindex']
	}
);
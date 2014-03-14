AUI.add(
	'liferay-scheduler',
	function(A) {
		var AArray = A.Array;
		var AObject = A.Object;
		var DateMath = A.DataType.DateMath;
		var Lang = A.Lang;

		var RecurrenceUtil = Liferay.RecurrenceUtil;
		var Workflow = Liferay.Workflow;

		var isBoolean = Lang.isBoolean;
		var isDate = Lang.isDate;
		var isFunction = Lang.isFunction;
		var isObject = Lang.isObject;
		var isValue = Lang.isValue;

		var toInitialCap = A.cached(
			function(str) {
				return str.substring(0, 1).toUpperCase() + str.substring(1);
			}
		);

		var toInt = function(value) {
			return Lang.toInt(value, 10, 0);
		};

		var STR_BLANK = '';

		var STR_COMMA_SPACE = ', ';

		var STR_DASH = '-';

		var STR_SPACE = ' ';

		var TPL_CALENDAR_BOOKINGS_URL = '{calendarBookingsURL}&{portletNamespace}calendarIds={calendarIds}&{portletNamespace}startTime={startTime}&{portletNamespace}endTime={endTime}&{portletNamespace}statuses={statuses}';

		var TPL_INVITEES_URL = '{inviteesURL}&{portletNamespace}parentCalendarBookingId={calendarBookingId}';

		var TPL_RENDERING_RULES_URL = '{renderingRulesURL}&{portletNamespace}calendarIds={calendarIds}&{portletNamespace}startTime={startTime}&{portletNamespace}endTime={endTime}&{portletNamespace}ruleName={ruleName}';

		var CONTROLS_NODE = 'controlsNode';

		var ICON_ADD_EVENT_NODE = 'iconAddEventNode';

		var TPL_ICON_ADD_EVENT_NODE = '<div class="btn-group">' +
										'<button type="button" class="btn btn-primary calendar-add-event-btn">' +
											Liferay.Language.get('add-calendar-booking') +
										'</div>' +
									 '</button>';

		var TPL_MESSAGE_UPDATE_ALL_INVITED = '<p class="calendar-portlet-confirmation-text">' +
												Liferay.Language.get('invited-users-will-be-notified') +
											'</p>';

		var COMPANY_ID = toInt(themeDisplay.getCompanyId());

		var USER_ID = toInt(themeDisplay.getUserId());

		var Time = {
			DAY: 86400000,
			HOUR: 3600000,
			MINUTE: 60000,
			SECOND: 1000,
			WEEK: 604800000,

			TIME_DESC: ['weeks', 'days', 'hours', 'minutes'],

			getDescription: function(milliseconds) {
				var instance = this;

				var desc = 'minutes';
				var value = 0;

				if (milliseconds > 0) {
					AArray.some(
						[ Time.WEEK, Time.DAY, Time.HOUR, Time.MINUTE ],
						function(item, index, collection) {
							value = milliseconds / item;
							desc = Time.TIME_DESC[index];

							return (milliseconds % item === 0);
						}
					);
				}

				return {
					desc: desc,
					value: value
				};
			}
		};

		Liferay.Time = Time;

		var CalendarUtil = {
			CALENDAR_BOOKINGS_URL: null,
			INVITEES_URL: null,
			INVOKER_URL: themeDisplay.getPathContext() + '/api/jsonws/invoke',
			NOTIFICATION_DEFAULT_TYPE: 'email',
			PORTLET_NAMESPACE: STR_BLANK,
			RENDERING_RULES_URL: null,
			USER_TIME_ZONE: 'UTC',

			availableCalendars: {},
			visibleCalendars: {},

			addEvent: function(schedulerEvent, success) {
				var instance = this;

				var endDate = schedulerEvent.get('endDate');
				var startDate = schedulerEvent.get('startDate');

				instance.invokeService(
					{
						'/calendar-portlet.calendarbooking/add-calendar-booking': {
							allDay: schedulerEvent.get('allDay'),
							calendarId: schedulerEvent.get('calendarId'),
							childCalendarIds: STR_BLANK,
							descriptionMap: instance.getLocalizationMap(schedulerEvent.get('description')),
							endTimeDay: endDate.getDate(),
							endTimeHour: endDate.getHours(),
							endTimeMinute: endDate.getMinutes(),
							endTimeMonth: endDate.getMonth(),
							endTimeYear: endDate.getFullYear(),
							firstReminder: schedulerEvent.get('firstReminder'),
							firstReminderType: schedulerEvent.get('firstReminderType'),
							location: schedulerEvent.get('location'),
							parentCalendarBookingId: schedulerEvent.get('parentCalendarBookingId'),
							recurrence: schedulerEvent.get('recurrence'),
							secondReminder: schedulerEvent.get('secondReminder'),
							secondReminderType: schedulerEvent.get('secondReminderType'),
							startTimeDay: startDate.getDate(),
							startTimeHour: startDate.getHours(),
							startTimeMinute: startDate.getMinutes(),
							startTimeMonth: startDate.getMonth(),
							startTimeYear: startDate.getFullYear(),
							titleMap: instance.getLocalizationMap(Liferay.Util.unescapeHTML(schedulerEvent.get('content'))),
							timeZoneId: instance.USER_TIME_ZONE
						}
					},
					{
						failure: function() {
							instance.destroyEvent(schedulerEvent);
						},

						start: function() {
							schedulerEvent.set(
								'loading',
								true,
								{
									silent: true
								}
							);
						},

						success: function(data) {
							schedulerEvent.set(
								'loading',
								false,
								{
									silent: true
								}
							);

							if (data) {
								if (data.exception) {
									instance.destroyEvent(schedulerEvent);
								}
								else {
									instance.setEventAttrs(schedulerEvent, data);

									if (success) {
										success.call(instance, data);
									}
								}
							}
						}
					}
				);
			},

			adjustSchedulerEventDisplayTime: function(schedulerEvent) {
				var instance = this;

				var allDay = schedulerEvent.get('allDay');

				var timeAdjuster = instance.toLocalTime;

				if (allDay) {
					timeAdjuster = instance.toLocalTimeWithoutUserTimeZone;
				}

				var endDate = schedulerEvent.get('endDate');
				var startDate = schedulerEvent.get('startDate');

				schedulerEvent.setAttrs(
					{
						endDate: timeAdjuster.call(instance, endDate),
						startDate: timeAdjuster.call(instance, startDate)
					},
					{
						silent: true
					}
				);
			},

			createCalendarsAutoComplete: function(resourceURL, input, afterSelectFn) {
				var instance = this;

				input.plug(
					A.Plugin.AutoComplete,
					{
						activateFirstItem: true,
						after: {
							select: afterSelectFn
						},
						maxResults: 20,
						requestTemplate: '&' + instance.PORTLET_NAMESPACE + 'keywords={query}',
						resultFilters: function(query, results) {
							return AArray.filter(
								results,
								function(item, index, collection) {
									return !instance.availableCalendars[item.raw.calendarId];
								}
							);
						},
						resultFormatter: function(query, results) {
							return AArray.map(
								results,
								function(result) {
									var calendar = result.raw;
									var name = calendar.name;
									var calendarResourceName = calendar.calendarResourceName;

									if (name !== calendarResourceName) {
										name = [calendarResourceName, STR_DASH, name].join(STR_SPACE);
									}

									return A.Highlight.words(name, query);
								}
							);
						},
						resultHighlighter: 'wordMatch',
						resultTextLocator: 'calendarResourceName',
						source: resourceURL
					}
				);
			},

			createSchedulerEvent: function(calendarBooking) {
				var instance = this;

				var endDate = new Date(calendarBooking.endTimeYear, calendarBooking.endTimeMonth, calendarBooking.endTimeDay, calendarBooking.endTimeHour, calendarBooking.endTimeMinute);
				var startDate = new Date(calendarBooking.startTimeYear, calendarBooking.startTimeMonth, calendarBooking.startTimeDay, calendarBooking.startTimeHour, calendarBooking.startTimeMinute);

				var schedulerEvent = new Liferay.SchedulerEvent(
					{
						allDay: calendarBooking.allDay,
						calendarBookingId: calendarBooking.calendarBookingId,
						calendarId: calendarBooking.calendarId,
						content: calendarBooking.title,
						description: calendarBooking.description,
						endDate: endDate.getTime(),
						firstReminder: calendarBooking.firstReminder,
						firstReminderType: calendarBooking.firstReminderType,
						location: calendarBooking.location,
						parentCalendarBookingId: calendarBooking.parentCalendarBookingId,
						recurrence: calendarBooking.recurrence,
						secondReminder: calendarBooking.secondReminder,
						secondReminderType: calendarBooking.secondReminderType,
						startDate: startDate.getTime(),
						status: calendarBooking.status
					}
				);

				return schedulerEvent;
			},

			deleteEvent: function(schedulerEvent, success) {
				var instance = this;

				instance.invokeService(
					{
						'/calendar-portlet.calendarbooking/move-calendar-booking-to-trash': {
							calendarBookingId: schedulerEvent.get('calendarBookingId')
						}
					},
					{
						success: function(data) {
							if (success) {
								success.call(instance, data);
							}
						}
					}
				);
			},

			deleteEventInstance: function(schedulerEvent, allFollowing, success) {
				var instance = this;

				instance.invokeService(
					{
						'/calendar-portlet.calendarbooking/delete-calendar-booking-instance': {
							allFollowing: allFollowing,
							calendarBookingId: schedulerEvent.get('calendarBookingId'),
							startTime: CalendarUtil.toUTC(schedulerEvent.get('startDate')).getTime()
						}
					},
					{
						success: function(data) {
							if (success) {
								success.call(instance, data);
							}
						}
					}
				);
			},

			destroyEvent: function(schedulerEvent) {
				var instance = this;

				var scheduler = schedulerEvent.get('scheduler');

				scheduler.removeEvents(schedulerEvent);

				scheduler.syncEventsUI();
			},

			getCalendarBookingInvitees: function(calendarBookingId, callback) {
				var instance = this;

				var inviteesURL = Lang.sub(
					TPL_INVITEES_URL,
					{
						calendarBookingId: calendarBookingId,
						inviteesURL: instance.INVITEES_URL,
						portletNamespace: instance.PORTLET_NAMESPACE
					}
				);

				A.io.request(
					inviteesURL,
					{
						dataType: 'json',
						on: {
							success: function() {
								callback(this.get('responseData'));
							}
						}
					}
				);
			},

			getCalendarName: function(name, calendarResourceName) {
				var instance = this;

				if (name !== calendarResourceName) {
					name = [calendarResourceName, STR_DASH, name].join(STR_SPACE);
				}

				return name;
			},

			getCalendarRenderingRules: function(calendarIds, startDate, endDate, ruleName, callback) {
				var instance = this;

				var renderingRulesURL = Lang.sub(
					TPL_RENDERING_RULES_URL,
					{
						calendarIds: calendarIds.join(),
						endTime: endDate.getTime(),
						portletNamespace: instance.PORTLET_NAMESPACE,
						renderingRulesURL: instance.RENDERING_RULES_URL,
						ruleName: ruleName,
						startTime: startDate.getTime()
					}
				);

				A.io.request(
					renderingRulesURL,
					{
						dataType: 'json',
						on: {
							success: function() {
								callback(this.get('responseData'));
							}
						}
					}
				);
			},

			getDatesList: function(startDate, total) {
				var instance = this;

				var ADate = A.Date;

				var output = [];

				if (ADate.isValidDate(startDate)) {
					for (var i = 0; i < total; i++) {
						output.push(ADate.addDays(startDate, i));
					}
				}

				return output;
			},

			getDefaultUserCalendar: function() {
				var instance = this;

				return instance.availableCalendars[CalendarUtil.DEFAULT_USER_CALENDAR_ID];
			},

			getEvent: function(calendarBookingId, success, failure) {
				var instance = this;

				instance.invokeService(
					{
						'/calendar-portlet.calendarbooking/get-calendar-booking': {
							calendarBookingId: calendarBookingId
						}
					},
					{
						failure: failure,
						success: success
					}
				);
			},

			getEvents: function(startDate, endDate, status, success, failure) {
				var instance = this;

				var calendarIds = AObject.keys(instance.availableCalendars);

				var calendarBookingsURL = Lang.sub(
					TPL_CALENDAR_BOOKINGS_URL,
					{
						calendarBookingsURL: instance.CALENDAR_BOOKINGS_URL,
						calendarIds: calendarIds.join(','),
						endTime: endDate.getTime(),
						portletNamespace: instance.PORTLET_NAMESPACE,
						startTime: startDate.getTime(),
						statuses: status.join(',')
					}
				);

				A.io.request(
					calendarBookingsURL,
					{
						dataType: 'json',
						on: {
							failure: function() {
								failure(this.get('responseData'));
							},
							success: function() {
								success(this.get('responseData'));
							}
						}
					}
				);
			},

			getLocalizationMap: function(value) {
				var instance = this;

				var map = {};
				map[themeDisplay.getLanguageId()] = value;

				return A.JSON.stringify(map);
			},

			hasChildCalendarBookings: function(schedulerEvent, callback) {
				var instance = this;

				instance.invokeService(
					{
						'/calendar-portlet/calendarbooking/has-child-calendar-bookings': {
							parentCalendarBookingId: schedulerEvent.get('calendarBookingId')
						}
					},
					{
						success: function() {
							callback(this.get('responseData'));
						}
					}
				);
			},

			invokeService: function(payload, callback) {
				var instance = this;

				callback = callback || {};

				A.io.request(
					instance.INVOKER_URL,
					{
						cache: false,
						data: {
							cmd: A.JSON.stringify(payload),
							p_auth: Liferay.authToken
						},
						dataType: 'json',
						on: {
							failure: callback.failure,
							start: callback.start,
							success: function(event) {
								if (callback.success) {
									var data = this.get('responseData');

									callback.success.apply(this, [data, event]);
								}
							}
						}
					}
				);
			},

			invokeTransition: function(schedulerEvent, status) {
				var instance = this;

				var scheduler = schedulerEvent.get('scheduler');

				instance.invokeService(
					{
						'/calendar-portlet.calendarbooking/invoke-transition': {
							calendarBookingId: schedulerEvent.get('calendarBookingId'),
							status: status,
							userId: USER_ID
						}
					},
					{
						start: function() {
							schedulerEvent.set(
								'loading',
								true,
								{
									silent: true
								}
							);
						},

						success: function(data) {
							schedulerEvent.set(
								'loading',
								false,
								{
									silent: true
								}
							);

							if (data && !data.exception && scheduler) {
								var eventRecorder = scheduler.get('eventRecorder');

								eventRecorder.hidePopover();

								scheduler.load();
							}
						}
					}
				);
			},

			message: function(msg) {
				var instance = this;

				A.oneNS(instance.PORTLET_NAMESPACE, '#message').html(msg);
			},

			setEventAttrs: function(schedulerEvent, data) {
				var instance = this;

				var scheduler = schedulerEvent.get('scheduler');

				var newCalendarId = data.calendarId;

				var oldCalendarId = schedulerEvent.get('calendarId');

				if (scheduler) {
					var oldCalendar = instance.availableCalendars[oldCalendarId];
					var newCalendar = instance.availableCalendars[newCalendarId];

					if (oldCalendar !== newCalendar) {
						oldCalendar.remove(schedulerEvent);
					}

					if (newCalendar) {
						newCalendar.add(schedulerEvent);
					}

					schedulerEvent.setAttrs(
						{
							calendarBookingId: data.calendarBookingId,
							calendarId: newCalendarId,
							calendarResourceId: data.calendarResourceId,
							parentCalendarBookingId: data.parentCalendarBookingId,
							recurrence: data.recurrence,
							status: data.status
						},
						{
							silent: true
						}
					);

					scheduler.syncEventsUI();
				}
			},

			syncCalendarsMap: function(calendarLists) {
				var instance = this;

				var visibleCalendars = instance.visibleCalendars = {};
				var availableCalendars = instance.availableCalendars = {};

				AArray.each(
					calendarLists,
					function(calendarList) {
						var calendars = calendarList.get('calendars');

						A.each(
							calendars,
							function(item, index, collection) {
								var calendarId = item.get('calendarId');

								availableCalendars[calendarId] = item;

								if (item.get('visible')) {
									visibleCalendars[calendarId] = item;
								}
							}
						);
					}
				);

				return availableCalendars;
			},

			toLocalTime: function(utc) {
				var instance = this;

				if (!isDate(utc)) {
					utc = new Date(utc);
				}

				return DateMath.add(utc, DateMath.MINUTES, utc.getTimezoneOffset());
			},

			toUTC: function(date) {
				var instance = this;

				if (!isDate(date)) {
					date = new Date(date);
				}

				return DateMath.subtract(date, DateMath.MINUTES, date.getTimezoneOffset());
			},

			updateEvent: function(schedulerEvent, offset, duration, success) {
				var instance = this;

				instance.invokeService(
					{
						'/calendar-portlet.calendarbooking/offset-and-duration': {
							allDay: schedulerEvent.get('allDay'),
							calendarBookingId: schedulerEvent.get('calendarBookingId'),
							calendarId: schedulerEvent.get('calendarId'),
							descriptionMap: instance.getLocalizationMap(schedulerEvent.get('description')),
							duration: duration,
							firstReminder: schedulerEvent.get('firstReminder'),
							firstReminderType: schedulerEvent.get('firstReminderType'),
							location: schedulerEvent.get('location'),
							offset: offset,
							recurrence: schedulerEvent.get('recurrence'),
							secondReminder: schedulerEvent.get('secondReminder'),
							secondReminderType: schedulerEvent.get('secondReminderType'),
							status: schedulerEvent.get('status'),
							titleMap: instance.getLocalizationMap(Liferay.Util.unescapeHTML(schedulerEvent.get('content')))
						}
					},
					{
						start: function() {
							schedulerEvent.set(
								'loading',
								true,
								{
									silent: true
								}
							);
						},

						success: function(data) {
							schedulerEvent.set(
								'loading',
								false,
								{
									silent: true
								}
							);

							if (success) {
								success.call(instance, data);
							}
						}
					}
				);
			},

			updateEventInstance: function(schedulerEvent, allFollowing, success) {
				var instance = this;

				var startDate = schedulerEvent.get('startDate');
				var endDate = schedulerEvent.get('endDate');

				instance.invokeService(
					{
						'/calendar-portlet.calendarbooking/update-calendar-booking-instance': {
							allDay: schedulerEvent.get('allDay'),
							allFollowing: allFollowing,
							calendarBookingId: schedulerEvent.get('calendarBookingId'),
							calendarId: schedulerEvent.get('calendarId'),
							descriptionMap: instance.getLocalizationMap(schedulerEvent.get('description')),
							endTimeDay: endDate.getDate(),
							endTimeHour: endDate.getHours(),
							endTimeMinute: endDate.getMinutes(),
							endTimeMonth: endDate.getMonth(),
							endTimeYear: endDate.getFullYear(),
							firstReminder: schedulerEvent.get('firstReminder'),
							firstReminderType: schedulerEvent.get('firstReminderType'),
							location: schedulerEvent.get('location'),
							recurrence: schedulerEvent.get('recurrence'),
							secondReminder: schedulerEvent.get('secondReminder'),
							secondReminderType: schedulerEvent.get('secondReminderType'),
							startTimeDay: startDate.getDate(),
							startTimeHour: startDate.getHours(),
							startTimeMinute: startDate.getMinutes(),
							startTimeMonth: startDate.getMonth(),
							startTimeYear: startDate.getFullYear(),
							status: schedulerEvent.get('status'),
							timeZoneId: instance.USER_TIME_ZONE,
							titleMap: instance.getLocalizationMap(Liferay.Util.unescapeHTML(schedulerEvent.get('content')))
						}
					},
					{
						start: function() {
							schedulerEvent.set(
								'loading',
								true,
								{
									silent: true
								}
							);
						},

						success: function(data) {
							schedulerEvent.set(
								'loading',
								false,
								{
									silent: true
								}
							);

							if (success) {
								success.call(instance, data);
							}
						}
					}
				);
			}
		};

		Liferay.CalendarUtil = CalendarUtil;

		var CalendarWorkflow = {
			STATUS_MAYBE: 9
		};

		A.mix(CalendarWorkflow, Workflow);

		Liferay.CalendarWorkflow = CalendarWorkflow;

		var SchedulerModelSync = function(config) {};

		SchedulerModelSync.prototype = {
			sync: function(action, options, callback) {
				var instance = this;

				var actionMethod = instance['_do' + toInitialCap(action)];

				if (isFunction(actionMethod)) {
					actionMethod.apply(instance, [options, callback]);
				}
			},

			_doRead: function() {
				var instance = this;

				var args = arguments;

				var callback = args[args.length - 1];

				if (isFunction(callback)) {
					callback();
				}
			}
		};

		Liferay.SchedulerModelSync = SchedulerModelSync;

		var SchedulerEvent = A.Component.create(
			{
				ATTRS: {
					calendarBookingId: {
						setter: toInt,
						value: 0
					},

					calendarId: {
						setter: toInt,
						value: 0
					},

					content: {
						getter: function(val) {
							if (val) {
								val = Liferay.Util.escapeHTML(val);
							}

							return val;
						}
					},

					description: {
						setter: String,
						validator: isValue,
						value: STR_BLANK
					},

					editingEvent: {
						validator: isBoolean,
						value: false
					},

					firstReminder: {
						setter: toInt,
						value: 0
					},

					firstReminderType: {
						setter: String,
						validator: isValue,
						value: CalendarUtil.NOTIFICATION_DEFAULT_TYPE
					},

					loading: {
						validator: isBoolean,
						value: false
					},

					location: {
						setter: String,
						validator: isValue,
						value: STR_BLANK
					},

					parentCalendarBookingId: {
						setter: toInt,
						value: 0
					},

					recurrence: {
						setter: String,
						validator: isValue,
						value: STR_BLANK
					},

					reminder: {
						getter: function() {
							var instance = this;

							return (instance.get('firstReminder') > 0) || (instance.get('secondReminder') > 0);
						}
					},

					repeated: {
						getter: 'isRecurring'
					},

					secondReminder: {
						setter: toInt,
						value: 0
					},

					secondReminderType: {
						setter: String,
						validator: isValue,
						value: CalendarUtil.NOTIFICATION_DEFAULT_TYPE
					},

					status: {
						setter: toInt,
						value: 0
					}
				},

				EXTENDS: A.SchedulerEvent,

				NAME: 'scheduler-event',

				PROPAGATE_ATTRS: A.SchedulerEvent.PROPAGATE_ATTRS.concat(['calendarBookingId', 'calendarId', 'calendarResourceId', 'parentCalendarBookingId', 'recurrence', 'status']),

				prototype: {
					eventModel: Liferay.SchedulerEvent,

					initializer: function() {
						var instance = this;

						instance._uiSetLoading(instance.get('loading'));
						instance._uiSetStartDate(instance.get('startDate'));
						instance._uiSetStatus(instance.get('status'));

						instance.on('loadingChange', instance._onLoadingChange);
						instance.on('startDateChange', instance._onStartDateChange);
						instance.on('statusChange', instance._onStatusChange);
					},

					isMasterBooking: function() {
						var instance = this;

						return (instance.get('parentCalendarBookingId') === instance.get('calendarBookingId'));
					},

					isRecurring: function() {
						var instance = this;

						return (instance.get('recurrence') !== STR_BLANK);
					},

					syncNodeColorUI: function() {
						var instance = this;

						Liferay.SchedulerEvent.superclass.syncNodeColorUI.apply(instance, arguments);

						var node = instance.get('node');
						var scheduler = instance.get('scheduler');

						if (scheduler && !instance.get('editingEvent')) {
							var activeViewName = scheduler.get('activeView').get('name');

							if ((activeViewName === 'month') && !instance.get('allDay')) {
								node.setStyles(
									{
										backgroundColor: instance.get('color'),
										border: 'none',
										color: '#111',
										padding: '0 2px'
									}
								);
							}
						}
					},

					_onLoadingChange: function(event) {
						var instance = this;

						instance._uiSetLoading(event.newVal);
					},

					_onStartDateChange: function(event) {
						var instance = this;

						instance._uiSetStartDate(event.newVal);
					},

					_onStatusChange: function(event) {
						var instance = this;

						instance._uiSetStatus(event.newVal);
					},

					_uiSetEndDate: function(val) {
						var instance = this;

						Liferay.SchedulerEvent.superclass._uiSetEndDate.apply(instance, arguments);

						var node = instance.get('node');

						node.attr('data-endDate', instance._formatDate(val, '%m/%d/%Y %I:%M %p'));
					},

					_uiSetLoading: function(val) {
						var instance = this;

						instance.get('node').toggleClass('calendar-portlet-event-loading', val);
					},

					_uiSetStartDate: function(val) {
						var instance = this;

						var node = instance.get('node');

						node.attr('data-startDate', instance._formatDate(val, '%m/%d/%Y %I:%M %p'));
					},

					_uiSetStatus: function(val) {
						var instance = this;

						var node = instance.get('node');

						node.toggleClass('calendar-portlet-event-approved', (val === CalendarWorkflow.STATUS_APPROVED));
						node.toggleClass('calendar-portlet-event-maybe', (val === CalendarWorkflow.STATUS_MAYBE));
						node.toggleClass('calendar-portlet-event-pending', (val === CalendarWorkflow.STATUS_PENDING));
					}
				}
			}
		);

		Liferay.SchedulerEvent = SchedulerEvent;

		var Calendar = A.Component.create(
			{
				ATTRS: {
					calendarId: {
						value: 0,
						setter: toInt
					},

					calendarResourceId: {
						value: 0,
						setter: toInt
					},

					calendarResourceName: {
						setter: String,
						validator: isValue,
						value: STR_BLANK
					},

					classNameId: {
						value: 0,
						setter: toInt
					},

					classPK: {
						value: 0,
						setter: toInt
					},

					defaultCalendar: {
						setter: A.DataType.Boolean.parse,
						value: false
					},

					groupId: {
						value: 0,
						setter: toInt
					},

					permissions: {
						lazyAdd: false,
						setter: function(val) {
							var instance = this;

							instance.set('disabled', !val.MANAGE_BOOKINGS);

							return val;
						},
						value: {},
						validator: isObject
					}
				},

				EXTENDS: A.SchedulerCalendar,

				NAME: 'scheduler-calendar',

				prototype: {
					getDisplayName: function() {
						var instance = this;

						var calendarResourceName = instance.get('calendarResourceName');
						var name = instance.get('name');

						return CalendarUtil.getCalendarName(name, calendarResourceName);
					},

					_afterColorChange: function(event) {
						var instance = this;

						Calendar.superclass._afterColorChange.apply(instance, arguments);

						var calendarId = instance.get('calendarId');

						var color = event.newVal;

						if (instance.get('permissions.UPDATE')) {
							CalendarUtil.invokeService(
								{
									'/calendar-portlet.calendar/update-color': {
										calendarId: calendarId,
										color: parseInt(color.substr(1), 16)
									}
								}
							);
						}
						else {
							Liferay.Store('calendar-portlet-calendar-' + calendarId + '-color', color);
						}
					},

					_afterVisibleChange: function(event) {
						var instance = this;

						Calendar.superclass._afterVisibleChange.apply(instance, arguments);

						var scheduler = instance.get('scheduler');

						scheduler.syncEventsUI();
					}
				}
			}
		);

		Liferay.SchedulerCalendar = Calendar;

		Liferay.SchedulerEvents = A.Base.create(
			'scheduler-events',
			A.SchedulerEvents,
			[Liferay.SchedulerModelSync],
			{
				getLoadEndDate: function(activeView) {
					var instance = this;

					var date = activeView.getNextDate();

					var viewName = activeView.get('name');

					if (viewName === 'agenda') {
						date = DateMath.add(date, DateMath.MONTH, 1);
					}
					else if (viewName === 'month') {
						date = DateMath.add(date, DateMath.WEEK, 1);
					}

					return CalendarUtil.toUTC(date);
				},

				getLoadStartDate: function(activeView) {
					var instance = this;

					var scheduler = activeView.get('scheduler');
					var viewName = activeView.get('name');

					var date = scheduler.get('viewDate');

					if (viewName === 'month') {
						date = DateMath.subtract(date, DateMath.WEEK, 1);
					}

					return CalendarUtil.toUTC(date);
				},

				_doRead: function(options, callback) {
					var instance = this;

					var scheduler = instance.get('scheduler');

					var activeView = scheduler.get('activeView');
					var filterCalendarBookings = scheduler.get('filterCalendarBookings');

					CalendarUtil.message(Liferay.Language.get('loading'));

					CalendarUtil.getEvents(
						instance.getLoadStartDate(activeView),
						instance.getLoadEndDate(activeView),
						[CalendarWorkflow.STATUS_APPROVED, CalendarWorkflow.STATUS_MAYBE, CalendarWorkflow.STATUS_PENDING],
						function(calendarBookings) {
							if (filterCalendarBookings) {
								calendarBookings = AArray.filter(calendarBookings, filterCalendarBookings);
							}

							callback(null, calendarBookings);
						}
					);
				}
			},
			{}
		);

		var Scheduler = A.Component.create(
			{
				ATTRS: {
					filterCalendarBookings: {
						validator: isFunction
					},

					iconAddEventNode: {
						valueFn: function() {
							return A.Node.create(TPL_ICON_ADD_EVENT_NODE);
						}
					},

					portletNamespace: {
						setter: String,
						validator: isValue,
						value: STR_BLANK
					},

					preventPersistence: {
						validator: isBoolean,
						value: false
					},

					showAddEventBtn: {
						validator: isBoolean,
						value: true
					}
				},

				EXTENDS: A.Scheduler,

				NAME: 'scheduler-base',

				prototype: {
					calendarModel: Liferay.SchedulerCalendar,
					eventModel: Liferay.SchedulerEvent,
					eventsModel: Liferay.SchedulerEvents,
					queue: null,

					renderUI: function() {
						var instance = this;

						Scheduler.superclass.renderUI.apply(this, arguments);

						var showAddEventBtn = instance.get('showAddEventBtn');

						if (showAddEventBtn) {
							instance[ICON_ADD_EVENT_NODE] = instance.get(ICON_ADD_EVENT_NODE);

							instance[CONTROLS_NODE].prepend(instance[ICON_ADD_EVENT_NODE]);

							instance[ICON_ADD_EVENT_NODE].on('click', instance._onClickAddEvent, instance);
						}
					},

					bindUI: function() {
						var instance = this;

						instance.after(
							{
								'scheduler-base:dateChange': instance._afterDateChange,
								'scheduler-event:change': instance._afterSchedulerEventChange
							}
						);

						instance.on(
							{
								'*:load': instance._onLoadSchedulerEvents,
								'scheduler-event-recorder:delete': instance._onDeleteEvent,
								'scheduler-event-recorder:save': instance._onSaveEvent
							}
						);

						Scheduler.superclass.bindUI.apply(this, arguments);
					},

					load: function() {
						var instance = this;

						var events = instance._events;

						return events.load.apply(events, arguments);
					},

					plotCalendarBookings: function(calendarBookings) {
						var instance = this;

						var events = [];
						var calendarEvents = {};

						AArray.each(
							calendarBookings,
							function(item, index, collection) {
								var calendarId = item.calendarId;

								if (!calendarEvents[calendarId]) {
									calendarEvents[calendarId] = [];
								}

								var schedulerEvent = CalendarUtil.createSchedulerEvent(item);

								schedulerEvent.set(
									'scheduler',
									instance,
									{
										silent: true
									}
								);

								events.push(schedulerEvent);
								calendarEvents[calendarId].push(schedulerEvent);
							}
						);

						instance.resetEvents(events);

						A.each(
							Liferay.CalendarUtil.availableCalendars,
							function(item, index, collection) {
								item.reset(calendarEvents[index]);
							}
						);

						if (instance.get('rendered')) {
							instance.syncEventsUI();
						}

						CalendarUtil.message(STR_BLANK);
					},

					sync: function() {
						var instance = this;

						var events = instance._events;

						return events.sync.apply(events, arguments);
					},

					_afterActiveViewChange: function(event) {
						var instance = this;

						Scheduler.superclass._afterActiveViewChange.apply(this, arguments);

						Liferay.Store('calendar-portlet-default-view', event.newVal.get('name'));

						instance.load();
					},

					_afterDateChange: function(event) {
						var instance = this;

						instance.load();
					},

					_afterSchedulerEventChange: function(event) {
						var instance = this;

						if (!instance.get('preventPersistence')) {
							var changedAttributes = event.changed;

							var persistentAttrMap = {
								calendarId: 1,
								color: 1,
								content: 1,
								endDate: 1,
								endTime: 1,
								startDate: 1,
								startTime: 1
							};

							var persist = true;

							A.each(
								changedAttributes,
								function(item, index, collection) {
									persist = AObject.owns(persistentAttrMap, index);
								}
							);

							if (persist) {
								var schedulerEvent = event.target;

								instance._updateSchedulerEvent(schedulerEvent, changedAttributes);
							}
						}
					},

					_getCalendarBookingDuration: function(schedulerEvent) {
						var instance = this;

						var duration = schedulerEvent.getSecondsDuration()*Time.SECOND;

						return duration;
					},

					_getCalendarBookingOffset: function(schedulerEvent, changedAttributes) {
						var instance = this;

						var offset = 0;

						if (changedAttributes.startDate) {
							offset = changedAttributes.startDate.newVal.getTime() - changedAttributes.startDate.prevVal.getTime();
						}

						return offset;
					},

					_hasChildCalendarBookingsPromise: function(schedulerEvent) {
						var instance = this;

						return A.Promise(
							function(resolve) {
								CalendarUtil.hasChildCalendarBookings(schedulerEvent, resolve);
							}
						);
					},

					_onClickAddEvent: function(event) {
						var instance = this;

						var recorder = instance.get('eventRecorder');

						var activeViewName = instance.get('activeView').get('name');

						var defaultUserCalendar = CalendarUtil.getDefaultUserCalendar();

						var calendarId = defaultUserCalendar.get('calendarId');

						var editCalendarBookingURL = decodeURIComponent(recorder.get('editCalendarBookingURL'));

						Liferay.Util.openWindow(
							{
								dialog: {
									after: {
										destroy: function(event) {
											instance.load();
										}
									},
									destroyOnHide: true,
									modal: true
								},
								title: Liferay.Language.get('new-calendar-booking'),
								uri: Lang.sub(
									editCalendarBookingURL,
									{
										activeView: activeViewName,
										calendarId: calendarId,
										titleCurrentValue: ''
									}
								)
							}
						);
					},

					_onDeleteEvent: function(event) {
						var instance = this;

						var schedulerEvent = event.schedulerEvent;

						var success = function() {
							instance.load();
							instance.get('eventRecorder').hidePopover();
						};

						if (schedulerEvent.isRecurring()) {
							RecurrenceUtil.openConfirmationPanel(
								'delete',
								function() {
									CalendarUtil.deleteEventInstance(schedulerEvent, false, success);
								},
								function() {
									CalendarUtil.deleteEventInstance(schedulerEvent, true, success);
								},
								function() {
									CalendarUtil.deleteEvent(schedulerEvent, success);
								}
							);
						}
						else if (schedulerEvent.isMasterBooking() && confirm(Liferay.Language.get('deleting-this-event-will-cancel-the-meeting-with-your-guests-would-you-like-to-delete'))) {
							CalendarUtil.deleteEvent(schedulerEvent, success);
						}

						event.preventDefault();
					},

					_onLoadSchedulerEvents: function(event) {
						var instance = this;

						instance.plotCalendarBookings(event.parsed);
					},

					_onSaveEvent: function(event) {
						var instance = this;

						CalendarUtil.addEvent(
							event.newSchedulerEvent,
							function() {
								instance.load();
								instance.get('eventRecorder').hidePopover();
							}
						);
					},

					_updateSchedulerEvent: function(schedulerEvent, changedAttributes) {
						var instance = this;

						var answers = {};
						var calendarBookingId = schedulerEvent.get('calendarBookingId');

						A.batch(
							schedulerEvent,
							instance._getCalendarBookingOffset(schedulerEvent, changedAttributes),
							instance._getCalendarBookingDuration(schedulerEvent),
							instance._hasChildCalendarBookingsPromise(schedulerEvent),
							answers
						)
						.then(
							function(data) {
								instance._promptSchedulerEventUpdate(data);
							}
						);
					},

					_promptSchedulerEventUpdate: function(data) {
						var instance = this;

						var hasChild = data[3];
						var schedulerEvent = data[0];

						instance.queue = new A.AsyncQueue();

						if (schedulerEvent.isRecurring()) {
							instance.queue.add(
								{
									args: [data],
									autoContinue: false,
									context: instance,
									fn: instance._queueableQuestionUpdateRecurring,
									timeout: 0
								}
							);
						}

						if (schedulerEvent.isMasterBooking()) {
							if (hasChild) {
								instance.queue.add(
									{
										args: [data],
										autoContinue: false,
										context: instance,
										fn: instance._queueableQuestionUpdateAllInvited,
										timeout: 0
									}
								);
							}
						}
						else {
							instance.queue.add(
								{
									args: [data],
									autoContinue: false,
									context: instance,
									fn: instance._queueableQuestionUserCalendarOnly,
									timeout: 0
								}
							);
						}

						instance.queue.add(
							{
								args: [data],
								autoContinue: false,
								context: instance,
								fn: instance._queueableQuestionResolver,
								timeout: 0
							}
						);

						instance.queue.add(
							{
								autoContinue: false,
								context: instance,
								fn: instance.load,
								timeout: 0
							}
						);

						instance.queue.run();
					},

					_queueableQuestionResolver: function(data) {
						var instance = this;

						var answers = data[4];
						var duration = data[2];
						var offset = data[1];
						var schedulerEvent = data[0];

						var showNextQuestion = A.bind(instance.queue.run, instance.queue);

						if (answers.cancel) {
							A.soon(showNextQuestion);
						}
						else if (answers.updateInstance) {
							CalendarUtil.updateEventInstance(schedulerEvent, !!answers.allFollowing, showNextQuestion);
						}
						else {
							CalendarUtil.updateEvent(schedulerEvent, offset, duration, showNextQuestion);
						}
					},

					_queueableQuestionUpdateAllInvited: function(data) {
						var instance = this;

						var answers = data[4];

						var showNextQuestion = A.bind(instance.queue.run, instance.queue);

						if (answers.cancel) {
							A.soon(showNextQuestion);
						}
						else {
							Liferay.CalendarMessageUtil.confirm(
								TPL_MESSAGE_UPDATE_ALL_INVITED,
								Liferay.Language.get('continue'),
								Liferay.Language.get('dont-change-the-event'),
								function() {
									showNextQuestion();
								},
								function() {
									answers.cancel = true;

									showNextQuestion();
								}
							);
						}
					},

					_queueableQuestionUpdateRecurring: function(data) {
						var instance = this;

						var answers = data[4];

						var showNextQuestion = A.bind(instance.queue.run, instance.queue);

						if (answers.cancel) {
							A.soon(showNextQuestion);
						}
						else {
							Liferay.RecurrenceUtil.openConfirmationPanel(
								'update',
								function() {
									answers.updateInstance = true;

									showNextQuestion();
								},
								function() {
									answers.allFollowing = true;
									answers.updateInstance = true;

									showNextQuestion();
								},
								function() {
									showNextQuestion();
								},
								function() {
									answers.cancel = true;

									showNextQuestion();
								}
							);
						}
					},

					_queueableQuestionUserCalendarOnly: function(data) {
						var instance = this;

						var answers = data[4];
						var schedulerEvent = data[0];

						var showNextQuestion = A.bind(instance.queue.run, instance.queue);

						if (answers.cancel) {
							A.soon(showNextQuestion);
						}
						else {
							var calendar = Liferay.CalendarUtil.availableCalendars[schedulerEvent.get('calendarId')];

							var content = [
								'<p class="calendar-portlet-confirmation-text">',
								Lang.sub(
									Liferay.Language.get('you-are-about-to-make-changes-that-will-only-effect-your-calendar-x'),
									[Liferay.Util.escapeHTML(calendar.get('name'))]
								),
								'</p>'
							].join(STR_BLANK);

							Liferay.CalendarMessageUtil.confirm(
								content,
								Liferay.Language.get('continue'),
								Liferay.Language.get('dont-change-the-event'),
								function() {
									showNextQuestion();
								},
								function() {
									answers.cancel = true;

									showNextQuestion();
								}
							);
						}
					}
				}
			}
		);

		Liferay.Scheduler = Scheduler;

		var SchedulerEventRecorder = A.Component.create(
			{
				ATTRS: {
					calendarId: {
						setter: toInt,
						value: 0
					},

					editCalendarBookingURL: {
						setter: String,
						validator: isValue,
						value: STR_BLANK
					},

					permissionsCalendarBookingURL: {
						setter: String,
						validator: isValue,
						value: STR_BLANK
					},

					portletNamespace: {
						setter: String,
						validator: isValue,
						value: STR_BLANK
					},

					status: {
						setter: toInt,
						value: CalendarWorkflow.STATUS_DRAFT
					},

					toolbar: {
						value: {
							children: []
						}
					},

					viewCalendarBookingURL: {
						setter: String,
						validator: isValue,
						value: STR_BLANK
					}
				},

				EXTENDS: A.SchedulerEventRecorder,

				NAME: 'scheduler-event-recorder',

				prototype: {
					initializer: function() {
						var instance = this;

						var popoverBB = instance.popover.get('boundingBox');

						popoverBB.delegate('click', instance._handleEventAnswer, '.calendar-event-answer', instance);
					},

					getTemplateData: function() {
						var instance = this;

						var editing = true;

						var schedulerEvent = instance.get('event');

						if (!schedulerEvent) {
							editing = false;

							schedulerEvent = instance;
						}

						var availableCalendars = CalendarUtil.availableCalendars;

						var calendar = availableCalendars[schedulerEvent.get('calendarId')];

						var permissions = calendar.get('permissions');

						var templateData = SchedulerEventRecorder.superclass.getTemplateData.apply(this, arguments);

						return A.merge(
							templateData,
							{
								acceptLinkEnabled: instance._hasWorkflowStatusPermission(schedulerEvent, CalendarWorkflow.STATUS_APPROVED),
								allDay: schedulerEvent.get('allDay'),
								availableCalendars: availableCalendars,
								calendar: calendar,
								calendarIds: AObject.keys(availableCalendars),
								declineLinkEnabled: instance._hasWorkflowStatusPermission(schedulerEvent, CalendarWorkflow.STATUS_DENIED),
								editing: editing,
								endTime: templateData.endDate,
								maybeLinkEnabled: instance._hasWorkflowStatusPermission(schedulerEvent, CalendarWorkflow.STATUS_MAYBE),
								permissions: permissions,
								startTime: templateData.startDate,
								status: schedulerEvent.get('status'),
								workflowStatus: CalendarWorkflow
							}
						);
					},

					getUpdatedSchedulerEvent: function(optAttrMap) {
						var instance = this;

						var	attrMap = {
							color: instance.get('color')
						};

						return SchedulerEventRecorder.superclass.getUpdatedSchedulerEvent.call(instance, A.merge(attrMap, optAttrMap));
					},

					isMasterBooking: Lang.emptyFnFalse,

					populateForm: function() {
						var instance = this;

						var bodyTemplate = instance.get('bodyTemplate');

						var headerTemplate = instance.get('headerTemplate');

						var templateData = instance.getTemplateData();

						if (A.instanceOf(bodyTemplate, A.Template) && A.instanceOf(headerTemplate, A.Template)) {
							instance.popover.setStdModContent('body', bodyTemplate.parse(templateData));
							instance.popover.setStdModContent('header', headerTemplate.parse(templateData));

							instance.popover.addToolbar(instance._getFooterToolbar(), 'footer');
						}
						else {
							SchedulerEventRecorder.superclass.populateForm.apply(instance, arguments);
						}

						instance.popover.addToolbar(
							[
								{
									cssClass: 'close',
									label: "\u00D7",
									on: {
										click: A.bind(instance._handleCancelEvent, instance)
									},
									render: true
								}
							],
							'header'
						);

						instance.popover.headerNode.toggleClass('hide', !templateData.permissions.VIEW_BOOKING_DETAILS);
					},

					_afterPopoverVisibleChange: function(event) {
						var instance = this;

						var schedulerEvent = instance.get('event');

						var popoverBB = instance.popover.get('boundingBox');

						popoverBB.toggleClass('calendar-portlet-event-recorder-editing', !!schedulerEvent);

						var defaultUserCalendar = CalendarUtil.getDefaultUserCalendar();

						var calendarId = defaultUserCalendar.get('calendarId');
						var color = defaultUserCalendar.get('color');

						var eventInstance = instance;

						if (schedulerEvent) {
							calendarId = schedulerEvent.get('calendarId');

							var calendar = CalendarUtil.availableCalendars[calendarId];

							if (calendar) {
								color = calendar.get('color');

								eventInstance = schedulerEvent;
							}
						}

						eventInstance.set(
							'color',
							color,
							{
								silent: true
							}
						);

						SchedulerEventRecorder.superclass._afterPopoverVisibleChange.apply(this, arguments);

						var portletNamespace = instance.get('portletNamespace');

						var eventRecorderCalendar = A.one('#' + portletNamespace + 'eventRecorderCalendar');

						if (eventRecorderCalendar) {
							eventRecorderCalendar.val(calendarId.toString());
						}

						if (event.newVal) {
							instance._syncInvitees();
						}
					},

					_handleEventAnswer: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						var schedulerEvent = instance.get('event');

						var linkEnabled = A.DataType.Boolean.parse(currentTarget.hasClass('calendar-event-answer-true'));

						var statusData = Lang.toInt(currentTarget.getData('status'));

						if (schedulerEvent && linkEnabled) {
							CalendarUtil.invokeTransition(schedulerEvent, statusData);
						}
					},

					_handleEditEvent: function(event) {
						var instance = this;

						var scheduler = instance.get('scheduler');

						var activeViewName = scheduler.get('activeView').get('name');

						var date = scheduler.get('date');

						var schedulerEvent = instance.get('event');

						var editCalendarBookingURL = decodeURIComponent(instance.get('editCalendarBookingURL'));

						var data = instance.serializeForm();

						data.activeView = activeViewName;

						data.date = date.getTime();

						data.endTime = CalendarUtil.toUTC(data.endTime).getTime();
						data.startTime = CalendarUtil.toUTC(data.startTime).getTime();

						data.titleCurrentValue = encodeURIComponent(data.content);

						if (schedulerEvent) {
							data.allDay = schedulerEvent.get('allDay');
							data.calendarBookingId = schedulerEvent.get('calendarBookingId');
						}

						Liferay.Util.openWindow(
							{
								dialog: {
									after: {
										destroy: function(event) {
											scheduler.load();
										}
									},
									destroyOnHide: true,
									modal: true
								},
								refreshWindow: window,
								title: Liferay.Language.get('edit-calendar-booking'),
								uri: Lang.sub(editCalendarBookingURL, data)
							}
						);

						instance.hidePopover();
					},

					_handleViewEvent: function(event) {
						var instance = this;

						var scheduler = instance.get('scheduler');

						var viewCalendarBookingURL = decodeURIComponent(instance.get('viewCalendarBookingURL'));

						var data = instance.serializeForm();

						var schedulerEvent = instance.get('event');

						data.calendarBookingId = schedulerEvent.get('calendarBookingId');

						Liferay.Util.openWindow(
							{
								dialog: {
									after: {
										destroy: function(event) {
											scheduler.load();
										}
									},
									destroyOnHide: true,
									modal: true
								},
								refreshWindow: window,
								title: Liferay.Language.get('view-calendar-booking-details'),
								uri: Lang.sub(viewCalendarBookingURL, data)
							}
						);

						event.domEvent.preventDefault();
					},

					_hasDeleteButton: function(permissions, calendar, status) {
						return permissions.MANAGE_BOOKINGS && calendar;
					},

					_hasEditButton: function(permissions, calendar, status) {
						return permissions.MANAGE_BOOKINGS;
					},

					_hasSaveButton: function(permissions, calendar, status) {
						return permissions.MANAGE_BOOKINGS;
					},

					_hasWorkflowStatusPermission: function(schedulerEvent, newStatus) {
						var instance = this;

						var hasPermission = false;

						if (schedulerEvent) {
							var calendarId = schedulerEvent.get('calendarId');

							var calendar = CalendarUtil.availableCalendars[calendarId];

							var permissions = calendar.get('permissions');

							var status = schedulerEvent.get('status');

							hasPermission = permissions.MANAGE_BOOKINGS && (status !== newStatus) && (status !== CalendarWorkflow.STATUS_DRAFT);
						}

						return hasPermission;
					},

					_renderPopOver: function() {
						var instance = this;

						var popoverBB = instance.popover.get('boundingBox');

						SchedulerEventRecorder.superclass._renderPopOver.apply(this, arguments);

						popoverBB.delegate(
							['change', 'keypress'],
							function(event) {
								var schedulerEvent = instance.get('event') || instance;

								var calendarId = toInt(event.currentTarget.val());

								var selectedCalendar = CalendarUtil.availableCalendars[calendarId];

								if (selectedCalendar) {
									schedulerEvent.set(
										'color',
										selectedCalendar.get('color'),
										{
											silent: true
										}
									);
								}
							},
							'#' + instance.get('portletNamespace') + 'eventRecorderCalendar'
						);
					},

					_syncInvitees: function() {
						var instance = this;

						var schedulerEvent = instance.get('event');

						if (schedulerEvent) {
							var calendar = CalendarUtil.availableCalendars[schedulerEvent.get('calendarId')];

							if (calendar) {
								var permissions = calendar.get('permissions');

								if (permissions.VIEW_BOOKING_DETAILS) {
									var parentCalendarBookingId = schedulerEvent.get('parentCalendarBookingId');
									var portletNamespace = instance.get('portletNamespace');

									CalendarUtil.getCalendarBookingInvitees(
										parentCalendarBookingId,
										function(data) {
											var results = AArray.partition(
													data,
													function(item) {
														return item.classNameId === CalendarUtil.USER_CLASS_NAME_ID;
													}
											);

											instance._syncInviteesContent('#' + portletNamespace + 'eventRecorderUsers', results.matches);
											instance._syncInviteesContent('#' + portletNamespace + 'eventRecorderResources', results.rejects);
										}
									);
								}
							}
						}
					},

					_syncInviteesContent: function(contentNode, calendarResources) {
						var instance = this;

						var values = AArray.map(
							calendarResources,
							function(item) {
								return item.name;
							}
						);

						contentNode = A.one(contentNode);

						var messageNode = contentNode.one('.calendar-portlet-invitees');

						var messageHTML = '&mdash;';

						if (values.length > 0) {
							contentNode.show();

							messageHTML = values.join(STR_COMMA_SPACE);
						}

						messageNode.html(messageHTML);
					},

					_getFooterToolbar: function() {
						var instance = this;

						var schedulerEvent = instance.get('event');

						var schedulerEventCreated = false;

						if (schedulerEvent) {
							schedulerEventCreated = true;
						}
						else {
							schedulerEvent = instance;
						}

						var children = [];
						var editGroup = [];
						var respondGroup = [];

						var status = schedulerEvent.get('status');
						var calendar = CalendarUtil.availableCalendars[schedulerEvent.get('calendarId')];

						if (calendar) {
							var permissions = calendar.get('permissions');

							if (instance._hasSaveButton(permissions, calendar, status)) {
								editGroup.push(
									{
										on: {
											click: A.bind(instance._handleSaveEvent, instance)
										},
										icon: 'icon-hdd',
										id: 'saveBtn',
										label: Liferay.Language.get('save'),
										primary: true
									}
								);
							}

							if (instance._hasEditButton(permissions, calendar, status)) {
								editGroup.push(
									{
										on: {
											click: A.bind(instance._handleEditEvent, instance)
										},
										icon: 'icon-edit',
										id: 'editBtn',
										label: Liferay.Language.get('edit')
									}
								);
							}

							if ((schedulerEventCreated === true) && permissions.VIEW_BOOKING_DETAILS) {
								editGroup.push(
									{
										on: {
											click: A.bind(instance._handleViewEvent, instance)
										},
										icon: 'icon-eye-open',
										id: 'viewBtn',
										label: Liferay.Language.get('view-details')
									}
								);
							}

							if (schedulerEvent.isMasterBooking() && instance._hasDeleteButton(permissions, calendar, status)) {
								editGroup.push(
									{
										on: {
											click: A.bind(instance._handleDeleteEvent, instance)
										},
										icon: 'icon-trash',
										id: 'deleteBtn',
										label: Liferay.Language.get('delete')
									}
								);
							}

							if (editGroup.length) {
								children.push(editGroup);
							}

							if (respondGroup.length) {
								children.push(respondGroup);
							}
						}

						return children;
					}
				}
			}
		);

		Liferay.SchedulerEventRecorder = SchedulerEventRecorder;
	},
	'',
	{
		requires: ['aui-datatype', 'aui-io', 'aui-scheduler', 'aui-toolbar', 'async-queue', 'autocomplete', 'autocomplete-highlighters', 'dd-plugin', 'liferay-calendar-message-util', 'liferay-calendar-recurrence-util', 'liferay-node', 'liferay-portlet-url', 'liferay-store', 'promise', 'resize-plugin']
	}
);
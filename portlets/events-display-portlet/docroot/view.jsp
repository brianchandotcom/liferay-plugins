<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This file is part of Liferay Social Office. Liferay Social Office is free
 * software: you can redistribute it and/or modify it under the terms of the GNU
 * Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * Liferay Social Office is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Liferay Social Office. If not, see http://www.gnu.org/licenses/agpl-3.0.html.
 */
--%>

<%@ include file="/init.jsp" %>

<%
List<Long> groupIds = new ArrayList<Long>();

Group group = GroupLocalServiceUtil.getGroup(layout.getGroupId());

boolean regularSite = group.isRegularSite();

if (regularSite) {
	groupIds.add(group.getGroupId());
}
else if (group.isUser() && themeDisplay.isSignedIn()) {
	for (Group mySite : user.getMySites()) {
		groupIds.add(mySite.getGroupId());
	}
}
else {
	Group guestGroup = GroupLocalServiceUtil.getGroup(themeDisplay.getCompanyId(), GroupConstants.GUEST);

	groupIds.add(guestGroup.getGroupId());
}

Calendar displayStartTimeJCalendar = (Calendar)jCalendar.clone();

displayStartTimeJCalendar.set(Calendar.HOUR_OF_DAY, 0);
displayStartTimeJCalendar.set(Calendar.MINUTE, 0);
displayStartTimeJCalendar.set(Calendar.SECOND, 0);
displayStartTimeJCalendar.set(Calendar.MILLISECOND, 0);

long displayEndTime = jCalendar.getTimeInMillis() + (Time.DAY * maxDaysDisplayed);

List<Long> calendarResourceIds = new ArrayList<Long>();

if (!regularSite) {
	for (long groupId : groupIds) {
		long classNameId = PortalUtil.getClassNameId(Group.class);

		if (group.isUser()) {
			classNameId = PortalUtil.getClassNameId(User.class);
		}

		CalendarResource calendarResource = CalendarResourceLocalServiceUtil.fetchCalendarResource(classNameId, groupId);

		if (calendarResource != null) {
			calendarResourceIds.add(calendarResource.getCalendarResourceId());
		}
	}
}

int[] statuses = {WorkflowConstants.STATUS_APPROVED};

List<CalendarBooking> calendarBookings = CalendarBookingServiceUtil.search(themeDisplay.getCompanyId(), ArrayUtil.toLongArray(groupIds), null, ArrayUtil.toLongArray(calendarResourceIds), -1, null, displayStartTimeJCalendar.getTimeInMillis(), displayEndTime, true, statuses, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

Map<Integer, List<CalendarBooking>> sortedCalendarBookings = new HashMap<Integer, List<CalendarBooking>>();

for (int i = 0; i < maxDaysDisplayed; i++) {
	sortedCalendarBookings.put(i, new ArrayList<CalendarBooking>());
}

for (CalendarBooking calendarBooking : calendarBookings) {
	if (Validator.isNull(calendarBooking.getTitle())) {
		continue;
	}

	if (!calendarBooking.isAllDay() && (calendarBooking.getEndTime() < jCalendar.getTimeInMillis())) {
		continue;
	}

	Calendar bookingStartTimeJCalendar = Calendar.getInstance(timeZone, locale);

	long startTime = calendarBooking.getStartTime();

	if (calendarBooking.isAllDay()) {
		startTime -= timeZone.getRawOffset();

		if (timeZone.inDaylightTime(new Date(startTime))) {
			startTime -= timeZone.getDSTSavings();
		}
	}

	bookingStartTimeJCalendar.setTimeInMillis(startTime);

	Calendar diplayEndTimeJCalendar = (Calendar)displayStartTimeJCalendar.clone();

	for (int i = 0; i < maxDaysDisplayed; i++) {
		List<CalendarBooking> currentCalendarBookings = sortedCalendarBookings.get(i);

		diplayEndTimeJCalendar.add(Calendar.DAY_OF_YEAR, 1);

		if (bookingStartTimeJCalendar.before(diplayEndTimeJCalendar)) {
			currentCalendarBookings.add(calendarBooking);

			break;
		}
	}
}
%>

<c:choose>
	<c:when test="<%= EventsDisplayUtil.checkEmpty(sortedCalendarBookings) %>">
		<liferay-ui:message key="there-are-no-more-events-today" />
	</c:when>
	<c:otherwise>

		<%
		for (int i = 0 ; i < maxDaysDisplayed; i++) {
			List<CalendarBooking> currentCalendarBookings = sortedCalendarBookings.get(i);

			if (!currentCalendarBookings.isEmpty()) {

				ListUtil.sort(currentCalendarBookings, new CalendarBookingTimeComparator(locale));

				request.setAttribute("view.jsp-calendarBookings", currentCalendarBookings);
		%>

				<liferay-util:include page="/view_events.jsp" servletContext="<%= application %>">
					<c:choose>
						<c:when test="<%= i == 0 %>">
							<liferay-util:param name="searchContainerName" value="todays-events" />
						</c:when>
						<c:when test="<%= i == 1 %>">
							<liferay-util:param name="searchContainerName" value="tomorrows-events" />
						</c:when>
						<c:otherwise>

							<%
								Calendar startTimeJCalendar = (Calendar)displayStartTimeJCalendar.clone();
								startTimeJCalendar.add(Calendar.DAY_OF_YEAR, i);
								String eventDay = LanguageUtil.format(pageContext, "x's-events", dateFormatDate.format(startTimeJCalendar.getTimeInMillis()), false);
							%>

							<liferay-util:param name="searchContainerName" value="<%= eventDay %>" />
						</c:otherwise>
					</c:choose>
				</liferay-util:include>

		<%
			}
		}
		%>

	</c:otherwise>
</c:choose>
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
Calendar displayStartTimeJCalendar = (Calendar)jCalendar.clone();

displayStartTimeJCalendar.set(Calendar.HOUR_OF_DAY, 0);
displayStartTimeJCalendar.set(Calendar.MINUTE, 0);
displayStartTimeJCalendar.set(Calendar.SECOND, 0);
displayStartTimeJCalendar.set(Calendar.MILLISECOND, 0);

long displayEndTime = jCalendar.getTimeInMillis() + (Time.DAY * maxDaysDisplayed);

int[] statuses = {WorkflowConstants.STATUS_APPROVED};

Map<Integer, List<CalendarBooking>> sortedCalendarBookings = EventsDisplayUtil.getCalendarBookings(maxDaysDisplayed, themeDisplay, layout.getGroupId(), displayStartTimeJCalendar, displayEndTime, statuses, jCalendar);
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
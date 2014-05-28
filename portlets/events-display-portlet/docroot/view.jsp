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
Map<Integer, List<CalendarBooking>> calendarBookings = EventsDisplayUtil.getCalendarBookings(layout.getGroupId(), themeDisplay, jCalendar, maxDaysDisplayed);
%>

<c:choose>
	<c:when test="<%= EventsDisplayUtil.checkEmpty(calendarBookings) %>">
		<liferay-ui:message key="there-are-no-more-events-today" />
	</c:when>
	<c:otherwise>

		<%
		for (int i = 0 ; i < maxDaysDisplayed; i++) {
			List<CalendarBooking> curCalendarBookings = calendarBookings.get(i);

			if (curCalendarBookings.isEmpty()) {
				continue;
			}

			ListUtil.sort(curCalendarBookings, new CalendarBookingTimeComparator(locale));

			request.setAttribute("view.jsp-calendarBookings", curCalendarBookings);
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
						Calendar startTimeJCalendar = EventsDisplayUtil.getDisplayStartTimeJCalendar(jCalendar);

						startTimeJCalendar.add(Calendar.DAY_OF_YEAR, i);

						String eventDay = LanguageUtil.format(pageContext, "x-events", dateFormatDate.format(startTimeJCalendar.getTimeInMillis()), false);
						%>

						<liferay-util:param name="searchContainerName" value="<%= eventDay %>" />
					</c:otherwise>
				</c:choose>
			</liferay-util:include>

		<%
		}
		%>

	</c:otherwise>
</c:choose>
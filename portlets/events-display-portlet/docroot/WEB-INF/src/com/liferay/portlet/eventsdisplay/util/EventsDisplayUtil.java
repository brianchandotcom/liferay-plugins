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

package com.liferay.portlet.eventsdisplay.util;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author Sherry Yang
 */
public class EventsDisplayUtil {

	public static boolean checkEmpty(
		Map<Integer, List<CalendarBooking>> calendarBookingsMap) {

		if (calendarBookingsMap.isEmpty()) {
			return true;
		}

		for (int key : calendarBookingsMap.keySet()) {
			List<CalendarBooking> calendarBookings = calendarBookingsMap.get(
				key);

			if (!calendarBookings.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	public static Map<Integer, List<CalendarBooking>>
		sortCalendarBookingsByDays(
			int maxDaysDisplayed, ThemeDisplay themeDisplay,
			Calendar displayStartTimeJCalendar, Calendar jCalendar,
			List<CalendarBooking> calendarBookings) {

		Map<Integer, List<CalendarBooking>> sortedCalendarBookings =
			new HashMap<Integer, List<CalendarBooking>>();

		for (int i = 0; i < maxDaysDisplayed; i++) {
			sortedCalendarBookings.put(i, new ArrayList<CalendarBooking>());
		}

		for (CalendarBooking calendarBooking : calendarBookings) {
			if (Validator.isNull(calendarBooking.getTitle())) {
				continue;
			}

			if (!calendarBooking.isAllDay() &&
				(calendarBooking.getEndTime() < jCalendar.getTimeInMillis())) {

				continue;
			}

			TimeZone timeZone = themeDisplay.getTimeZone();

			Calendar bookingStartTimeJCalendar = Calendar.getInstance(
				timeZone, themeDisplay.getLocale());

			long startTime = calendarBooking.getStartTime();

			if (calendarBooking.isAllDay()) {
				startTime -= timeZone.getRawOffset();

				if (timeZone.inDaylightTime(new Date(startTime))) {
					startTime -= timeZone.getDSTSavings();
				}
			}

			bookingStartTimeJCalendar.setTimeInMillis(startTime);

			Calendar diplayEndTimeJCalendar =
				(Calendar)displayStartTimeJCalendar.clone();

			for (int i = 0; i < maxDaysDisplayed; i++) {
				List<CalendarBooking> currentCalendarBookings =
					sortedCalendarBookings.get(i);

				diplayEndTimeJCalendar.add(Calendar.DAY_OF_YEAR, 1);

				if (bookingStartTimeJCalendar.before(diplayEndTimeJCalendar)) {
					currentCalendarBookings.add(calendarBooking);

					break;
				}
			}
		}

		return sortedCalendarBookings;
	}

}
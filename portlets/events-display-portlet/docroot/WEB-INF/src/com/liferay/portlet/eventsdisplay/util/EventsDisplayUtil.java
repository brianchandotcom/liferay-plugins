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

import java.util.List;
import java.util.Map;

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

}
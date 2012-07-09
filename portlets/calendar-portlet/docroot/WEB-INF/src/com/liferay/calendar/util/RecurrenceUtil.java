/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.calendar.util;

import com.google.ical.iter.RecurrenceIterator;
import com.google.ical.iter.RecurrenceIteratorFactory;
import com.google.ical.util.TimeUtils;
import com.google.ical.values.DateValue;
import com.google.ical.values.DateValueImpl;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * @author Marcellus Tavares
 */
public class RecurrenceUtil {

	public static List<CalendarBooking> expandCalendarBookings(
		List<CalendarBooking> calendarBookings, long endDate) {

		List<CalendarBooking> result = new ArrayList<CalendarBooking>();

		DateValue endDateValue = _toDateValue(endDate);

		try {
			for (CalendarBooking calendarBooking : calendarBookings) {
				if (!calendarBooking.isRecurring()) {
					result.add(calendarBooking);

					continue;
				}

				DateValue startDateValue = _toDateValue(
					calendarBooking.getStartDate());

				RecurrenceIterator recurrenceIterator =
					RecurrenceIteratorFactory.createRecurrenceIterator(
						calendarBooking.getRecurrence(), startDateValue,
						TimeUtils.utcTimezone());

				while (recurrenceIterator.hasNext()) {
					DateValue dateValue = (DateValue)recurrenceIterator.next();

					if (dateValue.compareTo(endDateValue) > 0) {
						break;
					}

					CalendarBooking clone = _cloneCalendarBooking(
						calendarBooking, dateValue);

					result.add(clone);
				}
			}
		}
		catch (ParseException pe) {
			_log.error("Unable to parse data ", pe);
		}

		return result;
	}

	private static CalendarBooking _cloneCalendarBooking(
		CalendarBooking calendarBooking, DateValue startDateValue) {

		CalendarBooking clone = (CalendarBooking)calendarBooking.clone();

		Calendar jCalendar = JCalendarUtil.getJCalendar(
			calendarBooking.getStartDate());

		Calendar startDateJCalendar =
			JCalendarUtil.getJCalendar(
				startDateValue.year(), startDateValue.month() - 1,
				startDateValue.day(), jCalendar.get(Calendar.HOUR_OF_DAY),
				jCalendar.get(Calendar.MINUTE), jCalendar.get(Calendar.SECOND),
				jCalendar.get(Calendar.MILLISECOND),
				TimeZone.getTimeZone(StringPool.UTC));

		long startDateInMillis = startDateJCalendar.getTimeInMillis();

		clone.setStartDate(startDateInMillis);
		clone.setEndDate(
			startDateInMillis + calendarBooking.getDurationInMillis());

		return clone;
	}

	private static DateValue _toDateValue(long time) {
		Calendar jCalendar = JCalendarUtil.getJCalendar(time);

		DateValue dateValue = new DateValueImpl(
			jCalendar.get(Calendar.YEAR), jCalendar.get(Calendar.MONTH) + 1,
			jCalendar.get(Calendar.DAY_OF_MONTH));

		return dateValue;
	}

	private static Log _log = LogFactoryUtil.getLog(RecurrenceUtil.class);

}
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

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarResourceLocalServiceUtil;
import com.liferay.calendar.service.permission.CalendarPermission;
import com.liferay.calendar.util.comparator.CalendarNameComparator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.UniqueList;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo Lundgren
 * @author Peter Shin
 * @author Fabio Pezzutto
 */
public class CalendarUtil {

	public static final String AM_SUFFIX = "am";

	public static final String HOUR_SUFFIX = "h";

	public static final String PM_SUFFIX = "pm";

	public static String formatHour(int isoHour) {
		return formatHour(isoHour, true, true, true);
	}

	public static String formatHour(int isoHour, boolean isoFormat) {
		return formatHour(isoHour, isoFormat, true, true);
	}

	public static String formatHour(
		int isoHour, boolean isoFormat, boolean padHours) {

		return formatHour(isoHour, isoFormat, padHours, true);
	}

	public static String formatHour(
		int isoHour, boolean isoFormat, boolean padHours, boolean showSuffix) {

		int hour = isoHour;
		String formatted = StringPool.BLANK;
		String suffix = StringPool.BLANK;

		if (isoFormat) {
			if (showSuffix) {
				suffix = CalendarUtil.HOUR_SUFFIX;
			}
		}
		else {
			if (isoHour == 0) {
				hour = 12;
			}
			else if (isoHour > 12) {
				hour -= 12;
			}

			if (showSuffix) {
				if (isoHour < 12) {
					suffix = CalendarUtil.AM_SUFFIX;
				}
				else {
					suffix = CalendarUtil.PM_SUFFIX;
				}
			}
		}

		if (padHours) {
			formatted = String.format("%02d", hour);
		}
		else {
			formatted = String.valueOf(hour);
		}

		return formatted.concat(suffix);
	}

	public static Calendar getCalendar(Calendar cal, TimeZone tz) {
		return getCalendar(
			tz, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
			cal.get(Calendar.DATE), cal.get(Calendar.HOUR_OF_DAY),
			cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND),
			cal.get(Calendar.MILLISECOND));
	}

	public static Calendar getCalendar(Date date, TimeZone tz) {
		Calendar cal = CalendarFactoryUtil.getCalendar(
			TimeZone.getTimeZone(StringPool.UTC));

		cal.setTime(date);

		return getCalendar(cal, tz);
	}

	public static Calendar getCalendar(long timestamp, TimeZone tz) {
		return getCalendar(new Date(timestamp), tz);
	}

	public static Calendar getCalendar(
			TimeZone tz, int year, int month, int day, int hour, int minutes,
			int seconds, int milliseconds) {

		java.util.Calendar cal = CalendarFactoryUtil.getCalendar(tz);

		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, day);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minutes);
		cal.set(Calendar.SECOND, seconds);
		cal.set(Calendar.MILLISECOND, milliseconds);

		return cal;
	}

	public static Date getDate(Date date, TimeZone tz) {
		Calendar cal = getCalendar(date, tz);

		return cal.getTime();
	}

	public static OrderByComparator getOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator orderByComparator = new CalendarNameComparator(
			orderByAsc);

		return orderByComparator;
	}

	public static int getTimeZoneOffset(TimeZone timeZone) {
		Date now = new Date();

		return timeZone.getOffset(now.getTime());
	}

	public static String[] splitKeywords(String keywords) {
		List<String> keywordsList = new UniqueList<String>();

		StringBundler sb = new StringBundler();

		for (char c : keywords.toCharArray()) {
			if (Character.isWhitespace(c)) {
				if (sb.length() > 0) {
					keywordsList.add(sb.toString());

					sb = new StringBundler();
				}
			}
			else if (Character.isLetterOrDigit(c)) {
				sb.append(c);
			}
			else {
				return new String[] {keywords};
			}
		}

		if (sb.length() > 0) {
			keywordsList.add(sb.toString());
		}

		return StringUtil.split(StringUtil.merge(keywordsList));
	}

	public static JSONArray toCalendarBookingsJSON(
			HttpServletRequest request, List<CalendarBooking> calendarBookings)
		throws PortalException, SystemException {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		if (calendarBookings != null) {
			for (CalendarBooking calendarBooking : calendarBookings) {
				jsonArray.put(
					toCalendarJSON(request, calendarBooking.getCalendar()));
			}
		}

		return jsonArray;
	}

	public static JSONObject toCalendarJSON(
		HttpServletRequest request,
		com.liferay.calendar.model.Calendar calendar) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			CalendarResource calendarResource =
				CalendarResourceLocalServiceUtil.fetchCalendarResource(
					calendar.getCalendarResourceId());

			jsonObject.put("calendarId", calendar.getCalendarId());
			jsonObject.put(
				"calendarResourceId", calendarResource.getCalendarResourceId());
			jsonObject.put(
				"calendarResourceName", calendarResource.getName(locale));
			jsonObject.put("color", ColorUtil.toHexString(calendar.getColor()));
			jsonObject.put("defaultCalendar", calendar.isDefaultCalendar());
			jsonObject.put("classNameId", calendarResource.getClassNameId());
			jsonObject.put("classPK", calendarResource.getClassPK());
			jsonObject.put("global", calendarResource.isGlobal());
			jsonObject.put("name", calendar.getName(locale));

			JSONObject jsonPermissions = JSONFactoryUtil.createJSONObject();

			jsonPermissions.put(
				"VIEW", CalendarPermission.contains(
					permissionChecker, calendar, ActionKeys.VIEW));

			jsonPermissions.put(
				"VIEW_BOOKING_DETAILS", CalendarPermission.contains(
					permissionChecker, calendar,
					ActionKeys.VIEW_BOOKING_DETAILS));

			jsonPermissions.put(
				"MANAGE_BOOKINGS",
				CalendarPermission.contains(
					permissionChecker, calendar, ActionKeys.MANAGE_BOOKINGS));

			jsonObject.put("permissions", jsonPermissions);
		}
		catch (Exception e) {
		}

		return jsonObject;
	}

	public static JSONArray toCalendarsJSON(
		HttpServletRequest request,
		List<com.liferay.calendar.model.Calendar> calendars) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		if (calendars != null) {
			for (com.liferay.calendar.model.Calendar calendar : calendars) {
				jsonArray.put(toCalendarJSON(request, calendar));
			}
		}

		return jsonArray;
	}

	public static Calendar toLastHour(Calendar cal) {
		Calendar gtCal = (Calendar)cal.clone();

		gtCal.set(Calendar.HOUR_OF_DAY, 23);
		gtCal.set(Calendar.MINUTE, 59);
		gtCal.set(Calendar.SECOND, 59);
		gtCal.set(Calendar.MILLISECOND, 999);

		return gtCal;
	}

	public static Calendar toMidnight(Calendar cal) {
		Calendar gtCal = (Calendar)cal.clone();

		gtCal.set(Calendar.HOUR_OF_DAY, 0);
		gtCal.set(Calendar.MINUTE, 0);
		gtCal.set(Calendar.SECOND, 0);
		gtCal.set(Calendar.MILLISECOND, 0);

		return gtCal;
	}

}
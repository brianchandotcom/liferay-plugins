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
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarBookingServiceUtil;
import com.liferay.calendar.service.CalendarResourceLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

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

	public static boolean isEmpty(
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

	public static Map<Integer, List<CalendarBooking>> getCalendarBookings(
			long layoutGroupId, ThemeDisplay themeDisplay, Calendar jCalendar,
			int maxDaysDisplayed)
		throws PortalException, SystemException {

		long[] groupIds = getGroupIds(layoutGroupId, themeDisplay);

		Calendar displayStartTimeJCalendar = getDisplayStartTimeJCalendar(
			jCalendar);

		long displayEndTime =
			jCalendar.getTimeInMillis() + (Time.DAY * maxDaysDisplayed);

		List<CalendarBooking> calendarBookings =
			CalendarBookingServiceUtil.search(
				themeDisplay.getCompanyId(), groupIds, null,
				getCalendarResourceIds(layoutGroupId, groupIds), -1, null,
				displayStartTimeJCalendar.getTimeInMillis(), displayEndTime,
				true, _STATUSES, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

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
				List<CalendarBooking> curCalendarBookings =
					sortedCalendarBookings.get(i);

				diplayEndTimeJCalendar.add(Calendar.DAY_OF_YEAR, 1);

				if (bookingStartTimeJCalendar.before(diplayEndTimeJCalendar)) {
					curCalendarBookings.add(calendarBooking);

					break;
				}
			}
		}

		return sortedCalendarBookings;
	}

	public static Calendar getDisplayStartTimeJCalendar(Calendar jCalendar) {
		Calendar displayStartTimeJCalendar = (Calendar)jCalendar.clone();

		displayStartTimeJCalendar.set(Calendar.HOUR_OF_DAY, 0);
		displayStartTimeJCalendar.set(Calendar.MINUTE, 0);
		displayStartTimeJCalendar.set(Calendar.SECOND, 0);
		displayStartTimeJCalendar.set(Calendar.MILLISECOND, 0);

		return displayStartTimeJCalendar;
	}

	protected static long[] getCalendarResourceIds(
			long layoutGroupId, long[] groupIds)
		throws PortalException, SystemException {

		Group group = GroupLocalServiceUtil.getGroup(layoutGroupId);

		if (group.isRegularSite()) {
			return null;
		}

		List<Long> calendarResourceIds = new ArrayList<Long>();

		for (long groupId : groupIds) {
			long classNameId = PortalUtil.getClassNameId(Group.class);

			if (group.isUser()) {
				classNameId = PortalUtil.getClassNameId(User.class);
			}

			CalendarResource calendarResource =
				CalendarResourceLocalServiceUtil.fetchCalendarResource(
					classNameId, groupId);

			if (calendarResource != null) {
				calendarResourceIds.add(
					calendarResource.getCalendarResourceId());
			}
		}

		return ArrayUtil.toLongArray(calendarResourceIds);
	}

	protected static long[] getGroupIds(
			long layoutGroupId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		List<Long> groupIds = new ArrayList<Long>();

		Group group = GroupLocalServiceUtil.getGroup(layoutGroupId);

		if (group.isRegularSite()) {
			groupIds.add(group.getGroupId());
		}
		else if (group.isUser() && themeDisplay.isSignedIn()) {
			User user = themeDisplay.getUser();

			for (Group mySite : user.getMySites()) {
				groupIds.add(mySite.getGroupId());
			}
		}
		else {
			Group guestGroup = GroupLocalServiceUtil.getGroup(
				themeDisplay.getCompanyId(), GroupConstants.GUEST);

			groupIds.add(guestGroup.getGroupId());
		}

		return ArrayUtil.toLongArray(groupIds);
	}

	private static final int[] _STATUSES = {WorkflowConstants.STATUS_APPROVED};

}
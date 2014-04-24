/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
package com.liferay.calendar.service;

import com.liferay.calendar.util.JCalendarUtil;
import com.liferay.plugins.test.WebArchiveUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.util.CalendarFactoryImpl;

import java.io.IOException;
import java.util.Calendar;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class CalendarLocalServiceTest {

	@Deployment
	public static Archive<?> getGetDeployment() throws IOException {
		WebArchive webArchive = WebArchiveUtil.createWebArchive();

		return webArchive;
	}

	@Test
	public void testGetCalendarCount() throws Exception {
		int calendarCount = CalendarLocalServiceUtil.getCalendarsCount();
		System.out.println("Calendar Count: " + calendarCount);
	}
}

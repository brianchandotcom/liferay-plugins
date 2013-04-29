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

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link CalendarNotificationTemplateService}.
 * </p>
 *
 * @author    Eduardo Lundgren
 * @see       CalendarNotificationTemplateService
 * @generated
 */
public class CalendarNotificationTemplateServiceWrapper
	implements CalendarNotificationTemplateService,
		ServiceWrapper<CalendarNotificationTemplateService> {
	public CalendarNotificationTemplateServiceWrapper(
		CalendarNotificationTemplateService calendarNotificationTemplateService) {
		_calendarNotificationTemplateService = calendarNotificationTemplateService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _calendarNotificationTemplateService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_calendarNotificationTemplateService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _calendarNotificationTemplateService.invokeMethod(name,
			parameterTypes, arguments);
	}

	public com.liferay.calendar.model.CalendarNotificationTemplate addCalendarNotificationTemplate(
		long calendarId,
		com.liferay.calendar.notification.NotificationType notificationType,
		com.liferay.calendar.notification.NotificationTemplateType notificationTemplateType,
		java.lang.String subject, java.lang.String body,
		java.lang.String typeSettings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _calendarNotificationTemplateService.addCalendarNotificationTemplate(calendarId,
			notificationType, notificationTemplateType, subject, body,
			typeSettings, serviceContext);
	}

	public com.liferay.calendar.model.CalendarNotificationTemplate updateCalendarNotificationTemplate(
		long calendarNotificationTemplateId, java.lang.String subject,
		java.lang.String body, java.lang.String typeSettings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _calendarNotificationTemplateService.updateCalendarNotificationTemplate(calendarNotificationTemplateId,
			subject, body, typeSettings, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public CalendarNotificationTemplateService getWrappedCalendarNotificationTemplateService() {
		return _calendarNotificationTemplateService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedCalendarNotificationTemplateService(
		CalendarNotificationTemplateService calendarNotificationTemplateService) {
		_calendarNotificationTemplateService = calendarNotificationTemplateService;
	}

	public CalendarNotificationTemplateService getWrappedService() {
		return _calendarNotificationTemplateService;
	}

	public void setWrappedService(
		CalendarNotificationTemplateService calendarNotificationTemplateService) {
		_calendarNotificationTemplateService = calendarNotificationTemplateService;
	}

	private CalendarNotificationTemplateService _calendarNotificationTemplateService;
}
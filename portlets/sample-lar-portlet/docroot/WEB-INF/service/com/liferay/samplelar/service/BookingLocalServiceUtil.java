/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.samplelar.service;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableLocalService;

/**
 * Provides the local service utility for Booking. This utility wraps
 * {@link com.liferay.samplelar.service.impl.BookingLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Mate Thurzo
 * @see BookingLocalService
 * @see com.liferay.samplelar.service.base.BookingLocalServiceBaseImpl
 * @see com.liferay.samplelar.service.impl.BookingLocalServiceImpl
 * @generated
 */
public class BookingLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.samplelar.service.impl.BookingLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the booking to the database. Also notifies the appropriate model listeners.
	*
	* @param booking the booking
	* @return the booking that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.samplelar.model.Booking addBooking(
		com.liferay.samplelar.model.Booking booking)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addBooking(booking);
	}

	/**
	* Creates a new booking with the primary key. Does not add the booking to the database.
	*
	* @param bookingId the primary key for the new booking
	* @return the new booking
	*/
	public static com.liferay.samplelar.model.Booking createBooking(
		long bookingId) {
		return getService().createBooking(bookingId);
	}

	/**
	* Deletes the booking with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param bookingId the primary key of the booking
	* @return the booking that was removed
	* @throws PortalException if a booking with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.samplelar.model.Booking deleteBooking(
		long bookingId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteBooking(bookingId);
	}

	/**
	* Deletes the booking from the database. Also notifies the appropriate model listeners.
	*
	* @param booking the booking
	* @return the booking that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.samplelar.model.Booking deleteBooking(
		com.liferay.samplelar.model.Booking booking)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteBooking(booking);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.samplelar.model.impl.BookingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.samplelar.model.impl.BookingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.samplelar.model.Booking fetchBooking(
		long bookingId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchBooking(bookingId);
	}

	/**
	* Returns the booking with the matching UUID and company.
	*
	* @param uuid the booking's UUID
	* @param companyId the primary key of the company
	* @return the matching booking, or <code>null</code> if a matching booking could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.samplelar.model.Booking fetchBookingByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchBookingByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns the booking matching the UUID and group.
	*
	* @param uuid the booking's UUID
	* @param groupId the primary key of the group
	* @return the matching booking, or <code>null</code> if a matching booking could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.samplelar.model.Booking fetchBookingByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchBookingByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns the booking with the primary key.
	*
	* @param bookingId the primary key of the booking
	* @return the booking
	* @throws PortalException if a booking with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.samplelar.model.Booking getBooking(long bookingId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getBooking(bookingId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the booking with the matching UUID and company.
	*
	* @param uuid the booking's UUID
	* @param companyId the primary key of the company
	* @return the matching booking
	* @throws PortalException if a matching booking could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.samplelar.model.Booking getBookingByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getBookingByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns the booking matching the UUID and group.
	*
	* @param uuid the booking's UUID
	* @param groupId the primary key of the group
	* @return the matching booking
	* @throws PortalException if a matching booking could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.samplelar.model.Booking getBookingByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getBookingByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the bookings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.samplelar.model.impl.BookingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of bookings
	* @param end the upper bound of the range of bookings (not inclusive)
	* @return the range of bookings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.samplelar.model.Booking> getBookings(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getBookings(start, end);
	}

	/**
	* Returns the number of bookings.
	*
	* @return the number of bookings
	* @throws SystemException if a system exception occurred
	*/
	public static int getBookingsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getBookingsCount();
	}

	/**
	* Updates the booking in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param booking the booking
	* @return the booking that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.samplelar.model.Booking updateBooking(
		com.liferay.samplelar.model.Booking booking)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateBooking(booking);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return getService().invokeMethod(name, parameterTypes, arguments);
	}

	public static void clearService() {
		_service = null;
	}

	public static BookingLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					BookingLocalService.class.getName());

			if (invokableLocalService instanceof BookingLocalService) {
				_service = (BookingLocalService)invokableLocalService;
			}
			else {
				_service = new BookingLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(BookingLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(BookingLocalService service) {
	}

	private static BookingLocalService _service;
}
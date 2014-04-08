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

package com.liferay.samplelar.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.samplelar.model.Booking;

/**
 * The persistence interface for the booking service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Mate Thurzo
 * @see BookingPersistenceImpl
 * @see BookingUtil
 * @generated
 */
public interface BookingPersistence extends BasePersistence<Booking> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BookingUtil} to access the booking persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the bookings where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching bookings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.samplelar.model.Booking> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the bookings where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.samplelar.model.impl.BookingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of bookings
	* @param end the upper bound of the range of bookings (not inclusive)
	* @return the range of matching bookings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.samplelar.model.Booking> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the bookings where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.samplelar.model.impl.BookingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of bookings
	* @param end the upper bound of the range of bookings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.samplelar.model.Booking> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first booking in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching booking
	* @throws com.liferay.samplelar.NoSuchBookingException if a matching booking could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.samplelar.model.Booking findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.samplelar.NoSuchBookingException;

	/**
	* Returns the first booking in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching booking, or <code>null</code> if a matching booking could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.samplelar.model.Booking fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last booking in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching booking
	* @throws com.liferay.samplelar.NoSuchBookingException if a matching booking could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.samplelar.model.Booking findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.samplelar.NoSuchBookingException;

	/**
	* Returns the last booking in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching booking, or <code>null</code> if a matching booking could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.samplelar.model.Booking fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the bookings before and after the current booking in the ordered set where uuid = &#63;.
	*
	* @param bookingId the primary key of the current booking
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next booking
	* @throws com.liferay.samplelar.NoSuchBookingException if a booking with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.samplelar.model.Booking[] findByUuid_PrevAndNext(
		long bookingId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.samplelar.NoSuchBookingException;

	/**
	* Removes all the bookings where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of bookings where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching bookings
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the booking where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.samplelar.NoSuchBookingException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching booking
	* @throws com.liferay.samplelar.NoSuchBookingException if a matching booking could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.samplelar.model.Booking findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.samplelar.NoSuchBookingException;

	/**
	* Returns the booking where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching booking, or <code>null</code> if a matching booking could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.samplelar.model.Booking fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the booking where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching booking, or <code>null</code> if a matching booking could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.samplelar.model.Booking fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the booking where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the booking that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.samplelar.model.Booking removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.samplelar.NoSuchBookingException;

	/**
	* Returns the number of bookings where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching bookings
	* @throws SystemException if a system exception occurred
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the bookings where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching bookings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.samplelar.model.Booking> findByUuid_C(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the bookings where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.samplelar.model.impl.BookingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of bookings
	* @param end the upper bound of the range of bookings (not inclusive)
	* @return the range of matching bookings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.samplelar.model.Booking> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the bookings where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.samplelar.model.impl.BookingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of bookings
	* @param end the upper bound of the range of bookings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.samplelar.model.Booking> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first booking in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching booking
	* @throws com.liferay.samplelar.NoSuchBookingException if a matching booking could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.samplelar.model.Booking findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.samplelar.NoSuchBookingException;

	/**
	* Returns the first booking in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching booking, or <code>null</code> if a matching booking could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.samplelar.model.Booking fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last booking in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching booking
	* @throws com.liferay.samplelar.NoSuchBookingException if a matching booking could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.samplelar.model.Booking findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.samplelar.NoSuchBookingException;

	/**
	* Returns the last booking in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching booking, or <code>null</code> if a matching booking could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.samplelar.model.Booking fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the bookings before and after the current booking in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param bookingId the primary key of the current booking
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next booking
	* @throws com.liferay.samplelar.NoSuchBookingException if a booking with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.samplelar.model.Booking[] findByUuid_C_PrevAndNext(
		long bookingId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.samplelar.NoSuchBookingException;

	/**
	* Removes all the bookings where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of bookings where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching bookings
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Caches the booking in the entity cache if it is enabled.
	*
	* @param booking the booking
	*/
	public void cacheResult(com.liferay.samplelar.model.Booking booking);

	/**
	* Caches the bookings in the entity cache if it is enabled.
	*
	* @param bookings the bookings
	*/
	public void cacheResult(
		java.util.List<com.liferay.samplelar.model.Booking> bookings);

	/**
	* Creates a new booking with the primary key. Does not add the booking to the database.
	*
	* @param bookingId the primary key for the new booking
	* @return the new booking
	*/
	public com.liferay.samplelar.model.Booking create(long bookingId);

	/**
	* Removes the booking with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param bookingId the primary key of the booking
	* @return the booking that was removed
	* @throws com.liferay.samplelar.NoSuchBookingException if a booking with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.samplelar.model.Booking remove(long bookingId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.samplelar.NoSuchBookingException;

	public com.liferay.samplelar.model.Booking updateImpl(
		com.liferay.samplelar.model.Booking booking)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the booking with the primary key or throws a {@link com.liferay.samplelar.NoSuchBookingException} if it could not be found.
	*
	* @param bookingId the primary key of the booking
	* @return the booking
	* @throws com.liferay.samplelar.NoSuchBookingException if a booking with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.samplelar.model.Booking findByPrimaryKey(long bookingId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.samplelar.NoSuchBookingException;

	/**
	* Returns the booking with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param bookingId the primary key of the booking
	* @return the booking, or <code>null</code> if a booking with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.samplelar.model.Booking fetchByPrimaryKey(long bookingId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the bookings.
	*
	* @return the bookings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.samplelar.model.Booking> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.samplelar.model.Booking> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the bookings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.samplelar.model.impl.BookingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of bookings
	* @param end the upper bound of the range of bookings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of bookings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.samplelar.model.Booking> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the bookings from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of bookings.
	*
	* @return the number of bookings
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}
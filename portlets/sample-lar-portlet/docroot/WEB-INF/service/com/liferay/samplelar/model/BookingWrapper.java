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

package com.liferay.samplelar.model;

import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Booking}.
 * </p>
 *
 * @author Mate Thurzo
 * @see Booking
 * @generated
 */
public class BookingWrapper implements Booking, ModelWrapper<Booking> {
	public BookingWrapper(Booking booking) {
		_booking = booking;
	}

	@Override
	public Class<?> getModelClass() {
		return Booking.class;
	}

	@Override
	public String getModelClassName() {
		return Booking.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("bookingId", getBookingId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("bookingNumber", getBookingNumber());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long bookingId = (Long)attributes.get("bookingId");

		if (bookingId != null) {
			setBookingId(bookingId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String bookingNumber = (String)attributes.get("bookingNumber");

		if (bookingNumber != null) {
			setBookingNumber(bookingNumber);
		}
	}

	/**
	* Returns the primary key of this booking.
	*
	* @return the primary key of this booking
	*/
	@Override
	public long getPrimaryKey() {
		return _booking.getPrimaryKey();
	}

	/**
	* Sets the primary key of this booking.
	*
	* @param primaryKey the primary key of this booking
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_booking.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this booking.
	*
	* @return the uuid of this booking
	*/
	@Override
	public java.lang.String getUuid() {
		return _booking.getUuid();
	}

	/**
	* Sets the uuid of this booking.
	*
	* @param uuid the uuid of this booking
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_booking.setUuid(uuid);
	}

	/**
	* Returns the booking ID of this booking.
	*
	* @return the booking ID of this booking
	*/
	@Override
	public long getBookingId() {
		return _booking.getBookingId();
	}

	/**
	* Sets the booking ID of this booking.
	*
	* @param bookingId the booking ID of this booking
	*/
	@Override
	public void setBookingId(long bookingId) {
		_booking.setBookingId(bookingId);
	}

	/**
	* Returns the group ID of this booking.
	*
	* @return the group ID of this booking
	*/
	@Override
	public long getGroupId() {
		return _booking.getGroupId();
	}

	/**
	* Sets the group ID of this booking.
	*
	* @param groupId the group ID of this booking
	*/
	@Override
	public void setGroupId(long groupId) {
		_booking.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this booking.
	*
	* @return the company ID of this booking
	*/
	@Override
	public long getCompanyId() {
		return _booking.getCompanyId();
	}

	/**
	* Sets the company ID of this booking.
	*
	* @param companyId the company ID of this booking
	*/
	@Override
	public void setCompanyId(long companyId) {
		_booking.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this booking.
	*
	* @return the user ID of this booking
	*/
	@Override
	public long getUserId() {
		return _booking.getUserId();
	}

	/**
	* Sets the user ID of this booking.
	*
	* @param userId the user ID of this booking
	*/
	@Override
	public void setUserId(long userId) {
		_booking.setUserId(userId);
	}

	/**
	* Returns the user uuid of this booking.
	*
	* @return the user uuid of this booking
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _booking.getUserUuid();
	}

	/**
	* Sets the user uuid of this booking.
	*
	* @param userUuid the user uuid of this booking
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_booking.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this booking.
	*
	* @return the user name of this booking
	*/
	@Override
	public java.lang.String getUserName() {
		return _booking.getUserName();
	}

	/**
	* Sets the user name of this booking.
	*
	* @param userName the user name of this booking
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_booking.setUserName(userName);
	}

	/**
	* Returns the create date of this booking.
	*
	* @return the create date of this booking
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _booking.getCreateDate();
	}

	/**
	* Sets the create date of this booking.
	*
	* @param createDate the create date of this booking
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_booking.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this booking.
	*
	* @return the modified date of this booking
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _booking.getModifiedDate();
	}

	/**
	* Sets the modified date of this booking.
	*
	* @param modifiedDate the modified date of this booking
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_booking.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the booking number of this booking.
	*
	* @return the booking number of this booking
	*/
	@Override
	public java.lang.String getBookingNumber() {
		return _booking.getBookingNumber();
	}

	/**
	* Sets the booking number of this booking.
	*
	* @param bookingNumber the booking number of this booking
	*/
	@Override
	public void setBookingNumber(java.lang.String bookingNumber) {
		_booking.setBookingNumber(bookingNumber);
	}

	@Override
	public boolean isNew() {
		return _booking.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_booking.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _booking.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_booking.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _booking.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _booking.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_booking.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _booking.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_booking.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_booking.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_booking.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new BookingWrapper((Booking)_booking.clone());
	}

	@Override
	public int compareTo(com.liferay.samplelar.model.Booking booking) {
		return _booking.compareTo(booking);
	}

	@Override
	public int hashCode() {
		return _booking.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.samplelar.model.Booking> toCacheModel() {
		return _booking.toCacheModel();
	}

	@Override
	public com.liferay.samplelar.model.Booking toEscapedModel() {
		return new BookingWrapper(_booking.toEscapedModel());
	}

	@Override
	public com.liferay.samplelar.model.Booking toUnescapedModel() {
		return new BookingWrapper(_booking.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _booking.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _booking.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_booking.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BookingWrapper)) {
			return false;
		}

		BookingWrapper bookingWrapper = (BookingWrapper)obj;

		if (Validator.equals(_booking, bookingWrapper._booking)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _booking.getStagedModelType();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	@Deprecated
	public Booking getWrappedBooking() {
		return _booking;
	}

	@Override
	public Booking getWrappedModel() {
		return _booking;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _booking.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _booking.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_booking.resetOriginalValues();
	}

	private Booking _booking;
}
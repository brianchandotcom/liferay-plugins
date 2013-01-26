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

package com.liferay.so.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SocialActivityGrouping}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialActivityGrouping
 * @generated
 */
public class SocialActivityGroupingWrapper implements SocialActivityGrouping,
	ModelWrapper<SocialActivityGrouping> {
	public SocialActivityGroupingWrapper(
		SocialActivityGrouping socialActivityGrouping) {
		_socialActivityGrouping = socialActivityGrouping;
	}

	public Class<?> getModelClass() {
		return SocialActivityGrouping.class;
	}

	public String getModelClassName() {
		return SocialActivityGrouping.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("activityGroupingId", getActivityGroupingId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("type", getType());
		attributes.put("activityIds", getActivityIds());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long activityGroupingId = (Long)attributes.get("activityGroupingId");

		if (activityGroupingId != null) {
			setActivityGroupingId(activityGroupingId);
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

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String activityIds = (String)attributes.get("activityIds");

		if (activityIds != null) {
			setActivityIds(activityIds);
		}
	}

	/**
	* Returns the primary key of this social activity grouping.
	*
	* @return the primary key of this social activity grouping
	*/
	public long getPrimaryKey() {
		return _socialActivityGrouping.getPrimaryKey();
	}

	/**
	* Sets the primary key of this social activity grouping.
	*
	* @param primaryKey the primary key of this social activity grouping
	*/
	public void setPrimaryKey(long primaryKey) {
		_socialActivityGrouping.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the activity grouping ID of this social activity grouping.
	*
	* @return the activity grouping ID of this social activity grouping
	*/
	public long getActivityGroupingId() {
		return _socialActivityGrouping.getActivityGroupingId();
	}

	/**
	* Sets the activity grouping ID of this social activity grouping.
	*
	* @param activityGroupingId the activity grouping ID of this social activity grouping
	*/
	public void setActivityGroupingId(long activityGroupingId) {
		_socialActivityGrouping.setActivityGroupingId(activityGroupingId);
	}

	/**
	* Returns the group ID of this social activity grouping.
	*
	* @return the group ID of this social activity grouping
	*/
	public long getGroupId() {
		return _socialActivityGrouping.getGroupId();
	}

	/**
	* Sets the group ID of this social activity grouping.
	*
	* @param groupId the group ID of this social activity grouping
	*/
	public void setGroupId(long groupId) {
		_socialActivityGrouping.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this social activity grouping.
	*
	* @return the company ID of this social activity grouping
	*/
	public long getCompanyId() {
		return _socialActivityGrouping.getCompanyId();
	}

	/**
	* Sets the company ID of this social activity grouping.
	*
	* @param companyId the company ID of this social activity grouping
	*/
	public void setCompanyId(long companyId) {
		_socialActivityGrouping.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this social activity grouping.
	*
	* @return the user ID of this social activity grouping
	*/
	public long getUserId() {
		return _socialActivityGrouping.getUserId();
	}

	/**
	* Sets the user ID of this social activity grouping.
	*
	* @param userId the user ID of this social activity grouping
	*/
	public void setUserId(long userId) {
		_socialActivityGrouping.setUserId(userId);
	}

	/**
	* Returns the user uuid of this social activity grouping.
	*
	* @return the user uuid of this social activity grouping
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityGrouping.getUserUuid();
	}

	/**
	* Sets the user uuid of this social activity grouping.
	*
	* @param userUuid the user uuid of this social activity grouping
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_socialActivityGrouping.setUserUuid(userUuid);
	}

	/**
	* Returns the create date of this social activity grouping.
	*
	* @return the create date of this social activity grouping
	*/
	public java.util.Date getCreateDate() {
		return _socialActivityGrouping.getCreateDate();
	}

	/**
	* Sets the create date of this social activity grouping.
	*
	* @param createDate the create date of this social activity grouping
	*/
	public void setCreateDate(java.util.Date createDate) {
		_socialActivityGrouping.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this social activity grouping.
	*
	* @return the modified date of this social activity grouping
	*/
	public java.util.Date getModifiedDate() {
		return _socialActivityGrouping.getModifiedDate();
	}

	/**
	* Sets the modified date of this social activity grouping.
	*
	* @param modifiedDate the modified date of this social activity grouping
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_socialActivityGrouping.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the fully qualified class name of this social activity grouping.
	*
	* @return the fully qualified class name of this social activity grouping
	*/
	public java.lang.String getClassName() {
		return _socialActivityGrouping.getClassName();
	}

	public void setClassName(java.lang.String className) {
		_socialActivityGrouping.setClassName(className);
	}

	/**
	* Returns the class name ID of this social activity grouping.
	*
	* @return the class name ID of this social activity grouping
	*/
	public long getClassNameId() {
		return _socialActivityGrouping.getClassNameId();
	}

	/**
	* Sets the class name ID of this social activity grouping.
	*
	* @param classNameId the class name ID of this social activity grouping
	*/
	public void setClassNameId(long classNameId) {
		_socialActivityGrouping.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this social activity grouping.
	*
	* @return the class p k of this social activity grouping
	*/
	public long getClassPK() {
		return _socialActivityGrouping.getClassPK();
	}

	/**
	* Sets the class p k of this social activity grouping.
	*
	* @param classPK the class p k of this social activity grouping
	*/
	public void setClassPK(long classPK) {
		_socialActivityGrouping.setClassPK(classPK);
	}

	/**
	* Returns the type of this social activity grouping.
	*
	* @return the type of this social activity grouping
	*/
	public int getType() {
		return _socialActivityGrouping.getType();
	}

	/**
	* Sets the type of this social activity grouping.
	*
	* @param type the type of this social activity grouping
	*/
	public void setType(int type) {
		_socialActivityGrouping.setType(type);
	}

	/**
	* Returns the activity IDs of this social activity grouping.
	*
	* @return the activity IDs of this social activity grouping
	*/
	public java.lang.String getActivityIds() {
		return _socialActivityGrouping.getActivityIds();
	}

	/**
	* Sets the activity IDs of this social activity grouping.
	*
	* @param activityIds the activity IDs of this social activity grouping
	*/
	public void setActivityIds(java.lang.String activityIds) {
		_socialActivityGrouping.setActivityIds(activityIds);
	}

	public boolean isNew() {
		return _socialActivityGrouping.isNew();
	}

	public void setNew(boolean n) {
		_socialActivityGrouping.setNew(n);
	}

	public boolean isCachedModel() {
		return _socialActivityGrouping.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_socialActivityGrouping.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _socialActivityGrouping.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _socialActivityGrouping.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_socialActivityGrouping.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _socialActivityGrouping.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_socialActivityGrouping.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new SocialActivityGroupingWrapper((SocialActivityGrouping)_socialActivityGrouping.clone());
	}

	public int compareTo(SocialActivityGrouping socialActivityGrouping) {
		return _socialActivityGrouping.compareTo(socialActivityGrouping);
	}

	@Override
	public int hashCode() {
		return _socialActivityGrouping.hashCode();
	}

	public com.liferay.portal.model.CacheModel<SocialActivityGrouping> toCacheModel() {
		return _socialActivityGrouping.toCacheModel();
	}

	public SocialActivityGrouping toEscapedModel() {
		return new SocialActivityGroupingWrapper(_socialActivityGrouping.toEscapedModel());
	}

	public SocialActivityGrouping toUnescapedModel() {
		return new SocialActivityGroupingWrapper(_socialActivityGrouping.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _socialActivityGrouping.toString();
	}

	public java.lang.String toXmlString() {
		return _socialActivityGrouping.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialActivityGrouping.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public SocialActivityGrouping getWrappedSocialActivityGrouping() {
		return _socialActivityGrouping;
	}

	public SocialActivityGrouping getWrappedModel() {
		return _socialActivityGrouping;
	}

	public void resetOriginalValues() {
		_socialActivityGrouping.resetOriginalValues();
	}

	private SocialActivityGrouping _socialActivityGrouping;
}
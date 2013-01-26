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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class SocialActivityGroupingSoap implements Serializable {
	public static SocialActivityGroupingSoap toSoapModel(
		SocialActivityGrouping model) {
		SocialActivityGroupingSoap soapModel = new SocialActivityGroupingSoap();

		soapModel.setActivityGroupingId(model.getActivityGroupingId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setType(model.getType());
		soapModel.setActivityIds(model.getActivityIds());

		return soapModel;
	}

	public static SocialActivityGroupingSoap[] toSoapModels(
		SocialActivityGrouping[] models) {
		SocialActivityGroupingSoap[] soapModels = new SocialActivityGroupingSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SocialActivityGroupingSoap[][] toSoapModels(
		SocialActivityGrouping[][] models) {
		SocialActivityGroupingSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SocialActivityGroupingSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SocialActivityGroupingSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SocialActivityGroupingSoap[] toSoapModels(
		List<SocialActivityGrouping> models) {
		List<SocialActivityGroupingSoap> soapModels = new ArrayList<SocialActivityGroupingSoap>(models.size());

		for (SocialActivityGrouping model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SocialActivityGroupingSoap[soapModels.size()]);
	}

	public SocialActivityGroupingSoap() {
	}

	public long getPrimaryKey() {
		return _activityGroupingId;
	}

	public void setPrimaryKey(long pk) {
		setActivityGroupingId(pk);
	}

	public long getActivityGroupingId() {
		return _activityGroupingId;
	}

	public void setActivityGroupingId(long activityGroupingId) {
		_activityGroupingId = activityGroupingId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public String getActivityIds() {
		return _activityIds;
	}

	public void setActivityIds(String activityIds) {
		_activityIds = activityIds;
	}

	private long _activityGroupingId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _classPK;
	private int _type;
	private String _activityIds;
}
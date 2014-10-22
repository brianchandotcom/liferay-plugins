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

package com.liferay.pushnotifications.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.pushnotifications.service.http.PushNotificationsEntryServiceSoap}.
 *
 * @author Silvio Santos
 * @see com.liferay.pushnotifications.service.http.PushNotificationsEntryServiceSoap
 * @generated
 */
@ProviderType
public class PushNotificationsEntrySoap implements Serializable {
	public static PushNotificationsEntrySoap toSoapModel(
		PushNotificationsEntry model) {
		PushNotificationsEntrySoap soapModel = new PushNotificationsEntrySoap();

		soapModel.setPushNotificationsEntryId(model.getPushNotificationsEntryId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setParentEntryId(model.getParentEntryId());
		soapModel.setPayload(model.getPayload());

		return soapModel;
	}

	public static PushNotificationsEntrySoap[] toSoapModels(
		PushNotificationsEntry[] models) {
		PushNotificationsEntrySoap[] soapModels = new PushNotificationsEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static PushNotificationsEntrySoap[][] toSoapModels(
		PushNotificationsEntry[][] models) {
		PushNotificationsEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new PushNotificationsEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new PushNotificationsEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static PushNotificationsEntrySoap[] toSoapModels(
		List<PushNotificationsEntry> models) {
		List<PushNotificationsEntrySoap> soapModels = new ArrayList<PushNotificationsEntrySoap>(models.size());

		for (PushNotificationsEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new PushNotificationsEntrySoap[soapModels.size()]);
	}

	public PushNotificationsEntrySoap() {
	}

	public long getPrimaryKey() {
		return _pushNotificationsEntryId;
	}

	public void setPrimaryKey(long pk) {
		setPushNotificationsEntryId(pk);
	}

	public long getPushNotificationsEntryId() {
		return _pushNotificationsEntryId;
	}

	public void setPushNotificationsEntryId(long pushNotificationsEntryId) {
		_pushNotificationsEntryId = pushNotificationsEntryId;
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

	public long getParentEntryId() {
		return _parentEntryId;
	}

	public void setParentEntryId(long parentEntryId) {
		_parentEntryId = parentEntryId;
	}

	public String getPayload() {
		return _payload;
	}

	public void setPayload(String payload) {
		_payload = payload;
	}

	private long _pushNotificationsEntryId;
	private long _userId;
	private Date _createDate;
	private long _parentEntryId;
	private String _payload;
}
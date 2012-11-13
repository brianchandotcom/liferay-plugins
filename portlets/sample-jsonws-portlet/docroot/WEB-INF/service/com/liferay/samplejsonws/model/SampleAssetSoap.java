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

package com.liferay.samplejsonws.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.samplejsonws.service.http.SampleAssetServiceSoap}.
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.samplejsonws.service.http.SampleAssetServiceSoap
 * @generated
 */
public class SampleAssetSoap implements Serializable {
	public static SampleAssetSoap toSoapModel(SampleAsset model) {
		SampleAssetSoap soapModel = new SampleAssetSoap();

		soapModel.setAssetId(model.getAssetId());
		soapModel.setUserId(model.getUserId());
		soapModel.setName(model.getName());
		soapModel.setDate(model.getDate());

		return soapModel;
	}

	public static SampleAssetSoap[] toSoapModels(SampleAsset[] models) {
		SampleAssetSoap[] soapModels = new SampleAssetSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SampleAssetSoap[][] toSoapModels(SampleAsset[][] models) {
		SampleAssetSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SampleAssetSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SampleAssetSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SampleAssetSoap[] toSoapModels(List<SampleAsset> models) {
		List<SampleAssetSoap> soapModels = new ArrayList<SampleAssetSoap>(models.size());

		for (SampleAsset model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SampleAssetSoap[soapModels.size()]);
	}

	public SampleAssetSoap() {
	}

	public long getPrimaryKey() {
		return _assetId;
	}

	public void setPrimaryKey(long pk) {
		setAssetId(pk);
	}

	public long getAssetId() {
		return _assetId;
	}

	public void setAssetId(long assetId) {
		_assetId = assetId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public Date getDate() {
		return _date;
	}

	public void setDate(Date date) {
		_date = date;
	}

	private long _assetId;
	private long _userId;
	private String _name;
	private Date _date;
}
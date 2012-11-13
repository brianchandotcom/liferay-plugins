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

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class SampleAssetClp extends BaseModelImpl<SampleAsset>
	implements SampleAsset {
	public SampleAssetClp() {
	}

	public Class<?> getModelClass() {
		return SampleAsset.class;
	}

	public String getModelClassName() {
		return SampleAsset.class.getName();
	}

	public long getPrimaryKey() {
		return _assetId;
	}

	public void setPrimaryKey(long primaryKey) {
		setAssetId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_assetId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
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

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
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

	@Override
	public SampleAsset toEscapedModel() {
		return (SampleAsset)Proxy.newProxyInstance(SampleAsset.class.getClassLoader(),
			new Class[] { SampleAsset.class }, new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		SampleAssetClp clone = new SampleAssetClp();

		clone.setAssetId(getAssetId());
		clone.setUserId(getUserId());
		clone.setName(getName());
		clone.setDate(getDate());

		return clone;
	}

	public int compareTo(SampleAsset sampleAsset) {
		long primaryKey = sampleAsset.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		SampleAssetClp sampleAsset = null;

		try {
			sampleAsset = (SampleAssetClp)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = sampleAsset.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{assetId=");
		sb.append(getAssetId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", date=");
		sb.append(getDate());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(16);

		sb.append("<model><model-name>");
		sb.append("com.liferay.samplejsonws.model.SampleAsset");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>assetId</column-name><column-value><![CDATA[");
		sb.append(getAssetId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>date</column-name><column-value><![CDATA[");
		sb.append(getDate());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _assetId;
	private long _userId;
	private String _userUuid;
	private String _name;
	private Date _date;
}
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

package com.liferay.samplejsonws.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.samplejsonws.model.SampleAsset;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing SampleAsset in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see SampleAsset
 * @generated
 */
public class SampleAssetCacheModel implements CacheModel<SampleAsset>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{assetId=");
		sb.append(assetId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", date=");
		sb.append(date);
		sb.append("}");

		return sb.toString();
	}

	public SampleAsset toEntityModel() {
		SampleAssetImpl sampleAssetImpl = new SampleAssetImpl();

		sampleAssetImpl.setAssetId(assetId);
		sampleAssetImpl.setUserId(userId);

		if (name == null) {
			sampleAssetImpl.setName(StringPool.BLANK);
		}
		else {
			sampleAssetImpl.setName(name);
		}

		if (date == Long.MIN_VALUE) {
			sampleAssetImpl.setDate(null);
		}
		else {
			sampleAssetImpl.setDate(new Date(date));
		}

		sampleAssetImpl.resetOriginalValues();

		return sampleAssetImpl;
	}

	public long assetId;
	public long userId;
	public String name;
	public long date;
}
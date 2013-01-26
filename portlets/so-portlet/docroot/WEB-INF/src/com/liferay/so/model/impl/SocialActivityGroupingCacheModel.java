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

package com.liferay.so.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.so.model.SocialActivityGrouping;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SocialActivityGrouping in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityGrouping
 * @generated
 */
public class SocialActivityGroupingCacheModel implements CacheModel<SocialActivityGrouping>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{activityGroupingId=");
		sb.append(activityGroupingId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", type=");
		sb.append(type);
		sb.append(", activityIds=");
		sb.append(activityIds);
		sb.append("}");

		return sb.toString();
	}

	public SocialActivityGrouping toEntityModel() {
		SocialActivityGroupingImpl socialActivityGroupingImpl = new SocialActivityGroupingImpl();

		socialActivityGroupingImpl.setActivityGroupingId(activityGroupingId);
		socialActivityGroupingImpl.setGroupId(groupId);
		socialActivityGroupingImpl.setCompanyId(companyId);
		socialActivityGroupingImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			socialActivityGroupingImpl.setCreateDate(null);
		}
		else {
			socialActivityGroupingImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			socialActivityGroupingImpl.setModifiedDate(null);
		}
		else {
			socialActivityGroupingImpl.setModifiedDate(new Date(modifiedDate));
		}

		socialActivityGroupingImpl.setClassNameId(classNameId);
		socialActivityGroupingImpl.setClassPK(classPK);
		socialActivityGroupingImpl.setType(type);

		if (activityIds == null) {
			socialActivityGroupingImpl.setActivityIds(StringPool.BLANK);
		}
		else {
			socialActivityGroupingImpl.setActivityIds(activityIds);
		}

		socialActivityGroupingImpl.resetOriginalValues();

		return socialActivityGroupingImpl;
	}

	public void readExternal(ObjectInput objectInput) throws IOException {
		activityGroupingId = objectInput.readLong();
		groupId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		classNameId = objectInput.readLong();
		classPK = objectInput.readLong();
		type = objectInput.readInt();
		activityIds = objectInput.readUTF();
	}

	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(activityGroupingId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
		objectOutput.writeLong(classNameId);
		objectOutput.writeLong(classPK);
		objectOutput.writeInt(type);

		if (activityIds == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(activityIds);
		}
	}

	public long activityGroupingId;
	public long groupId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public int type;
	public String activityIds;
}
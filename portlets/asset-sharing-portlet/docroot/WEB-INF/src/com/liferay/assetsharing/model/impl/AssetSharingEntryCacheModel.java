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

package com.liferay.assetsharing.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.assetsharing.model.AssetSharingEntry;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing AssetSharingEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see AssetSharingEntry
 * @generated
 */
@ProviderType
public class AssetSharingEntryCacheModel implements CacheModel<AssetSharingEntry>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", sharedClassNameId=");
		sb.append(sharedClassNameId);
		sb.append(", sharedClassPK=");
		sb.append(sharedClassPK);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetSharingEntry toEntityModel() {
		AssetSharingEntryImpl assetSharingEntryImpl = new AssetSharingEntryImpl();

		assetSharingEntryImpl.setClassNameId(classNameId);
		assetSharingEntryImpl.setClassPK(classPK);
		assetSharingEntryImpl.setSharedClassNameId(sharedClassNameId);
		assetSharingEntryImpl.setSharedClassPK(sharedClassPK);

		assetSharingEntryImpl.resetOriginalValues();

		return assetSharingEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		classNameId = objectInput.readLong();
		classPK = objectInput.readLong();
		sharedClassNameId = objectInput.readLong();
		sharedClassPK = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(classNameId);
		objectOutput.writeLong(classPK);
		objectOutput.writeLong(sharedClassNameId);
		objectOutput.writeLong(sharedClassPK);
	}

	public long classNameId;
	public long classPK;
	public long sharedClassNameId;
	public long sharedClassPK;
}
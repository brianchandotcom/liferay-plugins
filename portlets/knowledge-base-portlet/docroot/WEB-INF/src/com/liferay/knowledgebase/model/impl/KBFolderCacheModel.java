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

package com.liferay.knowledgebase.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.knowledgebase.model.KBFolder;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing KBFolder in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see KBFolder
 * @generated
 */
@ProviderType
public class KBFolderCacheModel implements CacheModel<KBFolder>, Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", kbFolderId=");
		sb.append(kbFolderId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", parentKbFolderId=");
		sb.append(parentKbFolderId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public KBFolder toEntityModel() {
		KBFolderImpl kbFolderImpl = new KBFolderImpl();

		if (uuid == null) {
			kbFolderImpl.setUuid(StringPool.BLANK);
		}
		else {
			kbFolderImpl.setUuid(uuid);
		}

		kbFolderImpl.setKbFolderId(kbFolderId);
		kbFolderImpl.setGroupId(groupId);
		kbFolderImpl.setCompanyId(companyId);
		kbFolderImpl.setUserId(userId);

		if (userName == null) {
			kbFolderImpl.setUserName(StringPool.BLANK);
		}
		else {
			kbFolderImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			kbFolderImpl.setCreateDate(null);
		}
		else {
			kbFolderImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			kbFolderImpl.setModifiedDate(null);
		}
		else {
			kbFolderImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			kbFolderImpl.setName(StringPool.BLANK);
		}
		else {
			kbFolderImpl.setName(name);
		}

		if (description == null) {
			kbFolderImpl.setDescription(StringPool.BLANK);
		}
		else {
			kbFolderImpl.setDescription(description);
		}

		kbFolderImpl.setParentKbFolderId(parentKbFolderId);

		kbFolderImpl.resetOriginalValues();

		return kbFolderImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		kbFolderId = objectInput.readLong();
		groupId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		parentKbFolderId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(kbFolderId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeLong(parentKbFolderId);
	}

	public String uuid;
	public long kbFolderId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public long parentKbFolderId;
}
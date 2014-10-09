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

package com.liferay.assetsharing.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class AssetSharingEntryPK implements Comparable<AssetSharingEntryPK>,
	Serializable {
	public long classNameId;
	public long classPK;
	public long sharedClassNameId;
	public long sharedClassPK;

	public AssetSharingEntryPK() {
	}

	public AssetSharingEntryPK(long classNameId, long classPK,
		long sharedClassNameId, long sharedClassPK) {
		this.classNameId = classNameId;
		this.classPK = classPK;
		this.sharedClassNameId = sharedClassNameId;
		this.sharedClassPK = sharedClassPK;
	}

	public long getClassNameId() {
		return classNameId;
	}

	public void setClassNameId(long classNameId) {
		this.classNameId = classNameId;
	}

	public long getClassPK() {
		return classPK;
	}

	public void setClassPK(long classPK) {
		this.classPK = classPK;
	}

	public long getSharedClassNameId() {
		return sharedClassNameId;
	}

	public void setSharedClassNameId(long sharedClassNameId) {
		this.sharedClassNameId = sharedClassNameId;
	}

	public long getSharedClassPK() {
		return sharedClassPK;
	}

	public void setSharedClassPK(long sharedClassPK) {
		this.sharedClassPK = sharedClassPK;
	}

	@Override
	public int compareTo(AssetSharingEntryPK pk) {
		if (pk == null) {
			return -1;
		}

		int value = 0;

		if (classNameId < pk.classNameId) {
			value = -1;
		}
		else if (classNameId > pk.classNameId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (classPK < pk.classPK) {
			value = -1;
		}
		else if (classPK > pk.classPK) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (sharedClassNameId < pk.sharedClassNameId) {
			value = -1;
		}
		else if (sharedClassNameId > pk.sharedClassNameId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (sharedClassPK < pk.sharedClassPK) {
			value = -1;
		}
		else if (sharedClassPK > pk.sharedClassPK) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetSharingEntryPK)) {
			return false;
		}

		AssetSharingEntryPK pk = (AssetSharingEntryPK)obj;

		if ((classNameId == pk.classNameId) && (classPK == pk.classPK) &&
				(sharedClassNameId == pk.sharedClassNameId) &&
				(sharedClassPK == pk.sharedClassPK)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (String.valueOf(classNameId) + String.valueOf(classPK) +
		String.valueOf(sharedClassNameId) + String.valueOf(sharedClassPK)).hashCode();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(20);

		sb.append(StringPool.OPEN_CURLY_BRACE);

		sb.append("classNameId");
		sb.append(StringPool.EQUAL);
		sb.append(classNameId);

		sb.append(StringPool.COMMA);
		sb.append(StringPool.SPACE);
		sb.append("classPK");
		sb.append(StringPool.EQUAL);
		sb.append(classPK);

		sb.append(StringPool.COMMA);
		sb.append(StringPool.SPACE);
		sb.append("sharedClassNameId");
		sb.append(StringPool.EQUAL);
		sb.append(sharedClassNameId);

		sb.append(StringPool.COMMA);
		sb.append(StringPool.SPACE);
		sb.append("sharedClassPK");
		sb.append(StringPool.EQUAL);
		sb.append(sharedClassPK);

		sb.append(StringPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}
}
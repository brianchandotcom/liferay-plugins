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

package com.liferay.sync.model;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * @author Michael Young
 * @author Shinn Lok
 */
@JSON
public class SyncDLObjectUpdate {

	public SyncDLObjectUpdate(
		List<SyncDLObject> syncDLObjects, int resultsTotal,
		long lastAccessTime) {

		_syncDLObjects = syncDLObjects;
		_resultsTotal = resultsTotal;
		_lastAccessTime = lastAccessTime;
	}

	public long getLastAccessTime() {
		return _lastAccessTime;
	}

	public int getResultsTotal() {
		return _resultsTotal;
	}

	@JSON
	public List<SyncDLObject> getSyncDLObjects() {
		return _syncDLObjects;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler((_syncDLObjects.size() * 153) + 5);

		sb.append("{\"lastAccessTime\":");
		sb.append(_lastAccessTime);
		sb.append(",\"resultsTotal\":");
		sb.append(_resultsTotal);
		sb.append(",\"syncDLObjects\":[");

		for (int i = 0; i < _syncDLObjects.size(); i++) {
			sb.append(StringPool.OPEN_CURLY_BRACE);

			SyncDLObject syncDLObject = _syncDLObjects.get(i);

			for (int j = 0; j < _FIELDS.length; j++) {
				String field = _FIELDS[j];

				sb.append(StringPool.QUOTE);
				sb.append(field);
				sb.append("\":");

				Object value = BeanPropertiesUtil.getObject(
					syncDLObject, field);

				if (value instanceof String) {
					sb.append(StringPool.QUOTE);
					sb.append(StringEscapeUtils.escapeJava((String)value));
					sb.append(StringPool.QUOTE);
				}
				else {
					sb.append(value);
				}

				if (j != (_FIELDS.length - 1)) {
					sb.append(StringPool.COMMA);
				}
			}

			sb.append(StringPool.CLOSE_CURLY_BRACE);

			if (i != (_syncDLObjects.size() - 1)) {
				sb.append(StringPool.COMMA);
			}
		}

		sb.append("]}");

		return sb.toString();
	}

	private static final String[] _FIELDS = {
		"checksum", "changeLog", "companyId", "createTime", "description",
		"extension", "extraSettings", "event", "lockExpirationDate",
		"lockUserId", "lockUserName", "mimeType", "modifiedTime", "name",
		"parentFolderId", "repositoryId", "size", "syncDLObjectId", "type",
		"typePK", "typeUuid", "userId", "userName", "version", "versionId"
	};

	private long _lastAccessTime;
	private int _resultsTotal;
	private List<SyncDLObject> _syncDLObjects;

}
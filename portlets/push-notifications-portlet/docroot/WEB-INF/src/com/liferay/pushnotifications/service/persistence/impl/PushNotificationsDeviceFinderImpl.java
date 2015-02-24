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

package com.liferay.pushnotifications.service.persistence.impl;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.pushnotifications.model.PushNotificationsDevice;
import com.liferay.pushnotifications.model.impl.PushNotificationsDeviceImpl;
import com.liferay.pushnotifications.service.persistence.PushNotificationsDeviceFinder;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.List;

/**
 * @author Bruno Farache
 */
public class PushNotificationsDeviceFinderImpl
	extends BasePersistenceImpl<PushNotificationsDevice>
	implements PushNotificationsDeviceFinder {

	public static final String FIND_BY_U_P =
		PushNotificationsDeviceFinder.class.getName() + ".findByU_P";

	@Override
	public List<PushNotificationsDevice> findByU_P(
		long[] userIds, String platform, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_U_P);

			sql = StringUtil.replace(sql, "[$USER_ID$]", getUserIds(userIds));

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity(
				"PushNotificationsDevice", PushNotificationsDeviceImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userIds);
			qPos.add(platform);

			return (List<PushNotificationsDevice>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getUserIds(long[] userIds) {
		if (userIds.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(userIds.length * 2);

		sb.append(StringPool.OPEN_PARENTHESIS);

		for (int i = 0; i < userIds.length; i++) {
			sb.append("userId = ?");

			if ((i + 1) < userIds.length) {
				sb.append(" OR ");
			}
		}

		sb.append(") AND");

		return sb.toString();
	}

}
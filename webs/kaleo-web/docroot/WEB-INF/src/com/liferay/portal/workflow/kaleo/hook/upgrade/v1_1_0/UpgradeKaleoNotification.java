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

package com.liferay.portal.workflow.kaleo.hook.upgrade.v1_1_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoNotificationModelImpl;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class UpgradeKaleoNotification extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		Map<Long, Long> kaleoNotificationIdMappings =
			getKaleoNotificationIdMappings();

		upgradeTable(
			KaleoNotificationModelImpl.TABLE_NAME,
			KaleoNotificationModelImpl.TABLE_COLUMNS,
			KaleoNotificationModelImpl.TABLE_SQL_CREATE,
			_TABLE_SQL_ADD_INDEXES);

		updateNotificationIdMappings(kaleoNotificationIdMappings);
	}

	protected Map<Long, Long> getKaleoNotificationIdMappings()
		throws SQLException {

		Map<Long, Long> kaleoNotificationIds = new HashMap<Long, Long>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select kaleoNotificationId, kaleoNodeId from " +
				KaleoNotificationModelImpl.TABLE_NAME);

			rs = ps.executeQuery();

			while (rs.next()) {
				long kaleoNotificationId = rs.getLong("kaleoNotificationId");
				long kaleoNodeId = rs.getLong("kaleoNodeId");

				if (_log.isDebugEnabled()) {
					_log.debug(
						"kaleoNotificationId = " + kaleoNotificationId +
						" kaleoNodeId: " + kaleoNodeId);
				}

				kaleoNotificationIds.put(kaleoNotificationId, kaleoNodeId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return kaleoNotificationIds;
	}

	protected void updateNotificationIdMappings(
			Map<Long, Long> kaleoNotificationIdMappings)
		throws IOException, SQLException {

		for (Map.Entry<Long, Long> kaleoNotificationMapping :
				kaleoNotificationIdMappings.entrySet()) {

			long kaleoNotificationId = kaleoNotificationMapping.getKey();
			long kaleoNodeId = kaleoNotificationMapping.getValue();

			StringBundler sb = new StringBundler(8);

			sb.append("update ");
			sb.append(KaleoNotificationModelImpl.TABLE_NAME);
			sb.append(" set kaleoClassName = ");
			sb.append("'com.liferay.portal.workflow.kaleo.model.KaleoNode', ");
			sb.append("kaleoClassPK = ");
			sb.append(kaleoNodeId);
			sb.append(" where kaleoNotificationId = ");
			sb.append(kaleoNotificationId);

			runSQL(sb.toString());
		}
	}

	private static final String[] _TABLE_SQL_ADD_INDEXES = {
		"create index IX_38829497 on KaleoNotification (companyId)",
		"create index IX_F3362E93 on KaleoNotification (kaleoClassName, kaleoClassPK, executionType)",
		"create index IX_4B968E8D on KaleoNotification (kaleoDefinitionId)"
	};

	private static Log _log = LogFactoryUtil.getLog(
		UpgradeKaleoNotification.class);

}
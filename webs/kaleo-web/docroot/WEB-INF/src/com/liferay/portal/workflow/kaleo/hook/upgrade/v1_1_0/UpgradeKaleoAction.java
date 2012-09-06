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
import com.liferay.portal.workflow.kaleo.model.impl.KaleoActionModelImpl;

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
public class UpgradeKaleoAction extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		Map<Long, Long> kaleoActionIdMappings = getKaleoActionIdMappings();

		upgradeTable(
			KaleoActionModelImpl.TABLE_NAME, KaleoActionModelImpl.TABLE_COLUMNS,
			KaleoActionModelImpl.TABLE_SQL_CREATE, _TABLE_SQL_ADD_INDEXES);

		updateKaleoActionIdMappings(kaleoActionIdMappings);
	}

	protected Map<Long, Long> getKaleoActionIdMappings() throws SQLException {

		Map<Long, Long> kaleoNodeIds = new HashMap<Long, Long>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select kaleoActionId, kaleoNodeId from " +
				KaleoActionModelImpl.TABLE_NAME);

			rs = ps.executeQuery();

			while (rs.next()) {
				long kaleoActionId = rs.getLong("kaleoActionId");
				long kaleoNodeId = rs.getLong("kaleoNodeId");

				if (_log.isDebugEnabled()) {
					_log.debug(
						"kaleoActionId = " + kaleoActionId +
						" kaleoNodeId: " + kaleoNodeId);
				}

				kaleoNodeIds.put(kaleoActionId, kaleoNodeId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return kaleoNodeIds;
	}

	protected void updateKaleoActionIdMappings(
			Map<Long, Long> kaleoActionIdMappings)
		throws IOException, SQLException {

		for (Map.Entry<Long, Long> kaleoActionIdMapping :
				kaleoActionIdMappings.entrySet()) {

			long kaleoActionId = kaleoActionIdMapping.getKey();
			long kaleoNodeId = kaleoActionIdMapping.getValue();

			StringBundler sb = new StringBundler(8);

			sb.append("update ");
			sb.append(KaleoActionModelImpl.TABLE_NAME);
			sb.append(" set kaleoClassName = ");
			sb.append("'com.liferay.portal.workflow.kaleo.model.KaleoNode', ");
			sb.append("kaleoClassPK = ");
			sb.append(kaleoNodeId);
			sb.append(" where kaleoActionId = ");
			sb.append(kaleoActionId);

			runSQL(sb.toString());
		}
	}

	private static final String[] _TABLE_SQL_ADD_INDEXES = {
		"create index IX_50E9112C on KaleoAction (companyId)",
		"create index IX_B88DF9B1 on KaleoAction (kaleoClassName, kaleoClassPK, executionType)",
		"create index IX_F95A622 on KaleoAction (kaleoDefinitionId)",
	};

	private static Log _log = LogFactoryUtil.getLog(UpgradeKaleoAction.class);

}
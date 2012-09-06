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
import com.liferay.portal.workflow.kaleo.model.impl.KaleoLogModelImpl;

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
public class UpgradeKaleoLog extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		Map<Long, Long> kaleoLogIdMappings = getKaleoLogIdMappings();

		upgradeTable(
			KaleoLogModelImpl.TABLE_NAME, KaleoLogModelImpl.TABLE_COLUMNS,
			KaleoLogModelImpl.TABLE_SQL_CREATE, _TABLE_SQL_ADD_INDEXES);

		updateKaleoLogIdMappings(kaleoLogIdMappings);
	}

	protected Map<Long, Long> getKaleoLogIdMappings() throws SQLException {

		Map<Long, Long> kaleoLogIds = new HashMap<Long, Long>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select kaleoLogId, kaleoNodeId from " +
				KaleoLogModelImpl.TABLE_NAME);

			rs = ps.executeQuery();

			while (rs.next()) {
				long kaleoLogId = rs.getLong("kaleoLogId");
				long kaleoNodeId = rs.getLong("kaleoNodeId");

				if (_log.isDebugEnabled()) {
					_log.debug(
						"kaleoLogId = " + kaleoLogId +
						" kaleoNodeId: " + kaleoNodeId);
				}

				kaleoLogIds.put(kaleoLogId, kaleoNodeId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return kaleoLogIds;
	}

	protected void updateKaleoLogIdMappings(
			Map<Long, Long> kaleoLogIdMappings)
		throws IOException, SQLException {

		for (Map.Entry<Long, Long> kaleoLogIdMapping :
				kaleoLogIdMappings.entrySet()) {

			long kaleoLogId = kaleoLogIdMapping.getKey();
			long kaleoNodeId = kaleoLogIdMapping.getValue();

			StringBundler sb = new StringBundler(8);

			sb.append("update ");
			sb.append(KaleoLogModelImpl.TABLE_NAME);
			sb.append(" set kaleoClassName = ");
			sb.append("'com.liferay.portal.workflow.kaleo.model.KaleoNode', ");
			sb.append("kaleoClassPK = ");
			sb.append(kaleoNodeId);
			sb.append(" where kaleoLogId = ");
			sb.append(kaleoLogId);

			runSQL(sb.toString());
		}
	}

	private static final String[] _TABLE_SQL_ADD_INDEXES = {
		"create index IX_73B5F4DE on KaleoLog (companyId)",
		"create index IX_E66A153A on KaleoLog (kaleoClassName, kaleoClassPK, kaleoInstanceTokenId, type_)",
		"create index IX_6C64B7D4 on KaleoLog (kaleoDefinitionId)",
		"create index IX_5BC6AB16 on KaleoLog (kaleoInstanceId)",
		"create index IX_470B9FF8 on KaleoLog (kaleoInstanceTokenId, type_)",
		"create index IX_B0CDCA38 on KaleoLog (kaleoTaskInstanceTokenId)"
	};

	private static Log _log = LogFactoryUtil.getLog(UpgradeKaleoLog.class);

}
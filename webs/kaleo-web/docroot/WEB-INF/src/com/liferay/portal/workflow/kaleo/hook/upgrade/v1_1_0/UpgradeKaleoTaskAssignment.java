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
import com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskAssignmentModelImpl;

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
public class UpgradeKaleoTaskAssignment extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		Map<Long, Long> kaleoTaskAssignmentIdMappings =
			getKaleoTaskAssignmentIdMappings();

		upgradeTable(
			KaleoTaskAssignmentModelImpl.TABLE_NAME,
			KaleoTaskAssignmentModelImpl.TABLE_COLUMNS,
			KaleoTaskAssignmentModelImpl.TABLE_SQL_CREATE,
			_TABLE_SQL_ADD_INDEXES);

		updateKaleoTaskAssignmentIdMappings(kaleoTaskAssignmentIdMappings);
	}

	protected Map<Long, Long> getKaleoTaskAssignmentIdMappings()
		throws SQLException {

		Map<Long, Long> kaleoTaskAssignmentIds = new HashMap<Long, Long>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select kaleoTaskAssignmentId, kaleoTaskId from " +
				KaleoTaskAssignmentModelImpl.TABLE_NAME);

			rs = ps.executeQuery();

			while (rs.next()) {
				long kaleoTaskAssignmentId = rs.getLong(
					"kaleoTaskAssignmentId");
				long kaleoTaskId = rs.getLong("kaleoTaskId");

				if (_log.isDebugEnabled()) {
					_log.debug(
						"kaleoTaskAssignmentId = " + kaleoTaskAssignmentId +
						" kaleoTaskId: " + kaleoTaskId);
				}

				kaleoTaskAssignmentIds.put(kaleoTaskAssignmentId, kaleoTaskId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return kaleoTaskAssignmentIds;
	}

	protected void updateKaleoTaskAssignmentIdMappings(
			Map<Long, Long> kaleoTaskAssignmentIdMappings)
		throws IOException, SQLException {

		for (Map.Entry<Long, Long> kaleoTaskAssignmentIdMapping :
				kaleoTaskAssignmentIdMappings.entrySet()) {

			long kaleoTaskAssignmentId = kaleoTaskAssignmentIdMapping.getKey();
			long kaleoTaskId = kaleoTaskAssignmentIdMapping.getValue();

			StringBundler sb = new StringBundler(8);

			sb.append("update ");
			sb.append(KaleoTaskAssignmentModelImpl.TABLE_NAME);
			sb.append(" set kaleoClassName = ");
			sb.append("'com.liferay.portal.workflow.kaleo.model.KaleoTask', ");
			sb.append("kaleoClassPK = ");
			sb.append(kaleoTaskId);
			sb.append(" where kaleoTaskAssignmentId = ");
			sb.append(kaleoTaskAssignmentId);

			runSQL(sb.toString());
		}
	}

	private static final String[] _TABLE_SQL_ADD_INDEXES = {
		"create index IX_611732B0 on KaleoTaskAssignment (companyId)",
		"create index IX_D835C576 on KaleoTaskAssignment (kaleoClassName, kaleoClassPK)",
		"create index IX_1087068E on KaleoTaskAssignment (kaleoClassName, kaleoClassPK, assigneeClassName)",
		"create index IX_575C03A6 on KaleoTaskAssignment (kaleoDefinitionId)"
	};

	private static Log _log = LogFactoryUtil.getLog(
		UpgradeKaleoTaskAssignment.class);

}
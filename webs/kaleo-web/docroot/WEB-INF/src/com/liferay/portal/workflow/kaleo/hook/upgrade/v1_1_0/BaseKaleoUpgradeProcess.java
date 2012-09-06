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
public abstract class BaseKaleoUpgradeProcess extends UpgradeProcess {

	protected Map<Long, Long> getKaleoIdMappings(
			String tableName, String keyColumn, String valueColumn)
		throws SQLException {

		Map<Long, Long> kaleoIdMappings = new HashMap<Long, Long>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select " + keyColumn + ", " + valueColumn + " from " +
				tableName);

			rs = ps.executeQuery();

			while (rs.next()) {
				long key = rs.getLong(keyColumn);
				long value = rs.getLong(valueColumn);

				if (_log.isDebugEnabled()) {
					_log.debug(
						keyColumn + " = " + key +
						valueColumn + " = " + value);
				}

				kaleoIdMappings.put(key, value);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return kaleoIdMappings;
	}

	protected void updateKaleoIdMappings(
			Map<Long, Long> kaleoIdMappings, String tableName,
			String kaleoClassName, String keyColumn)
		throws IOException, SQLException {

		for (Map.Entry<Long, Long> kaleoIdMapping :
				kaleoIdMappings.entrySet()) {

			long key = kaleoIdMapping.getKey();
			long value = kaleoIdMapping.getValue();

			StringBundler sb = new StringBundler(10);

			sb.append("update ");
			sb.append(tableName);
			sb.append(" set kaleoClassName = '");
			sb.append(kaleoClassName);
			sb.append("', kaleoClassPK = ");
			sb.append(value);
			sb.append(" where ");
			sb.append(keyColumn);
			sb.append(" = ");
			sb.append(key);

			runSQL(sb.toString());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseKaleoUpgradeProcess.class);

}
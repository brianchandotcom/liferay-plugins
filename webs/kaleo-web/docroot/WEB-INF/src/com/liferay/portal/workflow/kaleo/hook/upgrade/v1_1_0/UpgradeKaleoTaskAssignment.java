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

import com.liferay.portal.workflow.kaleo.hook.upgrade.v1_1_0.util.KaleoTaskAssignmentTable;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;

import java.util.Map;

/**
 * @author Michael C. Han
 */
public class UpgradeKaleoTaskAssignment extends BaseKaleoUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		Map<Long, Long> kaleoTaskAssignmentIdMappings = getKaleoIdMappings(
			KaleoTaskAssignmentTable.TABLE_NAME, _KEY_COLUMN, _VALUE_COLUMN);

		upgradeTable(
			KaleoTaskAssignmentTable.TABLE_NAME,
			KaleoTaskAssignmentTable.TABLE_COLUMNS,
			KaleoTaskAssignmentTable.TABLE_SQL_CREATE,
			KaleoTaskAssignmentTable.TABLE_SQL_ADD_INDEXES);

		updateKaleoIdMappings(
			kaleoTaskAssignmentIdMappings, KaleoTaskAssignmentTable.TABLE_NAME,
			KaleoTask.class.getName(), _KEY_COLUMN);
	}

	private static final String _KEY_COLUMN = "kaleoTaskAssignmentId";

	private static final String _VALUE_COLUMN = "kaleoTaskId";

}
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

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenModelImpl;

/**
 * @author Michael C. Han
 */
public class UpgradeKaleoTaskInstanceToken extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeTable(
			KaleoTaskInstanceTokenModelImpl.TABLE_NAME,
			KaleoTaskInstanceTokenModelImpl.TABLE_COLUMNS,
			KaleoTaskInstanceTokenModelImpl.TABLE_SQL_CREATE,
			_TABLE_SQL_ADD_INDEXES);
	}

	private static final String[] _TABLE_SQL_ADD_INDEXES = {
		"create index IX_997FE723 on KaleoTaskInstanceToken (companyId)",
		"create index IX_608E9519 on KaleoTaskInstanceToken (kaleoDefinitionId)",
		"create index IX_2CE1159B on KaleoTaskInstanceToken (kaleoInstanceId)",
		"create index IX_B857A115 on KaleoTaskInstanceToken (kaleoInstanceId, kaleoTaskId)"
	};

}
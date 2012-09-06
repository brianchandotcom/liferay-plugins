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
import com.liferay.portal.workflow.kaleo.model.impl.KaleoConditionModelImpl;

/**
 * @author Michael C. Han
 */
public class UpgradeKaleoCondition extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeTable(
			KaleoConditionModelImpl.TABLE_NAME,
			KaleoConditionModelImpl.TABLE_COLUMNS,
			KaleoConditionModelImpl.TABLE_SQL_CREATE, _TABLE_SQL_ADD_INDEXES);
	}

	private static final String[] _TABLE_SQL_ADD_INDEXES = {
		"create index IX_FEE46067 on KaleoCondition (companyId)",
		"create index IX_DC978A5D on KaleoCondition (kaleoDefinitionId)",
		"create index IX_86CBD4C on KaleoCondition (kaleoNodeId)"
	};

}
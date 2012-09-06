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
import com.liferay.portal.workflow.kaleo.model.impl.KaleoTimerInstanceTokenModelImpl;

/**
 * @author Michael C. Han
 */
public class UpgradeKaleoTimerInstanceToken extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeTable(
			KaleoTimerInstanceTokenModelImpl.TABLE_NAME,
			KaleoTimerInstanceTokenModelImpl.TABLE_COLUMNS,
			KaleoTimerInstanceTokenModelImpl.TABLE_SQL_CREATE,
			_TABLE_SQL_ADD_INDEXES);
	}

	private static final String[] _TABLE_SQL_ADD_INDEXES = {
		"create index IX_DB96C55B on KaleoTimerInstanceToken (kaleoInstanceId)",
		"create index IX_DB279423 on KaleoTimerInstanceToken (kaleoInstanceTokenId, completed)",
		"create index IX_9932524C on KaleoTimerInstanceToken (kaleoInstanceTokenId, completed, blocking)",
		"create index IX_13A5BA2C on KaleoTimerInstanceToken (kaleoInstanceTokenId, kaleoTimerId)"
	};

}
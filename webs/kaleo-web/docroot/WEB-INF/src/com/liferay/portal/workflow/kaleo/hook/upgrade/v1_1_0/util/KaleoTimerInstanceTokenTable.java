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

package com.liferay.portal.workflow.kaleo.hook.upgrade.v1_1_0.util;

import java.sql.Types;

/**
 * @author Michael C. Han
 */
public class KaleoTimerInstanceTokenTable {

	public static final String TABLE_NAME = "KaleoTimerInstanceToken";

	public static final Object[][] TABLE_COLUMNS = {
			{ "kaleoTimerInstanceTokenId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "kaleoClassName", Types.VARCHAR },
			{ "kaleoClassPK", Types.BIGINT },
			{ "kaleoDefinitionId", Types.BIGINT },
			{ "kaleoInstanceId", Types.BIGINT },
			{ "kaleoInstanceTokenId", Types.BIGINT },
			{ "kaleoTaskInstanceTokenId", Types.BIGINT },
			{ "kaleoTimerId", Types.BIGINT },
			{ "kaleoTimerName", Types.VARCHAR },
			{ "blocking", Types.BOOLEAN },
			{ "completionUserId", Types.BIGINT },
			{ "completed", Types.BOOLEAN },
			{ "completionDate", Types.TIMESTAMP },
			{ "workflowContext", Types.CLOB }
		};

	public static final String TABLE_SQL_CREATE = "create table KaleoTimerInstanceToken (kaleoTimerInstanceTokenId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(200) null,createDate DATE null,modifiedDate DATE null,kaleoClassName VARCHAR(200) null,kaleoClassPK LONG,kaleoDefinitionId LONG,kaleoInstanceId LONG,kaleoInstanceTokenId LONG,kaleoTaskInstanceTokenId LONG,kaleoTimerId LONG,kaleoTimerName VARCHAR(200) null,blocking BOOLEAN,completionUserId LONG,completed BOOLEAN,completionDate DATE null,workflowContext TEXT null)";

	public static final String TABLE_SQL_DROP = "drop table KaleoTimerInstanceToken";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_DB96C55B on KaleoTimerInstanceToken (kaleoInstanceId)",
		"create index IX_DB279423 on KaleoTimerInstanceToken (kaleoInstanceTokenId, completed)",
		"create index IX_9932524C on KaleoTimerInstanceToken (kaleoInstanceTokenId, completed, blocking)",
		"create index IX_13A5BA2C on KaleoTimerInstanceToken (kaleoInstanceTokenId, kaleoTimerId)"
	};

}
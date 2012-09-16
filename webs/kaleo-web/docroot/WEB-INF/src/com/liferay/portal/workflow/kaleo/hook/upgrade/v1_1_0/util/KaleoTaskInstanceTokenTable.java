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
public class KaleoTaskInstanceTokenTable {

	public static final String TABLE_NAME = "KaleoTaskInstanceToken";

	public static final Object[][] TABLE_COLUMNS = {
			{ "kaleoTaskInstanceTokenId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "kaleoDefinitionId", Types.BIGINT },
			{ "kaleoInstanceId", Types.BIGINT },
			{ "kaleoInstanceTokenId", Types.BIGINT },
			{ "kaleoTaskId", Types.BIGINT },
			{ "kaleoTaskName", Types.VARCHAR },
			{ "className", Types.VARCHAR },
			{ "classPK", Types.BIGINT },
			{ "completionUserId", Types.BIGINT },
			{ "completed", Types.BOOLEAN },
			{ "completionDate", Types.TIMESTAMP },
			{ "dueDate", Types.TIMESTAMP },
			{ "workflowContext", Types.CLOB }
		};

	public static final String TABLE_SQL_CREATE = "create table KaleoTaskInstanceToken (kaleoTaskInstanceTokenId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(200) null,createDate DATE null,modifiedDate DATE null,kaleoDefinitionId LONG,kaleoInstanceId LONG,kaleoInstanceTokenId LONG,kaleoTaskId LONG,kaleoTaskName VARCHAR(200) null,className VARCHAR(200) null,classPK LONG,completionUserId LONG,completed BOOLEAN,completionDate DATE null,dueDate DATE null,workflowContext TEXT null)";

	public static final String TABLE_SQL_DROP = "drop table KaleoTaskInstanceToken";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_997FE723 on KaleoTaskInstanceToken (companyId)",
		"create index IX_608E9519 on KaleoTaskInstanceToken (kaleoDefinitionId)",
		"create index IX_2CE1159B on KaleoTaskInstanceToken (kaleoInstanceId)",
		"create index IX_B857A115 on KaleoTaskInstanceToken (kaleoInstanceId, kaleoTaskId)"
	};

}
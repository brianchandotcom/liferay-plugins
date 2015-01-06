/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.workflow.kaleo.hook.upgrade.v1_3_0.util;

import java.sql.Types;

/**
 * @author Michael C. Han
 */
public class KaleoTaskAssignmentTable {

	public static final Object[][] TABLE_COLUMNS = {
		{ "kaleoTaskAssignmentId", Types.BIGINT },
		{ "groupId", Types.BIGINT },
		{ "companyId", Types.BIGINT },
		{ "userId", Types.BIGINT },
		{ "userName", Types.VARCHAR },
		{ "createDate", Types.TIMESTAMP },
		{ "modifiedDate", Types.TIMESTAMP },
		{ "kaleoClassName", Types.VARCHAR },
		{ "kaleoClassPK", Types.BIGINT },
		{ "kaleoDefinitionId", Types.BIGINT },
		{ "kaleoNodeId", Types.BIGINT },
		{ "assigneeClassName", Types.VARCHAR },
		{ "assigneeClassPK", Types.BIGINT },
		{ "assigneeActionId", Types.VARCHAR },
		{ "assigneeScript", Types.CLOB },
		{ "assigneeScriptLanguage", Types.VARCHAR },
		{ "assigneeScriptRequiredContexts", Types.VARCHAR }
	};

	public static final String TABLE_NAME = "KaleoCondition";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_611732B0 on KaleoTaskAssignment (companyId)",
		"create index IX_1087068E on KaleoTaskAssignment (kaleoClassName, kaleoClassPK, assigneeClassName)",
		"create index IX_575C03A6 on KaleoTaskAssignment (kaleoDefinitionId)"
	};

	public static final String TABLE_SQL_CREATE = "create table KaleoTaskAssignment (kaleoTaskAssignmentId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(200) null,createDate DATE null,modifiedDate DATE null,kaleoClassName VARCHAR(200) null,kaleoClassPK LONG,kaleoDefinitionId LONG,kaleoNodeId LONG,assigneeClassName VARCHAR(200) null,assigneeClassPK LONG,assigneeActionId VARCHAR(75) null,assigneeScript TEXT null,assigneeScriptLanguage VARCHAR(75) null,assigneeScriptRequiredContexts STRING null)";

	public static final String TABLE_SQL_DROP = "drop table KaleoTaskAssignment";

}
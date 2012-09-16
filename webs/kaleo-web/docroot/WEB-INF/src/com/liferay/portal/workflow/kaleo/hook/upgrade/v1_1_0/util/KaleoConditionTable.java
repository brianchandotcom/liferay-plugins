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
public class KaleoConditionTable {

	public static final String TABLE_NAME = "KaleoCondition";

	public static final Object[][] TABLE_COLUMNS = {
			{ "kaleoConditionId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "kaleoDefinitionId", Types.BIGINT },
			{ "kaleoNodeId", Types.BIGINT },
			{ "script", Types.CLOB },
			{ "scriptLanguage", Types.VARCHAR },
			{ "scriptRequiredContexts", Types.VARCHAR }
		};

	public static final String TABLE_SQL_CREATE = "create table KaleoCondition (kaleoConditionId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(200) null,createDate DATE null,modifiedDate DATE null,kaleoDefinitionId LONG,kaleoNodeId LONG,script TEXT null,scriptLanguage VARCHAR(75) null,scriptRequiredContexts VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table KaleoCondition";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_FEE46067 on KaleoCondition (companyId)",
		"create index IX_DC978A5D on KaleoCondition (kaleoDefinitionId)",
		"create index IX_86CBD4C on KaleoCondition (kaleoNodeId)"
	};

}
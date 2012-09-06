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
public class KaleoDefinitionTable {

	public static final String TABLE_NAME = "KaleoDefinition";

	public static final Object[][] TABLE_COLUMNS = {
			{ "kaleoDefinitionId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "name", Types.VARCHAR },
			{ "title", Types.VARCHAR },
			{ "description", Types.VARCHAR },
			{ "content", Types.CLOB },
			{ "version", Types.INTEGER },
			{ "active_", Types.BOOLEAN },
			{ "startKaleoNodeId", Types.BIGINT }
		};

	public static final String TABLE_SQL_CREATE = "create table KaleoDefinition (kaleoDefinitionId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(200) null,createDate DATE null,modifiedDate DATE null,name VARCHAR(200) null,title STRING null,description STRING null,content TEXT null,version INTEGER,active_ BOOLEAN,startKaleoNodeId LONG)";

	public static final String TABLE_SQL_DROP = "drop table KaleoDefinition";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_40B9112F on KaleoDefinition (companyId)",
		"create index IX_408542BA on KaleoDefinition (companyId, active_)",
		"create index IX_76C781AE on KaleoDefinition (companyId, name)",
		"create index IX_4C23F11B on KaleoDefinition (companyId, name, active_)",
		"create index IX_EC14F81A on KaleoDefinition (companyId, name, version)"
	};

}
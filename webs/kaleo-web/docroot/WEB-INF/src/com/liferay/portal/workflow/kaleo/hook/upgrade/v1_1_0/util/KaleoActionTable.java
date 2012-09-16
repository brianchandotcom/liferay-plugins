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
public class KaleoActionTable {

	public static final String TABLE_NAME = "KaleoAction";

	public static final Object[][] TABLE_COLUMNS = {
			{ "kaleoActionId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "kaleoClassName", Types.VARCHAR },
			{ "kaleoClassPK", Types.BIGINT },
			{ "kaleoDefinitionId", Types.BIGINT },
			{ "kaleoNodeName", Types.VARCHAR },
			{ "name", Types.VARCHAR },
			{ "description", Types.VARCHAR },
			{ "executionType", Types.VARCHAR },
			{ "script", Types.CLOB },
			{ "scriptLanguage", Types.VARCHAR },
			{ "scriptRequiredContexts", Types.VARCHAR },
			{ "priority", Types.INTEGER }
		};

	public static final String TABLE_SQL_CREATE = "create table KaleoAction (kaleoActionId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(200) null,createDate DATE null,modifiedDate DATE null,kaleoClassName VARCHAR(200) null,kaleoClassPK LONG,kaleoDefinitionId LONG,kaleoNodeName VARCHAR(200) null,name VARCHAR(200) null,description STRING null,executionType VARCHAR(20) null,script TEXT null,scriptLanguage VARCHAR(75) null,scriptRequiredContexts STRING null,priority INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table KaleoAction";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_50E9112C on KaleoAction (companyId)",
		"create index IX_B88DF9B1 on KaleoAction (kaleoClassName, kaleoClassPK, executionType)",
		"create index IX_F95A622 on KaleoAction (kaleoDefinitionId)",
	};

}
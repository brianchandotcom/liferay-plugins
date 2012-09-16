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
public class KaleoNodeTable {

	public static final String TABLE_NAME = "KaleoNode";

	public static final Object[][] TABLE_COLUMNS = {
			{ "kaleoNodeId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "kaleoDefinitionId", Types.BIGINT },
			{ "name", Types.VARCHAR },
			{ "metadata", Types.VARCHAR },
			{ "description", Types.VARCHAR },
			{ "type_", Types.VARCHAR },
			{ "initial_", Types.BOOLEAN },
			{ "terminal", Types.BOOLEAN }
		};

	public static final String TABLE_SQL_CREATE = "create table KaleoNode (kaleoNodeId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(200) null,createDate DATE null,modifiedDate DATE null,kaleoDefinitionId LONG,name VARCHAR(200) null,metadata STRING null,description STRING null,type_ VARCHAR(20) null,initial_ BOOLEAN,terminal BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table KaleoNode";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_C4E9ACE0 on KaleoNode (companyId)",
		"create index IX_F28C443E on KaleoNode (companyId, kaleoDefinitionId)",
		"create index IX_32E94DD6 on KaleoNode (kaleoDefinitionId)"
	};

}
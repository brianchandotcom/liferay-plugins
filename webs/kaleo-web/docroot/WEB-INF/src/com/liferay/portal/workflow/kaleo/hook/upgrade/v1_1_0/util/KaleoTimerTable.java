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
public class KaleoTimerTable {

	public static final String TABLE_NAME = "KaleoTimer";

	public static final Object[][] TABLE_COLUMNS = {
			{ "kaleoTimerId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "kaleoClassName", Types.VARCHAR },
			{ "kaleoClassPK", Types.BIGINT },
			{ "kaleoDefinitionId", Types.BIGINT },
			{ "name", Types.VARCHAR },
			{ "blocking", Types.BOOLEAN },
			{ "description", Types.VARCHAR },
			{ "duration", Types.DOUBLE },
			{ "scale", Types.VARCHAR },
			{ "recurrenceDuration", Types.DOUBLE },
			{ "recurrenceScale", Types.VARCHAR }
		};

	public static final String TABLE_SQL_CREATE = "create table KaleoTimer (kaleoTimerId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(200) null,createDate DATE null,modifiedDate DATE null,kaleoClassName VARCHAR(200) null,kaleoClassPK LONG,kaleoDefinitionId LONG,name VARCHAR(75) null,blocking BOOLEAN,description STRING null,duration DOUBLE,scale VARCHAR(75) null,recurrenceDuration DOUBLE,recurrenceScale VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table KaleoTimer";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_4DE6A889 on KaleoTimer (kaleoClassName, kaleoClassPK)",
		"create index IX_1A479F32 on KaleoTimer (kaleoClassName, kaleoClassPK, blocking)"
	};

}
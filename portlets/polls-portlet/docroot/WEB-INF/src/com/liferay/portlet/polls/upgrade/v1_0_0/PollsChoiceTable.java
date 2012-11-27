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

package com.liferay.portlet.polls.upgrade.v1_0_0;

import java.sql.Types;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PollsChoiceTable {

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"choiceId", Types.BIGINT},
		{"questionId", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR}
	};
	
	public static final String TABLE_NAME = "Polls_PollsChoice";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_34C7EF05 on Polls_PollsChoice (questionId)",
		"create unique index IX_A1F22704 on Polls_PollsChoice (questionId, name)",
		"create index IX_3A6EE84 on Polls_PollsChoice (uuid_)"
	};
	
	public static final String TABLE_SQL_CREATE = "create table Polls_PollsChoice (uuid_ VARCHAR(75) null,choiceId LONG not null primary key,questionId LONG,name VARCHAR(75) null,description STRING null)";

	public static final String TABLE_SQL_DROP = "drop table Polls_PollsChoice";

}
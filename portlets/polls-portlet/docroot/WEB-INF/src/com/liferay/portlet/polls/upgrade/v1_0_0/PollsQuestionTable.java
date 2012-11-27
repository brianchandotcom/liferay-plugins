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
 * @author Juan Fernández
 */
public class PollsQuestionTable {

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"questionId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"title", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"expirationDate", Types.TIMESTAMP},
		{"lastVoteDate", Types.TIMESTAMP}
	};

	public static final String TABLE_NAME = "Polls_PollsQuestion";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_687C1055 on Polls_PollsQuestion (groupId)",
		"create index IX_B693B41F on Polls_PollsQuestion (uuid_)",
		"create index IX_33D0D6A9 on Polls_PollsQuestion (uuid_, companyId)",
		"create unique index IX_D9C0F36B on Polls_PollsQuestion (uuid_, groupId)"
	};

	public static final String TABLE_SQL_CREATE = "create table Polls_PollsQuestion (uuid_ VARCHAR(75) null,questionId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,title STRING null,description STRING null,expirationDate DATE null,lastVoteDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table Polls_PollsQuestion";

}
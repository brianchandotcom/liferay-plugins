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
public class PollsVoteTable {

	public static final Object[][] TABLE_COLUMNS = {
		{"voteId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"questionId", Types.BIGINT},
		{"choiceId", Types.BIGINT},
		{"voteDate", Types.TIMESTAMP}
	};

	public static final String TABLE_NAME = "Polls_PollsVote";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_E1609DC9 on Polls_PollsVote (choiceId)",
		"create index IX_41D37ECE on Polls_PollsVote (questionId)",
		"create unique index IX_E6442908 on Polls_PollsVote (questionId, userId)"
	};

	public static final String TABLE_SQL_CREATE = "create table Polls_PollsVote (voteId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,questionId LONG,choiceId LONG,voteDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table Polls_PollsVote";

}
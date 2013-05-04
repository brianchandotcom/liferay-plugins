create table Sync_DLSync (
	syncId LONG not null primary key,
	companyId LONG,
	createDate LONG,
	modifiedDate LONG,
	fileId LONG,
	fileUuid VARCHAR(75) null,
	repositoryId LONG,
	parentFolderId LONG,
	name VARCHAR(75) null,
	checksum VARCHAR(75) null,
	description VARCHAR(75) null,
	event VARCHAR(75) null,
	lockUserId LONG,
	lockUserName VARCHAR(75) null,
	size_ LONG,
	type_ VARCHAR(75) null,
	version VARCHAR(75) null
);
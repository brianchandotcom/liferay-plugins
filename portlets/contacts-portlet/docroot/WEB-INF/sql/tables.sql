create table Contacts_Entry (
	entryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	entryUserId LONG,
	emailAddress VARCHAR(75) null,
	fullName VARCHAR(75) null,
	comments STRING null
);
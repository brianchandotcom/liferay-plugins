create table Sharing_AssetSharingEntry (
	classNameId LONG not null,
	classPK LONG not null,
	sharedClassNameId LONG not null,
	sharedClassPK LONG not null,
	primary key (classNameId, classPK, sharedClassNameId, sharedClassPK)
);
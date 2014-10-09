create index IX_655525EB on Sharing_AssetSharingEntry (classNameId, classPK, sharedClassNameId);
create index IX_7E8B497A on Sharing_AssetSharingEntry (classNameId, sharedClassNameId, sharedClassPK);
create index IX_9B7980DE on Sharing_AssetSharingEntry (sharedClassNameId, sharedClassPK);
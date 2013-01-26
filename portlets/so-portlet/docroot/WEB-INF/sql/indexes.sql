create unique index IX_3AA03DF8 on SO_FavoriteSite (groupId, userId);
create index IX_DE2F16A6 on SO_FavoriteSite (userId);

create index IX_D34593C1 on SO_MemberRequest (groupId, receiverUserId, status);
create index IX_212FA0EC on SO_MemberRequest (key_);
create index IX_D6810661 on SO_MemberRequest (receiverUserId);
create index IX_16475447 on SO_MemberRequest (receiverUserId, status);

create index IX_3371C715 on SO_ProjectsEntry (userId);

create index IX_6EA64EE6 on SO_SocialActivityGrouping (classNameId);
create index IX_80E5E7BD on SO_SocialActivityGrouping (classNameId, classPK);
create index IX_76FDFC0 on SO_SocialActivityGrouping (companyId);
create index IX_46ED5642 on SO_SocialActivityGrouping (groupId);
create index IX_A766F9D1 on SO_SocialActivityGrouping (groupId, userId, classNameId, classPK);
create index IX_C288F2E4 on SO_SocialActivityGrouping (groupId, userId, classNameId, classPK, type_);
create index IX_FDCF3343 on SO_SocialActivityGrouping (groupId, userId, classNameId, type_);
create index IX_1E86E3A2 on SO_SocialActivityGrouping (userId);
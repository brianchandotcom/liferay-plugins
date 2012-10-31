create index IX_34C7EF05 on Polls_PollsChoice (questionId);
create unique index IX_A1F22704 on Polls_PollsChoice (questionId, name);
create index IX_3A6EE84 on Polls_PollsChoice (uuid_);

create index IX_687C1055 on Polls_PollsQuestion (groupId);
create index IX_B693B41F on Polls_PollsQuestion (uuid_);
create unique index IX_D9C0F36B on Polls_PollsQuestion (uuid_, groupId);

create index IX_E1609DC9 on Polls_PollsVote (choiceId);
create index IX_41D37ECE on Polls_PollsVote (questionId);
create unique index IX_E6442908 on Polls_PollsVote (questionId, userId);
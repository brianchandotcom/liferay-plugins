Liferay.Service.register("Liferay.Service.Polls", "com.liferay.portlet.polls.service", "polls-portlet");

Liferay.Service.registerClass(
	Liferay.Service.Polls, "PollsQuestion",
	{
		addQuestion: true,
		deleteQuestion: true,
		getQuestion: true,
		updateQuestion: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Polls, "PollsVote",
	{
		addVote: true
	}
);
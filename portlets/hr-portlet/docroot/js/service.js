Liferay.Service.register("Liferay.Service.HR", "com.liferay.hr.service", "hr-portlet");

Liferay.Service.registerClass(
	Liferay.Service.HR, "HRExpense",
	{
		deleteTempDocument: true,
		getTempDocumentNames: true
	}
);
Liferay.Service.register("Liferay.Service.JSONWS", "com.liferay.samplejsonws.service", "sample-jsonws-portlet");

Liferay.Service.registerClass(
	Liferay.Service.JSONWS, "SampleAsset",
	{
		hello: true,
		hello2: true,
		methodOne: true,
		ping: true,
		process: true,
		workoutUser: true
	}
);
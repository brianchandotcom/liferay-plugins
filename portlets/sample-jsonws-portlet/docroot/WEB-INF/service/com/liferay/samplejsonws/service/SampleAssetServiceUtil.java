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

package com.liferay.samplejsonws.service;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the sample asset remote service. This utility wraps {@link com.liferay.samplejsonws.service.impl.SampleAssetServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SampleAssetService
 * @see com.liferay.samplejsonws.service.base.SampleAssetServiceBaseImpl
 * @see com.liferay.samplejsonws.service.impl.SampleAssetServiceImpl
 * @generated
 */
public class SampleAssetServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.samplejsonws.service.impl.SampleAssetServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Service method that takes few parameters of various types.
	*/
	public static java.lang.String hello(long userId, java.lang.String fullName) {
		return getService().hello(userId, fullName);
	}

	/**
	* Service method with many different parameter types.
	*/
	public static java.lang.String hello2(java.util.Calendar calendar,
		long[] longArray, java.util.List<java.util.Locale> locales) {
		return getService().hello2(calendar, longArray, locales);
	}

	public static java.lang.String methodOne(long id, long nameId) {
		return getService().methodOne(id, nameId);
	}

	public static java.lang.String methodOne(long id, long nameId,
		java.lang.String subname) {
		return getService().methodOne(id, nameId, subname);
	}

	public static java.lang.String methodOne(long id, java.lang.String name) {
		return getService().methodOne(id, name);
	}

	/**
	* Simple method.
	*/
	public static java.lang.String ping() {
		return getService().ping();
	}

	/**
	* Process that uses custom-type argument.
	*/
	public static java.lang.String process(
		com.liferay.samplejsonws.model.SampleAsset sampleAsset) {
		return getService().process(sampleAsset);
	}

	/**
	* A method that uses default parameters. Default parameters doesn't
	* have to be specified explicitly within request.
	*/
	public static java.lang.String workoutUser(long userId, long companyId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().workoutUser(userId, companyId, serviceContext);
	}

	public static void clearService() {
		_service = null;
	}

	public static SampleAssetService getService() {
		if (_service == null) {
			Object object = PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					SampleAssetService.class.getName());
			ClassLoader portletClassLoader = (ClassLoader)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					"portletClassLoader");

			ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(object,
					SampleAssetService.class.getName(), portletClassLoader);

			_service = new SampleAssetServiceClp(classLoaderProxy);

			ClpSerializer.setClassLoader(portletClassLoader);

			ReferenceRegistry.registerReference(SampleAssetServiceUtil.class,
				"_service");
			MethodCache.remove(SampleAssetService.class);
		}

		return _service;
	}

	public void setService(SampleAssetService service) {
		MethodCache.remove(SampleAssetService.class);

		_service = service;

		ReferenceRegistry.registerReference(SampleAssetServiceUtil.class,
			"_service");
		MethodCache.remove(SampleAssetService.class);
	}

	private static SampleAssetService _service;
}
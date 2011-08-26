/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.hr.service;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the h r expense remote service. This utility wraps {@link com.liferay.hr.service.impl.HRExpenseServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Wesley Gong
 * @see HRExpenseService
 * @see com.liferay.hr.service.base.HRExpenseServiceBaseImpl
 * @see com.liferay.hr.service.impl.HRExpenseServiceImpl
 * @generated
 */
public class HRExpenseServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.hr.service.impl.HRExpenseServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static void addDocument(long companyId, long userId, long classPK,
		long repositoryId, java.lang.String dirName, java.lang.String fileName,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addDocument(companyId, userId, classPK, repositoryId, dirName,
			fileName, file);
	}

	public static java.lang.String addTempDocument(long userId, long classPK,
		java.lang.String fileName, java.lang.String tempFolderName,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException,
			java.io.IOException {
		return getService()
				   .addTempDocument(userId, classPK, fileName, tempFolderName,
			file);
	}

	public static void deleteTempDocument(long userId, long classPK,
		java.lang.String fileName, java.lang.String tempFolderName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deleteTempDocument(userId, classPK, fileName, tempFolderName);
	}

	public static java.lang.String[] getTempDocumentNames(long userId,
		long classPK, java.lang.String tempFolderName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTempDocumentNames(userId, classPK, tempFolderName);
	}

	public static void clearService() {
		_service = null;
	}

	public static HRExpenseService getService() {
		if (_service == null) {
			Object object = PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					HRExpenseService.class.getName());
			ClassLoader portletClassLoader = (ClassLoader)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					"portletClassLoader");

			ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(object,
					HRExpenseService.class.getName(), portletClassLoader);

			_service = new HRExpenseServiceClp(classLoaderProxy);

			ClpSerializer.setClassLoader(portletClassLoader);

			ReferenceRegistry.registerReference(HRExpenseServiceUtil.class,
				"_service");
			MethodCache.remove(HRExpenseService.class);
		}

		return _service;
	}

	public void setService(HRExpenseService service) {
		MethodCache.remove(HRExpenseService.class);

		_service = service;

		ReferenceRegistry.registerReference(HRExpenseServiceUtil.class,
			"_service");
		MethodCache.remove(HRExpenseService.class);
	}

	private static HRExpenseService _service;
}
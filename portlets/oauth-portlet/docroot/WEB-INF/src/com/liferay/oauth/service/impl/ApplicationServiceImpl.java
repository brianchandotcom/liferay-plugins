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

package com.liferay.oauth.service.impl;

import com.liferay.oauth.model.Application;
import com.liferay.oauth.service.base.ApplicationServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;

/**
 * The implementation of the application remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.oauth.service.ApplicationService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.oauth.service.base.ApplicationServiceBaseImpl
 * @see com.liferay.oauth.service.ApplicationServiceUtil
 */
public class ApplicationServiceImpl extends ApplicationServiceBaseImpl {

	public Application deleteApplication(
			long applicationId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		Application application = applicationLocalService.getApplication(
			applicationId);

		if (!permissionChecker.hasOwnerPermission(
				application.getCompanyId(), Application.class.getName(),
				applicationId, application.getUserId(), ActionKeys.DELETE) &&
			!permissionChecker.hasPermission(
				0, Application.class.getName(), applicationId,
				ActionKeys.DELETE)) {

			throw new PrincipalException();
		}

		return applicationLocalService.deleteApplication(
			applicationId, serviceContext);
	}

	public Application updateApplication(
			long applicationId, String name, String description,
			String website, String callBackURL, ServiceContext serviceContext)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		Application application = applicationLocalService.getApplication(
			applicationId);

		if (!permissionChecker.hasOwnerPermission(
				application.getCompanyId(), Application.class.getName(),
				applicationId, application.getUserId(), ActionKeys.UPDATE) &&
			!permissionChecker.hasPermission(
				0, Application.class.getName(), applicationId,
				ActionKeys.UPDATE)) {

			throw new PrincipalException();
		}

		return applicationLocalService.updateApplication(
			applicationId, name, description, website, callBackURL,
			serviceContext);
	}

	public Application updateLogo(long applicationId, byte[] bytes)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		Application application = applicationLocalService.getApplication(
			applicationId);

		if (!permissionChecker.hasOwnerPermission(
				application.getCompanyId(), Application.class.getName(),
				applicationId, application.getUserId(), ActionKeys.UPDATE) &&
			!permissionChecker.hasPermission(
				0, Application.class.getName(), applicationId,
				ActionKeys.UPDATE)) {

			throw new PrincipalException();
		}

		return applicationLocalService.updateLogo(applicationId, bytes);
	}

}
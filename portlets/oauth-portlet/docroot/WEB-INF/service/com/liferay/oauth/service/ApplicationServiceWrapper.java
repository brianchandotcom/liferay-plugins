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

package com.liferay.oauth.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link ApplicationService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ApplicationService
 * @generated
 */
public class ApplicationServiceWrapper implements ApplicationService,
	ServiceWrapper<ApplicationService> {
	public ApplicationServiceWrapper(ApplicationService applicationService) {
		_applicationService = applicationService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _applicationService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_applicationService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _applicationService.invokeMethod(name, parameterTypes, arguments);
	}

	public com.liferay.oauth.model.Application deleteApplication(
		long applicationId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _applicationService.deleteApplication(applicationId,
			serviceContext);
	}

	public com.liferay.oauth.model.Application updateApplication(
		long applicationId, java.lang.String name,
		java.lang.String description, java.lang.String website,
		java.lang.String callBackURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _applicationService.updateApplication(applicationId, name,
			description, website, callBackURL, serviceContext);
	}

	public com.liferay.oauth.model.Application updateLogo(long applicationId,
		byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _applicationService.updateLogo(applicationId, bytes);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public ApplicationService getWrappedApplicationService() {
		return _applicationService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedApplicationService(
		ApplicationService applicationService) {
		_applicationService = applicationService;
	}

	public ApplicationService getWrappedService() {
		return _applicationService;
	}

	public void setWrappedService(ApplicationService applicationService) {
		_applicationService = applicationService;
	}

	private ApplicationService _applicationService;
}
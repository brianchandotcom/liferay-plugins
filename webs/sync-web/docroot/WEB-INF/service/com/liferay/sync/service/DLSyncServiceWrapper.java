/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.sync.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link DLSyncService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLSyncService
 * @generated
 */
public class DLSyncServiceWrapper implements DLSyncService,
	ServiceWrapper<DLSyncService> {
	public DLSyncServiceWrapper(DLSyncService dlSyncService) {
		_dlSyncService = dlSyncService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _dlSyncService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_dlSyncService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _dlSyncService.invokeMethod(name, parameterTypes, arguments);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public DLSyncService getWrappedDLSyncService() {
		return _dlSyncService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedDLSyncService(DLSyncService dlSyncService) {
		_dlSyncService = dlSyncService;
	}

	public DLSyncService getWrappedService() {
		return _dlSyncService;
	}

	public void setWrappedService(DLSyncService dlSyncService) {
		_dlSyncService = dlSyncService;
	}

	private DLSyncService _dlSyncService;
}
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.screens.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link LiferayScreensAssetEntryService}.
 *
 * @author José Manuel Navarro
 * @see LiferayScreensAssetEntryService
 * @generated
 */
public class LiferayScreensAssetEntryServiceWrapper
	implements LiferayScreensAssetEntryService,
		ServiceWrapper<LiferayScreensAssetEntryService> {
	public LiferayScreensAssetEntryServiceWrapper(
		LiferayScreensAssetEntryService liferayScreensAssetEntryService) {
		_liferayScreensAssetEntryService = liferayScreensAssetEntryService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _liferayScreensAssetEntryService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_liferayScreensAssetEntryService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _liferayScreensAssetEntryService.invokeMethod(name,
			parameterTypes, arguments);
	}

	@Override
	public com.liferay.portal.kernel.json.JSONArray getAssetEntries(
		com.liferay.portlet.asset.service.persistence.AssetEntryQuery assetEntryQuery,
		java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _liferayScreensAssetEntryService.getAssetEntries(assetEntryQuery,
			locale);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public LiferayScreensAssetEntryService getWrappedLiferayScreensAssetEntryService() {
		return _liferayScreensAssetEntryService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedLiferayScreensAssetEntryService(
		LiferayScreensAssetEntryService liferayScreensAssetEntryService) {
		_liferayScreensAssetEntryService = liferayScreensAssetEntryService;
	}

	@Override
	public LiferayScreensAssetEntryService getWrappedService() {
		return _liferayScreensAssetEntryService;
	}

	@Override
	public void setWrappedService(
		LiferayScreensAssetEntryService liferayScreensAssetEntryService) {
		_liferayScreensAssetEntryService = liferayScreensAssetEntryService;
	}

	private LiferayScreensAssetEntryService _liferayScreensAssetEntryService;
}
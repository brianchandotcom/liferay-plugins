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

package com.liferay.mobilewidgets.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MobileWidgetsAssetService}.
 *
 * @author José Manuel Navarro
 * @see MobileWidgetsAssetService
 * @generated
 */
public class MobileWidgetsAssetServiceWrapper
	implements MobileWidgetsAssetService,
		ServiceWrapper<MobileWidgetsAssetService> {
	public MobileWidgetsAssetServiceWrapper(
		MobileWidgetsAssetService mobileWidgetsAssetService) {
		_mobileWidgetsAssetService = mobileWidgetsAssetService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _mobileWidgetsAssetService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_mobileWidgetsAssetService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _mobileWidgetsAssetService.invokeMethod(name, parameterTypes,
			arguments);
	}

	/**
	* NOTE FOR DEVELOPERS:
	*
	* Never reference this interface directly. Always use {@link com.liferay.mobilewidgets.service.MobileWidgetsAssetServiceUtil} to access the mobile widgets asset remote service.
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getEntries(
		com.liferay.portlet.asset.service.persistence.AssetEntryQuery entryQuery,
		java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mobileWidgetsAssetService.getEntries(entryQuery, locale);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public MobileWidgetsAssetService getWrappedMobileWidgetsAssetService() {
		return _mobileWidgetsAssetService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedMobileWidgetsAssetService(
		MobileWidgetsAssetService mobileWidgetsAssetService) {
		_mobileWidgetsAssetService = mobileWidgetsAssetService;
	}

	@Override
	public MobileWidgetsAssetService getWrappedService() {
		return _mobileWidgetsAssetService;
	}

	@Override
	public void setWrappedService(
		MobileWidgetsAssetService mobileWidgetsAssetService) {
		_mobileWidgetsAssetService = mobileWidgetsAssetService;
	}

	private MobileWidgetsAssetService _mobileWidgetsAssetService;
}
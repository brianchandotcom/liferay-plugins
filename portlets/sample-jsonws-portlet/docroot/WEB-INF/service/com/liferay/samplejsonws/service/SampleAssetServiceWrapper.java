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

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link SampleAssetService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SampleAssetService
 * @generated
 */
public class SampleAssetServiceWrapper implements SampleAssetService,
	ServiceWrapper<SampleAssetService> {
	public SampleAssetServiceWrapper(SampleAssetService sampleAssetService) {
		_sampleAssetService = sampleAssetService;
	}

	/**
	* Service method that takes few parameters of various types.
	*/
	public java.lang.String hello(long userId, java.lang.String fullName) {
		return _sampleAssetService.hello(userId, fullName);
	}

	/**
	* Service method with many different parameter types.
	*/
	public java.lang.String hello2(java.util.Calendar calendar,
		long[] longArray, java.util.List<java.util.Locale> locales) {
		return _sampleAssetService.hello2(calendar, longArray, locales);
	}

	public java.lang.String methodOne(long id, long nameId) {
		return _sampleAssetService.methodOne(id, nameId);
	}

	public java.lang.String methodOne(long id, long nameId,
		java.lang.String subname) {
		return _sampleAssetService.methodOne(id, nameId, subname);
	}

	public java.lang.String methodOne(long id, java.lang.String name) {
		return _sampleAssetService.methodOne(id, name);
	}

	/**
	* Simple method.
	*/
	public java.lang.String ping() {
		return _sampleAssetService.ping();
	}

	/**
	* Process that uses custom-type argument.
	*/
	public java.lang.String process(
		com.liferay.samplejsonws.model.SampleAsset sampleAsset) {
		return _sampleAssetService.process(sampleAsset);
	}

	/**
	* A method that uses default parameters. Default parameters doesn't
	* have to be specified explicitly within request.
	*/
	public java.lang.String workoutUser(long userId, long companyId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _sampleAssetService.workoutUser(userId, companyId, serviceContext);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public SampleAssetService getWrappedSampleAssetService() {
		return _sampleAssetService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedSampleAssetService(
		SampleAssetService sampleAssetService) {
		_sampleAssetService = sampleAssetService;
	}

	public SampleAssetService getWrappedService() {
		return _sampleAssetService;
	}

	public void setWrappedService(SampleAssetService sampleAssetService) {
		_sampleAssetService = sampleAssetService;
	}

	private SampleAssetService _sampleAssetService;
}
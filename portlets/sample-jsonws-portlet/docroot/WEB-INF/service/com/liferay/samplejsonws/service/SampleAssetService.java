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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;

/**
 * The interface for the sample asset remote service.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SampleAssetServiceUtil
 * @see com.liferay.samplejsonws.service.base.SampleAssetServiceBaseImpl
 * @see com.liferay.samplejsonws.service.impl.SampleAssetServiceImpl
 * @generated
 */
@JSONWebService
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface SampleAssetService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SampleAssetServiceUtil} to access the sample asset remote service. Add custom service methods to {@link com.liferay.samplejsonws.service.impl.SampleAssetServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Service method that takes few parameters of various types.
	*/
	public java.lang.String hello(long userId, java.lang.String fullName);

	/**
	* Service method with many different parameter types.
	*/
	public java.lang.String hello2(java.util.Calendar calendar,
		long[] longArray, java.util.List<java.util.Locale> locales);

	public java.lang.String methodOne(long id, long nameId);

	public java.lang.String methodOne(long id, long nameId,
		java.lang.String subname);

	public java.lang.String methodOne(long id, java.lang.String name);

	/**
	* Simple method.
	*/
	public java.lang.String ping();

	/**
	* Process that uses custom-type argument.
	*/
	public java.lang.String process(
		com.liferay.samplejsonws.model.SampleAsset sampleAsset);

	/**
	* A method that uses default parameters. Default parameters doesn't
	* have to be specified explicitly within request.
	*/
	public java.lang.String workoutUser(long userId, long companyId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;
}
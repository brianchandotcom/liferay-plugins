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

package com.liferay.samplejsonws.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.samplejsonws.service.SampleAssetServiceUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.samplejsonws.service.SampleAssetServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.samplejsonws.model.SampleAssetSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.samplejsonws.model.SampleAsset}, that is translated to a
 * {@link com.liferay.samplejsonws.model.SampleAssetSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at
 * http://localhost:8080/api/secure/axis. Set the property
 * <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SampleAssetServiceHttp
 * @see       com.liferay.samplejsonws.model.SampleAssetSoap
 * @see       com.liferay.samplejsonws.service.SampleAssetServiceUtil
 * @generated
 */
public class SampleAssetServiceSoap {
	/**
	* Service method that takes few parameters of various types.
	*/
	public static java.lang.String hello(long userId, java.lang.String fullName)
		throws RemoteException {
		try {
			java.lang.String returnValue = SampleAssetServiceUtil.hello(userId,
					fullName);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Service method with many different parameter types.
	*/
	public static java.lang.String hello2(java.util.Calendar calendar,
		long[] longArray, java.util.List<java.util.Locale> locales)
		throws RemoteException {
		try {
			java.lang.String returnValue = SampleAssetServiceUtil.hello2(calendar,
					longArray, locales);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.String methodOne(long id, long nameId)
		throws RemoteException {
		try {
			java.lang.String returnValue = SampleAssetServiceUtil.methodOne(id,
					nameId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.String methodOne(long id, long nameId,
		java.lang.String subname) throws RemoteException {
		try {
			java.lang.String returnValue = SampleAssetServiceUtil.methodOne(id,
					nameId, subname);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.String methodOne(long id, java.lang.String name)
		throws RemoteException {
		try {
			java.lang.String returnValue = SampleAssetServiceUtil.methodOne(id,
					name);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Simple method.
	*/
	public static java.lang.String ping() throws RemoteException {
		try {
			java.lang.String returnValue = SampleAssetServiceUtil.ping();

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Process that uses custom-type argument.
	*/
	public static java.lang.String process(
		com.liferay.samplejsonws.model.SampleAssetSoap sampleAsset)
		throws RemoteException {
		try {
			java.lang.String returnValue = SampleAssetServiceUtil.process(com.liferay.samplejsonws.model.impl.SampleAssetModelImpl.toModel(
						sampleAsset));

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* A method that uses default parameters. Default parameters doesn't
	* have to be specified explicitly within request.
	*/
	public static java.lang.String workoutUser(long userId, long companyId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			java.lang.String returnValue = SampleAssetServiceUtil.workoutUser(userId,
					companyId, serviceContext);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SampleAssetServiceSoap.class);
}
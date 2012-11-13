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

import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * @author Brian Wing Shun Chan
 */
public class SampleAssetServiceClp implements SampleAssetService {
	public SampleAssetServiceClp(ClassLoaderProxy classLoaderProxy) {
		_classLoaderProxy = classLoaderProxy;

		_helloMethodKey0 = new MethodKey(_classLoaderProxy.getClassName(),
				"hello", long.class, java.lang.String.class);

		_hello2MethodKey1 = new MethodKey(_classLoaderProxy.getClassName(),
				"hello2", java.util.Calendar.class, long[].class,
				java.util.List.class);

		_methodOneMethodKey2 = new MethodKey(_classLoaderProxy.getClassName(),
				"methodOne", long.class, long.class);

		_methodOneMethodKey3 = new MethodKey(_classLoaderProxy.getClassName(),
				"methodOne", long.class, long.class, java.lang.String.class);

		_methodOneMethodKey4 = new MethodKey(_classLoaderProxy.getClassName(),
				"methodOne", long.class, java.lang.String.class);

		_pingMethodKey5 = new MethodKey(_classLoaderProxy.getClassName(), "ping");

		_processMethodKey6 = new MethodKey(_classLoaderProxy.getClassName(),
				"process", com.liferay.samplejsonws.model.SampleAsset.class);

		_workoutUserMethodKey7 = new MethodKey(_classLoaderProxy.getClassName(),
				"workoutUser", long.class, long.class,
				com.liferay.portal.service.ServiceContext.class);
	}

	public java.lang.String hello(long userId, java.lang.String fullName) {
		Object returnObj = null;

		MethodHandler methodHandler = new MethodHandler(_helloMethodKey0,
				userId, ClpSerializer.translateInput(fullName));

		try {
			returnObj = _classLoaderProxy.invoke(methodHandler);
		}
		catch (Throwable t) {
			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.lang.String)ClpSerializer.translateOutput(returnObj);
	}

	public java.lang.String hello2(java.util.Calendar calendar,
		long[] longArray, java.util.List<java.util.Locale> locales) {
		Object returnObj = null;

		MethodHandler methodHandler = new MethodHandler(_hello2MethodKey1,
				ClpSerializer.translateInput(calendar),
				ClpSerializer.translateInput(longArray),
				ClpSerializer.translateInput(locales));

		try {
			returnObj = _classLoaderProxy.invoke(methodHandler);
		}
		catch (Throwable t) {
			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.lang.String)ClpSerializer.translateOutput(returnObj);
	}

	public java.lang.String methodOne(long id, long nameId) {
		Object returnObj = null;

		MethodHandler methodHandler = new MethodHandler(_methodOneMethodKey2,
				id, nameId);

		try {
			returnObj = _classLoaderProxy.invoke(methodHandler);
		}
		catch (Throwable t) {
			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.lang.String)ClpSerializer.translateOutput(returnObj);
	}

	public java.lang.String methodOne(long id, long nameId,
		java.lang.String subname) {
		Object returnObj = null;

		MethodHandler methodHandler = new MethodHandler(_methodOneMethodKey3,
				id, nameId, ClpSerializer.translateInput(subname));

		try {
			returnObj = _classLoaderProxy.invoke(methodHandler);
		}
		catch (Throwable t) {
			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.lang.String)ClpSerializer.translateOutput(returnObj);
	}

	public java.lang.String methodOne(long id, java.lang.String name) {
		Object returnObj = null;

		MethodHandler methodHandler = new MethodHandler(_methodOneMethodKey4,
				id, ClpSerializer.translateInput(name));

		try {
			returnObj = _classLoaderProxy.invoke(methodHandler);
		}
		catch (Throwable t) {
			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.lang.String)ClpSerializer.translateOutput(returnObj);
	}

	public java.lang.String ping() {
		Object returnObj = null;

		MethodHandler methodHandler = new MethodHandler(_pingMethodKey5);

		try {
			returnObj = _classLoaderProxy.invoke(methodHandler);
		}
		catch (Throwable t) {
			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.lang.String)ClpSerializer.translateOutput(returnObj);
	}

	public java.lang.String process(
		com.liferay.samplejsonws.model.SampleAsset sampleAsset) {
		Object returnObj = null;

		MethodHandler methodHandler = new MethodHandler(_processMethodKey6,
				ClpSerializer.translateInput(sampleAsset));

		try {
			returnObj = _classLoaderProxy.invoke(methodHandler);
		}
		catch (Throwable t) {
			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.lang.String)ClpSerializer.translateOutput(returnObj);
	}

	public java.lang.String workoutUser(long userId, long companyId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		MethodHandler methodHandler = new MethodHandler(_workoutUserMethodKey7,
				userId, companyId, ClpSerializer.translateInput(serviceContext));

		try {
			returnObj = _classLoaderProxy.invoke(methodHandler);
		}
		catch (Throwable t) {
			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.lang.String)ClpSerializer.translateOutput(returnObj);
	}

	public ClassLoaderProxy getClassLoaderProxy() {
		return _classLoaderProxy;
	}

	private ClassLoaderProxy _classLoaderProxy;
	private MethodKey _helloMethodKey0;
	private MethodKey _hello2MethodKey1;
	private MethodKey _methodOneMethodKey2;
	private MethodKey _methodOneMethodKey3;
	private MethodKey _methodOneMethodKey4;
	private MethodKey _pingMethodKey5;
	private MethodKey _processMethodKey6;
	private MethodKey _workoutUserMethodKey7;
}
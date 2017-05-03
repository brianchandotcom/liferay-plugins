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

package com.liferay.testsearchpagination.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.testsearchpagination.service.FooServiceUtil;

import java.util.Arrays;

/**
 * @author Inácio Nery
 * @generated
 */
@ProviderType
public class FooServiceClpInvoker {
	public FooServiceClpInvoker() {
		_methodName26 = "getOSGiServiceIdentifier";

		_methodParameterTypes26 = new String[] {  };

		_methodName31 = "findAll";

		_methodParameterTypes31 = new String[] {  };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName26.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes26, parameterTypes)) {
			return FooServiceUtil.getOSGiServiceIdentifier();
		}

		if (_methodName31.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes31, parameterTypes)) {
			return FooServiceUtil.findAll();
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName26;
	private String[] _methodParameterTypes26;
	private String _methodName31;
	private String[] _methodParameterTypes31;
}
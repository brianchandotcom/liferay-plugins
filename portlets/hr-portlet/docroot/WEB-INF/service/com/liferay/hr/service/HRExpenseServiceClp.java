/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.hr.service;

import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * @author Wesley Gong
 */
public class HRExpenseServiceClp implements HRExpenseService {
	public HRExpenseServiceClp(ClassLoaderProxy classLoaderProxy) {
		_classLoaderProxy = classLoaderProxy;

		_addDocumentMethodKey0 = new MethodKey(_classLoaderProxy.getClassName(),
				"addDocument", long.class, long.class, long.class, long.class,
				java.lang.String.class, java.lang.String.class,
				java.io.File.class);

		_addTempDocumentMethodKey1 = new MethodKey(_classLoaderProxy.getClassName(),
				"addTempDocument", long.class, long.class,
				java.lang.String.class, java.lang.String.class,
				java.io.File.class);

		_deleteTempDocumentMethodKey2 = new MethodKey(_classLoaderProxy.getClassName(),
				"deleteTempDocument", long.class, long.class,
				java.lang.String.class, java.lang.String.class);

		_getTempDocumentNamesMethodKey3 = new MethodKey(_classLoaderProxy.getClassName(),
				"getTempDocumentNames", long.class, long.class,
				java.lang.String.class);
	}

	public void addDocument(long companyId, long userId, long classPK,
		long repositoryId, java.lang.String dirName, java.lang.String fileName,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		MethodHandler methodHandler = new MethodHandler(_addDocumentMethodKey0,
				companyId, userId, classPK, repositoryId,
				ClpSerializer.translateInput(dirName),
				ClpSerializer.translateInput(fileName),
				ClpSerializer.translateInput(file));

		try {
			_classLoaderProxy.invoke(methodHandler);
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
	}

	public java.lang.String addTempDocument(long userId, long classPK,
		java.lang.String fileName, java.lang.String tempFolderName,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException,
			java.io.IOException {
		Object returnObj = null;

		MethodHandler methodHandler = new MethodHandler(_addTempDocumentMethodKey1,
				userId, classPK, ClpSerializer.translateInput(fileName),
				ClpSerializer.translateInput(tempFolderName),
				ClpSerializer.translateInput(file));

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

			if (t instanceof java.io.IOException) {
				throw (java.io.IOException)t;
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

	public void deleteTempDocument(long userId, long classPK,
		java.lang.String fileName, java.lang.String tempFolderName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		MethodHandler methodHandler = new MethodHandler(_deleteTempDocumentMethodKey2,
				userId, classPK, ClpSerializer.translateInput(fileName),
				ClpSerializer.translateInput(tempFolderName));

		try {
			_classLoaderProxy.invoke(methodHandler);
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
	}

	public java.lang.String[] getTempDocumentNames(long userId, long classPK,
		java.lang.String tempFolderName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		MethodHandler methodHandler = new MethodHandler(_getTempDocumentNamesMethodKey3,
				userId, classPK, ClpSerializer.translateInput(tempFolderName));

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

		return (java.lang.String[])ClpSerializer.translateOutput(returnObj);
	}

	public ClassLoaderProxy getClassLoaderProxy() {
		return _classLoaderProxy;
	}

	private ClassLoaderProxy _classLoaderProxy;
	private MethodKey _addDocumentMethodKey0;
	private MethodKey _addTempDocumentMethodKey1;
	private MethodKey _deleteTempDocumentMethodKey2;
	private MethodKey _getTempDocumentNamesMethodKey3;
}
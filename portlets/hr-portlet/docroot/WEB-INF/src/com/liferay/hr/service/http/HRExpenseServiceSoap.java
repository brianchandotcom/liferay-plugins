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

package com.liferay.hr.service.http;

import com.liferay.hr.service.HRExpenseServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.hr.service.HRExpenseServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.hr.model.HRExpenseSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.hr.model.HRExpense}, that is translated to a
 * {@link com.liferay.hr.model.HRExpenseSoap}. Methods that SOAP cannot
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
 * http://localhost:8080/tunnel-web/secure/axis. Set the property
 * <b>tunnel.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    Wesley Gong
 * @see       HRExpenseServiceHttp
 * @see       com.liferay.hr.model.HRExpenseSoap
 * @see       com.liferay.hr.service.HRExpenseServiceUtil
 * @generated
 */
public class HRExpenseServiceSoap {
	public static void deleteTempDocument(long userId, long classPK,
		java.lang.String fileName, java.lang.String tempFolderName)
		throws RemoteException {
		try {
			HRExpenseServiceUtil.deleteTempDocument(userId, classPK, fileName,
				tempFolderName);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.String[] getTempDocumentNames(long userId,
		long classPK, java.lang.String tempFolderName)
		throws RemoteException {
		try {
			java.lang.String[] returnValue = HRExpenseServiceUtil.getTempDocumentNames(userId,
					classPK, tempFolderName);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(HRExpenseServiceSoap.class);
}
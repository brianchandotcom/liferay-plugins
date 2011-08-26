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

package com.liferay.hr.service.impl;

import com.liferay.hr.service.base.HRExpenseServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

import java.io.File;
import java.io.IOException;

/**
 * @author Wesley Gong
 */
public class HRExpenseServiceImpl extends HRExpenseServiceBaseImpl {

	public void addDocument(
			long companyId, long userId, long classPK, long repositoryId,
			String dirName, String fileName, File file)
		throws PortalException, SystemException {

		// Add permission checking into here

		hrExpenseLocalService.addDocument(
			companyId, userId, classPK, repositoryId, dirName, fileName, file);
	}

	public String addTempDocument(
			long userId, long classPK, String fileName, String tempFolderName,
			File file)
		throws IOException, PortalException, SystemException {

		// Add permission checking into here

		return hrExpenseLocalService.addTempDocument(
			userId, fileName, tempFolderName, file);
	}

	public void deleteTempDocument(
			long userId, long classPK, String fileName, String tempFolderName)
		throws PortalException, SystemException {

		// Add permission checking into here

		hrExpenseLocalService.deleteTempDocument(
			userId, fileName, tempFolderName);
	}

	public String[] getTempDocumentNames(
			long userId, long classPK, String tempFolderName)
		throws PortalException, SystemException {

		// Add permission checking into here

		return hrExpenseLocalService.getTempDocumentNames(
			userId, tempFolderName);
	}

}
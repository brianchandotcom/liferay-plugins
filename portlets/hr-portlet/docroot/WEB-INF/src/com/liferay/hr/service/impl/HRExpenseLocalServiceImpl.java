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

import com.liferay.hr.service.base.HRExpenseLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.TempFileUtil;
import com.liferay.portlet.documentlibrary.DuplicateDirectoryException;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author Wesley Gong
 */
public class HRExpenseLocalServiceImpl extends HRExpenseLocalServiceBaseImpl {

	public void addDocument(
			long companyId, long userId, long classPK, long repositoryId,
			String dirName, String fileName, File file)
		throws PortalException, SystemException {

		try {
			DLStoreUtil.addDirectory(companyId, repositoryId, dirName);
		}
		catch (DuplicateDirectoryException dde) {
		}

		DLStoreUtil.addFile(
			companyId, repositoryId, dirName + "/" + fileName, file);
	}

	public String addTempDocument(
			long userId, String fileName, String tempFolderName, File file)
		throws IOException, PortalException, SystemException {

		return TempFileUtil.addTempFile(userId, fileName, tempFolderName, file);
	}

	public void deleteTempDocument(
		long userId, String fileName, String tempFolderName) {

		TempFileUtil.deleteTempFile(userId, fileName, tempFolderName);
	}

	public String[] getTempDocumentNames(
		long userId, String tempFolderName) {

		return TempFileUtil.getTempFileEntryNames(userId, tempFolderName);
	}

}
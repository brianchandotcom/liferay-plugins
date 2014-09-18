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

package com.liferay.knowledgebase.service.impl;

import com.liferay.knowledgebase.NoSuchFolderException;
import com.liferay.knowledgebase.model.KBFolder;
import com.liferay.knowledgebase.model.KBFolderConstants;
import com.liferay.knowledgebase.service.base.KBFolderLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

import java.util.List;

/**
 * The implementation of the k b folder local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.knowledgebase.service.KBFolderLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.knowledgebase.service.base.KBFolderLocalServiceBaseImpl
 * @see com.liferay.knowledgebase.service.KBFolderLocalServiceUtil
 */
public class KBFolderLocalServiceImpl extends KBFolderLocalServiceBaseImpl {

	@Override
	public List<KBFolder> getFolders(
			long groupId, long parentKBFolderId, int start, int end)
		throws PortalException, SystemException {

		validateFolder(groupId, parentKBFolderId);

		return kbFolderPersistence.findByG_P(
			groupId, parentKBFolderId, start, end);
	}

	@Override
	public int getFoldersCount(long groupId, long parentKBFolderId)
		throws PortalException, SystemException {

		validateFolder(groupId, parentKBFolderId);

		return kbFolderPersistence.countByG_P(groupId, parentKBFolderId);
	}

	protected void validateFolder(long groupId, long kbFolderId)
		throws NoSuchFolderException, SystemException {

		if (kbFolderId == KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		KBFolder kbFolder = kbFolderPersistence.findByPrimaryKey(
			kbFolderId);

		if (kbFolder.getGroupId() != groupId) {
			throw new NoSuchFolderException(
				String.format(
					"No KBFolder found in group %s with kbFolderId %s", groupId,
					kbFolderId));
		}
	}

}
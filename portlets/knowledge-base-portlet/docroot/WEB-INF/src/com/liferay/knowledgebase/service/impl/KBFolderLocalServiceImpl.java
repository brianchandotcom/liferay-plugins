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
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;

import java.util.Date;
import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class KBFolderLocalServiceImpl extends KBFolderLocalServiceBaseImpl {

	@Override
	public KBFolder addKBFolder(
			long userId, long groupId, long parentResourceClassNameId,
			long parentResourcePrimKey, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		validateParent(parentResourceClassNameId, parentResourcePrimKey);

		long kbFolderId = counterLocalService.increment();

		KBFolder kbFolder = kbFolderPersistence.create(kbFolderId);

		User user = userPersistence.findByPrimaryKey(userId);

		Date now = DateUtil.newDate();

		kbFolder.setUuid(serviceContext.getUuid());
		kbFolder.setUserId(userId);
		kbFolder.setUserName(user.getFullName());
		kbFolder.setCompanyId(user.getCompanyId());
		kbFolder.setGroupId(groupId);
		kbFolder.setParentKbFolderId(parentResourcePrimKey);
		kbFolder.setName(name);
		kbFolder.setDescription(description);
		kbFolder.setModifiedDate(now);
		kbFolder.setCreateDate(now);

		kbFolderPersistence.update(kbFolder);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			resourceLocalService.addResources(
				kbFolder.getCompanyId(), kbFolder.getGroupId(),
				kbFolder.getUserId(), KBFolder.class.getName(),
				kbFolder.getKbFolderId(), false,
				serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			resourceLocalService.addModelResources(
				kbFolder.getCompanyId(), kbFolder.getGroupId(),
				kbFolder.getUserId(), KBFolder.class.getName(),
				kbFolder.getKbFolderId(), serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		return kbFolder;
	}

	@Override
	public List<KBFolder> getFolders(
			long groupId, long parentKbFolderId, int start, int end)
		throws PortalException, SystemException {

		validateFolder(groupId, parentKbFolderId);

		return kbFolderPersistence.findByG_P(
			groupId, parentKbFolderId, start, end);
	}

	@Override
	public int getFoldersCount(long groupId, long parentKbFolderId)
		throws PortalException, SystemException {

		validateFolder(groupId, parentKbFolderId);

		return kbFolderPersistence.countByG_P(groupId, parentKbFolderId);
	}

	@Override
	public KBFolder updateKBFolder(
			long userId, long groupId, long parentResourceClassNameId,
			long parentResourcePrimKey, long kbFolderId, String name,
			String description)
		throws PortalException, SystemException {

		validateParent(parentResourceClassNameId, parentResourcePrimKey);

		KBFolder kbFolder = kbFolderPersistence.findByPrimaryKey(kbFolderId);

		User user = userPersistence.findByPrimaryKey(userId);

		Date now = DateUtil.newDate();

		kbFolder.setUserId(userId);
		kbFolder.setUserName(user.getFullName());
		kbFolder.setCompanyId(user.getCompanyId());
		kbFolder.setGroupId(groupId);
		kbFolder.setParentKbFolderId(parentResourcePrimKey);
		kbFolder.setName(name);
		kbFolder.setDescription(description);
		kbFolder.setModifiedDate(now);

		return kbFolderPersistence.update(kbFolder);
	}

	protected void validateFolder(long groupId, long kbFolderId)
		throws NoSuchFolderException, SystemException {

		if (kbFolderId == KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		KBFolder kbFolder = kbFolderPersistence.findByPrimaryKey(kbFolderId);

		if (kbFolder.getGroupId() != groupId) {
			throw new NoSuchFolderException(
				String.format(
					"No KBFolder found in group %s with kbFolderId %s", groupId,
					kbFolderId));
		}
	}

	protected void validateParent(
			long parentResourceClassNameId, long parentResourcePrimKey)
		throws PortalException, SystemException {

		long kbFolderClassNameId = classNameLocalService.getClassNameId(
			KBFolderConstants.getClassName());

		KBFolder parentKBFolder = null;

		if (parentResourceClassNameId == kbFolderClassNameId) {
			if (parentResourcePrimKey ==
					KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				return;
			}

			parentKBFolder = kbFolderPersistence.fetchByPrimaryKey(
				parentResourcePrimKey);
		}

		if (parentKBFolder == null) {
			throw new NoSuchFolderException(
				String.format(
					"No KBFolder found with kbFolderId %",
					parentResourcePrimKey));
		}
	}

}
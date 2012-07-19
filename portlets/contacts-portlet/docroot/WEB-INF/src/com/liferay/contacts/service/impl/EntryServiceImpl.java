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

package com.liferay.contacts.service.impl;

import com.liferay.contacts.model.Entry;
import com.liferay.contacts.service.base.EntryServiceBaseImpl;
import com.liferay.contacts.service.permission.ContactsEntryPermission;
import com.liferay.contacts.service.permission.ContactsPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;

/**
 * The implementation of the entry remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.contacts.service.EntryService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.contacts.service.base.EntryServiceBaseImpl
 * @see com.liferay.contacts.service.EntryServiceUtil
 */
public class EntryServiceImpl extends EntryServiceBaseImpl {
	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.contacts.service.EntryServiceUtil} to access the entry remote service.
	 */

	public Entry addEntry(
			long userId, String fullName, String emailAddress, String comments,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		ContactsPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_ENTRY);

		return entryLocalService.addEntry(
			userId, fullName, emailAddress, comments, serviceContext);
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		ContactsEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.DELETE);

		entryLocalService.deleteEntry(entryId);
	}

	public Entry updateEntry(
			long entryId, String fullName, String emailAddress, String comments,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		ContactsEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.UPDATE);

		return entryLocalService.updateEntry(
			entryId, fullName, emailAddress, comments, serviceContext);
	}

}
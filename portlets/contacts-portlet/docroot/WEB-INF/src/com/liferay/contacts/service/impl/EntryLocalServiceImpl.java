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

import com.liferay.contacts.DuplicateEntryEmailAddressException;
import com.liferay.contacts.EntryEmailAddressException;
import com.liferay.contacts.model.Entry;
import com.liferay.contacts.service.base.EntryLocalServiceBaseImpl;
import com.liferay.portal.ContactFullNameException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;

import java.util.Date;
import java.util.List;

/**
 * The implementation of the entry local service.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.contacts.service.base.EntryLocalServiceBaseImpl
 * @see com.liferay.contacts.service.EntryLocalService
 * @see com.liferay.contacts.service.EntryLocalServiceUtil
 */
public class EntryLocalServiceImpl extends EntryLocalServiceBaseImpl {
	public Entry addEntry(
			User user, String fullName, String emailAddress, String comments)
		throws PortalException, SystemException {

		long companyId = user.getCompanyId();
		long userId = user.getUserId();

		validate(companyId, userId, fullName, emailAddress);

		long contactId = counterLocalService.increment();

		Entry entry = entryPersistence.create(contactId);

		entry.setCompanyId(user.getCompanyId());
		entry.setUserId(user.getUserId());
		entry.setUserName(user.getFullName());
		entry.setFullName(fullName);
		entry.setEmailAddress(emailAddress);
		entry.setComments(comments);
		entry.setCreateDate(new Date());
		entry.setModifiedDate(new Date());

		entryPersistence.update(entry, true);

		return entry;
	}

	public List<Entry> getEntries(long companyId, long userId)
		throws SystemException {

		return entryPersistence.findByC_U(companyId, userId);
	}

	public List<Entry> getEntries(
			long companyId, long userId, String keywords, int start, int end)
		throws SystemException {

		return entryFinder.findByC_U_FN_EA(
			companyId, userId, keywords, start, end);
	}

	public int getEntriesCount(long companyId, long userId)
		throws SystemException {

		return entryPersistence.countByC_U(companyId, userId);
	}

	public int getEntriesCount(long companyId, long userId, String keywords)
		throws SystemException {

		return entryFinder.countByC_U_FN_EA(companyId, userId, keywords);
	}

	public List<Object> getUserAndEntries(
			long companyId, long userId, String keywords, int start, int end)
		throws SystemException {

		return entryFinder.findByC_U_FN_MN_LN_SN_EA(
			companyId, userId, keywords, start, end);
	}

	public int getUserAndEntriesCount(
			long companyId, long userId, String keywords)
		throws SystemException {

		return entryFinder.countByC_U_FN_MN_LN_SN_EA(companyId, userId, keywords);
	}

	public Entry updateEntry(
			long entryId, User user, String fullName, String emailAddress,
			String comments)
		throws PortalException, SystemException {

		long companyId = user.getCompanyId();
		long userId = user.getUserId();

		validate(companyId, entryId, userId, fullName, emailAddress);

		Entry entry = entryPersistence.findByPrimaryKey(entryId);

		entry.setCompanyId(user.getCompanyId());
		entry.setUserId(user.getUserId());
		entry.setUserName(user.getFullName());
		entry.setFullName(fullName);
		entry.setEmailAddress(emailAddress);
		entry.setComments(comments);
		entry.setModifiedDate(new Date());

		entryPersistence.update(entry, true);

		return entry;
	}

	protected void validate(
			long companyId, long userId, String fullName, String emailAddress)
		throws PortalException, SystemException {

		if (Validator.isNull(fullName)) {
			throw new ContactFullNameException();
		}

		validateEmailAddress(emailAddress);
		validateDuplicateEmailAddress(companyId, userId, emailAddress);
	}

	protected void validate(
			long companyId, long entryId, long userId, String fullName,
			String emailAddress)
		throws PortalException, SystemException {

		if (Validator.isNull(fullName)) {
			throw new ContactFullNameException();
		}

		validateEmailAddress(emailAddress);

		Entry entry = entryPersistence.findByPrimaryKey(entryId);

		if (!emailAddress.equalsIgnoreCase(entry.getEmailAddress())) {
			validateDuplicateEmailAddress(companyId, userId, emailAddress);
		}
	}

	protected void validateDuplicateEmailAddress(
			long companyId, long userId, String emailAddress)
		throws PortalException, SystemException {

		if (entryPersistence.fetchByC_U_EA(companyId, userId, emailAddress)
				!= null) {

			throw new DuplicateEntryEmailAddressException();
		}
	}

	protected void validateEmailAddress(String emailAddress)
		throws PortalException, SystemException {

		if (Validator.isNull(emailAddress)) {
			throw new EntryEmailAddressException(
				EntryEmailAddressException.EMPTY);
		}

		if (!Validator.isEmailAddress(emailAddress)) {
			throw new EntryEmailAddressException(
				EntryEmailAddressException.NOT_EMAIL);
		}
	}

}
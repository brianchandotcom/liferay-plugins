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

package com.liferay.oauth.service.impl;

import com.liferay.oauth.model.ApplicationUser;
import com.liferay.oauth.service.base.ApplicationUserLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The implementation of the application user local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.oauth.service.ApplicationUserLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.oauth.service.base.ApplicationUserLocalServiceBaseImpl
 * @see com.liferay.oauth.service.ApplicationUserLocalServiceUtil
 */
public class ApplicationUserLocalServiceImpl
	extends ApplicationUserLocalServiceBaseImpl {

	public ApplicationUser addApplicationUser(
			long userId, long applicationId, String accessToken,
			String accessSecret, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// ApplicationUser

		long oaauId = counterLocalService.increment();

		ApplicationUser applicationUser = applicationUserPersistence.create(
			oaauId);

		applicationUser.setUserId(userId);
		applicationUser.setApplicationId(applicationId);
		applicationUser.setAccessToken(accessToken);
		applicationUser.setAccessSecret(accessSecret);

		applicationUserPersistence.update(applicationUser);

		// Resources

		resourceLocalService.addResources(
			serviceContext.getCompanyId(), serviceContext.getScopeGroupId(),
			serviceContext.getUserId(), ApplicationUser.class.getName(),
			applicationUser.getPrimaryKey(), false, false, false);

		return applicationUser;
	}

	public ApplicationUser deleteApplicationUser(
			ApplicationUser applicationUser, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Resources

		resourceLocalService.deleteResource(
			serviceContext.getCompanyId(), ApplicationUser.class.getName(),
			ResourceConstants.SCOPE_COMPANY, applicationUser.getPrimaryKey());

		applicationUserPersistence.remove(applicationUser);

		return applicationUser;
	}

	public ApplicationUser deleteApplicationUser(
			long userId, long applicationId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		ApplicationUser applicationUser = applicationUserPersistence.findByU_AP(
			userId, applicationId);

		return deleteApplicationUser(applicationUser, serviceContext);
	}

	public ApplicationUser fetchApplicationUser(String accessToken)
		throws SystemException {

		return applicationUserPersistence.fetchByAccessToken(accessToken);
	}

	public ApplicationUser fetchApplicationUser(
			long userId, long applicationId)
		throws SystemException {

		return applicationUserPersistence.fetchByU_AP(userId, applicationId);
	}

	public ApplicationUser getApplicationUser(String accessToken)
		throws PortalException, SystemException {

		return applicationUserPersistence.findByAccessToken(accessToken);
	}

	public ApplicationUser getApplicationUser(
			long userId, long applicationId)
		throws PortalException, SystemException {

		return applicationUserPersistence.findByU_AP(userId, applicationId);
	}

	public List<ApplicationUser> getApplicationUsers(long applicationId)
		throws SystemException {

		return applicationUserPersistence.findByApplicationId(applicationId);
	}

	public List<ApplicationUser> getApplicationUsers(
			long applicationId, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return applicationUserPersistence.findByApplicationId(
			applicationId, start, end, orderByComparator);
	}

	public List<ApplicationUser> getApplicationUsersByUserId(
			long userId, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return applicationUserPersistence.findByUserId(
			userId, start, end, orderByComparator);
	}

	public int getApplicationUsersByUserIdCount(long userId)
		throws SystemException {

		return applicationUserPersistence.countByUserId(userId);
	}

	public int getApplicationUsersCount(long applicationId)
		throws SystemException {

		return applicationUserPersistence.countByApplicationId(applicationId);
	}

	public ApplicationUser updateApplicationUser(
			long userId, long applicationId, String accessToken,
			String accessSecret, ServiceContext serviceContext)
		throws PortalException, SystemException {

		ApplicationUser applicationUser = applicationUserPersistence.findByU_AP(
			userId, applicationId);

		applicationUser.setAccessToken(accessToken);
		applicationUser.setAccessSecret(accessSecret);

		applicationUserPersistence.update(applicationUser);

		return applicationUser;
	}

}
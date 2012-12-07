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

import com.liferay.oauth.OAuthUtil;
import com.liferay.oauth.model.Application;
import com.liferay.oauth.service.base.ApplicationLocalServiceBaseImpl;
import com.liferay.oauth.util.OAuthConstants;
import com.liferay.portal.RequiredFieldException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;

import java.net.MalformedURLException;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * The implementation of the application local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.oauth.service.ApplicationLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.oauth.service.base.ApplicationLocalServiceBaseImpl
 * @see com.liferay.oauth.service.ApplicationLocalServiceUtil
 */
public class ApplicationLocalServiceImpl
	extends ApplicationLocalServiceBaseImpl {
	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.oauth.service.ApplicationLocalServiceUtil} to access the application local service.
	 */
	/**
	 * Add info about new application that should use OAuth feature. Method will
	 * generate new consumer key and secret that will be used by this
	 * application to do authorized access to portal resources.
	 */
	public Application addApplication(
			long userId, String name, String description, String website,
			String callBackURL, int accessLevel, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		validate(name, website, callBackURL);

		Date now = new Date();

		// Application

		long applicationId = counterLocalService.increment();

		Application application = applicationPersistence.create(applicationId);

		application.setCompanyId(user.getCompanyId());
		application.setUserId(user.getUserId());
		application.setUserName(user.getFullName());
		application.setCreateDate(serviceContext.getCreateDate(now));
		application.setModifiedDate(serviceContext.getModifiedDate(now));
		application.setName(name);
		application.setDescription(description);
		application.setWebsite(website);
		application.setCallBackURL(callBackURL);
		application.setAccessLevel(accessLevel);

		// This is to support potential import

		String consumerKey = serviceContext.getUuid();

		if (Validator.isNull(consumerKey)) {
			consumerKey = PortalUUIDUtil.generate();
		}

		String consumerSecret = OAuthUtil.randomizeToken(consumerKey);

		application.setConsumerKey(consumerKey);
		application.setConsumerSecret(consumerSecret);

		applicationPersistence.update(application);

		// Resources

		resourceLocalService.addResources(
			application.getCompanyId(), 0, userId, Application.class.getName(),
			application.getApplicationId(), false, false, false);

		return application;
	}

	public Application deleteApplication(
			Application application, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Applications

		applicationUserPersistence.removeByApplicationId(
			application.getApplicationId());

		// Resources

		resourceLocalService.deleteResource(
			application.getCompanyId(), Application.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, application.getApplicationId());

		//Image

		ImageLocalServiceUtil.deleteImage(application.getLogoId());

		// Application

		applicationPersistence.remove(application);

		return application;
	}

	/**
	 * Delete OAuth application designated by applicationId. Method will
	 * delete all application user's authorizations, application and
	 * corresponding resource entries.
	 *
	 * @param applicationId
	 * @param serviceContext
	 * @return
	 * @throws PortalException
	 * @throws SystemException
	 */
	public Application deleteApplication(
			long applicationId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Application application = applicationPersistence.findByPrimaryKey(
			applicationId);

		return deleteApplication(application, serviceContext);
	}

	public Application fetchApplication(String consumerKey)
		throws SystemException {

		return applicationPersistence.fetchByConsumerKey(consumerKey);
	}

	public Application getApplication(String consumerKey)
		throws PortalException, SystemException {

		return applicationPersistence.findByConsumerKey(consumerKey);
	}

	public List<Application> getApplications(
			long companyId, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return applicationPersistence.findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	public int getApplicationsCount(long companyId) throws SystemException {
		return applicationPersistence.countByCompanyId(companyId);
	}

	public List<Application> search(
			long companyId, String keywords,
			LinkedHashMap<String, Object> params, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		if (params == null) {
			params = _emptyLinkedHashMap;
		}

		if (params.containsKey("userName")) {
			Long userId = (Long)params.get("userName");

			if (Validator.isNotNull(keywords)) {
				return applicationPersistence.findByU_N(
					userId, keywords, start, end, orderByComparator);
			}

			return applicationPersistence.findByUserId(
				userId, start, end, orderByComparator);
		}

		if (Validator.isNotNull(keywords)) {
			return applicationPersistence.findByC_N(
				companyId, keywords, start, end, orderByComparator);
		}

		return applicationPersistence.findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	public int searchCount(
			long companyId, String keywords,
			LinkedHashMap<String, Object> params)
		throws SystemException {

		if (params == null) {
			params = _emptyLinkedHashMap;
		}

		if (params.containsKey("userName")) {
			Long userId = (Long)params.get("userName");

			if (Validator.isNotNull(keywords)) {
				return applicationPersistence.countByU_N(userId, keywords);
			}

			return applicationPersistence.countByUserId(userId);
		}

		if (Validator.isNotNull(keywords)) {
			return applicationPersistence.countByC_N(companyId, keywords);
		}

		return applicationPersistence.countByCompanyId(companyId);
	}

	/**
	 * Update existing application that should use OAuth feature. If changed
	 * method will update name, description, website, callbackURL and
	 * access level.
	 */
	public Application updateApplication(
			long applicationId, String name, String description,
			String website, String callBackURL, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Application

		validate(name, website, callBackURL);

		Date now = new Date();

		Application application = applicationPersistence.findByPrimaryKey(
			applicationId);

		application.setModifiedDate(serviceContext.getModifiedDate(now));
		application.setName(name);
		application.setDescription(description);
		application.setWebsite(website);
		application.setCallBackURL(callBackURL);

		applicationPersistence.update(application);

		return application;
	}

	public Application updateLogo(long applicationId, byte[] bytes)
		throws PortalException, SystemException {

		Application application = applicationPersistence.findByPrimaryKey(
			applicationId);

		long logoId = application.getLogoId();

		if (logoId <= 0) {
			logoId = counterLocalService.increment();

			application.setLogoId(logoId);

			applicationPersistence.update(application);
		}

		ImageLocalServiceUtil.updateImage(logoId, bytes);

		return application;
	}

	private void validate(String name, String website, String callBackURL)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new RequiredFieldException(
				"required-field", OAuthConstants.NAME);
		}

		if (Validator.isNull(callBackURL)) {
			throw new RequiredFieldException(
				"required-field", OAuthConstants.CALLBACK_URL);
		}

		if (!Validator.isUrl(callBackURL)) {
			throw new PortalException(new MalformedURLException(callBackURL));
		}

		if (Validator.isNull(website)) {
			throw new RequiredFieldException(
				"required-field", OAuthConstants.WEBSITE);
		}

		if (!Validator.isUrl(website)) {
			throw new PortalException(new MalformedURLException(website));
		}
	}

	private LinkedHashMap<String, Object> _emptyLinkedHashMap =
		new LinkedHashMap<String, Object>(0);

}
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

package com.liferay.oauth.service.base;

import com.liferay.counter.service.CounterLocalService;

import com.liferay.oauth.model.ApplicationUser;
import com.liferay.oauth.service.ApplicationLocalService;
import com.liferay.oauth.service.ApplicationService;
import com.liferay.oauth.service.ApplicationUserLocalService;
import com.liferay.oauth.service.ApplicationUserService;
import com.liferay.oauth.service.persistence.ApplicationPersistence;
import com.liferay.oauth.service.persistence.ApplicationUserPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.BaseServiceImpl;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.persistence.UserPersistence;

import javax.sql.DataSource;

/**
 * The base implementation of the application user remote service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.oauth.service.impl.ApplicationUserServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.oauth.service.impl.ApplicationUserServiceImpl
 * @see com.liferay.oauth.service.ApplicationUserServiceUtil
 * @generated
 */
public abstract class ApplicationUserServiceBaseImpl extends BaseServiceImpl
	implements ApplicationUserService, IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.oauth.service.ApplicationUserServiceUtil} to access the application user remote service.
	 */

	/**
	 * Returns the application local service.
	 *
	 * @return the application local service
	 */
	public ApplicationLocalService getApplicationLocalService() {
		return applicationLocalService;
	}

	/**
	 * Sets the application local service.
	 *
	 * @param applicationLocalService the application local service
	 */
	public void setApplicationLocalService(
		ApplicationLocalService applicationLocalService) {
		this.applicationLocalService = applicationLocalService;
	}

	/**
	 * Returns the application remote service.
	 *
	 * @return the application remote service
	 */
	public ApplicationService getApplicationService() {
		return applicationService;
	}

	/**
	 * Sets the application remote service.
	 *
	 * @param applicationService the application remote service
	 */
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}

	/**
	 * Returns the application persistence.
	 *
	 * @return the application persistence
	 */
	public ApplicationPersistence getApplicationPersistence() {
		return applicationPersistence;
	}

	/**
	 * Sets the application persistence.
	 *
	 * @param applicationPersistence the application persistence
	 */
	public void setApplicationPersistence(
		ApplicationPersistence applicationPersistence) {
		this.applicationPersistence = applicationPersistence;
	}

	/**
	 * Returns the application user local service.
	 *
	 * @return the application user local service
	 */
	public ApplicationUserLocalService getApplicationUserLocalService() {
		return applicationUserLocalService;
	}

	/**
	 * Sets the application user local service.
	 *
	 * @param applicationUserLocalService the application user local service
	 */
	public void setApplicationUserLocalService(
		ApplicationUserLocalService applicationUserLocalService) {
		this.applicationUserLocalService = applicationUserLocalService;
	}

	/**
	 * Returns the application user remote service.
	 *
	 * @return the application user remote service
	 */
	public ApplicationUserService getApplicationUserService() {
		return applicationUserService;
	}

	/**
	 * Sets the application user remote service.
	 *
	 * @param applicationUserService the application user remote service
	 */
	public void setApplicationUserService(
		ApplicationUserService applicationUserService) {
		this.applicationUserService = applicationUserService;
	}

	/**
	 * Returns the application user persistence.
	 *
	 * @return the application user persistence
	 */
	public ApplicationUserPersistence getApplicationUserPersistence() {
		return applicationUserPersistence;
	}

	/**
	 * Sets the application user persistence.
	 *
	 * @param applicationUserPersistence the application user persistence
	 */
	public void setApplicationUserPersistence(
		ApplicationUserPersistence applicationUserPersistence) {
		this.applicationUserPersistence = applicationUserPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		Class<?> clazz = getClass();

		_classLoader = clazz.getClassLoader();
	}

	public void destroy() {
	}

	/**
	 * Returns the Spring bean ID for this bean.
	 *
	 * @return the Spring bean ID for this bean
	 */
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		if (contextClassLoader != _classLoader) {
			currentThread.setContextClassLoader(_classLoader);
		}

		try {
			return _clpInvoker.invokeMethod(name, parameterTypes, arguments);
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	protected Class<?> getModelClass() {
		return ApplicationUser.class;
	}

	protected String getModelClassName() {
		return ApplicationUser.class.getName();
	}

	/**
	 * Performs an SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) throws SystemException {
		try {
			DataSource dataSource = applicationUserPersistence.getDataSource();

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = ApplicationLocalService.class)
	protected ApplicationLocalService applicationLocalService;
	@BeanReference(type = ApplicationService.class)
	protected ApplicationService applicationService;
	@BeanReference(type = ApplicationPersistence.class)
	protected ApplicationPersistence applicationPersistence;
	@BeanReference(type = ApplicationUserLocalService.class)
	protected ApplicationUserLocalService applicationUserLocalService;
	@BeanReference(type = ApplicationUserService.class)
	protected ApplicationUserService applicationUserService;
	@BeanReference(type = ApplicationUserPersistence.class)
	protected ApplicationUserPersistence applicationUserPersistence;
	@BeanReference(type = CounterLocalService.class)
	protected CounterLocalService counterLocalService;
	@BeanReference(type = ResourceLocalService.class)
	protected ResourceLocalService resourceLocalService;
	@BeanReference(type = UserLocalService.class)
	protected UserLocalService userLocalService;
	@BeanReference(type = UserService.class)
	protected UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private String _beanIdentifier;
	private ClassLoader _classLoader;
	private ApplicationUserServiceClpInvoker _clpInvoker = new ApplicationUserServiceClpInvoker();
}
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

package com.liferay.oauth.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link ApplicationLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ApplicationLocalService
 * @generated
 */
public class ApplicationLocalServiceWrapper implements ApplicationLocalService,
	ServiceWrapper<ApplicationLocalService> {
	public ApplicationLocalServiceWrapper(
		ApplicationLocalService applicationLocalService) {
		_applicationLocalService = applicationLocalService;
	}

	/**
	* Adds the application to the database. Also notifies the appropriate model listeners.
	*
	* @param application the application
	* @return the application that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.oauth.model.Application addApplication(
		com.liferay.oauth.model.Application application)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _applicationLocalService.addApplication(application);
	}

	/**
	* Creates a new application with the primary key. Does not add the application to the database.
	*
	* @param applicationId the primary key for the new application
	* @return the new application
	*/
	public com.liferay.oauth.model.Application createApplication(
		long applicationId) {
		return _applicationLocalService.createApplication(applicationId);
	}

	/**
	* Deletes the application with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param applicationId the primary key of the application
	* @return the application that was removed
	* @throws PortalException if a application with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.oauth.model.Application deleteApplication(
		long applicationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _applicationLocalService.deleteApplication(applicationId);
	}

	/**
	* Deletes the application from the database. Also notifies the appropriate model listeners.
	*
	* @param application the application
	* @return the application that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.oauth.model.Application deleteApplication(
		com.liferay.oauth.model.Application application)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _applicationLocalService.deleteApplication(application);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _applicationLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _applicationLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth.model.impl.ApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _applicationLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth.model.impl.ApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _applicationLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _applicationLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.oauth.model.Application fetchApplication(
		long applicationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _applicationLocalService.fetchApplication(applicationId);
	}

	/**
	* Returns the application with the primary key.
	*
	* @param applicationId the primary key of the application
	* @return the application
	* @throws PortalException if a application with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.oauth.model.Application getApplication(
		long applicationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _applicationLocalService.getApplication(applicationId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _applicationLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the applications.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth.model.impl.ApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of applications
	* @param end the upper bound of the range of applications (not inclusive)
	* @return the range of applications
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.oauth.model.Application> getApplications(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _applicationLocalService.getApplications(start, end);
	}

	/**
	* Returns the number of applications.
	*
	* @return the number of applications
	* @throws SystemException if a system exception occurred
	*/
	public int getApplicationsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _applicationLocalService.getApplicationsCount();
	}

	/**
	* Updates the application in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param application the application
	* @return the application that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.oauth.model.Application updateApplication(
		com.liferay.oauth.model.Application application)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _applicationLocalService.updateApplication(application);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _applicationLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_applicationLocalService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _applicationLocalService.invokeMethod(name, parameterTypes,
			arguments);
	}

	/**
	* Add info about new application that should use OAuth feature. Method will
	* generate new consumer key and secret that will be used by this
	* application to do authorized access to portal resources.
	*/
	public com.liferay.oauth.model.Application addApplication(long userId,
		java.lang.String name, java.lang.String description,
		java.lang.String website, java.lang.String callBackURL,
		int accessLevel,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _applicationLocalService.addApplication(userId, name,
			description, website, callBackURL, accessLevel, serviceContext);
	}

	public com.liferay.oauth.model.Application deleteApplication(
		com.liferay.oauth.model.Application application,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _applicationLocalService.deleteApplication(application,
			serviceContext);
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
	public com.liferay.oauth.model.Application deleteApplication(
		long applicationId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _applicationLocalService.deleteApplication(applicationId,
			serviceContext);
	}

	public com.liferay.oauth.model.Application fetchApplication(
		java.lang.String consumerKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _applicationLocalService.fetchApplication(consumerKey);
	}

	public com.liferay.oauth.model.Application getApplication(
		java.lang.String consumerKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _applicationLocalService.getApplication(consumerKey);
	}

	public java.util.List<com.liferay.oauth.model.Application> getApplications(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _applicationLocalService.getApplications(companyId, start, end,
			orderByComparator);
	}

	public int getApplicationsCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _applicationLocalService.getApplicationsCount(companyId);
	}

	public java.util.List<com.liferay.oauth.model.Application> search(
		long companyId, java.lang.String keywords,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _applicationLocalService.search(companyId, keywords, params,
			start, end, orderByComparator);
	}

	public int searchCount(long companyId, java.lang.String keywords,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _applicationLocalService.searchCount(companyId, keywords, params);
	}

	/**
	* Update existing application that should use OAuth feature. If changed
	* method will update name, description, website, callbackURL and
	* access level.
	*/
	public com.liferay.oauth.model.Application updateApplication(
		long applicationId, java.lang.String name,
		java.lang.String description, java.lang.String website,
		java.lang.String callBackURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _applicationLocalService.updateApplication(applicationId, name,
			description, website, callBackURL, serviceContext);
	}

	public com.liferay.oauth.model.Application updateLogo(long applicationId,
		byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _applicationLocalService.updateLogo(applicationId, bytes);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public ApplicationLocalService getWrappedApplicationLocalService() {
		return _applicationLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedApplicationLocalService(
		ApplicationLocalService applicationLocalService) {
		_applicationLocalService = applicationLocalService;
	}

	public ApplicationLocalService getWrappedService() {
		return _applicationLocalService;
	}

	public void setWrappedService(
		ApplicationLocalService applicationLocalService) {
		_applicationLocalService = applicationLocalService;
	}

	private ApplicationLocalService _applicationLocalService;
}
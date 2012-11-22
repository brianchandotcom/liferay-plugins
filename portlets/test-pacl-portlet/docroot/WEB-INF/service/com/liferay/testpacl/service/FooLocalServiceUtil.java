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

package com.liferay.testpacl.service;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableLocalService;

/**
 * The utility for the foo local service. This utility wraps {@link com.liferay.testpacl.service.impl.FooLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FooLocalService
 * @see com.liferay.testpacl.service.base.FooLocalServiceBaseImpl
 * @see com.liferay.testpacl.service.impl.FooLocalServiceImpl
 * @generated
 */
public class FooLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.testpacl.service.impl.FooLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the foo to the database. Also notifies the appropriate model listeners.
	*
	* @param foo the foo
	* @return the foo that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.testpacl.model.Foo addFoo(
		com.liferay.testpacl.model.Foo foo)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addFoo(foo);
	}

	/**
	* Creates a new foo with the primary key. Does not add the foo to the database.
	*
	* @param fooId the primary key for the new foo
	* @return the new foo
	*/
	public static com.liferay.testpacl.model.Foo createFoo(long fooId) {
		return getService().createFoo(fooId);
	}

	/**
	* Deletes the foo with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fooId the primary key of the foo
	* @return the foo that was removed
	* @throws PortalException if a foo with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.testpacl.model.Foo deleteFoo(long fooId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteFoo(fooId);
	}

	/**
	* Deletes the foo from the database. Also notifies the appropriate model listeners.
	*
	* @param foo the foo
	* @return the foo that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.testpacl.model.Foo deleteFoo(
		com.liferay.testpacl.model.Foo foo)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteFoo(foo);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. When orderByComparator is specified, the query will include the given ORDER BY logic. When orderByComparator is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), the query will include the default ORDER BY logic from FooModelImpl. If both orderByComparator and pagination are absent, for performance reason, the query will not have a ORDER BY clause, on returning the result set will be sorted on portal side by PK ASC order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. When orderByComparator is specified, the query will include the given ORDER BY logic. When orderByComparator is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), the query will include the default ORDER BY logic from FooModelImpl. If both orderByComparator and pagination are absent, for performance reason, the query will not have a ORDER BY clause, on returning the result set will be sorted on portal side by PK ASC order.
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.testpacl.model.Foo fetchFoo(long fooId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchFoo(fooId);
	}

	/**
	* Returns the foo with the primary key.
	*
	* @param fooId the primary key of the foo
	* @return the foo
	* @throws PortalException if a foo with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.testpacl.model.Foo getFoo(long fooId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFoo(fooId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the foos.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. When orderByComparator is specified, the query will include the given ORDER BY logic. When orderByComparator is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), the query will include the default ORDER BY logic from FooModelImpl. If both orderByComparator and pagination are absent, for performance reason, the query will not have a ORDER BY clause, on returning the result set will be sorted on portal side by PK ASC order.
	* </p>
	*
	* @param start the lower bound of the range of foos
	* @param end the upper bound of the range of foos (not inclusive)
	* @return the range of foos
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.testpacl.model.Foo> getFoos(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFoos(start, end);
	}

	/**
	* Returns the number of foos.
	*
	* @return the number of foos
	* @throws SystemException if a system exception occurred
	*/
	public static int getFoosCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFoosCount();
	}

	/**
	* Updates the foo in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param foo the foo
	* @return the foo that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.testpacl.model.Foo updateFoo(
		com.liferay.testpacl.model.Foo foo)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateFoo(foo);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return getService().invokeMethod(name, parameterTypes, arguments);
	}

	public static com.liferay.portal.model.Company getCompanyPersistence_FindByPrimaryKey(
		long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyPersistence_FindByPrimaryKey(companyId);
	}

	public static com.liferay.portal.model.Company getCompanyUtil_FindByPrimaryKey(
		long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyUtil_FindByPrimaryKey(companyId);
	}

	public static java.util.List<com.liferay.chat.model.Entry> getEntryLocalServiceUtil_GetEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntryLocalServiceUtil_GetEntries(start, end);
	}

	public static com.liferay.chat.model.Entry getEntryLocalServiceUtil_GetEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntryLocalServiceUtil_GetEntry(entryId);
	}

	public static com.liferay.portal.model.Group getGroupPersistence_FindByPrimaryKey(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupPersistence_FindByPrimaryKey(groupId);
	}

	public static com.liferay.portal.model.Group getGroupUtil_FindByPrimaryKey(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupUtil_FindByPrimaryKey(groupId);
	}

	public static int getPortalService_GetBuildNumber() {
		return getService().getPortalService_GetBuildNumber();
	}

	public static int getPortalService_TestGetBuildNumber() {
		return getService().getPortalService_TestGetBuildNumber();
	}

	public static boolean getPortalService_TestHasClassName()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortalService_TestHasClassName();
	}

	public static int getPortalServiceUtil_GetBuildNumber() {
		return getService().getPortalServiceUtil_GetBuildNumber();
	}

	public static int getPortalServiceUtil_TestGetBuildNumber() {
		return getService().getPortalServiceUtil_TestGetBuildNumber();
	}

	public static boolean getPortalServiceUtil_TestHasClassName()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortalServiceUtil_TestHasClassName();
	}

	public static int getReleaseInfo_GetBuildNumber() {
		return getService().getReleaseInfo_GetBuildNumber();
	}

	public static com.liferay.chat.model.Status getStatusLocalServiceUtil_GetStatus(
		long statusId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getStatusLocalServiceUtil_GetStatus(statusId);
	}

	public static java.util.List<com.liferay.chat.model.Status> getStatusLocalServiceUtil_GetStatuses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStatusLocalServiceUtil_GetStatuses(start, end);
	}

	public static com.liferay.portal.model.User getUserPersistence_FindByPrimaryKey(
		long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserPersistence_FindByPrimaryKey(userId);
	}

	public static com.liferay.portal.model.User getUserUtil_FindByPrimaryKey(
		long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserUtil_FindByPrimaryKey(userId);
	}

	public static void clearService() {
		_service = null;
	}

	public static FooLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					FooLocalService.class.getName());

			if (invokableLocalService instanceof FooLocalService) {
				_service = (FooLocalService)invokableLocalService;
			}
			else {
				_service = new FooLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(FooLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	public void setService(FooLocalService service) {
	}

	private static FooLocalService _service;
}
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

package com.liferay.privatemessaging.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link UserThreadLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserThreadLocalService
 * @generated
 */
public class UserThreadLocalServiceWrapper implements UserThreadLocalService,
	ServiceWrapper<UserThreadLocalService> {
	public UserThreadLocalServiceWrapper(
		UserThreadLocalService userThreadLocalService) {
		_userThreadLocalService = userThreadLocalService;
	}

	/**
	* Adds the user thread to the database. Also notifies the appropriate model listeners.
	*
	* @param userThread the user thread
	* @return the user thread that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.privatemessaging.model.UserThread addUserThread(
		com.liferay.privatemessaging.model.UserThread userThread)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userThreadLocalService.addUserThread(userThread);
	}

	/**
	* Creates a new user thread with the primary key. Does not add the user thread to the database.
	*
	* @param userThreadId the primary key for the new user thread
	* @return the new user thread
	*/
	public com.liferay.privatemessaging.model.UserThread createUserThread(
		long userThreadId) {
		return _userThreadLocalService.createUserThread(userThreadId);
	}

	/**
	* Deletes the user thread with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userThreadId the primary key of the user thread
	* @return the user thread that was removed
	* @throws PortalException if a user thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.privatemessaging.model.UserThread deleteUserThread(
		long userThreadId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userThreadLocalService.deleteUserThread(userThreadId);
	}

	/**
	* Deletes the user thread from the database. Also notifies the appropriate model listeners.
	*
	* @param userThread the user thread
	* @return the user thread that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.privatemessaging.model.UserThread deleteUserThread(
		com.liferay.privatemessaging.model.UserThread userThread)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userThreadLocalService.deleteUserThread(userThread);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _userThreadLocalService.dynamicQuery();
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
		return _userThreadLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. When orderByComparator is specified, the query will include the given ORDER BY logic. When orderByComparator is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), the query will include the default ORDER BY logic from UserThreadModelImpl. If both orderByComparator and pagination are absent, for performance reason, the query will not have a ORDER BY clause, on returning the result set will be sorted on portal side by PK ASC order.
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
		return _userThreadLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. When orderByComparator is specified, the query will include the given ORDER BY logic. When orderByComparator is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), the query will include the default ORDER BY logic from UserThreadModelImpl. If both orderByComparator and pagination are absent, for performance reason, the query will not have a ORDER BY clause, on returning the result set will be sorted on portal side by PK ASC order.
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
		return _userThreadLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _userThreadLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.privatemessaging.model.UserThread fetchUserThread(
		long userThreadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userThreadLocalService.fetchUserThread(userThreadId);
	}

	/**
	* Returns the user thread with the primary key.
	*
	* @param userThreadId the primary key of the user thread
	* @return the user thread
	* @throws PortalException if a user thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.privatemessaging.model.UserThread getUserThread(
		long userThreadId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userThreadLocalService.getUserThread(userThreadId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userThreadLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the user threads.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. When orderByComparator is specified, the query will include the given ORDER BY logic. When orderByComparator is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), the query will include the default ORDER BY logic from UserThreadModelImpl. If both orderByComparator and pagination are absent, for performance reason, the query will not have a ORDER BY clause, on returning the result set will be sorted on portal side by PK ASC order.
	* </p>
	*
	* @param start the lower bound of the range of user threads
	* @param end the upper bound of the range of user threads (not inclusive)
	* @return the range of user threads
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.privatemessaging.model.UserThread> getUserThreads(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userThreadLocalService.getUserThreads(start, end);
	}

	/**
	* Returns the number of user threads.
	*
	* @return the number of user threads
	* @throws SystemException if a system exception occurred
	*/
	public int getUserThreadsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userThreadLocalService.getUserThreadsCount();
	}

	/**
	* Updates the user thread in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param userThread the user thread
	* @return the user thread that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.privatemessaging.model.UserThread updateUserThread(
		com.liferay.privatemessaging.model.UserThread userThread)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userThreadLocalService.updateUserThread(userThread);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _userThreadLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_userThreadLocalService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _userThreadLocalService.invokeMethod(name, parameterTypes,
			arguments);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addPrivateMessage(
		long userId, long mbThreadId, java.lang.String to,
		java.lang.String subject, java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.io.InputStream>> inputStreamOVPs,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userThreadLocalService.addPrivateMessage(userId, mbThreadId,
			to, subject, body, inputStreamOVPs, themeDisplay);
	}

	public com.liferay.portlet.messageboards.model.MBMessage addPrivateMessageBranch(
		long userId, long parentMBMessageId, java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.io.InputStream>> inputStreamOVPs,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userThreadLocalService.addPrivateMessageBranch(userId,
			parentMBMessageId, body, inputStreamOVPs, themeDisplay);
	}

	public void addUserThread(long userId, long mbThreadId,
		long topMBMessageId, boolean read, boolean deleted)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userThreadLocalService.addUserThread(userId, mbThreadId,
			topMBMessageId, read, deleted);
	}

	public void deleteUser(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userThreadLocalService.deleteUser(userId);
	}

	public void deleteUserThread(long userId, long mbThreadId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userThreadLocalService.deleteUserThread(userId, mbThreadId);
	}

	public java.util.List<com.liferay.privatemessaging.model.UserThread> getMBThreadUserThreads(
		long mbThreadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userThreadLocalService.getMBThreadUserThreads(mbThreadId);
	}

	public com.liferay.privatemessaging.model.UserThread getUserThread(
		long userId, long mbThreadId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userThreadLocalService.getUserThread(userId, mbThreadId);
	}

	public int getUserUserThreadCount(long userId, boolean deleted)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userThreadLocalService.getUserUserThreadCount(userId, deleted);
	}

	public int getUserUserThreadCount(long userId, boolean read, boolean deleted)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userThreadLocalService.getUserUserThreadCount(userId, read,
			deleted);
	}

	public java.util.List<com.liferay.privatemessaging.model.UserThread> getUserUserThreads(
		long userId, boolean deleted)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userThreadLocalService.getUserUserThreads(userId, deleted);
	}

	public java.util.List<com.liferay.privatemessaging.model.UserThread> getUserUserThreads(
		long userId, boolean read, boolean deleted)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userThreadLocalService.getUserUserThreads(userId, read, deleted);
	}

	public java.util.List<com.liferay.privatemessaging.model.UserThread> getUserUserThreads(
		long userId, boolean deleted, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userThreadLocalService.getUserUserThreads(userId, deleted,
			start, end);
	}

	public void markUserThreadAsRead(long userId, long mbThreadId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userThreadLocalService.markUserThreadAsRead(userId, mbThreadId);
	}

	public void markUserThreadAsUnread(long userId, long mbThreadId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userThreadLocalService.markUserThreadAsUnread(userId, mbThreadId);
	}

	public void updateUserName(com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userThreadLocalService.updateUserName(user);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public UserThreadLocalService getWrappedUserThreadLocalService() {
		return _userThreadLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedUserThreadLocalService(
		UserThreadLocalService userThreadLocalService) {
		_userThreadLocalService = userThreadLocalService;
	}

	public UserThreadLocalService getWrappedService() {
		return _userThreadLocalService;
	}

	public void setWrappedService(UserThreadLocalService userThreadLocalService) {
		_userThreadLocalService = userThreadLocalService;
	}

	private UserThreadLocalService _userThreadLocalService;
}
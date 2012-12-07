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

package com.liferay.oauth.service.persistence;

import com.liferay.oauth.model.ApplicationUser;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the application user service. This utility wraps {@link ApplicationUserPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ApplicationUserPersistence
 * @see ApplicationUserPersistenceImpl
 * @generated
 */
public class ApplicationUserUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(ApplicationUser applicationUser) {
		getPersistence().clearCache(applicationUser);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ApplicationUser> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ApplicationUser> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ApplicationUser> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static ApplicationUser update(ApplicationUser applicationUser)
		throws SystemException {
		return getPersistence().update(applicationUser);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static ApplicationUser update(ApplicationUser applicationUser,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(applicationUser, serviceContext);
	}

	/**
	* Returns the application user where accessToken = &#63; or throws a {@link com.liferay.oauth.NoSuchApplicationUserException} if it could not be found.
	*
	* @param accessToken the access token
	* @return the matching application user
	* @throws com.liferay.oauth.NoSuchApplicationUserException if a matching application user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.oauth.model.ApplicationUser findByAccessToken(
		java.lang.String accessToken)
		throws com.liferay.oauth.NoSuchApplicationUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByAccessToken(accessToken);
	}

	/**
	* Returns the application user where accessToken = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param accessToken the access token
	* @return the matching application user, or <code>null</code> if a matching application user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.oauth.model.ApplicationUser fetchByAccessToken(
		java.lang.String accessToken)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByAccessToken(accessToken);
	}

	/**
	* Returns the application user where accessToken = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param accessToken the access token
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching application user, or <code>null</code> if a matching application user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.oauth.model.ApplicationUser fetchByAccessToken(
		java.lang.String accessToken, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByAccessToken(accessToken, retrieveFromCache);
	}

	/**
	* Removes the application user where accessToken = &#63; from the database.
	*
	* @param accessToken the access token
	* @return the application user that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.oauth.model.ApplicationUser removeByAccessToken(
		java.lang.String accessToken)
		throws com.liferay.oauth.NoSuchApplicationUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().removeByAccessToken(accessToken);
	}

	/**
	* Returns the number of application users where accessToken = &#63;.
	*
	* @param accessToken the access token
	* @return the number of matching application users
	* @throws SystemException if a system exception occurred
	*/
	public static int countByAccessToken(java.lang.String accessToken)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByAccessToken(accessToken);
	}

	/**
	* Returns all the application users where applicationId = &#63;.
	*
	* @param applicationId the application ID
	* @return the matching application users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.oauth.model.ApplicationUser> findByApplicationId(
		long applicationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByApplicationId(applicationId);
	}

	/**
	* Returns a range of all the application users where applicationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth.model.impl.ApplicationUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param applicationId the application ID
	* @param start the lower bound of the range of application users
	* @param end the upper bound of the range of application users (not inclusive)
	* @return the range of matching application users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.oauth.model.ApplicationUser> findByApplicationId(
		long applicationId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByApplicationId(applicationId, start, end);
	}

	/**
	* Returns an ordered range of all the application users where applicationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth.model.impl.ApplicationUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param applicationId the application ID
	* @param start the lower bound of the range of application users
	* @param end the upper bound of the range of application users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching application users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.oauth.model.ApplicationUser> findByApplicationId(
		long applicationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByApplicationId(applicationId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first application user in the ordered set where applicationId = &#63;.
	*
	* @param applicationId the application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching application user
	* @throws com.liferay.oauth.NoSuchApplicationUserException if a matching application user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.oauth.model.ApplicationUser findByApplicationId_First(
		long applicationId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.oauth.NoSuchApplicationUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByApplicationId_First(applicationId, orderByComparator);
	}

	/**
	* Returns the first application user in the ordered set where applicationId = &#63;.
	*
	* @param applicationId the application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching application user, or <code>null</code> if a matching application user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.oauth.model.ApplicationUser fetchByApplicationId_First(
		long applicationId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByApplicationId_First(applicationId, orderByComparator);
	}

	/**
	* Returns the last application user in the ordered set where applicationId = &#63;.
	*
	* @param applicationId the application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching application user
	* @throws com.liferay.oauth.NoSuchApplicationUserException if a matching application user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.oauth.model.ApplicationUser findByApplicationId_Last(
		long applicationId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.oauth.NoSuchApplicationUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByApplicationId_Last(applicationId, orderByComparator);
	}

	/**
	* Returns the last application user in the ordered set where applicationId = &#63;.
	*
	* @param applicationId the application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching application user, or <code>null</code> if a matching application user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.oauth.model.ApplicationUser fetchByApplicationId_Last(
		long applicationId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByApplicationId_Last(applicationId, orderByComparator);
	}

	/**
	* Returns the application users before and after the current application user in the ordered set where applicationId = &#63;.
	*
	* @param oaauId the primary key of the current application user
	* @param applicationId the application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next application user
	* @throws com.liferay.oauth.NoSuchApplicationUserException if a application user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.oauth.model.ApplicationUser[] findByApplicationId_PrevAndNext(
		long oaauId, long applicationId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.oauth.NoSuchApplicationUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByApplicationId_PrevAndNext(oaauId, applicationId,
			orderByComparator);
	}

	/**
	* Returns all the application users that the user has permission to view where applicationId = &#63;.
	*
	* @param applicationId the application ID
	* @return the matching application users that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.oauth.model.ApplicationUser> filterFindByApplicationId(
		long applicationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByApplicationId(applicationId);
	}

	/**
	* Returns a range of all the application users that the user has permission to view where applicationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth.model.impl.ApplicationUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param applicationId the application ID
	* @param start the lower bound of the range of application users
	* @param end the upper bound of the range of application users (not inclusive)
	* @return the range of matching application users that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.oauth.model.ApplicationUser> filterFindByApplicationId(
		long applicationId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByApplicationId(applicationId, start, end);
	}

	/**
	* Returns an ordered range of all the application users that the user has permissions to view where applicationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth.model.impl.ApplicationUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param applicationId the application ID
	* @param start the lower bound of the range of application users
	* @param end the upper bound of the range of application users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching application users that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.oauth.model.ApplicationUser> filterFindByApplicationId(
		long applicationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByApplicationId(applicationId, start, end,
			orderByComparator);
	}

	/**
	* Returns the application users before and after the current application user in the ordered set of application users that the user has permission to view where applicationId = &#63;.
	*
	* @param oaauId the primary key of the current application user
	* @param applicationId the application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next application user
	* @throws com.liferay.oauth.NoSuchApplicationUserException if a application user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.oauth.model.ApplicationUser[] filterFindByApplicationId_PrevAndNext(
		long oaauId, long applicationId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.oauth.NoSuchApplicationUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByApplicationId_PrevAndNext(oaauId,
			applicationId, orderByComparator);
	}

	/**
	* Removes all the application users where applicationId = &#63; from the database.
	*
	* @param applicationId the application ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByApplicationId(long applicationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByApplicationId(applicationId);
	}

	/**
	* Returns the number of application users where applicationId = &#63;.
	*
	* @param applicationId the application ID
	* @return the number of matching application users
	* @throws SystemException if a system exception occurred
	*/
	public static int countByApplicationId(long applicationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByApplicationId(applicationId);
	}

	/**
	* Returns the number of application users that the user has permission to view where applicationId = &#63;.
	*
	* @param applicationId the application ID
	* @return the number of matching application users that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByApplicationId(long applicationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByApplicationId(applicationId);
	}

	/**
	* Returns all the application users where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching application users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.oauth.model.ApplicationUser> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns a range of all the application users where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth.model.impl.ApplicationUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of application users
	* @param end the upper bound of the range of application users (not inclusive)
	* @return the range of matching application users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.oauth.model.ApplicationUser> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the application users where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth.model.impl.ApplicationUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of application users
	* @param end the upper bound of the range of application users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching application users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.oauth.model.ApplicationUser> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns the first application user in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching application user
	* @throws com.liferay.oauth.NoSuchApplicationUserException if a matching application user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.oauth.model.ApplicationUser findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.oauth.NoSuchApplicationUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first application user in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching application user, or <code>null</code> if a matching application user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.oauth.model.ApplicationUser fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last application user in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching application user
	* @throws com.liferay.oauth.NoSuchApplicationUserException if a matching application user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.oauth.model.ApplicationUser findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.oauth.NoSuchApplicationUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last application user in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching application user, or <code>null</code> if a matching application user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.oauth.model.ApplicationUser fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the application users before and after the current application user in the ordered set where userId = &#63;.
	*
	* @param oaauId the primary key of the current application user
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next application user
	* @throws com.liferay.oauth.NoSuchApplicationUserException if a application user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.oauth.model.ApplicationUser[] findByUserId_PrevAndNext(
		long oaauId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.oauth.NoSuchApplicationUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId_PrevAndNext(oaauId, userId, orderByComparator);
	}

	/**
	* Returns all the application users that the user has permission to view where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching application users that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.oauth.model.ApplicationUser> filterFindByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByUserId(userId);
	}

	/**
	* Returns a range of all the application users that the user has permission to view where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth.model.impl.ApplicationUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of application users
	* @param end the upper bound of the range of application users (not inclusive)
	* @return the range of matching application users that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.oauth.model.ApplicationUser> filterFindByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the application users that the user has permissions to view where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth.model.impl.ApplicationUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of application users
	* @param end the upper bound of the range of application users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching application users that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.oauth.model.ApplicationUser> filterFindByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns the application users before and after the current application user in the ordered set of application users that the user has permission to view where userId = &#63;.
	*
	* @param oaauId the primary key of the current application user
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next application user
	* @throws com.liferay.oauth.NoSuchApplicationUserException if a application user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.oauth.model.ApplicationUser[] filterFindByUserId_PrevAndNext(
		long oaauId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.oauth.NoSuchApplicationUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByUserId_PrevAndNext(oaauId, userId,
			orderByComparator);
	}

	/**
	* Removes all the application users where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Returns the number of application users where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching application users
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns the number of application users that the user has permission to view where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching application users that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByUserId(userId);
	}

	/**
	* Returns the application user where userId = &#63; and applicationId = &#63; or throws a {@link com.liferay.oauth.NoSuchApplicationUserException} if it could not be found.
	*
	* @param userId the user ID
	* @param applicationId the application ID
	* @return the matching application user
	* @throws com.liferay.oauth.NoSuchApplicationUserException if a matching application user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.oauth.model.ApplicationUser findByU_AP(
		long userId, long applicationId)
		throws com.liferay.oauth.NoSuchApplicationUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByU_AP(userId, applicationId);
	}

	/**
	* Returns the application user where userId = &#63; and applicationId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param applicationId the application ID
	* @return the matching application user, or <code>null</code> if a matching application user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.oauth.model.ApplicationUser fetchByU_AP(
		long userId, long applicationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByU_AP(userId, applicationId);
	}

	/**
	* Returns the application user where userId = &#63; and applicationId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param applicationId the application ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching application user, or <code>null</code> if a matching application user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.oauth.model.ApplicationUser fetchByU_AP(
		long userId, long applicationId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByU_AP(userId, applicationId, retrieveFromCache);
	}

	/**
	* Removes the application user where userId = &#63; and applicationId = &#63; from the database.
	*
	* @param userId the user ID
	* @param applicationId the application ID
	* @return the application user that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.oauth.model.ApplicationUser removeByU_AP(
		long userId, long applicationId)
		throws com.liferay.oauth.NoSuchApplicationUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().removeByU_AP(userId, applicationId);
	}

	/**
	* Returns the number of application users where userId = &#63; and applicationId = &#63;.
	*
	* @param userId the user ID
	* @param applicationId the application ID
	* @return the number of matching application users
	* @throws SystemException if a system exception occurred
	*/
	public static int countByU_AP(long userId, long applicationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByU_AP(userId, applicationId);
	}

	/**
	* Caches the application user in the entity cache if it is enabled.
	*
	* @param applicationUser the application user
	*/
	public static void cacheResult(
		com.liferay.oauth.model.ApplicationUser applicationUser) {
		getPersistence().cacheResult(applicationUser);
	}

	/**
	* Caches the application users in the entity cache if it is enabled.
	*
	* @param applicationUsers the application users
	*/
	public static void cacheResult(
		java.util.List<com.liferay.oauth.model.ApplicationUser> applicationUsers) {
		getPersistence().cacheResult(applicationUsers);
	}

	/**
	* Creates a new application user with the primary key. Does not add the application user to the database.
	*
	* @param oaauId the primary key for the new application user
	* @return the new application user
	*/
	public static com.liferay.oauth.model.ApplicationUser create(long oaauId) {
		return getPersistence().create(oaauId);
	}

	/**
	* Removes the application user with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param oaauId the primary key of the application user
	* @return the application user that was removed
	* @throws com.liferay.oauth.NoSuchApplicationUserException if a application user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.oauth.model.ApplicationUser remove(long oaauId)
		throws com.liferay.oauth.NoSuchApplicationUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(oaauId);
	}

	public static com.liferay.oauth.model.ApplicationUser updateImpl(
		com.liferay.oauth.model.ApplicationUser applicationUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(applicationUser);
	}

	/**
	* Returns the application user with the primary key or throws a {@link com.liferay.oauth.NoSuchApplicationUserException} if it could not be found.
	*
	* @param oaauId the primary key of the application user
	* @return the application user
	* @throws com.liferay.oauth.NoSuchApplicationUserException if a application user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.oauth.model.ApplicationUser findByPrimaryKey(
		long oaauId)
		throws com.liferay.oauth.NoSuchApplicationUserException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(oaauId);
	}

	/**
	* Returns the application user with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param oaauId the primary key of the application user
	* @return the application user, or <code>null</code> if a application user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.oauth.model.ApplicationUser fetchByPrimaryKey(
		long oaauId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(oaauId);
	}

	/**
	* Returns all the application users.
	*
	* @return the application users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.oauth.model.ApplicationUser> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the application users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth.model.impl.ApplicationUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of application users
	* @param end the upper bound of the range of application users (not inclusive)
	* @return the range of application users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.oauth.model.ApplicationUser> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the application users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth.model.impl.ApplicationUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of application users
	* @param end the upper bound of the range of application users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of application users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.oauth.model.ApplicationUser> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the application users from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of application users.
	*
	* @return the number of application users
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ApplicationUserPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ApplicationUserPersistence)PortletBeanLocatorUtil.locate(com.liferay.oauth.service.ClpSerializer.getServletContextName(),
					ApplicationUserPersistence.class.getName());

			ReferenceRegistry.registerReference(ApplicationUserUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated
	 */
	public void setPersistence(ApplicationUserPersistence persistence) {
	}

	private static ApplicationUserPersistence _persistence;
}
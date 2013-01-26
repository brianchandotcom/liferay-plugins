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

package com.liferay.so.service.persistence;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.so.model.SocialActivityGrouping;

import java.util.List;

/**
 * The persistence utility for the social activity grouping service. This utility wraps {@link SocialActivityGroupingPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityGroupingPersistence
 * @see SocialActivityGroupingPersistenceImpl
 * @generated
 */
public class SocialActivityGroupingUtil {
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
	public static void clearCache(SocialActivityGrouping socialActivityGrouping) {
		getPersistence().clearCache(socialActivityGrouping);
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
	public static List<SocialActivityGrouping> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SocialActivityGrouping> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SocialActivityGrouping> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static SocialActivityGrouping update(
		SocialActivityGrouping socialActivityGrouping)
		throws SystemException {
		return getPersistence().update(socialActivityGrouping);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static SocialActivityGrouping update(
		SocialActivityGrouping socialActivityGrouping,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(socialActivityGrouping, serviceContext);
	}

	/**
	* Returns all the social activity groupings where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.so.model.SocialActivityGrouping> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the social activity groupings where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.so.model.impl.SocialActivityGroupingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activity groupings
	* @param end the upper bound of the range of social activity groupings (not inclusive)
	* @return the range of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.so.model.SocialActivityGrouping> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the social activity groupings where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.so.model.impl.SocialActivityGroupingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activity groupings
	* @param end the upper bound of the range of social activity groupings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.so.model.SocialActivityGrouping> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the first social activity grouping in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first social activity grouping in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity grouping, or <code>null</code> if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last social activity grouping in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last social activity grouping in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity grouping, or <code>null</code> if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the social activity groupings before and after the current social activity grouping in the ordered set where groupId = &#63;.
	*
	* @param activityGroupingId the primary key of the current social activity grouping
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a social activity grouping with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping[] findByGroupId_PrevAndNext(
		long activityGroupingId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(activityGroupingId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the social activity groupings where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of social activity groupings where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the social activity groupings where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.so.model.SocialActivityGrouping> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the social activity groupings where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.so.model.impl.SocialActivityGroupingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of social activity groupings
	* @param end the upper bound of the range of social activity groupings (not inclusive)
	* @return the range of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.so.model.SocialActivityGrouping> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the social activity groupings where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.so.model.impl.SocialActivityGroupingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of social activity groupings
	* @param end the upper bound of the range of social activity groupings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.so.model.SocialActivityGrouping> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first social activity grouping in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first social activity grouping in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity grouping, or <code>null</code> if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last social activity grouping in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last social activity grouping in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity grouping, or <code>null</code> if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the social activity groupings before and after the current social activity grouping in the ordered set where companyId = &#63;.
	*
	* @param activityGroupingId the primary key of the current social activity grouping
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a social activity grouping with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping[] findByCompanyId_PrevAndNext(
		long activityGroupingId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(activityGroupingId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the social activity groupings where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of social activity groupings where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the social activity groupings where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.so.model.SocialActivityGrouping> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns a range of all the social activity groupings where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.so.model.impl.SocialActivityGroupingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of social activity groupings
	* @param end the upper bound of the range of social activity groupings (not inclusive)
	* @return the range of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.so.model.SocialActivityGrouping> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the social activity groupings where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.so.model.impl.SocialActivityGroupingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of social activity groupings
	* @param end the upper bound of the range of social activity groupings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.so.model.SocialActivityGrouping> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns the first social activity grouping in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first social activity grouping in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity grouping, or <code>null</code> if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last social activity grouping in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last social activity grouping in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity grouping, or <code>null</code> if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the social activity groupings before and after the current social activity grouping in the ordered set where userId = &#63;.
	*
	* @param activityGroupingId the primary key of the current social activity grouping
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a social activity grouping with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping[] findByUserId_PrevAndNext(
		long activityGroupingId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException {
		return getPersistence()
				   .findByUserId_PrevAndNext(activityGroupingId, userId,
			orderByComparator);
	}

	/**
	* Removes all the social activity groupings where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Returns the number of social activity groupings where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns all the social activity groupings where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.so.model.SocialActivityGrouping> findByClassNameId(
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByClassNameId(classNameId);
	}

	/**
	* Returns a range of all the social activity groupings where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.so.model.impl.SocialActivityGroupingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of social activity groupings
	* @param end the upper bound of the range of social activity groupings (not inclusive)
	* @return the range of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.so.model.SocialActivityGrouping> findByClassNameId(
		long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByClassNameId(classNameId, start, end);
	}

	/**
	* Returns an ordered range of all the social activity groupings where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.so.model.impl.SocialActivityGroupingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of social activity groupings
	* @param end the upper bound of the range of social activity groupings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.so.model.SocialActivityGrouping> findByClassNameId(
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByClassNameId(classNameId, start, end, orderByComparator);
	}

	/**
	* Returns the first social activity grouping in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping findByClassNameId_First(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException {
		return getPersistence()
				   .findByClassNameId_First(classNameId, orderByComparator);
	}

	/**
	* Returns the first social activity grouping in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity grouping, or <code>null</code> if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping fetchByClassNameId_First(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByClassNameId_First(classNameId, orderByComparator);
	}

	/**
	* Returns the last social activity grouping in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping findByClassNameId_Last(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException {
		return getPersistence()
				   .findByClassNameId_Last(classNameId, orderByComparator);
	}

	/**
	* Returns the last social activity grouping in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity grouping, or <code>null</code> if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping fetchByClassNameId_Last(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByClassNameId_Last(classNameId, orderByComparator);
	}

	/**
	* Returns the social activity groupings before and after the current social activity grouping in the ordered set where classNameId = &#63;.
	*
	* @param activityGroupingId the primary key of the current social activity grouping
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a social activity grouping with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping[] findByClassNameId_PrevAndNext(
		long activityGroupingId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException {
		return getPersistence()
				   .findByClassNameId_PrevAndNext(activityGroupingId,
			classNameId, orderByComparator);
	}

	/**
	* Removes all the social activity groupings where classNameId = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByClassNameId(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByClassNameId(classNameId);
	}

	/**
	* Returns the number of social activity groupings where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the number of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static int countByClassNameId(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByClassNameId(classNameId);
	}

	/**
	* Returns all the social activity groupings where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.so.model.SocialActivityGrouping> findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	* Returns a range of all the social activity groupings where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.so.model.impl.SocialActivityGroupingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activity groupings
	* @param end the upper bound of the range of social activity groupings (not inclusive)
	* @return the range of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.so.model.SocialActivityGrouping> findByC_C(
		long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the social activity groupings where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.so.model.impl.SocialActivityGroupingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activity groupings
	* @param end the upper bound of the range of social activity groupings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.so.model.SocialActivityGrouping> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns the first social activity grouping in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping findByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException {
		return getPersistence()
				   .findByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the first social activity grouping in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity grouping, or <code>null</code> if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping fetchByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last social activity grouping in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping findByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException {
		return getPersistence()
				   .findByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last social activity grouping in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity grouping, or <code>null</code> if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping fetchByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the social activity groupings before and after the current social activity grouping in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param activityGroupingId the primary key of the current social activity grouping
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a social activity grouping with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping[] findByC_C_PrevAndNext(
		long activityGroupingId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException {
		return getPersistence()
				   .findByC_C_PrevAndNext(activityGroupingId, classNameId,
			classPK, orderByComparator);
	}

	/**
	* Removes all the social activity groupings where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	* Returns the number of social activity groupings where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	* Returns all the social activity groupings where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.so.model.SocialActivityGrouping> findByG_U_C_C(
		long groupId, long userId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_U_C_C(groupId, userId, classNameId, classPK);
	}

	/**
	* Returns a range of all the social activity groupings where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.so.model.impl.SocialActivityGroupingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activity groupings
	* @param end the upper bound of the range of social activity groupings (not inclusive)
	* @return the range of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.so.model.SocialActivityGrouping> findByG_U_C_C(
		long groupId, long userId, long classNameId, long classPK, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_U_C_C(groupId, userId, classNameId, classPK, start,
			end);
	}

	/**
	* Returns an ordered range of all the social activity groupings where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.so.model.impl.SocialActivityGroupingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activity groupings
	* @param end the upper bound of the range of social activity groupings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.so.model.SocialActivityGrouping> findByG_U_C_C(
		long groupId, long userId, long classNameId, long classPK, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_U_C_C(groupId, userId, classNameId, classPK, start,
			end, orderByComparator);
	}

	/**
	* Returns the first social activity grouping in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping findByG_U_C_C_First(
		long groupId, long userId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException {
		return getPersistence()
				   .findByG_U_C_C_First(groupId, userId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the first social activity grouping in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity grouping, or <code>null</code> if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping fetchByG_U_C_C_First(
		long groupId, long userId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_U_C_C_First(groupId, userId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the last social activity grouping in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping findByG_U_C_C_Last(
		long groupId, long userId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException {
		return getPersistence()
				   .findByG_U_C_C_Last(groupId, userId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the last social activity grouping in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity grouping, or <code>null</code> if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping fetchByG_U_C_C_Last(
		long groupId, long userId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_U_C_C_Last(groupId, userId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the social activity groupings before and after the current social activity grouping in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param activityGroupingId the primary key of the current social activity grouping
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a social activity grouping with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping[] findByG_U_C_C_PrevAndNext(
		long activityGroupingId, long groupId, long userId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException {
		return getPersistence()
				   .findByG_U_C_C_PrevAndNext(activityGroupingId, groupId,
			userId, classNameId, classPK, orderByComparator);
	}

	/**
	* Removes all the social activity groupings where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_U_C_C(long groupId, long userId,
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_U_C_C(groupId, userId, classNameId, classPK);
	}

	/**
	* Returns the number of social activity groupings where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_U_C_C(long groupId, long userId,
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByG_U_C_C(groupId, userId, classNameId, classPK);
	}

	/**
	* Returns all the social activity groupings where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param type the type
	* @return the matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.so.model.SocialActivityGrouping> findByG_U_C_T(
		long groupId, long userId, long classNameId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_U_C_T(groupId, userId, classNameId, type);
	}

	/**
	* Returns a range of all the social activity groupings where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.so.model.impl.SocialActivityGroupingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param type the type
	* @param start the lower bound of the range of social activity groupings
	* @param end the upper bound of the range of social activity groupings (not inclusive)
	* @return the range of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.so.model.SocialActivityGrouping> findByG_U_C_T(
		long groupId, long userId, long classNameId, int type, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_U_C_T(groupId, userId, classNameId, type, start, end);
	}

	/**
	* Returns an ordered range of all the social activity groupings where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.so.model.impl.SocialActivityGroupingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param type the type
	* @param start the lower bound of the range of social activity groupings
	* @param end the upper bound of the range of social activity groupings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.so.model.SocialActivityGrouping> findByG_U_C_T(
		long groupId, long userId, long classNameId, int type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_U_C_T(groupId, userId, classNameId, type, start,
			end, orderByComparator);
	}

	/**
	* Returns the first social activity grouping in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping findByG_U_C_T_First(
		long groupId, long userId, long classNameId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException {
		return getPersistence()
				   .findByG_U_C_T_First(groupId, userId, classNameId, type,
			orderByComparator);
	}

	/**
	* Returns the first social activity grouping in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity grouping, or <code>null</code> if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping fetchByG_U_C_T_First(
		long groupId, long userId, long classNameId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_U_C_T_First(groupId, userId, classNameId, type,
			orderByComparator);
	}

	/**
	* Returns the last social activity grouping in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping findByG_U_C_T_Last(
		long groupId, long userId, long classNameId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException {
		return getPersistence()
				   .findByG_U_C_T_Last(groupId, userId, classNameId, type,
			orderByComparator);
	}

	/**
	* Returns the last social activity grouping in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity grouping, or <code>null</code> if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping fetchByG_U_C_T_Last(
		long groupId, long userId, long classNameId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_U_C_T_Last(groupId, userId, classNameId, type,
			orderByComparator);
	}

	/**
	* Returns the social activity groupings before and after the current social activity grouping in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	*
	* @param activityGroupingId the primary key of the current social activity grouping
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a social activity grouping with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping[] findByG_U_C_T_PrevAndNext(
		long activityGroupingId, long groupId, long userId, long classNameId,
		int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException {
		return getPersistence()
				   .findByG_U_C_T_PrevAndNext(activityGroupingId, groupId,
			userId, classNameId, type, orderByComparator);
	}

	/**
	* Removes all the social activity groupings where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param type the type
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_U_C_T(long groupId, long userId,
		long classNameId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_U_C_T(groupId, userId, classNameId, type);
	}

	/**
	* Returns the number of social activity groupings where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param type the type
	* @return the number of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_U_C_T(long groupId, long userId,
		long classNameId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByG_U_C_T(groupId, userId, classNameId, type);
	}

	/**
	* Returns all the social activity groupings where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @return the matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.so.model.SocialActivityGrouping> findByG_U_C_C_T(
		long groupId, long userId, long classNameId, long classPK, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_U_C_C_T(groupId, userId, classNameId, classPK, type);
	}

	/**
	* Returns a range of all the social activity groupings where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.so.model.impl.SocialActivityGroupingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of social activity groupings
	* @param end the upper bound of the range of social activity groupings (not inclusive)
	* @return the range of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.so.model.SocialActivityGrouping> findByG_U_C_C_T(
		long groupId, long userId, long classNameId, long classPK, int type,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_U_C_C_T(groupId, userId, classNameId, classPK,
			type, start, end);
	}

	/**
	* Returns an ordered range of all the social activity groupings where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.so.model.impl.SocialActivityGroupingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of social activity groupings
	* @param end the upper bound of the range of social activity groupings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.so.model.SocialActivityGrouping> findByG_U_C_C_T(
		long groupId, long userId, long classNameId, long classPK, int type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_U_C_C_T(groupId, userId, classNameId, classPK,
			type, start, end, orderByComparator);
	}

	/**
	* Returns the first social activity grouping in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping findByG_U_C_C_T_First(
		long groupId, long userId, long classNameId, long classPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException {
		return getPersistence()
				   .findByG_U_C_C_T_First(groupId, userId, classNameId,
			classPK, type, orderByComparator);
	}

	/**
	* Returns the first social activity grouping in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity grouping, or <code>null</code> if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping fetchByG_U_C_C_T_First(
		long groupId, long userId, long classNameId, long classPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_U_C_C_T_First(groupId, userId, classNameId,
			classPK, type, orderByComparator);
	}

	/**
	* Returns the last social activity grouping in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping findByG_U_C_C_T_Last(
		long groupId, long userId, long classNameId, long classPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException {
		return getPersistence()
				   .findByG_U_C_C_T_Last(groupId, userId, classNameId, classPK,
			type, orderByComparator);
	}

	/**
	* Returns the last social activity grouping in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity grouping, or <code>null</code> if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping fetchByG_U_C_C_T_Last(
		long groupId, long userId, long classNameId, long classPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_U_C_C_T_Last(groupId, userId, classNameId,
			classPK, type, orderByComparator);
	}

	/**
	* Returns the social activity groupings before and after the current social activity grouping in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param activityGroupingId the primary key of the current social activity grouping
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a social activity grouping with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping[] findByG_U_C_C_T_PrevAndNext(
		long activityGroupingId, long groupId, long userId, long classNameId,
		long classPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException {
		return getPersistence()
				   .findByG_U_C_C_T_PrevAndNext(activityGroupingId, groupId,
			userId, classNameId, classPK, type, orderByComparator);
	}

	/**
	* Removes all the social activity groupings where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_U_C_C_T(long groupId, long userId,
		long classNameId, long classPK, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByG_U_C_C_T(groupId, userId, classNameId, classPK, type);
	}

	/**
	* Returns the number of social activity groupings where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @return the number of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_U_C_C_T(long groupId, long userId,
		long classNameId, long classPK, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByG_U_C_C_T(groupId, userId, classNameId, classPK, type);
	}

	/**
	* Caches the social activity grouping in the entity cache if it is enabled.
	*
	* @param socialActivityGrouping the social activity grouping
	*/
	public static void cacheResult(
		com.liferay.so.model.SocialActivityGrouping socialActivityGrouping) {
		getPersistence().cacheResult(socialActivityGrouping);
	}

	/**
	* Caches the social activity groupings in the entity cache if it is enabled.
	*
	* @param socialActivityGroupings the social activity groupings
	*/
	public static void cacheResult(
		java.util.List<com.liferay.so.model.SocialActivityGrouping> socialActivityGroupings) {
		getPersistence().cacheResult(socialActivityGroupings);
	}

	/**
	* Creates a new social activity grouping with the primary key. Does not add the social activity grouping to the database.
	*
	* @param activityGroupingId the primary key for the new social activity grouping
	* @return the new social activity grouping
	*/
	public static com.liferay.so.model.SocialActivityGrouping create(
		long activityGroupingId) {
		return getPersistence().create(activityGroupingId);
	}

	/**
	* Removes the social activity grouping with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activityGroupingId the primary key of the social activity grouping
	* @return the social activity grouping that was removed
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a social activity grouping with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping remove(
		long activityGroupingId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException {
		return getPersistence().remove(activityGroupingId);
	}

	public static com.liferay.so.model.SocialActivityGrouping updateImpl(
		com.liferay.so.model.SocialActivityGrouping socialActivityGrouping)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(socialActivityGrouping);
	}

	/**
	* Returns the social activity grouping with the primary key or throws a {@link com.liferay.so.NoSuchSocialActivityGroupingException} if it could not be found.
	*
	* @param activityGroupingId the primary key of the social activity grouping
	* @return the social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a social activity grouping with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping findByPrimaryKey(
		long activityGroupingId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException {
		return getPersistence().findByPrimaryKey(activityGroupingId);
	}

	/**
	* Returns the social activity grouping with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param activityGroupingId the primary key of the social activity grouping
	* @return the social activity grouping, or <code>null</code> if a social activity grouping with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.so.model.SocialActivityGrouping fetchByPrimaryKey(
		long activityGroupingId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(activityGroupingId);
	}

	/**
	* Returns all the social activity groupings.
	*
	* @return the social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.so.model.SocialActivityGrouping> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the social activity groupings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.so.model.impl.SocialActivityGroupingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity groupings
	* @param end the upper bound of the range of social activity groupings (not inclusive)
	* @return the range of social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.so.model.SocialActivityGrouping> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the social activity groupings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.so.model.impl.SocialActivityGroupingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity groupings
	* @param end the upper bound of the range of social activity groupings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.so.model.SocialActivityGrouping> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the social activity groupings from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of social activity groupings.
	*
	* @return the number of social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static SocialActivityGroupingPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SocialActivityGroupingPersistence)PortletBeanLocatorUtil.locate(com.liferay.so.service.ClpSerializer.getServletContextName(),
					SocialActivityGroupingPersistence.class.getName());

			ReferenceRegistry.registerReference(SocialActivityGroupingUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated
	 */
	public void setPersistence(SocialActivityGroupingPersistence persistence) {
	}

	private static SocialActivityGroupingPersistence _persistence;
}
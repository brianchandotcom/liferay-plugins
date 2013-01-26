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

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.so.model.SocialActivityGrouping;

/**
 * The persistence interface for the social activity grouping service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityGroupingPersistenceImpl
 * @see SocialActivityGroupingUtil
 * @generated
 */
public interface SocialActivityGroupingPersistence extends BasePersistence<SocialActivityGrouping> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SocialActivityGroupingUtil} to access the social activity grouping persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the social activity groupings where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.so.model.SocialActivityGrouping> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.so.model.SocialActivityGrouping> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.so.model.SocialActivityGrouping> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social activity grouping in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.so.model.SocialActivityGrouping findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException;

	/**
	* Returns the first social activity grouping in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity grouping, or <code>null</code> if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.so.model.SocialActivityGrouping fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last social activity grouping in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.so.model.SocialActivityGrouping findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException;

	/**
	* Returns the last social activity grouping in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity grouping, or <code>null</code> if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.so.model.SocialActivityGrouping fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.so.model.SocialActivityGrouping[] findByGroupId_PrevAndNext(
		long activityGroupingId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException;

	/**
	* Removes all the social activity groupings where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activity groupings where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the social activity groupings where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.so.model.SocialActivityGrouping> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.so.model.SocialActivityGrouping> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.so.model.SocialActivityGrouping> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social activity grouping in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.so.model.SocialActivityGrouping findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException;

	/**
	* Returns the first social activity grouping in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity grouping, or <code>null</code> if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.so.model.SocialActivityGrouping fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last social activity grouping in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.so.model.SocialActivityGrouping findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException;

	/**
	* Returns the last social activity grouping in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity grouping, or <code>null</code> if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.so.model.SocialActivityGrouping fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.so.model.SocialActivityGrouping[] findByCompanyId_PrevAndNext(
		long activityGroupingId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException;

	/**
	* Removes all the social activity groupings where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activity groupings where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the social activity groupings where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.so.model.SocialActivityGrouping> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.so.model.SocialActivityGrouping> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.so.model.SocialActivityGrouping> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social activity grouping in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.so.model.SocialActivityGrouping findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException;

	/**
	* Returns the first social activity grouping in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity grouping, or <code>null</code> if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.so.model.SocialActivityGrouping fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last social activity grouping in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.so.model.SocialActivityGrouping findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException;

	/**
	* Returns the last social activity grouping in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity grouping, or <code>null</code> if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.so.model.SocialActivityGrouping fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.so.model.SocialActivityGrouping[] findByUserId_PrevAndNext(
		long activityGroupingId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException;

	/**
	* Removes all the social activity groupings where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activity groupings where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the social activity groupings where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.so.model.SocialActivityGrouping> findByClassNameId(
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.so.model.SocialActivityGrouping> findByClassNameId(
		long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.so.model.SocialActivityGrouping> findByClassNameId(
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social activity grouping in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.so.model.SocialActivityGrouping findByClassNameId_First(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException;

	/**
	* Returns the first social activity grouping in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity grouping, or <code>null</code> if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.so.model.SocialActivityGrouping fetchByClassNameId_First(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last social activity grouping in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.so.model.SocialActivityGrouping findByClassNameId_Last(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException;

	/**
	* Returns the last social activity grouping in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity grouping, or <code>null</code> if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.so.model.SocialActivityGrouping fetchByClassNameId_Last(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.so.model.SocialActivityGrouping[] findByClassNameId_PrevAndNext(
		long activityGroupingId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException;

	/**
	* Removes all the social activity groupings where classNameId = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByClassNameId(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activity groupings where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the number of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public int countByClassNameId(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the social activity groupings where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.so.model.SocialActivityGrouping> findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.so.model.SocialActivityGrouping> findByC_C(
		long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.so.model.SocialActivityGrouping> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.so.model.SocialActivityGrouping findByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException;

	/**
	* Returns the first social activity grouping in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity grouping, or <code>null</code> if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.so.model.SocialActivityGrouping fetchByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.so.model.SocialActivityGrouping findByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException;

	/**
	* Returns the last social activity grouping in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity grouping, or <code>null</code> if a matching social activity grouping could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.so.model.SocialActivityGrouping fetchByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.so.model.SocialActivityGrouping[] findByC_C_PrevAndNext(
		long activityGroupingId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException;

	/**
	* Removes all the social activity groupings where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activity groupings where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.so.model.SocialActivityGrouping> findByG_U_C_C(
		long groupId, long userId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.so.model.SocialActivityGrouping> findByG_U_C_C(
		long groupId, long userId, long classNameId, long classPK, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.so.model.SocialActivityGrouping> findByG_U_C_C(
		long groupId, long userId, long classNameId, long classPK, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.so.model.SocialActivityGrouping findByG_U_C_C_First(
		long groupId, long userId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException;

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
	public com.liferay.so.model.SocialActivityGrouping fetchByG_U_C_C_First(
		long groupId, long userId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.so.model.SocialActivityGrouping findByG_U_C_C_Last(
		long groupId, long userId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException;

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
	public com.liferay.so.model.SocialActivityGrouping fetchByG_U_C_C_Last(
		long groupId, long userId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.so.model.SocialActivityGrouping[] findByG_U_C_C_PrevAndNext(
		long activityGroupingId, long groupId, long userId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException;

	/**
	* Removes all the social activity groupings where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_U_C_C(long groupId, long userId, long classNameId,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public int countByG_U_C_C(long groupId, long userId, long classNameId,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.so.model.SocialActivityGrouping> findByG_U_C_T(
		long groupId, long userId, long classNameId, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.so.model.SocialActivityGrouping> findByG_U_C_T(
		long groupId, long userId, long classNameId, int type, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.so.model.SocialActivityGrouping> findByG_U_C_T(
		long groupId, long userId, long classNameId, int type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.so.model.SocialActivityGrouping findByG_U_C_T_First(
		long groupId, long userId, long classNameId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException;

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
	public com.liferay.so.model.SocialActivityGrouping fetchByG_U_C_T_First(
		long groupId, long userId, long classNameId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.so.model.SocialActivityGrouping findByG_U_C_T_Last(
		long groupId, long userId, long classNameId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException;

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
	public com.liferay.so.model.SocialActivityGrouping fetchByG_U_C_T_Last(
		long groupId, long userId, long classNameId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.so.model.SocialActivityGrouping[] findByG_U_C_T_PrevAndNext(
		long activityGroupingId, long groupId, long userId, long classNameId,
		int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException;

	/**
	* Removes all the social activity groupings where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param type the type
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_U_C_T(long groupId, long userId, long classNameId,
		int type) throws com.liferay.portal.kernel.exception.SystemException;

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
	public int countByG_U_C_T(long groupId, long userId, long classNameId,
		int type) throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.so.model.SocialActivityGrouping> findByG_U_C_C_T(
		long groupId, long userId, long classNameId, long classPK, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.so.model.SocialActivityGrouping> findByG_U_C_C_T(
		long groupId, long userId, long classNameId, long classPK, int type,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.so.model.SocialActivityGrouping> findByG_U_C_C_T(
		long groupId, long userId, long classNameId, long classPK, int type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.so.model.SocialActivityGrouping findByG_U_C_C_T_First(
		long groupId, long userId, long classNameId, long classPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException;

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
	public com.liferay.so.model.SocialActivityGrouping fetchByG_U_C_C_T_First(
		long groupId, long userId, long classNameId, long classPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.so.model.SocialActivityGrouping findByG_U_C_C_T_Last(
		long groupId, long userId, long classNameId, long classPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException;

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
	public com.liferay.so.model.SocialActivityGrouping fetchByG_U_C_C_T_Last(
		long groupId, long userId, long classNameId, long classPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.so.model.SocialActivityGrouping[] findByG_U_C_C_T_PrevAndNext(
		long activityGroupingId, long groupId, long userId, long classNameId,
		long classPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException;

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
	public void removeByG_U_C_C_T(long groupId, long userId, long classNameId,
		long classPK, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public int countByG_U_C_C_T(long groupId, long userId, long classNameId,
		long classPK, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Caches the social activity grouping in the entity cache if it is enabled.
	*
	* @param socialActivityGrouping the social activity grouping
	*/
	public void cacheResult(
		com.liferay.so.model.SocialActivityGrouping socialActivityGrouping);

	/**
	* Caches the social activity groupings in the entity cache if it is enabled.
	*
	* @param socialActivityGroupings the social activity groupings
	*/
	public void cacheResult(
		java.util.List<com.liferay.so.model.SocialActivityGrouping> socialActivityGroupings);

	/**
	* Creates a new social activity grouping with the primary key. Does not add the social activity grouping to the database.
	*
	* @param activityGroupingId the primary key for the new social activity grouping
	* @return the new social activity grouping
	*/
	public com.liferay.so.model.SocialActivityGrouping create(
		long activityGroupingId);

	/**
	* Removes the social activity grouping with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activityGroupingId the primary key of the social activity grouping
	* @return the social activity grouping that was removed
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a social activity grouping with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.so.model.SocialActivityGrouping remove(
		long activityGroupingId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException;

	public com.liferay.so.model.SocialActivityGrouping updateImpl(
		com.liferay.so.model.SocialActivityGrouping socialActivityGrouping)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the social activity grouping with the primary key or throws a {@link com.liferay.so.NoSuchSocialActivityGroupingException} if it could not be found.
	*
	* @param activityGroupingId the primary key of the social activity grouping
	* @return the social activity grouping
	* @throws com.liferay.so.NoSuchSocialActivityGroupingException if a social activity grouping with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.so.model.SocialActivityGrouping findByPrimaryKey(
		long activityGroupingId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.so.NoSuchSocialActivityGroupingException;

	/**
	* Returns the social activity grouping with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param activityGroupingId the primary key of the social activity grouping
	* @return the social activity grouping, or <code>null</code> if a social activity grouping with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.so.model.SocialActivityGrouping fetchByPrimaryKey(
		long activityGroupingId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the social activity groupings.
	*
	* @return the social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.so.model.SocialActivityGrouping> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.so.model.SocialActivityGrouping> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.so.model.SocialActivityGrouping> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social activity groupings from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activity groupings.
	*
	* @return the number of social activity groupings
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}
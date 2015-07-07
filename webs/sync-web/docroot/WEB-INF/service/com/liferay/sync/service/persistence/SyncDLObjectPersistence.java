/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.sync.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.sync.model.SyncDLObject;

/**
 * The persistence interface for the sync d l object service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.sync.service.persistence.impl.SyncDLObjectPersistenceImpl
 * @see SyncDLObjectUtil
 * @generated
 */
@ProviderType
public interface SyncDLObjectPersistence extends BasePersistence<SyncDLObject> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SyncDLObjectUtil} to access the sync d l object persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the sync d l objects where parentFolderId = &#63;.
	*
	* @param parentFolderId the parent folder ID
	* @return the matching sync d l objects
	*/
	public java.util.List<SyncDLObject> findByParentFolderId(
		long parentFolderId);

	/**
	* Returns a range of all the sync d l objects where parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SyncDLObjectModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of sync d l objects
	* @param end the upper bound of the range of sync d l objects (not inclusive)
	* @return the range of matching sync d l objects
	*/
	public java.util.List<SyncDLObject> findByParentFolderId(
		long parentFolderId, int start, int end);

	/**
	* Returns an ordered range of all the sync d l objects where parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SyncDLObjectModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of sync d l objects
	* @param end the upper bound of the range of sync d l objects (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching sync d l objects
	*/
	public java.util.List<SyncDLObject> findByParentFolderId(
		long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator);

	/**
	* Returns the first sync d l object in the ordered set where parentFolderId = &#63;.
	*
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sync d l object
	* @throws NoSuchDLObjectException if a matching sync d l object could not be found
	*/
	public SyncDLObject findByParentFolderId_First(long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.NoSuchDLObjectException;

	/**
	* Returns the first sync d l object in the ordered set where parentFolderId = &#63;.
	*
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sync d l object, or <code>null</code> if a matching sync d l object could not be found
	*/
	public SyncDLObject fetchByParentFolderId_First(long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator);

	/**
	* Returns the last sync d l object in the ordered set where parentFolderId = &#63;.
	*
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sync d l object
	* @throws NoSuchDLObjectException if a matching sync d l object could not be found
	*/
	public SyncDLObject findByParentFolderId_Last(long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.NoSuchDLObjectException;

	/**
	* Returns the last sync d l object in the ordered set where parentFolderId = &#63;.
	*
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sync d l object, or <code>null</code> if a matching sync d l object could not be found
	*/
	public SyncDLObject fetchByParentFolderId_Last(long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator);

	/**
	* Returns the sync d l objects before and after the current sync d l object in the ordered set where parentFolderId = &#63;.
	*
	* @param syncDLObjectId the primary key of the current sync d l object
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next sync d l object
	* @throws NoSuchDLObjectException if a sync d l object with the primary key could not be found
	*/
	public SyncDLObject[] findByParentFolderId_PrevAndNext(
		long syncDLObjectId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.NoSuchDLObjectException;

	/**
	* Removes all the sync d l objects where parentFolderId = &#63; from the database.
	*
	* @param parentFolderId the parent folder ID
	*/
	public void removeByParentFolderId(long parentFolderId);

	/**
	* Returns the number of sync d l objects where parentFolderId = &#63;.
	*
	* @param parentFolderId the parent folder ID
	* @return the number of matching sync d l objects
	*/
	public int countByParentFolderId(long parentFolderId);

	/**
	* Returns all the sync d l objects where version = &#63; and type = &#63;.
	*
	* @param version the version
	* @param type the type
	* @return the matching sync d l objects
	*/
	public java.util.List<SyncDLObject> findByV_T(java.lang.String version,
		java.lang.String type);

	/**
	* Returns a range of all the sync d l objects where version = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SyncDLObjectModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param version the version
	* @param type the type
	* @param start the lower bound of the range of sync d l objects
	* @param end the upper bound of the range of sync d l objects (not inclusive)
	* @return the range of matching sync d l objects
	*/
	public java.util.List<SyncDLObject> findByV_T(java.lang.String version,
		java.lang.String type, int start, int end);

	/**
	* Returns an ordered range of all the sync d l objects where version = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SyncDLObjectModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param version the version
	* @param type the type
	* @param start the lower bound of the range of sync d l objects
	* @param end the upper bound of the range of sync d l objects (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching sync d l objects
	*/
	public java.util.List<SyncDLObject> findByV_T(java.lang.String version,
		java.lang.String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator);

	/**
	* Returns the first sync d l object in the ordered set where version = &#63; and type = &#63;.
	*
	* @param version the version
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sync d l object
	* @throws NoSuchDLObjectException if a matching sync d l object could not be found
	*/
	public SyncDLObject findByV_T_First(java.lang.String version,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.NoSuchDLObjectException;

	/**
	* Returns the first sync d l object in the ordered set where version = &#63; and type = &#63;.
	*
	* @param version the version
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sync d l object, or <code>null</code> if a matching sync d l object could not be found
	*/
	public SyncDLObject fetchByV_T_First(java.lang.String version,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator);

	/**
	* Returns the last sync d l object in the ordered set where version = &#63; and type = &#63;.
	*
	* @param version the version
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sync d l object
	* @throws NoSuchDLObjectException if a matching sync d l object could not be found
	*/
	public SyncDLObject findByV_T_Last(java.lang.String version,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.NoSuchDLObjectException;

	/**
	* Returns the last sync d l object in the ordered set where version = &#63; and type = &#63;.
	*
	* @param version the version
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sync d l object, or <code>null</code> if a matching sync d l object could not be found
	*/
	public SyncDLObject fetchByV_T_Last(java.lang.String version,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator);

	/**
	* Returns the sync d l objects before and after the current sync d l object in the ordered set where version = &#63; and type = &#63;.
	*
	* @param syncDLObjectId the primary key of the current sync d l object
	* @param version the version
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next sync d l object
	* @throws NoSuchDLObjectException if a sync d l object with the primary key could not be found
	*/
	public SyncDLObject[] findByV_T_PrevAndNext(long syncDLObjectId,
		java.lang.String version, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.NoSuchDLObjectException;

	/**
	* Removes all the sync d l objects where version = &#63; and type = &#63; from the database.
	*
	* @param version the version
	* @param type the type
	*/
	public void removeByV_T(java.lang.String version, java.lang.String type);

	/**
	* Returns the number of sync d l objects where version = &#63; and type = &#63;.
	*
	* @param version the version
	* @param type the type
	* @return the number of matching sync d l objects
	*/
	public int countByV_T(java.lang.String version, java.lang.String type);

	/**
	* Returns the sync d l object where type = &#63; and typePK = &#63; or throws a {@link NoSuchDLObjectException} if it could not be found.
	*
	* @param type the type
	* @param typePK the type p k
	* @return the matching sync d l object
	* @throws NoSuchDLObjectException if a matching sync d l object could not be found
	*/
	public SyncDLObject findByT_T(java.lang.String type, long typePK)
		throws com.liferay.sync.NoSuchDLObjectException;

	/**
	* Returns the sync d l object where type = &#63; and typePK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param type the type
	* @param typePK the type p k
	* @return the matching sync d l object, or <code>null</code> if a matching sync d l object could not be found
	*/
	public SyncDLObject fetchByT_T(java.lang.String type, long typePK);

	/**
	* Returns the sync d l object where type = &#63; and typePK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param type the type
	* @param typePK the type p k
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching sync d l object, or <code>null</code> if a matching sync d l object could not be found
	*/
	public SyncDLObject fetchByT_T(java.lang.String type, long typePK,
		boolean retrieveFromCache);

	/**
	* Removes the sync d l object where type = &#63; and typePK = &#63; from the database.
	*
	* @param type the type
	* @param typePK the type p k
	* @return the sync d l object that was removed
	*/
	public SyncDLObject removeByT_T(java.lang.String type, long typePK)
		throws com.liferay.sync.NoSuchDLObjectException;

	/**
	* Returns the number of sync d l objects where type = &#63; and typePK = &#63;.
	*
	* @param type the type
	* @param typePK the type p k
	* @return the number of matching sync d l objects
	*/
	public int countByT_T(java.lang.String type, long typePK);

	/**
	* Returns all the sync d l objects where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @return the matching sync d l objects
	*/
	public java.util.List<SyncDLObject> findByC_M_R(long companyId,
		long modifiedTime, long repositoryId);

	/**
	* Returns a range of all the sync d l objects where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SyncDLObjectModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @param start the lower bound of the range of sync d l objects
	* @param end the upper bound of the range of sync d l objects (not inclusive)
	* @return the range of matching sync d l objects
	*/
	public java.util.List<SyncDLObject> findByC_M_R(long companyId,
		long modifiedTime, long repositoryId, int start, int end);

	/**
	* Returns an ordered range of all the sync d l objects where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SyncDLObjectModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @param start the lower bound of the range of sync d l objects
	* @param end the upper bound of the range of sync d l objects (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching sync d l objects
	*/
	public java.util.List<SyncDLObject> findByC_M_R(long companyId,
		long modifiedTime, long repositoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator);

	/**
	* Returns the first sync d l object in the ordered set where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sync d l object
	* @throws NoSuchDLObjectException if a matching sync d l object could not be found
	*/
	public SyncDLObject findByC_M_R_First(long companyId, long modifiedTime,
		long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.NoSuchDLObjectException;

	/**
	* Returns the first sync d l object in the ordered set where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sync d l object, or <code>null</code> if a matching sync d l object could not be found
	*/
	public SyncDLObject fetchByC_M_R_First(long companyId, long modifiedTime,
		long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator);

	/**
	* Returns the last sync d l object in the ordered set where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sync d l object
	* @throws NoSuchDLObjectException if a matching sync d l object could not be found
	*/
	public SyncDLObject findByC_M_R_Last(long companyId, long modifiedTime,
		long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.NoSuchDLObjectException;

	/**
	* Returns the last sync d l object in the ordered set where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sync d l object, or <code>null</code> if a matching sync d l object could not be found
	*/
	public SyncDLObject fetchByC_M_R_Last(long companyId, long modifiedTime,
		long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator);

	/**
	* Returns the sync d l objects before and after the current sync d l object in the ordered set where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63;.
	*
	* @param syncDLObjectId the primary key of the current sync d l object
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next sync d l object
	* @throws NoSuchDLObjectException if a sync d l object with the primary key could not be found
	*/
	public SyncDLObject[] findByC_M_R_PrevAndNext(long syncDLObjectId,
		long companyId, long modifiedTime, long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.NoSuchDLObjectException;

	/**
	* Removes all the sync d l objects where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	*/
	public void removeByC_M_R(long companyId, long modifiedTime,
		long repositoryId);

	/**
	* Returns the number of sync d l objects where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @return the number of matching sync d l objects
	*/
	public int countByC_M_R(long companyId, long modifiedTime, long repositoryId);

	/**
	* Returns all the sync d l objects where companyId = &#63; and repositoryId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param type the type
	* @return the matching sync d l objects
	*/
	public java.util.List<SyncDLObject> findByC_R_T(long companyId,
		long repositoryId, java.lang.String type);

	/**
	* Returns a range of all the sync d l objects where companyId = &#63; and repositoryId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SyncDLObjectModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param type the type
	* @param start the lower bound of the range of sync d l objects
	* @param end the upper bound of the range of sync d l objects (not inclusive)
	* @return the range of matching sync d l objects
	*/
	public java.util.List<SyncDLObject> findByC_R_T(long companyId,
		long repositoryId, java.lang.String type, int start, int end);

	/**
	* Returns an ordered range of all the sync d l objects where companyId = &#63; and repositoryId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SyncDLObjectModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param type the type
	* @param start the lower bound of the range of sync d l objects
	* @param end the upper bound of the range of sync d l objects (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching sync d l objects
	*/
	public java.util.List<SyncDLObject> findByC_R_T(long companyId,
		long repositoryId, java.lang.String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator);

	/**
	* Returns the first sync d l object in the ordered set where companyId = &#63; and repositoryId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sync d l object
	* @throws NoSuchDLObjectException if a matching sync d l object could not be found
	*/
	public SyncDLObject findByC_R_T_First(long companyId, long repositoryId,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.NoSuchDLObjectException;

	/**
	* Returns the first sync d l object in the ordered set where companyId = &#63; and repositoryId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sync d l object, or <code>null</code> if a matching sync d l object could not be found
	*/
	public SyncDLObject fetchByC_R_T_First(long companyId, long repositoryId,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator);

	/**
	* Returns the last sync d l object in the ordered set where companyId = &#63; and repositoryId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sync d l object
	* @throws NoSuchDLObjectException if a matching sync d l object could not be found
	*/
	public SyncDLObject findByC_R_T_Last(long companyId, long repositoryId,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.NoSuchDLObjectException;

	/**
	* Returns the last sync d l object in the ordered set where companyId = &#63; and repositoryId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sync d l object, or <code>null</code> if a matching sync d l object could not be found
	*/
	public SyncDLObject fetchByC_R_T_Last(long companyId, long repositoryId,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator);

	/**
	* Returns the sync d l objects before and after the current sync d l object in the ordered set where companyId = &#63; and repositoryId = &#63; and type = &#63;.
	*
	* @param syncDLObjectId the primary key of the current sync d l object
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next sync d l object
	* @throws NoSuchDLObjectException if a sync d l object with the primary key could not be found
	*/
	public SyncDLObject[] findByC_R_T_PrevAndNext(long syncDLObjectId,
		long companyId, long repositoryId, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.NoSuchDLObjectException;

	/**
	* Removes all the sync d l objects where companyId = &#63; and repositoryId = &#63; and type = &#63; from the database.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param type the type
	*/
	public void removeByC_R_T(long companyId, long repositoryId,
		java.lang.String type);

	/**
	* Returns the number of sync d l objects where companyId = &#63; and repositoryId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param type the type
	* @return the number of matching sync d l objects
	*/
	public int countByC_R_T(long companyId, long repositoryId,
		java.lang.String type);

	/**
	* Returns all the sync d l objects where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63; and parentFolderId = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @param parentFolderId the parent folder ID
	* @return the matching sync d l objects
	*/
	public java.util.List<SyncDLObject> findByC_M_R_P(long companyId,
		long modifiedTime, long repositoryId, long parentFolderId);

	/**
	* Returns a range of all the sync d l objects where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SyncDLObjectModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of sync d l objects
	* @param end the upper bound of the range of sync d l objects (not inclusive)
	* @return the range of matching sync d l objects
	*/
	public java.util.List<SyncDLObject> findByC_M_R_P(long companyId,
		long modifiedTime, long repositoryId, long parentFolderId, int start,
		int end);

	/**
	* Returns an ordered range of all the sync d l objects where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SyncDLObjectModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of sync d l objects
	* @param end the upper bound of the range of sync d l objects (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching sync d l objects
	*/
	public java.util.List<SyncDLObject> findByC_M_R_P(long companyId,
		long modifiedTime, long repositoryId, long parentFolderId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator);

	/**
	* Returns the first sync d l object in the ordered set where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63; and parentFolderId = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sync d l object
	* @throws NoSuchDLObjectException if a matching sync d l object could not be found
	*/
	public SyncDLObject findByC_M_R_P_First(long companyId, long modifiedTime,
		long repositoryId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.NoSuchDLObjectException;

	/**
	* Returns the first sync d l object in the ordered set where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63; and parentFolderId = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sync d l object, or <code>null</code> if a matching sync d l object could not be found
	*/
	public SyncDLObject fetchByC_M_R_P_First(long companyId, long modifiedTime,
		long repositoryId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator);

	/**
	* Returns the last sync d l object in the ordered set where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63; and parentFolderId = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sync d l object
	* @throws NoSuchDLObjectException if a matching sync d l object could not be found
	*/
	public SyncDLObject findByC_M_R_P_Last(long companyId, long modifiedTime,
		long repositoryId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.NoSuchDLObjectException;

	/**
	* Returns the last sync d l object in the ordered set where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63; and parentFolderId = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sync d l object, or <code>null</code> if a matching sync d l object could not be found
	*/
	public SyncDLObject fetchByC_M_R_P_Last(long companyId, long modifiedTime,
		long repositoryId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator);

	/**
	* Returns the sync d l objects before and after the current sync d l object in the ordered set where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63; and parentFolderId = &#63;.
	*
	* @param syncDLObjectId the primary key of the current sync d l object
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next sync d l object
	* @throws NoSuchDLObjectException if a sync d l object with the primary key could not be found
	*/
	public SyncDLObject[] findByC_M_R_P_PrevAndNext(long syncDLObjectId,
		long companyId, long modifiedTime, long repositoryId,
		long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.NoSuchDLObjectException;

	/**
	* Removes all the sync d l objects where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63; and parentFolderId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @param parentFolderId the parent folder ID
	*/
	public void removeByC_M_R_P(long companyId, long modifiedTime,
		long repositoryId, long parentFolderId);

	/**
	* Returns the number of sync d l objects where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63; and parentFolderId = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @param parentFolderId the parent folder ID
	* @return the number of matching sync d l objects
	*/
	public int countByC_M_R_P(long companyId, long modifiedTime,
		long repositoryId, long parentFolderId);

	/**
	* Returns all the sync d l objects where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	*
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @param event the event
	* @return the matching sync d l objects
	*/
	public java.util.List<SyncDLObject> findByC_M_R_NotE(long companyId,
		long modifiedTime, long repositoryId, java.lang.String event);

	/**
	* Returns a range of all the sync d l objects where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SyncDLObjectModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @param event the event
	* @param start the lower bound of the range of sync d l objects
	* @param end the upper bound of the range of sync d l objects (not inclusive)
	* @return the range of matching sync d l objects
	*/
	public java.util.List<SyncDLObject> findByC_M_R_NotE(long companyId,
		long modifiedTime, long repositoryId, java.lang.String event,
		int start, int end);

	/**
	* Returns an ordered range of all the sync d l objects where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SyncDLObjectModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @param event the event
	* @param start the lower bound of the range of sync d l objects
	* @param end the upper bound of the range of sync d l objects (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching sync d l objects
	*/
	public java.util.List<SyncDLObject> findByC_M_R_NotE(long companyId,
		long modifiedTime, long repositoryId, java.lang.String event,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator);

	/**
	* Returns the first sync d l object in the ordered set where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	*
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @param event the event
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sync d l object
	* @throws NoSuchDLObjectException if a matching sync d l object could not be found
	*/
	public SyncDLObject findByC_M_R_NotE_First(long companyId,
		long modifiedTime, long repositoryId, java.lang.String event,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.NoSuchDLObjectException;

	/**
	* Returns the first sync d l object in the ordered set where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	*
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @param event the event
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sync d l object, or <code>null</code> if a matching sync d l object could not be found
	*/
	public SyncDLObject fetchByC_M_R_NotE_First(long companyId,
		long modifiedTime, long repositoryId, java.lang.String event,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator);

	/**
	* Returns the last sync d l object in the ordered set where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	*
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @param event the event
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sync d l object
	* @throws NoSuchDLObjectException if a matching sync d l object could not be found
	*/
	public SyncDLObject findByC_M_R_NotE_Last(long companyId,
		long modifiedTime, long repositoryId, java.lang.String event,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.NoSuchDLObjectException;

	/**
	* Returns the last sync d l object in the ordered set where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	*
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @param event the event
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sync d l object, or <code>null</code> if a matching sync d l object could not be found
	*/
	public SyncDLObject fetchByC_M_R_NotE_Last(long companyId,
		long modifiedTime, long repositoryId, java.lang.String event,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator);

	/**
	* Returns the sync d l objects before and after the current sync d l object in the ordered set where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	*
	* @param syncDLObjectId the primary key of the current sync d l object
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @param event the event
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next sync d l object
	* @throws NoSuchDLObjectException if a sync d l object with the primary key could not be found
	*/
	public SyncDLObject[] findByC_M_R_NotE_PrevAndNext(long syncDLObjectId,
		long companyId, long modifiedTime, long repositoryId,
		java.lang.String event,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.NoSuchDLObjectException;

	/**
	* Removes all the sync d l objects where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63; from the database.
	*
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @param event the event
	*/
	public void removeByC_M_R_NotE(long companyId, long modifiedTime,
		long repositoryId, java.lang.String event);

	/**
	* Returns the number of sync d l objects where companyId = &#63; and modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	*
	* @param companyId the company ID
	* @param modifiedTime the modified time
	* @param repositoryId the repository ID
	* @param event the event
	* @return the number of matching sync d l objects
	*/
	public int countByC_M_R_NotE(long companyId, long modifiedTime,
		long repositoryId, java.lang.String event);

	/**
	* Caches the sync d l object in the entity cache if it is enabled.
	*
	* @param syncDLObject the sync d l object
	*/
	public void cacheResult(SyncDLObject syncDLObject);

	/**
	* Caches the sync d l objects in the entity cache if it is enabled.
	*
	* @param syncDLObjects the sync d l objects
	*/
	public void cacheResult(java.util.List<SyncDLObject> syncDLObjects);

	/**
	* Creates a new sync d l object with the primary key. Does not add the sync d l object to the database.
	*
	* @param syncDLObjectId the primary key for the new sync d l object
	* @return the new sync d l object
	*/
	public SyncDLObject create(long syncDLObjectId);

	/**
	* Removes the sync d l object with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param syncDLObjectId the primary key of the sync d l object
	* @return the sync d l object that was removed
	* @throws NoSuchDLObjectException if a sync d l object with the primary key could not be found
	*/
	public SyncDLObject remove(long syncDLObjectId)
		throws com.liferay.sync.NoSuchDLObjectException;

	public SyncDLObject updateImpl(SyncDLObject syncDLObject);

	/**
	* Returns the sync d l object with the primary key or throws a {@link NoSuchDLObjectException} if it could not be found.
	*
	* @param syncDLObjectId the primary key of the sync d l object
	* @return the sync d l object
	* @throws NoSuchDLObjectException if a sync d l object with the primary key could not be found
	*/
	public SyncDLObject findByPrimaryKey(long syncDLObjectId)
		throws com.liferay.sync.NoSuchDLObjectException;

	/**
	* Returns the sync d l object with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param syncDLObjectId the primary key of the sync d l object
	* @return the sync d l object, or <code>null</code> if a sync d l object with the primary key could not be found
	*/
	public SyncDLObject fetchByPrimaryKey(long syncDLObjectId);

	@Override
	public java.util.Map<java.io.Serializable, SyncDLObject> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the sync d l objects.
	*
	* @return the sync d l objects
	*/
	public java.util.List<SyncDLObject> findAll();

	/**
	* Returns a range of all the sync d l objects.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SyncDLObjectModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of sync d l objects
	* @param end the upper bound of the range of sync d l objects (not inclusive)
	* @return the range of sync d l objects
	*/
	public java.util.List<SyncDLObject> findAll(int start, int end);

	/**
	* Returns an ordered range of all the sync d l objects.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SyncDLObjectModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of sync d l objects
	* @param end the upper bound of the range of sync d l objects (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of sync d l objects
	*/
	public java.util.List<SyncDLObject> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject> orderByComparator);

	/**
	* Removes all the sync d l objects from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of sync d l objects.
	*
	* @return the number of sync d l objects
	*/
	public int countAll();
}
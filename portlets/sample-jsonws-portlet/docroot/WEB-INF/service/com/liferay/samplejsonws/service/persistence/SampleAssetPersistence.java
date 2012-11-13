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

package com.liferay.samplejsonws.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.samplejsonws.model.SampleAsset;

/**
 * The persistence interface for the sample asset service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SampleAssetPersistenceImpl
 * @see SampleAssetUtil
 * @generated
 */
public interface SampleAssetPersistence extends BasePersistence<SampleAsset> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SampleAssetUtil} to access the sample asset persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the sample asset in the entity cache if it is enabled.
	*
	* @param sampleAsset the sample asset
	*/
	public void cacheResult(
		com.liferay.samplejsonws.model.SampleAsset sampleAsset);

	/**
	* Caches the sample assets in the entity cache if it is enabled.
	*
	* @param sampleAssets the sample assets
	*/
	public void cacheResult(
		java.util.List<com.liferay.samplejsonws.model.SampleAsset> sampleAssets);

	/**
	* Creates a new sample asset with the primary key. Does not add the sample asset to the database.
	*
	* @param assetId the primary key for the new sample asset
	* @return the new sample asset
	*/
	public com.liferay.samplejsonws.model.SampleAsset create(long assetId);

	/**
	* Removes the sample asset with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param assetId the primary key of the sample asset
	* @return the sample asset that was removed
	* @throws com.liferay.samplejsonws.NoSuchSampleAssetException if a sample asset with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.samplejsonws.model.SampleAsset remove(long assetId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.samplejsonws.NoSuchSampleAssetException;

	public com.liferay.samplejsonws.model.SampleAsset updateImpl(
		com.liferay.samplejsonws.model.SampleAsset sampleAsset, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the sample asset with the primary key or throws a {@link com.liferay.samplejsonws.NoSuchSampleAssetException} if it could not be found.
	*
	* @param assetId the primary key of the sample asset
	* @return the sample asset
	* @throws com.liferay.samplejsonws.NoSuchSampleAssetException if a sample asset with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.samplejsonws.model.SampleAsset findByPrimaryKey(
		long assetId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.samplejsonws.NoSuchSampleAssetException;

	/**
	* Returns the sample asset with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param assetId the primary key of the sample asset
	* @return the sample asset, or <code>null</code> if a sample asset with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.samplejsonws.model.SampleAsset fetchByPrimaryKey(
		long assetId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the sample assets.
	*
	* @return the sample assets
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.samplejsonws.model.SampleAsset> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the sample assets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of sample assets
	* @param end the upper bound of the range of sample assets (not inclusive)
	* @return the range of sample assets
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.samplejsonws.model.SampleAsset> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the sample assets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of sample assets
	* @param end the upper bound of the range of sample assets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of sample assets
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.samplejsonws.model.SampleAsset> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the sample assets from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of sample assets.
	*
	* @return the number of sample assets
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}
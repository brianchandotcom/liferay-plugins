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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.samplejsonws.model.SampleAsset;

import java.util.List;

/**
 * The persistence utility for the sample asset service. This utility wraps {@link SampleAssetPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SampleAssetPersistence
 * @see SampleAssetPersistenceImpl
 * @generated
 */
public class SampleAssetUtil {
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
	public static void clearCache(SampleAsset sampleAsset) {
		getPersistence().clearCache(sampleAsset);
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
	public static List<SampleAsset> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SampleAsset> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SampleAsset> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static SampleAsset update(SampleAsset sampleAsset, boolean merge)
		throws SystemException {
		return getPersistence().update(sampleAsset, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static SampleAsset update(SampleAsset sampleAsset, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(sampleAsset, merge, serviceContext);
	}

	/**
	* Caches the sample asset in the entity cache if it is enabled.
	*
	* @param sampleAsset the sample asset
	*/
	public static void cacheResult(
		com.liferay.samplejsonws.model.SampleAsset sampleAsset) {
		getPersistence().cacheResult(sampleAsset);
	}

	/**
	* Caches the sample assets in the entity cache if it is enabled.
	*
	* @param sampleAssets the sample assets
	*/
	public static void cacheResult(
		java.util.List<com.liferay.samplejsonws.model.SampleAsset> sampleAssets) {
		getPersistence().cacheResult(sampleAssets);
	}

	/**
	* Creates a new sample asset with the primary key. Does not add the sample asset to the database.
	*
	* @param assetId the primary key for the new sample asset
	* @return the new sample asset
	*/
	public static com.liferay.samplejsonws.model.SampleAsset create(
		long assetId) {
		return getPersistence().create(assetId);
	}

	/**
	* Removes the sample asset with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param assetId the primary key of the sample asset
	* @return the sample asset that was removed
	* @throws com.liferay.samplejsonws.NoSuchSampleAssetException if a sample asset with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.samplejsonws.model.SampleAsset remove(
		long assetId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.samplejsonws.NoSuchSampleAssetException {
		return getPersistence().remove(assetId);
	}

	public static com.liferay.samplejsonws.model.SampleAsset updateImpl(
		com.liferay.samplejsonws.model.SampleAsset sampleAsset, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(sampleAsset, merge);
	}

	/**
	* Returns the sample asset with the primary key or throws a {@link com.liferay.samplejsonws.NoSuchSampleAssetException} if it could not be found.
	*
	* @param assetId the primary key of the sample asset
	* @return the sample asset
	* @throws com.liferay.samplejsonws.NoSuchSampleAssetException if a sample asset with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.samplejsonws.model.SampleAsset findByPrimaryKey(
		long assetId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.samplejsonws.NoSuchSampleAssetException {
		return getPersistence().findByPrimaryKey(assetId);
	}

	/**
	* Returns the sample asset with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param assetId the primary key of the sample asset
	* @return the sample asset, or <code>null</code> if a sample asset with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.samplejsonws.model.SampleAsset fetchByPrimaryKey(
		long assetId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(assetId);
	}

	/**
	* Returns all the sample assets.
	*
	* @return the sample assets
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.samplejsonws.model.SampleAsset> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

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
	public static java.util.List<com.liferay.samplejsonws.model.SampleAsset> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

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
	public static java.util.List<com.liferay.samplejsonws.model.SampleAsset> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the sample assets from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of sample assets.
	*
	* @return the number of sample assets
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static SampleAssetPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SampleAssetPersistence)PortletBeanLocatorUtil.locate(com.liferay.samplejsonws.service.ClpSerializer.getServletContextName(),
					SampleAssetPersistence.class.getName());

			ReferenceRegistry.registerReference(SampleAssetUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(SampleAssetPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(SampleAssetUtil.class,
			"_persistence");
	}

	private static SampleAssetPersistence _persistence;
}
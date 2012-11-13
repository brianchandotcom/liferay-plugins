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

package com.liferay.samplejsonws.model;

import com.liferay.portal.model.ModelWrapper;

/**
 * <p>
 * This class is a wrapper for {@link SampleAsset}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SampleAsset
 * @generated
 */
public class SampleAssetWrapper implements SampleAsset,
	ModelWrapper<SampleAsset> {
	public SampleAssetWrapper(SampleAsset sampleAsset) {
		_sampleAsset = sampleAsset;
	}

	public Class<?> getModelClass() {
		return SampleAsset.class;
	}

	public String getModelClassName() {
		return SampleAsset.class.getName();
	}

	/**
	* Returns the primary key of this sample asset.
	*
	* @return the primary key of this sample asset
	*/
	public long getPrimaryKey() {
		return _sampleAsset.getPrimaryKey();
	}

	/**
	* Sets the primary key of this sample asset.
	*
	* @param primaryKey the primary key of this sample asset
	*/
	public void setPrimaryKey(long primaryKey) {
		_sampleAsset.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the asset ID of this sample asset.
	*
	* @return the asset ID of this sample asset
	*/
	public long getAssetId() {
		return _sampleAsset.getAssetId();
	}

	/**
	* Sets the asset ID of this sample asset.
	*
	* @param assetId the asset ID of this sample asset
	*/
	public void setAssetId(long assetId) {
		_sampleAsset.setAssetId(assetId);
	}

	/**
	* Returns the user ID of this sample asset.
	*
	* @return the user ID of this sample asset
	*/
	public long getUserId() {
		return _sampleAsset.getUserId();
	}

	/**
	* Sets the user ID of this sample asset.
	*
	* @param userId the user ID of this sample asset
	*/
	public void setUserId(long userId) {
		_sampleAsset.setUserId(userId);
	}

	/**
	* Returns the user uuid of this sample asset.
	*
	* @return the user uuid of this sample asset
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _sampleAsset.getUserUuid();
	}

	/**
	* Sets the user uuid of this sample asset.
	*
	* @param userUuid the user uuid of this sample asset
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_sampleAsset.setUserUuid(userUuid);
	}

	/**
	* Returns the name of this sample asset.
	*
	* @return the name of this sample asset
	*/
	public java.lang.String getName() {
		return _sampleAsset.getName();
	}

	/**
	* Sets the name of this sample asset.
	*
	* @param name the name of this sample asset
	*/
	public void setName(java.lang.String name) {
		_sampleAsset.setName(name);
	}

	/**
	* Returns the date of this sample asset.
	*
	* @return the date of this sample asset
	*/
	public java.util.Date getDate() {
		return _sampleAsset.getDate();
	}

	/**
	* Sets the date of this sample asset.
	*
	* @param date the date of this sample asset
	*/
	public void setDate(java.util.Date date) {
		_sampleAsset.setDate(date);
	}

	public boolean isNew() {
		return _sampleAsset.isNew();
	}

	public void setNew(boolean n) {
		_sampleAsset.setNew(n);
	}

	public boolean isCachedModel() {
		return _sampleAsset.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_sampleAsset.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _sampleAsset.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _sampleAsset.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_sampleAsset.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _sampleAsset.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_sampleAsset.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new SampleAssetWrapper((SampleAsset)_sampleAsset.clone());
	}

	public int compareTo(com.liferay.samplejsonws.model.SampleAsset sampleAsset) {
		return _sampleAsset.compareTo(sampleAsset);
	}

	@Override
	public int hashCode() {
		return _sampleAsset.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.samplejsonws.model.SampleAsset> toCacheModel() {
		return _sampleAsset.toCacheModel();
	}

	public com.liferay.samplejsonws.model.SampleAsset toEscapedModel() {
		return new SampleAssetWrapper(_sampleAsset.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _sampleAsset.toString();
	}

	public java.lang.String toXmlString() {
		return _sampleAsset.toXmlString();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public SampleAsset getWrappedSampleAsset() {
		return _sampleAsset;
	}

	public SampleAsset getWrappedModel() {
		return _sampleAsset;
	}

	public void resetOriginalValues() {
		_sampleAsset.resetOriginalValues();
	}

	private SampleAsset _sampleAsset;
}
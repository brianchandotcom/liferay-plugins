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

package com.liferay.so.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

/**
 * The base model interface for the FavoriteSite service. Represents a row in the &quot;SO_FavoriteSite&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.so.model.impl.FavoriteSiteModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.so.model.impl.FavoriteSiteImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FavoriteSite
 * @see com.liferay.so.model.impl.FavoriteSiteImpl
 * @see com.liferay.so.model.impl.FavoriteSiteModelImpl
 * @generated
 */
@ProviderType
public interface FavoriteSiteModel extends BaseModel<FavoriteSite>, ShardedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a favorite site model instance should use the {@link FavoriteSite} interface instead.
	 */

	/**
	 * Returns the primary key of this favorite site.
	 *
	 * @return the primary key of this favorite site
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this favorite site.
	 *
	 * @param primaryKey the primary key of this favorite site
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the favorite site ID of this favorite site.
	 *
	 * @return the favorite site ID of this favorite site
	 */
	public long getFavoriteSiteId();

	/**
	 * Sets the favorite site ID of this favorite site.
	 *
	 * @param favoriteSiteId the favorite site ID of this favorite site
	 */
	public void setFavoriteSiteId(long favoriteSiteId);

	/**
	 * Returns the group ID of this favorite site.
	 *
	 * @return the group ID of this favorite site
	 */
	public long getGroupId();

	/**
	 * Sets the group ID of this favorite site.
	 *
	 * @param groupId the group ID of this favorite site
	 */
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this favorite site.
	 *
	 * @return the company ID of this favorite site
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this favorite site.
	 *
	 * @param companyId the company ID of this favorite site
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this favorite site.
	 *
	 * @return the user ID of this favorite site
	 */
	public long getUserId();

	/**
	 * Sets the user ID of this favorite site.
	 *
	 * @param userId the user ID of this favorite site
	 */
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this favorite site.
	 *
	 * @return the user uuid of this favorite site
	 */
	public String getUserUuid();

	/**
	 * Sets the user uuid of this favorite site.
	 *
	 * @param userUuid the user uuid of this favorite site
	 */
	public void setUserUuid(String userUuid);

	@Override
	public boolean isNew();

	@Override
	public void setNew(boolean n);

	@Override
	public boolean isCachedModel();

	@Override
	public void setCachedModel(boolean cachedModel);

	@Override
	public boolean isEscapedModel();

	@Override
	public Serializable getPrimaryKeyObj();

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	@Override
	public ExpandoBridge getExpandoBridge();

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel);

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge);

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	@Override
	public Object clone();

	@Override
	public int compareTo(com.liferay.so.model.FavoriteSite favoriteSite);

	@Override
	public int hashCode();

	@Override
	public CacheModel<com.liferay.so.model.FavoriteSite> toCacheModel();

	@Override
	public com.liferay.so.model.FavoriteSite toEscapedModel();

	@Override
	public com.liferay.so.model.FavoriteSite toUnescapedModel();

	@Override
	public String toString();

	@Override
	public String toXmlString();
}
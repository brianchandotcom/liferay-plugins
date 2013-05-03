/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.sync.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DLSync}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLSync
 * @generated
 */
public class DLSyncWrapper implements DLSync, ModelWrapper<DLSync> {
	public DLSyncWrapper(DLSync dlSync) {
		_dlSync = dlSync;
	}

	public Class<?> getModelClass() {
		return DLSync.class;
	}

	public String getModelClassName() {
		return DLSync.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("syncId", getSyncId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("fileId", getFileId());
		attributes.put("fileUuid", getFileUuid());
		attributes.put("repositoryId", getRepositoryId());
		attributes.put("parentFolderId", getParentFolderId());
		attributes.put("name", getName());
		attributes.put("checksum", getChecksum());
		attributes.put("description", getDescription());
		attributes.put("event", getEvent());
		attributes.put("lockUserId", getLockUserId());
		attributes.put("lockUserName", getLockUserName());
		attributes.put("size", getSize());
		attributes.put("type", getType());
		attributes.put("version", getVersion());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long syncId = (Long)attributes.get("syncId");

		if (syncId != null) {
			setSyncId(syncId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long createDate = (Long)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Long modifiedDate = (Long)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long fileId = (Long)attributes.get("fileId");

		if (fileId != null) {
			setFileId(fileId);
		}

		String fileUuid = (String)attributes.get("fileUuid");

		if (fileUuid != null) {
			setFileUuid(fileUuid);
		}

		Long repositoryId = (Long)attributes.get("repositoryId");

		if (repositoryId != null) {
			setRepositoryId(repositoryId);
		}

		Long parentFolderId = (Long)attributes.get("parentFolderId");

		if (parentFolderId != null) {
			setParentFolderId(parentFolderId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String checksum = (String)attributes.get("checksum");

		if (checksum != null) {
			setChecksum(checksum);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String event = (String)attributes.get("event");

		if (event != null) {
			setEvent(event);
		}

		Long lockUserId = (Long)attributes.get("lockUserId");

		if (lockUserId != null) {
			setLockUserId(lockUserId);
		}

		String lockUserName = (String)attributes.get("lockUserName");

		if (lockUserName != null) {
			setLockUserName(lockUserName);
		}

		Long size = (Long)attributes.get("size");

		if (size != null) {
			setSize(size);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}
	}

	/**
	* Returns the primary key of this d l sync.
	*
	* @return the primary key of this d l sync
	*/
	public long getPrimaryKey() {
		return _dlSync.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d l sync.
	*
	* @param primaryKey the primary key of this d l sync
	*/
	public void setPrimaryKey(long primaryKey) {
		_dlSync.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the sync ID of this d l sync.
	*
	* @return the sync ID of this d l sync
	*/
	public long getSyncId() {
		return _dlSync.getSyncId();
	}

	/**
	* Sets the sync ID of this d l sync.
	*
	* @param syncId the sync ID of this d l sync
	*/
	public void setSyncId(long syncId) {
		_dlSync.setSyncId(syncId);
	}

	/**
	* Returns the company ID of this d l sync.
	*
	* @return the company ID of this d l sync
	*/
	public long getCompanyId() {
		return _dlSync.getCompanyId();
	}

	/**
	* Sets the company ID of this d l sync.
	*
	* @param companyId the company ID of this d l sync
	*/
	public void setCompanyId(long companyId) {
		_dlSync.setCompanyId(companyId);
	}

	/**
	* Returns the create date of this d l sync.
	*
	* @return the create date of this d l sync
	*/
	public long getCreateDate() {
		return _dlSync.getCreateDate();
	}

	/**
	* Sets the create date of this d l sync.
	*
	* @param createDate the create date of this d l sync
	*/
	public void setCreateDate(long createDate) {
		_dlSync.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this d l sync.
	*
	* @return the modified date of this d l sync
	*/
	public long getModifiedDate() {
		return _dlSync.getModifiedDate();
	}

	/**
	* Sets the modified date of this d l sync.
	*
	* @param modifiedDate the modified date of this d l sync
	*/
	public void setModifiedDate(long modifiedDate) {
		_dlSync.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the file ID of this d l sync.
	*
	* @return the file ID of this d l sync
	*/
	public long getFileId() {
		return _dlSync.getFileId();
	}

	/**
	* Sets the file ID of this d l sync.
	*
	* @param fileId the file ID of this d l sync
	*/
	public void setFileId(long fileId) {
		_dlSync.setFileId(fileId);
	}

	/**
	* Returns the file uuid of this d l sync.
	*
	* @return the file uuid of this d l sync
	*/
	public java.lang.String getFileUuid() {
		return _dlSync.getFileUuid();
	}

	/**
	* Sets the file uuid of this d l sync.
	*
	* @param fileUuid the file uuid of this d l sync
	*/
	public void setFileUuid(java.lang.String fileUuid) {
		_dlSync.setFileUuid(fileUuid);
	}

	/**
	* Returns the repository ID of this d l sync.
	*
	* @return the repository ID of this d l sync
	*/
	public long getRepositoryId() {
		return _dlSync.getRepositoryId();
	}

	/**
	* Sets the repository ID of this d l sync.
	*
	* @param repositoryId the repository ID of this d l sync
	*/
	public void setRepositoryId(long repositoryId) {
		_dlSync.setRepositoryId(repositoryId);
	}

	/**
	* Returns the parent folder ID of this d l sync.
	*
	* @return the parent folder ID of this d l sync
	*/
	public long getParentFolderId() {
		return _dlSync.getParentFolderId();
	}

	/**
	* Sets the parent folder ID of this d l sync.
	*
	* @param parentFolderId the parent folder ID of this d l sync
	*/
	public void setParentFolderId(long parentFolderId) {
		_dlSync.setParentFolderId(parentFolderId);
	}

	/**
	* Returns the name of this d l sync.
	*
	* @return the name of this d l sync
	*/
	public java.lang.String getName() {
		return _dlSync.getName();
	}

	/**
	* Sets the name of this d l sync.
	*
	* @param name the name of this d l sync
	*/
	public void setName(java.lang.String name) {
		_dlSync.setName(name);
	}

	/**
	* Returns the checksum of this d l sync.
	*
	* @return the checksum of this d l sync
	*/
	public java.lang.String getChecksum() {
		return _dlSync.getChecksum();
	}

	/**
	* Sets the checksum of this d l sync.
	*
	* @param checksum the checksum of this d l sync
	*/
	public void setChecksum(java.lang.String checksum) {
		_dlSync.setChecksum(checksum);
	}

	/**
	* Returns the description of this d l sync.
	*
	* @return the description of this d l sync
	*/
	public java.lang.String getDescription() {
		return _dlSync.getDescription();
	}

	/**
	* Sets the description of this d l sync.
	*
	* @param description the description of this d l sync
	*/
	public void setDescription(java.lang.String description) {
		_dlSync.setDescription(description);
	}

	/**
	* Returns the event of this d l sync.
	*
	* @return the event of this d l sync
	*/
	public java.lang.String getEvent() {
		return _dlSync.getEvent();
	}

	/**
	* Sets the event of this d l sync.
	*
	* @param event the event of this d l sync
	*/
	public void setEvent(java.lang.String event) {
		_dlSync.setEvent(event);
	}

	/**
	* Returns the lock user ID of this d l sync.
	*
	* @return the lock user ID of this d l sync
	*/
	public long getLockUserId() {
		return _dlSync.getLockUserId();
	}

	/**
	* Sets the lock user ID of this d l sync.
	*
	* @param lockUserId the lock user ID of this d l sync
	*/
	public void setLockUserId(long lockUserId) {
		_dlSync.setLockUserId(lockUserId);
	}

	/**
	* Returns the lock user uuid of this d l sync.
	*
	* @return the lock user uuid of this d l sync
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getLockUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlSync.getLockUserUuid();
	}

	/**
	* Sets the lock user uuid of this d l sync.
	*
	* @param lockUserUuid the lock user uuid of this d l sync
	*/
	public void setLockUserUuid(java.lang.String lockUserUuid) {
		_dlSync.setLockUserUuid(lockUserUuid);
	}

	/**
	* Returns the lock user name of this d l sync.
	*
	* @return the lock user name of this d l sync
	*/
	public java.lang.String getLockUserName() {
		return _dlSync.getLockUserName();
	}

	/**
	* Sets the lock user name of this d l sync.
	*
	* @param lockUserName the lock user name of this d l sync
	*/
	public void setLockUserName(java.lang.String lockUserName) {
		_dlSync.setLockUserName(lockUserName);
	}

	/**
	* Returns the size of this d l sync.
	*
	* @return the size of this d l sync
	*/
	public long getSize() {
		return _dlSync.getSize();
	}

	/**
	* Sets the size of this d l sync.
	*
	* @param size the size of this d l sync
	*/
	public void setSize(long size) {
		_dlSync.setSize(size);
	}

	/**
	* Returns the type of this d l sync.
	*
	* @return the type of this d l sync
	*/
	public java.lang.String getType() {
		return _dlSync.getType();
	}

	/**
	* Sets the type of this d l sync.
	*
	* @param type the type of this d l sync
	*/
	public void setType(java.lang.String type) {
		_dlSync.setType(type);
	}

	/**
	* Returns the version of this d l sync.
	*
	* @return the version of this d l sync
	*/
	public java.lang.String getVersion() {
		return _dlSync.getVersion();
	}

	/**
	* Sets the version of this d l sync.
	*
	* @param version the version of this d l sync
	*/
	public void setVersion(java.lang.String version) {
		_dlSync.setVersion(version);
	}

	public boolean isNew() {
		return _dlSync.isNew();
	}

	public void setNew(boolean n) {
		_dlSync.setNew(n);
	}

	public boolean isCachedModel() {
		return _dlSync.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_dlSync.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _dlSync.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _dlSync.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_dlSync.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _dlSync.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_dlSync.setExpandoBridgeAttributes(baseModel);
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_dlSync.setExpandoBridgeAttributes(expandoBridge);
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_dlSync.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new DLSyncWrapper((DLSync)_dlSync.clone());
	}

	public int compareTo(DLSync dlSync) {
		return _dlSync.compareTo(dlSync);
	}

	@Override
	public int hashCode() {
		return _dlSync.hashCode();
	}

	public com.liferay.portal.model.CacheModel<DLSync> toCacheModel() {
		return _dlSync.toCacheModel();
	}

	public DLSync toEscapedModel() {
		return new DLSyncWrapper(_dlSync.toEscapedModel());
	}

	public DLSync toUnescapedModel() {
		return new DLSyncWrapper(_dlSync.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _dlSync.toString();
	}

	public java.lang.String toXmlString() {
		return _dlSync.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlSync.persist();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public DLSync getWrappedDLSync() {
		return _dlSync;
	}

	public DLSync getWrappedModel() {
		return _dlSync;
	}

	public void resetOriginalValues() {
		_dlSync.resetOriginalValues();
	}

	private DLSync _dlSync;
}
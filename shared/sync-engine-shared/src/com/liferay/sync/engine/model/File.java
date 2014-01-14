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

package com.liferay.sync.engine.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import com.liferay.sync.engine.service.persistence.BaseSyncDaoImpl;

/**
 * @author Shinn Lok
 */
@DatabaseTable(tableName = "File", daoClass = BaseSyncDaoImpl.class)
public class File {

	public long getFileId() {
		return fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public boolean getFolder() {
		return folder;
	}

	public long getParentFileId() {
		return parentFileId;
	}

	public long getRemoteFileId() {
		return remoteFileId;
	}

	public long getRemoteParentFileId() {
		return remoteParentFileId;
	}

	public long getRepositoryId() {
		return repositoryId;
	}

	public void setFileId(long fileId) {
		this.fileId = fileId;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFolder(boolean folder) {
		this.folder = folder;
	}

	public void setParentFileId(long parentFileId) {
		this.parentFileId = parentFileId;
	}

	public void setRemoteFileId(long remoteFileId) {
		this.remoteFileId = remoteFileId;
	}

	public void setRemoteParentFileId(long remoteParentFileId) {
		this.remoteParentFileId = remoteParentFileId;
	}

	public void setRepositoryId(long repositoryId) {
		this.repositoryId = repositoryId;
	}

	@DatabaseField(generatedId = true, useGetSet = true)
	private long fileId;

	@DatabaseField(useGetSet = true, width = 255)
	private String fileName;

	@DatabaseField(useGetSet = true)
	private boolean folder;

	@DatabaseField(useGetSet = true)
	private long parentFileId;

	@DatabaseField(useGetSet = true)
	private long remoteFileId;

	@DatabaseField(useGetSet = true)
	private long remoteParentFileId;

	@DatabaseField(useGetSet = true)
	private long repositoryId;

}
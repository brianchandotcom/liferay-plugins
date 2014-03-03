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

package com.liferay.sync.engine.filesystem;

import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncSite;
import com.liferay.sync.engine.model.SyncWatchEvent;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.service.SyncSiteService;
import com.liferay.sync.engine.service.SyncWatchEventService;
import com.liferay.sync.engine.util.FilePathNameUtil;
import com.liferay.sync.engine.util.FileUtil;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Michael Young
 */
public class SyncSiteWatchEventListener extends BaseWatchEventListener {

	public SyncSiteWatchEventListener(long syncAccountId) {
		super(syncAccountId);
	}

	@Override
	public void watchEvent(String eventType, Path filePath) {
		addSyncWatchEvent(eventType, filePath);
	}

	protected void addSyncWatchEvent(String eventType, Path filePath) {
		try {
			if (eventType.equals(SyncWatchEvent.EVENT_TYPE_CREATE) &&
				FileUtil.isIgnoredFilePath(filePath)) {

				return;
			}

			String parentFilePathName = FilePathNameUtil.getFilePathName(
				filePath.getParent());

			SyncFile parentSyncFile = SyncFileService.fetchSyncFile(
				parentFilePathName, getSyncAccountId());

			SyncSite syncSite = SyncSiteService.fetchSyncSite(
				parentSyncFile.getRepositoryId(), getSyncAccountId());

			List<Long> syncSiteIds = SyncSiteService.getActiveSyncSiteIds(
				getSyncAccountId());

			if ((parentSyncFile == null) ||
				!syncSiteIds.contains(syncSite.getSyncSiteId())) {

				return;
			}

			String filePathName = FilePathNameUtil.getFilePathName(filePath);

			SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
				getSyncAccountId());

			if (filePathName.equals(syncAccount.getFilePathName()) ||
				parentFilePathName.equals(syncAccount.getFilePathName())) {

				return;
			}

			SyncWatchEventService.addSyncWatchEvent(
				eventType, filePathName, getFileType(eventType, filePath),
				getSyncAccountId());
		}
		catch (Exception e) {
			_logger.error(e.getMessage(), e);
		}
	}

	protected String getFileType(String eventType, Path filePath) {
		if (eventType.equals(SyncWatchEvent.EVENT_TYPE_DELETE)) {
			SyncFile syncFile = SyncFileService.fetchSyncFile(
				FilePathNameUtil.getFilePathName(filePath), getSyncAccountId());

			if (syncFile != null) {
				return syncFile.getType();
			}
		}

		if (Files.isDirectory(filePath, LinkOption.NOFOLLOW_LINKS)) {
			return SyncFile.TYPE_FOLDER;
		}

		return SyncFile.TYPE_FILE;
	}

	private static Logger _logger = LoggerFactory.getLogger(
		SyncWatchEventService.class);

}
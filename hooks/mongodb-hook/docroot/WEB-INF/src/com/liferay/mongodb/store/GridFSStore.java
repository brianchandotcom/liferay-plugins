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

package com.liferay.mongodb.store;

import com.liferay.mongodb.util.MongoDBUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.InvalidFileVersionException;
import com.liferay.portlet.documentlibrary.NoSuchFileException;
import com.liferay.portlet.documentlibrary.store.BaseStore;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import com.mongodb.gridfs.GridFSInputFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Michael C. Han
 */
public class GridFSStore extends BaseStore {

	@Override
	public void addDirectory(long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, InputStream is)
		throws PortalException, SystemException {

		String fileKey = getFileKey(companyId, repositoryId, fileName);

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();

			_log.info("Starting upload for: " + fileKey);
		}

		try {
			doAddFile(
				companyId, repositoryId, fileName, VERSION_DEFAULT, fileKey, is,
				true);
		}
		finally {
			if (_log.isInfoEnabled()) {
				stopWatch.stop();

				_log.info(
					"Upload complete for: " + fileKey + " in " +
						stopWatch.getTime() + "ms");
			}
		}
	}

	@Override
	public void checkRoot(long companyId) throws SystemException {
	}

	@Override
	public void copyFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException, SystemException {

		if (Validator.isNull(toVersionLabel)) {
			throw new InvalidFileVersionException(
				"Cannot copy file: " + fileName +
				" to an invalid version: " + toVersionLabel);
		}

		String toFileKey = getFileKey(
			companyId, repositoryId, fileName, toVersionLabel);

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();

			_log.info(
				"Starting copy from: " +
					getFileKey(
						companyId, repositoryId, fileName, fromVersionLabel) +
					" to " + toFileKey);
		}

		GridFS gridFS = new GridFS(
			MongoDBUtil.getDB(companyId), String.valueOf(repositoryId));

		if (Validator.isNull(fromVersionLabel)) {
			fromVersionLabel = getHeadVersionLabel(
				gridFS, companyId, repositoryId, fileName);
		}

		String fromFileKey = getFileKey(
			companyId, repositoryId, fileName, fromVersionLabel);

		try {
			if (hasFile(gridFS, toFileKey)) {
				throw new DuplicateFileException(
					"Duplicate file for: " + companyId + " " +
					repositoryId + " " + fileName + " " + toVersionLabel);
			}

			GridFSDBFile fromGridFSDBFile = gridFS.findOne(fromFileKey);

			if (fromGridFSDBFile == null) {
				throw new NoSuchFileException(
					"Unable to find file for: " + companyId + " " +
					repositoryId + " " + fileName + " " + fromVersionLabel);
			}

			doAddFile(
				companyId, repositoryId, fileName, toVersionLabel, toFileKey,
				fromGridFSDBFile.getInputStream(), true);
		}
		finally {
			if (_log.isInfoEnabled()) {
				stopWatch.stop();

				_log.info(
					"Copy complete from: " + fromFileKey + " to " + toFileKey +
						" in " + stopWatch.getTime() + "ms");
			}
		}
	}

	@Override
	public void deleteDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		String fileKey = getFileKey(companyId, repositoryId, dirName, null);

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();

			_log.info("Starting deletion of all files in: " + fileKey);
		}

		GridFS gridFS = new GridFS(
			MongoDBUtil.getDB(companyId), String.valueOf(repositoryId));

		String regex = getRegex(companyId, repositoryId, dirName);

		DBObject queryDBObject = getFileNameQueryDBObject(regex);

		gridFS.remove(queryDBObject);

		if (_log.isInfoEnabled()) {
			stopWatch.stop();

			_log.info(
				"Deletion complete for: " + fileKey + " in " +
					stopWatch.getTime() + "ms");
		}
	}

	@Override
	public void deleteFile(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		String fileKey = getFileKey(companyId, repositoryId, fileName, null);

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();

			_log.info("Starting deletion of all versions of: " + fileKey);
		}

		GridFS gridFS = new GridFS(
			MongoDBUtil.getDB(companyId), String.valueOf(repositoryId));

		String regex = getRegex(companyId, repositoryId, fileName);

		DBObject queryDBObject = getFileNameQueryDBObject(regex);

		gridFS.remove(queryDBObject);

		if (_log.isInfoEnabled()) {
			stopWatch.stop();

			_log.info(
				"Deletion complete for: " + fileKey + " in " +
					stopWatch.getTime() + "ms");
		}
	}

	@Override
	public void deleteFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		String fileKey = getFileKey(
			companyId, repositoryId, fileName, versionLabel);

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();

			_log.info("Starting deletion of all versions of: " + fileKey);
		}

		GridFS gridFS = new GridFS(
			MongoDBUtil.getDB(companyId), String.valueOf(repositoryId));

		gridFS.remove(fileKey);

		if (_log.isInfoEnabled()) {
			stopWatch.stop();

			_log.info(
				"Deletion complete for: " + fileKey + " in " +
					stopWatch.getTime() + "ms");
		}
	}

	@Override
	public File getFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();

			_log.info("Starting retrieval of: " + getFileKey(
				companyId, repositoryId, fileName, versionLabel));
		}

		GridFS gridFS = new GridFS(
			MongoDBUtil.getDB(companyId), String.valueOf(repositoryId));

		if (Validator.isNull(versionLabel)) {
			versionLabel = getHeadVersionLabel(
				gridFS, companyId, repositoryId, fileName);
		}

		String fileKey = getFileKey(
			companyId, repositoryId, fileName, versionLabel);

		InputStream inputStream = null;

		try {
			GridFSDBFile gridFSDBFile = gridFS.findOne(fileKey);

			if (gridFSDBFile == null) {
				throw new NoSuchFileException(
					"Unable to find file for: " + companyId + " " +
					repositoryId + " " + fileName + " " + versionLabel);
			}

			inputStream = gridFSDBFile.getInputStream();

			return FileUtil.createTempFile(gridFSDBFile.getInputStream());
		}
		catch (IOException ie) {
			throw new SystemException("Unable to retrieve file:" + fileKey, ie);
		}
		finally {
			try {
				inputStream.close();
			}
			catch (IOException e) {
			}

			if (_log.isInfoEnabled()) {
				stopWatch.stop();

				_log.info(
					"Retrieval complete for: " + fileKey + " in " +
						stopWatch.getTime() + "ms");
			}
		}
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();

			_log.info("Starting retrieval of: " + getFileKey(
				companyId, repositoryId, fileName, versionLabel));
		}

		GridFS gridFS = new GridFS(
			MongoDBUtil.getDB(companyId), String.valueOf(repositoryId));

		if (Validator.isNull(versionLabel)) {
			versionLabel = getHeadVersionLabel(
				gridFS, companyId, repositoryId, fileName);
		}

		String fileKey = getFileKey(
			companyId, repositoryId, fileName, versionLabel);

		try {
			GridFSDBFile gridFSDBFile = gridFS.findOne(fileKey);

			if (gridFSDBFile == null) {
				throw new NoSuchFileException(
					"Unable to find file for: " + companyId + " " +
					repositoryId + " " + fileName + " " + versionLabel);
			}

			return gridFSDBFile.getInputStream();
		}
		finally {
			if (_log.isInfoEnabled()) {
				stopWatch.stop();

				_log.info(
					"Retrieval complete for: " + fileKey + " in " +
						stopWatch.getTime() + "ms");
			}
		}
	}

	public String[] getFileNames(long companyId, long repositoryId)
		throws SystemException {

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();

			_log.info(
				"Starting file listing for: " + companyId + "." + repositoryId);
		}

		GridFS gridFS = new GridFS(
			MongoDBUtil.getDB(companyId), String.valueOf(repositoryId));

		DBCursor dbCursor = gridFS.getFileList();

		int size = dbCursor.size();

		Set<String> fileNames = new HashSet<String>(size);

		while (dbCursor.hasNext()) {
			DBObject dbObject = dbCursor.next();

			String originalFileName = (String)dbObject.get(
				_ORIGINAL_FILE_NAME_PROPERTY);

			fileNames.add(originalFileName);
		}

		try {
			return fileNames.toArray(new String[fileNames.size()]);
		}
		finally {
			if (_log.isInfoEnabled()) {
				stopWatch.stop();

				_log.info(
					"File listing complete for " + companyId + "." +
						repositoryId + " in " + stopWatch.getTime() + "ms");
			}
		}
	}

	@Override
	public String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		String fileKey = getFileKey(companyId, repositoryId, dirName, null);

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();

			_log.info("Starting file listing for: " + fileKey);
		}

		GridFS gridFS = new GridFS(
			MongoDBUtil.getDB(companyId), String.valueOf(repositoryId));

		String regex = getRegex(companyId, repositoryId, dirName);

		DBObject queryDBObject = getFileNameQueryDBObject(regex);

		List<GridFSDBFile> gridFSDBFiles = gridFS.find(queryDBObject);

		int size = gridFSDBFiles.size();

		Set<String> fileNames = new HashSet<String>(size);

		for (GridFSDBFile gridFSDBFile : gridFSDBFiles) {

			String originalFileName = (String)gridFSDBFile.get(
				_ORIGINAL_FILE_NAME_PROPERTY);

			fileNames.add(originalFileName);
		}

		try {
			return fileNames.toArray(new String[fileNames.size()]);
		}
		finally {
			if (_log.isInfoEnabled()) {
				stopWatch.stop();

				_log.info(
					"File listing complete for " + fileKey + " in " +
						stopWatch.getTime() + "ms");
			}
		}
	}

	@Override
	public long getFileSize(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();

			_log.info(
				"Starting file size retrieval for: " + getFileKey(
					companyId, repositoryId, fileName, null));
		}

		GridFS gridFS = new GridFS(
			MongoDBUtil.getDB(companyId), String.valueOf(repositoryId));

		String versionLabel = getHeadVersionLabel(
			gridFS, companyId, repositoryId, fileName);

		String fileKey = getFileKey(
			companyId, repositoryId, fileName, versionLabel);

		GridFSDBFile gridFSDBFile = gridFS.findOne(fileKey);

		try {
			if (gridFSDBFile == null) {
				throw new NoSuchFileException(
					"Unable to find file for: " + companyId + " " +
					repositoryId + " " + fileName + " " + versionLabel);
			}

			return gridFSDBFile.getLength();
		}
		finally {
			if (_log.isInfoEnabled()) {
				stopWatch.stop();

				_log.info(
					"File size retrieval complete for " + fileKey + " in " +
						stopWatch.getTime() + "ms");
			}
		}
	}

	@Override
	public boolean hasDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		return true;
	}

	@Override
	public boolean hasFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		String fileKey = getFileKey(
			companyId, repositoryId, fileName, versionLabel);

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();

			_log.info("Starting presence check for file: " + fileKey);
		}

		try {
			GridFS gridFS = new GridFS(
				MongoDBUtil.getDB(companyId), String.valueOf(repositoryId));

			return hasFile(gridFS, fileKey);
		}
		finally {
			if (_log.isInfoEnabled()) {
				stopWatch.stop();

				_log.info(
					"File presence check complete for " + fileKey + " in " +
						stopWatch.getTime() + "ms");
			}
		}
	}

	@Override
	public void move(String srcDir, String destDir) throws SystemException {
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, long newRepositoryId,
			String fileName)
		throws PortalException, SystemException {

		String oldFileKey = getFileKey(companyId, repositoryId, fileName, null);

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();

			_log.info(
				"Updating repositoryIds for file: " + oldFileKey + " to " +
				getFileKey(companyId, newRepositoryId, fileName, null));
		}

		GridFS originGridFS = new GridFS(
			MongoDBUtil.getDB(companyId), String.valueOf(repositoryId));

		String regex = getRegex(companyId, repositoryId, fileName);

		DBObject queryDBObject = getFileNameQueryDBObject(regex);

		List<GridFSDBFile> gridFSDBFiles = originGridFS.find(queryDBObject);

		try {
			if (gridFSDBFiles.isEmpty()) {
				throw new NoSuchFileException(
					"Unable to find file for: " + companyId + " " +
					repositoryId + " " + fileName);
			}

			GridFS destinationGridFS = new GridFS(
				MongoDBUtil.getDB(companyId), String.valueOf(newRepositoryId));

			for (GridFSDBFile gridFSDBFile : gridFSDBFiles) {
				String version = (String)gridFSDBFile.get(_VERSION_PROPERTY);

				String newFileKey = getFileKey(
					companyId, newRepositoryId, fileName, version);

				doAddFile(
					destinationGridFS, companyId, repositoryId, fileName,
					version, newFileKey, gridFSDBFile.getInputStream(), false);

				originGridFS.remove(gridFSDBFile.getFilename());
			}
		}
		finally {
			if (_log.isInfoEnabled()) {
				stopWatch.stop();

				_log.info(
					"Updating completed for " + oldFileKey + " in " +
						stopWatch.getTime() + "ms");
			}
		}
	}

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String newFileName)
		throws PortalException, SystemException {

		String oldFileKey = getFileKey(companyId, repositoryId, fileName, null);

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();

			_log.info(
				"Updating repositoryIds for file: " + oldFileKey + " to " +
				getFileKey(companyId, repositoryId, newFileName, null));
		}

		GridFS gridFS = new GridFS(
			MongoDBUtil.getDB(companyId), String.valueOf(repositoryId));

		String regex = getRegex(companyId, repositoryId, fileName);

		DBObject queryDBObject = getFileNameQueryDBObject(regex);

		List<GridFSDBFile> gridFSDBFiles = gridFS.find(queryDBObject);

		try {
			if (gridFSDBFiles.isEmpty()) {
				throw new NoSuchFileException(
					"Unable to find file for: " + companyId + " " +
					repositoryId + " " + fileName);
			}

			for (GridFSDBFile gridFSDBFile : gridFSDBFiles) {
				String version = (String)gridFSDBFile.get(_VERSION_PROPERTY);

				String newFileKey = getFileKey(
					companyId, repositoryId, newFileName, version);

				gridFSDBFile.put(_FILE_NAME_PROPERTY, newFileKey);

				gridFSDBFile.save();
			}
		}
		finally {
			if (_log.isInfoEnabled()) {
				stopWatch.stop();

				_log.info(
					"Updating completed for " + oldFileKey + " in " +
						stopWatch.getTime() + "ms");
			}
		}
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream is)
		throws PortalException, SystemException {

		if (is == null) {
			throw new IllegalArgumentException(
				"Cannot pass null for input stream");
		}

		if (Validator.isNull(versionLabel)) {
			throw new InvalidFileVersionException(
				"Cannot update file: " + fileName +
				" due to invalid version: " + versionLabel);
		}

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();

			_log.info(
				"Starting update for file : " + getFileKey(
					companyId, repositoryId, fileName, versionLabel));
		}

		GridFS gridFS = new GridFS(
			MongoDBUtil.getDB(companyId), String.valueOf(repositoryId));

		String fileKey = getFileKey(
			companyId, repositoryId, fileName, versionLabel);

		if (hasFile(gridFS, fileKey)) {
			throw new DuplicateFileException(
				"Duplicate file for: " + companyId + " " +
				repositoryId + " " + fileName + " " + versionLabel);
		}

		GridFSInputFile gridFSInputFile = gridFS.createFile(is, fileKey, true);

		populateGridFSAttributes(
			gridFSInputFile, companyId, repositoryId, fileName, versionLabel);

		try {
			gridFSInputFile.save();
		}
		finally {
			if (_log.isInfoEnabled()) {
				stopWatch.stop();

				_log.info(
					"Updating completed for " + fileKey + " in " +
						stopWatch.getTime() + "ms");
			}
		}
	}

	@Override
	public void updateFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException, SystemException {

		if (Validator.isNull(toVersionLabel)) {
			throw new InvalidFileVersionException(
				"Invalid version of file " + fileName +
				" to copy to: " + toVersionLabel);
		}

		String toFileKey = getFileKey(
			companyId, repositoryId, fileName, toVersionLabel);

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();

			_log.info(
				"Starting update version from: " +
					getFileKey(
						companyId, repositoryId, fileName, fromVersionLabel) +
					" to " + toFileKey);
		}

		GridFS gridFS = new GridFS(
			MongoDBUtil.getDB(companyId), String.valueOf(repositoryId));

		if (Validator.isNull(fromVersionLabel)) {
			fromVersionLabel = getHeadVersionLabel(
				gridFS, companyId, repositoryId, fileName);
		}

		String fromFileKey = getFileKey(
			companyId, repositoryId, fileName, fromVersionLabel);

		try {
			if (hasFile(gridFS, toFileKey)) {
				throw new DuplicateFileException(
					"Duplicate file : " + getFileKey(
						companyId, repositoryId, fileName, toVersionLabel));
			}

			GridFSDBFile fromGridFSDBFile = gridFS.findOne(fromFileKey);

			if (fromGridFSDBFile == null) {
				throw new NoSuchFileException(
					"Unable to find file for: " + companyId + " " +
					repositoryId + " " + fileName + " " + fromVersionLabel);
			}

			fromGridFSDBFile.put(_FILE_NAME_PROPERTY, toFileKey);

			fromGridFSDBFile.put(_VERSION_PROPERTY, toVersionLabel);

			fromGridFSDBFile.save();
		}
		finally {
			if (_log.isInfoEnabled()) {
				stopWatch.stop();

				_log.info(
					"Version update complete: " + fromFileKey + " to " +
						toFileKey + " in " + stopWatch.getTime() + "ms");
			}
		}
	}

	protected void doAddFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, String fileKey, InputStream is,
			boolean validateDuplicateFile)
		throws PortalException, SystemException {

		GridFS gridFS = new GridFS(
			MongoDBUtil.getDB(companyId), String.valueOf(repositoryId));

		doAddFile(
			gridFS, companyId, repositoryId, fileName, versionLabel, fileKey,
			is, validateDuplicateFile);
	}

	protected void doAddFile(
			GridFS gridFS, long companyId, long repositoryId, String fileName,
			String versionLabel, String fileKey, InputStream is,
			boolean validateDuplicateFile)
		throws PortalException, SystemException {

		if (validateDuplicateFile &&
			hasFile(
				gridFS, companyId, repositoryId, fileName, versionLabel)) {

			throw new DuplicateFileException(
				"Duplicate file for: " + companyId + " " +
				repositoryId + " " + fileName + " " + versionLabel);
		}

		GridFSInputFile gridFSInputFile = gridFS.createFile(is, fileKey, true);

		populateGridFSAttributes(
			gridFSInputFile, companyId, repositoryId, fileName, versionLabel);

		gridFSInputFile.save();
	}

	protected String getFileKey(
		long companyId, long repositoryId, String fileName) {

		return getFileKey(companyId, repositoryId, fileName, VERSION_DEFAULT);
	}

	protected String getFileKey(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		StringBundler sb = new StringBundler(7);

		sb.append(companyId);
		sb.append(StringPool.SLASH);
		sb.append(repositoryId);
		sb.append(StringPool.SLASH);
		sb.append(fileName);

		if (Validator.isNotNull(versionLabel)) {
			sb.append(StringPool.SLASH);
			sb.append(versionLabel);
		}

		return sb.toString();
	}

	protected DBObject getFileNameQueryDBObject(String regex) {
		BasicDBObject queryDBObject = new BasicDBObject();

		Pattern regexPattern = Pattern.compile(regex);
		queryDBObject.put(_FILE_NAME_PROPERTY, regexPattern);

		return queryDBObject;
	}

	protected String getHeadVersionLabel(
			GridFS gridFS, long companyId, long repositoryId, String fileName)
		throws PortalException {

		String regex = getRegex(companyId, repositoryId, fileName);

		DBObject queryDBObject = getFileNameQueryDBObject(regex);

		List<GridFSDBFile> gridFSDBFiles = gridFS.find(queryDBObject);

		if (gridFSDBFiles.isEmpty()) {
			throw new NoSuchFileException(
				"Unable to find file for: " + companyId + " " +
				repositoryId + " " + fileName);
		}

		String headVersion = null;

		for (GridFSDBFile gridFSDBFile : gridFSDBFiles) {
			String version = (String)gridFSDBFile.get(_VERSION_PROPERTY);

			if (headVersion == null) {
				headVersion = version;
			}
			else if (headVersion.compareTo(version) < 0) {
				headVersion = version;
			}
		}

		return headVersion;
	}

	protected String getRegex(
		long companyId, long repositoryId, String fileName) {

		StringBundler sb = new StringBundler(7);

		sb.append(companyId);
		sb.append(StringPool.SLASH);
		sb.append(repositoryId);
		sb.append(StringPool.SLASH);
		sb.append(StringPool.PLUS);
		sb.append(fileName);
		sb.append(StringPool.SLASH);
		sb.append(StringPool.PLUS);
		sb.append(StringPool.PERIOD);
		sb.append(StringPool.STAR);

		return sb.toString();
	}

	protected boolean hasFile(GridFS gridFS, String fileKey) {
		GridFSDBFile gridFSDBFile = gridFS.findOne(fileKey);

		if (gridFSDBFile == null) {
			return false;
		}

		return true;
	}

	public boolean hasFile(
			GridFS gridFS, long companyId, long repositoryId, String fileName,
			String versionLabel) {
		String fileKey = getFileKey(
			companyId, repositoryId, fileName, versionLabel);

		return hasFile(gridFS, fileKey);
	}

	protected void populateGridFSAttributes(
		GridFSFile gridFSFile, long companyId, long repositoryId,
		String fileName, String version) {

		gridFSFile.put(_COMPANY_ID_PROPERTY, companyId);
		gridFSFile.put(_ORIGINAL_FILE_NAME_PROPERTY, fileName);
		gridFSFile.put(_REPOSITORY_ID_PROPERTY, repositoryId);

		if (Validator.isNotNull(version)) {
			gridFSFile.put(_VERSION_PROPERTY, version);
		}
	}

	private static final String _COMPANY_ID_PROPERTY = "companyId";
	private static final String _FILE_NAME_PROPERTY = "filename";
	private static final String _ORIGINAL_FILE_NAME_PROPERTY =
		"originalFileName";
	private static final String _REPOSITORY_ID_PROPERTY = "repositoryId";
	private static final String _VERSION_PROPERTY = "version";

	private static Log _log = LogFactoryUtil.getLog(GridFSStore.class);

}
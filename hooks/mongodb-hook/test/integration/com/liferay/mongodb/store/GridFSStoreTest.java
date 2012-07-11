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
import com.liferay.mongodb.util.TestingMongoDBImpl;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.InvalidFileVersionException;
import com.liferay.portlet.documentlibrary.NoSuchFileException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import jodd.io.StreamUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class GridFSStoreTest {

	@Test
	public void testAddFileViaInputStream() throws Exception {

		String fileName = "test.txt";

		ByteArrayInputStream bais = new ByteArrayInputStream(
			_testFileText.getBytes());

		//begin test case
		GridFSStore gridFSStore = new GridFSStore();

		gridFSStore.addFile(_companyId, _repositoryId, fileName, bais);

		List<GridFSDBFile> storedFiles = getFiles(fileName);

		Assert.assertEquals(
			"Should only have 1 file in storage", 1, storedFiles.size());

		GridFSDBFile storedFile = storedFiles.get(0);

		String fileKey = getFileKey(
			_companyId, _repositoryId, fileName, GridFSStore.VERSION_DEFAULT);

		validateGridFSFile(fileKey, GridFSStore.VERSION_DEFAULT, storedFile);
	}

	@Test
	public void testAddDuplicateFileViaInputStream() throws Exception {

		String fileName = "myDirectory/test.txt";

		String versionLabel = GridFSStore.VERSION_DEFAULT;

		String fileKey1 = getFileKey(
			_companyId, _repositoryId, fileName, versionLabel);

		doAddFile(
			fileKey1, fileName, versionLabel,
			new ByteArrayInputStream(_testFileText.getBytes()));

		//begin test case
		GridFSStore gridFSStore = new GridFSStore();

		try {
			gridFSStore.addFile(
				_companyId, _repositoryId, fileName,
				new ByteArrayInputStream(_testFileText.getBytes()));
		}
		catch (DuplicateFileException e) {
			Assert.assertTrue(
				"Bad error message", e.getMessage().contains(fileName));

			return;
		}

		Assert.fail("Should have received a duplicate file exception");
	}

	@Test
	public void testCopyFileVersion() throws Exception {
		String fileName = "test.txt";

		String fromVersionLabel = GridFSStore.VERSION_DEFAULT;
		String toVersionLabel = "2.0";

		String fileKey = getFileKey(
			_companyId, _repositoryId, fileName, fromVersionLabel);

		doAddFile(
			fileKey, fileName, fromVersionLabel,
			new ByteArrayInputStream(_testFileText.getBytes()));

		//Begin test case
		GridFSStore gridFSStore = new GridFSStore();

		gridFSStore.copyFileVersion(
			_companyId, _repositoryId, fileName, fromVersionLabel,
			toVersionLabel);

		//validate
		List<GridFSDBFile> storedFiles = getFiles(fileName);

		Assert.assertEquals(
			"Invalid number of files found", 2, storedFiles.size());

		//check from file
		GridFSDBFile fromFile = storedFiles.get(0);

		String fromFileKey = getFileKey(
			_companyId, _repositoryId, fileName, fromVersionLabel);

		validateGridFSFile(fromFileKey, fromVersionLabel, fromFile);

		//check to file
		GridFSDBFile toFile = storedFiles.get(1);

		String toFileKey = getFileKey(
			_companyId, _repositoryId, fileName, toVersionLabel);

		validateGridFSFile(toFileKey, toVersionLabel, toFile);
	}

	@Test
	public void testCopyFileVersionWithBadVersion() throws Exception {
		//Begin test case
		GridFSStore gridFSStore = new GridFSStore();

		String fileName1 = "myDirectory/test.txt";

		String fromVersionLabel = GridFSStore.VERSION_DEFAULT;
		String toVersion = null;

		try {
			gridFSStore.copyFileVersion(
				_companyId, _repositoryId, fileName1, fromVersionLabel,
				toVersion);
		}
		catch (InvalidFileVersionException e) {
			Assert.assertTrue(
				"Bad error message", e.getMessage().contains(fileName1));
			Assert.assertTrue(
				"Bad error message", e.getMessage().contains("null"));

			return;
		}

		Assert.fail("Should have received an invalid version error");

		toVersion = "";

		try {
			gridFSStore.copyFileVersion(
				_companyId, _repositoryId, fileName1, fromVersionLabel,
				toVersion);
		}
		catch (InvalidFileVersionException e) {
			Assert.assertTrue(
				"Bad error message", e.getMessage().contains(fileName1));
			Assert.assertTrue(
				"Bad error message", e.getMessage().contains(toVersion));

			return;
		}

		Assert.fail("Should have received an invalid version error");
	}

	@Test
	public void testCopyFileVersionWithDuplicateVersion() throws Exception {
		String fileName = "test.txt";

		ByteArrayInputStream bais = new ByteArrayInputStream(
			_testFileText.getBytes());

		String fromVersionLabel = GridFSStore.VERSION_DEFAULT;
		String toVersionLabel = "2.0";

		String fromFileKey = getFileKey(
			_companyId, _repositoryId, fileName, fromVersionLabel);
		String toFileKey = getFileKey(
			_companyId, _repositoryId, fileName, toVersionLabel);

		doAddFile(fromFileKey, fileName, fromVersionLabel, bais);

		doAddFile(toFileKey, fileName, toVersionLabel, bais);

		//Begin test case
		GridFSStore gridFSStore = new GridFSStore();

		try {
			gridFSStore.copyFileVersion(
				_companyId, _repositoryId, fileName, fromVersionLabel,
				toVersionLabel);
		}
		catch (DuplicateFileException e) {
			Assert.assertTrue(
				"Bad error message", e.getMessage().contains(fileName));

			return;
		}

		Assert.fail("Should have received a duplicate file error");
	}

	@Test
	public void testDeleteDirectory() throws Exception {
		String fileName1 = "myDirectory/test.txt";
		String fileName2 = "myDirectory/test2.txt";
		String fileName3 = "2myDirectory/test3.txt";
		String fileName4 = "myDirectory2/test3.txt";
		String fileName5 = "/myDirectory/test3.txt";

		String fromVersionLabel = GridFSStore.VERSION_DEFAULT;

		String fileKey1 = getFileKey(
			_companyId, _repositoryId, fileName1, fromVersionLabel);
		String fileKey2 = getFileKey(
			_companyId, _repositoryId, fileName2, fromVersionLabel);
		String fileKey3 = getFileKey(
			_companyId, _repositoryId, fileName3, fromVersionLabel);
		String fileKey4 = getFileKey(
			_companyId, _repositoryId, fileName4, fromVersionLabel);
		String fileKey5 = getFileKey(
			_companyId, _repositoryId, fileName5, fromVersionLabel);

		doAddFile(
			fileKey1, fileName1, fromVersionLabel,
			new ByteArrayInputStream(_testFileText.getBytes()));
		doAddFile(
			fileKey2, fileName2, fromVersionLabel,
			new ByteArrayInputStream(_testFileText.getBytes()));
		doAddFile(
			fileKey3, fileName3, fromVersionLabel,
			new ByteArrayInputStream(_testFileText.getBytes()));
		doAddFile(
			fileKey4, fileName4, fromVersionLabel,
			new ByteArrayInputStream(_testFileText.getBytes()));
		doAddFile(
			fileKey5, fileName5, fromVersionLabel,
			new ByteArrayInputStream(_testFileText.getBytes()));

		//Begin test case
		GridFSStore gridFSStore = new GridFSStore();

		gridFSStore.deleteDirectory(_companyId, _repositoryId, "myDirectory");

		//validate
		GridFS gridFS = new GridFS(
			MongoDBUtil.getDB(_companyId), String.valueOf(_repositoryId));

		DBCursor dbCursor = gridFS.getFileList();

		Assert.assertEquals(
			"Invalid number of files found", 2, dbCursor.size());

		DBObject dbObject = dbCursor.next();

		validateGridFSFile(fileKey3, fromVersionLabel, dbObject);

		DBObject dbObject2 = dbCursor.next();

		validateGridFSFile(fileKey4, fromVersionLabel, dbObject2);
	}

	@Test
	public void testDeleteFile() throws Exception {
		String fileName1 = "myDirectory/test.txt";
		String fileName2 = fileName1;
		String fileName3 = "/myDirectory/test3.txt";
		String fileName4 = "myDirectory/test2.txt";
		String fileName5 = "myDirectory2/test3.txt";
		String fileName6 = "test.txt";

		String fromVersionLabel = GridFSStore.VERSION_DEFAULT;

		String fileKey1 = getFileKey(
			_companyId, _repositoryId, fileName1, fromVersionLabel);
		String fileKey2 = getFileKey(
			_companyId, _repositoryId, fileName2, "2.0");
		String fileKey3 = getFileKey(
			_companyId, _repositoryId, fileName3, fromVersionLabel);
		String fileKey4 = getFileKey(
			_companyId, _repositoryId, fileName4, fromVersionLabel);
		String fileKey5 = getFileKey(
			_companyId, _repositoryId, fileName5, fromVersionLabel);
		String fileKey6 = getFileKey(
			_companyId, _repositoryId, fileName6, fromVersionLabel);

		doAddFile(
			fileKey1, fileName1, fromVersionLabel,
			new ByteArrayInputStream(_testFileText.getBytes()));
		doAddFile(
			fileKey2, fileName2, fromVersionLabel,
			new ByteArrayInputStream(_testFileText.getBytes()));
		doAddFile(
			fileKey3, fileName3, fromVersionLabel,
			new ByteArrayInputStream(_testFileText.getBytes()));
		doAddFile(
			fileKey4, fileName4, fromVersionLabel,
			new ByteArrayInputStream(_testFileText.getBytes()));
		doAddFile(
			fileKey5, fileName5, fromVersionLabel,
			new ByteArrayInputStream(_testFileText.getBytes()));
		doAddFile(
			fileKey6, fileName6, fromVersionLabel,
			new ByteArrayInputStream(_testFileText.getBytes()));

		//Begin test case
		GridFSStore gridFSStore = new GridFSStore();

		gridFSStore.deleteFile(_companyId, _repositoryId, fileName1);

		//validate
		GridFS gridFS = new GridFS(
			MongoDBUtil.getDB(_companyId), String.valueOf(_repositoryId));

		DBCursor dbCursor = gridFS.getFileList();

		Assert.assertEquals(
			"Invalid number of files found", 4, dbCursor.size());

		DBObject dbObject = dbCursor.next();

		validateGridFSFile(fileKey3, fromVersionLabel, dbObject);

		DBObject dbObject2 = dbCursor.next();

		validateGridFSFile(fileKey4, fromVersionLabel, dbObject2);
	}

	@Test
	public void testGetFileAsStream() throws Exception {

		String fileName = "myDirectory/test.txt";

		String versionLabel1 = GridFSStore.VERSION_DEFAULT;
		String versionLabel2 = "2.0";

		String fileKey1 = getFileKey(
			_companyId, _repositoryId, fileName, versionLabel1);
		String fileKey2 = getFileKey(
			_companyId, _repositoryId, fileName, versionLabel2);

		doAddFile(
			fileKey1, fileName, versionLabel1,
			new ByteArrayInputStream(_testFileText.getBytes()));

		doAddFile(
			fileKey2, fileName, versionLabel2,
			new ByteArrayInputStream(
				_testFileText.concat(_testFileText).getBytes()));

		//begin test case
		GridFSStore gridFSStore = new GridFSStore();

		InputStream inputStream = gridFSStore.getFileAsStream(
			_companyId, _repositoryId, fileName, versionLabel1);

		ByteArrayOutputStream baos = new ByteArrayOutputStream(25000);

		StreamUtil.copy(inputStream, baos);

		String results = new String(baos.toByteArray());

		Assert.assertEquals(
			"File sizes are not the same", _testFileText.length(),
			results.length());

		Assert.assertNotSame(
			"Files are the same", _testFileText.concat(_testFileText), results);

		Assert.assertEquals("Files are not the same", _testFileText, results);
	}

	@Test
	public void testGetFileAsStreamWithNoVersion() throws Exception {

		String fileName = "myDirectory/test.txt";

		String versionLabel1 = GridFSStore.VERSION_DEFAULT;
		String versionLabel2 = "2.0";

		String fileKey1 = getFileKey(
			_companyId, _repositoryId, fileName, versionLabel1);
		String fileKey2 = getFileKey(
			_companyId, _repositoryId, fileName, versionLabel2);

		doAddFile(
			fileKey1, fileName, versionLabel1,
			new ByteArrayInputStream(_testFileText.getBytes()));

		doAddFile(
			fileKey2, fileName, versionLabel2,
			new ByteArrayInputStream(
				_testFileText.concat(_testFileText).getBytes()));

		//begin test case
		GridFSStore gridFSStore = new GridFSStore();

		InputStream inputStream = gridFSStore.getFileAsStream(
			_companyId, _repositoryId, fileName, null);

		ByteArrayOutputStream baos = new ByteArrayOutputStream(25000);

		StreamUtil.copy(inputStream, baos);

		String results = new String(baos.toByteArray());

		Assert.assertEquals(
			"File sizes are not the same", _testFileText.length() * 2,
			results.length());

		Assert.assertNotSame("Files are the same", _testFileText, results);

		Assert.assertEquals(
			"Files are not the same", _testFileText.concat(_testFileText),
			results);
	}

	@Test
	public void testGetFileAsStreamWithNoFile() throws Exception {

		String fileName1 = "myDirectory/test.txt";
		String fileName2 = "myDirectory/test1.txt";

		String versionLabel1 = GridFSStore.VERSION_DEFAULT;
		String versionLabel2 = "2.0";

		String fileKey1 = getFileKey(
			_companyId, _repositoryId, fileName1, versionLabel1);
		String fileKey2 = getFileKey(
			_companyId, _repositoryId, fileName1, versionLabel2);

		doAddFile(
			fileKey1, fileName1, versionLabel1,
			new ByteArrayInputStream(_testFileText.getBytes()));

		doAddFile(
			fileKey2, fileName2, versionLabel2,
			new ByteArrayInputStream(
				_testFileText.concat(_testFileText).getBytes()));

		//begin test case
		GridFSStore gridFSStore = new GridFSStore();

		try {
			//test for head version
			gridFSStore.getFileAsStream(
				_companyId, _repositoryId, fileName2, null);
		}
		catch (NoSuchFileException e) {
			Assert.assertTrue(
				"Bad error message", e.getMessage().contains(fileName2));

			return;
		}

		//test for default version
		try {
			gridFSStore.getFileAsStream(
				_companyId, _repositoryId, fileName2, versionLabel1);
		}
		catch (NoSuchFileException e) {
			Assert.assertTrue(
				"Bad error message", e.getMessage().contains(fileName2));
			Assert.assertTrue(
				"Bad error message", e.getMessage().contains(versionLabel1));

			return;
		}

		//test for specified version
		try {
			gridFSStore.getFileAsStream(
				_companyId, _repositoryId, fileName1, "3.0");
		}
		catch (NoSuchFileException e) {
			Assert.assertTrue(
				"Bad error message", e.getMessage().contains(fileName1));
			Assert.assertTrue(
				"Bad error message", e.getMessage().contains("3.0"));

			return;
		}

		Assert.fail("Should have received a NoSuchFileException");
	}

	@Test
	public void testGetFileNames() throws Exception {
		String fileName1 = "myDirectory/test.txt";
		String fileName2 = "myDirectory/test2.txt";
		String fileName3 = "2myDirectory/test3.txt";
		String fileName4 = "myDirectory2/test3.txt";
		String fileName5 = "/myDirectory/test3.txt";

		Set<String> expectedFileNames = new HashSet<String>();
		expectedFileNames.add(fileName1);
		expectedFileNames.add(fileName2);
		expectedFileNames.add(fileName3);
		expectedFileNames.add(fileName4);

		String fromVersionLabel = GridFSStore.VERSION_DEFAULT;

		String fileKey1 = getFileKey(
			_companyId, _repositoryId, fileName1, fromVersionLabel);
		String fileKey2 = getFileKey(
			_companyId, _repositoryId, fileName2, fromVersionLabel);
		String fileKey3 = getFileKey(
			_companyId, _repositoryId, fileName3, fromVersionLabel);
		String fileKey4 = getFileKey(
			_companyId, _repositoryId, fileName4, fromVersionLabel);
		String fileKey5 = getFileKey(
			_companyId + 1, _repositoryId + 1, fileName5, fromVersionLabel);

		doAddFile(
			fileKey1, fileName1, fromVersionLabel,
			new ByteArrayInputStream(_testFileText.getBytes()));
		doAddFile(
			fileKey2, fileName2, fromVersionLabel,
			new ByteArrayInputStream(_testFileText.getBytes()));
		doAddFile(
			fileKey3, fileName3, fromVersionLabel,
			new ByteArrayInputStream(_testFileText.getBytes()));
		doAddFile(
			fileKey4, fileName4, fromVersionLabel,
			new ByteArrayInputStream(_testFileText.getBytes()));
		doAddFile(
			_companyId + 1, _repositoryId + 1, fileKey5, fileName5,
			fromVersionLabel,
			new ByteArrayInputStream(_testFileText.getBytes()));

		//begin test case
		GridFSStore gridFSStore = new GridFSStore();

		String[] fileNames = gridFSStore.getFileNames(
			_companyId, _repositoryId);

		Assert.assertEquals("Write number of files", 4, fileNames.length);

		for (String fileName : fileNames) {
			Assert.assertTrue(
				"File not expected", expectedFileNames.contains(fileName));
		}

	}

	@Test
	public void testGetFileNamesInDirectory() throws Exception {
		String fileName1 = "myDirectory/test.txt";
		String fileName2 = "myDirectory/test2.txt";
		String fileName3 = "2myDirectory/test3.txt";
		String fileName4 = "myDirectory2/test3.txt";
		String fileName5 = "/myDirectory/test3.txt";

		String directoryName = "myDirectory";

		Set<String> expectedFileNames = new HashSet<String>();
		expectedFileNames.add(fileName1);
		expectedFileNames.add(fileName2);

		String fromVersionLabel = GridFSStore.VERSION_DEFAULT;

		String fileKey1 = getFileKey(
			_companyId, _repositoryId, fileName1, fromVersionLabel);
		String fileKey2 = getFileKey(
			_companyId, _repositoryId, fileName2, fromVersionLabel);
		String fileKey3 = getFileKey(
			_companyId, _repositoryId, fileName3, fromVersionLabel);
		String fileKey4 = getFileKey(
			_companyId, _repositoryId, fileName4, fromVersionLabel);
		String fileKey5 = getFileKey(
			_companyId + 1, _repositoryId + 1, fileName5, fromVersionLabel);

		doAddFile(
			fileKey1, fileName1, fromVersionLabel,
			new ByteArrayInputStream(_testFileText.getBytes()));
		doAddFile(
			fileKey2, fileName2, fromVersionLabel,
			new ByteArrayInputStream(_testFileText.getBytes()));
		doAddFile(
			fileKey3, fileName3, fromVersionLabel,
			new ByteArrayInputStream(_testFileText.getBytes()));
		doAddFile(
			fileKey4, fileName4, fromVersionLabel,
			new ByteArrayInputStream(_testFileText.getBytes()));
		doAddFile(
			_companyId + 1, _repositoryId + 1, fileKey5, fileName5,
			fromVersionLabel,
			new ByteArrayInputStream(_testFileText.getBytes()));

		//begin test case
		GridFSStore gridFSStore = new GridFSStore();

		String[] fileNames = gridFSStore.getFileNames(
			_companyId, _repositoryId, directoryName);

		Assert.assertEquals("Write number of files", 2, fileNames.length);

		for (String fileName : fileNames) {
			Assert.assertTrue(
				"File not expected", expectedFileNames.contains(fileName));
		}
	}

	@Test
	public void testGetFileSize() throws Exception {
		String fileName1 = "myDirectory/test.txt";
		String fileName2 = "myDirectory/test2.txt";

		String fromVersionLabel = GridFSStore.VERSION_DEFAULT;

		String fileKey1 = getFileKey(
			_companyId, _repositoryId, fileName1, fromVersionLabel);
		String fileKey2 = getFileKey(
			_companyId, _repositoryId, fileName2, fromVersionLabel);

		doAddFile(
			fileKey1, fileName1, fromVersionLabel,
			new ByteArrayInputStream(
				_testFileText.concat(_testFileText).getBytes()));
		doAddFile(
			fileKey2, fileName2, fromVersionLabel,
			new ByteArrayInputStream(_testFileText.getBytes()));

		//begin test case
		GridFSStore gridFSStore = new GridFSStore();

		long file1Size = gridFSStore.getFileSize(
			_companyId, _repositoryId, fileName1);

		Assert.assertEquals(
			"File size not expected",
			_testFileText.concat(_testFileText).length(), file1Size);

		long file2Size = gridFSStore.getFileSize(
			_companyId, _repositoryId, fileName2);

		Assert.assertEquals(
			"File size not expected",_testFileText.length(), file2Size);
	}

	@Test
	public void testGetFileSizeWithInvalidFile() throws Exception {
		String fileName1 = "myDirectory/test.txt";
		String fileName3 = "2myDirectory/test3.txt";

		String fromVersionLabel = GridFSStore.VERSION_DEFAULT;

		String fileKey1 = getFileKey(
			_companyId, _repositoryId, fileName1, fromVersionLabel);

		doAddFile(
			fileKey1, fileName1, fromVersionLabel,
			new ByteArrayInputStream(
				_testFileText.concat(_testFileText).getBytes()));

		//begin test case
		GridFSStore gridFSStore = new GridFSStore();

		try {
			gridFSStore.getFileSize(_companyId, _repositoryId, fileName3);
		}
		catch (NoSuchFileException e) {
			Assert.assertTrue(
				"Bad error message", e.getMessage().contains(fileName3));

			return;
		}

		Assert.fail("Should have received a NoSuchFileException");
	}

	@Test
	public void testHasFile() throws Exception {
		String fileName1 = "myDirectory/test.txt";
		String fileName2 = "myDirectory/test2.txt";

		String versionLabel = GridFSStore.VERSION_DEFAULT;

		String fileKey1 = getFileKey(
			_companyId, _repositoryId, fileName1, versionLabel);

		doAddFile(
			fileKey1, fileName1, versionLabel,
			new ByteArrayInputStream(_testFileText.getBytes()));

		//begin test case
		GridFSStore gridFSStore = new GridFSStore();

		boolean hasFile1 = gridFSStore.hasFile(
			_companyId, _repositoryId, fileName1);

		Assert.assertTrue("File should be present", hasFile1);

		boolean hasFile2 = gridFSStore.hasFile(
			_companyId, _repositoryId, fileName2);

		Assert.assertFalse("File should not be present", hasFile2);
	}

	@Test
	public void testHasFileWithVersion() throws Exception {
		String fileName1 = "myDirectory/test.txt";
		String fileName2 = "myDirectory/test2.txt";

		String versionLabel1 = GridFSStore.VERSION_DEFAULT;
		String versionLabel2 = "2.0";

		String fileKey1 = getFileKey(
			_companyId, _repositoryId, fileName1, versionLabel1);
		String fileKey2 = getFileKey(
			_companyId, _repositoryId, fileName1, versionLabel2);

		doAddFile(
			fileKey1, fileName1, versionLabel1,
			new ByteArrayInputStream(
				_testFileText.concat(_testFileText).getBytes()));
		doAddFile(
			fileKey2, fileName1, versionLabel2,
			new ByteArrayInputStream(_testFileText.getBytes()));

		//begin test case
		GridFSStore gridFSStore = new GridFSStore();

		boolean hasFile1 = gridFSStore.hasFile(
			_companyId, _repositoryId, fileName1, versionLabel1);

		Assert.assertTrue("File should be present", hasFile1);

		boolean hasFile2 = gridFSStore.hasFile(
			_companyId, _repositoryId, fileName1, versionLabel2);

		Assert.assertTrue("File should be present", hasFile2);

		boolean hasFile3 = gridFSStore.hasFile(
			_companyId, _repositoryId, fileName2, versionLabel1);

		Assert.assertFalse("File should not be present", hasFile3);
	}

	@Test
	public void testUpdateFileNewRepositoryId() throws Exception {
		String fileName1 = "myDirectory/test.txt";
		String fileName3 = "myDirectory/test2.txt";

		String versionLabel1 = GridFSStore.VERSION_DEFAULT;
		String versionLabel2 = "2.0";

		long newRepositoryId = _repositoryId + 1;

		String fileKey1 = getFileKey(
			_companyId, _repositoryId, fileName1, versionLabel1);
		String fileKey2 = getFileKey(
			_companyId, _repositoryId, fileName1, versionLabel2);
		String fileKey3 = getFileKey(
			_companyId, _repositoryId, fileName3, versionLabel1);

		String newFileKey1 = getFileKey(
			_companyId, newRepositoryId, fileName1, versionLabel1);
		String newFileKey2 = getFileKey(
			_companyId, newRepositoryId, fileName1, versionLabel2);

		doAddFile(
			fileKey1, fileName1, versionLabel1,
			new ByteArrayInputStream(
				_testFileText.concat(_testFileText).getBytes()));
		doAddFile(
			fileKey2, fileName1, versionLabel2,
			new ByteArrayInputStream(_testFileText.getBytes()));
		doAddFile(
			fileKey3, fileName3, versionLabel1,
			new ByteArrayInputStream(_testFileText.getBytes()));

		//begin test case
		GridFSStore gridFSStore = new GridFSStore();

		gridFSStore.updateFile(
			_companyId, _repositoryId, newRepositoryId, fileName1);

		//validate
		GridFS gridFS = new GridFS(
			MongoDBUtil.getDB(_companyId), String.valueOf(_repositoryId));

		DBCursor dbCursor = gridFS.getFileList();

		Assert.assertEquals("Should contain 1 file", 1, dbCursor.size());

		DBObject dbObject = dbCursor.next();

		validateGridFSFile(
			fileKey3, versionLabel1, _testFileText.length(), dbObject);

		GridFS gridFS2 = new GridFS(
			MongoDBUtil.getDB(_companyId), String.valueOf(newRepositoryId));

		DBCursor dbCursor2 = gridFS2.getFileList();

		Assert.assertEquals("Should contain 2 files", 2, dbCursor2.size());

		DBObject dbObject2 = dbCursor2.next();

		validateGridFSFile(
			newFileKey1, versionLabel1, _testFileText.length() * 2, dbObject2);

		DBObject dbObject3 = dbCursor2.next();

		validateGridFSFile(newFileKey2, versionLabel2, dbObject3);
	}

	@Test
	public void testUpdateFileNewRepositoryIdWithInvalidFile()
		throws Exception {

		String fileName1 = "myDirectory/test.txt";
		String fileName3 = "myDirectory/test2.txt";

		String versionLabel1 = GridFSStore.VERSION_DEFAULT;

		long newRepositoryId = _repositoryId + 1;

		String fileKey1 = getFileKey(
			_companyId, _repositoryId, fileName1, versionLabel1);

		doAddFile(
			fileKey1, fileName1, versionLabel1,
			new ByteArrayInputStream(_testFileText.getBytes()));

		//begin test case
		GridFSStore gridFSStore = new GridFSStore();

		try {
			gridFSStore.updateFile(
				_companyId, _repositoryId, newRepositoryId, fileName3);
		}
		catch (NoSuchFileException e) {
			Assert.assertTrue(
				"Bad error message", e.getMessage().contains(fileName3));

			GridFS gridFS = new GridFS(
				MongoDBUtil.getDB(_companyId), String.valueOf(_repositoryId));

			DBCursor dbCursor = gridFS.getFileList();

			Assert.assertEquals("Should contain 1 file", 1, dbCursor.size());

			DBObject dbObject = dbCursor.next();

			validateGridFSFile(fileKey1, versionLabel1, dbObject);

			return;
		}

		Assert.fail("Should have received a NoSuchFileException");
	}

	@Test
	public void testUpdateFileNewFileName() throws Exception {
		String fileName1 = "myDirectory/test.txt";
		String fileName3 = "myDirectory/test2.txt";
		String newFileName = "myDirectory/newTest.txt";

		String versionLabel1 = GridFSStore.VERSION_DEFAULT;
		String versionLabel2 = "2.0";

		String fileKey1 = getFileKey(
			_companyId, _repositoryId, fileName1, versionLabel1);
		String fileKey2 = getFileKey(
			_companyId, _repositoryId, fileName1, versionLabel2);
		String fileKey3 = getFileKey(
			_companyId, _repositoryId, fileName3, versionLabel1);

		String newFileKey1 = getFileKey(
			_companyId, _repositoryId, newFileName, versionLabel1);
		String newFileKey2 = getFileKey(
			_companyId, _repositoryId, newFileName, versionLabel2);

		doAddFile(
			fileKey1, fileName1, versionLabel1,
			new ByteArrayInputStream(
				_testFileText.concat(_testFileText).getBytes()));
		doAddFile(
			fileKey2, fileName1, versionLabel2,
			new ByteArrayInputStream(_testFileText.getBytes()));
		doAddFile(
			fileKey3, fileName3, versionLabel1,
			new ByteArrayInputStream(_testFileText.getBytes()));

		//begin test case
		GridFSStore gridFSStore = new GridFSStore();

		gridFSStore.updateFile(
			_companyId, _repositoryId, fileName1, newFileName);

		//validate
		GridFS gridFS = new GridFS(
			MongoDBUtil.getDB(_companyId), String.valueOf(_repositoryId));

		DBCursor dbCursor = gridFS.getFileList();

		Assert.assertEquals("Should contain 3 files", 3, dbCursor.size());

		DBObject dbObject1 = dbCursor.next();

		validateGridFSFile(
			newFileKey1, versionLabel1, _testFileText.length() * 2, dbObject1);

		DBObject dbObject2 = dbCursor.next();

		validateGridFSFile(newFileKey2, versionLabel2, dbObject2);

		DBObject dbObject3 = dbCursor.next();

		validateGridFSFile(fileKey3, versionLabel1, dbObject3);
	}

	@Test
	public void testUpdateFileNewFileNameWithInvalidFile() throws Exception {
		String fileName1 = "myDirectory/test.txt";
		String fileName3 = "myDirectory/test2.txt";
		String newFileName = "myDirectory/newTest.txt";

		String versionLabel1 = GridFSStore.VERSION_DEFAULT;

		String fileKey1 = getFileKey(
			_companyId, _repositoryId, fileName1, versionLabel1);

		doAddFile(
			fileKey1, fileName1, versionLabel1,
			new ByteArrayInputStream(_testFileText.getBytes()));

		//begin test case
		GridFSStore gridFSStore = new GridFSStore();

		try {
			gridFSStore.updateFile(
				_companyId, _repositoryId, fileName3, newFileName);
		}
		catch (NoSuchFileException e) {
			Assert.assertTrue(
				"Bad error message", e.getMessage().contains(fileName3));

			GridFS gridFS = new GridFS(
				MongoDBUtil.getDB(_companyId), String.valueOf(_repositoryId));

			DBCursor dbCursor = gridFS.getFileList();

			Assert.assertEquals("Should contain 1 file", 1, dbCursor.size());

			DBObject dbObject = dbCursor.next();

			validateGridFSFile(fileKey1, versionLabel1, dbObject);

			return;
		}

		Assert.fail("Should have received a NoSuchFileException");
	}

	@Test
	public void testUpdateFileNewInputStream() throws Exception {
		String fileName1 = "myDirectory/test.txt";
		String fileName3 = "myDirectory/test2.txt";

		String versionLabel1 = GridFSStore.VERSION_DEFAULT;
		String versionLabel2 = "2.0";

		String fileKey1 = getFileKey(
			_companyId, _repositoryId, fileName1, versionLabel1);
		String fileKey2 = getFileKey(
			_companyId, _repositoryId, fileName1, versionLabel2);
		String fileKey3 = getFileKey(
			_companyId, _repositoryId, fileName3, versionLabel1);

		doAddFile(
			fileKey1, fileName1, versionLabel1,
			new ByteArrayInputStream(
				_testFileText.concat(_testFileText).getBytes()));
		doAddFile(
			fileKey3, fileName3, versionLabel1,
			new ByteArrayInputStream(_testFileText.getBytes()));

		//begin test case
		GridFSStore gridFSStore = new GridFSStore();

		gridFSStore.updateFile(
			_companyId, _repositoryId, fileName1, versionLabel2,
			new ByteArrayInputStream(_testFileText.getBytes()));

		//validate
		GridFS gridFS = new GridFS(
			MongoDBUtil.getDB(_companyId), String.valueOf(_repositoryId));

		DBCursor dbCursor = gridFS.getFileList();

		Assert.assertEquals("Should contain 3 files", 3, dbCursor.size());

		DBObject dbObject1 = dbCursor.next();

		validateGridFSFile(
			fileKey1, versionLabel1, _testFileText.length() * 2, dbObject1);

		DBObject dbObject2 = dbCursor.next();

		validateGridFSFile(fileKey2, versionLabel2, dbObject2);

		DBObject dbObject3 = dbCursor.next();

		validateGridFSFile(fileKey3, versionLabel1, dbObject3);
	}

	@Test
	public void testUpdateFileNewInputStreamWithDuplicateVersion()
		throws Exception {

		String fileName1 = "myDirectory/test.txt";

		String versionLabel1 = GridFSStore.VERSION_DEFAULT;

		String fileKey1 = getFileKey(
			_companyId, _repositoryId, fileName1, versionLabel1);

		doAddFile(
			fileKey1, fileName1, versionLabel1,
			new ByteArrayInputStream(_testFileText.getBytes()));

		//begin test case
		GridFSStore gridFSStore = new GridFSStore();

		try {
			gridFSStore.updateFile(
				_companyId, _repositoryId, fileName1, versionLabel1,
				new ByteArrayInputStream(_testFileText.getBytes()));
		}
		catch (DuplicateFileException e) {
			Assert.assertTrue(
				"Bad error message", e.getMessage().contains(fileName1));

			GridFS gridFS = new GridFS(
				MongoDBUtil.getDB(_companyId), String.valueOf(_repositoryId));

			DBCursor dbCursor = gridFS.getFileList();

			Assert.assertEquals("Should contain 1 file", 1, dbCursor.size());

			DBObject dbObject = dbCursor.next();

			validateGridFSFile(fileKey1, versionLabel1, dbObject);

			return;
		}

		Assert.fail("Should have received a DuplicateFileException");
	}

	@Test
	public void testUpdateFileNewInputStreamWithNullVersion() throws Exception {
		String fileName1 = "myDirectory/test.txt";

		String versionLabel1 = GridFSStore.VERSION_DEFAULT;

		String fileKey1 = getFileKey(
			_companyId, _repositoryId, fileName1, versionLabel1);

		doAddFile(
			fileKey1, fileName1, versionLabel1,
			new ByteArrayInputStream(_testFileText.getBytes()));

		//begin test case
		GridFSStore gridFSStore = new GridFSStore();

		try {
			gridFSStore.updateFile(
				_companyId, _repositoryId, fileName1, null,
				new ByteArrayInputStream(_testFileText.getBytes()));
		}
		catch (InvalidFileVersionException e) {
			Assert.assertTrue(
				"Bad error message", e.getMessage().contains(fileName1));

			GridFS gridFS = new GridFS(
				MongoDBUtil.getDB(_companyId), String.valueOf(_repositoryId));

			DBCursor dbCursor = gridFS.getFileList();

			Assert.assertEquals("Should contain 1 file", 1, dbCursor.size());

			DBObject dbObject = dbCursor.next();

			validateGridFSFile(fileKey1, versionLabel1, dbObject);

			return;
		}

		Assert.fail("Should have received a DuplicateFileException");
	}

	@Test
	public void testUpdateFileVersion() throws Exception {
		String fileName1 = "myDirectory/test.txt";
		String fileName3 = "myDirectory/test2.txt";

		String versionLabel1 = GridFSStore.VERSION_DEFAULT;
		String versionLabel2 = "2.0";

		String newVersionLabel = "3.0";

		String fileKey1 = getFileKey(
			_companyId, _repositoryId, fileName1, versionLabel1);
		String fileKey2 = getFileKey(
			_companyId, _repositoryId, fileName1, versionLabel2);
		String fileKey3 = getFileKey(
			_companyId, _repositoryId, fileName3, versionLabel1);

		String newFileKey1 = getFileKey(
			_companyId, _repositoryId, fileName1, newVersionLabel);

		doAddFile(
			fileKey1, fileName1, versionLabel1,
			new ByteArrayInputStream(
				_testFileText.concat(_testFileText).getBytes()));
		doAddFile(
			fileKey2, fileName1, versionLabel2,
			new ByteArrayInputStream(_testFileText.getBytes()));
		doAddFile(
			fileKey3, fileName3, versionLabel1,
			new ByteArrayInputStream(_testFileText.getBytes()));

		//begin test case
		GridFSStore gridFSStore = new GridFSStore();

		gridFSStore.updateFileVersion(
			_companyId, _repositoryId, fileName1, versionLabel1,
			newVersionLabel);

		//validate
		GridFS gridFS = new GridFS(
			MongoDBUtil.getDB(_companyId), String.valueOf(_repositoryId));

		DBCursor dbCursor = gridFS.getFileList();

		Assert.assertEquals("Should contain 3 file", 3, dbCursor.size());

		DBObject dbObject = dbCursor.next();

		validateGridFSFile(fileKey2, versionLabel2, dbObject);

		DBObject dbObject2 = dbCursor.next();

		validateGridFSFile(
			newFileKey1, newVersionLabel, _testFileText.length() * 2,
			dbObject2);

		DBObject dbObject3 = dbCursor.next();

		validateGridFSFile(fileKey3, versionLabel1, dbObject3);
	}

	@Test
	public void testUpdateFileVersionWithBadFile() throws Exception {
		String fileName1 = "myDirectory/test.txt";
		String fileName3 = "myDirectory/test2.txt";

		String versionLabel1 = GridFSStore.VERSION_DEFAULT;
		String versionLabel2 = "2.0";

		String fileKey1 = getFileKey(
			_companyId, _repositoryId, fileName1, versionLabel1);
		String fileKey2 = getFileKey(
			_companyId, _repositoryId, fileName1, versionLabel2);

		doAddFile(
			fileKey1, fileName1, versionLabel1,
			new ByteArrayInputStream(
				_testFileText.concat(_testFileText).getBytes()));
		doAddFile(
			fileKey2, fileName1, versionLabel2,
			new ByteArrayInputStream(_testFileText.getBytes()));

		//begin test case
		GridFSStore gridFSStore = new GridFSStore();

		try {
			gridFSStore.updateFileVersion(
				_companyId, _repositoryId, fileName3, versionLabel1,
				versionLabel2);
		}
		catch (NoSuchFileException e) {
			Assert.assertTrue(
				"Bad error message", e.getMessage().contains(fileName3));

			GridFS gridFS = new GridFS(
				MongoDBUtil.getDB(_companyId), String.valueOf(_repositoryId));

			DBCursor dbCursor = gridFS.getFileList();

			Assert.assertEquals("Should contain 2 file", 2, dbCursor.size());

			return;
		}

		Assert.fail("Should have received a NoSuchFileException");
	}

	@Test
	public void testUpdateFileVersionWithNullFromVersion() throws Exception {
		String fileName1 = "myDirectory/test.txt";
		String fileName3 = "myDirectory/test2.txt";

		String versionLabel1 = GridFSStore.VERSION_DEFAULT;
		String versionLabel2 = "2.0";

		String newVersionLabel = "3.0";

		String fileKey1 = getFileKey(
			_companyId, _repositoryId, fileName1, versionLabel1);
		String fileKey2 = getFileKey(
			_companyId, _repositoryId, fileName1, versionLabel2);
		String fileKey3 = getFileKey(
			_companyId, _repositoryId, fileName3, versionLabel1);

		String newFileKey1 = getFileKey(
			_companyId, _repositoryId, fileName1, newVersionLabel);

		doAddFile(
			fileKey1, fileName1, versionLabel1,
			new ByteArrayInputStream(
				_testFileText.concat(_testFileText).getBytes()));
		doAddFile(
			fileKey2, fileName1, versionLabel2,
			new ByteArrayInputStream(_testFileText.getBytes()));
		doAddFile(
			fileKey3, fileName3, versionLabel1,
			new ByteArrayInputStream(_testFileText.getBytes()));

		//begin test case
		GridFSStore gridFSStore = new GridFSStore();

		gridFSStore.updateFileVersion(
			_companyId, _repositoryId, fileName1, null, newVersionLabel);

		//validate
		GridFS gridFS = new GridFS(
			MongoDBUtil.getDB(_companyId), String.valueOf(_repositoryId));

		DBCursor dbCursor = gridFS.getFileList();

		Assert.assertEquals("Should contain 3 file", 3, dbCursor.size());

		DBObject dbObject = dbCursor.next();

		validateGridFSFile(
			fileKey1, versionLabel1, _testFileText.length() * 2, dbObject);

		DBObject dbObject2 = dbCursor.next();

		validateGridFSFile(newFileKey1, newVersionLabel, dbObject2);

		DBObject dbObject3 = dbCursor.next();

		validateGridFSFile(fileKey3, versionLabel1, dbObject3);
	}

	@Test
	public void testUpdateFileVersionWithNullToVersion() throws Exception {
		String fileName1 = "myDirectory/test.txt";
		String fileName3 = "myDirectory/test2.txt";

		String versionLabel1 = GridFSStore.VERSION_DEFAULT;
		String versionLabel2 = "2.0";

		String fileKey1 = getFileKey(
			_companyId, _repositoryId, fileName1, versionLabel1);
		String fileKey2 = getFileKey(
			_companyId, _repositoryId, fileName1, versionLabel2);
		String fileKey3 = getFileKey(
			_companyId, _repositoryId, fileName3, versionLabel1);

		doAddFile(
			fileKey1, fileName1, versionLabel1,
			new ByteArrayInputStream(
				_testFileText.concat(_testFileText).getBytes()));
		doAddFile(
			fileKey2, fileName1, versionLabel2,
			new ByteArrayInputStream(_testFileText.getBytes()));
		doAddFile(
			fileKey3, fileName3, versionLabel1,
			new ByteArrayInputStream(_testFileText.getBytes()));

		//begin test case
		GridFSStore gridFSStore = new GridFSStore();

		try {
			gridFSStore.updateFileVersion(
				_companyId, _repositoryId, fileName1, versionLabel1, null);
		}
		catch (InvalidFileVersionException e) {
			Assert.assertTrue(
				"Bad error message", e.getMessage().contains(fileName1));
			Assert.assertTrue(
				"Bad error message", e.getMessage().contains("null"));

			GridFS gridFS = new GridFS(
				MongoDBUtil.getDB(_companyId), String.valueOf(_repositoryId));

			DBCursor dbCursor = gridFS.getFileList();

			Assert.assertEquals("Should contain 3 file", 3, dbCursor.size());

			return;
		}

		Assert.fail("Should have received a InvalidFileVersionException");
	}

	@Before
	public void setUp() throws Exception {
		MongoDBUtil mongoDBUtil = new MongoDBUtil();
		mongoDBUtil.setMongDB(new TestingMongoDBImpl());

		_companyId = 10519;
		_repositoryId = 20534;

		_testFileText =
			"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	}

	@After
	public void tearDown() throws Exception {
		GridFS gridFS = new GridFS(
			MongoDBUtil.getDB(_companyId), String.valueOf(_repositoryId));

		deleteAllFiles(gridFS);

		GridFS gridFS2 = new GridFS(
			MongoDBUtil.getDB(_companyId), String.valueOf(_repositoryId + 1));

		deleteAllFiles(gridFS2);
	}

	protected void deleteAllFiles(GridFS gridFS) {
		DBCursor dbCursor = gridFS.getFileList();

		while (dbCursor.hasNext()) {
			DBObject dbObject = dbCursor.next();

			String fileName = (String) dbObject.get("filename");

			gridFS.remove(fileName);
		}
	}

	protected List<GridFSDBFile> getFiles(String fileName) {
		GridFS gridFS = new GridFS(
			MongoDBUtil.getDB(_companyId), String.valueOf(_repositoryId));

		String fileKey = getFileKey(_companyId, _repositoryId, fileName, null);

		String regex = fileKey.concat("/.*");

		BasicDBObject queryDBObject = new BasicDBObject();

		Pattern regexPattern = Pattern.compile(regex);
		queryDBObject.put("filename", regexPattern);

		List<GridFSDBFile> gridFSDBFiles = gridFS.find(queryDBObject);

		return gridFSDBFiles;
	}

	protected void doAddFile(
		String fileKey, String fileName, String version, InputStream is) {

		doAddFile(_companyId, _repositoryId, fileKey, fileName, version, is);
	}

	protected void doAddFile(
		long companyId, long repositoryId, String fileKey, String fileName,
		String version, InputStream is) {

		GridFS gridFS = new GridFS(
			MongoDBUtil.getDB(companyId), String.valueOf(repositoryId));

		GridFSInputFile gridFSInputFile = gridFS.createFile(is, fileKey, true);

		gridFSInputFile.put("companyId", _companyId);
		gridFSInputFile.put("repositoryId", _repositoryId);
		gridFSInputFile.put("originalFileName", fileName);

		gridFSInputFile.put("version", version);

		gridFSInputFile.save();

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

	protected void validateGridFSFile(
		String expectedFileName, String expectedVersion, DBObject dbObject) {

		validateGridFSFile(
			expectedFileName, expectedVersion, _testFileText.length(),
			dbObject);

	}

	protected void validateGridFSFile(
		String expectedFileName, String expectedVersion, int expectedLength,
		DBObject dbObject) {

		Assert.assertEquals(
			"File name invalid", expectedFileName, dbObject.get("filename"));

		long toLength = (Long)dbObject.get("length");

		Assert.assertEquals("File length mismatch", expectedLength, toLength);

		Assert.assertEquals(
			"Version mismatch", expectedVersion, dbObject.get("version"));
	}

	private long _companyId;
	private long _repositoryId;
	private String _testFileText;
}
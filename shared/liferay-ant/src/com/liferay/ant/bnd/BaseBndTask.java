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

package com.liferay.ant.bnd;

import aQute.bnd.ant.BndTask;
import aQute.bnd.build.Project;
import aQute.bnd.build.Workspace;
import aQute.bnd.service.IndexProvider;
import aQute.bnd.service.RepositoryPlugin;

import java.io.File;

import java.util.Properties;

import org.apache.tools.ant.BuildException;

/**
 * @author Raymond Augé
 */
public abstract class BaseBndTask extends BndTask {

	@Override
	public void execute() throws BuildException {
		try {
			project = getProject();

			doBeforeExecute();
			doExecute();
		}
		catch (Exception e) {
			if (e instanceof BuildException) {
				throw (BuildException)e;
			}

			throw new BuildException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Project getBndProject() throws Exception {
		File bndRootFile = getBndRootFile();

		Workspace workspace = Workspace.getWorkspace(
			bndRootFile.getParentFile(), getBndDirName());

		workspace.setProperties(bndRootFile);

		Properties properties = new Properties();

		properties.putAll(project.getProperties());
		properties.putAll(workspace.getProperties());

		if (properties.containsKey("-baseline")) {
			baselineProperty = (String)properties.remove("-baseline");
		}

		workspace.setProperties(properties);

		Project bndProject = new Project(
			workspace, bndRootFile.getParentFile());

		bndProject.setFileMustExist(true);

		for (RepositoryPlugin plugin : workspace.getRepositories()) {
			if (plugin instanceof IndexProvider) {
				IndexProvider indexProvider = (IndexProvider)plugin;

				indexProvider.getIndexLocations();
			}
		}

		return bndProject;
	}

	public File getBndRootFile() {
		return _bndRootFile;
	}

	public void setBndRootFile(File bndRootFile) {
		_bndRootFile = bndRootFile;
	}

	protected void doBeforeExecute() throws Exception {
		if ((_bndRootFile == null) || !_bndRootFile.exists() ||
			_bndRootFile.isDirectory()) {

			if (_bndRootFile != null) {
				project.log(
					"bndRootFile is either missing or is a directory " +
						_bndRootFile.getAbsolutePath(),
					org.apache.tools.ant.Project.MSG_ERR);
			}

			throw new Exception("bndRootFile is invalid");
		}

		_bndRootFile = _bndRootFile.getAbsoluteFile();

		File rootDir = _bndRootFile.getParentFile();

		if (!rootDir.canWrite()) {
			return;
		}

		File bndDir = new File(rootDir, getBndDirName());

		if (!bndDir.exists() && !bndDir.mkdir()) {
			return;
		}

		File buildFile = new File(bndDir, "build.bnd");

		if (buildFile.exists() || !bndDir.canWrite()) {
			return;
		}

		buildFile.createNewFile();
	}

	protected abstract void doExecute() throws Exception;

	protected String getBndDirName() {
		if (_bndDirName != null) {
			return _bndDirName;
		}

		_bndDirName = project.getProperty("baseline.jar.bnddir.name");

		if (_bndDirName == null) {
			_bndDirName = _BND_DIR;
		}

		return _bndDirName;
	}

	protected String baselineProperty = null;
	protected org.apache.tools.ant.Project project;

	private static final String _BND_DIR = ".bnd";

	private String _bndDirName;
	private File _bndRootFile;

}
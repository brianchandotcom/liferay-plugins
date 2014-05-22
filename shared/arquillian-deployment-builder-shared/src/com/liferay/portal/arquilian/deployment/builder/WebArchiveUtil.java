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

package com.liferay.portal.arquilian.deployment.builder;

import java.io.File;
import java.io.IOException;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.junit.rules.TemporaryFolder;

/**
 * @author Manuel de la Peña
 * @author Cristina González
 */
public class WebArchiveUtil {

	public static WebArchive createJarWebArchive() throws IOException {
		TemporaryFolder temporaryFolder = new TemporaryFolder();

		try {
			temporaryFolder.create();

			File rootFile = temporaryFolder.getRoot();

			Project project = getProject();

			project.setProperty("auto.deploy.dir", rootFile.getAbsolutePath());

			project.executeTarget("deploy");

			StringBuilder sb = new StringBuilder(4);

			sb.append(project.getProperty("plugin.name"));
			sb.append("-");
			sb.append(project.getProperty("plugin.full.version"));
			sb.append(".jar");

			File jarFile = new File(rootFile.getAbsolutePath(), sb.toString());

			return ShrinkWrap.createFromZipFile(WebArchive.class, jarFile);
		}
		finally {
			temporaryFolder.delete();
		}
	}

	public static WebArchive createWarWebArchive() throws IOException {
		TemporaryFolder temporaryFolder = new TemporaryFolder();

		try {
			temporaryFolder.create();

			File rootFile = temporaryFolder.getRoot();

			Project project = getProject();

			project.setProperty(
				"app.server.deploy.dir", rootFile.getAbsolutePath());
			project.setProperty("auto.deploy.unpack.war", "false");

			project.executeTarget("direct-deploy");

			File warFile = new File(
				rootFile.getAbsolutePath(),
				project.getProperty("plugin.name") + ".war");

			return ShrinkWrap.createFromZipFile(WebArchive.class, warFile);
		}
		finally {
			temporaryFolder.delete();
		}
	}

	protected static Project getProject() {
		File buildFile = new File("build.xml");

		Project project = new Project();

		DefaultLogger defaultLogger = new DefaultLogger() {

			@Override
			public void buildFinished(BuildEvent buildEvent) {
				System.out.println("[BUILD FINISHED]");
			}

			@Override
			public void buildStarted(BuildEvent buildEvent) {
				System.out.println("[BUILD STARTED]");
			}

			@Override
			public void messageLogged(BuildEvent buildEvent) {
				System.out.println(buildEvent.getMessage());
			}

		};

		defaultLogger.setErrorPrintStream(System.err);
		defaultLogger.setMessageOutputLevel(Project.MSG_INFO);
		defaultLogger.setOutputPrintStream(System.out);

		project.addBuildListener(defaultLogger);

		project.setUserProperty("ant.file", buildFile.getAbsolutePath());

		project.init();

		ProjectHelper projectHelper = ProjectHelper.getProjectHelper();

		project.addReference("ant.projectHelper", projectHelper);

		projectHelper.parse(project, buildFile);

		return project;
	}

}
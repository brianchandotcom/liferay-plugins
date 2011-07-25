/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.server.manager.servlet;

import com.liferay.portal.kernel.deploy.DeployManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jonathan Potter
 */
public class PluginsHandler {

	protected void create(HttpServletRequest request,
		Map<Object,Object> jsonResponse, String context) throws Exception {

		File tempFile = ServerManagerServlet.getFileItemTemp(request);

		if (tempFile == null) {
			return;
		}

		DeployManagerUtil.deploy(tempFile, context);

		boolean success = FileUtil.delete(tempFile);

		if (!success) {
			jsonResponse.put(ServerManagerServlet.JSON_SUCCESS_KEY, 1);
		}
	}

	protected void delete(HttpServletRequest request,
		Map<Object,Object> jsonResponse, String context) throws Exception {

		if (context == null) {
			return;
		}

		File contextDirectory =
			ServerManagerServlet.getDeployDirectory(context);

		if (!contextDirectory.exists()) {
			_log.error("Context directory not found: " +
				contextDirectory.getAbsolutePath());

			jsonResponse.put(ServerManagerServlet.JSON_SUCCESS_KEY, 1);
			return;
		}

		DeployManagerUtil.undeploy(contextDirectory.getName());
	}

	protected void read(HttpServletRequest request,
		Map<Object, Object> jsonResponse, String context) throws Exception {

		List<PluginPackage> installedPlugins =
			DeployManagerUtil.getInstalledPlugins();

		List<String> installedContexts = new ArrayList<String>();

		if (context == null) {
			for (PluginPackage plugin : installedPlugins) {
				installedContexts.add(plugin.getContext());
			}

			jsonResponse.put(
				ServerManagerServlet.JSON_OUTPUT_STREAM_KEY, installedContexts);
		}
		else {
			boolean installed = true;
			boolean started = true;

			PluginPackage installedPlugin = null;
			for (PluginPackage plugin : installedPlugins) {
				if (context.equalsIgnoreCase(plugin.getContext())) {
					installedPlugin = plugin;
					break;
				}
			}

			List<String> types = new ArrayList<String>();

			if (installedPlugin == null) {
				installed = false;
				started = false;
			} else {
				types = installedPlugin.getTypes();
			}

			Map<Object, Object> pluginInformation =
				new HashMap<Object, Object>();

			pluginInformation.put("installed", installed);
			pluginInformation.put("started", started);
			pluginInformation.put("types", types);

			jsonResponse.put(
				ServerManagerServlet.JSON_OUTPUT_STREAM_KEY, pluginInformation);
		}
	}

	protected void update(HttpServletRequest request,
		Map<Object,Object> jsonResponse, String context) throws Exception {

		if (context == null) {
			return;
		}

		File contextDirectory =
			ServerManagerServlet.getDeployDirectory(context);

		if (!contextDirectory.exists()) {
			_log.error("Context directory not found: " +
				contextDirectory.getAbsolutePath());

			jsonResponse.put(ServerManagerServlet.JSON_SUCCESS_KEY, 1);
			return;
		}

		File tempFile = ServerManagerServlet.getFileItemTemp(request);

		if (tempFile == null) {
			_log.error("Could not create temp file for uploaded plugin");

			jsonResponse.put(ServerManagerServlet.JSON_SUCCESS_KEY, 1);
			return;
		}

		// Unzip over current files
		FileUtil.unzip(tempFile, contextDirectory);

		// delete uneeded files
		File deleteInfo = new File(
			contextDirectory, "META-INF/liferay-partialapp-delete.props");

		if (deleteInfo.exists()) {
			BufferedReader reader =
				new BufferedReader(new FileReader(deleteInfo));

			String line;
			while ((line = reader.readLine()) != null) {
				File fileToDelete = new File(contextDirectory, line.trim());

				boolean success = FileUtil.delete(fileToDelete);

				if (!success) {
					_log.info("Could not delete file: " +
						fileToDelete.getAbsolutePath());

					jsonResponse.put(ServerManagerServlet.JSON_SUCCESS_KEY, 1);
				}
			}

			FileUtil.delete(deleteInfo);
		}

		// Liferay IDE handles redeploy so we don't worry about it
	}

	protected static Log _log = LogFactoryUtil.getLog(PluginsHandler.class);

}

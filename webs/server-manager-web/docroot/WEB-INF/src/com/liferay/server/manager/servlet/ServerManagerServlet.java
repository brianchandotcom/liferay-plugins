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

import com.google.gson.Gson;
import com.liferay.portal.kernel.deploy.DeployManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.util.SystemProperties;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * @author Jonathan Potter
 */
public class ServerManagerServlet extends HttpServlet {

	protected static FileItem getFileItem(HttpServletRequest request) {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);

		try {
			@SuppressWarnings("unchecked")
			List<FileItem> fileItems = uploader.parseRequest(request);

			for (FileItem fileItem : fileItems) {
				if (!fileItem.isFormField()) {
					return fileItem;
				}
			}
		}
		catch (FileUploadException e) {
			_log.error(e);
		}

		return null;
	}

	protected static File getFileItemTemp(HttpServletRequest request)
		throws Exception {

		FileItem fileItem = getFileItem(request);

		if (fileItem == null) {
			return null;
		}

		UUID uuid = UUID.randomUUID();
		File systemTempDirectory =
			new File(SystemProperties.get(SystemProperties.TMP_DIR));
		File tempDirectory = new File(systemTempDirectory, uuid.toString());
		File tempFile = new File(tempDirectory, fileItem.getName());

		if (!tempFile.getParentFile().mkdirs()) {
			return null;
		}

		fileItem.write(tempFile);

		return tempFile;
	}

	protected static List<FileItem> getFileItems(HttpServletRequest request) {
		List<FileItem> fileItems = new ArrayList<FileItem>();

		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);

		try {
			@SuppressWarnings("unchecked")
			List<FileItem> uploadedItems = uploader.parseRequest(request);

			for (FileItem fileItem : uploadedItems) {
				if (!fileItem.isFormField()) {
					fileItems.add(fileItem);
				}
			}
		}
		catch (FileUploadException e) {
			_log.error(e);
		}

		return fileItems;
	}

	protected void debugPortHandler(
		HttpServletRequest request, Map<Object,Object> jsonResponse)
		throws IOException {

		String debugPort = null;

		// Get JVM arguments
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		List<String> jvmArguments = runtimeMXBean.getInputArguments();

		if (jvmArguments == null) {
			// Server was not started in debug mode
			jsonResponse.put(JSON_SUCCESS_KEY, 1);
			jsonResponse.put(
				JSON_ERROR_STREAM_KEY, "Server was not started in debug mode.");
			return;
		}

		for (String jvmArgument : jvmArguments) {
			if (jvmArgument.contains("agentlib:jdwp")) {
				Pattern pattern = Pattern.compile("address=(\\d+)");
				Matcher matcher = pattern.matcher(jvmArgument);

				if (matcher.find()) {
					debugPort = matcher.group(1);
				}
			}
		}

		if (debugPort == null) {
			jsonResponse.put(JSON_SUCCESS_KEY, 1);
		}
		else {
			jsonResponse.put(JSON_OUTPUT_STREAM_KEY, debugPort);
		}
	}

	protected void deployHandler(HttpServletRequest request, String[] path,
		Map<Object,Object> jsonResponse) throws Exception {

		String context = null;

		if (path.length >= 2) {
			context = path[1];
		}

		File tempFile = getFileItemTemp(request);

		if (tempFile == null) {
			return;
		}

		DeployManagerUtil.deploy(tempFile, context);

		boolean success = FileUtil.delete(tempFile);

		if (!success) {
			jsonResponse.put(JSON_SUCCESS_KEY, 1);
		}
	}

	protected void deployUpdateHandler(
		HttpServletRequest request, String[] path,
		Map<Object,Object> jsonResponse) throws Exception {

		if (path.length < 2) {
			return;
		}

		String context = path[1];
		String webappsDirectory = DeployManagerUtil.getDeployDir();
		File contextDirectory = new File(webappsDirectory, context);

		File tempFile = getFileItemTemp(request);

		if (tempFile == null) {
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

					jsonResponse.put(JSON_SUCCESS_KEY, 1);
				}
			}

			FileUtil.delete(deleteInfo);
		}

		// Liferay IDE handles redeploy so we don't worry about it
	}

	protected void handleDelete(HttpServletRequest request, String[] path,
		Map<Object,Object> jsonResponse) throws Exception {

		if (path.length == 0) {
			return;
		}

		if (path[0].equals("undeploy")) {
			undeployHandler(request, path, jsonResponse);
		}
	}

	protected void handleGet(HttpServletRequest request, String[] path,
		Map<Object,Object> jsonResponse) throws Exception {

		if (path.length == 0) {
			return;
		}

		if (path[0].equals("is-alive")) {
			isAliveHandler(request, jsonResponse);
		}
		else if (path[0].equals("log")) {
			logHandler(request, path, jsonResponse);
		}
		else if (path[0].equals("debug-port")) {
			debugPortHandler(request, jsonResponse);
		}
		else if (path[0].equals("plugins")) {
			pluginsHandler(request, path, jsonResponse);
		}
		else {
			unknownUrlHandler(request, path, jsonResponse);
		}
	}

	protected void handlePost(HttpServletRequest request, String[] path,
		Map<Object,Object> jsonResponse) throws Exception {

		if (path.length == 0) {
			return;
		}

		if (path[0].equals("deploy")) {
			deployHandler(request, path, jsonResponse);
		}
	}

	protected void handlePut(HttpServletRequest request, String[] path,
		Map<Object,Object> jsonResponse) throws Exception {

		if (path.length == 0) {
			return;
		}

		if (path[0].equals("deploy")) {
			deployUpdateHandler(request, path, jsonResponse);
		}
	}

	protected void isAliveHandler(
		HttpServletRequest request, Map<Object,Object> jsonResponse) {

		// Nothing to do, because the success flag on this request is all
		// that's needed to tell if the server is alive, and success is assumed
		// unless otherwise specified
	}

	protected void logHandler(HttpServletRequest request, String[] path,
		Map<Object,Object> jsonResponse) throws IOException {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String dateString = dateFormat.format(date);

		if (path.length < 2) {
			return;
		}

		File logFile = null;

		if (path[1].equalsIgnoreCase("sysout")) {
			logFile = new File(PropsUtil.get(PropsKeys.LIFERAY_HOME) +
				"/logs/liferay." + dateString + ".log");
		}
		else if (path[1].equalsIgnoreCase("syserr")) {
			logFile = new File(System.getProperty("catalina.base") +
				"/logs/catalina." + dateString + ".log");
		}
		else {
			return;
		}

		if (!logFile.exists()) {
			return;
		}

		int offset = 0;

		try {
			if (path.length >= 3) {
				offset = Integer.parseInt(path[2]);
			}
		}
		catch (NumberFormatException e) {
			// Just use default offset
		}

		BufferedReader reader = new BufferedReader(new FileReader(logFile));
		reader.skip(offset);

		StringWriter sw = new StringWriter();
		IOUtils.copy(reader, sw);

		jsonResponse.put(JSON_OUTPUT_STREAM_KEY, sw.toString());
	}

	protected void pluginsHandler(HttpServletRequest request, String[] path,
		Map<Object, Object> jsonResponse) throws Exception {

		List<PluginPackage> installedPlugins =
			DeployManagerUtil.getInstalledPlugins();

		List<String> installedContexts = new ArrayList<String>();

		if (path.length == 1) {
			for (PluginPackage plugin : installedPlugins) {
				installedContexts.add(plugin.getContext());
			}

			jsonResponse.put(JSON_OUTPUT_STREAM_KEY, installedContexts);
		}
		else if (path.length == 2) {
			boolean installed = true;
			boolean started = true;

			String context = path[1];

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

			jsonResponse.put(JSON_OUTPUT_STREAM_KEY, pluginInformation);
		}
		else {
			unknownUrlHandler(request, path, jsonResponse);
		}
	}

	@Override
	protected void service(
		HttpServletRequest request, HttpServletResponse httpResponse)
		throws ServletException, IOException {

		PrintWriter out = httpResponse.getWriter();
		String path = request.getServletPath().toLowerCase();

		// Remove first / so that split won't have empty strings
		if (path.startsWith("/")) {
			path = path.substring(1);
		}

		String[] pathPieces = path.split("/");

		httpResponse.setContentType("application/json");

		// Set up Json response
		Gson gson = new Gson();
		Map<Object,Object> jsonResponse = new HashMap<Object,Object>();
		jsonResponse.put(JSON_ERROR_STREAM_KEY, "");
		jsonResponse.put(JSON_SUCCESS_KEY, 0);
		jsonResponse.put(JSON_OUTPUT_STREAM_KEY, "");

		try {
			if (request.getMethod().equalsIgnoreCase("get")) {
				handleGet(request, pathPieces, jsonResponse);
			}
			else if (request.getMethod().equalsIgnoreCase("post")) {
				handlePost(request, pathPieces, jsonResponse);
			}
			else if (request.getMethod().equalsIgnoreCase("put")) {
				handlePut(request, pathPieces, jsonResponse);
			}
			else if (request.getMethod().equalsIgnoreCase("delete")) {
				handleDelete(request, pathPieces, jsonResponse);
			}
		}
		catch (Exception e) {
			_log.error(e);

			jsonResponse.put(
				JSON_ERROR_STREAM_KEY, ExceptionUtils.getFullStackTrace(e));
			jsonResponse.put(JSON_SUCCESS_KEY, 1);
		}

		String format = request.getParameter("format");

		if (format != null) {
			if (format.equalsIgnoreCase("raw")) {
				out.write(jsonResponse.get(JSON_OUTPUT_STREAM_KEY).toString());
			}
		}
		else {
			out.write(gson.toJson(jsonResponse));
		}

		out.flush();
		out.close();
	}

	protected void undeployHandler(HttpServletRequest request, String[] path,
		Map<Object,Object> jsonResponse) throws Exception {

		if (path.length < 2) {
			return;
		}

		String context = path[1];

		DeployManagerUtil.undeploy(context);
	}

	protected void unknownUrlHandler(HttpServletRequest request, String[] path,
		Map<Object,Object> jsonResponse) throws Exception {

		jsonResponse.put(JSON_ERROR_STREAM_KEY, "ERROR: unknown command. "
			+ "Check the documentation and enter a valid URL.");
	}

	private static final String JSON_ERROR_STREAM_KEY = "error-stream";
	private static final String JSON_OUTPUT_STREAM_KEY = "output-stream";
	private static final String JSON_SUCCESS_KEY = "success";
	private static final long serialVersionUID = 1L;

	protected static Log _log =
		LogFactoryUtil.getLog(ServerManagerServlet.class);

}

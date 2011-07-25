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
import com.liferay.util.SystemProperties;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * @author Jonathan Potter
 */
public class ServerManagerServlet extends HttpServlet {

	public static File getDeployDirectory (String context) throws Exception {
		String webappsDirectory = DeployManagerUtil.getDeployDir();
		File deployDirectory = new File(webappsDirectory, context);

		if (!deployDirectory.exists()) {
			File deployWarDirectory =
				new File(webappsDirectory, context + ".war");

			if (deployWarDirectory.exists()) {
				return deployWarDirectory;
			}
		}

		return deployDirectory;
	}

	public static FileItem getFileItem(HttpServletRequest request) {
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

	public static List<FileItem> getFileItems(HttpServletRequest request) {
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

	public static File getFileItemTemp(HttpServletRequest request)
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
	public static String shift(List<String> parameters) {
		if (parameters.isEmpty()) {
			return null;
		}

		String result = parameters.get(0);
		parameters.remove(0);

		return result;
	}
	public static void unknownUrlHandler(
		HttpServletRequest request, String[] path,
		Map<Object, Object> jsonResponse)
		throws Exception {

		jsonResponse.put(JSON_ERROR_STREAM_KEY, "ERROR: unknown command. "
			+ "Check the documentation and enter a valid URL.");
	}

	protected void mapUrl(
		HttpServletRequest request, Map<Object, Object> jsonResponse,
		List<String> pathPieces)
		throws Exception {

		if (!pathPieces.isEmpty()) {
			String category = shift(pathPieces);

			if (category.equalsIgnoreCase("plugins")) {
				String context = shift(pathPieces);
				PluginsHandler pluginsHandler = new PluginsHandler();

				if (request.getMethod().equalsIgnoreCase("get")) {
					pluginsHandler.read(request, jsonResponse, context);
				}
				else if (request.getMethod().equalsIgnoreCase("post")) {
					pluginsHandler.create(request, jsonResponse, context);
				}
				else if (request.getMethod().equalsIgnoreCase("put")) {
					pluginsHandler.update(request, jsonResponse, context);
				}
				else if (request.getMethod().equalsIgnoreCase("delete")) {
					pluginsHandler.delete(request, jsonResponse, context);
				}
			}
			else {
				String parameter1 = shift(pathPieces);
				String parameter2 = shift(pathPieces);
				GlobalHandler globalHandler = new GlobalHandler();

				if (category.equalsIgnoreCase("is-alive")) {
					globalHandler.isAlive(request, jsonResponse);
				}
				else if (category.equalsIgnoreCase("debug-port")) {
					globalHandler.debugPort(request, jsonResponse);
				}
				else if (category.equalsIgnoreCase("log")) {
					globalHandler.log(
						request, jsonResponse, parameter1, parameter2);
				}
			}
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

		String[] pathPiecesArray = path.split("/");
		List<String> pathPieces = new LinkedList<String>();

		for (String pathPiece : pathPiecesArray) {
			pathPieces.add(pathPiece);
		}

		httpResponse.setContentType("application/json");

		// Set up Json response
		Gson gson = new Gson();
		Map<Object,Object> jsonResponse = new HashMap<Object,Object>();
		jsonResponse.put(JSON_ERROR_STREAM_KEY, "");
		jsonResponse.put(JSON_SUCCESS_KEY, 0);
		jsonResponse.put(JSON_OUTPUT_STREAM_KEY, "");

		try {
			mapUrl(request, jsonResponse, pathPieces);
		}
		catch (Exception e) {
			_log.error(e);

			jsonResponse.put(
				JSON_ERROR_STREAM_KEY, ExceptionUtils.getFullStackTrace(e));
			jsonResponse.put(JSON_SUCCESS_KEY, 1);
		}

		// Handle global format
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

	public static final String JSON_ERROR_STREAM_KEY = "error-stream";
	public static final String JSON_OUTPUT_STREAM_KEY = "output-stream";
	public static final String JSON_SUCCESS_KEY = "success";

	private static final long serialVersionUID = 1L;

	protected static Log _log =
		LogFactoryUtil.getLog(ServerManagerServlet.class);

}

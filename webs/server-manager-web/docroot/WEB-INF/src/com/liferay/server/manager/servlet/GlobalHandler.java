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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ServerDetector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;

/**
 * @author Jonathan Potter
 */
public class GlobalHandler {

	protected void debugPort(
		HttpServletRequest request, Map<Object, Object> jsonResponse)
		throws Exception {

		String debugPort = null;

		// Get JVM arguments
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		List<String> jvmArguments = runtimeMXBean.getInputArguments();

		if (jvmArguments == null) {
			// Server was not started in debug mode
			jsonResponse.put(ServerManagerServlet.JSON_SUCCESS_KEY, 1);
			jsonResponse.put(
				ServerManagerServlet.JSON_ERROR_STREAM_KEY,
				"Server was not started in debug mode.");
			return;
		}

		String jvmArgumentContains = "transport=dt_socket";
		Pattern jvmArgumentPattern = Pattern.compile("address=(\\d+)");

		for (String jvmArgument : jvmArguments) {
			if (jvmArgument.contains(jvmArgumentContains)) {
				Matcher matcher = jvmArgumentPattern.matcher(jvmArgument);

				if (matcher.find()) {
					debugPort = matcher.group(1);
					break;
				}
			}
		}

		if (debugPort == null) {
			jsonResponse.put(ServerManagerServlet.JSON_SUCCESS_KEY, 1);
		}
		else {
			jsonResponse.put(
				ServerManagerServlet.JSON_OUTPUT_STREAM_KEY, debugPort);
		}
	}

	protected void isAlive(
		HttpServletRequest request, Map<Object, Object> jsonResponse) {

		// Nothing to do, because the success flag on this request is all
		// that's needed to tell if the server is alive, and success is assumed
		// unless otherwise specified
	}

	protected void log(
		HttpServletRequest request, Map<Object, Object> jsonResponse,
		String stream, String offsetString) throws IOException {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String dateString = dateFormat.format(date);

		if (stream == null) {
			return;
		}

		File logFile = null;

		// App server specific settings
		if (ServerDetector.isJBoss()) {
			File logDirectory =
				new File(System.getProperty("jboss.server.log.dir"));

			if (stream.equalsIgnoreCase("sysout")) {
				logFile = new File(logDirectory, "server.log");
			}
			else if (stream.equalsIgnoreCase("syserr")) {
				logFile = new File(logDirectory, "boot.log");
			}
		}
		else if (ServerDetector.isTomcat()) {
			if (stream.equalsIgnoreCase("syserr")) {
				logFile = new File(System.getProperty("catalina.base") +
					"/logs/catalina." + dateString + ".log");
			}
		}

		// Default settings
		if (logFile == null) {
			if (stream.equalsIgnoreCase("sysout")) {
				logFile = new File(PropsUtil.get(PropsKeys.LIFERAY_HOME) +
					"/logs/liferay." + dateString + ".log");
			} else {
				return;
			}
		}

		if (!logFile.exists()) {
			return;
		}

		int offset = 0;

		try {
			if (offsetString != null) {
				offset = Integer.parseInt(offsetString);
			}
		}
		catch (NumberFormatException e) {
			// Just use default offset
		}

		BufferedReader reader = new BufferedReader(new FileReader(logFile));
		reader.skip(offset);

		StringWriter sw = new StringWriter();
		IOUtils.copy(reader, sw);

		jsonResponse.put(
			ServerManagerServlet.JSON_OUTPUT_STREAM_KEY, sw.toString());
	}

	protected static Log _log = LogFactoryUtil.getLog(GlobalHandler.class);

}

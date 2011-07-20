
package com.liferay.server.manager.servlet;

import com.google.gson.Gson;
import com.liferay.portal.kernel.deploy.DeployManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.util.SystemProperties;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

public class ServerManagerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String JSON_OUTPUT_STREAM_KEY = "output-stream";
	private static final String JSON_SUCCESS_KEY = "success";
	private static final String JSON_ERROR_STREAM_KEY = "error-stream";

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

		// Set up Json
		Gson gson = new Gson();
		Map<String, String> jsonResponse = new HashMap<String, String>();
		jsonResponse.put(JSON_ERROR_STREAM_KEY, "");
		jsonResponse.put(JSON_SUCCESS_KEY, "0");
		jsonResponse.put(JSON_OUTPUT_STREAM_KEY, "");

		try {
			if (request.getMethod().equalsIgnoreCase("get")) {
				handleGet(request, httpResponse, pathPieces, jsonResponse);
			}
			else if (request.getMethod().equalsIgnoreCase("post")) {
				handlePost(request, httpResponse, pathPieces, jsonResponse);
			}
			else if (request.getMethod().equalsIgnoreCase("put")) {
				handlePut(request, httpResponse, pathPieces, jsonResponse);
			}
			else if (request.getMethod().equalsIgnoreCase("delete")) {
				handleDelete(request, httpResponse, pathPieces, jsonResponse);
			}
		} catch (Exception e) {
			_log.error(e);

			jsonResponse.put(
				JSON_ERROR_STREAM_KEY, ExceptionUtils.getFullStackTrace(e));
			jsonResponse.put(
				JSON_SUCCESS_KEY, "1");
		}

		out.write(gson.toJson(jsonResponse));
		out.flush();
		out.close();
	}

	protected void handleGet(
		HttpServletRequest request, HttpServletResponse httpResponse, String[] path, Map<String, String> jsonResponse)
						throws IOException {

		if (path.length == 0) {
			return;
		}

		if (path[0].equals("is-alive")) {
			isAliveHandler(request, httpResponse, jsonResponse);
		} else if (path[0].equals("log")) {
			logHandler(request, httpResponse, path, jsonResponse);
		} else if (path[0].equals("debug-port")) {
			debugPortHandler(request, httpResponse, jsonResponse);
		}
	}

	protected void handlePost(
		HttpServletRequest request, HttpServletResponse httpResponse, String[] path, Map<String, String> jsonResponse)
						throws Exception {

		if (path.length == 0) {
			return;
		}

		if (path[0].equals("deploy")) {
			deployHandler(request, httpResponse, path, jsonResponse);
		}
	}

	protected void handlePut(HttpServletRequest request, HttpServletResponse httpResponse, String[] path, Map<String, String> jsonResponse) throws Exception {
		if (path.length == 0) {
			return;
		}

		if (path[0].equals("deploy")) {
			deployUpdateHandler(request, httpResponse, path, jsonResponse);
		}
	}

	protected void handleDelete(HttpServletRequest request, HttpServletResponse httpResponse, String[] path, Map<String, String> jsonResponse) throws Exception {
		if (path.length == 0) {
			return;
		}

		if (path[0].equals("undeploy")) {
			undeployHandler(request, httpResponse, path, jsonResponse);
		}
	}

	protected void isAliveHandler(
		HttpServletRequest request, HttpServletResponse httpResponse, Map<String, String> jsonResponse) {

		// Nothing to do, because we assume success unless otherwise specified
	}

	protected void deployHandler(
		HttpServletRequest request, HttpServletResponse httpResponse, String[] path, Map<String, String> jsonResponse)
		throws Exception {

		String context = null;

		if (path.length >= 2) {
			context = path[1];
		}

		FileItem fileItem = getFileItem(request);

		if (fileItem == null) {
			return;
		}

		UUID uuid = UUID.randomUUID();
		File systemTempDirectory = new File(SystemProperties.get(SystemProperties.TMP_DIR));
		File tempDirectory = new File(systemTempDirectory, uuid.toString());
		File tempFile = new File(tempDirectory, fileItem.getName());

		FileUtil.touch(tempFile);

		fileItem.write(tempFile);

		DeployManagerUtil.deploy(tempFile, context);

		boolean success = FileUtils.deleteQuietly(tempFile);

		if (!success) {
			jsonResponse.put(JSON_SUCCESS_KEY, "1");
		}
	}

	protected void deployUpdateHandler(HttpServletRequest request, HttpServletResponse httpResponse, String[] path, Map<String, String> jsonResponse) throws Exception {
		if (path.length < 2) {
			return;
		}

		String context = path[1];
		String webappsDirectory = DeployManagerUtil.getDeployDir();
		File contextDirectory = new File(webappsDirectory, context);

		FileItem fileItem = getFileItem(request);

		if (fileItem == null) {
			return;
		}

		InputStream is = fileItem.getInputStream();

		ZipInputStream zis = new ZipInputStream(is);

		// unzip over current files
		decompressToDirectory(zis, contextDirectory);

		// delete uneeded files
		File deleteInfo = new File(contextDirectory, "META-INF/liferay-partialapp-delete.props");

		if (deleteInfo.exists()) {
			BufferedReader reader = new BufferedReader(new FileReader(deleteInfo));

			while (true) {
				String line = reader.readLine();

				if (line == null) {
					break;
				}

				File f = new File(contextDirectory, line.trim());

				boolean success = FileUtils.deleteQuietly(f);

				if (!success) {
					_log.info("Could not delete file: " + f.getAbsolutePath());
					jsonResponse.put(JSON_SUCCESS_KEY, "1");
				}
			}

			FileUtils.deleteQuietly(deleteInfo);
		}

		// Eclipse plugin handles redeploy so we don't worry about it
	}

	protected void undeployHandler(
		HttpServletRequest request, HttpServletResponse httpResponse, String[] path, Map<String, String> jsonResponse) throws Exception {

		if (path.length < 2) {
			return;
		}

		String context = path[1];

		DeployManagerUtil.undeploy(context);
	}

	protected void logHandler(HttpServletRequest request, HttpServletResponse httpResponse, String[] path, Map<String, String> jsonResponse) throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String dateString = dateFormat.format(date);

		if (path.length < 2) {
			return;
		}

		File log = null;

		if (path[1].equalsIgnoreCase("sysout")) {
			log = new File(PropsUtil.get(PropsKeys.LIFERAY_HOME) +
				"/logs/liferay." + dateString + ".log");
		} else if (path[1].equalsIgnoreCase("syserr")) {
			log = new File(System.getProperty("catalina.base") +
				"/logs/catalina." + dateString + ".log");
		} else {
			return;
		}

		if (!log.exists()) {
			return;
		}

		int offset = 0;

		try {
			if (path.length >= 3) {
				offset = Integer.parseInt(path[2]);
			}
		} catch (NumberFormatException e) {
			// Just use default offset
		}

		BufferedReader reader = new BufferedReader(new FileReader(log));
		reader.skip(offset);

		StringWriter sw = new StringWriter();
		IOUtils.copy(reader, sw);

		jsonResponse.put(JSON_OUTPUT_STREAM_KEY, sw.toString());
	}

	protected void debugPortHandler(HttpServletRequest request, HttpServletResponse httpResponse, Map<String, String> jsonResponse) throws IOException {
		String debugPort = null;

		// Get JVM arguments
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		List<String> options = runtimeMXBean.getInputArguments();

		if (options == null) {
			// Server was not started in debug mode
			jsonResponse.put(JSON_SUCCESS_KEY, "1");
			jsonResponse.put(JSON_ERROR_STREAM_KEY, "Server was not started in debug mode.");
			return;
		}

		for (String option : options) {
			if (option.contains("agentlib:jdwp")) {
				Pattern pattern = Pattern.compile("address=(\\d+)");
				Matcher matcher = pattern.matcher(option);

				if (matcher.find()) {
					debugPort = matcher.group(1);
				}
			}
		}

		if (debugPort == null) {
			jsonResponse.put(JSON_SUCCESS_KEY, "1");
		} else {
			jsonResponse.put(JSON_OUTPUT_STREAM_KEY, debugPort);
		}
	}

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
		} catch (FileUploadException e) {
			_log.error(e);
		}

		return null;
	}

	protected static List<FileItem> getFileItems(HttpServletRequest request) {
		List<FileItem> results = new ArrayList<FileItem>();

		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);

		try {
			@SuppressWarnings("unchecked")
			List<FileItem> fileItems = uploader.parseRequest(request);

			for (FileItem fileItem : fileItems) {
				if (!fileItem.isFormField()) {
					results.add(fileItem);
				}
			}
		} catch (FileUploadException e) {
			_log.error(e);
		}

		return results;
	}

	public static void decompressToDirectory(ZipInputStream is, File directory) throws IOException {
		while (true) {
			ZipEntry entry = is.getNextEntry();

			if (entry == null) {
				break;
			}

			if (!entry.isDirectory()) {
				toFile(is, new File(directory, entry.getName()));
			}
		}
	}

	public static void toFile(InputStream is, File f) throws IOException {
		FileUtils.touch(f);
		OutputStream os = new FileOutputStream(f);

		IOUtils.copy(is, os);
	}

	protected static Log _log =
					LogFactoryUtil.getLog(ServerManagerServlet.class);

}

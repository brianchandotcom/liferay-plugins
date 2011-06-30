
package com.liferay.server.manager.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ServerDetector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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

public class ServerManagerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(
		HttpServletRequest request, HttpServletResponse response)
						throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		String path = request.getServletPath().toLowerCase();

		// Remove first / so that split won't have empty strings
		if (path.startsWith("/")) {
			path = path.substring(1);
		}

		String[] pathPieces = path.split("/");

		response.setContentType("application/json");

		try {
			if (request.getMethod().equalsIgnoreCase("get")) {
				handleGet(request, response, pathPieces);
			}
			else if (request.getMethod().equalsIgnoreCase("post")) {
				handlePost(request, response, pathPieces);
			}
			else if (request.getMethod().equalsIgnoreCase("put")) {
				handlePut(request, response, pathPieces);
			}
			else if (request.getMethod().equalsIgnoreCase("delete")) {
				handleDelete(request, response, pathPieces);
			}
		} catch (Exception e) {
			_log.error(e);
		}

		out.flush();
		out.close();
	}

	protected void handleGet(
		HttpServletRequest request, HttpServletResponse response, String[] path)
						throws IOException {

		if (path.length == 0) {
			return;
		}

		if (path[0].equals("is-alive")) {
			isAliveHandler(request, response);
		} else if (path[0].equals("log")) {
			logHandler(request, response);
		} else if (path[0].equals("debug-port")) {
			debugPortHandler(request, response);
		}
	}

	protected void handlePost(
		HttpServletRequest request, HttpServletResponse response, String[] path)
						throws Exception {

		if (path.length == 0) {
			return;
		}

		if (path[0].equals("deploy")) {
			deployHandler(request, response, path);
		}
	}

	protected void handlePut (HttpServletRequest request, HttpServletResponse response, String[] path) throws Exception {
		if (path.length == 0) {
			return;
		}

		if (path[0].equals("deploy")) {
			deployUpdateHandler(request, response, path);
		}
	}

	protected void handleDelete (HttpServletRequest request, HttpServletResponse response, String[] path) throws Exception {
		if (path.length == 0) {
			return;
		}

		if (path[0].equals("undeploy")) {
			undeployHandler(request, response, path);
		}
	}

	protected void isAliveHandler(
		HttpServletRequest request, HttpServletResponse response)
						throws IOException {

		response.getWriter().print("1");
	}

	protected void deployHandler(
		HttpServletRequest request, HttpServletResponse response, String[] path)
						throws Exception {

		//		if (path.length < 2) {
		//			return;
		//		}
		//
		//		String contextName = path[1];

		List<FileItem> fileItems = getFileItems(request);

		for (FileItem fileItem : fileItems) {
			InputStream is = fileItem.getInputStream();

			String deployDir = PrefsPropsUtil.getString(PropsKeys.AUTO_DEPLOY_DEPLOY_DIR);

			if (deployDir == null) {
				return;
			}

			File target = new File(deployDir, fileItem.getName());

			toFile(is, target);

			response.getWriter().print("deployed " + fileItem.getName());

			break;
		}
	}

	protected void deployUpdateHandler (HttpServletRequest request, HttpServletResponse response, String[] path) throws IOException {
		if (path.length < 2) {
			return;
		}

		String context = path[1];
		String webappsDirectory = getWebappsDirectory();
		File contextDirectory = new File(webappsDirectory, context);

		List<FileItem> fileItems = getFileItems(request);

		for (FileItem fileItem : fileItems) {
			InputStream is = fileItem.getInputStream();

			ZipInputStream zis = new ZipInputStream(is);

			// unzip over current files
			decompressToDirectory(zis, contextDirectory);

			// delete uneeded files
			File deleteInfo = new File(contextDirectory, "META-INF/liferay-partialapp-delete.props");
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
				}
			}

			FileUtils.deleteQuietly(deleteInfo);

			response.getWriter().print("updated deployment " + context);

			break;
		}
	}

	protected void undeployHandler(
		HttpServletRequest request, HttpServletResponse response, String[] path) {

		if (path.length < 2) {
			return;
		}

		String context = path[1];
		String webappsDirectory = getWebappsDirectory();

		File directory = new File(webappsDirectory, context);

		if (directory.exists()) {
			boolean success = FileUtils.deleteQuietly(directory);
			if (!success) {
				_log.info("Could not remove " + context);
			}
		}
	}

	protected void logHandler (HttpServletRequest request, HttpServletResponse response) throws IOException {
		File log = new File(System.getProperty("catalina.base") + "/logs/catalina.out");

		if (!log.exists()) {
			return;
		}

		BufferedReader reader = new BufferedReader(new FileReader(log));

		while (true) {
			String line = reader.readLine();

			if (line == null) {
				break;
			}

			response.getWriter().write(line + "\n");
		}
	}

	protected void debugPortHandler (HttpServletRequest request, HttpServletResponse response) throws IOException {
		String debugPort = "";

		String catalinaOptionString = System.getProperty("env.CATALINA_OPTS");
		String[] options = catalinaOptionString.split("\\s*-D?");

		for (String option : options) {
			if (option.contains("agentlib:jdwp")) {
				Pattern pattern = Pattern.compile("address=(\\d+)");
				Matcher matcher = pattern.matcher(option);

				if (matcher.find()) {
					debugPort = matcher.group(1);
				}
			}
		}

		response.getWriter().write(debugPort);
	}

	protected static String getWebappsDirectory() {
		try {
			if (ServerDetector.isGeronimo()) {
				// return PrefsPropsUtil.getString(PropsKeys.AUTO_DEPLOY_GERONIMO_DEST_DIR);
			}
			else if (ServerDetector.isGlassfish()) {
				// return PrefsPropsUtil.getString(PropsKeys.AUTO_DEPLOY_GLASSFISH_DEST_DIR);
			}
			else if (ServerDetector.isJBoss()) {
				// return PrefsPropsUtil.getString(PropsKeys.AUTO_DEPLOY_JBOSS_DEST_DIR);
			}
			else if (ServerDetector.isJetty()) {
				// return PrefsPropsUtil.getString(PropsKeys.AUTO_DEPLOY_JETTY_DEST_DIR);
			}
			else if (ServerDetector.isJOnAS()) {
				// return PrefsPropsUtil.getString(PropsKeys.AUTO_DEPLOY_JONAS_DEST_DIR);
			}
			else if (ServerDetector.isResin()) {
				// return PrefsPropsUtil.getString(PropsKeys.AUTO_DEPLOY_RESIN_DEST_DIR);
			}
			else if (ServerDetector.isTomcat()) {
				return PrefsPropsUtil.getString(PropsKeys.AUTO_DEPLOY_TOMCAT_DEST_DIR);
			}
			else if (ServerDetector.isWebLogic()) {
				// return PrefsPropsUtil.getString(PropsKeys.AUTO_DEPLOY_WEBLOGIC_DEST_DIR);
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		return null;
	}

	protected static List<FileItem> getFileItems (HttpServletRequest request) {
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

	public static void decompressToDirectory (ZipInputStream is, File directory) throws IOException {
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

	public static void toFile (InputStream is, File f) throws IOException {
		FileUtils.touch(f);
		OutputStream os = new FileOutputStream(f);

		IOUtils.copy(is, os);
	}

	protected static Log _log =
					LogFactoryUtil.getLog(ServerManagerServlet.class);

}

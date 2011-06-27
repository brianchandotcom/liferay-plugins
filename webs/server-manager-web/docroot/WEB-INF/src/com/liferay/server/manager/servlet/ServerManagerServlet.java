
package com.liferay.server.manager.servlet;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.management.MBeanServer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Manager;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.HostConfig;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.modeler.Registry;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

public class ServerManagerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		String path = request.getServletPath().toLowerCase();

		// Remove first / so that split won't have empty strings
		if (path.startsWith("/")) {
			path = path.substring(1);
		}

		String[] pathPieces = path.split("/");

		response.setContentType("application/json");

		if (request.getMethod().equalsIgnoreCase("get")) {
			handleGet(request, response, pathPieces);
		} else if (request.getMethod().equalsIgnoreCase("post")) {
			handlePost(request, response, pathPieces);
		}

		out.flush();
		out.close();
	}

	protected void handleGet (HttpServletRequest request, HttpServletResponse response, String[] path) throws IOException {
		if (path.length == 0) {
			return;
		}

		if (path[0].equals("is-alive")) {
			isAliveHandler(request, response);
		}
	}


	/**
	 * Invoke the check method on the deployer.
	 */
	protected void check(String name, MBeanServer mBeanServer)
		throws Exception {

		String[] params = {
			name
		};

		String[] signature = {
			"java.lang.String"
		};

//		mBeanServer.invoke(oname, "check", params, signature);
	}

	/**
	 * Upload the WAR file included in this request, and store it at the
	 * specified file location.
	 *
	 * @param writer    Writer to render to
	 * @param request   The servlet request we are processing
	 * @param war       The file into which we should store the uploaded WAR
	 * @param smClient  The StringManager used to construct i18n messages based
	 *                  on the Locale of the client
	 *
	 * @exception IOException if an I/O error occurs during processing
	 */
	protected void uploadWar(InputStream istream, File war)
		throws IOException {

		if (war.exists() && !war.delete()) {
			throw new IOException("Failed to delete file: " + war);
		}
		BufferedOutputStream ostream = null;
		try {
			ostream =
				new BufferedOutputStream(new FileOutputStream(war), 1024);
			byte buffer[] = new byte[1024];
			while (true) {
				int n = istream.read(buffer);

				if (n < 0) {
					break;
				}
				ostream.write(buffer, 0, n);
			}
			ostream.flush();
			ostream.close();
			ostream = null;
			istream.close();
			istream = null;
		} catch (IOException e) {
			if (war.exists() && !war.delete()) {
				throw new IOException("Failed to delete file: " + war);
			}
			throw e;
		} finally {
			if (ostream != null) {
				try {
					ostream.close();
				} catch (Throwable t) {
					_log.error(t);
				}
				ostream = null;
			}
			if (istream != null) {
				try {
					istream.close();
				} catch (Throwable t) {
					_log.error(t);
				}
				istream = null;
			}
		}
	}



	protected void handlePost (HttpServletRequest request, HttpServletResponse response, String[] path) throws IOException {
		if (path.length == 0) {
			return;
		}

		if (path[0].equals("deploy")) {
			if (ServletFileUpload.isMultipartContent(request)) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload uploader = new ServletFileUpload(factory);

				try {
					@SuppressWarnings("unchecked")
					List<FileItem> fileItems = uploader.parseRequest(request);

					for (FileItem fileItem : fileItems) {
						if (fileItem.isFormField()) {
						} else {
							// File
							InputStream is = fileItem.getInputStream();

							StandardHost host = new StandardHost();
							Manager manager = host.getManager();
							HostConfig hc = new HostConfig();

							MBeanServer mBeanServer = Registry.getRegistry(null, null).getMBeanServer();

							File file = new File("test.war");

							System.out.println("DEBUG: war file location: " + file.getAbsolutePath());

							uploadWar(is, file);
						}
					}
				}
				catch (FileUploadException e) {
					_log.error(e);
					return;
				}

				deployHandler(request, response, path);
			}
		}
	}

	protected void deployHandler (HttpServletRequest request, HttpServletResponse response, String[] path) throws IOException {
		if (path.length < 2) {
			return;
		}

		String contextName = path[1];

		Object war = request.getParameter("deploy-war");

		response.getWriter().print("deploying " + contextName);

	}

	protected void isAliveHandler (HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.getWriter().print("1");
	}

//	@Override
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//		throws ServletException, IOException {
//
//		PrintWriter out = response.getWriter();
//		String path = request.getServletPath().toLowerCase();
//		String[] pathPieces = path.split("/");
//
//		response.setContentType("application/json");
//
//		if (pathPieces.length == 0) {
//			return;
//		}
//
//		if (path.startsWith("/is-alive")) {
//			isAliveHandler(request, response);
//		}
//
//		out.flush();
//		out.close();
//	}
//
//	@Override
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//		throws ServletException, IOException {
//
//		PrintWriter out = response.getWriter();
//		String path = request.getServletPath().toLowerCase();
//
//		response.setContentType("application/json");
//
//		if (path.startsWith("/deploy")) {
//			isAliveHandler(request, response);
//		}
//
//		out.flush();
//		out.close();
//	}


	protected static Log _log = LogFactoryUtil.getLog(ServerManagerServlet.class);

}


package com.liferay.server.manager.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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

		for (int i = 0; i < pathPieces.length; ++i) {
			System.out.print(pathPieces[i] + ", ");
		}

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

		ServletFileUpload uploader = new ServletFileUpload();
		try {
			List fileItemsList = uploader.parseRequest(request);
			Iterator it = fileItemsList.iterator();
			while (it.hasNext()) {
				FileItem fileItem = (FileItem) it.next();
				if (fileItem.isFormField()) {
					// This item is a form field
					String key = fileItem.getFieldName();
					String value = fileItem.getString();
				}
				else {
					// This item is a file

				}
			}
		}
		catch (FileUploadException e) {
			_log.error(e);
		}

		if (path[0].equals("is-alive")) {
			isAliveHandler(request, response);
		}
	}

	protected void handlePost (HttpServletRequest request, HttpServletResponse response, String[] path) throws IOException {
		if (path.length == 0) {
			return;
		}

		if (path[0].equals("deploy")) {
			deployHandler(request, response, path);
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

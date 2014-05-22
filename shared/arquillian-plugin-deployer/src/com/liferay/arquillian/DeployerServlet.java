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

package com.liferay.arquillian;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.BundleReference;
import org.osgi.framework.Filter;
import org.osgi.util.tracker.ServiceTracker;

/**
 * This servlet installs or uninstalls a OSGi bundle received by a POST request,
 * then waits for the Arquillian servlet runner to be started, and then returns
 * Arquillian test runner context in the response.
 *
 * @author Carlos Sierra Andrés
 */
public class DeployerServlet extends HttpServlet {

	public static final String BUNDLE_CONTEXT_PATH = "Bundle-Context-Path";

	public static final String DEPLOYER_SERVLET_LOCATION = "DeployerServlet";

	public static final String TEXT = "text/text";

	public static final long TIMEOUT = 10 * 1000L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		ServletContext servletContext = config.getServletContext();

		if (servletContext instanceof BundleReference) {
			_bundle = ((BundleReference)servletContext).getBundle();
		}

		_contextPathHeader = GetterUtil.getString(
			config.getInitParameter("contextPathHeader"), BUNDLE_CONTEXT_PATH);

		_deployerServletInstallLocation = GetterUtil.getString(
			config.getInitParameter("deployerServletInstallLocation"),
			DEPLOYER_SERVLET_LOCATION);

		_installTimeout = GetterUtil.getLong(
			config.getInitParameter("installTimeout"), TIMEOUT);
	}

	@Override
	protected void doDelete(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		BundleContext bundleContext = _bundle.getBundleContext();

		try {
			Bundle bundle = bundleContext.getBundle(DEPLOYER_SERVLET_LOCATION);

			bundle.stop();

			bundle.uninstall();
		}
		catch (BundleException e) {
			throw new ServletException(e);
		}
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		ServiceTracker servletContextServiceTracker = null;

		try {
			InputStream bundleInputStream = _getUploadedBundleInputStream(
				request);

			BundleContext bundleContext = _bundle.getBundleContext();

			Bundle newBundle = bundleContext.installBundle(
				_deployerServletInstallLocation, bundleInputStream);

			newBundle.start();

			Filter bundleContextFilter = bundleContext.createFilter(
				"(&(objectClass=javax.servlet." + "ServletContext)(bundle.id=" +
					newBundle.getBundleId() + "))");

			servletContextServiceTracker = new ServiceTracker(
				bundleContext, bundleContextFilter, null);

			servletContextServiceTracker.open();

			ServletContext servletContext =
				(ServletContext)servletContextServiceTracker.waitForService(
					_installTimeout);

			Servlet arquillianServletRunner = _waitForServlet(
				servletContext, "ArquillianServletRunner", _installTimeout);

			if (arquillianServletRunner == null) {
				throw new TimeoutException(
					"The arquillian servlet runner is taking more than " +
						_installTimeout + " to deploy");
			}

			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType(TEXT);
			response.setHeader(
				_contextPathHeader, servletContext.getContextPath());
		}
		catch (Exception e) {
			_signalError(e, response);
		}
		finally {
			if (servletContextServiceTracker != null) {
				servletContextServiceTracker.close();
			}

			ServletOutputStream outputStream = response.getOutputStream();

			outputStream.flush();
		}
	}

	private InputStream _getUploadedBundleInputStream(
			HttpServletRequest httpServletRequest)
		throws FileUploadException, IOException {

		DiskFileItemFactory factory = new DiskFileItemFactory();

		ServletConfig servletConfig = this.getServletConfig();

		ServletContext servletContext = servletConfig.getServletContext();

		File repository = (File)servletContext.getAttribute(
			"javax.servlet.context.tempdir");

		factory.setRepository(repository);

		ServletFileUpload upload = new ServletFileUpload(factory);

		List<FileItem> items = upload.parseRequest(httpServletRequest);

		FileItem fileItem = items.get(0);

		return fileItem.getInputStream();
	}

	private void _signalError(
		Throwable throwable, HttpServletResponse httpServletResponse) {

		httpServletResponse.setStatus(
			HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

		try {
			ServletOutputStream outputStream =
				httpServletResponse.getOutputStream();

			httpServletResponse.setContentType(StringPool.UTF8);

			PrintWriter printWriter = new PrintWriter(outputStream);

			throwable.printStackTrace(printWriter);

			printWriter.flush();
		}
		catch (IOException ioe) {
			if (_log.isErrorEnabled()) {
				_log.error(
					"Unexpected error while signaling " +
						throwable.getMessage(), ioe);
			}
		}
	}

	private Servlet _waitForServlet(
		ServletContext servletContext, String servletName, long timeout) {

		long elapsedTime = 0;

		Servlet found = null;

		final long step = 10;

		while ((found == null) && (elapsedTime < timeout)) {
			try {
				Thread.sleep(step);
			}
			catch (InterruptedException e) {
				break;
			}

			try {

				// Our current Http Service implementation returns a valid
				// servlet so we currently can rely on this method invocation.
				// The way we discover the servlet will be improved when a
				// newer version of the Http Service is in place

				found = servletContext.getServlet(servletName);
			}
			catch (ServletException e) {
			}

			elapsedTime += step;
		}

		return found;
	}

	private static Log _log = LogFactoryUtil.getLog(DeployerServlet.class);

	private Bundle _bundle;
	private String _contextPathHeader;
	private String _deployerServletInstallLocation;
	private long _installTimeout;

}
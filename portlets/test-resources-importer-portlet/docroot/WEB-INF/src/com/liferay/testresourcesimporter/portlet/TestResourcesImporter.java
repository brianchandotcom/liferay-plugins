/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.testresourcesimporter.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.testresourcesimporter.util.TestResourcesImporterUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.io.File;
import java.io.IOException;

import java.lang.Exception;
import java.lang.String;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.ServletContext;

/**
 * @author Shinn Lok
 */
public class TestResourcesImporter extends MVCPortlet {

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		cleanUp();

		String importer = actionRequest.getParameter("importer");

		prepareImporter(importer);

		invokeDeploy();

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			actionRequest, "1_WAR_testresourcesimporterportlet",
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath","/test.jsp");

		actionResponse.sendRedirect(portletURL.toString());
	}

	protected void cleanUp() {
		if ((_resourcesPath != null) && FileUtil.exists(_resourcesPath)) {
			FileUtil.delete(_resourcesPath);
		}

		long groupId = TestResourcesImporterUtil.getGroupId();

		if (groupId == 0) {
			return;
		}

		try {
			GroupLocalServiceUtil.deleteGroup(groupId);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void invokeDeploy() {
		Message message = new Message();

		message.put("command", "deploy");
		message.put("servletContextName", _SERVLET_CONTEXT_NAME);

		message.setResponseDestinationName("liferay/resources_importer");

		try {
			MessageBusUtil.sendSynchronousMessage(
				DestinationNames.HOT_DEPLOY, message);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void prepareImporter(String importer)
		throws IOException, PortletException {

		ServletContext servletContext = ServletContextPool.get(
			_SERVLET_CONTEXT_NAME);

		String importerPath = servletContext.getRealPath(
			"WEB-INF/classes/temp/" + importer);
		String resourcesPath = servletContext.getRealPath(
			"/WEB-INF/classes/resources-importer");

		_resourcesPath = new File(resourcesPath);

		_resourcesPath.mkdir();

		if (!importer.equals("filesystem")) {
			FileUtil.copyDirectory(importerPath, resourcesPath);
		}
	}

	private static final String _SERVLET_CONTEXT_NAME =
		"test-resources-importer-portlet";

	private static Log _log = LogFactoryUtil.getLog(
		TestResourcesImporter.class);

	private File _resourcesPath;

}
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

package com.liferay.hr.portlet;

import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.util.bridges.alloy.AlloyPortlet;

import java.io.InputStream;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Wesley Gong
 */

public class HRPortlet extends AlloyPortlet {

	public void serveDownload(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		String className = ParamUtil.getString(resourceRequest, "className");
		String folderId = ParamUtil.getString(resourceRequest, "folderId");
		String fileName = ParamUtil.getString(resourceRequest, "fileName");

		String path = className + "/" + folderId + "/" + fileName;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);

		try {
			InputStream is = DLStoreUtil.getFileAsStream(
				themeDisplay.getCompanyId(), CompanyConstants.SYSTEM, path);
			int contentLength = (int)DLStoreUtil.getFileSize(
				themeDisplay.getCompanyId(), CompanyConstants.SYSTEM, path);
			String contentType = MimeTypesUtil.getContentType(fileName);

			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				resourceRequest);
			HttpServletResponse response = PortalUtil.getHttpServletResponse(
				resourceResponse);

			ServletResponseUtil.sendFile(
				request, response, fileName, is, contentLength, contentType);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		try {
			String id = resourceRequest.getResourceID();

			if (id.equals("download")) {
				serveDownload(resourceRequest, resourceResponse);
			}
			else {
				super.serveResource(resourceRequest, resourceResponse);
			}
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}
}
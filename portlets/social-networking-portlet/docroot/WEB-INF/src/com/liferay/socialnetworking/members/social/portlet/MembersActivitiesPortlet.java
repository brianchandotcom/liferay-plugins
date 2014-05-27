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

package com.liferay.socialnetworking.members.social.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.io.IOException;
import java.io.OutputStream;

import java.lang.reflect.Method;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Dale Shan
 */
public class MembersActivitiesPortlet extends MVCPortlet {

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		ClassLoader portalClassLoader = PortalClassLoaderUtil.getClassLoader();

		OutputStream outputStream = null;

		try {
			if (contextClassLoader != portalClassLoader) {
				currentThread.setContextClassLoader(portalClassLoader);
			}

			if (!PortalUtil.isRSSFeedsEnabled()) {
				PortalUtil.sendRSSFeedsDisabledError(
					resourceRequest, resourceResponse);

				return;
			}

			resourceResponse.setContentType(ContentTypes.TEXT_XML_UTF8);

			outputStream = resourceResponse.getPortletOutputStream();

			Class<?> clazz = portalClassLoader.loadClass(
				"com.liferay.portlet.activities.action.RSSAction");

			Method method = ReflectionUtil.getDeclaredMethod(
				clazz, "getRSS", ResourceRequest.class, ResourceResponse.class);

			Object obj = clazz.newInstance();

			byte[] bytes = (byte[])method.invoke(
				obj, resourceRequest, resourceResponse);

			outputStream.write(bytes);
		}
		catch (Exception e) {
			_log.error(e);
		}
		finally {
			if (contextClassLoader != portalClassLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}

			outputStream.close();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		MembersActivitiesPortlet.class);

}
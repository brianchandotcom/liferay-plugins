/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
package com.liferay.shibboleth.events;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.shibboleth.util.PortletPropsValues;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Eric Chin
 */
public class ShibbolethLogoutAction extends Action {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response)
		throws ActionException {

		if (!PortletPropsValues.SHIBBOLETH_LOGOUT_ENABLED) {
			return;
		}

		String pathInfo = request.getPathInfo();

		if (pathInfo.contains("/portal/logout")) {
			HttpSession session = request.getSession();

			session.invalidate();

			try {
				response.sendRedirect(PortletPropsValues.SHIBBOLETH_LOGOUT_URL);
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ShibbolethLogoutAction.class);

}
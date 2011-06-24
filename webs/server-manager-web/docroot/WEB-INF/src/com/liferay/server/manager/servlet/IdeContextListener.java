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

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;

/**
 * @author Jonathan Potter
 */
public class IdeContextListener
	extends HttpServlet implements ServletContextListener, Servlet {

	protected boolean _calledPortalDestroy = false;
	
	protected static Log _log = LogFactoryUtil.getLog(IdeContextListener.class);
	
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		portalDestroy();
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		registerPortalLifecycle();
	}
	
	public void portalDestroy() {
		if (!_calledPortalDestroy) {
//			PortalLifecycleUtil.removeDestroy(this);

			try {
				doPortalDestroy();
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			_calledPortalDestroy = true;
		}
	}

	public void portalInit() {
		try {
			doPortalInit();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}
	
	public void registerPortalLifecycle() {
//		PortalLifecycleUtil.register(this);
	}

	public void registerPortalLifecycle(int method) {
//		PortalLifecycleUtil.register(this, method);
	}

	protected void doPortalDestroy() throws Exception {
	}

	protected void doPortalInit() throws Exception {
	}

	@Override
	public void destroy() {

		// TODO Auto-generated method stub
		
	}

	@Override
	public ServletConfig getServletConfig() {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletInfo() {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(ServletConfig arg0)
		throws ServletException {

		// TODO Auto-generated method stub
		
	}

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1)
		throws ServletException, IOException {

		// TODO Auto-generated method stub
		
	}

}
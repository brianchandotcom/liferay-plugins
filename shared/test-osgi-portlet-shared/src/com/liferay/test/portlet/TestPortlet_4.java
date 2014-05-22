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

package com.liferay.test.portlet;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Enumeration;

import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=TP_4",
		"javax.portlet.init-param.param1=value1",
		"javax.portlet.init-param.param2=value2",
	},
	service = Portlet.class
)
public class TestPortlet_4 extends BaseTestPortlet {

	@Override
	public void render(RenderRequest request, RenderResponse response)
		throws PortletException, IOException {

		PortletConfig portletConfig = getPortletConfig();

		PrintWriter writer = response.getWriter();

		writer.print("Test portlet ");
		writer.print(getPortletConfig().getPortletName());
		writer.println("<br/>");
		writer.println("Init Params:");
		writer.println("<ul>");

		Enumeration<String> initParameterNames =
			portletConfig.getInitParameterNames();

		while (initParameterNames.hasMoreElements()) {
			String initParameterName = initParameterNames.nextElement();

			String initParamValue = portletConfig.getInitParameter(
				initParameterName);

			writer.print("<li>");
			writer.print(initParameterName);
			writer.print(" = ");
			writer.print(initParamValue);
			writer.println("</li>");
		}

		writer.println("</ul>");

		writer.flush();
		writer.close();
	}

}
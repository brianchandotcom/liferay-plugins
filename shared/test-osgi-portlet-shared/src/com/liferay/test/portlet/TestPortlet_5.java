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

import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.Portlet;
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
		"javax.portlet.name=TP_5",
		"javax.portlet.expiration-cache:Integer=1000"
	},
	service = Portlet.class
)
public class TestPortlet_5 extends BaseTestPortlet {

	@Override
	public void render(RenderRequest request, RenderResponse response)
		throws PortletException, IOException {

		com.liferay.portal.model.Portlet portlet =
			(com.liferay.portal.model.Portlet)request.getAttribute(
				WebKeys.RENDER_PORTLET);

		PrintWriter writer = response.getWriter();

		writer.print("Test portlet ");
		writer.print(getPortletConfig().getPortletName());
		writer.println("<br/>");
		writer.print("Expiration Cache: ");
		writer.println(portlet.getExpCache());

		writer.flush();
		writer.close();
	}

}
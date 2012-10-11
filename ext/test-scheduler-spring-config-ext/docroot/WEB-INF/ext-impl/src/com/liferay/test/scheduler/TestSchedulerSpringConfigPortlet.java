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

package com.liferay.test.scheduler;

import com.liferay.portal.kernel.util.ContentTypes;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.GenericPortlet;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Tina Tian
 */
public class TestSchedulerSpringConfigPortlet extends GenericPortlet {

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException {

		renderResponse.setContentType(ContentTypes.TEXT_HTML_UTF8);

		PrintWriter printWriter = renderResponse.getWriter();

		boolean isReceived = TestMessageListenerForSpringConfigJob.isReceived();

		if (isReceived) {
			printWriter.println(
				TestMessageListenerForSpringConfigJob.class.getName() +
					".isReceived()=PASSED.");
		}
		else {
			printWriter.println(
				TestMessageListenerForSpringConfigJob.class.getName() +
					".isReceived()=FAILED.");
		}

		printWriter.close();
	}

}
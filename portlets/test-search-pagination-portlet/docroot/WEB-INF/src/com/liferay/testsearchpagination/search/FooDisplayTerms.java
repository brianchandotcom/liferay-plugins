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

package com.liferay.testsearchpagination.search;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequest;

/**
 * @author Inácio Nery
 */
public class FooDisplayTerms extends DisplayTerms {

	public static final String FOO_IDS = "fooIds";

	public FooDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		fooIds = ParamUtil.getParameterValues(portletRequest, FOO_IDS);
	}

	public String[] getFooIds() {
		return fooIds;
	}

	protected String[] fooIds;

}
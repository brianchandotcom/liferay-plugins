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

package com.liferay.sampleservicebuilder.search;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.sampleservicebuilder.model.Foo;
import com.liferay.sampleservicebuilder.service.FooLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Inácio Nery
 */
public class FooSearch extends SearchContainer<Foo> {

	public static final String EMPTY_RESULTS_MESSAGE = "no-entries-were-found";

	public static List<String> headerNames = new ArrayList<>();

	public FooSearch(PortletRequest portletRequest, PortletURL iteratorURL) {
		super(
			portletRequest, new FooDisplayTerms(portletRequest),
			new FooDisplayTerms(portletRequest),
			SearchContainer.DEFAULT_CUR_PARAM, 0, SearchContainer.DEFAULT_DELTA,
			iteratorURL, null, null, StringPool.BLANK);

		iteratorURL.setParameter(FooDisplayTerms.FOOIDS, getFooIds());

		setForcePost(true);
	}

	@Override
	public List<Foo> getResults() {
		return FooLocalServiceUtil.findByFooIds(
			getFooIds(), getStart(), getEnd());
	}

	@Override
	public int getTotal() {
		return FooLocalServiceUtil.countByFooIds(getFooIds());
	}

	private String[] getFooIds() {
		FooDisplayTerms displayTerms = (FooDisplayTerms)getDisplayTerms();

		return displayTerms.getFooIds();
	}

}
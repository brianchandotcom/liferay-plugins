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

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.testsearchpagination.model.Foo;
import com.liferay.testsearchpagination.service.FooLocalServiceUtil;

import java.util.ArrayList;
import java.util.Collections;
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

		FooDisplayTerms displayTerms = (FooDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			FooDisplayTerms.FOO_IDS, displayTerms.getFooIds());

		setForcePost(true);
	}

	@Override
	public List<Foo> getResults() {
		List<Foo> results = Collections.emptyList();

		try {
			results = FooLocalServiceUtil.findByFooIds(
				getFooIds(), getStart(), getEnd());
		}
		catch (SystemException se) {
			if (_log.isWarnEnabled()) {
				_log.warn(se);
			}
		}

		return results;
	}

	@Override
	public int getTotal() {
		int fooIds = 0;

		try {
			fooIds = FooLocalServiceUtil.countByFooIds(getFooIds());
		}
		catch (SystemException se) {
			if (_log.isWarnEnabled()) {
				_log.warn(se);
			}
		}

		return fooIds;
	}

	private String[] getFooIds() {
		FooDisplayTerms displayTerms = (FooDisplayTerms)getDisplayTerms();

		return displayTerms.getFooIds();
	}

	private static final Log _log = LogFactoryUtil.getLog(FooSearch.class);

}
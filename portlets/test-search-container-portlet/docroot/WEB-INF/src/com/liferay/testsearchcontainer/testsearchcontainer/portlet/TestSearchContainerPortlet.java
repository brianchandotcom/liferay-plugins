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

package com.liferay.testsearchcontainer.testsearchcontainer.portlet;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.testsearchcontainer.model.Foo;
import com.liferay.testsearchcontainer.service.FooLocalServiceUtil;
import com.liferay.testsearchcontainer.util.PortletPropsKeys;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.liferay.util.portlet.PortletProps;

import java.lang.String;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

/**
 * @author Janghyun Kim
 */
public class TestSearchContainerPortlet extends MVCPortlet {

	public void initAndForward(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		FooLocalServiceUtil.removeAll();

		String nextFormNumber = ParamUtil.getString(
			actionRequest, "nextFormNumber", "1");

		Filter filter = new Filter(nextFormNumber);

		int total = GetterUtil.getInteger(
			PortletProps.get(
				PortletPropsKeys.TEST_SEARCH_CONTAINER_TOTAL, filter));

		for (int i = 1; i <= total; i++) {
			FooLocalServiceUtil.addFoo(i);
		}

		actionResponse.setRenderParameter("curFormNumber", nextFormNumber);
		actionResponse.setRenderParameter(
			"mvcPath", "/forms/form" + nextFormNumber + ".jsp");
	}

	public void testLastPagination(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		int total = FooLocalServiceUtil.getFoosCount();
		int cur = (int)Math.ceil((double)total / SearchContainer.DEFAULT_DELTA);

		// fetch last entry and delete

		Foo lastFoo = FooLocalServiceUtil.getFooByValue(total);
		FooLocalServiceUtil.delete(lastFoo.getFooId());

		String redirect = ParamUtil.getString(actionRequest, "redirect");
		String mvcPath = ParamUtil.getString(actionRequest, "mvcPath");

		redirect = HttpUtil.setParameter(
			redirect, actionResponse.getNamespace() + "mvcPath", mvcPath);
		redirect = HttpUtil.setParameter(
			redirect, actionResponse.getNamespace() + "cur",
			String.valueOf(cur));

		actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
	}

}
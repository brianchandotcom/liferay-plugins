<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/form5.jsp");
%>

<liferay-ui:search-container
	headerNames="id,text"
	iteratorURL="<%= portletURL %>"
>
	<liferay-ui:search-container-results
		total="<%= FooLocalServiceUtil.getFoosCount() %>"
		results="<%= FooLocalServiceUtil.getFoos(searchContainer.getStart(), searchContainer.getEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.testsearchcontainer.model.Foo"
		escapedModel="<%= true %>"
		keyProperty="fooId"
		modelVar="curFoo"
	>
		<liferay-ui:search-container-column-text
			name="id"
			property="fooId"
		/>

		<liferay-ui:search-container-column-text
			name="value"
			property="value"
		/>

	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<liferay-util:include page="/forward.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/test.jsp" servletContext="<%= application %>" />
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
String nextFormNumber = ParamUtil.getString(request, "nextFormNumber");
%>

<c:if test="<%= Validator.isNotNull(curFormNumber) %>">

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("curFormNumber", curFormNumber);
	portletURL.setParameter("mvcPath", mvcPath);
	%>

	<liferay-portlet:actionURL name="testLastPagination" var="testURL">
		<portlet:param name="curFormNumber" value="<%= curFormNumber %>" />
		<portlet:param name="mvcPath" value="<%= mvcPath %>" />
		<portlet:param name="redirect" value="<%= portletURL.toString() %>" />
	</liferay-portlet:actionURL>

	<div class="separator"></div>

	<a href="<%= testURL %>">PAGINATION TEST</a>
</c:if>

<c:if test="<%= Validator.isNotNull(nextFormNumber) %>">
	<liferay-portlet:actionURL name="init" var="initURL">
		<portlet:param name="nextFormNumber" value="<%= nextFormNumber %>" />
		<portlet:param name="mvcPath" value="<%= mvcPath %>" />
	</liferay-portlet:actionURL>

	<div class="separator"></div>

	<a href="<%= initURL %>">NEXT TEST</a>
</c:if>
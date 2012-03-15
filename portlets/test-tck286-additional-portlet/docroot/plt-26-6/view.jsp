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

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />

<p>
Test ParamTag removing feature, see PLT.26.6 for more info.
</p>

<portlet:renderURL var="testActionURL">
	<portlet:param name = "mvcPath" value="/plt-26-6/actionURL.jsp" />
	<portlet:param name = "testRenderParamName" value="testRenderParamValue" />
</portlet:renderURL>

<portlet:renderURL var="testRenderURL">
	<portlet:param name = "mvcPath" value="/plt-26-6/renderURL.jsp" />
	<portlet:param name = "testRenderParamName" value="testRenderParamValue" />
</portlet:renderURL>

<portlet:renderURL var="testResourceURL">
	<portlet:param name = "mvcPath" value="/plt-26-6/resourceURL.jsp" />
	<portlet:param name = "testRenderParamName" value="testRenderParamValue" />
</portlet:renderURL>

<p>
<a href="<%=testActionURL%>">Test ParamTag in actionURL</a>
</p>

<p>
<a href="<%=testRenderURL%>">Test ParamTag in renderURL</a>
</p>

<p>
<a href="<%=testResourceURL%>">Test ParamTag in resourceURL</a>
</p>
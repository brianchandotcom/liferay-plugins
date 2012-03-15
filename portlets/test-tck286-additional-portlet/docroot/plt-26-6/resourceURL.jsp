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

<portlet:resourceURL var="resourceURL1" />

<portlet:resourceURL var="resourceURL2">
	<portlet:param name="name1" value="value1" />
	<portlet:param name="name2" value="value2" />
	<portlet:param name="name1" value="" />
</portlet:resourceURL>

<portlet:resourceURL var="resourceURL3">
	<portlet:param name="testRenderParamName" value="" />
</portlet:resourceURL>

<%
	String result1 = "Pass";

	if (!resourceURL1.contains("mvcPath")) {
		result1 = "Fail";
	}

	if (!resourceURL1.contains("testRenderParamName")) {
		result1 = "Fail";
	}

	String result2 = "Pass";

	if (!resourceURL2.contains("mvcPath")) {
		result2 = "Fail";
	}

	if (!resourceURL2.contains("testRenderParamName")) {
		result2 = "Fail";
	}

	if (resourceURL2.contains("name1")) {
		result2 = "Fail";
	}

	if (!resourceURL2.contains("name2")) {
		result2 = "Fail";
	}

	String result3 = "Pass";

	if (!resourceURL1.contains("mvcPath")) {
		result3 = "Fail";
	}

	if (!resourceURL1.contains("testRenderParamName")) {
		result3 = "Fail";
	}
%>

<table>
	<tr>
		<td>Copy current render parameters :</td>
		<td><%=result1%></td>
	</tr>
	<tr>
		<td>Remove regular parameter :</td>
		<td><%=result2%></td>
	</tr>
	<tr>
		<td>Remove copied current render parameter :</td>
		<td><%=result3%></td>
	</tr>
</table>
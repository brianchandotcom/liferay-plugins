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
if (!themeDisplay.isSignedIn()) {
%>

	Please sign in to run the test.

<%
	return;
}
%>

<h3>File System Importer</h3>

<p>
	<a href="<portlet:actionURL><portlet:param name="importer" value="filesystem" /></portlet:actionURL>">Invoke Deploy</a>
</p>

<h3>LAR Importer</h3>

<p>
	<a href="<portlet:actionURL><portlet:param name="importer" value="lar" /></portlet:actionURL>">Invoke Deploy</a>
</p>

<h3>Resource Importer</h3>

<p>
	<a href="<portlet:actionURL><portlet:param name="importer" value="resource" /></portlet:actionURL>">Invoke Deploy</a>
</p>
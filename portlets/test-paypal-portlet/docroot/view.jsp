<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
String tabs1 = ParamUtil.getString(request, "tabs1", "Purchase");
String tabs2 = ParamUtil.getString(request, "tabs2", "Account Payment");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view.jsp");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
%>

<liferay-ui:tabs
	names="Purchase"
	param="tabs1"
	url="<%= portletURL.toString() %>"
/>

<liferay-ui:tabs
	names="Account Payment, REST"
	param="tabs2"
	url="<%= portletURL.toString() %>"
/>

<c:choose>
	<c:when test='<%= tabs2.equals("Account Payment") %>'>
		<liferay-util:include page="/purchase/purchase_with_account_payment.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= tabs2.equals("REST") %>'>
		<liferay-util:include page="/purchase/purchase_with_rest.jsp" servletContext="<%= application %>" />
	</c:when>
</c:choose>
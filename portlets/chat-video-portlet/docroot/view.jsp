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

<c:if test="<%= themeDisplay.isSignedIn() && !(BrowserSnifferUtil.isIe(request) && (BrowserSnifferUtil.getMajorVersion(request) < 7)) && !BrowserSnifferUtil.isMobile(request) %>">

	<%
	Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletDisplay.getId());
	%>

	<liferay-util:html-top>
		<link href="<%= PortalUtil.getStaticResourceURL(request, request.getContextPath() + "/css/main.css", portlet.getTimestamp()) %>" rel="stylesheet" type="text/css" />
	</liferay-util:html-top>
	
	<liferay-util:html-bottom>
		<script defer="defer" src="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathContext(request) + "/js/webrtc-adapter.js", portlet.getTimestamp()) %>" type="text/javascript"></script>
		<script defer="defer" src="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathContext(request) + "/js/webrtc.js", portlet.getTimestamp()) %>" type="text/javascript"></script>
		<script defer="defer" src="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathContext(request) + "/js/inject-chat-portlet.js", portlet.getTimestamp()) %>" type="text/javascript"></script>
	</liferay-util:html-bottom>

	<div class="portlet-chat-video" id="chat-video">
		<audio preload loop id="webrtc-ringtone" src="<%= PortalUtil.getStaticResourceURL(request, request.getContextPath() + "/audio/webrtc-ringtone.ogg", portlet.getTimestamp()) %>"></audio>
		<audio preload loop id="webrtc-out-ringtone" src="<%= PortalUtil.getStaticResourceURL(request, request.getContextPath() + "/audio/webrtc-out-ringtone.ogg", portlet.getTimestamp()) %>"></audio>
		<div id="webrtc-video-overlay" class="hide"></div>
		<div id="webrtc-mute-ctrl" class="unmuted hide"></div>
		<input id="chat-video-portlet-id" type="hidden" value="<%= portletDisplay.getId() %>" />
	</div>
</c:if>

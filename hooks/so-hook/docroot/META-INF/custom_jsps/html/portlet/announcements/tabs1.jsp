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

<%@ include file="/html/portlet/announcements/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "entries");
%>

<c:if test="<%= PortletPermissionUtil.contains(permissionChecker, plid, PortletKeys.ANNOUNCEMENTS, ActionKeys.ADD_ENTRY) %>">
	<div class="controls">
		<div class="controls-content">
			<c:choose>
				<c:when test='<%= tabs1.equals("entries") %>'>
					<input type="button" value='<liferay-ui:message key="add-entry" />' onClick="location.href = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/announcements/edit_entry" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>'" />
					<input type="button" value='<liferay-ui:message key="manage-entries" />' onClick="location.href = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/announcements/view" /><portlet:param name="tabs1" value="manage-entries" /></portlet:renderURL>'" />
				</c:when>
				<c:when test='<%= tabs1.equals("manage-entries") %>'>
					<input type="button" value='<liferay-ui:message key="entries" />' onClick="location.href = '<portlet:renderURL windowState="<%= WindowState.NORMAL.toString() %>"><portlet:param name="struts_action" value="/announcements/view" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>'" />
				</c:when>
			</c:choose>
		</div>
	</div>
</c:if>
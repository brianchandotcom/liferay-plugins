<%--
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
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.ip.geocoder.IPInfo" %>
<%@ page import="com.liferay.ip.geocoder.util.IPGeocoderServiceClient" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>

<%
HttpServletRequest originalRequest = PortalUtil.getOriginalServletRequest(request);

IPInfo ipInfo = IPGeocoderServiceClient.getIPInfo(originalRequest.getRemoteAddr());
%>

<c:choose>
	<c:when test="<%= ipInfo != null %>">
		City: <%= ipInfo.getCity() %><br />
		Country Code: <%= ipInfo.getCountryCode() %><br />
		Country Name: <%= ipInfo.getCountryName() %><br />
		IP: <%= ipInfo.getIpAddress() %><br />
		Latitude: <%= ipInfo.getLatitude() %><br />
		Longitude: <%= ipInfo.getLongitude() %><br />
		Postal Code: <%= ipInfo.getPostalCode() %><br />
		Region: <%= ipInfo.getRegion() %>
	</c:when>
	<c:otherwise>
		<div class="alert alert-error">
			<a href="http://www.maxmind.com/app/geolitecity" target="_blank"><liferay-ui:message key="install-and-configure-maxmind-geoip-city-or-geolite-city-to-enable-this-portlet" /></a>
		</div>
	</c:otherwise>
</c:choose>
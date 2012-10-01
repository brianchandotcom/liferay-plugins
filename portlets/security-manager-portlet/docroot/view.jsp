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

<liferay-portlet:actionURL name="updateConfiguration" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<aui:fieldset>
		<aui:input label="enable-java-security-manager" name="securityManagerEnabled" type="checkbox" value="<%= SecurityManagerUtil.isEnabled(company.getCompanyId()) %>" />

		<aui:button-row>
			<aui:button type="submit" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<%
List<String> headerNames = new ArrayList<String>();

headerNames.add("plugins-secured-by-java-security-manager");

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, renderResponse.createRenderURL(), headerNames, null);

List<ResultRow> resultRows = searchContainer.getResultRows();

Map<ClassLoader, Object> paclPolicies = SecurityManagerUtil.getPACLPolicies();

List<ClassLoader> classLoaders = new ArrayList<ClassLoader>(paclPolicies.keySet());

searchContainer.setTotal(classLoaders.size());

for (int i = 0; i < classLoaders.size(); i++) {
	String classLoaderString = String.valueOf(classLoaders.get(i));

	ResultRow row = new ResultRow(classLoaderString, classLoaderString, i);

	row.addText(classLoaderString);

	resultRows.add(row);
}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
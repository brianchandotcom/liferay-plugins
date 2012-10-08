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
List<String> headerNames = new ArrayList<String>();

headerNames.add("id");
headerNames.add("text");

SearchContainer searchContainer = new SearchContainer();

searchContainer.setHeaderNames(headerNames);

List<Foo> results = FooLocalServiceUtil.getFoos(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	Foo curFoo = (Foo)results.get(i);

	curFoo = curFoo.toEscapedModel();

	ResultRow row = new ResultRow(curFoo, curFoo.getFooId(), i);

	row.addText(String.valueOf(curFoo.getFooId()));
	row.addText(String.valueOf(curFoo.getValue()));

	resultRows.add(row);
}
%>

<liferay-ui:search-iterator paginate="<%= false %>" searchContainer="<%= searchContainer %>" />

<liferay-util:include page="/test.jsp" servletContext="<%= application %>" />
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

<%@ include file="/init.jsp" %>

<%
PortletURL iteratorURL = renderResponse.createRenderURL();

FooSearch fooSearch = new FooSearch(renderRequest, iteratorURL);

FooDisplayTerms fooDisplayTerms = (FooDisplayTerms)fooSearch.getDisplayTerms();
%>

<strong><liferay-ui:message key="welcome-to-the-test-search-pagination-portlet" /></strong>

<liferay-ui:search-container
	searchContainer="<%= fooSearch %>"
>
	<portlet:renderURL var="refreshURL" windowState="normal">
		<portlet:param name="mvcPath" value="/view.jsp" />
	</portlet:renderURL>

	<aui:form action="<%= refreshURL %>" method="post" name="fm">
		<section>
			<article>
				<aui:select label="Select" multiple="true" name="fooIds" />

				<aui:script use="liferay-dynamic-select">
					var data = function(callback) {
						Liferay.Service(
							'/test-search-pagination-portlet.foo/find-all',
							callback
						);
					};

					new Liferay.DynamicSelect(
						[
							{
								select: '<portlet:namespace />fooIds',
								selectData: data,
								selectDesc: 'fieldName',
								selectId: 'fooId',
								selectSort: '',
								selectVal: [<%= StringUtil.merge(fooDisplayTerms.getFooIds()) %>]
							}
						]
					);
				</aui:script>

				<aui:button type="submit" value="search" />
			</article>
		</section>
	</aui:form>

	<liferay-ui:search-container-row
		className="com.liferay.testsearchpagination.model.Foo"
		escapedModel="true"
		modelVar="foo"
	>
		<liferay-ui:search-container-column-text
			name="fieldId"
			property="fieldId"
			valign="top"
		/>

		<liferay-ui:search-container-column-text
			name="fieldName"
			property="fieldName"
			valign="top"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>
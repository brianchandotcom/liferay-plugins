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

<div class="separator"></div>

<div class="testResult">
</div>

<aui:script use="aui-base">
	var searchContainer = A.one('.lfr-search-container');
	var testResult = A.one('.testResult');

	if (searchContainer.hasClass('aui-helper-hidden')) {
		testResult.html('<b>FORM#<%= curFormNumber %>: FAILED</b>');
	}
	else {
		testResult.html('<b>FORM#<%= curFormNumber %>: PASSED</b>');
	}
</aui:script>
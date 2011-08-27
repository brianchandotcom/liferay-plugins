<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/WEB-INF/jsp/hr_expense/views/init.jsp" %>

<c:if test="<%= !windowState.equals(LiferayWindowState.POP_UP) %>">
	<portlet:renderURL var="addHRExpenseCurrencyConversionURL">
		<portlet:param name="controller" value="currency_conversions" />
		<portlet:param name="action" value="edit" />
		<portlet:param name="format" value="html" />
	</portlet:renderURL>

	<aui:button-row>
		<aui:button onClick="${addHRExpenseCurrencyConversionURL}" value="add-expense-currency" />
	</aui:button-row>
</c:if>

<liferay-ui:search-container
	emptyResultsMessage="there-are-no-expenses"
	headerNames="id,conversion-date,from-currency,to-currency,conversion-value"
>

	<liferay-ui:search-container-results
		results="<%= HRExpenseCurrencyConversionLocalServiceUtil.getHRExpenseCurrencyConversions(searchContainer.getStart(), searchContainer.getEnd()) %>"
		total="<%= HRExpenseCurrencyConversionLocalServiceUtil.getHRExpenseCurrencyConversionsCount() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.hr.model.HRExpenseCurrencyConversion"
		escapedModel="<%= true %>"
		keyProperty="hrExpenseCurrencyConversionId"
		modelVar="curHRExpenseCurrencyConversion"
	>

		<portlet:renderURL var="viewHRExpenseCurrencyConversionURL">
			<portlet:param name="controller" value="currency_conversions" />
			<portlet:param name="action" value="view" />
			<portlet:param name="id" value="${curHRExpenseCurrencyConversion.hrExpenseCurrencyConversionId}" />
			<portlet:param name="format" value="html" />
		</portlet:renderURL>

		<liferay-ui:search-container-column-text
			href="${viewHRExpenseCurrencyConversionURL}"
			name="id"
			property="hrExpenseCurrencyConversionId"
		/>

		<liferay-ui:search-container-column-text
			href="${viewHRExpenseCurrencyConversionURL}"
			name="conversion-date"
			value="<%= dateFormat.format(curHRExpenseCurrencyConversion.getConversionDate()) %>"
		/>

		<liferay-ui:search-container-column-text
			buffer="buffer"
			href="${viewHRExpenseCurrencyConversionURL}"
			name="from-currency"
		>
			<%
			long fromHRExpenseCurrencyId = curHRExpenseCurrencyConversion.getFromHRExpenseCurrencyId();

			if (fromHRExpenseCurrencyId > 0) {
				HRExpenseCurrency fromHRExpenseCurrency = HRExpenseCurrencyLocalServiceUtil.getHRExpenseCurrency(fromHRExpenseCurrencyId);

				buffer.append(fromHRExpenseCurrency.getCode());
			}
			%>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			buffer="buffer"
			href="${viewHRExpenseCurrencyConversionURL}"
			name="to-currency"
		>
			<%
			long toHRExpenseCurrencyId = curHRExpenseCurrencyConversion.getToHRExpenseCurrencyId();

			if (toHRExpenseCurrencyId > 0) {
				HRExpenseCurrency toHRExpenseCurrency = HRExpenseCurrencyLocalServiceUtil.getHRExpenseCurrency(toHRExpenseCurrencyId);

				buffer.append(toHRExpenseCurrency.getCode());
			}
			%>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			href="${viewHRExpenseCurrencyConversionURL}"
			name="conversion-value"
			property="conversionValue"
		/>

		<portlet:renderURL var="editHRExpenseCurrencyConversionURL">
			<portlet:param name="controller" value="currency_conversions" />
			<portlet:param name="action" value="edit" />
			<portlet:param name="id" value="${curHRExpenseCurrencyConversion.hrExpenseCurrencyConversionId}" />
			<portlet:param name="format" value="html" />
		</portlet:renderURL>

		<liferay-ui:search-container-column-text
			href="${editHRExpenseCurrencyConversionURL}"
			value="edit"
		/>

		<portlet:actionURL var="deleteHRExpenseCurrencyConversionURL">
			<portlet:param name="controller" value="currency_conversions" />
			<portlet:param name="action" value="delete" />
			<portlet:param name="id" value="${curHRExpenseCurrencyConversion.hrExpenseCurrencyConversionId}" />
			<portlet:param name="format" value="html" />
		</portlet:actionURL>

		<liferay-ui:search-container-column-text
			href="javascript:submitForm(document.hrefFm, '${deleteHRExpenseCurrencyConversionURL}&p_p_state=normal');"
			value="x"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>
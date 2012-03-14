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

<%@ include file="/calendar/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

CalendarResource calendarResource = (CalendarResource)request.getAttribute(WebKeys.ENTERPRISE_CALENDAR_RESOURCE);
long calendarResourceId = 0;

List<Calendar> calendars;

if (calendarResource != null) {
	calendarResourceId = calendarResource.getCalendarResourceId();

	calendars = CalendarLocalServiceUtil.getResourceCalendars(themeDisplay.getScopeGroupId(), calendarResourceId);
}
else {
	calendars = Collections.emptyList();
}
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	title='<%= (calendarResource != null) ? calendarResource.getName(locale) : "new-calendar-resource" %>'
/>

<liferay-portlet:actionURL name="updateResource" var="updateResourceURL">
	<liferay-portlet:param name="mvcPath" value="/calendar/edit_resource.jsp" />
	<liferay-portlet:param name="redirect" value="<%= redirect %>" />
	<liferay-portlet:param name="calendarResourceId" value="<%= String.valueOf(calendarResourceId) %>" />
</liferay-portlet:actionURL>

<aui:form action="<%= updateResourceURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "updateResource();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (calendarResource == null) ? Constants.ADD : Constants.UPDATE %>" />

	<aui:model-context bean="<%= calendarResource %>" model="<%= CalendarResource.class %>" />

	<aui:fieldset>
		<aui:input name="code" />

		<aui:input name="name" />

		<aui:input name="description" />

		<aui:select name="type">
			<aui:option label="" value="" />
			<%
			for (String resourceType : PortletPropsValues.ENTERPRISE_CALENDAR_RESOURCE_TYPES) {
			%>
				<aui:option
					label="<%= resourceType %>"
					selected="<%= calendarResource != null && calendarResource.getType().equals(resourceType) %>"
					value="<%= resourceType %>"
				/>
			<%
			}
			%>
		</aui:select>

		<aui:select name="defaultCalendarId" label="default-calendar" >
			<%
			for (Calendar calendar : calendars) {
			%>
				<aui:option
					label="<%= calendar.getName(locale) %>"
					selected="<%= calendar.getCalendarId() == calendarResource.getDefaultCalendarId() %>"
					value="<%= calendar.getCalendarId() %>"
				/>
			<%
			}
			%>
		</aui:select>

		<aui:input inlineLabel="left" name="active" type="checkbox" value="<%= calendarResource == null ? true : calendarResource.isActive() %>" />

		<c:if test="<%= calendarResource == null %>">
			<aui:field-wrapper label="permissions">
				<liferay-ui:input-permissions modelName="<%= CalendarResource.class.getName() %>" />
			</aui:field-wrapper>
		</c:if>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:fieldset>

</aui:form>

<aui:script>
	function <portlet:namespace />updateResource() {
		submitForm(document.<portlet:namespace />fm);
	}

	Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />code);
</aui:script>
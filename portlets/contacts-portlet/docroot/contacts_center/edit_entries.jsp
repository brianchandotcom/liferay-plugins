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
String redirect = ParamUtil.getString(request, "redirect");

long entryId = ParamUtil.getLong(request, "entryId");

Entry entry = null;

if (entryId > 0) {
	entry = EntryLocalServiceUtil.getEntry(entryId);
}
%>

<div id="<portlet:namespace />errorMessage"></div>

<aui:form action="" method="post" name="addEntry" onSubmit="event.preventDefault();">
	<aui:input name="action" type="hidden"  value="<%= (entry != null) ? Constants.UPDATE : Constants.ADD %>" />
	<aui:input name="entryId" type="hidden"  value="<%= entryId %>" />
	<aui:input name="redirect" type="hidden"  value="<%= redirect %>" />

	<aui:model-context bean="<%= entry %>" model="<%= Entry.class %>" />

	<aui:input name="fullName" label="name" />
	<aui:input name="emailAddress" />
	<aui:input name="comments" />

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-io-request">
	Liferay.Util.focusFormField(document.<portlet:namespace />addEntry.<portlet:namespace />fullName);

	var form = A.one('#<portlet:namespace />addEntry');

	form.on(
		'submit',
		function(event) {
			A.io.request(
				'<liferay-portlet:actionURL name="addEntry" />',
				{
					after: {
						failure: function(event, id, obj) {
							var errorMessage = A.one('#<portlet:namespace/>errorMessage');

							if (errorMessage) {
								errorMessage.html('<span class="portlet-msg-error">' + Liferay.Language.get('an-error-occurred-while-retrieving-the-users-information') + '</span>');
							}
						},
						success: function(event, id, obj) {
							var responseData = this.get('responseData');

							if (!responseData.success) {
								var message = A.one('#<portlet:namespace />errorMessage');

								if (message) {
									message.html('<span class="portlet-msg-error">' + responseData.message + '</span>');
								}
							}
							else {
								<liferay-portlet:renderURL var="viewUserSummaryURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
									<portlet:param name="mvcPath" value="/contacts_center/view_resources.jsp" />
									<portlet:param name="redirect" value="<%= currentURL %>" />
									<portlet:param name="isUser" value="<%= String.valueOf(false) %>" />
								</liferay-portlet:renderURL>

								var ioRequest = A.io.request(
									'<%= viewUserSummaryURL %>',
									{
										after: {
											failure: function(event, id, obj) {
												var errorMessage = A.one('#<portlet:namespace/>errorMessage');

												if (errorMessage) {
													errorMessage.html('<span class="portlet-msg-error">' + Liferay.Language.get('an-error-occurred-while-retrieving-the-users-information') + '</span>');
												}
											},
											success: function(event, id, obj) {
												var instance = this;

												var fm = A.one('#<portlet:namespace />fm');

												var dialog = fm.getData('dialogInstance');

												Liferay.ContactsCenter.renderContent(this.get('responseData'));

												var searchInput = A.one('.contacts-portlet #<portlet:namespace />name');

												var contactFilterSelect = A.one('.contacts-portlet .contact-group-filter select[name=<portlet:namespace />socialRelationType]');

												Liferay.ContactsCenter.updateContacts(searchInput.get('value'), contactFilterSelect.get('value'));

												dialog.destroy();
											}
										},
										data: {
											entryId: responseData.entryId
										}
									}
								);
							}
						}
					},
					dataType: 'json',
					form: {
						id: form
					}
				}
			);
		}
	);
</aui:script>
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

<%@ include file="/html/portlet/portal_settings/init.jsp" %>

<%@ page import="com.liferay.portal.kernel.portlet.PortletClassLoaderUtil" %>
<%@ page import="com.liferay.portlet.social.model.SocialRelationConstants" %>

<%@ page import="java.lang.reflect.Method" %>

<h3><liferay-ui:message key="mentions" /><h3>

<%
PortletPreferences companyPortletPreferences = PrefsPropsUtil.getPreferences(company.getCompanyId(), true);

boolean mentionsEnabled = PrefsParamUtil.getBoolean(companyPortletPreferences, request, "mentionsEnabled", true);

ClassLoader classLoader = PortletClassLoaderUtil.getClassLoader("1_WAR_mentionsportlet");

Class<?> clazz = classLoader.loadClass("com.liferay.mentions.util.MentionsUtil");

Method method = clazz.getMethod("getAllSocialRelationTypes");

boolean mentionsAnyUser = PrefsParamUtil.getBoolean(companyPortletPreferences, request, "mentionsAnyUser", true);

boolean mentionsSitesEnabled = PrefsParamUtil.getBoolean(companyPortletPreferences, request, "mentionsSitesEnabled", true);

boolean mentionsSocialRelationTypesEnabled = PrefsParamUtil.getBoolean(companyPortletPreferences, request, "mentionsSocialRelationTypesEnabled", true);
%>

<aui:input checked="<%= mentionsEnabled %>" label="enable-mentions" name="settings--mentionsEnabled--" type="checkbox" value="<%= mentionsEnabled %>" />

<div class="mentions-settings" id="<portlet:namespace />mentionsSettings">
	<aui:input checked="<%= mentionsAnyUser %>" id="mentionsAnyUser" label="each-user-can-mention-any-user" name="settings--mentionsAnyUser--" type="radio" value="<%= true %>" />

	<aui:input checked="<%= !mentionsAnyUser %>" id="mentionsChooseUsers" label="each-user-can-mention" name="settings--mentionsAnyUser--" type="radio" value="<%= false %>" />

	<div class="mentions-users" id="<portlet:namespace />mentionsUsersWrapper">
		<aui:input checked="<%= mentionsSitesEnabled %>" label="users-that-belong-to-the-sites-that-the-user-also-belongs-to" name="settings--mentionsSitesEnabled--" type="checkbox" value="<%= mentionsSitesEnabled %>" />

		<aui:input checked="<%= mentionsSocialRelationTypesEnabled %>" label="users-with-the-following-social-relations" name="settings--mentionsSocialRelationTypesEnabled--" type="checkbox" value="<%= mentionsSocialRelationTypesEnabled %>" />

		<aui:input name="settings--socialRelationTypes--" type="hidden" />

		<%
		Map<String, Integer> allSocialRelationTypes = (Map<String, Integer>)method.invoke(null);
		%>

		<div class="social-relations" id="<portlet:namespace />socialRelations">
			<aui:field-wrapper>

				<%
				method = clazz.getMethod("getSocialRelationTypes", PortletPreferences.class, PortletRequest.class);

				// Left list

				List leftList = new ArrayList();

				String[] socialRelationTypesArray = (String[])method.invoke(null, companyPortletPreferences, renderRequest);

				for (String socialRelationType : socialRelationTypesArray) {
					leftList.add(new KeyValuePair(socialRelationType, LanguageUtil.get(pageContext, SocialRelationConstants.getLabel(allSocialRelationTypes.get(socialRelationType)))));
				}

				// Right list

				List rightList = new ArrayList();

				Set<String> allSocialRelationTypesTypesSet = allSocialRelationTypes.keySet();

				for (String socialRelationType : allSocialRelationTypesTypesSet) {
					if (Arrays.binarySearch(socialRelationTypesArray, socialRelationType) < 0) {
						rightList.add(new KeyValuePair(socialRelationType, LanguageUtil.get(pageContext, SocialRelationConstants.getLabel(allSocialRelationTypes.get(socialRelationType)))));
					}
				}
				%>

				<liferay-ui:input-move-boxes
					leftBoxName="currentSocialRelationTypes"
					leftList="<%= leftList %>"
					leftTitle="current"
					rightBoxName="availableSocialRelationTypes"
					rightList="<%= rightList %>"
					rightTitle="available"
				/>
			</aui:field-wrapper>
		</div>
	</div>
</div>

<aui:script use="aui-base,liferay-util-list-fields">
	Liferay.Util.toggleBoxes('<portlet:namespace />mentionsEnabledCheckbox','<portlet:namespace />mentionsSettings');
	Liferay.Util.toggleBoxes('<portlet:namespace />mentionsSocialRelationTypesEnabledCheckbox','<portlet:namespace />socialRelations');

	Liferay.Util.toggleRadio('<portlet:namespace />mentionsAnyUser', '', '<portlet:namespace />mentionsUsersWrapper');
	Liferay.Util.toggleRadio('<portlet:namespace />mentionsChooseUsers','<portlet:namespace />mentionsUsersWrapper', '');

	var form = A.one('#<portlet:namespace />fm');

	form.on('submit', function(e) {
		document.<portlet:namespace />fm.<portlet:namespace />socialRelationTypes.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentSocialRelationTypes);
	});
</aui:script>
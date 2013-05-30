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

package com.liferay.scripting.executor.groovy

import com.liferay.portal.NoSuchTeamException
import com.liferay.portal.model.Group
import com.liferay.portal.model.GroupConstants
import com.liferay.portal.service.GroupLocalServiceUtil
import com.liferay.portal.service.TeamLocalServiceUtil
import com.liferay.portal.service.UserLocalServiceUtil

/**
 * @author Michael C. Han
 */
class Site {

	static Site openSite(String name, String description) {
		def site = new Site();

		site.name = name;
		site.description = description;
		site.type = GroupConstants.TYPE_SITE_OPEN;

		return site;
	}

	static Site privateSite(String name, String description) {
		def site = new Site();

		site.name = name;
		site.description = description;
		site.type = GroupConstants.TYPE_SITE_PRIVATE;

		return site;
	}

	static Site restrictedSite(String name, String description) {
		def site = new Site();

		site.name = name;
		site.description = description;
		site.type = GroupConstants.TYPE_SITE_RESTRICTED;

		return site;
	}

	void addOrganizations(
		ScriptingContext scriptingContext, String... organizationNames) {

		for (String organizationName : organizationNames) {
			def organization = Organization.fetchOrganization(
					scriptingContext, organizationName);

			if (organization != null) {
				GroupLocalServiceUtil.addOrganizationGroup(
					organization.getOrganizationId(), liferaySite);
			}
		}
	}

	void addTeamUserGroupMembers(
		ScriptingContext scriptingContext, String teamName,
		String... userGroupNames) {

		def team = null;

		try {
			team = TeamLocalServiceUtil.getTeam(
				liferaySite.getGroupId(), teamName);
		}
		catch (NoSuchTeamException nste) {
			team = TeamLocalServiceUtil.addTeam(
				scriptingContext.defaultUserId, liferaySite.getGroupId(),
				teamName, null);
		}

		for (String userGroupName : userGroupNames) {
			def userGroup = UserGroup.fetchUserGroup(
				scriptingContext, userGroupName);

			if (userGroup != null) {
				TeamLocalServiceUtil.addUserGroupTeam(
					userGroup.getUserGroupId(), team);
			}
		}
	}

	void addTeamUserMembers(
		ScriptingContext scriptingContext, String teamName,
		String... screenNames) {

		def team = null;

		try {
			team = TeamLocalServiceUtil.getTeam(
				liferaySite.getGroupId(), teamName);
		}
		catch (NoSuchTeamException nste) {
			team = TeamLocalServiceUtil.addTeam(
				scriptingContext.defaultUserId, liferaySite.getGroupId(),
				teamName, null);
		}

		for (String screenName : screenNames) {
			def user = UserLocalServiceUtil.fetchUserByScreenName(
				scriptingContext.companyId, screenName);

			if (user != null) {
				TeamLocalServiceUtil.addUserTeam(user.getUserId(), team);
			}
		}
	}

	void create(ScriptingContext scriptingContext) {
		liferaySite = GroupLocalServiceUtil.fetchGroup(
			scriptingContext.companyId, name);

		if (liferaySite != null) {
			return;
		}

		liferaySite = _addSite(scriptingContext)
	}

	private Group _addSite(ScriptingContext scriptingContext) {

		return GroupLocalServiceUtil.addGroup(
			scriptingContext.defaultUserId, null, 0, 0, name, description, type,
			null, true, true, scriptingContext.serviceContext);
	}

	String description;
	Group liferaySite;
	String name;
	int type;

}

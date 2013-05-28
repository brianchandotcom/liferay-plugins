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

package com.liferay.scripting.executor.groovy;

import com.liferay.portal.kernel.util.LocaleUtil
import com.liferay.portal.model.Group
import com.liferay.portal.model.Organization
import com.liferay.portal.model.Role
import com.liferay.portal.service.GroupLocalServiceUtil
import com.liferay.portal.service.OrganizationLocalServiceUtil
import com.liferay.portal.service.RoleLocalServiceUtil
import com.liferay.portal.service.UserLocalServiceUtil

class User {

	User(
		String firstName_, String lastName_, String jobTitle_, String email_,
		String password_) {

		firstName = firstName_;
		lastName = lastName_;
		jobTitle = jobTitle_;
		email = email_;
		password = password_;
	}

	void addRoles(
		ScriptingContext scriptingContext, String... roleNames) {

		def roles = new ArrayList<Role>(roleNames.length);

		for (String roleName : roleNames) {
			Role role = RoleLocalServiceUtil.fetchRole(
				scriptingContext.companyId, roleName);

			roles.add(role);
		}

		RoleLocalServiceUtil.addUserRoles(liferayUser.getUserId(), roles);
	}

	void create(
		ScriptingContext scriptingContext) {

		liferayUser = UserLocalServiceUtil.fetchUserByEmailAddress(
			scriptingContext.companyId, email);

		if (liferayUser != null) {
			return;
		}

		liferayUser = UserLocalServiceUtil.addUser(
			scriptingContext.defaultUserId,
			scriptingContext.companyId, false, password, password, true,
			null, email, 0, null, LocaleUtil.getDefault(), firstName, null,
			lastName, -1, -1, true, 1, 1, 1977, jobTitle, new long[0],
			new long[0], new long[0], new long[0], false,
			scriptingContext.serviceContext);
	}

	void joinOrganizations(
		ScriptingContext scriptingContext, String... organizationNames) {

		for (String organizationName : organizationNames) {
			def organization =
				OrganizationLocalServiceUtil.fetchOrganization(
					scriptingContext.companyId, organizationName);

			UserLocalServiceUtil.addOrganizationUser(
				organization.getOrganizationId(), liferayUser.getUserId());
		}
	}

	void joinSites(
		ScriptingContext liferayScriptingContext, String... siteNames) {

		for (String siteName : siteNames) {
			def group = GroupLocalServiceUtil.fetchGroup(
				liferayScriptingContext.companyId, siteName);

			UserLocalServiceUtil.addGroupUser(
				group.getGroupId(), liferayUser.getUserId());
		}
	}

	String email;
	String firstName;
	String jobTitle;
	String lastName;
	com.liferay.portal.model.User liferayUser;
	String password;

}
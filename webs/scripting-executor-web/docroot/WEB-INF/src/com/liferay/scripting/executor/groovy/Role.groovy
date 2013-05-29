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

import com.liferay.portal.model.RoleConstants
import com.liferay.portal.service.RoleLocalServiceUtil;

/**
 * @author Michael C. Han
 */
class Role {

	static Role organizationRole(String roleName, String description) {
		def role = new Role();

		role.name = roleName;
		role.description = description;
		role.type = RoleConstants.TYPE_ORGANIZATION;

		return role;
	}

	static Role portalRole(String roleName, String description) {
		def role = new Role();

		role.name = roleName;
		role.description = description;
		role.type = RoleConstants.TYPE_REGULAR;

		return role;
	}

	static Role siteRole(String roleName, String description) {
		def role = new Role();

		role.name = roleName;
		role.description = description;
		role.type = RoleConstants.TYPE_SITE;

		return role;
	}

	void create(ScriptingContext scriptingContext) {

		liferayRole = RoleLocalServiceUtil.fetchRole(
			scriptingContext.companyId, name);

		if (liferayRole != null) {
			return;
		}

		liferayRole = _addRole(scriptingContext)
	}

	private com.liferay.portal.model.Role _addRole(
		ScriptingContext scriptingContext) {

		return RoleLocalServiceUtil.addRole(
			scriptingContext.defaultUserId, scriptingContext.companyId,
			name, ScriptingContext.getLocalizedMap(name),
			ScriptingContext.getLocalizedMap(description), type);
	}

	String description;
	String name;
	com.liferay.portal.model.Role liferayRole;
	int type;

}

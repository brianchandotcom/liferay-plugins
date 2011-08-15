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

package com.liferay.vldap.server.directory.builder;

import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.vldap.server.directory.DirectoryTree;
import com.liferay.vldap.server.directory.FilterConstraint;
import com.liferay.vldap.server.directory.SearchBase;
import com.liferay.vldap.server.directory.ldap.LdapDirectory;
import com.liferay.vldap.server.directory.ldap.UserGroupDirectory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.directory.shared.ldap.model.name.Dn;

/**
 * @author Jonathan Potter
 */
public class UserGroupBuilder extends DirectoryBuilder {

	@Override
	public List<LdapDirectory> buildThisLevel(
		Set<FilterConstraint> constraints, SearchBase base)
		throws Exception {

		List<UserGroup> userGroups = new ArrayList<UserGroup>();

		if (constraints == null) {
			// No constraints so find all user groups
			userGroups =
				UserGroupLocalServiceUtil.getUserGroups(
					base.getCompany().getCompanyId());
		}
		else {
			for (FilterConstraint constraint : constraints) {
				if (!isValidConstraint(constraint)) {
					continue;
				}

				String name = constraint.getValue("ou");
				String memberString = constraint.getValue("member");
				String description = constraint.getValue("description");

				if (name == null) {
					name = constraint.getValue("cn");
				}

				String memberScreenName =
					DirectoryTree.getRdnValue(new Dn(memberString), 3);

				if (memberScreenName == null) {
					userGroups.addAll(UserGroupLocalServiceUtil.search(
						base.getCompany().getCompanyId(), name, description,
						null, 0, (int)base.getSizeLimit(), null));
				}
				else {
					User memberUser =
						UserLocalServiceUtil.getUserByScreenName(
							base.getCompany().getCompanyId(), memberScreenName);
					for (UserGroup userGroup : memberUser.getUserGroups()) {
						if (name != null) {
							if (!name.equals(userGroup.getName())) {
								continue;
							}
						}

						if (description != null) {
							if (!description.equals(
								userGroup.getDescription())) {

								continue;
							}
						}

						userGroups.add(userGroup);
					}
				}
			}
		}

		List<LdapDirectory> directories = new ArrayList<LdapDirectory>();

		for (UserGroup userGroup: userGroups) {
			directories.add(new UserGroupDirectory(
				base.getTop(), base.getCompany(), userGroup));
		}

		return directories;
	}

	@Override
	public boolean isValidAttribute (String attribute, String value) {
		if (attribute.equalsIgnoreCase("objectClass")) {
			if (value.equalsIgnoreCase("groupOfNames")
				|| value.equalsIgnoreCase("organizationalUnit")
				|| value.equalsIgnoreCase("top")
				|| value.equalsIgnoreCase("*")) {

				return true;
			}
		}
		else if (attribute.equalsIgnoreCase("cn")) {
			return true;
		}
		else if (attribute.equalsIgnoreCase("ou")) {
			return true;
		}
		else if (attribute.equalsIgnoreCase("member")) {
			return true;
		}
		else if (attribute.equalsIgnoreCase("description")) {
			return true;
		}

		return false;
	}

}
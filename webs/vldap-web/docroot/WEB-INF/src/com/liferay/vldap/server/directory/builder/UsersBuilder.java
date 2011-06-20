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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.liferay.vldap.server.directory.FilterConstraint;
import com.liferay.vldap.server.directory.SearchBase;
import com.liferay.vldap.server.directory.ldap.LdapDirectory;
import com.liferay.vldap.server.directory.ldap.UsersDirectory;

/**
 * @author Jonathan Potter
 */
public class UsersBuilder extends DirectoryBuilder {

	@Override
	public List<LdapDirectory> buildThisLevel(
		Set<FilterConstraint> constraints, SearchBase base)
		throws Exception {

		List<LdapDirectory> directories = new ArrayList<LdapDirectory>();
		
		if (constraints == null) {
			directories.add(new UsersDirectory(
				base.getTop(), base.getCompany()));
		} else {
			for (FilterConstraint constraint : constraints) {
				if (isValidConstraint(constraint)) {
					directories.add(new UsersDirectory(
						base.getTop(), base.getCompany()));
					break;
				}
			}
		}
		
		return directories;
	}
	
	@Override
	public boolean isValidAttribute (String attribute, String value) {
		if (attribute.equalsIgnoreCase("objectClass")) {
			if (value.equalsIgnoreCase("organizationalUnit")
				|| value.equalsIgnoreCase("top")
				|| value.equalsIgnoreCase("*")) {

				return true;
			}
		}
		else if (attribute.equalsIgnoreCase("ou")) {
			if (value.equalsIgnoreCase("Communities")
				|| value.equalsIgnoreCase("*")) {
				return true;
			}
		}

		return false;
	}

}
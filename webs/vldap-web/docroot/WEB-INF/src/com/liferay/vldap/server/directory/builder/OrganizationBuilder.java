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

import org.apache.directory.shared.ldap.model.name.Dn;

import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.vldap.server.directory.DirectoryTree;
import com.liferay.vldap.server.directory.FilterConstraint;
import com.liferay.vldap.server.directory.SearchBase;
import com.liferay.vldap.server.directory.ldap.LdapDirectory;
import com.liferay.vldap.server.directory.ldap.OrganizationDirectory;

/**
 * @author Jonathan Potter
 */
public class OrganizationBuilder extends DirectoryBuilder {
	
	@Override
	public List<LdapDirectory> buildThisLevel(
		Set<FilterConstraint> constraints, SearchBase base)
		throws Exception {

		List<Organization> organizations = new ArrayList<Organization>();
		
		if (constraints == null) {
			// No constraints so find all organizations
			organizations =
				OrganizationLocalServiceUtil.getOrganizations(
					base.getCompany().getCompanyId(),
					OrganizationConstants.ANY_PARENT_ORGANIZATION_ID);
		}
		else {
			for (FilterConstraint constraint : constraints) {
				if (!isValidConstraint(constraint)) {
					continue;
				}
				
				String name = constraint.getValue("ou");
				String memberString = constraint.getValue("member");
				
				if (name == null) {
					name = constraint.getValue("cn");
				}
				
				String memberScreenName =
					DirectoryTree.getRdnValue(new Dn(memberString), 3);
				
				if (memberScreenName == null) {
					organizations.addAll(OrganizationLocalServiceUtil.search(
						base.getCompany().getCompanyId(),
						OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, name,
						null, null, null, null, null, null, null, true, 0,
						(int)base.getSizeLimit()));
				}
				else {
					User memberUser =
						UserLocalServiceUtil.getUserByScreenName(
							base.getCompany().getCompanyId(), memberScreenName);
					for (Organization organization : 
						memberUser.getOrganizations()) {
						
						if (name != null) {
							if (!name.equals(organization.getName())) {
								continue;
							}
						}

						organizations.add(organization);
					}
				}
			}
		}
		
		List<LdapDirectory> directories = new ArrayList<LdapDirectory>();
		
		for (Organization organization: organizations) {
			directories.add(new OrganizationDirectory(
				base.getTop(), base.getCompany(), organization));
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

		return false;
	}

}

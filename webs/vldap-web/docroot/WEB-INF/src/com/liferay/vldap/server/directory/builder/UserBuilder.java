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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.directory.shared.ldap.model.name.Dn;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.comparator.UserScreenNameComparator;
import com.liferay.vldap.server.directory.DirectoryTree;
import com.liferay.vldap.server.directory.FilterConstraint;
import com.liferay.vldap.server.directory.SearchBase;
import com.liferay.vldap.server.directory.ldap.LdapDirectory;
import com.liferay.vldap.server.directory.ldap.UserDirectory;

/**
 * @author Jonathan Potter
 */
public class UserBuilder extends DirectoryBuilder {

	@Override
	public List<LdapDirectory> buildThisLevel(
		Set<FilterConstraint> constraints, SearchBase base)
		throws Exception {

		List<User> users = new ArrayList<User>();
		
		LinkedHashMap<String, Object> params = base.generateParams();
		
		if (constraints == null) {
			// No constraints so find all users
			users =
				UserLocalServiceUtil.getCompanyUsers(
					base.getCompany().getCompanyId(), 0,
					(int)base.getSizeLimit());
		}
		else {
			for (FilterConstraint constraint : constraints) {
				if (!isValidConstraint(constraint)) {
					continue;
				}
				
				String screenName = constraint.getValue("cn");
				String memberString = constraint.getValue("member");
				String lastName = constraint.getValue("sn");
				String firstName = constraint.getValue("givenName");
				String email = constraint.getValue("mail");
				String uidString = constraint.getValue("uid");
				String uuid = constraint.getValue("uuid");
				
				boolean satisfiable = addMemberParams(memberString, params);
				
				if (!satisfiable) {
					continue;
				}
				
				if (uidString != null) {
					long uid = Long.parseLong(uidString);
					users.add(UserLocalServiceUtil.getUser(uid));
				}
				else if (uuid != null) {
					users.add(UserLocalServiceUtil.getUserByUuid(uuid));
				}
				else {
					users.addAll(UserLocalServiceUtil.search(
						base.getCompany().getCompanyId(), firstName, null,
						lastName, screenName, email,
						WorkflowConstants.STATUS_APPROVED, params, true, 0,
						(int)base.getSizeLimit(),
						new UserScreenNameComparator()));
				}
			}
		}
		
		List<LdapDirectory> directories = new ArrayList<LdapDirectory>();
		
		for (User user: users) {
			directories.add(new UserDirectory(
				base.getTop(), base.getCompany(), user));
		}
		
		return directories;
	}
	
	@Override
	public boolean isValidAttribute (String attribute, String value) {
		if (attribute.equalsIgnoreCase("objectClass")) {
			if (value.equalsIgnoreCase("groupOfNames")
				|| value.equalsIgnoreCase("inetOrgPerson")
				|| value.equalsIgnoreCase("liferayPerson")
				|| value.equalsIgnoreCase("top")
				|| value.equalsIgnoreCase("*")) {

				return true;
			}
		}
		else if (attribute.equalsIgnoreCase("cn")) {
			return true;
		}
		else if (attribute.equalsIgnoreCase("member")) {
			return true;
		}
		else if (attribute.equalsIgnoreCase("sn")) {
			return true;
		}
		else if (attribute.equalsIgnoreCase("displayName")) {
			if (value.equalsIgnoreCase("*")) {
				return true;
			}
		}
		else if (attribute.equalsIgnoreCase("givenName")) {
			return true;
		}
		else if (attribute.equalsIgnoreCase("mail")) {
			return true;
		}
		else if (attribute.equalsIgnoreCase("uid")) {
			return true;
		}
		else if (attribute.equalsIgnoreCase("createTimestamp")) {
			if (value.equalsIgnoreCase("*")) {
				return true;
			}
		}
		else if (attribute.equalsIgnoreCase("modifyTimestamp")) {
			if (value.equalsIgnoreCase("*")) {
				return true;
			}
		}
		else if (attribute.equalsIgnoreCase("uuid")) {
			return true;
		}

		return false;
	}
	
	protected boolean addMemberParams(
		String memberFilter, Map<String, Object> params)
		throws Exception {

		if (memberFilter == null) {
			return true;
		}
		
		Dn dn = new Dn(memberFilter);
		
		String companyWebId = DirectoryTree.getRdnValue(dn, 1);
		String category = DirectoryTree.getRdnValue(dn, 2);
		String categoryValue = DirectoryTree.getRdnValue(dn, 3);

		Company company;
		
		try {
			company = CompanyLocalServiceUtil.getCompanyByWebId(companyWebId);
		} catch (Exception e) {
			_log.error(e);
			return false;
		}

		if (company == null) {
			return false;
		}

		long companyId = company.getCompanyId();
		
		try {
			if (category.equalsIgnoreCase("Communities")) {
				Group group =
					GroupLocalServiceUtil.getGroup(companyId, categoryValue);
				params.put("usersGroups", group.getGroupId());
			}
			else if (category.equalsIgnoreCase("Organizations")) {
				Organization organization =
					OrganizationLocalServiceUtil.getOrganization(
						companyId, categoryValue);
				params.put("usersOrgs", organization.getOrganizationId());
			}
			else if (category.equalsIgnoreCase("Roles")) {
				Role role =
					RoleLocalServiceUtil.getRole(companyId, categoryValue);
				params.put("usersRoles", role.getRoleId());
			}
			else if (category.equalsIgnoreCase("User Groups")) {
				UserGroup userGroup =
					UserGroupLocalServiceUtil.getUserGroup(
						companyId, categoryValue);
				params.put("usersUserGroups", userGroup.getUserGroupId());
			}
			else {
				return false;
			}
		} catch (Exception e) {
			_log.error(e);
		}
		
		return true;
	}

	protected static Log _log = LogFactoryUtil.getLog(UserBuilder.class);
	
}
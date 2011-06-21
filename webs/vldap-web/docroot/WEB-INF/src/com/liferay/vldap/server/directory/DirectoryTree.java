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

package com.liferay.vldap.server.directory;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
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
import com.liferay.vldap.server.directory.builder.CommunitiesBuilder;
import com.liferay.vldap.server.directory.builder.CommunityBuilder;
import com.liferay.vldap.server.directory.builder.CompanyBuilder;
import com.liferay.vldap.server.directory.builder.DirectoryBuilder;
import com.liferay.vldap.server.directory.builder.OrganizationBuilder;
import com.liferay.vldap.server.directory.builder.OrganizationsBuilder;
import com.liferay.vldap.server.directory.builder.RoleBuilder;
import com.liferay.vldap.server.directory.builder.RolesBuilder;
import com.liferay.vldap.server.directory.builder.RootBuilder;
import com.liferay.vldap.server.directory.builder.TopBuilder;
import com.liferay.vldap.server.directory.builder.UserBuilder;
import com.liferay.vldap.server.directory.builder.UserGroupBuilder;
import com.liferay.vldap.server.directory.builder.UserGroupsBuilder;
import com.liferay.vldap.server.directory.builder.UsersBuilder;
import com.liferay.vldap.server.directory.ldap.CommunitiesDirectory;
import com.liferay.vldap.server.directory.ldap.CommunityDirectory;
import com.liferay.vldap.server.directory.ldap.CompanyDirectory;
import com.liferay.vldap.server.directory.ldap.LdapDirectory;
import com.liferay.vldap.server.directory.ldap.OrganizationDirectory;
import com.liferay.vldap.server.directory.ldap.OrganizationsDirectory;
import com.liferay.vldap.server.directory.ldap.RoleDirectory;
import com.liferay.vldap.server.directory.ldap.RolesDirectory;
import com.liferay.vldap.server.directory.ldap.RootDirectory;
import com.liferay.vldap.server.directory.ldap.TopDirectory;
import com.liferay.vldap.server.directory.ldap.UserDirectory;
import com.liferay.vldap.server.directory.ldap.UserGroupDirectory;
import com.liferay.vldap.server.directory.ldap.UserGroupsDirectory;
import com.liferay.vldap.server.directory.ldap.UsersDirectory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.directory.shared.ldap.model.filter.AndNode;
import org.apache.directory.shared.ldap.model.filter.BranchNode;
import org.apache.directory.shared.ldap.model.filter.EqualityNode;
import org.apache.directory.shared.ldap.model.filter.ExprNode;
import org.apache.directory.shared.ldap.model.filter.GreaterEqNode;
import org.apache.directory.shared.ldap.model.filter.LessEqNode;
import org.apache.directory.shared.ldap.model.filter.NotNode;
import org.apache.directory.shared.ldap.model.filter.OrNode;
import org.apache.directory.shared.ldap.model.filter.PresenceNode;
import org.apache.directory.shared.ldap.model.filter.SubstringNode;
import org.apache.directory.shared.ldap.model.message.SearchScope;
import org.apache.directory.shared.ldap.model.name.Dn;
import org.apache.directory.shared.ldap.model.name.Rdn;

/**
 * @author Jonathan Potter
 */
public class DirectoryTree {

	RootBuilder _rootBuilder;
	TopBuilder _topBuilder;
	CompanyBuilder _companyBuilder;

	CommunitiesBuilder _communitiesBuilder;
	OrganizationsBuilder _organizationsBuilder;
	RolesBuilder _rolesBuilder;
	UserGroupsBuilder _userGroupsBuilder;
	UsersBuilder _usersBuilder;

	CommunityBuilder _communityBuilder;
	OrganizationBuilder _organizationBuilder;
	RoleBuilder _roleBuilder;
	UserGroupBuilder _userGroupBuilder;
	UserBuilder _userBuilder;

	public DirectoryTree() {

		_rootBuilder = new RootBuilder();
		_topBuilder = new TopBuilder();
		_companyBuilder = new CompanyBuilder();

		_communitiesBuilder = new CommunitiesBuilder();
		_organizationsBuilder = new OrganizationsBuilder();
		_rolesBuilder = new RolesBuilder();
		_userGroupsBuilder = new UserGroupsBuilder();
		_usersBuilder = new UsersBuilder();

		_communityBuilder = new CommunityBuilder();
		_organizationBuilder = new OrganizationBuilder();
		_roleBuilder = new RoleBuilder();
		_userGroupBuilder = new UserGroupBuilder();
		_userBuilder = new UserBuilder();

		_rootBuilder.addChild(_topBuilder);
		_topBuilder.addChild(_companyBuilder);

		_companyBuilder.addChild(_communitiesBuilder);
		_companyBuilder.addChild(_organizationsBuilder);
		_companyBuilder.addChild(_rolesBuilder);
		_companyBuilder.addChild(_userGroupsBuilder);
		_companyBuilder.addChild(_usersBuilder);

		_communitiesBuilder.addChild(_communityBuilder);
		_organizationsBuilder.addChild(_organizationBuilder);
		_rolesBuilder.addChild(_roleBuilder);
		_userGroupsBuilder.addChild(_userGroupBuilder);
		_usersBuilder.addChild(_userBuilder);

		_communityBuilder.addChild(_userBuilder);
		_organizationBuilder.addChild(_userBuilder);
		_roleBuilder.addChild(_userBuilder);
		_userGroupBuilder.addChild(_userBuilder);
	}

	public SearchBase findBase(Dn dn, long sizeLimit)
		throws Exception {

		if (dn == null) {
			return null;
		}

		String top = getRdnValue(dn, 0);
		String companyWebId = getRdnValue(dn, 1);
		String category = getRdnValue(dn, 2);
		String categoryValue = getRdnValue(dn, 3);
		String screenName = getRdnValue(dn, 4);

		if (top == null || top.equals("")) {
			return new SearchBase(new RootDirectory(), _rootBuilder);
		}

		if (!top.equalsIgnoreCase("Liferay")) {
			return null;
		}

		if (companyWebId == null) {
			return new SearchBase(new TopDirectory(top), _topBuilder, top);
		}

		Company company =
			CompanyLocalServiceUtil.getCompanyByWebId(companyWebId);

		if (company == null) {
			return null;
		}

		long companyId = company.getCompanyId();

		if (category == null) {
			return new SearchBase(
				new CompanyDirectory(top, company), _companyBuilder, top,
				company);
		}

		if (categoryValue == null) {
			if (category.equalsIgnoreCase("Communities")) {
				return new SearchBase(
					new CommunitiesDirectory(top, company),
					_communitiesBuilder, top, company);
			}
			else if (category.equalsIgnoreCase("Organizations")) {
				return new SearchBase(
					new OrganizationsDirectory(top, company),
					_organizationsBuilder, top, company);
			}
			else if (category.equalsIgnoreCase("Roles")) {
				return new SearchBase(
					new RolesDirectory(top, company), _rolesBuilder, top,
					company);
			}
			else if (category.equalsIgnoreCase("User Groups")) {
				return new SearchBase(
					new UserGroupsDirectory(top, company), _userGroupsBuilder,
					top, company);
			}
			else if (category.equalsIgnoreCase("Users")) {
				return new SearchBase(
					new UsersDirectory(top, company), _usersBuilder, top,
					company);
			}
		}

		Group community = null;
		Organization organization = null;
		Role role = null;
		UserGroup userGroup = null;

		if (category.equalsIgnoreCase("Communities")) {
			community =
				GroupLocalServiceUtil.getGroup(companyId, categoryValue);
		}
		else if (category.equalsIgnoreCase("Organizations")) {
			organization =
				OrganizationLocalServiceUtil.getOrganization(
					companyId, categoryValue);
		}
		else if (category.equalsIgnoreCase("Roles")) {
			role = RoleLocalServiceUtil.getRole(companyId, categoryValue);
		}
		else if (category.equalsIgnoreCase("User Groups")) {
			userGroup =
				UserGroupLocalServiceUtil.getUserGroup(
					companyId, categoryValue);
		}

		if (screenName == null) {
			if (category.equalsIgnoreCase("Communities")) {
				return new SearchBase(
					new CommunityDirectory(top, company, community),
					_communityBuilder, top, company, community);
			}
			else if (category.equalsIgnoreCase("Organizations")) {
				return new SearchBase(
					new OrganizationDirectory(top, company, organization),
					_organizationBuilder, top, company, organization);
			}
			else if (category.equalsIgnoreCase("Roles")) {
				return new SearchBase(
					new RoleDirectory(top, company, role), _roleBuilder, top,
					company, role);
			}
			else if (category.equalsIgnoreCase("User Groups")) {
				return new SearchBase(
					new UserGroupDirectory(top, company, userGroup),
					_userGroupBuilder, top, company, userGroup);
			}
			else if (category.equalsIgnoreCase("Users")) {
				User user =
					UserLocalServiceUtil.getUserByScreenName(
						companyId, categoryValue);
				return new SearchBase(
					new UserDirectory(top, company, user), _userBuilder, top,
					company, user);
			}
		}

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		if (community != null) {
			params.put("usersGroups", community.getGroupId());
		}
		else if (organization != null) {
			params.put("usersOrgs", organization.getOrganizationId());
		}
		else if (role != null) {
			params.put("usersRoles", role.getRoleId());
		}
		else if (userGroup != null) {
			params.put("usersUserGroups", userGroup.getUserGroupId());
		}

		OrderByComparator orderByComparator = new UserScreenNameComparator();
		List<User> users =
			UserLocalServiceUtil.search(
				companyId, null, null, null, screenName, null,
				WorkflowConstants.STATUS_APPROVED, params, true, 0,
				(int)sizeLimit, orderByComparator);

		if (users.isEmpty()) {
			return null;
		}

		return new SearchBase(
			new UserDirectory(top, company, users.get(0)), _userBuilder, top,
			company, users.get(0));
	}

	public List<LdapDirectory> findRest(
		SearchBase base, ExprNode filter, SearchScope scope) {

		List<LdapDirectory> directories = new ArrayList<LdapDirectory>();

		Set<FilterConstraint> constraints = toConstraints(filter);
		boolean allLevels = scope.equals(SearchScope.SUBTREE);

		for (DirectoryBuilder child :
			base.getDirectoryBuilder().getChildren()) {

			directories.addAll(child.buildDirectories(
				constraints, allLevels, base));
		}

		return directories;
	}

	public static Set<FilterConstraint> toConstraints(ExprNode filter) {

		if (filter.isLeaf()) {
			if (filter instanceof EqualityNode<?>) {
				EqualityNode<?> equalityNode = (EqualityNode<?>) filter;

				String attributeId = equalityNode.getAttribute();
				String value = equalityNode.getValue().getString();

				Set<FilterConstraint> constraints =
					new HashSet<FilterConstraint>();
				FilterConstraint constraint = new FilterConstraint();
				constraint.addAttribute(attributeId, value);
				constraints.add(constraint);

				return constraints;
			}
			else if (filter instanceof GreaterEqNode<?>) {
			}
			else if (filter instanceof LessEqNode<?>) {
			}
			else if (filter instanceof PresenceNode) {
				PresenceNode specificFilter = (PresenceNode) filter;

				String attributeId = specificFilter.getAttribute();

				Set<FilterConstraint> constraints =
					new HashSet<FilterConstraint>();
				FilterConstraint constraint = new FilterConstraint();
				constraint.addAttribute(attributeId, "*");
				constraints.add(constraint);

				return constraints;
			}
			else if (filter instanceof SubstringNode) {
			}
			else {
				_log.error("Unsupported expression " + filter);
			}
		}
		else {
			BranchNode branchNode = (BranchNode) filter;

			if (filter instanceof AndNode) {
				// Build up constraints as we iterate through child nodes
				Set<FilterConstraint> constraints =
					new HashSet<FilterConstraint>();

				for (ExprNode childBranchNode : branchNode.getChildren()) {
					Set<FilterConstraint> childConstraints =
						toConstraints(childBranchNode);

					if (childConstraints != null) {
						constraints =
							FilterConstraint.allCombinations(
								constraints, childConstraints);
					}
				}

				return constraints;
			}
			else if (filter instanceof NotNode) {
				// We don't support the "Not" operator because it's difficult
				// and almost never used in queries
			}
			else if (filter instanceof OrNode) {
				Set<FilterConstraint> constraints =
					new HashSet<FilterConstraint>();

				for (ExprNode childBranchNode : branchNode.getChildren()) {
					Set<FilterConstraint> childConstraints =
						toConstraints(childBranchNode);

					if (childConstraints != null) {
						constraints.addAll(childConstraints);
					}
				}

				return constraints;
			}
		}

		return null;
	}

	public static String getRdnValue(Dn dn, int rdnIndex) {
		try {
			rdnIndex = dn.size() - 1 - rdnIndex;

			if (rdnIndex < dn.size()) {
				Rdn rdn = dn.getRdn(rdnIndex);
				return rdn.getValue(rdn.getNormType()).toString();
			}
		}
		catch (Exception e) {
		}

		return null;
	}

	protected static Log _log = LogFactoryUtil.getLog(DirectoryBuilder.class);

}
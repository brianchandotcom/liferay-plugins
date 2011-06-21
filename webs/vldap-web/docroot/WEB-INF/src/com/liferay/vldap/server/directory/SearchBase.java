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

import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.vldap.server.directory.builder.DirectoryBuilder;
import com.liferay.vldap.server.directory.ldap.LdapDirectory;

import java.util.LinkedHashMap;

/**
 * @author Jonathan Potter
 */
public class SearchBase {

	public SearchBase(
		LdapDirectory ldapDirectory, DirectoryBuilder directoryBuilder) {

		_ldapDirectory = ldapDirectory;
		_directoryBuilder = directoryBuilder;
	}

	public SearchBase(
		LdapDirectory ldapDirectory, DirectoryBuilder directoryBuilder,
		String top) {

		this(ldapDirectory, directoryBuilder);
		_top = top;
	}

	public SearchBase(
		LdapDirectory ldapDirectory, DirectoryBuilder directoryBuilder,
		String top, Company company) {

		this(ldapDirectory, directoryBuilder, top);
		_company = company;
	}

	public SearchBase(
		LdapDirectory ldapDirectory, DirectoryBuilder directoryBuilder,
		String top, Company company, UserGroup userGroup) {

		this(ldapDirectory, directoryBuilder, top, company);
		_userGroup = userGroup;
	}

	public SearchBase(
		LdapDirectory ldapDirectory, DirectoryBuilder directoryBuilder,
		String top, Company company, Role role) {

		this(ldapDirectory, directoryBuilder, top, company);
		_role = role;
	}

	public SearchBase(
		LdapDirectory ldapDirectory, DirectoryBuilder directoryBuilder,
		String top, Company company, Organization organization) {

		this(ldapDirectory, directoryBuilder, top, company);
		_organization = organization;
	}

	public SearchBase(
		LdapDirectory ldapDirectory, DirectoryBuilder directoryBuilder,
		String top, Company company, Group community) {

		this(ldapDirectory, directoryBuilder, top, company);
		_community = community;
	}

	public SearchBase(
		LdapDirectory ldapDirectory, DirectoryBuilder directoryBuilder,
		String top, Company company, User user) {

		this(ldapDirectory, directoryBuilder, top, company);
		_user = user;
	}

	public DirectoryBuilder getDirectoryBuilder() {
		return _directoryBuilder;
	}

	public LdapDirectory getLdapDirectory() {
		return _ldapDirectory;
	}

	public String getTop() {
		return _top;
	}

	public Company getCompany() {
		return _company;
	}

	public Group getCommunity() {
		return _community;
	}

	public Organization getOrganization() {
		return _organization;
	}

	public Role getRole() {
		return _role;
	}

	public long getSizeLimit() {
		return _sizeLimit;
	}

	public void setSizeLimit(long sizeLimit) {
		_sizeLimit = sizeLimit;
	}

	public UserGroup getUserGroup() {
		return _userGroup;
	}

	public User getUser() {
		return _user;
	}

	public LinkedHashMap<String, Object> generateParams() {
		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		if (_community != null) {
			params.put("usersGroups", _community.getGroupId());
		}
		if (_organization != null) {
			params.put("usersOrgs", _organization.getOrganizationId());
		}
		if (_role != null) {
			params.put("usersRoles", _role.getRoleId());
		}
		if (_userGroup != null) {
			params.put("usersUserGroups", _userGroup.getUserGroupId());
		}

		return params;
	}

	protected Group _community;
	protected Company _company;
	protected Organization _organization;
	protected Role _role;
	protected String _top;
	protected User _user;
	protected UserGroup _userGroup;

	protected DirectoryBuilder _directoryBuilder;
	protected LdapDirectory _ldapDirectory;

	protected long _sizeLimit;

}
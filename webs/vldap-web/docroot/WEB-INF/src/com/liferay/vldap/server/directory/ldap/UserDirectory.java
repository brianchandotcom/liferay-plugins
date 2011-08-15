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

package com.liferay.vldap.server.directory.ldap;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.text.Format;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class UserDirectory extends LdapDirectory {

	public UserDirectory(String top, Company company, User user) {
		_top = top;
		_company = company;
		_user = user;
	}

	@Override
	public List<Attribute> getAttributes() {
		List<Attribute> attributes = new ArrayList<Attribute>();

		attributes.add(new Attribute("cn", _user.getScreenName()));
		attributes.add(new Attribute(
			"createTimestamp", _format.format(_user.getCreateDate())));
		attributes.add(new Attribute("displayName", _user.getFullName()));
		attributes.add(new Attribute("givenName", _user.getFirstName()));
		attributes.add(new Attribute("mail", _user.getEmailAddress()));
		attributes.add(new Attribute(
			"modifyTimestamp", _format.format(_user.getModifiedDate())));
		attributes.add(new Attribute("sn", _user.getLastName()));
		attributes.add(new Attribute("objectclass", "groupOfNames"));
		attributes.add(new Attribute("objectclass", "inetOrgPerson"));
		attributes.add(new Attribute("objectclass", "liferayPerson"));
		attributes.add(new Attribute("objectclass", "top"));
		attributes.add(new Attribute("uid", String.valueOf(_user.getUserId())));
		attributes.add(new Attribute("uuid", _user.getUuid()));

		try {
			addMemberAttributes(attributes);
		} catch (Exception e) {
			_log.error(e, e);
		}

		return attributes;
	}

	@Override
	public String getName() {
		return "cn=" + LdapDirectory.escape(_user.getScreenName()) +
			",ou=Users," + getNamePrefix();
	}

	protected void addMemberAttributes(List<Attribute> attributes)
		throws Exception {

		String prefix = getNamePrefix();

		long groupClassNameId = PortalUtil.getClassNameId(
			Group.class.getName());

		LinkedHashMap<String, Object> groupParams =
			new LinkedHashMap<String, Object>();

		groupParams.put("usersGroups", new Long(_user.getUserId()));

		List<Group> groups = GroupLocalServiceUtil.search(
			_user.getCompanyId(), new long[] {groupClassNameId}, null, null,
			groupParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Group group : groups) {
			attributes.add(new Attribute(
				"member",
				"cn=" + group.getName() + ",ou=" + group.getName() +
					",ou=Communities," + prefix));
		}

		for (Organization organization : _user.getOrganizations()) {
			attributes.add(new Attribute(
				"member",
				"cn=" + organization.getName() + ",ou=" +
					organization.getName() + ",ou=Organizations," +
					prefix));
		}

		for (Role role : _user.getRoles()) {
			attributes.add(new Attribute(
				"member",
				"cn=" + role.getName() + ",ou=" + role.getName() +
					",ou=Roles," + prefix));
		}

		for (UserGroup userGroup : _user.getUserGroups()) {
			attributes.add(new Attribute(
				"member",
				"cn=" + userGroup.getName() + ",ou=" + userGroup.getName() +
					",ou=User Groups," + prefix));
		}
	}

	private String getNamePrefix() {
		return "ou=" + LdapDirectory.escape(_company.getWebId()) + ",o=" +
			LdapDirectory.escape(_top);
	}

	protected Company _company;
	protected String _top;
	protected User _user;

	private Format _format =
		FastDateFormatFactoryUtil.getSimpleDateFormat("yyyyMMddHHmmss.SZ");

}
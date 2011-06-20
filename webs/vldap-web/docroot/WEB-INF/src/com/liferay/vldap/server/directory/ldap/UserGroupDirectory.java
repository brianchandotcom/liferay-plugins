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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.liferay.portal.model.Company;
import com.liferay.portal.model.UserGroup;

/**
 * @author Raymond Augé
 * @author Jonathan Potter
 */
public class UserGroupDirectory extends LdapDirectory {
	
	public UserGroupDirectory(
		String top, Company company, UserGroup userGroup) {

		_top = top;
		_company = company;
		_userGroup = userGroup;
	}

	@Override
	public List<Attribute> getAttributes() {
		List<Attribute> attributes = new ArrayList<Attribute>();

		attributes.add(new Attribute("cn", _userGroup.getName()));
		attributes.add(
			new Attribute("description", _userGroup.getDescription()));
		attributes.add(new Attribute("objectClass", "groupOfNames"));
		attributes.add(new Attribute("objectclass", "organizationalUnit"));
		attributes.add(new Attribute("objectclass", "top"));
		attributes.add(new Attribute("ou", _userGroup.getName()));
		
		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();
		params.put("usersUserGroups", _userGroup.getUserGroupId());
		
		attributes.addAll(getMemberAttributes(_top, _company, params));
		
		return attributes;
	}
	
	@Override
	public String getName() {
		return "ou=" + LdapDirectory.escape(_userGroup.getName()) +
			",ou=User Groups,ou=" + LdapDirectory.escape(_company.getWebId()) +
			",o=" + LdapDirectory.escape(_top);
	}

	protected Company _company;
	protected String _top;
	protected UserGroup _userGroup;
	
}

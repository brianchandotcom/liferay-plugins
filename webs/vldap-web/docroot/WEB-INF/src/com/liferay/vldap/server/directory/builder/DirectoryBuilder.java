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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.vldap.server.directory.FilterConstraint;
import com.liferay.vldap.server.directory.SearchBase;
import com.liferay.vldap.server.directory.ldap.LdapDirectory;
import com.liferay.vldap.server.directory.ldap.RootDirectory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Jonathan Potter
 */
public class DirectoryBuilder {

	public DirectoryBuilder () {
		_children = new ArrayList<DirectoryBuilder>();
		_built = false;
	}

	public List<DirectoryBuilder> getChildren () {
		return _children;
	}

	public void addChild (DirectoryBuilder child) {
		_children.add(child);
	}

	public List<LdapDirectory> buildDirectories(
		Set<FilterConstraint> constraints, boolean allLevels, SearchBase base) {

		if (_built) {
			return new ArrayList<LdapDirectory>();
		}

		List<LdapDirectory> directories;
		try {
			directories = buildThisLevel(constraints, base);
		} catch (Exception e) {
			_log.error(e);
			return null;
		}

		if (allLevels) {
			for (DirectoryBuilder child : _children) {
				directories.addAll(child.buildDirectories(
					constraints, allLevels, base));
			}
		}

		_built = true;

		return directories;
	}

	public List<LdapDirectory> buildThisLevel(
		Set<FilterConstraint> constraints, SearchBase base)
		throws Exception {

		List<LdapDirectory> directories = new ArrayList<LdapDirectory>();

		directories.add(new RootDirectory());

		return directories;
	}

	public boolean isValidAttribute (String attribute, String value) {
		return true;
	}

	public boolean isValidConstraint (FilterConstraint constraint) {
		for (Map.Entry<String, String> entry : constraint.getMap().entrySet()) {
			if (!isValidAttribute(entry.getKey(), entry.getValue())) {
				return false;
			}
		}

		return true;
	}

	protected static Log _log = LogFactoryUtil.getLog(DirectoryBuilder.class);

	protected boolean _built;
	protected List<DirectoryBuilder> _children;

}
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.comparator.UserScreenNameComparator;
import com.liferay.vldap.util.PortletPropsValues;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.directory.shared.ldap.model.entry.DefaultEntry;
import org.apache.directory.shared.ldap.model.entry.Entry;
import org.apache.directory.shared.ldap.model.name.Dn;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public abstract class LdapDirectory {

	public abstract List<Attribute> getAttributes();

	public Dn getDn()
		throws Exception {

		String name = getName();

		try {
			return new Dn(name);
		} catch (Exception e) {
			_log.error("Invalid name " + name);

			throw e;
		}
	}

	public abstract String getName();

	public Attribute getAttribute(String attributeId) {
		for (Attribute attribute : getAttributes()) {
			if (attributeId.equalsIgnoreCase(attribute.getAttributeId())) {
				return attribute;
			}
		}

		return null;
	}

	public Attribute getAttribute(String attributeId, String value) {
		for (Attribute attribute : getAttributes()) {
			if (attributeId.equalsIgnoreCase(attribute.getAttributeId()) &&
				value.equalsIgnoreCase(attribute.getValue())) {

				return attribute;
			}
		}

		return null;
	}

	public Entry toEntry(List<String> requestAttributes)
		throws Exception {

		Entry entry = new DefaultEntry();

		entry.setDn(getDn());

		boolean wildcardAttributes = requestAttributes.contains(
			StringPool.STAR);

		for (Attribute attribute : getAttributes()) {
			if (!wildcardAttributes &&
				!containsIgnoreCase(requestAttributes,
					attribute.getAttributeId())) {
				continue;
			}

			entry.add(attribute.getAttributeId(), attribute.getValue());
		}

		if (containsIgnoreCase(requestAttributes, "hassubordinates")) {
			entry.add("hassubordinates", "true");
		}

		return entry;
	}

	public static boolean containsIgnoreCase(List<String> list, String other) {
		if (list == null) {
			return false;
		}

		for (String current : list) {
			if (current.equalsIgnoreCase(other)) {
				return true;
			}
		}

		return false;
	}

	public static List<Attribute> getMemberAttributes(
		String top, Company company, LinkedHashMap<String, Object> params) {

		List<Attribute> attributes = new ArrayList<Attribute>();
		List<User> users = null;

		try {
			users =
				UserLocalServiceUtil.search(
					company.getCompanyId(), null, null, null, null, null,
					WorkflowConstants.STATUS_APPROVED, params, true, 0,
					PortletPropsValues.SEARCH_MAX_SIZE,
					new UserScreenNameComparator());
		} catch (Exception e) {
			_log.error(e);
		}

		if (users != null) {
			for (User user : users) {
				UserDirectory userDirectory =
					new UserDirectory(top, company, user);
				attributes.add(new Attribute(
					"member", userDirectory.getName()));
			}
		}

		return attributes;
	}

	protected static String escape(String name) {
		int pos = name.indexOf(CharPool.EQUAL);

		String suffix = name.substring(pos + 1);

		char[] chars = suffix.toCharArray();

		StringBundler sb = new StringBundler();

		for (char c : chars) {
			for (char escapeChar : _ESCAPE_CHARS) {
				if (c == escapeChar) {
					sb.append(CharPool.BACK_SLASH);

					break;
				}
			}

			sb.append(c);
		}

		String escapedSuffix = sb.toString();

		return name.substring(0, pos + 1) + escapedSuffix;
	}

	protected static Log _log = LogFactoryUtil.getLog(LdapDirectory.class);

	/**
	 * http://www.rlmueller.net/CharactersEscaped.htm
	 */
	private static final char[] _ESCAPE_CHARS = {
		 ',', '\\', '#', '+', '<', '>', ';', '"', '='
	};

}
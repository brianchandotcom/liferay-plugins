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
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Jonathan Potter
 */
public class TopDirectory extends LdapDirectory {
	
	public TopDirectory(String top) {
		_top = top;
	}

	@Override
	public List<Attribute> getAttributes() {
		List<Attribute> attributes = new ArrayList<Attribute>();
		
		attributes.add(new Attribute("objectclass", "organizationalUnit"));
		attributes.add(new Attribute("objectclass", "top"));
		attributes.add(new Attribute("o", "Liferay"));
		
		return attributes;
	}
	
	@Override
	public String getName() {
		return "o=" + LdapDirectory.escape(_top);
	}

	protected String _top;
	
}
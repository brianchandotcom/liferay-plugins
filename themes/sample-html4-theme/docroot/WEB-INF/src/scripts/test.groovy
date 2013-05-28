import com.liferay.scripting.executor.groovy.Role
import com.liferay.scripting.executor.groovy.ScriptingContext
import com.liferay.scripting.executor.groovy.Site
import com.liferay.scripting.executor.groovy.User

/*
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

ScriptingContext scriptingContext = new ScriptingContext();

def testRole = Role.portalRole("testRole", "Testing this role");
testRole.create(scriptingContext);

def site1 = Site.openSite("HR Site", "My firts site");
site1.create(scriptingContext);

def user1 = new User(
	"Michael", "Han", "VP Operations", "michael.han@liferay.com", "test");

user1.create(scriptingContext);

user1.addRoles(scriptingContext, testRole.name);

user1.joinSites(scriptingContext, site1.name);


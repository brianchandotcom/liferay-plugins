/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.testhook.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eric Min
 */
public class TestHookFilterUtil {

	public static void addClassName(String className) {
		if (!_classNames.contains(className)) {
			_classNames.add(className);
		}
	}

	public static List<String> getClassNames() {
		return _classNames;
	}

	private static List<String> _classNames = new ArrayList<String>();

}
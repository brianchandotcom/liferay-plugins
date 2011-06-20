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

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

/**
 * @author Jonathan Potter
 */
public class FilterConstraint {

	public FilterConstraint() {
		_map = new HashMap<String, String>();
	}

	public FilterConstraint(FilterConstraint other) {
		_map = new HashMap<String, String>(other.getMap());
	}

	public void addAttribute(String attribute, String value) {
		if (attribute == null) {
			return;
		}

		_map.put(attribute, value);
	}

	public boolean contains (String attribute) {
		return _map.containsKey(attribute);
	}

	@Override
	public boolean equals (Object other){
		if (this == other) {
			return true;
		}
		else if (!(other instanceof FilterConstraint)) {
			return false;
		}
		else {
			return _map.equals(((FilterConstraint) other).getMap());
		}
	}

	public Map<String, String> getMap () {
		return _map;
	}

	public String getValue (String attribute) {
		return _map.get(attribute);
	}

	public boolean isEmpty () {
		return _map.isEmpty();
	}

	public boolean merge (FilterConstraint right) {
		boolean collision = false;

		for (Map.Entry<String, String> entry : right.getMap().entrySet()) {
			String previousValue = _map.put(entry.getKey(), entry.getValue());

			if (previousValue != null) {
				collision = true;
			}
		}

		return collision;
	}

	public static Set<FilterConstraint> allCombinations(
		Set<FilterConstraint> left, Set<FilterConstraint> right) {

		if (left.isEmpty()) {
			return right;
		}

		Set<FilterConstraint> results = new HashSet<FilterConstraint>();

		for (FilterConstraint leftItem : left) {
			for (FilterConstraint rightItem : right) {
				FilterConstraint merged = merge(leftItem, rightItem);

				if (merged != null) {
					if (!merged.isEmpty()) {
						results.add(merged);
					}
				}
			}
		}

		return results;
	}

	public static FilterConstraint merge(
		FilterConstraint left, FilterConstraint right) {

		FilterConstraint result = new FilterConstraint(left);

		boolean collision = result.merge(right);

		if (collision) {
			return null;
		}
		else {
			return result;
		}
	}

	protected Map<String, String> _map;

}

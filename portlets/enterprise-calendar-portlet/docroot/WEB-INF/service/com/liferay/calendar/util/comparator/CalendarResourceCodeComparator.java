/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.calendar.util.comparator;

import com.liferay.calendar.model.CalendarResource;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Fabio Pezzutto
 */
public class CalendarResourceCodeComparator extends OrderByComparator {

	public static final String ORDER_BY_ASC =
		"CalendarResource.code, CalendarResource.name ASC";

	public static final String ORDER_BY_DESC =
		"CalendarResource.code, CalendarResource.name DESC";

	public static final String[] ORDER_BY_FIELDS = {"code", "name"};

	public CalendarResourceCodeComparator() {
		this(false);
	}

	public CalendarResourceCodeComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		CalendarResource resource1 = (CalendarResource)obj1;
		CalendarResource resource2 = (CalendarResource)obj2;

		int value = resource1.getCode().compareTo(resource2.getCode());

		if (value == 0) {
			value = resource1.getName().compareTo(resource2.getName());
		}

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private boolean _ascending;

}

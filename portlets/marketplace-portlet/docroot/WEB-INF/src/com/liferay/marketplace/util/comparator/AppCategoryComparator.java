/**
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

package com.liferay.marketplace.util.comparator;

import com.liferay.marketplace.model.App;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Ryan Park
 */
public class AppCategoryComparator extends OrderByComparator {

	public static final String ORDER_BY_ASC = "category ASC";

	public static final String ORDER_BY_DESC = "category DESC";

	public static final String[] ORDER_BY_FIELDS = {"category"};

	public AppCategoryComparator() {
		this(true);
	}

	public AppCategoryComparator(boolean asc) {
		_asc = asc;
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		App app1 = (App)obj1;
		App app2 = (App)obj2;

		int value = app1.getCategory().toLowerCase().compareTo(
			app2.getCategory().toLowerCase());

		if (_asc) {
			return value;
		}
		else {
			return -value;
		}
	}

	@Override
	public String getOrderBy() {
		if (_asc) {
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
		return _asc;
	}

	private boolean _asc;

}
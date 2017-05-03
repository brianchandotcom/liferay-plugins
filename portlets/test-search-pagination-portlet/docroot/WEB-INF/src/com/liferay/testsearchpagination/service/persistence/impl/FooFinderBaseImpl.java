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

package com.liferay.testsearchpagination.service.persistence.impl;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import com.liferay.testsearchpagination.model.Foo;
import com.liferay.testsearchpagination.service.persistence.FooPersistence;

import java.util.Set;

/**
 * @author Inácio Nery
 * @generated
 */
public class FooFinderBaseImpl extends BasePersistenceImpl<Foo> {
	@Override
	public Set<String> getBadColumnNames() {
		return getFooPersistence().getBadColumnNames();
	}

	/**
	 * Returns the foo persistence.
	 *
	 * @return the foo persistence
	 */
	public FooPersistence getFooPersistence() {
		return fooPersistence;
	}

	/**
	 * Sets the foo persistence.
	 *
	 * @param fooPersistence the foo persistence
	 */
	public void setFooPersistence(FooPersistence fooPersistence) {
		this.fooPersistence = fooPersistence;
	}

	@BeanReference(type = FooPersistence.class)
	protected FooPersistence fooPersistence;
}
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

package com.liferay.testsearchpagination.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.testsearchpagination.model.Foo;
import com.liferay.testsearchpagination.service.base.FooLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Inácio Nery
 */
@ProviderType
public class FooLocalServiceImpl extends FooLocalServiceBaseImpl {

	@Override
	public void addFoo(int fieldId, String fieldName)throws PortalException {
		long fooId = counterLocalService.increment();

		Foo foo = fooPersistence.create(fooId);

		foo.setFieldId(fieldId);
		foo.setFieldName(fieldName);

		fooPersistence.update(foo);
	}

	@Override
	public int countByFooIds(String[] fooIds) {
		return fooFinder.countByFooIds(fooIds);
	}

	@Override
	public List<Foo> findAll() {
		return fooPersistence.findAll();
	}

	@Override
	public List<Foo> findByFooIds(String[] fooIds, int start, int end) {
		return fooFinder.findByFooIds(fooIds, start, end);
	}

	@Override
	public void removeAll() {
		fooPersistence.removeAll();
	}

}
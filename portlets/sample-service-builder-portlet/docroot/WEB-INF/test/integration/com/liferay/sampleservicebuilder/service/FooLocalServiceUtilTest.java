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

package integration.com.liferay.sampleservicebuilder.service;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.test.plugins.LiferayPluginsIntegrationJUnitRunner;
import com.liferay.sampleservicebuilder.model.Foo;
import com.liferay.sampleservicebuilder.service.FooLocalServiceUtil;

import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(LiferayPluginsIntegrationJUnitRunner.class)
public class FooLocalServiceUtilTest {

	@After
	public void tearDown() throws Exception {
		_deleteAllFoos(0, 10);
	}

	@Test
	public void testAddFoo() throws Exception {
		int initialCount = _getFooCount();

		_createFoo();

		Assert.assertEquals(initialCount + 1, _getFooCount());
	}

	@Test
	public void testCreateFoo() throws Exception {
		long fooId = _random.nextLong();

		Foo foo = _createFoo(fooId);

		Assert.assertEquals(fooId, foo.getFooId());
	}

	@Test
	public void testDeleteFoo() throws Exception {
		int initialCount = _getFooCount();

		Foo foo = _createFoo();

		int nextCount = _getFooCount();

		Assert.assertEquals(initialCount + 1, nextCount);

		_deleteFoo(foo);

		nextCount = _getFooCount();

		Assert.assertEquals(initialCount, nextCount);
	}

	private Foo _createFoo() throws Exception {
		return _createFoo(_random.nextLong());
	}

	private Foo _createFoo(long fooId) throws Exception {
		Foo foo = FooLocalServiceUtil.createFoo(fooId);

		FooLocalServiceUtil.addFoo(foo);

		return foo;
	}

	private void _deleteAllFoos(int start, int end) throws SystemException {
		List<Foo> foos = FooLocalServiceUtil.getFoos(start, end);

		for (Foo foo : foos) {
			_deleteFoo(foo);
		}
	}

	private void _deleteFoo(Foo foo) throws SystemException {
		FooLocalServiceUtil.deleteFoo(foo);
	}

	private int _getFooCount() throws SystemException {
		return FooLocalServiceUtil.getFoosCount();
	}

	private Random _random = new Random();

}
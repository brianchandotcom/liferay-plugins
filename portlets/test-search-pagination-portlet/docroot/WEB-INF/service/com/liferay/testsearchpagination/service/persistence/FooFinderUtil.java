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

package com.liferay.testsearchpagination.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Inácio Nery
 * @generated
 */
@ProviderType
public class FooFinderUtil {
	public static int countByFooIds(java.lang.String[] fooIds) {
		return getFinder().countByFooIds(fooIds);
	}

	public static java.util.List<com.liferay.testsearchpagination.model.Foo> findByFooIds(
		java.lang.String[] fooIds, int start, int end) {
		return getFinder().findByFooIds(fooIds, start, end);
	}

	public static FooFinder getFinder() {
		if (_finder == null) {
			_finder = (FooFinder)PortletBeanLocatorUtil.locate(com.liferay.testsearchpagination.service.ClpSerializer.getServletContextName(),
					FooFinder.class.getName());

			ReferenceRegistry.registerReference(FooFinderUtil.class, "_finder");
		}

		return _finder;
	}

	public void setFinder(FooFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(FooFinderUtil.class, "_finder");
	}

	private static FooFinder _finder;
}
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

package com.liferay.testresourcesimporter.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;

/**
 * @author Shinn Lok
 */
public class TestResourcesImporterUtil {

	public static long getGroupId() {
		try {
			long companyId = CompanyThreadLocal.getCompanyId();

			Group group = GroupLocalServiceUtil.fetchGroup(
				companyId, "Test Resources Importer Portlet");

			if (group != null) {
				return group.getGroupId();
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return 0;
	}

	private static Log _log = LogFactoryUtil.getLog(
		TestResourcesImporterUtil.class);

}
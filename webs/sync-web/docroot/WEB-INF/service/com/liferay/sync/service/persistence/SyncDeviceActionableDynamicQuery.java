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

package com.liferay.sync.service.persistence;

import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;

import com.liferay.sync.model.SyncDevice;
import com.liferay.sync.service.SyncDeviceLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @deprecated As of 7.0.0, replaced by {@link SyncDeviceLocalServiceUtil#getActionableDynamicQuery()}
 * @generated
 */
@Deprecated
public abstract class SyncDeviceActionableDynamicQuery
	extends DefaultActionableDynamicQuery {
	public SyncDeviceActionableDynamicQuery() {
		setBaseLocalService(SyncDeviceLocalServiceUtil.getService());

		setClassLoader(com.liferay.sync.service.ClpSerializer.class.getClassLoader());

		setModelClass(SyncDevice.class);

		setPrimaryKeyPropertyName("syncDeviceId");
	}
}
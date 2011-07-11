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

package com.liferay.socialcoding.model.impl;

import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.socialcoding.model.JIRAChangeItem;
import com.liferay.socialcoding.service.JIRAChangeItemLocalServiceUtil;

/**
 * The extended model base implementation for the JIRAChangeItem service. Represents a row in the &quot;changeitem&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link JIRAChangeItemImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JIRAChangeItemImpl
 * @see com.liferay.socialcoding.model.JIRAChangeItem
 * @generated
 */
public abstract class JIRAChangeItemBaseImpl extends JIRAChangeItemModelImpl
	implements JIRAChangeItem {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a j i r a change item model instance should use the {@link JIRAChangeItem} interface instead.
	 */
	public void save() throws SystemException {
		JIRAChangeItemLocalServiceUtil.updateJIRAChangeItem(this);
	}
}
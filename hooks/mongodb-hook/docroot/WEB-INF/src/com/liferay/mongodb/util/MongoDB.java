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

package com.liferay.mongodb.util;

import com.mongodb.DB;
import com.mongodb.Mongo;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 * @author Michael C. Han
 */
public interface MongoDB {

	public boolean authenticate(long companyId);

	public DB getDB(long companyId);

	public Mongo getMongo();

}
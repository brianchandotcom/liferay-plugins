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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.expando.model.ExpandoTable;

import com.mongodb.DB;
import com.mongodb.DBCollection;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 * @author Michael C. Han
 */
public class MongoDBExpandoUtil {
	public static DBCollection getCollection(ExpandoTable expandoTable) {
		return getCollection(
			expandoTable.getCompanyId(), expandoTable.getClassName(),
			expandoTable.getName());
	}

	public static DBCollection getCollection(
		long companyId, long classNameId, String tableName) {

		String className = PortalUtil.getClassName(classNameId);

		return getCollection(companyId, className, tableName);
	}

	public static DBCollection getCollection(
		long companyId, String className, String tableName) {

		DB db = _mongoDB.getDB(companyId);

		String collectionName = getCollectionName(className, tableName);

		return db.getCollection(collectionName);
	}

	public static String getCollectionName(String className, String tableName) {
		return className.concat(StringPool.POUND).concat(tableName);
	}

	public void setMongoDB(MongoDB mongoDB) {
		_mongoDB = mongoDB;
	}

	private static MongoDB _mongoDB;
}
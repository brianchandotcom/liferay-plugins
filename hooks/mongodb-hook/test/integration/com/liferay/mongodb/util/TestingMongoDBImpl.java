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
 * @author Michael C. Han
 */
public class TestingMongoDBImpl implements MongoDB {
	public TestingMongoDBImpl()
	throws Exception {
		try {
			_mongo = new Mongo("localhost", 27017);
		}
		catch (Exception e) {
			throw new IllegalStateException("Unable to initialize MongoDB", e);
		}
	}

	public boolean authenticate(long companyId) {
		DB db = getDB(companyId);

		return db.authenticate("", "".toCharArray());
	}

	public DB getDB(long companyId) {
		String dbName = "lportal".concat("_").concat(String.valueOf(companyId));

		return _mongo.getDB(dbName);
	}

	public Mongo getMongo() {
		return _mongo;
	}

	private Mongo _mongo;

}
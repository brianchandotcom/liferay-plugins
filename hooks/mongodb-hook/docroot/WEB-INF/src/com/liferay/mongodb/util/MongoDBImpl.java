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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 * @author Michael C. Han
 */
public class MongoDBImpl implements MongoDB {

	public MongoDBImpl() {
		try {
			_mongo = new Mongo(getServerAddresses(), getMongoOptions());
		}
		catch (Exception e) {
			throw new IllegalStateException("Unable to initialize MongoDB", e);
		}
	}

	public boolean authenticate(long companyId) {
		if (Validator.isNull(PortletPropsValues.SERVER_USERNAME) ||
			Validator.isNull(PortletPropsValues.SERVER_PASSWORD)) {

			return true;
		}

		DB db = getDB(companyId);

		return db.authenticate(
			PortletPropsValues.SERVER_USERNAME,
			PortletPropsValues.SERVER_PASSWORD.toCharArray());
	}

	public DB getDB(long companyId) {
		String dbName = PortletPropsValues.SERVER_DATABASE.concat(
			StringPool.UNDERLINE).concat(String.valueOf(companyId));

		return _mongo.getDB(dbName);
	}

	public Mongo getMongo() {
		return _mongo;
	}

	protected MongoOptions getMongoOptions() {
		MongoOptions mongoOptions = new MongoOptions();

		mongoOptions.autoConnectRetry = GetterUtil.getBoolean(
			PortletPropsValues.DRIVER_AUTOCONNECT_RETRY,
			mongoOptions.autoConnectRetry);
		mongoOptions.connectTimeout = GetterUtil.getInteger(
			PortletPropsValues.DRIVER_CONNECT_TIMEOUT,
			mongoOptions.connectTimeout);
		mongoOptions.connectionsPerHost = GetterUtil.getInteger(
			PortletPropsValues.DRIVER_CONNECTIONS_PER_HOST,
			mongoOptions.connectionsPerHost);
		mongoOptions.maxWaitTime = GetterUtil.getInteger(
			PortletPropsValues.DRIVER_MAX_WAIT_TIME, mongoOptions.maxWaitTime);
		mongoOptions.socketTimeout = GetterUtil.getInteger(
			PortletPropsValues.DRIVER_SOCKET_TIMEOUT,
			mongoOptions.socketTimeout);
		mongoOptions.threadsAllowedToBlockForConnectionMultiplier =
			GetterUtil.getInteger(
				PortletPropsValues.DRIVER_THREADS_ALLOWED_TO_BLOCK,
				mongoOptions.threadsAllowedToBlockForConnectionMultiplier);

		return mongoOptions;
	}

	protected List<ServerAddress> getServerAddresses() throws Exception {
		List<ServerAddress> serverAddresses = new ArrayList<ServerAddress>();

		for (String hostname : PortletPropsValues.SERVER_HOSTNAMES) {
			ServerAddress serverAddress = new ServerAddress(
				hostname, PortletPropsValues.SERVER_PORT);

			serverAddresses.add(serverAddress);
		}

		return serverAddresses;
	}

	private static Log _log = LogFactoryUtil.getLog(MongoDBUtil.class);

	private Mongo _mongo;
}
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search.elasticsearch.connection;

import org.elasticsearch.client.Client;

/**
 * @author Michael C. Han
 */
public class ElasticSearchConnectionManager {

	public static ElasticSearchConnectionManager getInstance() {
		return _instance;
	}

	public ElasticSearchConnection getElasticSearchConnection() {
		return _elasticSearchConnection;
	}

	public Client getClient() {
		if (_elasticSearchConnection == null) {
			throw new IllegalStateException(
				"Elastic Search Connection not initialized");
		}

		return _elasticSearchConnection.getClient();
	}

	public void setElasticSearchConnection(
		ElasticSearchConnection elasticSearchConnection) {

		_elasticSearchConnection = elasticSearchConnection;
	}

	private static ElasticSearchConnectionManager _instance =
		new ElasticSearchConnectionManager();

	private static ElasticSearchConnection _elasticSearchConnection;

}
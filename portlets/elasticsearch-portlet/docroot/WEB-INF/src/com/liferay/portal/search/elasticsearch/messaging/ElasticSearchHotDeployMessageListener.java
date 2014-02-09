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

package com.liferay.portal.search.elasticsearch.messaging;

import com.liferay.portal.kernel.messaging.HotDeployMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.search.elasticsearch.connection.ElasticSearchConnection;
import com.liferay.portal.search.elasticsearch.connection.ElasticSearchConnectionManager;

/**
 * @author Michael C. Han
 */
public class ElasticSearchHotDeployMessageListener
	extends HotDeployMessageListener {

	@Override
	protected void onDeploy(Message message) throws Exception {
		ElasticSearchConnectionManager elasticSearchConnectionManager =
			ElasticSearchConnectionManager.getInstance();

		ElasticSearchConnection elasticSearchConnection =
			elasticSearchConnectionManager.getElasticSearchConnection();

		elasticSearchConnection.initialize();
	}

	@Override
	protected void onUndeploy(Message message) throws Exception {
		ElasticSearchConnectionManager elasticSearchConnectionManager =
			ElasticSearchConnectionManager.getInstance();

		ElasticSearchConnection elasticSearchConnection =
			elasticSearchConnectionManager.getElasticSearchConnection();

		elasticSearchConnection.close();
	}

}
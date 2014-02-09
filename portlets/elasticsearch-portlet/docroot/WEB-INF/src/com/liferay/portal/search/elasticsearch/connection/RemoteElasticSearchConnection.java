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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.net.InetAddress;

import java.util.HashSet;
import java.util.Set;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 * @author Michael C. Han
 */
public class RemoteElasticSearchConnection extends BaseElasticSearchConnection {

	@Override
	public void initialize() {

		if (_transportAddresses.isEmpty()) {
			throw new IllegalStateException(
				"Must configure at least one transport address");
		}

		ImmutableSettings.Builder settingsBuilder =
			ImmutableSettings.settingsBuilder();

		settingsBuilder.put("client.transport.sniff", true);
		settingsBuilder.put("cluster.name", getClusterName());

		Settings settings = settingsBuilder.build();

		TransportClient transportClient = new TransportClient(settings);

		for (String transportAddress : _transportAddresses) {
			String[] transportAddressComponents = StringUtil.split(
				transportAddress, StringPool.COLON);

			try {
				InetAddress inetAddress = InetAddress.getByName(
					transportAddressComponents[0]);

				int port = Integer.parseInt(transportAddressComponents[1]);

				transportClient.addTransportAddress(
					new InetSocketTransportAddress(inetAddress, port));
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to add address: " + transportAddress, e);
				}
			}
		}

		setClient(transportClient);
	}

	public void setTransportAddresses(Set<String> transportAddresses) {
		_transportAddresses = transportAddresses;
	}

	private static Log _log = LogFactoryUtil.getLog(
		RemoteElasticSearchConnection.class);

	private Set<String> _transportAddresses = new HashSet<String>();

}
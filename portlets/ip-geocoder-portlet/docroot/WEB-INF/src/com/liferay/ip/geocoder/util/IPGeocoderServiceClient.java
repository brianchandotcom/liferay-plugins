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
package com.liferay.ip.geocoder.util;

import com.liferay.ip.geocoder.IPGeocoder;
import com.liferay.ip.geocoder.IPInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julio Camarero
 */
@Component (immediate = true)
public class IPGeocoderServiceClient {

	public static IPInfo getIPInfo(String ipAddress) {
		if (_IPGeocoder == null) {
			return null;
		}

		return _IPGeocoder.getIPInfo(ipAddress);
	}

	@Reference (unbind = "unsetIPGeocoder")
	public void setIPGeocoder(IPGeocoder IPGeocoder) {
		_IPGeocoder = IPGeocoder;
	}

	public void unsetIPGeocoder(IPGeocoder IPGeocoder) {
		_IPGeocoder = null;
	}

	private static IPGeocoder _IPGeocoder;
}
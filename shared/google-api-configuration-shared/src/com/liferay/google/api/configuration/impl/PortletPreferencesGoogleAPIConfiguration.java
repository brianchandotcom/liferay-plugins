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

package com.liferay.google.api.configuration.impl;

import com.liferay.google.api.configuration.GoogleAPIConfiguration;
import com.liferay.portal.kernel.util.PrefsPropsUtil;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera
 */
@Component(service = GoogleAPIConfiguration.class)
public class PortletPreferencesGoogleAPIConfiguration
	implements GoogleAPIConfiguration {

	@Override
	public String getAPIKey(long companyId) {
		PortletPreferences companyPortletPreferences =
			PrefsPropsUtil.getPreferences(companyId);

		return companyPortletPreferences.getValue("googleAPIKey", null);
	}

	@Override
	public String getClientID(long companyId) {
		PortletPreferences companyPortletPreferences =
			PrefsPropsUtil.getPreferences(companyId);

		return companyPortletPreferences.getValue("googleClientId", null);
	}

}
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

package com.liferay.testpaypal.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.util.portlet.PortletProps;

/**
 * @author Douglas Wong
 */
public class PortletPropsValues {

	public static final String PAYPAL_CLIENT_ID = PortletProps.get(
		PortletPropsKeys.PAYPAL_CLIENT_ID);

	public static final String PAYPAL_CLIENT_SECRET = PortletProps.get(
		PortletPropsKeys.PAYPAL_CLIENT_SECRET);

}
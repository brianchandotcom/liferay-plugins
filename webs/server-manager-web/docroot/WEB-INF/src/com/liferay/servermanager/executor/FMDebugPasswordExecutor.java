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

package com.liferay.servermanager.executor;

import java.util.Queue;

import javax.servlet.http.HttpServletRequest;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.servermanager.util.JSONKeys;

/**
 * @author Cindy Li
 */
public class FMDebugPasswordExecutor extends BaseExecutor {

	@Override
	public void executeRead(
		HttpServletRequest request, JSONObject responseJSONObject,
		Queue<String> arguments) {

		String fmDebugPassword = System.getProperty("freemarker.debug.password");

		if (fmDebugPassword == null) {
			responseJSONObject.put(
				JSONKeys.ERROR, "Freemarker debugger is not enabled");
			responseJSONObject.put(JSONKeys.STATUS, 1);
		}
		else {
			responseJSONObject.put(JSONKeys.OUTPUT, fmDebugPassword);
		}
	}

}

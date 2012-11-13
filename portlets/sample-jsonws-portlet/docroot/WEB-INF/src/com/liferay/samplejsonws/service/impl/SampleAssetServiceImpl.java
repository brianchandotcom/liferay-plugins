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

package com.liferay.samplejsonws.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.samplejsonws.model.SampleAsset;
import com.liferay.samplejsonws.service.base.SampleAssetServiceBaseImpl;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * The implementation of the sample asset remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.samplejsonws.service.SampleAssetService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.samplejsonws.service.base.SampleAssetServiceBaseImpl
 * @see com.liferay.samplejsonws.service.SampleAssetServiceUtil
 */
public class SampleAssetServiceImpl extends SampleAssetServiceBaseImpl {

	/**
	 * Service method that takes few parameters of various types.
	 */
	public String hello(long userId, String fullName) {
		return "Hello " + fullName + " " + userId;
	}

	/**
	 * Service method with many different parameter types.
	 */
	public String hello2(
			Calendar calendar, long[] longArray, List<Locale> locales) {

		return "Hello2 " + calendar.get(Calendar.YEAR) + " " + longArray[0] +
				" /" + longArray.length + " " + locales.get(0) +
				locales.size();
	}

	public String methodOne(long id, long nameId) {
		return "one-2";
	}

	public String methodOne(long id, long nameId, String subname) {
		return "one-3";
	}

	public String methodOne(long id, String name) {
		return "one-1";
	}

	/**
	 * Simple method.
	 */
	public String ping() {
		return "pong";
	}

	/**
	 * Process that uses custom-type argument.
	 */
	public String process(SampleAsset sampleAsset) {
		return sampleAsset.getName();
	}

	/**
	 * A method that uses default parameters. Default parameters doesn't
	 * have to be specified explicitly within request.
	 */
	public String workoutUser(
			long userId, long companyId, ServiceContext serviceContext)
			throws PortalException, SystemException {

		User user = UserServiceUtil.getUserById(userId);

		return "work user: " + userId + "/" + companyId + " " +
			(user == null ? user : user.getLogin()) + " " +
			(serviceContext != null);
	}

}
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

package com.liferay.feedback.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.util.ContentUtil;

import java.util.List;

/**
 * @author Lin Cui
 */
public class FeedbackUtil {

	public static String[] getFeedbackQuestions() throws SystemException {
		String feedbackTemplate = ContentUtil.get(
			FeedbackConstants.FEEDBACK_TEMPLATE);

		if ((feedbackTemplate == null) ||
			(feedbackTemplate.trim().length() == 0)) {

			return new String[0];
		}
		else {
			String[] questions = StringUtil.split(
				feedbackTemplate, StringPool.NEW_LINE);

			return questions;
		}
	}

	public static String[] readPortletConfig() throws Exception {
		String[] emptyIds = new String[0];

		List<PortletPreferences> portletPreferencesList =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				PortletKeys.PREFS_OWNER_TYPE_COMPANY, 0, PortletKeys.FEEDBACK);

		if (portletPreferencesList.isEmpty()) {
			return emptyIds;
		}

		for (PortletPreferences lfPortletPreferences : portletPreferencesList) {
			PortletPreferencesIds portletPreferencesIds =
				new PortletPreferencesIds(
					lfPortletPreferences.getOwnerId(),
					lfPortletPreferences.getOwnerId(),
					lfPortletPreferences.getOwnerType(),
					lfPortletPreferences.getPlid(), PortletKeys.FEEDBACK);

			javax.portlet.PortletPreferences portletPreferences =
				PortletPreferencesLocalServiceUtil.getPreferences(
					portletPreferencesIds);

			if (portletPreferences == null) {
				continue;
			}

			String groupId = portletPreferences.getValue(
				"groupId", StringPool.BLANK);

			String categoryId = portletPreferences.getValue(
				"categoryId", StringPool.BLANK);

			if (Validator.isNull(groupId) && Validator.isNull(categoryId)) {
				return emptyIds;
			}

			return new String[] { groupId, categoryId};
		}

		return emptyIds;
	}

}
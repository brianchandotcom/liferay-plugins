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
import com.liferay.util.ContentUtil;

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

}
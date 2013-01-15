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

package com.liferay.mboriginalmessage.hook.sanitizer;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StreamUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Douglas Wong
 */
public class MBOriginalMessageSanitizerImpl implements Sanitizer {

	public byte[] sanitize(
		long companyId, long groupId, long userId, String className,
		long classPK, String contentType, String[] modes, byte[] bytes,
		Map<String, Object> options) {

		if (_log.isDebugEnabled()) {
			_log.debug("Sanitizing " + className + "#" + classPK);
		}

		return bytes;
	}

	public void sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes,
			InputStream inputStream, OutputStream outputStream,
			Map<String, Object> options)
		throws SanitizerException {

		if (_log.isDebugEnabled()) {
			_log.debug("Sanitizing " + className + "#" + classPK);
		}

		try {
			StreamUtil.transfer(inputStream, outputStream);
		}
		catch (IOException ioe) {
			throw new SanitizerException(ioe);
		}
	}

	public String sanitize(
		long companyId, long groupId, long userId, String className,
		long classPK, String contentType, String[] modes, String s,
		Map<String, Object> options) {

		if (_log.isDebugEnabled()) {
			_log.debug("Sanitizing " + className + "#" + classPK);
		}

		if (contentType.startsWith(ContentTypes.TEXT_PLAIN)) {
			Matcher matcher = _QUOTE_TEXT_BEGINNING.matcher(s);

			if (matcher.find()) {
				String sanitizedText = s.substring(0, matcher.start());

				return sanitizedText.trim();
			}
		}

		return s;
	}

	private static final String _BASE_SPACER_REGEX = "[\\s,/\\.\\-]";

	private static final String _DT_DAY_OF_MONTH_REGEX =
		"[0-3]?[0-9]" + _BASE_SPACER_REGEX +
			"*(?:(?:th)|(?:st)|(?:nd)|(?:rd))?";

	private static final String _DT_DAY_OF_WEEK_REGEX =
		"(?:(?:Mon(?:day)?)|(?:Tue(?:sday)?)|(?:Wed(?:nesday)?)|" +
			"(?:Thu(?:rsday)?)|(?:Fri(?:day)?)|(?:Sat(?:urday)?)|" +
				"(?:Sun(?:day)?))";

	private static final String _DT_MONTH_REGEX =
		"(?:(?:Jan(?:uary)?)|(?:Feb(?:uary)?)|(?:Mar(?:ch)?)|(?:Apr(?:il)?)|" +
			"(?:May)|(?:Jun(?:e)?)|(?:Jul(?:y)?)|(?:Aug(?:ust)?)|" +
				"(?:Sep(?:tember)?)|(?:Oct(?:ober)?)|(?:Nov(?:ember)?)|" +
					"(?:Dec(?:ember)?)|(?:[0-1]?[0-9]))";

	private static final String _DT_TIME_REGEX =
		"(?:[0-2])?[0-9]:[0-5][0-9](?::[0-5][0-9])?(?:(?:\\s)?[AP]M)?";

	private static final String _DT_YEAR_REGEX = "(?:[1-2]?[0-9])[0-9][0-9]";

	private static final String _FORMATTED_DATE_REGEX =
		"(?:" + _DT_DAY_OF_WEEK_REGEX + _BASE_SPACER_REGEX + "+)?(?:(?:" +
			_DT_DAY_OF_MONTH_REGEX + _BASE_SPACER_REGEX + "+" +
				_DT_MONTH_REGEX + ")|(?:" + _DT_MONTH_REGEX +
					_BASE_SPACER_REGEX + "+" + _DT_DAY_OF_MONTH_REGEX + "))" +
						_BASE_SPACER_REGEX + "+" + _DT_YEAR_REGEX;

	private static final String _FORMATTED_DATE_TIME_REGEX =
		"(?:" + _FORMATTED_DATE_REGEX + "[\\s,]*(?:(?:at)|(?:@))?\\s*" +
			_DT_TIME_REGEX + ")|(?:" + _DT_TIME_REGEX + "[\\s,]*(?:on)?\\s*" +
				_FORMATTED_DATE_REGEX + ")";

	private static final String _HEADER_DATE_TIME_REGEX =
		"(?:(?:date)|(?:sent)|(?:time)):\\s*("+ _FORMATTED_DATE_TIME_REGEX +
			").*\r\n";

	private static final String _HEADER_SUBJECT_ADDRESS_REGEX =
		"((?:from)|(?:subject)|(?:b?cc)|(?:to)):.*\r\n";

	private static final String _QUOTE_GMAIL_REGEX =
		"(On\\s+" + _FORMATTED_DATE_TIME_REGEX + ".*wrote:\n)";

	private static final String _QUOTE_LINE_REGEX =
		"[-,_]+\\s*(?:Original(?:\\sMessage)?)?\\s*[-,_]+\r\n\\s*";

	private static final Pattern _QUOTE_TEXT_BEGINNING =
		Pattern.compile("(?i)(?:(?:" + _QUOTE_LINE_REGEX + ")?(?:(?:" +
			_HEADER_SUBJECT_ADDRESS_REGEX + ")|(?:" + _HEADER_DATE_TIME_REGEX +
				")){2,6})|(?:" + _QUOTE_GMAIL_REGEX + ")");

	private static Log _log = LogFactoryUtil.getLog(
		MBOriginalMessageSanitizerImpl.class);

}
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

package com.liferay.portlet.polls.service.base;

import com.liferay.portlet.polls.service.PollsQuestionServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 */
public class PollsQuestionServiceClpInvoker {
	public PollsQuestionServiceClpInvoker() {
		_methodName34 = "getBeanIdentifier";

		_methodParameterTypes34 = new String[] {  };

		_methodName35 = "setBeanIdentifier";

		_methodParameterTypes35 = new String[] { "java.lang.String" };

		_methodName40 = "addQuestion";

		_methodParameterTypes40 = new String[] {
				"java.util.Map", "java.util.Map", "int", "int", "int", "int",
				"int", "boolean", "java.util.List",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName41 = "deleteQuestion";

		_methodParameterTypes41 = new String[] { "long" };

		_methodName42 = "getQuestion";

		_methodParameterTypes42 = new String[] { "long" };

		_methodName43 = "updateQuestion";

		_methodParameterTypes43 = new String[] {
				"long", "java.util.Map", "java.util.Map", "int", "int", "int",
				"int", "int", "boolean", "java.util.List",
				"com.liferay.portal.service.ServiceContext"
			};
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName34.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes34, parameterTypes)) {
			return PollsQuestionServiceUtil.getBeanIdentifier();
		}

		if (_methodName35.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes35, parameterTypes)) {
			PollsQuestionServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);
		}

		if (_methodName40.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes40, parameterTypes)) {
			return PollsQuestionServiceUtil.addQuestion((java.util.Map<java.util.Locale, java.lang.String>)arguments[0],
				(java.util.Map<java.util.Locale, java.lang.String>)arguments[1],
				((Integer)arguments[2]).intValue(),
				((Integer)arguments[3]).intValue(),
				((Integer)arguments[4]).intValue(),
				((Integer)arguments[5]).intValue(),
				((Integer)arguments[6]).intValue(),
				((Boolean)arguments[7]).booleanValue(),
				(java.util.List<com.liferay.portlet.polls.model.PollsChoice>)arguments[8],
				(com.liferay.portal.service.ServiceContext)arguments[9]);
		}

		if (_methodName41.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes41, parameterTypes)) {
			PollsQuestionServiceUtil.deleteQuestion(((Long)arguments[0]).longValue());
		}

		if (_methodName42.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes42, parameterTypes)) {
			return PollsQuestionServiceUtil.getQuestion(((Long)arguments[0]).longValue());
		}

		if (_methodName43.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes43, parameterTypes)) {
			return PollsQuestionServiceUtil.updateQuestion(((Long)arguments[0]).longValue(),
				(java.util.Map<java.util.Locale, java.lang.String>)arguments[1],
				(java.util.Map<java.util.Locale, java.lang.String>)arguments[2],
				((Integer)arguments[3]).intValue(),
				((Integer)arguments[4]).intValue(),
				((Integer)arguments[5]).intValue(),
				((Integer)arguments[6]).intValue(),
				((Integer)arguments[7]).intValue(),
				((Boolean)arguments[8]).booleanValue(),
				(java.util.List<com.liferay.portlet.polls.model.PollsChoice>)arguments[9],
				(com.liferay.portal.service.ServiceContext)arguments[10]);
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName34;
	private String[] _methodParameterTypes34;
	private String _methodName35;
	private String[] _methodParameterTypes35;
	private String _methodName40;
	private String[] _methodParameterTypes40;
	private String _methodName41;
	private String[] _methodParameterTypes41;
	private String _methodName42;
	private String[] _methodParameterTypes42;
	private String _methodName43;
	private String[] _methodParameterTypes43;
}
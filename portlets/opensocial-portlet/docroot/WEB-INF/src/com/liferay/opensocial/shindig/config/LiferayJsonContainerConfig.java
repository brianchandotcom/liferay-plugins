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

package com.liferay.opensocial.shindig.config;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import com.liferay.opensocial.shindig.util.ShindigUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.shindig.config.ContainerConfigException;
import org.apache.shindig.config.DynamicConfigProperty;
import org.apache.shindig.config.JsonContainerConfig;
import org.apache.shindig.expressions.Expressions;

/**
 * @author Michael Young
 */
@Singleton
public class LiferayJsonContainerConfig extends JsonContainerConfig {

	@Inject
	public LiferayJsonContainerConfig(
			@Named("shindig.containers.default") String containers,
			Expressions expressions)
		throws ContainerConfigException {

		super(containers, null, null, expressions);
	}

	@Override
	public String getString(String container, String property) {
		String value = super.getString(container, property);

		if (Validator.isNotNull(value)) {
			value = replaceTokens(value);
		}

		return value;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object getProperty(String container, String property) {
		Object value = super.getProperty(container, property);

		if (Validator.isNotNull(value)) {
			if (value instanceof String) {
				value = replaceTokens((String)value);
			}
			else if (value instanceof Map) {
				value = replaceMap((Map<String, ?>)value);
			}
			else if (value instanceof DynamicConfigProperty) {
				DynamicConfigProperty dynamicConfigProperty =
					(DynamicConfigProperty)value;

				value = replaceTokens(dynamicConfigProperty.toString());
			}
		}

		return value;
	}

	public String replaceTokens(String value) {
		String dynamicHost = ShindigUtil.getHost();

		if (Validator.isNull(dynamicHost)) {
			dynamicHost = "%host%";
		}

		return StringUtil.replace(
			value, new String[] {
				"%host%",
				"%context%"},
			new String[] {
				dynamicHost,
				ShindigUtil.getContextPath()}
		);
	}

	@SuppressWarnings("unchecked")
	private <T> Object replaceMap(Map<String, T> map) {
		Map<String, T> newMap = new LinkedHashMap<String, T>();

		for (Map.Entry<String, T> entry : map.entrySet()) {
			String key = entry.getKey();
			T value = entry.getValue();

			if (value instanceof String) {
				value = (T)replaceTokens((String)value);
			}
			else if (value instanceof Map) {
				value = (T)replaceMap((Map<String, ?>)value);
			}
			else if (value instanceof List) {
				value = (T)replaceList((List<?>)value);
			}
			else if (value instanceof DynamicConfigProperty) {
				DynamicConfigProperty dynamicConfigProperty =
					(DynamicConfigProperty)value;

				value = (T)replaceTokens(dynamicConfigProperty.toString());
			}

			newMap.put(key, value);
		}

		return Collections.unmodifiableMap(newMap);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<?> replaceList(List<?> list) {
		List newList = new ArrayList();

		for (Object object : list) {
			if (object instanceof String) {
				String value = replaceTokens((String)object);

				newList.add(value);
			}
			else {
				newList.add(object);
			}
		}

		return Collections.unmodifiableList(newList);
	}

}
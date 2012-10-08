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

package com.liferay.testsearchcontainer.model;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;

import com.liferay.testsearchcontainer.service.FooLocalServiceUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Janghyun Kim
 */
public class FooClp extends BaseModelImpl<Foo> implements Foo {
	public FooClp() {
	}

	public Class<?> getModelClass() {
		return Foo.class;
	}

	public String getModelClassName() {
		return Foo.class.getName();
	}

	public long getPrimaryKey() {
		return _fooId;
	}

	public void setPrimaryKey(long primaryKey) {
		setFooId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_fooId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("fooId", getFooId());
		attributes.put("value", getValue());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long fooId = (Long)attributes.get("fooId");

		if (fooId != null) {
			setFooId(fooId);
		}

		Integer value = (Integer)attributes.get("value");

		if (value != null) {
			setValue(value);
		}
	}

	public long getFooId() {
		return _fooId;
	}

	public void setFooId(long fooId) {
		_fooId = fooId;
	}

	public int getValue() {
		return _value;
	}

	public void setValue(int value) {
		_value = value;
	}

	public BaseModel<?> getFooRemoteModel() {
		return _fooRemoteModel;
	}

	public void setFooRemoteModel(BaseModel<?> fooRemoteModel) {
		_fooRemoteModel = fooRemoteModel;
	}

	public void persist() throws SystemException {
		if (this.isNew()) {
			FooLocalServiceUtil.addFoo(this);
		}
		else {
			FooLocalServiceUtil.updateFoo(this);
		}
	}

	@Override
	public Foo toEscapedModel() {
		return (Foo)Proxy.newProxyInstance(Foo.class.getClassLoader(),
			new Class[] { Foo.class }, new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		FooClp clone = new FooClp();

		clone.setFooId(getFooId());
		clone.setValue(getValue());

		return clone;
	}

	public int compareTo(Foo foo) {
		int value = 0;

		if (getFooId() < foo.getFooId()) {
			value = -1;
		}
		else if (getFooId() > foo.getFooId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		FooClp foo = null;

		try {
			foo = (FooClp)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = foo.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{fooId=");
		sb.append(getFooId());
		sb.append(", value=");
		sb.append(getValue());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(10);

		sb.append("<model><model-name>");
		sb.append("com.liferay.testsearchcontainer.model.Foo");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>fooId</column-name><column-value><![CDATA[");
		sb.append(getFooId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>value</column-name><column-value><![CDATA[");
		sb.append(getValue());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _fooId;
	private int _value;
	private BaseModel<?> _fooRemoteModel;
}
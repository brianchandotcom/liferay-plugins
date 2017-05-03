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

package com.liferay.testsearchpagination.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;

import com.liferay.testsearchpagination.service.ClpSerializer;
import com.liferay.testsearchpagination.service.FooLocalServiceUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

/**
 * @generated
 */
@ProviderType
public class FooClp extends BaseModelImpl<Foo> implements Foo {
	public FooClp() {
	}

	@Override
	public Class<?> getModelClass() {
		return Foo.class;
	}

	@Override
	public String getModelClassName() {
		return Foo.class.getName();
	}

	@Override
	public long getPrimaryKey() {
		return _fooId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setFooId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _fooId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("fooId", getFooId());
		attributes.put("fieldId", getFieldId());
		attributes.put("fieldName", getFieldName());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long fooId = (Long)attributes.get("fooId");

		if (fooId != null) {
			setFooId(fooId);
		}

		Integer fieldId = (Integer)attributes.get("fieldId");

		if (fieldId != null) {
			setFieldId(fieldId);
		}

		String fieldName = (String)attributes.get("fieldName");

		if (fieldName != null) {
			setFieldName(fieldName);
		}

		_entityCacheEnabled = GetterUtil.getBoolean("entityCacheEnabled");
		_finderCacheEnabled = GetterUtil.getBoolean("finderCacheEnabled");
	}

	@Override
	public String getUuid() {
		return _uuid;
	}

	@Override
	public void setUuid(String uuid) {
		_uuid = uuid;

		if (_fooRemoteModel != null) {
			try {
				Class<?> clazz = _fooRemoteModel.getClass();

				Method method = clazz.getMethod("setUuid", String.class);

				method.invoke(_fooRemoteModel, uuid);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public long getFooId() {
		return _fooId;
	}

	@Override
	public void setFooId(long fooId) {
		_fooId = fooId;

		if (_fooRemoteModel != null) {
			try {
				Class<?> clazz = _fooRemoteModel.getClass();

				Method method = clazz.getMethod("setFooId", long.class);

				method.invoke(_fooRemoteModel, fooId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public int getFieldId() {
		return _fieldId;
	}

	@Override
	public void setFieldId(int fieldId) {
		_fieldId = fieldId;

		if (_fooRemoteModel != null) {
			try {
				Class<?> clazz = _fooRemoteModel.getClass();

				Method method = clazz.getMethod("setFieldId", int.class);

				method.invoke(_fooRemoteModel, fieldId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getFieldName() {
		return _fieldName;
	}

	@Override
	public void setFieldName(String fieldName) {
		_fieldName = fieldName;

		if (_fooRemoteModel != null) {
			try {
				Class<?> clazz = _fooRemoteModel.getClass();

				Method method = clazz.getMethod("setFieldName", String.class);

				method.invoke(_fooRemoteModel, fieldName);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public BaseModel<?> getFooRemoteModel() {
		return _fooRemoteModel;
	}

	public void setFooRemoteModel(BaseModel<?> fooRemoteModel) {
		_fooRemoteModel = fooRemoteModel;
	}

	public Object invokeOnRemoteModel(String methodName,
		Class<?>[] parameterTypes, Object[] parameterValues)
		throws Exception {
		Object[] remoteParameterValues = new Object[parameterValues.length];

		for (int i = 0; i < parameterValues.length; i++) {
			if (parameterValues[i] != null) {
				remoteParameterValues[i] = ClpSerializer.translateInput(parameterValues[i]);
			}
		}

		Class<?> remoteModelClass = _fooRemoteModel.getClass();

		ClassLoader remoteModelClassLoader = remoteModelClass.getClassLoader();

		Class<?>[] remoteParameterTypes = new Class[parameterTypes.length];

		for (int i = 0; i < parameterTypes.length; i++) {
			if (parameterTypes[i].isPrimitive()) {
				remoteParameterTypes[i] = parameterTypes[i];
			}
			else {
				String parameterTypeName = parameterTypes[i].getName();

				remoteParameterTypes[i] = remoteModelClassLoader.loadClass(parameterTypeName);
			}
		}

		Method method = remoteModelClass.getMethod(methodName,
				remoteParameterTypes);

		Object returnValue = method.invoke(_fooRemoteModel,
				remoteParameterValues);

		if (returnValue != null) {
			returnValue = ClpSerializer.translateOutput(returnValue);
		}

		return returnValue;
	}

	@Override
	public void persist() {
		if (this.isNew()) {
			FooLocalServiceUtil.addFoo(this);
		}
		else {
			FooLocalServiceUtil.updateFoo(this);
		}
	}

	@Override
	public Foo toEscapedModel() {
		return (Foo)ProxyUtil.newProxyInstance(Foo.class.getClassLoader(),
			new Class[] { Foo.class }, new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		FooClp clone = new FooClp();

		clone.setUuid(getUuid());
		clone.setFooId(getFooId());
		clone.setFieldId(getFieldId());
		clone.setFieldName(getFieldName());

		return clone;
	}

	@Override
	public int compareTo(Foo foo) {
		long primaryKey = foo.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FooClp)) {
			return false;
		}

		FooClp foo = (FooClp)obj;

		long primaryKey = foo.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	public Class<?> getClpSerializerClass() {
		return _clpSerializerClass;
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _entityCacheEnabled;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _finderCacheEnabled;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", fooId=");
		sb.append(getFooId());
		sb.append(", fieldId=");
		sb.append(getFieldId());
		sb.append(", fieldName=");
		sb.append(getFieldName());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(16);

		sb.append("<model><model-name>");
		sb.append("com.liferay.testsearchpagination.model.Foo");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>fooId</column-name><column-value><![CDATA[");
		sb.append(getFooId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>fieldId</column-name><column-value><![CDATA[");
		sb.append(getFieldId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>fieldName</column-name><column-value><![CDATA[");
		sb.append(getFieldName());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private String _uuid;
	private long _fooId;
	private int _fieldId;
	private String _fieldName;
	private BaseModel<?> _fooRemoteModel;
	private Class<?> _clpSerializerClass = ClpSerializer.class;
	private boolean _entityCacheEnabled;
	private boolean _finderCacheEnabled;
}
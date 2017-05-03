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

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link Foo}.
 * </p>
 *
 * @author Inácio Nery
 * @see Foo
 * @generated
 */
@ProviderType
public class FooWrapper implements Foo, ModelWrapper<Foo> {
	public FooWrapper(Foo foo) {
		_foo = foo;
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
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("fooId", getFooId());
		attributes.put("fieldId", getFieldId());
		attributes.put("fieldName", getFieldName());

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
	}

	@Override
	public Foo toEscapedModel() {
		return new FooWrapper(_foo.toEscapedModel());
	}

	@Override
	public Foo toUnescapedModel() {
		return new FooWrapper(_foo.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _foo.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _foo.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _foo.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _foo.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<Foo> toCacheModel() {
		return _foo.toCacheModel();
	}

	@Override
	public int compareTo(Foo foo) {
		return _foo.compareTo(foo);
	}

	/**
	* Returns the field ID of this foo.
	*
	* @return the field ID of this foo
	*/
	@Override
	public int getFieldId() {
		return _foo.getFieldId();
	}

	@Override
	public int hashCode() {
		return _foo.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _foo.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new FooWrapper((Foo)_foo.clone());
	}

	/**
	* Returns the field name of this foo.
	*
	* @return the field name of this foo
	*/
	@Override
	public java.lang.String getFieldName() {
		return _foo.getFieldName();
	}

	/**
	* Returns the uuid of this foo.
	*
	* @return the uuid of this foo
	*/
	@Override
	public java.lang.String getUuid() {
		return _foo.getUuid();
	}

	@Override
	public java.lang.String toString() {
		return _foo.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _foo.toXmlString();
	}

	/**
	* Returns the foo ID of this foo.
	*
	* @return the foo ID of this foo
	*/
	@Override
	public long getFooId() {
		return _foo.getFooId();
	}

	/**
	* Returns the primary key of this foo.
	*
	* @return the primary key of this foo
	*/
	@Override
	public long getPrimaryKey() {
		return _foo.getPrimaryKey();
	}

	@Override
	public void persist() {
		_foo.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_foo.setCachedModel(cachedModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_foo.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_foo.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_foo.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the field ID of this foo.
	*
	* @param fieldId the field ID of this foo
	*/
	@Override
	public void setFieldId(int fieldId) {
		_foo.setFieldId(fieldId);
	}

	/**
	* Sets the field name of this foo.
	*
	* @param fieldName the field name of this foo
	*/
	@Override
	public void setFieldName(java.lang.String fieldName) {
		_foo.setFieldName(fieldName);
	}

	/**
	* Sets the foo ID of this foo.
	*
	* @param fooId the foo ID of this foo
	*/
	@Override
	public void setFooId(long fooId) {
		_foo.setFooId(fooId);
	}

	@Override
	public void setNew(boolean n) {
		_foo.setNew(n);
	}

	/**
	* Sets the primary key of this foo.
	*
	* @param primaryKey the primary key of this foo
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_foo.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_foo.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the uuid of this foo.
	*
	* @param uuid the uuid of this foo
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_foo.setUuid(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FooWrapper)) {
			return false;
		}

		FooWrapper fooWrapper = (FooWrapper)obj;

		if (Objects.equals(_foo, fooWrapper._foo)) {
			return true;
		}

		return false;
	}

	@Override
	public Foo getWrappedModel() {
		return _foo;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _foo.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _foo.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_foo.resetOriginalValues();
	}

	private final Foo _foo;
}
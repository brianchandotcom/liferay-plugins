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

package com.liferay.calendar.model.impl;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarBookingModel;
import com.liferay.calendar.model.CalendarBookingSoap;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * The base model implementation for the CalendarBooking service. Represents a row in the &quot;CalendarBooking&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.calendar.model.CalendarBookingModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CalendarBookingImpl}.
 * </p>
 *
 * @author Eduardo Lundgren
 * @see CalendarBookingImpl
 * @see com.liferay.calendar.model.CalendarBooking
 * @see com.liferay.calendar.model.CalendarBookingModel
 * @generated
 */
@JSON(strict = true)
public class CalendarBookingModelImpl extends BaseModelImpl<CalendarBooking>
	implements CalendarBookingModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a calendar booking model instance should use the {@link com.liferay.calendar.model.CalendarBooking} interface instead.
	 */
	public static final String TABLE_NAME = "CalendarBooking";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "calendarBookingId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "calendarEventId", Types.BIGINT },
			{ "calendarResourceId", Types.BIGINT },
			{ "classNameId", Types.BIGINT },
			{ "classPK", Types.BIGINT },
			{ "title", Types.VARCHAR },
			{ "name", Types.VARCHAR },
			{ "description", Types.VARCHAR },
			{ "location", Types.VARCHAR },
			{ "startDate", Types.TIMESTAMP },
			{ "endDate", Types.TIMESTAMP },
			{ "durationHour", Types.INTEGER },
			{ "durationMinute", Types.INTEGER },
			{ "recurrence", Types.VARCHAR },
			{ "type_", Types.VARCHAR },
			{ "required", Types.BOOLEAN },
			{ "status", Types.INTEGER },
			{ "statusByUserId", Types.BIGINT },
			{ "statusByUserName", Types.VARCHAR },
			{ "statusDate", Types.TIMESTAMP }
		};
	public static final String TABLE_SQL_CREATE = "create table CalendarBooking (uuid_ VARCHAR(75) null,calendarBookingId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,calendarEventId LONG,calendarResourceId LONG,classNameId LONG,classPK LONG,title STRING null,name STRING null,description STRING null,location STRING null,startDate DATE null,endDate DATE null,durationHour INTEGER,durationMinute INTEGER,recurrence VARCHAR(75) null,type_ VARCHAR(75) null,required BOOLEAN,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table CalendarBooking";
	public static final String ORDER_BY_JPQL = " ORDER BY calendarBooking.title ASC";
	public static final String ORDER_BY_SQL = " ORDER BY CalendarBooking.title ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.calendar.model.CalendarBooking"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.calendar.model.CalendarBooking"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.calendar.model.CalendarBooking"),
			true);
	public static long CALENDAREVENTID_COLUMN_BITMASK = 1L;
	public static long CALENDARRESOURCEID_COLUMN_BITMASK = 2L;
	public static long CLASSNAMEID_COLUMN_BITMASK = 4L;
	public static long CLASSPK_COLUMN_BITMASK = 8L;
	public static long COMPANYID_COLUMN_BITMASK = 16L;
	public static long GROUPID_COLUMN_BITMASK = 32L;
	public static long UUID_COLUMN_BITMASK = 64L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static CalendarBooking toModel(CalendarBookingSoap soapModel) {
		CalendarBooking model = new CalendarBookingImpl();

		model.setUuid(soapModel.getUuid());
		model.setCalendarBookingId(soapModel.getCalendarBookingId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setCalendarEventId(soapModel.getCalendarEventId());
		model.setCalendarResourceId(soapModel.getCalendarResourceId());
		model.setClassNameId(soapModel.getClassNameId());
		model.setClassPK(soapModel.getClassPK());
		model.setTitle(soapModel.getTitle());
		model.setName(soapModel.getName());
		model.setDescription(soapModel.getDescription());
		model.setLocation(soapModel.getLocation());
		model.setStartDate(soapModel.getStartDate());
		model.setEndDate(soapModel.getEndDate());
		model.setDurationHour(soapModel.getDurationHour());
		model.setDurationMinute(soapModel.getDurationMinute());
		model.setRecurrence(soapModel.getRecurrence());
		model.setType(soapModel.getType());
		model.setRequired(soapModel.getRequired());
		model.setStatus(soapModel.getStatus());
		model.setStatusByUserId(soapModel.getStatusByUserId());
		model.setStatusByUserName(soapModel.getStatusByUserName());
		model.setStatusDate(soapModel.getStatusDate());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<CalendarBooking> toModels(
		CalendarBookingSoap[] soapModels) {
		List<CalendarBooking> models = new ArrayList<CalendarBooking>(soapModels.length);

		for (CalendarBookingSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.util.service.ServiceProps.get(
				"lock.expiration.time.com.liferay.calendar.model.CalendarBooking"));

	public CalendarBookingModelImpl() {
	}

	public long getPrimaryKey() {
		return _calendarBookingId;
	}

	public void setPrimaryKey(long primaryKey) {
		setCalendarBookingId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_calendarBookingId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public Class<?> getModelClass() {
		return CalendarBooking.class;
	}

	public String getModelClassName() {
		return CalendarBooking.class.getName();
	}

	@JSON
	public String getUuid() {
		if (_uuid == null) {
			return StringPool.BLANK;
		}
		else {
			return _uuid;
		}
	}

	public void setUuid(String uuid) {
		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	@JSON
	public long getCalendarBookingId() {
		return _calendarBookingId;
	}

	public void setCalendarBookingId(long calendarBookingId) {
		_calendarBookingId = calendarBookingId;
	}

	@JSON
	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_columnBitmask |= GROUPID_COLUMN_BITMASK;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = _groupId;
		}

		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	@JSON
	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_columnBitmask |= COMPANYID_COLUMN_BITMASK;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = _companyId;
		}

		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	@JSON
	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	@JSON
	public String getUserName() {
		if (_userName == null) {
			return StringPool.BLANK;
		}
		else {
			return _userName;
		}
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	@JSON
	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@JSON
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	@JSON
	public long getCalendarEventId() {
		return _calendarEventId;
	}

	public void setCalendarEventId(long calendarEventId) {
		_columnBitmask |= CALENDAREVENTID_COLUMN_BITMASK;

		if (!_setOriginalCalendarEventId) {
			_setOriginalCalendarEventId = true;

			_originalCalendarEventId = _calendarEventId;
		}

		_calendarEventId = calendarEventId;
	}

	public long getOriginalCalendarEventId() {
		return _originalCalendarEventId;
	}

	@JSON
	public long getCalendarResourceId() {
		return _calendarResourceId;
	}

	public void setCalendarResourceId(long calendarResourceId) {
		_columnBitmask |= CALENDARRESOURCEID_COLUMN_BITMASK;

		if (!_setOriginalCalendarResourceId) {
			_setOriginalCalendarResourceId = true;

			_originalCalendarResourceId = _calendarResourceId;
		}

		_calendarResourceId = calendarResourceId;
	}

	public long getOriginalCalendarResourceId() {
		return _originalCalendarResourceId;
	}

	public String getClassName() {
		if (getClassNameId() <= 0) {
			return StringPool.BLANK;
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	public void setClassName(String className) {
		long classNameId = 0;

		if (Validator.isNotNull(className)) {
			classNameId = PortalUtil.getClassNameId(className);
		}

		setClassNameId(classNameId);
	}

	@JSON
	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_columnBitmask |= CLASSNAMEID_COLUMN_BITMASK;

		if (!_setOriginalClassNameId) {
			_setOriginalClassNameId = true;

			_originalClassNameId = _classNameId;
		}

		_classNameId = classNameId;
	}

	public long getOriginalClassNameId() {
		return _originalClassNameId;
	}

	@JSON
	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_columnBitmask |= CLASSPK_COLUMN_BITMASK;

		if (!_setOriginalClassPK) {
			_setOriginalClassPK = true;

			_originalClassPK = _classPK;
		}

		_classPK = classPK;
	}

	public long getOriginalClassPK() {
		return _originalClassPK;
	}

	@JSON
	public String getTitle() {
		if (_title == null) {
			return StringPool.BLANK;
		}
		else {
			return _title;
		}
	}

	public String getTitle(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getTitle(languageId);
	}

	public String getTitle(Locale locale, boolean useDefault) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getTitle(languageId, useDefault);
	}

	public String getTitle(String languageId) {
		return LocalizationUtil.getLocalization(getTitle(), languageId);
	}

	public String getTitle(String languageId, boolean useDefault) {
		return LocalizationUtil.getLocalization(getTitle(), languageId,
			useDefault);
	}

	public String getTitleCurrentLanguageId() {
		return _titleCurrentLanguageId;
	}

	@JSON
	public String getTitleCurrentValue() {
		Locale locale = getLocale(_titleCurrentLanguageId);

		return getTitle(locale);
	}

	public Map<Locale, String> getTitleMap() {
		return LocalizationUtil.getLocalizationMap(getTitle());
	}

	public void setTitle(String title) {
		_columnBitmask = -1L;

		_title = title;
	}

	public void setTitle(String title, Locale locale) {
		setTitle(title, locale, LocaleUtil.getDefault());
	}

	public void setTitle(String title, Locale locale, Locale defaultLocale) {
		String languageId = LocaleUtil.toLanguageId(locale);
		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		if (Validator.isNotNull(title)) {
			setTitle(LocalizationUtil.updateLocalization(getTitle(), "Title",
					title, languageId, defaultLanguageId));
		}
		else {
			setTitle(LocalizationUtil.removeLocalization(getTitle(), "Title",
					languageId));
		}
	}

	public void setTitleCurrentLanguageId(String languageId) {
		_titleCurrentLanguageId = languageId;
	}

	public void setTitleMap(Map<Locale, String> titleMap) {
		setTitleMap(titleMap, LocaleUtil.getDefault());
	}

	public void setTitleMap(Map<Locale, String> titleMap, Locale defaultLocale) {
		if (titleMap == null) {
			return;
		}

		setTitle(LocalizationUtil.updateLocalization(titleMap, getTitle(),
				"Title", LocaleUtil.toLanguageId(defaultLocale)));
	}

	@JSON
	public String getName() {
		if (_name == null) {
			return StringPool.BLANK;
		}
		else {
			return _name;
		}
	}

	public String getName(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getName(languageId);
	}

	public String getName(Locale locale, boolean useDefault) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getName(languageId, useDefault);
	}

	public String getName(String languageId) {
		return LocalizationUtil.getLocalization(getName(), languageId);
	}

	public String getName(String languageId, boolean useDefault) {
		return LocalizationUtil.getLocalization(getName(), languageId,
			useDefault);
	}

	public String getNameCurrentLanguageId() {
		return _nameCurrentLanguageId;
	}

	@JSON
	public String getNameCurrentValue() {
		Locale locale = getLocale(_nameCurrentLanguageId);

		return getName(locale);
	}

	public Map<Locale, String> getNameMap() {
		return LocalizationUtil.getLocalizationMap(getName());
	}

	public void setName(String name) {
		_name = name;
	}

	public void setName(String name, Locale locale) {
		setName(name, locale, LocaleUtil.getDefault());
	}

	public void setName(String name, Locale locale, Locale defaultLocale) {
		String languageId = LocaleUtil.toLanguageId(locale);
		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		if (Validator.isNotNull(name)) {
			setName(LocalizationUtil.updateLocalization(getName(), "Name",
					name, languageId, defaultLanguageId));
		}
		else {
			setName(LocalizationUtil.removeLocalization(getName(), "Name",
					languageId));
		}
	}

	public void setNameCurrentLanguageId(String languageId) {
		_nameCurrentLanguageId = languageId;
	}

	public void setNameMap(Map<Locale, String> nameMap) {
		setNameMap(nameMap, LocaleUtil.getDefault());
	}

	public void setNameMap(Map<Locale, String> nameMap, Locale defaultLocale) {
		if (nameMap == null) {
			return;
		}

		setName(LocalizationUtil.updateLocalization(nameMap, getName(), "Name",
				LocaleUtil.toLanguageId(defaultLocale)));
	}

	@JSON
	public String getDescription() {
		if (_description == null) {
			return StringPool.BLANK;
		}
		else {
			return _description;
		}
	}

	public String getDescription(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getDescription(languageId);
	}

	public String getDescription(Locale locale, boolean useDefault) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getDescription(languageId, useDefault);
	}

	public String getDescription(String languageId) {
		return LocalizationUtil.getLocalization(getDescription(), languageId);
	}

	public String getDescription(String languageId, boolean useDefault) {
		return LocalizationUtil.getLocalization(getDescription(), languageId,
			useDefault);
	}

	public String getDescriptionCurrentLanguageId() {
		return _descriptionCurrentLanguageId;
	}

	@JSON
	public String getDescriptionCurrentValue() {
		Locale locale = getLocale(_descriptionCurrentLanguageId);

		return getDescription(locale);
	}

	public Map<Locale, String> getDescriptionMap() {
		return LocalizationUtil.getLocalizationMap(getDescription());
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setDescription(String description, Locale locale) {
		setDescription(description, locale, LocaleUtil.getDefault());
	}

	public void setDescription(String description, Locale locale,
		Locale defaultLocale) {
		String languageId = LocaleUtil.toLanguageId(locale);
		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		if (Validator.isNotNull(description)) {
			setDescription(LocalizationUtil.updateLocalization(
					getDescription(), "Description", description, languageId,
					defaultLanguageId));
		}
		else {
			setDescription(LocalizationUtil.removeLocalization(
					getDescription(), "Description", languageId));
		}
	}

	public void setDescriptionCurrentLanguageId(String languageId) {
		_descriptionCurrentLanguageId = languageId;
	}

	public void setDescriptionMap(Map<Locale, String> descriptionMap) {
		setDescriptionMap(descriptionMap, LocaleUtil.getDefault());
	}

	public void setDescriptionMap(Map<Locale, String> descriptionMap,
		Locale defaultLocale) {
		if (descriptionMap == null) {
			return;
		}

		setDescription(LocalizationUtil.updateLocalization(descriptionMap,
				getDescription(), "Description",
				LocaleUtil.toLanguageId(defaultLocale)));
	}

	@JSON
	public String getLocation() {
		if (_location == null) {
			return StringPool.BLANK;
		}
		else {
			return _location;
		}
	}

	public void setLocation(String location) {
		_location = location;
	}

	@JSON
	public Date getStartDate() {
		return _startDate;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	@JSON
	public Date getEndDate() {
		return _endDate;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	@JSON
	public int getDurationHour() {
		return _durationHour;
	}

	public void setDurationHour(int durationHour) {
		_durationHour = durationHour;
	}

	@JSON
	public int getDurationMinute() {
		return _durationMinute;
	}

	public void setDurationMinute(int durationMinute) {
		_durationMinute = durationMinute;
	}

	@JSON
	public String getRecurrence() {
		if (_recurrence == null) {
			return StringPool.BLANK;
		}
		else {
			return _recurrence;
		}
	}

	public void setRecurrence(String recurrence) {
		_recurrence = recurrence;
	}

	@JSON
	public String getType() {
		if (_type == null) {
			return StringPool.BLANK;
		}
		else {
			return _type;
		}
	}

	public void setType(String type) {
		_type = type;
	}

	@JSON
	public boolean getRequired() {
		return _required;
	}

	public boolean isRequired() {
		return _required;
	}

	public void setRequired(boolean required) {
		_required = required;
	}

	@JSON
	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	@JSON
	public long getStatusByUserId() {
		return _statusByUserId;
	}

	public void setStatusByUserId(long statusByUserId) {
		_statusByUserId = statusByUserId;
	}

	public String getStatusByUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getStatusByUserId(), "uuid",
			_statusByUserUuid);
	}

	public void setStatusByUserUuid(String statusByUserUuid) {
		_statusByUserUuid = statusByUserUuid;
	}

	@JSON
	public String getStatusByUserName() {
		if (_statusByUserName == null) {
			return StringPool.BLANK;
		}
		else {
			return _statusByUserName;
		}
	}

	public void setStatusByUserName(String statusByUserName) {
		_statusByUserName = statusByUserName;
	}

	@JSON
	public Date getStatusDate() {
		return _statusDate;
	}

	public void setStatusDate(Date statusDate) {
		_statusDate = statusDate;
	}

	/**
	 * @deprecated {@link #isApproved}
	 */
	public boolean getApproved() {
		return isApproved();
	}

	public boolean isApproved() {
		if (getStatus() == WorkflowConstants.STATUS_APPROVED) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isDraft() {
		if ((getStatus() == WorkflowConstants.STATUS_DRAFT) ||
				(getStatus() == WorkflowConstants.STATUS_DRAFT_FROM_APPROVED)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isExpired() {
		if (getStatus() == WorkflowConstants.STATUS_EXPIRED) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isPending() {
		if (getStatus() == WorkflowConstants.STATUS_PENDING) {
			return true;
		}
		else {
			return false;
		}
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public CalendarBooking toEscapedModel() {
		if (_escapedModelProxy == null) {
			_escapedModelProxy = (CalendarBooking)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelProxyInterfaces,
					new AutoEscapeBeanHandler(this));
		}

		return _escapedModelProxy;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					CalendarBooking.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		CalendarBookingImpl calendarBookingImpl = new CalendarBookingImpl();

		calendarBookingImpl.setUuid(getUuid());
		calendarBookingImpl.setCalendarBookingId(getCalendarBookingId());
		calendarBookingImpl.setGroupId(getGroupId());
		calendarBookingImpl.setCompanyId(getCompanyId());
		calendarBookingImpl.setUserId(getUserId());
		calendarBookingImpl.setUserName(getUserName());
		calendarBookingImpl.setCreateDate(getCreateDate());
		calendarBookingImpl.setModifiedDate(getModifiedDate());
		calendarBookingImpl.setCalendarEventId(getCalendarEventId());
		calendarBookingImpl.setCalendarResourceId(getCalendarResourceId());
		calendarBookingImpl.setClassNameId(getClassNameId());
		calendarBookingImpl.setClassPK(getClassPK());
		calendarBookingImpl.setTitle(getTitle());
		calendarBookingImpl.setName(getName());
		calendarBookingImpl.setDescription(getDescription());
		calendarBookingImpl.setLocation(getLocation());
		calendarBookingImpl.setStartDate(getStartDate());
		calendarBookingImpl.setEndDate(getEndDate());
		calendarBookingImpl.setDurationHour(getDurationHour());
		calendarBookingImpl.setDurationMinute(getDurationMinute());
		calendarBookingImpl.setRecurrence(getRecurrence());
		calendarBookingImpl.setType(getType());
		calendarBookingImpl.setRequired(getRequired());
		calendarBookingImpl.setStatus(getStatus());
		calendarBookingImpl.setStatusByUserId(getStatusByUserId());
		calendarBookingImpl.setStatusByUserName(getStatusByUserName());
		calendarBookingImpl.setStatusDate(getStatusDate());

		calendarBookingImpl.resetOriginalValues();

		return calendarBookingImpl;
	}

	public int compareTo(CalendarBooking calendarBooking) {
		int value = 0;

		value = getTitle().toLowerCase()
					.compareTo(calendarBooking.getTitle().toLowerCase());

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

		CalendarBooking calendarBooking = null;

		try {
			calendarBooking = (CalendarBooking)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = calendarBooking.getPrimaryKey();

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
	public void resetOriginalValues() {
		CalendarBookingModelImpl calendarBookingModelImpl = this;

		calendarBookingModelImpl._originalUuid = calendarBookingModelImpl._uuid;

		calendarBookingModelImpl._originalGroupId = calendarBookingModelImpl._groupId;

		calendarBookingModelImpl._setOriginalGroupId = false;

		calendarBookingModelImpl._originalCompanyId = calendarBookingModelImpl._companyId;

		calendarBookingModelImpl._setOriginalCompanyId = false;

		calendarBookingModelImpl._originalCalendarEventId = calendarBookingModelImpl._calendarEventId;

		calendarBookingModelImpl._setOriginalCalendarEventId = false;

		calendarBookingModelImpl._originalCalendarResourceId = calendarBookingModelImpl._calendarResourceId;

		calendarBookingModelImpl._setOriginalCalendarResourceId = false;

		calendarBookingModelImpl._originalClassNameId = calendarBookingModelImpl._classNameId;

		calendarBookingModelImpl._setOriginalClassNameId = false;

		calendarBookingModelImpl._originalClassPK = calendarBookingModelImpl._classPK;

		calendarBookingModelImpl._setOriginalClassPK = false;

		calendarBookingModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<CalendarBooking> toCacheModel() {
		CalendarBookingCacheModel calendarBookingCacheModel = new CalendarBookingCacheModel();

		calendarBookingCacheModel.uuid = getUuid();

		String uuid = calendarBookingCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			calendarBookingCacheModel.uuid = null;
		}

		calendarBookingCacheModel.calendarBookingId = getCalendarBookingId();

		calendarBookingCacheModel.groupId = getGroupId();

		calendarBookingCacheModel.companyId = getCompanyId();

		calendarBookingCacheModel.userId = getUserId();

		calendarBookingCacheModel.userName = getUserName();

		String userName = calendarBookingCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			calendarBookingCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			calendarBookingCacheModel.createDate = createDate.getTime();
		}
		else {
			calendarBookingCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			calendarBookingCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			calendarBookingCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		calendarBookingCacheModel.calendarEventId = getCalendarEventId();

		calendarBookingCacheModel.calendarResourceId = getCalendarResourceId();

		calendarBookingCacheModel.classNameId = getClassNameId();

		calendarBookingCacheModel.classPK = getClassPK();

		calendarBookingCacheModel.title = getTitle();

		String title = calendarBookingCacheModel.title;

		if ((title != null) && (title.length() == 0)) {
			calendarBookingCacheModel.title = null;
		}

		calendarBookingCacheModel.name = getName();

		String name = calendarBookingCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			calendarBookingCacheModel.name = null;
		}

		calendarBookingCacheModel.description = getDescription();

		String description = calendarBookingCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			calendarBookingCacheModel.description = null;
		}

		calendarBookingCacheModel.location = getLocation();

		String location = calendarBookingCacheModel.location;

		if ((location != null) && (location.length() == 0)) {
			calendarBookingCacheModel.location = null;
		}

		Date startDate = getStartDate();

		if (startDate != null) {
			calendarBookingCacheModel.startDate = startDate.getTime();
		}
		else {
			calendarBookingCacheModel.startDate = Long.MIN_VALUE;
		}

		Date endDate = getEndDate();

		if (endDate != null) {
			calendarBookingCacheModel.endDate = endDate.getTime();
		}
		else {
			calendarBookingCacheModel.endDate = Long.MIN_VALUE;
		}

		calendarBookingCacheModel.durationHour = getDurationHour();

		calendarBookingCacheModel.durationMinute = getDurationMinute();

		calendarBookingCacheModel.recurrence = getRecurrence();

		String recurrence = calendarBookingCacheModel.recurrence;

		if ((recurrence != null) && (recurrence.length() == 0)) {
			calendarBookingCacheModel.recurrence = null;
		}

		calendarBookingCacheModel.type = getType();

		String type = calendarBookingCacheModel.type;

		if ((type != null) && (type.length() == 0)) {
			calendarBookingCacheModel.type = null;
		}

		calendarBookingCacheModel.required = getRequired();

		calendarBookingCacheModel.status = getStatus();

		calendarBookingCacheModel.statusByUserId = getStatusByUserId();

		calendarBookingCacheModel.statusByUserName = getStatusByUserName();

		String statusByUserName = calendarBookingCacheModel.statusByUserName;

		if ((statusByUserName != null) && (statusByUserName.length() == 0)) {
			calendarBookingCacheModel.statusByUserName = null;
		}

		Date statusDate = getStatusDate();

		if (statusDate != null) {
			calendarBookingCacheModel.statusDate = statusDate.getTime();
		}
		else {
			calendarBookingCacheModel.statusDate = Long.MIN_VALUE;
		}

		return calendarBookingCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(55);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", calendarBookingId=");
		sb.append(getCalendarBookingId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", calendarEventId=");
		sb.append(getCalendarEventId());
		sb.append(", calendarResourceId=");
		sb.append(getCalendarResourceId());
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append(", classPK=");
		sb.append(getClassPK());
		sb.append(", title=");
		sb.append(getTitle());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", location=");
		sb.append(getLocation());
		sb.append(", startDate=");
		sb.append(getStartDate());
		sb.append(", endDate=");
		sb.append(getEndDate());
		sb.append(", durationHour=");
		sb.append(getDurationHour());
		sb.append(", durationMinute=");
		sb.append(getDurationMinute());
		sb.append(", recurrence=");
		sb.append(getRecurrence());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", required=");
		sb.append(getRequired());
		sb.append(", status=");
		sb.append(getStatus());
		sb.append(", statusByUserId=");
		sb.append(getStatusByUserId());
		sb.append(", statusByUserName=");
		sb.append(getStatusByUserName());
		sb.append(", statusDate=");
		sb.append(getStatusDate());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(85);

		sb.append("<model><model-name>");
		sb.append("com.liferay.calendar.model.CalendarBooking");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>calendarBookingId</column-name><column-value><![CDATA[");
		sb.append(getCalendarBookingId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userName</column-name><column-value><![CDATA[");
		sb.append(getUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>calendarEventId</column-name><column-value><![CDATA[");
		sb.append(getCalendarEventId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>calendarResourceId</column-name><column-value><![CDATA[");
		sb.append(getCalendarResourceId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classNameId</column-name><column-value><![CDATA[");
		sb.append(getClassNameId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classPK</column-name><column-value><![CDATA[");
		sb.append(getClassPK());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>title</column-name><column-value><![CDATA[");
		sb.append(getTitle());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>location</column-name><column-value><![CDATA[");
		sb.append(getLocation());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>startDate</column-name><column-value><![CDATA[");
		sb.append(getStartDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>endDate</column-name><column-value><![CDATA[");
		sb.append(getEndDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>durationHour</column-name><column-value><![CDATA[");
		sb.append(getDurationHour());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>durationMinute</column-name><column-value><![CDATA[");
		sb.append(getDurationMinute());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>recurrence</column-name><column-value><![CDATA[");
		sb.append(getRecurrence());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>required</column-name><column-value><![CDATA[");
		sb.append(getRequired());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>status</column-name><column-value><![CDATA[");
		sb.append(getStatus());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>statusByUserId</column-name><column-value><![CDATA[");
		sb.append(getStatusByUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>statusByUserName</column-name><column-value><![CDATA[");
		sb.append(getStatusByUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>statusDate</column-name><column-value><![CDATA[");
		sb.append(getStatusDate());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = CalendarBooking.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			CalendarBooking.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _calendarBookingId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _calendarEventId;
	private long _originalCalendarEventId;
	private boolean _setOriginalCalendarEventId;
	private long _calendarResourceId;
	private long _originalCalendarResourceId;
	private boolean _setOriginalCalendarResourceId;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private String _title;
	private String _titleCurrentLanguageId;
	private String _name;
	private String _nameCurrentLanguageId;
	private String _description;
	private String _descriptionCurrentLanguageId;
	private String _location;
	private Date _startDate;
	private Date _endDate;
	private int _durationHour;
	private int _durationMinute;
	private String _recurrence;
	private String _type;
	private boolean _required;
	private int _status;
	private long _statusByUserId;
	private String _statusByUserUuid;
	private String _statusByUserName;
	private Date _statusDate;
	private transient ExpandoBridge _expandoBridge;
	private long _columnBitmask;
	private CalendarBooking _escapedModelProxy;
}
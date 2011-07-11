/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.hr.model.impl;

import com.liferay.hr.model.HRUserTask;
import com.liferay.hr.model.HRUserTaskModel;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.Date;

/**
 * The base model implementation for the HRUserTask service. Represents a row in the &quot;HRUserTask&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.hr.model.HRUserTaskModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link HRUserTaskImpl}.
 * </p>
 *
 * @author Wesley Gong
 * @see HRUserTaskImpl
 * @see com.liferay.hr.model.HRUserTask
 * @see com.liferay.hr.model.HRUserTaskModel
 * @generated
 */
public class HRUserTaskModelImpl extends BaseModelImpl<HRUserTask>
	implements HRUserTaskModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a h r user task model instance should use the {@link com.liferay.hr.model.HRUserTask} interface instead.
	 */
	public static final String TABLE_NAME = "HRUserTask";
	public static final Object[][] TABLE_COLUMNS = {
			{ "hrUserTaskId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "hrBillabilityId", Types.BIGINT },
			{ "hrTaskId", Types.BIGINT },
			{ "hrTimesheetId", Types.BIGINT },
			{ "hrUserId", Types.BIGINT }
		};
	public static final String TABLE_SQL_CREATE = "create table HRUserTask (hrUserTaskId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,hrBillabilityId LONG,hrTaskId LONG,hrTimesheetId LONG,hrUserId LONG)";
	public static final String TABLE_SQL_DROP = "drop table HRUserTask";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.hr.model.HRUserTask"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.hr.model.HRUserTask"),
			true);

	public Class<?> getModelClass() {
		return HRUserTask.class;
	}

	public String getModelClassName() {
		return HRUserTask.class.getName();
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.util.service.ServiceProps.get(
				"lock.expiration.time.com.liferay.hr.model.HRUserTask"));

	public HRUserTaskModelImpl() {
	}

	public long getPrimaryKey() {
		return _hrUserTaskId;
	}

	public void setPrimaryKey(long primaryKey) {
		setHrUserTaskId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_hrUserTaskId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public long getHrUserTaskId() {
		return _hrUserTaskId;
	}

	public void setHrUserTaskId(long hrUserTaskId) {
		_hrUserTaskId = hrUserTaskId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

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

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getHrBillabilityId() {
		return _hrBillabilityId;
	}

	public void setHrBillabilityId(long hrBillabilityId) {
		_hrBillabilityId = hrBillabilityId;
	}

	public long getHrTaskId() {
		return _hrTaskId;
	}

	public void setHrTaskId(long hrTaskId) {
		_hrTaskId = hrTaskId;
	}

	public long getHrTimesheetId() {
		return _hrTimesheetId;
	}

	public void setHrTimesheetId(long hrTimesheetId) {
		_hrTimesheetId = hrTimesheetId;
	}

	public long getHrUserId() {
		return _hrUserId;
	}

	public void setHrUserId(long hrUserId) {
		_hrUserId = hrUserId;
	}

	public String getHrUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getHrUserId(), "uuid", _hrUserUuid);
	}

	public void setHrUserUuid(String hrUserUuid) {
		_hrUserUuid = hrUserUuid;
	}

	@Override
	public HRUserTask toEscapedModel() {
		if (isEscapedModel()) {
			return (HRUserTask)this;
		}
		else {
			if (_escapedModelProxy == null) {
				_escapedModelProxy = (HRUserTask)Proxy.newProxyInstance(_classLoader,
						_escapedModelProxyInterfaces,
						new AutoEscapeBeanHandler(this));
			}

			return _escapedModelProxy;
		}
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					HRUserTask.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		HRUserTaskImpl hrUserTaskImpl = new HRUserTaskImpl();

		hrUserTaskImpl.setHrUserTaskId(getHrUserTaskId());
		hrUserTaskImpl.setGroupId(getGroupId());
		hrUserTaskImpl.setCompanyId(getCompanyId());
		hrUserTaskImpl.setUserId(getUserId());
		hrUserTaskImpl.setUserName(getUserName());
		hrUserTaskImpl.setCreateDate(getCreateDate());
		hrUserTaskImpl.setModifiedDate(getModifiedDate());
		hrUserTaskImpl.setHrBillabilityId(getHrBillabilityId());
		hrUserTaskImpl.setHrTaskId(getHrTaskId());
		hrUserTaskImpl.setHrTimesheetId(getHrTimesheetId());
		hrUserTaskImpl.setHrUserId(getHrUserId());

		hrUserTaskImpl.resetOriginalValues();

		return hrUserTaskImpl;
	}

	public int compareTo(HRUserTask hrUserTask) {
		long primaryKey = hrUserTask.getPrimaryKey();

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
		if (obj == null) {
			return false;
		}

		HRUserTask hrUserTask = null;

		try {
			hrUserTask = (HRUserTask)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = hrUserTask.getPrimaryKey();

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
	}

	@Override
	public CacheModel<HRUserTask> toCacheModel() {
		HRUserTaskCacheModel hrUserTaskCacheModel = new HRUserTaskCacheModel();

		hrUserTaskCacheModel.hrUserTaskId = getHrUserTaskId();

		hrUserTaskCacheModel.groupId = getGroupId();

		hrUserTaskCacheModel.companyId = getCompanyId();

		hrUserTaskCacheModel.userId = getUserId();

		hrUserTaskCacheModel.userName = getUserName();

		String userName = hrUserTaskCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			hrUserTaskCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			hrUserTaskCacheModel.createDate = createDate.getTime();
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			hrUserTaskCacheModel.modifiedDate = modifiedDate.getTime();
		}

		hrUserTaskCacheModel.hrBillabilityId = getHrBillabilityId();

		hrUserTaskCacheModel.hrTaskId = getHrTaskId();

		hrUserTaskCacheModel.hrTimesheetId = getHrTimesheetId();

		hrUserTaskCacheModel.hrUserId = getHrUserId();

		return hrUserTaskCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{hrUserTaskId=");
		sb.append(getHrUserTaskId());
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
		sb.append(", hrBillabilityId=");
		sb.append(getHrBillabilityId());
		sb.append(", hrTaskId=");
		sb.append(getHrTaskId());
		sb.append(", hrTimesheetId=");
		sb.append(getHrTimesheetId());
		sb.append(", hrUserId=");
		sb.append(getHrUserId());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(37);

		sb.append("<model><model-name>");
		sb.append("com.liferay.hr.model.HRUserTask");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>hrUserTaskId</column-name><column-value><![CDATA[");
		sb.append(getHrUserTaskId());
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
			"<column><column-name>hrBillabilityId</column-name><column-value><![CDATA[");
		sb.append(getHrBillabilityId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>hrTaskId</column-name><column-value><![CDATA[");
		sb.append(getHrTaskId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>hrTimesheetId</column-name><column-value><![CDATA[");
		sb.append(getHrTimesheetId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>hrUserId</column-name><column-value><![CDATA[");
		sb.append(getHrUserId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = HRUserTask.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			HRUserTask.class
		};
	private long _hrUserTaskId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _hrBillabilityId;
	private long _hrTaskId;
	private long _hrTimesheetId;
	private long _hrUserId;
	private String _hrUserUuid;
	private transient ExpandoBridge _expandoBridge;
	private HRUserTask _escapedModelProxy;
}
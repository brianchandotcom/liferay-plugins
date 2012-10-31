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

package com.liferay.portlet.polls.model;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.polls.service.PollsVoteLocalServiceUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class PollsVoteClp extends BaseModelImpl<PollsVote> implements PollsVote {
	public PollsVoteClp() {
	}

	public Class<?> getModelClass() {
		return PollsVote.class;
	}

	public String getModelClassName() {
		return PollsVote.class.getName();
	}

	public long getPrimaryKey() {
		return _voteId;
	}

	public void setPrimaryKey(long primaryKey) {
		setVoteId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_voteId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("voteId", getVoteId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("questionId", getQuestionId());
		attributes.put("choiceId", getChoiceId());
		attributes.put("voteDate", getVoteDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long voteId = (Long)attributes.get("voteId");

		if (voteId != null) {
			setVoteId(voteId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long questionId = (Long)attributes.get("questionId");

		if (questionId != null) {
			setQuestionId(questionId);
		}

		Long choiceId = (Long)attributes.get("choiceId");

		if (choiceId != null) {
			setChoiceId(choiceId);
		}

		Date voteDate = (Date)attributes.get("voteDate");

		if (voteDate != null) {
			setVoteDate(voteDate);
		}
	}

	public long getVoteId() {
		return _voteId;
	}

	public void setVoteId(long voteId) {
		_voteId = voteId;
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
		return _userName;
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

	public long getQuestionId() {
		return _questionId;
	}

	public void setQuestionId(long questionId) {
		_questionId = questionId;
	}

	public long getChoiceId() {
		return _choiceId;
	}

	public void setChoiceId(long choiceId) {
		_choiceId = choiceId;
	}

	public Date getVoteDate() {
		return _voteDate;
	}

	public void setVoteDate(Date voteDate) {
		_voteDate = voteDate;
	}

	public BaseModel<?> getPollsVoteRemoteModel() {
		return _pollsVoteRemoteModel;
	}

	public void setPollsVoteRemoteModel(BaseModel<?> pollsVoteRemoteModel) {
		_pollsVoteRemoteModel = pollsVoteRemoteModel;
	}

	public void persist() throws SystemException {
		if (this.isNew()) {
			PollsVoteLocalServiceUtil.addPollsVote(this);
		}
		else {
			PollsVoteLocalServiceUtil.updatePollsVote(this);
		}
	}

	@Override
	public PollsVote toEscapedModel() {
		return (PollsVote)Proxy.newProxyInstance(PollsVote.class.getClassLoader(),
			new Class[] { PollsVote.class }, new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		PollsVoteClp clone = new PollsVoteClp();

		clone.setVoteId(getVoteId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setQuestionId(getQuestionId());
		clone.setChoiceId(getChoiceId());
		clone.setVoteDate(getVoteDate());

		return clone;
	}

	public int compareTo(PollsVote pollsVote) {
		long primaryKey = pollsVote.getPrimaryKey();

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

		PollsVoteClp pollsVote = null;

		try {
			pollsVote = (PollsVoteClp)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = pollsVote.getPrimaryKey();

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
		StringBundler sb = new StringBundler(19);

		sb.append("{voteId=");
		sb.append(getVoteId());
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
		sb.append(", questionId=");
		sb.append(getQuestionId());
		sb.append(", choiceId=");
		sb.append(getChoiceId());
		sb.append(", voteDate=");
		sb.append(getVoteDate());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(31);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.polls.model.PollsVote");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>voteId</column-name><column-value><![CDATA[");
		sb.append(getVoteId());
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
			"<column><column-name>questionId</column-name><column-value><![CDATA[");
		sb.append(getQuestionId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>choiceId</column-name><column-value><![CDATA[");
		sb.append(getChoiceId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>voteDate</column-name><column-value><![CDATA[");
		sb.append(getVoteDate());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _voteId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _questionId;
	private long _choiceId;
	private Date _voteDate;
	private BaseModel<?> _pollsVoteRemoteModel;
}
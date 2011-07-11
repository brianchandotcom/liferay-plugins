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

package com.liferay.hr.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.GroupedModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * The base model interface for the HRUser service. Represents a row in the &quot;HRUser&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.hr.model.impl.HRUserModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.hr.model.impl.HRUserImpl}.
 * </p>
 *
 * @author Wesley Gong
 * @see HRUser
 * @see com.liferay.hr.model.impl.HRUserImpl
 * @see com.liferay.hr.model.impl.HRUserModelImpl
 * @generated
 */
public interface HRUserModel extends BaseModel<HRUser>, GroupedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a h r user model instance should use the {@link HRUser} interface instead.
	 */

	/**
	 * Returns the primary key of this h r user.
	 *
	 * @return the primary key of this h r user
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this h r user.
	 *
	 * @param primaryKey the primary key of this h r user
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the hr user ID of this h r user.
	 *
	 * @return the hr user ID of this h r user
	 */
	public long getHrUserId();

	/**
	 * Sets the hr user ID of this h r user.
	 *
	 * @param hrUserId the hr user ID of this h r user
	 */
	public void setHrUserId(long hrUserId);

	/**
	 * Returns the hr user uuid of this h r user.
	 *
	 * @return the hr user uuid of this h r user
	 * @throws SystemException if a system exception occurred
	 */
	public String getHrUserUuid() throws SystemException;

	/**
	 * Sets the hr user uuid of this h r user.
	 *
	 * @param hrUserUuid the hr user uuid of this h r user
	 */
	public void setHrUserUuid(String hrUserUuid);

	/**
	 * Returns the group ID of this h r user.
	 *
	 * @return the group ID of this h r user
	 */
	public long getGroupId();

	/**
	 * Sets the group ID of this h r user.
	 *
	 * @param groupId the group ID of this h r user
	 */
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this h r user.
	 *
	 * @return the company ID of this h r user
	 */
	public long getCompanyId();

	/**
	 * Sets the company ID of this h r user.
	 *
	 * @param companyId the company ID of this h r user
	 */
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this h r user.
	 *
	 * @return the user ID of this h r user
	 */
	public long getUserId();

	/**
	 * Sets the user ID of this h r user.
	 *
	 * @param userId the user ID of this h r user
	 */
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this h r user.
	 *
	 * @return the user uuid of this h r user
	 * @throws SystemException if a system exception occurred
	 */
	public String getUserUuid() throws SystemException;

	/**
	 * Sets the user uuid of this h r user.
	 *
	 * @param userUuid the user uuid of this h r user
	 */
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this h r user.
	 *
	 * @return the user name of this h r user
	 */
	@AutoEscape
	public String getUserName();

	/**
	 * Sets the user name of this h r user.
	 *
	 * @param userName the user name of this h r user
	 */
	public void setUserName(String userName);

	/**
	 * Returns the create date of this h r user.
	 *
	 * @return the create date of this h r user
	 */
	public Date getCreateDate();

	/**
	 * Sets the create date of this h r user.
	 *
	 * @param createDate the create date of this h r user
	 */
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this h r user.
	 *
	 * @return the modified date of this h r user
	 */
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this h r user.
	 *
	 * @param modifiedDate the modified date of this h r user
	 */
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the hr employment type ID of this h r user.
	 *
	 * @return the hr employment type ID of this h r user
	 */
	public long getHrEmploymentTypeId();

	/**
	 * Sets the hr employment type ID of this h r user.
	 *
	 * @param hrEmploymentTypeId the hr employment type ID of this h r user
	 */
	public void setHrEmploymentTypeId(long hrEmploymentTypeId);

	/**
	 * Returns the hr job title ID of this h r user.
	 *
	 * @return the hr job title ID of this h r user
	 */
	public long getHrJobTitleId();

	/**
	 * Sets the hr job title ID of this h r user.
	 *
	 * @param hrJobTitleId the hr job title ID of this h r user
	 */
	public void setHrJobTitleId(long hrJobTitleId);

	/**
	 * Returns the hr office ID of this h r user.
	 *
	 * @return the hr office ID of this h r user
	 */
	public long getHrOfficeId();

	/**
	 * Sets the hr office ID of this h r user.
	 *
	 * @param hrOfficeId the hr office ID of this h r user
	 */
	public void setHrOfficeId(long hrOfficeId);

	/**
	 * Returns the hr termination type ID of this h r user.
	 *
	 * @return the hr termination type ID of this h r user
	 */
	public long getHrTerminationTypeId();

	/**
	 * Sets the hr termination type ID of this h r user.
	 *
	 * @param hrTerminationTypeId the hr termination type ID of this h r user
	 */
	public void setHrTerminationTypeId(long hrTerminationTypeId);

	/**
	 * Returns the hr wage type ID of this h r user.
	 *
	 * @return the hr wage type ID of this h r user
	 */
	public long getHrWageTypeId();

	/**
	 * Sets the hr wage type ID of this h r user.
	 *
	 * @param hrWageTypeId the hr wage type ID of this h r user
	 */
	public void setHrWageTypeId(long hrWageTypeId);

	/**
	 * Returns the hire date of this h r user.
	 *
	 * @return the hire date of this h r user
	 */
	public Date getHireDate();

	/**
	 * Sets the hire date of this h r user.
	 *
	 * @param hireDate the hire date of this h r user
	 */
	public void setHireDate(Date hireDate);

	/**
	 * Returns the termination date of this h r user.
	 *
	 * @return the termination date of this h r user
	 */
	public Date getTerminationDate();

	/**
	 * Sets the termination date of this h r user.
	 *
	 * @param terminationDate the termination date of this h r user
	 */
	public void setTerminationDate(Date terminationDate);

	/**
	 * Returns the wage amount of this h r user.
	 *
	 * @return the wage amount of this h r user
	 */
	public double getWageAmount();

	/**
	 * Sets the wage amount of this h r user.
	 *
	 * @param wageAmount the wage amount of this h r user
	 */
	public void setWageAmount(double wageAmount);

	/**
	 * Returns the wage currency code of this h r user.
	 *
	 * @return the wage currency code of this h r user
	 */
	@AutoEscape
	public String getWageCurrencyCode();

	/**
	 * Sets the wage currency code of this h r user.
	 *
	 * @param wageCurrencyCode the wage currency code of this h r user
	 */
	public void setWageCurrencyCode(String wageCurrencyCode);

	/**
	 * Returns the benefits exempt of this h r user.
	 *
	 * @return the benefits exempt of this h r user
	 */
	public boolean getBenefitsExempt();

	/**
	 * Returns <code>true</code> if this h r user is benefits exempt.
	 *
	 * @return <code>true</code> if this h r user is benefits exempt; <code>false</code> otherwise
	 */
	public boolean isBenefitsExempt();

	/**
	 * Sets whether this h r user is benefits exempt.
	 *
	 * @param benefitsExempt the benefits exempt of this h r user
	 */
	public void setBenefitsExempt(boolean benefitsExempt);

	/**
	 * Returns the overtime exempt of this h r user.
	 *
	 * @return the overtime exempt of this h r user
	 */
	public boolean getOvertimeExempt();

	/**
	 * Returns <code>true</code> if this h r user is overtime exempt.
	 *
	 * @return <code>true</code> if this h r user is overtime exempt; <code>false</code> otherwise
	 */
	public boolean isOvertimeExempt();

	/**
	 * Sets whether this h r user is overtime exempt.
	 *
	 * @param overtimeExempt the overtime exempt of this h r user
	 */
	public void setOvertimeExempt(boolean overtimeExempt);

	public boolean isNew();

	public void setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public void setEscapedModel(boolean escapedModel);

	public Serializable getPrimaryKeyObj();

	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(HRUser hrUser);

	public int hashCode();

	public CacheModel<HRUser> toCacheModel();

	public HRUser toEscapedModel();

	public String toString();

	public String toXmlString();
}
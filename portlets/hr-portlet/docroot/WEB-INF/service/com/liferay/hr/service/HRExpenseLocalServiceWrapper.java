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

package com.liferay.hr.service;

/**
 * <p>
 * This class is a wrapper for {@link HRExpenseLocalService}.
 * </p>
 *
 * @author    Wesley Gong
 * @see       HRExpenseLocalService
 * @generated
 */
public class HRExpenseLocalServiceWrapper implements HRExpenseLocalService {
	public HRExpenseLocalServiceWrapper(
		HRExpenseLocalService hrExpenseLocalService) {
		_hrExpenseLocalService = hrExpenseLocalService;
	}

	/**
	* Adds the h r expense to the database. Also notifies the appropriate model listeners.
	*
	* @param hrExpense the h r expense
	* @return the h r expense that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.hr.model.HRExpense addHRExpense(
		com.liferay.hr.model.HRExpense hrExpense)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _hrExpenseLocalService.addHRExpense(hrExpense);
	}

	/**
	* Creates a new h r expense with the primary key. Does not add the h r expense to the database.
	*
	* @param hrExpenseId the primary key for the new h r expense
	* @return the new h r expense
	*/
	public com.liferay.hr.model.HRExpense createHRExpense(long hrExpenseId) {
		return _hrExpenseLocalService.createHRExpense(hrExpenseId);
	}

	/**
	* Deletes the h r expense with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param hrExpenseId the primary key of the h r expense
	* @throws PortalException if a h r expense with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteHRExpense(long hrExpenseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_hrExpenseLocalService.deleteHRExpense(hrExpenseId);
	}

	/**
	* Deletes the h r expense from the database. Also notifies the appropriate model listeners.
	*
	* @param hrExpense the h r expense
	* @throws SystemException if a system exception occurred
	*/
	public void deleteHRExpense(com.liferay.hr.model.HRExpense hrExpense)
		throws com.liferay.portal.kernel.exception.SystemException {
		_hrExpenseLocalService.deleteHRExpense(hrExpense);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _hrExpenseLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _hrExpenseLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _hrExpenseLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _hrExpenseLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the h r expense with the primary key.
	*
	* @param hrExpenseId the primary key of the h r expense
	* @return the h r expense
	* @throws PortalException if a h r expense with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.hr.model.HRExpense getHRExpense(long hrExpenseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _hrExpenseLocalService.getHRExpense(hrExpenseId);
	}

	/**
	* Returns a range of all the h r expenses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of h r expenses
	* @param end the upper bound of the range of h r expenses (not inclusive)
	* @return the range of h r expenses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.hr.model.HRExpense> getHRExpenses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _hrExpenseLocalService.getHRExpenses(start, end);
	}

	/**
	* Returns the number of h r expenses.
	*
	* @return the number of h r expenses
	* @throws SystemException if a system exception occurred
	*/
	public int getHRExpensesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _hrExpenseLocalService.getHRExpensesCount();
	}

	/**
	* Updates the h r expense in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param hrExpense the h r expense
	* @return the h r expense that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.hr.model.HRExpense updateHRExpense(
		com.liferay.hr.model.HRExpense hrExpense)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _hrExpenseLocalService.updateHRExpense(hrExpense);
	}

	/**
	* Updates the h r expense in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param hrExpense the h r expense
	* @param merge whether to merge the h r expense with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the h r expense that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.hr.model.HRExpense updateHRExpense(
		com.liferay.hr.model.HRExpense hrExpense, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _hrExpenseLocalService.updateHRExpense(hrExpense, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _hrExpenseLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_hrExpenseLocalService.setBeanIdentifier(beanIdentifier);
	}

	public HRExpenseLocalService getWrappedHRExpenseLocalService() {
		return _hrExpenseLocalService;
	}

	public void setWrappedHRExpenseLocalService(
		HRExpenseLocalService hrExpenseLocalService) {
		_hrExpenseLocalService = hrExpenseLocalService;
	}

	private HRExpenseLocalService _hrExpenseLocalService;
}
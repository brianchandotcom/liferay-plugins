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

package com.liferay.wsrp.service.base;

import com.liferay.counter.service.CounterLocalService;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.GroupService;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.LayoutService;
import com.liferay.portal.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.LayoutPersistence;
import com.liferay.portal.service.persistence.UserPersistence;

import com.liferay.wsrp.model.WSRPProducer;
import com.liferay.wsrp.service.WSRPConsumerLocalService;
import com.liferay.wsrp.service.WSRPConsumerPortletLocalService;
import com.liferay.wsrp.service.WSRPProducerLocalService;
import com.liferay.wsrp.service.persistence.WSRPConsumerPersistence;
import com.liferay.wsrp.service.persistence.WSRPConsumerPortletPersistence;
import com.liferay.wsrp.service.persistence.WSRPProducerPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * The base implementation of the w s r p producer local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.wsrp.service.impl.WSRPProducerLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.wsrp.service.impl.WSRPProducerLocalServiceImpl
 * @see com.liferay.wsrp.service.WSRPProducerLocalServiceUtil
 * @generated
 */
public abstract class WSRPProducerLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements WSRPProducerLocalService,
		IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.wsrp.service.WSRPProducerLocalServiceUtil} to access the w s r p producer local service.
	 */

	/**
	 * Adds the w s r p producer to the database. Also notifies the appropriate model listeners.
	 *
	 * @param wsrpProducer the w s r p producer
	 * @return the w s r p producer that was added
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	public WSRPProducer addWSRPProducer(WSRPProducer wsrpProducer)
		throws SystemException {
		wsrpProducer.setNew(true);

		return wsrpProducerPersistence.update(wsrpProducer);
	}

	/**
	 * Creates a new w s r p producer with the primary key. Does not add the w s r p producer to the database.
	 *
	 * @param wsrpProducerId the primary key for the new w s r p producer
	 * @return the new w s r p producer
	 */
	public WSRPProducer createWSRPProducer(long wsrpProducerId) {
		return wsrpProducerPersistence.create(wsrpProducerId);
	}

	/**
	 * Deletes the w s r p producer with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param wsrpProducerId the primary key of the w s r p producer
	 * @return the w s r p producer that was removed
	 * @throws PortalException if a w s r p producer with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	public WSRPProducer deleteWSRPProducer(long wsrpProducerId)
		throws PortalException, SystemException {
		return wsrpProducerPersistence.remove(wsrpProducerId);
	}

	/**
	 * Deletes the w s r p producer from the database. Also notifies the appropriate model listeners.
	 *
	 * @param wsrpProducer the w s r p producer
	 * @return the w s r p producer that was removed
	 * @throws PortalException
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	public WSRPProducer deleteWSRPProducer(WSRPProducer wsrpProducer)
		throws PortalException, SystemException {
		return wsrpProducerPersistence.remove(wsrpProducer);
	}

	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(WSRPProducer.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return wsrpProducerPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. When orderByComparator is specified, the query will include the given ORDER BY logic. When orderByComparator is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), the query will include the default ORDER BY logic from WSRPProducerModelImpl. If both orderByComparator and pagination are absent, for performance reason, the query will not have a ORDER BY clause, on returning the result set will be sorted on portal side by PK ASC order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return wsrpProducerPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. When orderByComparator is specified, the query will include the given ORDER BY logic. When orderByComparator is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), the query will include the default ORDER BY logic from WSRPProducerModelImpl. If both orderByComparator and pagination are absent, for performance reason, the query will not have a ORDER BY clause, on returning the result set will be sorted on portal side by PK ASC order.
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
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return wsrpProducerPersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows that match the dynamic query
	 * @throws SystemException if a system exception occurred
	 */
	public long dynamicQueryCount(DynamicQuery dynamicQuery)
		throws SystemException {
		return wsrpProducerPersistence.countWithDynamicQuery(dynamicQuery);
	}

	public WSRPProducer fetchWSRPProducer(long wsrpProducerId)
		throws SystemException {
		return wsrpProducerPersistence.fetchByPrimaryKey(wsrpProducerId);
	}

	/**
	 * Returns the w s r p producer with the primary key.
	 *
	 * @param wsrpProducerId the primary key of the w s r p producer
	 * @return the w s r p producer
	 * @throws PortalException if a w s r p producer with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public WSRPProducer getWSRPProducer(long wsrpProducerId)
		throws PortalException, SystemException {
		return wsrpProducerPersistence.findByPrimaryKey(wsrpProducerId);
	}

	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException, SystemException {
		return wsrpProducerPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns the w s r p producer with the UUID in the group.
	 *
	 * @param uuid the UUID of w s r p producer
	 * @param groupId the group id of the w s r p producer
	 * @return the w s r p producer
	 * @throws PortalException if a w s r p producer with the UUID in the group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public WSRPProducer getWSRPProducerByUuidAndGroupId(String uuid,
		long groupId) throws PortalException, SystemException {
		return wsrpProducerPersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the w s r p producers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. When orderByComparator is specified, the query will include the given ORDER BY logic. When orderByComparator is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), the query will include the default ORDER BY logic from WSRPProducerModelImpl. If both orderByComparator and pagination are absent, for performance reason, the query will not have a ORDER BY clause, on returning the result set will be sorted on portal side by PK ASC order.
	 * </p>
	 *
	 * @param start the lower bound of the range of w s r p producers
	 * @param end the upper bound of the range of w s r p producers (not inclusive)
	 * @return the range of w s r p producers
	 * @throws SystemException if a system exception occurred
	 */
	public List<WSRPProducer> getWSRPProducers(int start, int end)
		throws SystemException {
		return wsrpProducerPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of w s r p producers.
	 *
	 * @return the number of w s r p producers
	 * @throws SystemException if a system exception occurred
	 */
	public int getWSRPProducersCount() throws SystemException {
		return wsrpProducerPersistence.countAll();
	}

	/**
	 * Updates the w s r p producer in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param wsrpProducer the w s r p producer
	 * @return the w s r p producer that was updated
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	public WSRPProducer updateWSRPProducer(WSRPProducer wsrpProducer)
		throws SystemException {
		return wsrpProducerPersistence.update(wsrpProducer);
	}

	/**
	 * Returns the w s r p consumer local service.
	 *
	 * @return the w s r p consumer local service
	 */
	public WSRPConsumerLocalService getWSRPConsumerLocalService() {
		return wsrpConsumerLocalService;
	}

	/**
	 * Sets the w s r p consumer local service.
	 *
	 * @param wsrpConsumerLocalService the w s r p consumer local service
	 */
	public void setWSRPConsumerLocalService(
		WSRPConsumerLocalService wsrpConsumerLocalService) {
		this.wsrpConsumerLocalService = wsrpConsumerLocalService;
	}

	/**
	 * Returns the w s r p consumer persistence.
	 *
	 * @return the w s r p consumer persistence
	 */
	public WSRPConsumerPersistence getWSRPConsumerPersistence() {
		return wsrpConsumerPersistence;
	}

	/**
	 * Sets the w s r p consumer persistence.
	 *
	 * @param wsrpConsumerPersistence the w s r p consumer persistence
	 */
	public void setWSRPConsumerPersistence(
		WSRPConsumerPersistence wsrpConsumerPersistence) {
		this.wsrpConsumerPersistence = wsrpConsumerPersistence;
	}

	/**
	 * Returns the w s r p consumer portlet local service.
	 *
	 * @return the w s r p consumer portlet local service
	 */
	public WSRPConsumerPortletLocalService getWSRPConsumerPortletLocalService() {
		return wsrpConsumerPortletLocalService;
	}

	/**
	 * Sets the w s r p consumer portlet local service.
	 *
	 * @param wsrpConsumerPortletLocalService the w s r p consumer portlet local service
	 */
	public void setWSRPConsumerPortletLocalService(
		WSRPConsumerPortletLocalService wsrpConsumerPortletLocalService) {
		this.wsrpConsumerPortletLocalService = wsrpConsumerPortletLocalService;
	}

	/**
	 * Returns the w s r p consumer portlet persistence.
	 *
	 * @return the w s r p consumer portlet persistence
	 */
	public WSRPConsumerPortletPersistence getWSRPConsumerPortletPersistence() {
		return wsrpConsumerPortletPersistence;
	}

	/**
	 * Sets the w s r p consumer portlet persistence.
	 *
	 * @param wsrpConsumerPortletPersistence the w s r p consumer portlet persistence
	 */
	public void setWSRPConsumerPortletPersistence(
		WSRPConsumerPortletPersistence wsrpConsumerPortletPersistence) {
		this.wsrpConsumerPortletPersistence = wsrpConsumerPortletPersistence;
	}

	/**
	 * Returns the w s r p producer local service.
	 *
	 * @return the w s r p producer local service
	 */
	public WSRPProducerLocalService getWSRPProducerLocalService() {
		return wsrpProducerLocalService;
	}

	/**
	 * Sets the w s r p producer local service.
	 *
	 * @param wsrpProducerLocalService the w s r p producer local service
	 */
	public void setWSRPProducerLocalService(
		WSRPProducerLocalService wsrpProducerLocalService) {
		this.wsrpProducerLocalService = wsrpProducerLocalService;
	}

	/**
	 * Returns the w s r p producer persistence.
	 *
	 * @return the w s r p producer persistence
	 */
	public WSRPProducerPersistence getWSRPProducerPersistence() {
		return wsrpProducerPersistence;
	}

	/**
	 * Sets the w s r p producer persistence.
	 *
	 * @param wsrpProducerPersistence the w s r p producer persistence
	 */
	public void setWSRPProducerPersistence(
		WSRPProducerPersistence wsrpProducerPersistence) {
		this.wsrpProducerPersistence = wsrpProducerPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the group local service.
	 *
	 * @return the group local service
	 */
	public GroupLocalService getGroupLocalService() {
		return groupLocalService;
	}

	/**
	 * Sets the group local service.
	 *
	 * @param groupLocalService the group local service
	 */
	public void setGroupLocalService(GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	/**
	 * Returns the group remote service.
	 *
	 * @return the group remote service
	 */
	public GroupService getGroupService() {
		return groupService;
	}

	/**
	 * Sets the group remote service.
	 *
	 * @param groupService the group remote service
	 */
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	/**
	 * Returns the group persistence.
	 *
	 * @return the group persistence
	 */
	public GroupPersistence getGroupPersistence() {
		return groupPersistence;
	}

	/**
	 * Sets the group persistence.
	 *
	 * @param groupPersistence the group persistence
	 */
	public void setGroupPersistence(GroupPersistence groupPersistence) {
		this.groupPersistence = groupPersistence;
	}

	/**
	 * Returns the layout local service.
	 *
	 * @return the layout local service
	 */
	public LayoutLocalService getLayoutLocalService() {
		return layoutLocalService;
	}

	/**
	 * Sets the layout local service.
	 *
	 * @param layoutLocalService the layout local service
	 */
	public void setLayoutLocalService(LayoutLocalService layoutLocalService) {
		this.layoutLocalService = layoutLocalService;
	}

	/**
	 * Returns the layout remote service.
	 *
	 * @return the layout remote service
	 */
	public LayoutService getLayoutService() {
		return layoutService;
	}

	/**
	 * Sets the layout remote service.
	 *
	 * @param layoutService the layout remote service
	 */
	public void setLayoutService(LayoutService layoutService) {
		this.layoutService = layoutService;
	}

	/**
	 * Returns the layout persistence.
	 *
	 * @return the layout persistence
	 */
	public LayoutPersistence getLayoutPersistence() {
		return layoutPersistence;
	}

	/**
	 * Sets the layout persistence.
	 *
	 * @param layoutPersistence the layout persistence
	 */
	public void setLayoutPersistence(LayoutPersistence layoutPersistence) {
		this.layoutPersistence = layoutPersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		Class<?> clazz = getClass();

		_classLoader = clazz.getClassLoader();

		PersistedModelLocalServiceRegistryUtil.register("com.liferay.wsrp.model.WSRPProducer",
			wsrpProducerLocalService);
	}

	public void destroy() {
		PersistedModelLocalServiceRegistryUtil.unregister(
			"com.liferay.wsrp.model.WSRPProducer");
	}

	/**
	 * Returns the Spring bean ID for this bean.
	 *
	 * @return the Spring bean ID for this bean
	 */
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		if (contextClassLoader != _classLoader) {
			currentThread.setContextClassLoader(_classLoader);
		}

		try {
			return _clpInvoker.invokeMethod(name, parameterTypes, arguments);
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	protected Class<?> getModelClass() {
		return WSRPProducer.class;
	}

	protected String getModelClassName() {
		return WSRPProducer.class.getName();
	}

	/**
	 * Performs an SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) throws SystemException {
		try {
			DataSource dataSource = wsrpProducerPersistence.getDataSource();

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = WSRPConsumerLocalService.class)
	protected WSRPConsumerLocalService wsrpConsumerLocalService;
	@BeanReference(type = WSRPConsumerPersistence.class)
	protected WSRPConsumerPersistence wsrpConsumerPersistence;
	@BeanReference(type = WSRPConsumerPortletLocalService.class)
	protected WSRPConsumerPortletLocalService wsrpConsumerPortletLocalService;
	@BeanReference(type = WSRPConsumerPortletPersistence.class)
	protected WSRPConsumerPortletPersistence wsrpConsumerPortletPersistence;
	@BeanReference(type = WSRPProducerLocalService.class)
	protected WSRPProducerLocalService wsrpProducerLocalService;
	@BeanReference(type = WSRPProducerPersistence.class)
	protected WSRPProducerPersistence wsrpProducerPersistence;
	@BeanReference(type = CounterLocalService.class)
	protected CounterLocalService counterLocalService;
	@BeanReference(type = GroupLocalService.class)
	protected GroupLocalService groupLocalService;
	@BeanReference(type = GroupService.class)
	protected GroupService groupService;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = LayoutLocalService.class)
	protected LayoutLocalService layoutLocalService;
	@BeanReference(type = LayoutService.class)
	protected LayoutService layoutService;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = ResourceLocalService.class)
	protected ResourceLocalService resourceLocalService;
	@BeanReference(type = UserLocalService.class)
	protected UserLocalService userLocalService;
	@BeanReference(type = UserService.class)
	protected UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private String _beanIdentifier;
	private ClassLoader _classLoader;
	private WSRPProducerLocalServiceClpInvoker _clpInvoker = new WSRPProducerLocalServiceClpInvoker();
}
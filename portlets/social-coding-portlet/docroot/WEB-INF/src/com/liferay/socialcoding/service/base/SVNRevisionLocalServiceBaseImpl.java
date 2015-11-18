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

package com.liferay.socialcoding.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.service.persistence.ClassNamePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.util.PortalUtil;

import com.liferay.socialcoding.model.SVNRevision;
import com.liferay.socialcoding.service.SVNRevisionLocalService;
import com.liferay.socialcoding.service.persistence.JIRAActionFinder;
import com.liferay.socialcoding.service.persistence.JIRAActionPersistence;
import com.liferay.socialcoding.service.persistence.JIRAChangeGroupFinder;
import com.liferay.socialcoding.service.persistence.JIRAChangeGroupPersistence;
import com.liferay.socialcoding.service.persistence.JIRAChangeItemPersistence;
import com.liferay.socialcoding.service.persistence.JIRAIssueFinder;
import com.liferay.socialcoding.service.persistence.JIRAIssuePersistence;
import com.liferay.socialcoding.service.persistence.JIRAProjectPersistence;
import com.liferay.socialcoding.service.persistence.SVNRepositoryPersistence;
import com.liferay.socialcoding.service.persistence.SVNRevisionPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the s v n revision local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.socialcoding.service.impl.SVNRevisionLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.socialcoding.service.impl.SVNRevisionLocalServiceImpl
 * @see com.liferay.socialcoding.service.SVNRevisionLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class SVNRevisionLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements SVNRevisionLocalService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.socialcoding.service.SVNRevisionLocalServiceUtil} to access the s v n revision local service.
	 */

	/**
	 * Adds the s v n revision to the database. Also notifies the appropriate model listeners.
	 *
	 * @param svnRevision the s v n revision
	 * @return the s v n revision that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SVNRevision addSVNRevision(SVNRevision svnRevision) {
		svnRevision.setNew(true);

		return svnRevisionPersistence.update(svnRevision);
	}

	/**
	 * Creates a new s v n revision with the primary key. Does not add the s v n revision to the database.
	 *
	 * @param svnRevisionId the primary key for the new s v n revision
	 * @return the new s v n revision
	 */
	@Override
	public SVNRevision createSVNRevision(long svnRevisionId) {
		return svnRevisionPersistence.create(svnRevisionId);
	}

	/**
	 * Deletes the s v n revision with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param svnRevisionId the primary key of the s v n revision
	 * @return the s v n revision that was removed
	 * @throws PortalException if a s v n revision with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public SVNRevision deleteSVNRevision(long svnRevisionId)
		throws PortalException {
		return svnRevisionPersistence.remove(svnRevisionId);
	}

	/**
	 * Deletes the s v n revision from the database. Also notifies the appropriate model listeners.
	 *
	 * @param svnRevision the s v n revision
	 * @return the s v n revision that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public SVNRevision deleteSVNRevision(SVNRevision svnRevision) {
		return svnRevisionPersistence.remove(svnRevision);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(SVNRevision.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return svnRevisionPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.socialcoding.model.impl.SVNRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) {
		return svnRevisionPersistence.findWithDynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.socialcoding.model.impl.SVNRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator) {
		return svnRevisionPersistence.findWithDynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return svnRevisionPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) {
		return svnRevisionPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public SVNRevision fetchSVNRevision(long svnRevisionId) {
		return svnRevisionPersistence.fetchByPrimaryKey(svnRevisionId);
	}

	/**
	 * Returns the s v n revision with the primary key.
	 *
	 * @param svnRevisionId the primary key of the s v n revision
	 * @return the s v n revision
	 * @throws PortalException if a s v n revision with the primary key could not be found
	 */
	@Override
	public SVNRevision getSVNRevision(long svnRevisionId)
		throws PortalException {
		return svnRevisionPersistence.findByPrimaryKey(svnRevisionId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(com.liferay.socialcoding.service.SVNRevisionLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(SVNRevision.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName("svnRevisionId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(com.liferay.socialcoding.service.SVNRevisionLocalServiceUtil.getService());
		indexableActionableDynamicQuery.setClass(SVNRevision.class);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"svnRevisionId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(com.liferay.socialcoding.service.SVNRevisionLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(SVNRevision.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName("svnRevisionId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return svnRevisionLocalService.deleteSVNRevision((SVNRevision)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return svnRevisionPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the s v n revisions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.socialcoding.model.impl.SVNRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of s v n revisions
	 * @param end the upper bound of the range of s v n revisions (not inclusive)
	 * @return the range of s v n revisions
	 */
	@Override
	public List<SVNRevision> getSVNRevisions(int start, int end) {
		return svnRevisionPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of s v n revisions.
	 *
	 * @return the number of s v n revisions
	 */
	@Override
	public int getSVNRevisionsCount() {
		return svnRevisionPersistence.countAll();
	}

	/**
	 * Updates the s v n revision in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param svnRevision the s v n revision
	 * @return the s v n revision that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SVNRevision updateSVNRevision(SVNRevision svnRevision) {
		return svnRevisionPersistence.update(svnRevision);
	}

	/**
	 * Returns the j i r a action local service.
	 *
	 * @return the j i r a action local service
	 */
	public com.liferay.socialcoding.service.JIRAActionLocalService getJIRAActionLocalService() {
		return jiraActionLocalService;
	}

	/**
	 * Sets the j i r a action local service.
	 *
	 * @param jiraActionLocalService the j i r a action local service
	 */
	public void setJIRAActionLocalService(
		com.liferay.socialcoding.service.JIRAActionLocalService jiraActionLocalService) {
		this.jiraActionLocalService = jiraActionLocalService;
	}

	/**
	 * Returns the j i r a action persistence.
	 *
	 * @return the j i r a action persistence
	 */
	public JIRAActionPersistence getJIRAActionPersistence() {
		return jiraActionPersistence;
	}

	/**
	 * Sets the j i r a action persistence.
	 *
	 * @param jiraActionPersistence the j i r a action persistence
	 */
	public void setJIRAActionPersistence(
		JIRAActionPersistence jiraActionPersistence) {
		this.jiraActionPersistence = jiraActionPersistence;
	}

	/**
	 * Returns the j i r a action finder.
	 *
	 * @return the j i r a action finder
	 */
	public JIRAActionFinder getJIRAActionFinder() {
		return jiraActionFinder;
	}

	/**
	 * Sets the j i r a action finder.
	 *
	 * @param jiraActionFinder the j i r a action finder
	 */
	public void setJIRAActionFinder(JIRAActionFinder jiraActionFinder) {
		this.jiraActionFinder = jiraActionFinder;
	}

	/**
	 * Returns the j i r a change group local service.
	 *
	 * @return the j i r a change group local service
	 */
	public com.liferay.socialcoding.service.JIRAChangeGroupLocalService getJIRAChangeGroupLocalService() {
		return jiraChangeGroupLocalService;
	}

	/**
	 * Sets the j i r a change group local service.
	 *
	 * @param jiraChangeGroupLocalService the j i r a change group local service
	 */
	public void setJIRAChangeGroupLocalService(
		com.liferay.socialcoding.service.JIRAChangeGroupLocalService jiraChangeGroupLocalService) {
		this.jiraChangeGroupLocalService = jiraChangeGroupLocalService;
	}

	/**
	 * Returns the j i r a change group persistence.
	 *
	 * @return the j i r a change group persistence
	 */
	public JIRAChangeGroupPersistence getJIRAChangeGroupPersistence() {
		return jiraChangeGroupPersistence;
	}

	/**
	 * Sets the j i r a change group persistence.
	 *
	 * @param jiraChangeGroupPersistence the j i r a change group persistence
	 */
	public void setJIRAChangeGroupPersistence(
		JIRAChangeGroupPersistence jiraChangeGroupPersistence) {
		this.jiraChangeGroupPersistence = jiraChangeGroupPersistence;
	}

	/**
	 * Returns the j i r a change group finder.
	 *
	 * @return the j i r a change group finder
	 */
	public JIRAChangeGroupFinder getJIRAChangeGroupFinder() {
		return jiraChangeGroupFinder;
	}

	/**
	 * Sets the j i r a change group finder.
	 *
	 * @param jiraChangeGroupFinder the j i r a change group finder
	 */
	public void setJIRAChangeGroupFinder(
		JIRAChangeGroupFinder jiraChangeGroupFinder) {
		this.jiraChangeGroupFinder = jiraChangeGroupFinder;
	}

	/**
	 * Returns the j i r a change item local service.
	 *
	 * @return the j i r a change item local service
	 */
	public com.liferay.socialcoding.service.JIRAChangeItemLocalService getJIRAChangeItemLocalService() {
		return jiraChangeItemLocalService;
	}

	/**
	 * Sets the j i r a change item local service.
	 *
	 * @param jiraChangeItemLocalService the j i r a change item local service
	 */
	public void setJIRAChangeItemLocalService(
		com.liferay.socialcoding.service.JIRAChangeItemLocalService jiraChangeItemLocalService) {
		this.jiraChangeItemLocalService = jiraChangeItemLocalService;
	}

	/**
	 * Returns the j i r a change item persistence.
	 *
	 * @return the j i r a change item persistence
	 */
	public JIRAChangeItemPersistence getJIRAChangeItemPersistence() {
		return jiraChangeItemPersistence;
	}

	/**
	 * Sets the j i r a change item persistence.
	 *
	 * @param jiraChangeItemPersistence the j i r a change item persistence
	 */
	public void setJIRAChangeItemPersistence(
		JIRAChangeItemPersistence jiraChangeItemPersistence) {
		this.jiraChangeItemPersistence = jiraChangeItemPersistence;
	}

	/**
	 * Returns the j i r a issue local service.
	 *
	 * @return the j i r a issue local service
	 */
	public com.liferay.socialcoding.service.JIRAIssueLocalService getJIRAIssueLocalService() {
		return jiraIssueLocalService;
	}

	/**
	 * Sets the j i r a issue local service.
	 *
	 * @param jiraIssueLocalService the j i r a issue local service
	 */
	public void setJIRAIssueLocalService(
		com.liferay.socialcoding.service.JIRAIssueLocalService jiraIssueLocalService) {
		this.jiraIssueLocalService = jiraIssueLocalService;
	}

	/**
	 * Returns the j i r a issue persistence.
	 *
	 * @return the j i r a issue persistence
	 */
	public JIRAIssuePersistence getJIRAIssuePersistence() {
		return jiraIssuePersistence;
	}

	/**
	 * Sets the j i r a issue persistence.
	 *
	 * @param jiraIssuePersistence the j i r a issue persistence
	 */
	public void setJIRAIssuePersistence(
		JIRAIssuePersistence jiraIssuePersistence) {
		this.jiraIssuePersistence = jiraIssuePersistence;
	}

	/**
	 * Returns the j i r a issue finder.
	 *
	 * @return the j i r a issue finder
	 */
	public JIRAIssueFinder getJIRAIssueFinder() {
		return jiraIssueFinder;
	}

	/**
	 * Sets the j i r a issue finder.
	 *
	 * @param jiraIssueFinder the j i r a issue finder
	 */
	public void setJIRAIssueFinder(JIRAIssueFinder jiraIssueFinder) {
		this.jiraIssueFinder = jiraIssueFinder;
	}

	/**
	 * Returns the j i r a project local service.
	 *
	 * @return the j i r a project local service
	 */
	public com.liferay.socialcoding.service.JIRAProjectLocalService getJIRAProjectLocalService() {
		return jiraProjectLocalService;
	}

	/**
	 * Sets the j i r a project local service.
	 *
	 * @param jiraProjectLocalService the j i r a project local service
	 */
	public void setJIRAProjectLocalService(
		com.liferay.socialcoding.service.JIRAProjectLocalService jiraProjectLocalService) {
		this.jiraProjectLocalService = jiraProjectLocalService;
	}

	/**
	 * Returns the j i r a project persistence.
	 *
	 * @return the j i r a project persistence
	 */
	public JIRAProjectPersistence getJIRAProjectPersistence() {
		return jiraProjectPersistence;
	}

	/**
	 * Sets the j i r a project persistence.
	 *
	 * @param jiraProjectPersistence the j i r a project persistence
	 */
	public void setJIRAProjectPersistence(
		JIRAProjectPersistence jiraProjectPersistence) {
		this.jiraProjectPersistence = jiraProjectPersistence;
	}

	/**
	 * Returns the s v n repository local service.
	 *
	 * @return the s v n repository local service
	 */
	public com.liferay.socialcoding.service.SVNRepositoryLocalService getSVNRepositoryLocalService() {
		return svnRepositoryLocalService;
	}

	/**
	 * Sets the s v n repository local service.
	 *
	 * @param svnRepositoryLocalService the s v n repository local service
	 */
	public void setSVNRepositoryLocalService(
		com.liferay.socialcoding.service.SVNRepositoryLocalService svnRepositoryLocalService) {
		this.svnRepositoryLocalService = svnRepositoryLocalService;
	}

	/**
	 * Returns the s v n repository persistence.
	 *
	 * @return the s v n repository persistence
	 */
	public SVNRepositoryPersistence getSVNRepositoryPersistence() {
		return svnRepositoryPersistence;
	}

	/**
	 * Sets the s v n repository persistence.
	 *
	 * @param svnRepositoryPersistence the s v n repository persistence
	 */
	public void setSVNRepositoryPersistence(
		SVNRepositoryPersistence svnRepositoryPersistence) {
		this.svnRepositoryPersistence = svnRepositoryPersistence;
	}

	/**
	 * Returns the s v n revision local service.
	 *
	 * @return the s v n revision local service
	 */
	public SVNRevisionLocalService getSVNRevisionLocalService() {
		return svnRevisionLocalService;
	}

	/**
	 * Sets the s v n revision local service.
	 *
	 * @param svnRevisionLocalService the s v n revision local service
	 */
	public void setSVNRevisionLocalService(
		SVNRevisionLocalService svnRevisionLocalService) {
		this.svnRevisionLocalService = svnRevisionLocalService;
	}

	/**
	 * Returns the s v n revision persistence.
	 *
	 * @return the s v n revision persistence
	 */
	public SVNRevisionPersistence getSVNRevisionPersistence() {
		return svnRevisionPersistence;
	}

	/**
	 * Sets the s v n revision persistence.
	 *
	 * @param svnRevisionPersistence the s v n revision persistence
	 */
	public void setSVNRevisionPersistence(
		SVNRevisionPersistence svnRevisionPersistence) {
		this.svnRevisionPersistence = svnRevisionPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name remote service.
	 *
	 * @return the class name remote service
	 */
	public com.liferay.portal.service.ClassNameService getClassNameService() {
		return classNameService;
	}

	/**
	 * Sets the class name remote service.
	 *
	 * @param classNameService the class name remote service
	 */
	public void setClassNameService(
		com.liferay.portal.service.ClassNameService classNameService) {
		this.classNameService = classNameService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public com.liferay.portal.service.UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(
		com.liferay.portal.service.UserService userService) {
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

		PersistedModelLocalServiceRegistryUtil.register("com.liferay.socialcoding.model.SVNRevision",
			svnRevisionLocalService);
	}

	public void destroy() {
		PersistedModelLocalServiceRegistryUtil.unregister(
			"com.liferay.socialcoding.model.SVNRevision");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return SVNRevisionLocalService.class.getName();
	}

	@Override
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
		return SVNRevision.class;
	}

	protected String getModelClassName() {
		return SVNRevision.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = svnRevisionPersistence.getDataSource();

			DB db = DBFactoryUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.socialcoding.service.JIRAActionLocalService.class)
	protected com.liferay.socialcoding.service.JIRAActionLocalService jiraActionLocalService;
	@BeanReference(type = JIRAActionPersistence.class)
	protected JIRAActionPersistence jiraActionPersistence;
	@BeanReference(type = JIRAActionFinder.class)
	protected JIRAActionFinder jiraActionFinder;
	@BeanReference(type = com.liferay.socialcoding.service.JIRAChangeGroupLocalService.class)
	protected com.liferay.socialcoding.service.JIRAChangeGroupLocalService jiraChangeGroupLocalService;
	@BeanReference(type = JIRAChangeGroupPersistence.class)
	protected JIRAChangeGroupPersistence jiraChangeGroupPersistence;
	@BeanReference(type = JIRAChangeGroupFinder.class)
	protected JIRAChangeGroupFinder jiraChangeGroupFinder;
	@BeanReference(type = com.liferay.socialcoding.service.JIRAChangeItemLocalService.class)
	protected com.liferay.socialcoding.service.JIRAChangeItemLocalService jiraChangeItemLocalService;
	@BeanReference(type = JIRAChangeItemPersistence.class)
	protected JIRAChangeItemPersistence jiraChangeItemPersistence;
	@BeanReference(type = com.liferay.socialcoding.service.JIRAIssueLocalService.class)
	protected com.liferay.socialcoding.service.JIRAIssueLocalService jiraIssueLocalService;
	@BeanReference(type = JIRAIssuePersistence.class)
	protected JIRAIssuePersistence jiraIssuePersistence;
	@BeanReference(type = JIRAIssueFinder.class)
	protected JIRAIssueFinder jiraIssueFinder;
	@BeanReference(type = com.liferay.socialcoding.service.JIRAProjectLocalService.class)
	protected com.liferay.socialcoding.service.JIRAProjectLocalService jiraProjectLocalService;
	@BeanReference(type = JIRAProjectPersistence.class)
	protected JIRAProjectPersistence jiraProjectPersistence;
	@BeanReference(type = com.liferay.socialcoding.service.SVNRepositoryLocalService.class)
	protected com.liferay.socialcoding.service.SVNRepositoryLocalService svnRepositoryLocalService;
	@BeanReference(type = SVNRepositoryPersistence.class)
	protected SVNRepositoryPersistence svnRepositoryPersistence;
	@BeanReference(type = com.liferay.socialcoding.service.SVNRevisionLocalService.class)
	protected SVNRevisionLocalService svnRevisionLocalService;
	@BeanReference(type = SVNRevisionPersistence.class)
	protected SVNRevisionPersistence svnRevisionPersistence;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.service.ClassNameLocalService.class)
	protected com.liferay.portal.service.ClassNameLocalService classNameLocalService;
	@BeanReference(type = com.liferay.portal.service.ClassNameService.class)
	protected com.liferay.portal.service.ClassNameService classNameService;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = com.liferay.portal.service.ResourceLocalService.class)
	protected com.liferay.portal.service.ResourceLocalService resourceLocalService;
	@BeanReference(type = com.liferay.portal.service.UserLocalService.class)
	protected com.liferay.portal.service.UserLocalService userLocalService;
	@BeanReference(type = com.liferay.portal.service.UserService.class)
	protected com.liferay.portal.service.UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private ClassLoader _classLoader;
	private SVNRevisionLocalServiceClpInvoker _clpInvoker = new SVNRevisionLocalServiceClpInvoker();
}
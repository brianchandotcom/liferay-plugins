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

package com.liferay.knowledgebase.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link KBArticleLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see KBArticleLocalService
 * @generated
 */
public class KBArticleLocalServiceWrapper implements KBArticleLocalService,
	ServiceWrapper<KBArticleLocalService> {
	public KBArticleLocalServiceWrapper(
		KBArticleLocalService kbArticleLocalService) {
		_kbArticleLocalService = kbArticleLocalService;
	}

	/**
	* Adds the k b article to the database. Also notifies the appropriate model listeners.
	*
	* @param kbArticle the k b article
	* @return the k b article that was added
	*/
	@Override
	public com.liferay.knowledgebase.model.KBArticle addKBArticle(
		com.liferay.knowledgebase.model.KBArticle kbArticle) {
		return _kbArticleLocalService.addKBArticle(kbArticle);
	}

	/**
	* Creates a new k b article with the primary key. Does not add the k b article to the database.
	*
	* @param kbArticleId the primary key for the new k b article
	* @return the new k b article
	*/
	@Override
	public com.liferay.knowledgebase.model.KBArticle createKBArticle(
		long kbArticleId) {
		return _kbArticleLocalService.createKBArticle(kbArticleId);
	}

	/**
	* Deletes the k b article with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param kbArticleId the primary key of the k b article
	* @return the k b article that was removed
	* @throws PortalException if a k b article with the primary key could not be found
	*/
	@Override
	public com.liferay.knowledgebase.model.KBArticle deleteKBArticle(
		long kbArticleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbArticleLocalService.deleteKBArticle(kbArticleId);
	}

	/**
	* Deletes the k b article from the database. Also notifies the appropriate model listeners.
	*
	* @param kbArticle the k b article
	* @return the k b article that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.knowledgebase.model.KBArticle deleteKBArticle(
		com.liferay.knowledgebase.model.KBArticle kbArticle)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbArticleLocalService.deleteKBArticle(kbArticle);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _kbArticleLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _kbArticleLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.knowledgebase.model.impl.KBArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _kbArticleLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.knowledgebase.model.impl.KBArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _kbArticleLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _kbArticleLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _kbArticleLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.knowledgebase.model.KBArticle fetchKBArticle(
		long kbArticleId) {
		return _kbArticleLocalService.fetchKBArticle(kbArticleId);
	}

	/**
	* Returns the k b article with the matching UUID and company.
	*
	* @param uuid the k b article's UUID
	* @param companyId the primary key of the company
	* @return the matching k b article, or <code>null</code> if a matching k b article could not be found
	*/
	@Override
	public com.liferay.knowledgebase.model.KBArticle fetchKBArticleByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _kbArticleLocalService.fetchKBArticleByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns the k b article matching the UUID and group.
	*
	* @param uuid the k b article's UUID
	* @param groupId the primary key of the group
	* @return the matching k b article, or <code>null</code> if a matching k b article could not be found
	*/
	@Override
	public com.liferay.knowledgebase.model.KBArticle fetchKBArticleByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _kbArticleLocalService.fetchKBArticleByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns the k b article with the primary key.
	*
	* @param kbArticleId the primary key of the k b article
	* @return the k b article
	* @throws PortalException if a k b article with the primary key could not be found
	*/
	@Override
	public com.liferay.knowledgebase.model.KBArticle getKBArticle(
		long kbArticleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbArticleLocalService.getKBArticle(kbArticleId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _kbArticleLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portal.kernel.lar.PortletDataContext portletDataContext) {
		return _kbArticleLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbArticleLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbArticleLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the k b article with the matching UUID and company.
	*
	* @param uuid the k b article's UUID
	* @param companyId the primary key of the company
	* @return the matching k b article
	* @throws PortalException if a matching k b article could not be found
	*/
	@Override
	public com.liferay.knowledgebase.model.KBArticle getKBArticleByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbArticleLocalService.getKBArticleByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns the k b article matching the UUID and group.
	*
	* @param uuid the k b article's UUID
	* @param groupId the primary key of the group
	* @return the matching k b article
	* @throws PortalException if a matching k b article could not be found
	*/
	@Override
	public com.liferay.knowledgebase.model.KBArticle getKBArticleByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbArticleLocalService.getKBArticleByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the k b articles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.knowledgebase.model.impl.KBArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of k b articles
	* @param end the upper bound of the range of k b articles (not inclusive)
	* @return the range of k b articles
	*/
	@Override
	public java.util.List<com.liferay.knowledgebase.model.KBArticle> getKBArticles(
		int start, int end) {
		return _kbArticleLocalService.getKBArticles(start, end);
	}

	/**
	* Returns the number of k b articles.
	*
	* @return the number of k b articles
	*/
	@Override
	public int getKBArticlesCount() {
		return _kbArticleLocalService.getKBArticlesCount();
	}

	/**
	* Updates the k b article in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param kbArticle the k b article
	* @return the k b article that was updated
	*/
	@Override
	public com.liferay.knowledgebase.model.KBArticle updateKBArticle(
		com.liferay.knowledgebase.model.KBArticle kbArticle) {
		return _kbArticleLocalService.updateKBArticle(kbArticle);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _kbArticleLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_kbArticleLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _kbArticleLocalService.invokeMethod(name, parameterTypes,
			arguments);
	}

	@Override
	public void addAttachment(java.lang.String dirName,
		java.lang.String shortFileName, java.io.InputStream inputStream,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_kbArticleLocalService.addAttachment(dirName, shortFileName,
			inputStream, serviceContext);
	}

	@Override
	public com.liferay.knowledgebase.model.KBArticle addKBArticle(long userId,
		long parentResourcePrimKey, java.lang.String title,
		java.lang.String urlTitle, java.lang.String content,
		java.lang.String description, java.lang.String[] sections,
		java.lang.String dirName,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbArticleLocalService.addKBArticle(userId,
			parentResourcePrimKey, title, urlTitle, content, description,
			sections, dirName, serviceContext);
	}

	@Override
	public void addKBArticleResources(
		com.liferay.knowledgebase.model.KBArticle kbArticle,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_kbArticleLocalService.addKBArticleResources(kbArticle,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addKBArticleResources(
		com.liferay.knowledgebase.model.KBArticle kbArticle,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_kbArticleLocalService.addKBArticleResources(kbArticle,
			groupPermissions, guestPermissions);
	}

	@Override
	public void addKBArticleResources(long kbArticleId,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_kbArticleLocalService.addKBArticleResources(kbArticleId,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addKBArticleResources(long kbArticleId,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_kbArticleLocalService.addKBArticleResources(kbArticleId,
			groupPermissions, guestPermissions);
	}

	@Override
	public void addKBArticlesMarkdown(long userId, long groupId,
		java.lang.String fileName, java.io.InputStream inputStream,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			java.io.IOException {
		_kbArticleLocalService.addKBArticlesMarkdown(userId, groupId, fileName,
			inputStream, serviceContext);
	}

	@Override
	public void checkAttachments()
		throws com.liferay.portal.kernel.exception.PortalException {
		_kbArticleLocalService.checkAttachments();
	}

	@Override
	public void deleteAttachment(long companyId, java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		_kbArticleLocalService.deleteAttachment(companyId, fileName);
	}

	@Override
	public void deleteGroupKBArticles(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_kbArticleLocalService.deleteGroupKBArticles(groupId);
	}

	@Override
	public void deleteKBArticles(long[] resourcePrimKeys)
		throws com.liferay.portal.kernel.exception.PortalException {
		_kbArticleLocalService.deleteKBArticles(resourcePrimKeys);
	}

	@Override
	public com.liferay.knowledgebase.model.KBArticle fetchKBArticleByUrlTitle(
		long groupId, java.lang.String urlTitle) {
		return _kbArticleLocalService.fetchKBArticleByUrlTitle(groupId, urlTitle);
	}

	@Override
	public com.liferay.knowledgebase.model.KBArticle fetchLatestKBArticle(
		long resourcePrimKey, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbArticleLocalService.fetchLatestKBArticle(resourcePrimKey,
			status);
	}

	@Override
	public com.liferay.knowledgebase.model.KBArticle fetchLatestKBArticleByUrlTitle(
		long groupId, java.lang.String urlTitle, int status) {
		return _kbArticleLocalService.fetchLatestKBArticleByUrlTitle(groupId,
			urlTitle, status);
	}

	@Override
	public java.util.List<com.liferay.knowledgebase.model.KBArticle> getAllDescendantKBArticles(
		long resourcePrimKey, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _kbArticleLocalService.getAllDescendantKBArticles(resourcePrimKey,
			status, orderByComparator);
	}

	@Override
	public java.io.File getAttachment(long companyId, java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbArticleLocalService.getAttachment(companyId, fileName);
	}

	@Override
	public java.util.List<com.liferay.knowledgebase.model.KBArticle> getCompanyKBArticles(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _kbArticleLocalService.getCompanyKBArticles(companyId, status,
			start, end, orderByComparator);
	}

	@Override
	public int getCompanyKBArticlesCount(long companyId, int status) {
		return _kbArticleLocalService.getCompanyKBArticlesCount(companyId,
			status);
	}

	@Override
	public java.util.List<com.liferay.knowledgebase.model.KBArticle> getGroupKBArticles(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _kbArticleLocalService.getGroupKBArticles(groupId, status,
			start, end, orderByComparator);
	}

	@Override
	public int getGroupKBArticlesCount(long groupId, int status) {
		return _kbArticleLocalService.getGroupKBArticlesCount(groupId, status);
	}

	@Override
	public com.liferay.knowledgebase.model.KBArticle getKBArticle(
		long resourcePrimKey, int version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbArticleLocalService.getKBArticle(resourcePrimKey, version);
	}

	@Override
	public java.util.List<com.liferay.knowledgebase.model.KBArticle> getKBArticleAndAllDescendantKBArticles(
		long resourcePrimKey, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _kbArticleLocalService.getKBArticleAndAllDescendantKBArticles(resourcePrimKey,
			status, orderByComparator);
	}

	/**
	* @deprecated As of 7.0.0, replaced by
	{@link #getKBArticleAndAllDescendantKBArticles(long, int,
	com.liferay.portal.kernel.util.OrderByComparator)}
	*/
	@Override
	public java.util.List<com.liferay.knowledgebase.model.KBArticle> getKBArticleAndAllDescendants(
		long resourcePrimKey, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _kbArticleLocalService.getKBArticleAndAllDescendants(resourcePrimKey,
			status, orderByComparator);
	}

	@Override
	public com.liferay.knowledgebase.model.KBArticle getKBArticleByUrlTitle(
		long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbArticleLocalService.getKBArticleByUrlTitle(groupId, urlTitle);
	}

	@Override
	public java.util.List<com.liferay.knowledgebase.model.KBArticle> getKBArticles(
		long groupId, long parentResourcePrimKey, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _kbArticleLocalService.getKBArticles(groupId,
			parentResourcePrimKey, status, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.knowledgebase.model.KBArticle> getKBArticles(
		long[] resourcePrimKeys, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _kbArticleLocalService.getKBArticles(resourcePrimKeys, status,
			orderByComparator);
	}

	@Override
	public int getKBArticlesCount(long groupId, long parentResourcePrimKey,
		int status) {
		return _kbArticleLocalService.getKBArticlesCount(groupId,
			parentResourcePrimKey, status);
	}

	@Override
	public java.util.List<com.liferay.knowledgebase.model.KBArticle> getKBArticleVersions(
		long resourcePrimKey, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _kbArticleLocalService.getKBArticleVersions(resourcePrimKey,
			status, start, end, orderByComparator);
	}

	@Override
	public int getKBArticleVersionsCount(long resourcePrimKey, int status) {
		return _kbArticleLocalService.getKBArticleVersionsCount(resourcePrimKey,
			status);
	}

	@Override
	public com.liferay.knowledgebase.model.KBArticle getLatestKBArticle(
		long resourcePrimKey, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbArticleLocalService.getLatestKBArticle(resourcePrimKey, status);
	}

	@Override
	public com.liferay.knowledgebase.model.KBArticle getLatestKBArticleByUrlTitle(
		long groupId, java.lang.String urlTitle, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbArticleLocalService.getLatestKBArticleByUrlTitle(groupId,
			urlTitle, status);
	}

	@Override
	public java.util.List<com.liferay.knowledgebase.model.KBArticle> getSectionsKBArticles(
		long groupId, java.lang.String[] sections, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _kbArticleLocalService.getSectionsKBArticles(groupId, sections,
			status, start, end, orderByComparator);
	}

	@Override
	public int getSectionsKBArticlesCount(long groupId,
		java.lang.String[] sections, int status) {
		return _kbArticleLocalService.getSectionsKBArticlesCount(groupId,
			sections, status);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getKBArticles(long, long,
	int, int, int,
	com.liferay.portal.kernel.util.OrderByComparator)}
	*/
	@Override
	public java.util.List<com.liferay.knowledgebase.model.KBArticle> getSiblingKBArticles(
		long groupId, long parentResourcePrimKey, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _kbArticleLocalService.getSiblingKBArticles(groupId,
			parentResourcePrimKey, status, start, end, orderByComparator);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getKBArticlesCount(long,
	long, int)}
	*/
	@Override
	public int getSiblingKBArticlesCount(long groupId,
		long parentResourcePrimKey, int status) {
		return _kbArticleLocalService.getSiblingKBArticlesCount(groupId,
			parentResourcePrimKey, status);
	}

	@Override
	public void moveKBArticle(long userId, long resourcePrimKey,
		long parentResourcePrimKey, double priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		_kbArticleLocalService.moveKBArticle(userId, resourcePrimKey,
			parentResourcePrimKey, priority);
	}

	@Override
	public java.util.List<com.liferay.knowledgebase.model.KBArticle> search(
		long groupId, java.lang.String title, java.lang.String content,
		int status, java.util.Date startDate, java.util.Date endDate,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _kbArticleLocalService.search(groupId, title, content, status,
			startDate, endDate, andOperator, start, end, orderByComparator);
	}

	@Override
	public void subscribeGroupKBArticles(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_kbArticleLocalService.subscribeGroupKBArticles(userId, groupId);
	}

	@Override
	public void subscribeKBArticle(long userId, long groupId,
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		_kbArticleLocalService.subscribeKBArticle(userId, groupId,
			resourcePrimKey);
	}

	@Override
	public void unsubscribeGroupKBArticles(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_kbArticleLocalService.unsubscribeGroupKBArticles(userId, groupId);
	}

	@Override
	public void unsubscribeKBArticle(long userId, long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		_kbArticleLocalService.unsubscribeKBArticle(userId, resourcePrimKey);
	}

	@Override
	public java.lang.String updateAttachments(long resourcePrimKey,
		java.lang.String dirName,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbArticleLocalService.updateAttachments(resourcePrimKey,
			dirName, serviceContext);
	}

	@Override
	public com.liferay.knowledgebase.model.KBArticle updateKBArticle(
		long userId, long resourcePrimKey, java.lang.String title,
		java.lang.String content, java.lang.String description,
		java.lang.String[] sections, java.lang.String dirName,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbArticleLocalService.updateKBArticle(userId, resourcePrimKey,
			title, content, description, sections, dirName, serviceContext);
	}

	@Override
	public void updateKBArticleAsset(long userId,
		com.liferay.knowledgebase.model.KBArticle kbArticle,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException {
		_kbArticleLocalService.updateKBArticleAsset(userId, kbArticle,
			assetCategoryIds, assetTagNames);
	}

	@Override
	public void updateKBArticleResources(
		com.liferay.knowledgebase.model.KBArticle kbArticle,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_kbArticleLocalService.updateKBArticleResources(kbArticle,
			groupPermissions, guestPermissions);
	}

	@Override
	public void updateKBArticlesPriorities(
		java.util.Map<java.lang.Long, java.lang.Double> resourcePrimKeyToPriorityMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		_kbArticleLocalService.updateKBArticlesPriorities(resourcePrimKeyToPriorityMap);
	}

	@Override
	public void updatePriority(long resourcePrimKey, double priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		_kbArticleLocalService.updatePriority(resourcePrimKey, priority);
	}

	@Override
	public com.liferay.knowledgebase.model.KBArticle updateStatus(long userId,
		long resourcePrimKey, int status,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kbArticleLocalService.updateStatus(userId, resourcePrimKey,
			status, serviceContext);
	}

	@Override
	public void updateViewCount(long userId, long resourcePrimKey, int viewCount)
		throws com.liferay.portal.kernel.exception.PortalException {
		_kbArticleLocalService.updateViewCount(userId, resourcePrimKey,
			viewCount);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public KBArticleLocalService getWrappedKBArticleLocalService() {
		return _kbArticleLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedKBArticleLocalService(
		KBArticleLocalService kbArticleLocalService) {
		_kbArticleLocalService = kbArticleLocalService;
	}

	@Override
	public KBArticleLocalService getWrappedService() {
		return _kbArticleLocalService;
	}

	@Override
	public void setWrappedService(KBArticleLocalService kbArticleLocalService) {
		_kbArticleLocalService = kbArticleLocalService;
	}

	private KBArticleLocalService _kbArticleLocalService;
}
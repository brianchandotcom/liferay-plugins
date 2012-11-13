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

package com.liferay.samplejsonws.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.samplejsonws.NoSuchSampleAssetException;
import com.liferay.samplejsonws.model.SampleAsset;
import com.liferay.samplejsonws.model.impl.SampleAssetImpl;
import com.liferay.samplejsonws.model.impl.SampleAssetModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the sample asset service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SampleAssetPersistence
 * @see SampleAssetUtil
 * @generated
 */
public class SampleAssetPersistenceImpl extends BasePersistenceImpl<SampleAsset>
	implements SampleAssetPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SampleAssetUtil} to access the sample asset persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SampleAssetImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SampleAssetModelImpl.ENTITY_CACHE_ENABLED,
			SampleAssetModelImpl.FINDER_CACHE_ENABLED, SampleAssetImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SampleAssetModelImpl.ENTITY_CACHE_ENABLED,
			SampleAssetModelImpl.FINDER_CACHE_ENABLED, SampleAssetImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SampleAssetModelImpl.ENTITY_CACHE_ENABLED,
			SampleAssetModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the sample asset in the entity cache if it is enabled.
	 *
	 * @param sampleAsset the sample asset
	 */
	public void cacheResult(SampleAsset sampleAsset) {
		EntityCacheUtil.putResult(SampleAssetModelImpl.ENTITY_CACHE_ENABLED,
			SampleAssetImpl.class, sampleAsset.getPrimaryKey(), sampleAsset);

		sampleAsset.resetOriginalValues();
	}

	/**
	 * Caches the sample assets in the entity cache if it is enabled.
	 *
	 * @param sampleAssets the sample assets
	 */
	public void cacheResult(List<SampleAsset> sampleAssets) {
		for (SampleAsset sampleAsset : sampleAssets) {
			if (EntityCacheUtil.getResult(
						SampleAssetModelImpl.ENTITY_CACHE_ENABLED,
						SampleAssetImpl.class, sampleAsset.getPrimaryKey()) == null) {
				cacheResult(sampleAsset);
			}
			else {
				sampleAsset.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all sample assets.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(SampleAssetImpl.class.getName());
		}

		EntityCacheUtil.clearCache(SampleAssetImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the sample asset.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SampleAsset sampleAsset) {
		EntityCacheUtil.removeResult(SampleAssetModelImpl.ENTITY_CACHE_ENABLED,
			SampleAssetImpl.class, sampleAsset.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<SampleAsset> sampleAssets) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SampleAsset sampleAsset : sampleAssets) {
			EntityCacheUtil.removeResult(SampleAssetModelImpl.ENTITY_CACHE_ENABLED,
				SampleAssetImpl.class, sampleAsset.getPrimaryKey());
		}
	}

	/**
	 * Creates a new sample asset with the primary key. Does not add the sample asset to the database.
	 *
	 * @param assetId the primary key for the new sample asset
	 * @return the new sample asset
	 */
	public SampleAsset create(long assetId) {
		SampleAsset sampleAsset = new SampleAssetImpl();

		sampleAsset.setNew(true);
		sampleAsset.setPrimaryKey(assetId);

		return sampleAsset;
	}

	/**
	 * Removes the sample asset with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetId the primary key of the sample asset
	 * @return the sample asset that was removed
	 * @throws com.liferay.samplejsonws.NoSuchSampleAssetException if a sample asset with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SampleAsset remove(long assetId)
		throws NoSuchSampleAssetException, SystemException {
		return remove(Long.valueOf(assetId));
	}

	/**
	 * Removes the sample asset with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the sample asset
	 * @return the sample asset that was removed
	 * @throws com.liferay.samplejsonws.NoSuchSampleAssetException if a sample asset with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SampleAsset remove(Serializable primaryKey)
		throws NoSuchSampleAssetException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SampleAsset sampleAsset = (SampleAsset)session.get(SampleAssetImpl.class,
					primaryKey);

			if (sampleAsset == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSampleAssetException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(sampleAsset);
		}
		catch (NoSuchSampleAssetException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected SampleAsset removeImpl(SampleAsset sampleAsset)
		throws SystemException {
		sampleAsset = toUnwrappedModel(sampleAsset);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, sampleAsset);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(sampleAsset);

		return sampleAsset;
	}

	@Override
	public SampleAsset updateImpl(
		com.liferay.samplejsonws.model.SampleAsset sampleAsset, boolean merge)
		throws SystemException {
		sampleAsset = toUnwrappedModel(sampleAsset);

		boolean isNew = sampleAsset.isNew();

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, sampleAsset, merge);

			sampleAsset.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(SampleAssetModelImpl.ENTITY_CACHE_ENABLED,
			SampleAssetImpl.class, sampleAsset.getPrimaryKey(), sampleAsset);

		return sampleAsset;
	}

	protected SampleAsset toUnwrappedModel(SampleAsset sampleAsset) {
		if (sampleAsset instanceof SampleAssetImpl) {
			return sampleAsset;
		}

		SampleAssetImpl sampleAssetImpl = new SampleAssetImpl();

		sampleAssetImpl.setNew(sampleAsset.isNew());
		sampleAssetImpl.setPrimaryKey(sampleAsset.getPrimaryKey());

		sampleAssetImpl.setAssetId(sampleAsset.getAssetId());
		sampleAssetImpl.setUserId(sampleAsset.getUserId());
		sampleAssetImpl.setName(sampleAsset.getName());
		sampleAssetImpl.setDate(sampleAsset.getDate());

		return sampleAssetImpl;
	}

	/**
	 * Returns the sample asset with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the sample asset
	 * @return the sample asset
	 * @throws com.liferay.portal.NoSuchModelException if a sample asset with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SampleAsset findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the sample asset with the primary key or throws a {@link com.liferay.samplejsonws.NoSuchSampleAssetException} if it could not be found.
	 *
	 * @param assetId the primary key of the sample asset
	 * @return the sample asset
	 * @throws com.liferay.samplejsonws.NoSuchSampleAssetException if a sample asset with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SampleAsset findByPrimaryKey(long assetId)
		throws NoSuchSampleAssetException, SystemException {
		SampleAsset sampleAsset = fetchByPrimaryKey(assetId);

		if (sampleAsset == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + assetId);
			}

			throw new NoSuchSampleAssetException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				assetId);
		}

		return sampleAsset;
	}

	/**
	 * Returns the sample asset with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the sample asset
	 * @return the sample asset, or <code>null</code> if a sample asset with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SampleAsset fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the sample asset with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetId the primary key of the sample asset
	 * @return the sample asset, or <code>null</code> if a sample asset with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SampleAsset fetchByPrimaryKey(long assetId)
		throws SystemException {
		SampleAsset sampleAsset = (SampleAsset)EntityCacheUtil.getResult(SampleAssetModelImpl.ENTITY_CACHE_ENABLED,
				SampleAssetImpl.class, assetId);

		if (sampleAsset == _nullSampleAsset) {
			return null;
		}

		if (sampleAsset == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				sampleAsset = (SampleAsset)session.get(SampleAssetImpl.class,
						Long.valueOf(assetId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (sampleAsset != null) {
					cacheResult(sampleAsset);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(SampleAssetModelImpl.ENTITY_CACHE_ENABLED,
						SampleAssetImpl.class, assetId, _nullSampleAsset);
				}

				closeSession(session);
			}
		}

		return sampleAsset;
	}

	/**
	 * Returns all the sample assets.
	 *
	 * @return the sample assets
	 * @throws SystemException if a system exception occurred
	 */
	public List<SampleAsset> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sample assets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of sample assets
	 * @param end the upper bound of the range of sample assets (not inclusive)
	 * @return the range of sample assets
	 * @throws SystemException if a system exception occurred
	 */
	public List<SampleAsset> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the sample assets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of sample assets
	 * @param end the upper bound of the range of sample assets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of sample assets
	 * @throws SystemException if a system exception occurred
	 */
	public List<SampleAsset> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<SampleAsset> list = (List<SampleAsset>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SAMPLEASSET);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SAMPLEASSET;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SampleAsset>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SampleAsset>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the sample assets from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (SampleAsset sampleAsset : findAll()) {
			remove(sampleAsset);
		}
	}

	/**
	 * Returns the number of sample assets.
	 *
	 * @return the number of sample assets
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SAMPLEASSET);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the sample asset persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.samplejsonws.model.SampleAsset")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SampleAsset>> listenersList = new ArrayList<ModelListener<SampleAsset>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SampleAsset>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(SampleAssetImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = SampleAssetPersistence.class)
	protected SampleAssetPersistence sampleAssetPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_SAMPLEASSET = "SELECT sampleAsset FROM SampleAsset sampleAsset";
	private static final String _SQL_COUNT_SAMPLEASSET = "SELECT COUNT(sampleAsset) FROM SampleAsset sampleAsset";
	private static final String _ORDER_BY_ENTITY_ALIAS = "sampleAsset.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SampleAsset exists with the primary key ";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(SampleAssetPersistenceImpl.class);
	private static SampleAsset _nullSampleAsset = new SampleAssetImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<SampleAsset> toCacheModel() {
				return _nullSampleAssetCacheModel;
			}
		};

	private static CacheModel<SampleAsset> _nullSampleAssetCacheModel = new CacheModel<SampleAsset>() {
			public SampleAsset toEntityModel() {
				return _nullSampleAsset;
			}
		};
}
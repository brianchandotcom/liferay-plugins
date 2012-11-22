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

package com.liferay.twitter.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.twitter.NoSuchFeedException;
import com.liferay.twitter.model.Feed;
import com.liferay.twitter.model.impl.FeedImpl;
import com.liferay.twitter.model.impl.FeedModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the feed service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FeedPersistence
 * @see FeedUtil
 * @generated
 */
public class FeedPersistenceImpl extends BasePersistenceImpl<Feed>
	implements FeedPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link FeedUtil} to access the feed persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = FeedImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(FeedModelImpl.ENTITY_CACHE_ENABLED,
			FeedModelImpl.FINDER_CACHE_ENABLED, FeedImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(FeedModelImpl.ENTITY_CACHE_ENABLED,
			FeedModelImpl.FINDER_CACHE_ENABLED, FeedImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(FeedModelImpl.ENTITY_CACHE_ENABLED,
			FeedModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_TWUI = new FinderPath(FeedModelImpl.ENTITY_CACHE_ENABLED,
			FeedModelImpl.FINDER_CACHE_ENABLED, FeedImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_TWUI",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_TWUI =
		new FinderPath(FeedModelImpl.ENTITY_CACHE_ENABLED,
			FeedModelImpl.FINDER_CACHE_ENABLED, FeedImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_TWUI",
			new String[] { Long.class.getName(), Long.class.getName() },
			FeedModelImpl.COMPANYID_COLUMN_BITMASK |
			FeedModelImpl.TWITTERUSERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_TWUI = new FinderPath(FeedModelImpl.ENTITY_CACHE_ENABLED,
			FeedModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_TWUI",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns an ordered range of all the feeds where companyId = &#63; and twitterUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. When orderByComparator is specified, the query will include the given ORDER BY logic. When orderByComparator is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), the query will include the default ORDER BY logic from FeedModelImpl. If both orderByComparator and pagination are absent, for performance reason, the query will not have a ORDER BY clause, on returning the result set will be sorted in portal side by PK ASC order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param twitterUserId the twitter user ID
	 * @param start the lower bound of the range of feeds
	 * @param end the upper bound of the range of feeds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching feeds
	 * @throws SystemException if a system exception occurred
	 */
	protected List<Feed> findByC_TWUI(long companyId, long twitterUserId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_TWUI;
			finderArgs = new Object[] { companyId, twitterUserId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_TWUI;
			finderArgs = new Object[] {
					companyId, twitterUserId,
					
					start, end, orderByComparator
				};
		}

		List<Feed> list = (List<Feed>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Feed feed : list) {
				if ((companyId != feed.getCompanyId()) ||
						(twitterUserId != feed.getTwitterUserId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_FEED_WHERE);

			query.append(_FINDER_COLUMN_C_TWUI_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_TWUI_TWITTERUSERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else if (pagination) {
				query.append(FeedModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(twitterUserId);

				if (!pagination) {
					list = (List<Feed>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList(list);
				}
				else {
					list = (List<Feed>)QueryUtil.list(q, getDialect(), start,
							end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first feed in the default ordered set defined by {@link FeedModelImpl#ORDER_BY_JPQL} where companyId = &#63; and twitterUserId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param twitterUserId the twitter user ID
	 * @return the first matching feed
	 * @throws com.liferay.twitter.NoSuchFeedException if a matching feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Feed findByC_TWUI_First(long companyId, long twitterUserId)
		throws NoSuchFeedException, SystemException {
		return findByC_TWUI_First(companyId, twitterUserId, null);
	}

	/**
	 * Returns the first feed in the ordered set where companyId = &#63; and twitterUserId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param twitterUserId the twitter user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching feed
	 * @throws com.liferay.twitter.NoSuchFeedException if a matching feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Feed findByC_TWUI_First(long companyId, long twitterUserId,
		OrderByComparator orderByComparator)
		throws NoSuchFeedException, SystemException {
		Feed feed = fetchByC_TWUI_First(companyId, twitterUserId,
				orderByComparator);

		if (feed != null) {
			return feed;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", twitterUserId=");
		msg.append(twitterUserId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFeedException(msg.toString());
	}

	/**
	 * Returns the first feed in the default ordered set defined by {@link FeedModelImpl#ORDER_BY_JPQL} where companyId = &#63; and twitterUserId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param twitterUserId the twitter user ID
	 * @return the first matching feed, or <code>null</code> if a matching feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Feed fetchByC_TWUI_First(long companyId, long twitterUserId)
		throws SystemException {
		return fetchByC_TWUI_First(companyId, twitterUserId, null);
	}

	/**
	 * Returns the first feed in the ordered set where companyId = &#63; and twitterUserId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param twitterUserId the twitter user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching feed, or <code>null</code> if a matching feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Feed fetchByC_TWUI_First(long companyId, long twitterUserId,
		OrderByComparator orderByComparator) throws SystemException {
		List<Feed> list = findByC_TWUI(companyId, twitterUserId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last feed in the default ordered set defined by {@link FeedModelImpl#ORDER_BY_JPQL} where companyId = &#63; and twitterUserId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param twitterUserId the twitter user ID
	 * @return the last matching feed
	 * @throws com.liferay.twitter.NoSuchFeedException if a matching feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Feed findByC_TWUI_Last(long companyId, long twitterUserId)
		throws NoSuchFeedException, SystemException {
		return findByC_TWUI_Last(companyId, twitterUserId, null);
	}

	/**
	 * Returns the last feed in the ordered set where companyId = &#63; and twitterUserId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param twitterUserId the twitter user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching feed
	 * @throws com.liferay.twitter.NoSuchFeedException if a matching feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Feed findByC_TWUI_Last(long companyId, long twitterUserId,
		OrderByComparator orderByComparator)
		throws NoSuchFeedException, SystemException {
		Feed feed = fetchByC_TWUI_Last(companyId, twitterUserId,
				orderByComparator);

		if (feed != null) {
			return feed;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", twitterUserId=");
		msg.append(twitterUserId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFeedException(msg.toString());
	}

	/**
	 * Returns the last feed in the default ordered set defined by {@link FeedModelImpl#ORDER_BY_JPQL} where companyId = &#63; and twitterUserId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param twitterUserId the twitter user ID
	 * @return the last matching feed, or <code>null</code> if a matching feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Feed fetchByC_TWUI_Last(long companyId, long twitterUserId)
		throws SystemException {
		return fetchByC_TWUI_Last(companyId, twitterUserId, null);
	}

	/**
	 * Returns the last feed in the ordered set where companyId = &#63; and twitterUserId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param twitterUserId the twitter user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching feed, or <code>null</code> if a matching feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Feed fetchByC_TWUI_Last(long companyId, long twitterUserId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByC_TWUI(companyId, twitterUserId);

		List<Feed> list = findByC_TWUI(companyId, twitterUserId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the feeds where companyId = &#63; and twitterUserId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param twitterUserId the twitter user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_TWUI(long companyId, long twitterUserId)
		throws SystemException {
		for (Feed feed : findByC_TWUI(companyId, twitterUserId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(feed);
		}
	}

	/**
	 * Returns the number of feeds where companyId = &#63; and twitterUserId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param twitterUserId the twitter user ID
	 * @return the number of matching feeds
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_TWUI(long companyId, long twitterUserId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_TWUI;

		Object[] finderArgs = new Object[] { companyId, twitterUserId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_FEED_WHERE);

			query.append(_FINDER_COLUMN_C_TWUI_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_TWUI_TWITTERUSERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(twitterUserId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_TWUI_COMPANYID_2 = "feed.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_TWUI_TWITTERUSERID_2 = "feed.twitterUserId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_TSN = new FinderPath(FeedModelImpl.ENTITY_CACHE_ENABLED,
			FeedModelImpl.FINDER_CACHE_ENABLED, FeedImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_TSN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_TSN = new FinderPath(FeedModelImpl.ENTITY_CACHE_ENABLED,
			FeedModelImpl.FINDER_CACHE_ENABLED, FeedImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_TSN",
			new String[] { Long.class.getName(), String.class.getName() },
			FeedModelImpl.COMPANYID_COLUMN_BITMASK |
			FeedModelImpl.TWITTERSCREENNAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_TSN = new FinderPath(FeedModelImpl.ENTITY_CACHE_ENABLED,
			FeedModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_TSN",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns an ordered range of all the feeds where companyId = &#63; and twitterScreenName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. When orderByComparator is specified, the query will include the given ORDER BY logic. When orderByComparator is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), the query will include the default ORDER BY logic from FeedModelImpl. If both orderByComparator and pagination are absent, for performance reason, the query will not have a ORDER BY clause, on returning the result set will be sorted in portal side by PK ASC order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param twitterScreenName the twitter screen name
	 * @param start the lower bound of the range of feeds
	 * @param end the upper bound of the range of feeds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching feeds
	 * @throws SystemException if a system exception occurred
	 */
	protected List<Feed> findByC_TSN(long companyId, String twitterScreenName,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_TSN;
			finderArgs = new Object[] { companyId, twitterScreenName };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_TSN;
			finderArgs = new Object[] {
					companyId, twitterScreenName,
					
					start, end, orderByComparator
				};
		}

		List<Feed> list = (List<Feed>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Feed feed : list) {
				if ((companyId != feed.getCompanyId()) ||
						!Validator.equals(twitterScreenName,
							feed.getTwitterScreenName())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_FEED_WHERE);

			query.append(_FINDER_COLUMN_C_TSN_COMPANYID_2);

			if (twitterScreenName == null) {
				query.append(_FINDER_COLUMN_C_TSN_TWITTERSCREENNAME_1);
			}
			else {
				if (twitterScreenName.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_TSN_TWITTERSCREENNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_TSN_TWITTERSCREENNAME_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else if (pagination) {
				query.append(FeedModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (twitterScreenName != null) {
					qPos.add(twitterScreenName);
				}

				if (!pagination) {
					list = (List<Feed>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList(list);
				}
				else {
					list = (List<Feed>)QueryUtil.list(q, getDialect(), start,
							end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first feed in the default ordered set defined by {@link FeedModelImpl#ORDER_BY_JPQL} where companyId = &#63; and twitterScreenName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param twitterScreenName the twitter screen name
	 * @return the first matching feed
	 * @throws com.liferay.twitter.NoSuchFeedException if a matching feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Feed findByC_TSN_First(long companyId, String twitterScreenName)
		throws NoSuchFeedException, SystemException {
		return findByC_TSN_First(companyId, twitterScreenName, null);
	}

	/**
	 * Returns the first feed in the ordered set where companyId = &#63; and twitterScreenName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param twitterScreenName the twitter screen name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching feed
	 * @throws com.liferay.twitter.NoSuchFeedException if a matching feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Feed findByC_TSN_First(long companyId, String twitterScreenName,
		OrderByComparator orderByComparator)
		throws NoSuchFeedException, SystemException {
		Feed feed = fetchByC_TSN_First(companyId, twitterScreenName,
				orderByComparator);

		if (feed != null) {
			return feed;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", twitterScreenName=");
		msg.append(twitterScreenName);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFeedException(msg.toString());
	}

	/**
	 * Returns the first feed in the default ordered set defined by {@link FeedModelImpl#ORDER_BY_JPQL} where companyId = &#63; and twitterScreenName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param twitterScreenName the twitter screen name
	 * @return the first matching feed, or <code>null</code> if a matching feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Feed fetchByC_TSN_First(long companyId, String twitterScreenName)
		throws SystemException {
		return fetchByC_TSN_First(companyId, twitterScreenName, null);
	}

	/**
	 * Returns the first feed in the ordered set where companyId = &#63; and twitterScreenName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param twitterScreenName the twitter screen name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching feed, or <code>null</code> if a matching feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Feed fetchByC_TSN_First(long companyId, String twitterScreenName,
		OrderByComparator orderByComparator) throws SystemException {
		List<Feed> list = findByC_TSN(companyId, twitterScreenName, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last feed in the default ordered set defined by {@link FeedModelImpl#ORDER_BY_JPQL} where companyId = &#63; and twitterScreenName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param twitterScreenName the twitter screen name
	 * @return the last matching feed
	 * @throws com.liferay.twitter.NoSuchFeedException if a matching feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Feed findByC_TSN_Last(long companyId, String twitterScreenName)
		throws NoSuchFeedException, SystemException {
		return findByC_TSN_Last(companyId, twitterScreenName, null);
	}

	/**
	 * Returns the last feed in the ordered set where companyId = &#63; and twitterScreenName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param twitterScreenName the twitter screen name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching feed
	 * @throws com.liferay.twitter.NoSuchFeedException if a matching feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Feed findByC_TSN_Last(long companyId, String twitterScreenName,
		OrderByComparator orderByComparator)
		throws NoSuchFeedException, SystemException {
		Feed feed = fetchByC_TSN_Last(companyId, twitterScreenName,
				orderByComparator);

		if (feed != null) {
			return feed;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", twitterScreenName=");
		msg.append(twitterScreenName);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFeedException(msg.toString());
	}

	/**
	 * Returns the last feed in the default ordered set defined by {@link FeedModelImpl#ORDER_BY_JPQL} where companyId = &#63; and twitterScreenName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param twitterScreenName the twitter screen name
	 * @return the last matching feed, or <code>null</code> if a matching feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Feed fetchByC_TSN_Last(long companyId, String twitterScreenName)
		throws SystemException {
		return fetchByC_TSN_Last(companyId, twitterScreenName, null);
	}

	/**
	 * Returns the last feed in the ordered set where companyId = &#63; and twitterScreenName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param twitterScreenName the twitter screen name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching feed, or <code>null</code> if a matching feed could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Feed fetchByC_TSN_Last(long companyId, String twitterScreenName,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByC_TSN(companyId, twitterScreenName);

		List<Feed> list = findByC_TSN(companyId, twitterScreenName, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the feeds where companyId = &#63; and twitterScreenName = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param twitterScreenName the twitter screen name
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_TSN(long companyId, String twitterScreenName)
		throws SystemException {
		for (Feed feed : findByC_TSN(companyId, twitterScreenName,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(feed);
		}
	}

	/**
	 * Returns the number of feeds where companyId = &#63; and twitterScreenName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param twitterScreenName the twitter screen name
	 * @return the number of matching feeds
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_TSN(long companyId, String twitterScreenName)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_TSN;

		Object[] finderArgs = new Object[] { companyId, twitterScreenName };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_FEED_WHERE);

			query.append(_FINDER_COLUMN_C_TSN_COMPANYID_2);

			if (twitterScreenName == null) {
				query.append(_FINDER_COLUMN_C_TSN_TWITTERSCREENNAME_1);
			}
			else {
				if (twitterScreenName.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_TSN_TWITTERSCREENNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_TSN_TWITTERSCREENNAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (twitterScreenName != null) {
					qPos.add(twitterScreenName);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_TSN_COMPANYID_2 = "feed.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_TSN_TWITTERSCREENNAME_1 = "feed.twitterScreenName IS NULL";
	private static final String _FINDER_COLUMN_C_TSN_TWITTERSCREENNAME_2 = "feed.twitterScreenName = ?";
	private static final String _FINDER_COLUMN_C_TSN_TWITTERSCREENNAME_3 = "(feed.twitterScreenName IS NULL OR feed.twitterScreenName = ?)";

	/**
	 * Caches the feed in the entity cache if it is enabled.
	 *
	 * @param feed the feed
	 */
	public void cacheResult(Feed feed) {
		EntityCacheUtil.putResult(FeedModelImpl.ENTITY_CACHE_ENABLED,
			FeedImpl.class, feed.getPrimaryKey(), feed);

		feed.resetOriginalValues();
	}

	/**
	 * Caches the feeds in the entity cache if it is enabled.
	 *
	 * @param feeds the feeds
	 */
	public void cacheResult(List<Feed> feeds) {
		for (Feed feed : feeds) {
			if (EntityCacheUtil.getResult(FeedModelImpl.ENTITY_CACHE_ENABLED,
						FeedImpl.class, feed.getPrimaryKey()) == null) {
				cacheResult(feed);
			}
			else {
				feed.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all feeds.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(FeedImpl.class.getName());
		}

		EntityCacheUtil.clearCache(FeedImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the feed.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Feed feed) {
		EntityCacheUtil.removeResult(FeedModelImpl.ENTITY_CACHE_ENABLED,
			FeedImpl.class, feed.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<Feed> feeds) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Feed feed : feeds) {
			EntityCacheUtil.removeResult(FeedModelImpl.ENTITY_CACHE_ENABLED,
				FeedImpl.class, feed.getPrimaryKey());
		}
	}

	/**
	 * Creates a new feed with the primary key. Does not add the feed to the database.
	 *
	 * @param feedId the primary key for the new feed
	 * @return the new feed
	 */
	public Feed create(long feedId) {
		Feed feed = new FeedImpl();

		feed.setNew(true);
		feed.setPrimaryKey(feedId);

		return feed;
	}

	/**
	 * Removes the feed with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param feedId the primary key of the feed
	 * @return the feed that was removed
	 * @throws com.liferay.twitter.NoSuchFeedException if a feed with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Feed remove(long feedId) throws NoSuchFeedException, SystemException {
		return remove(Long.valueOf(feedId));
	}

	/**
	 * Removes the feed with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the feed
	 * @return the feed that was removed
	 * @throws com.liferay.twitter.NoSuchFeedException if a feed with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Feed remove(Serializable primaryKey)
		throws NoSuchFeedException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Feed feed = (Feed)session.get(FeedImpl.class, primaryKey);

			if (feed == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFeedException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(feed);
		}
		catch (NoSuchFeedException nsee) {
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
	protected Feed removeImpl(Feed feed) throws SystemException {
		feed = toUnwrappedModel(feed);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(feed)) {
				feed = (Feed)session.get(FeedImpl.class, feed.getPrimaryKeyObj());
			}

			if (feed != null) {
				session.delete(feed);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (feed != null) {
			clearCache(feed);
		}

		return feed;
	}

	@Override
	public Feed updateImpl(com.liferay.twitter.model.Feed feed)
		throws SystemException {
		feed = toUnwrappedModel(feed);

		boolean isNew = feed.isNew();

		Session session = null;

		try {
			session = openSession();

			if (feed.isNew()) {
				session.save(feed);

				feed.setNew(false);
			}
			else {
				session.merge(feed);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !FeedModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(FeedModelImpl.ENTITY_CACHE_ENABLED,
			FeedImpl.class, feed.getPrimaryKey(), feed);

		return feed;
	}

	protected Feed toUnwrappedModel(Feed feed) {
		if (feed instanceof FeedImpl) {
			return feed;
		}

		FeedImpl feedImpl = new FeedImpl();

		feedImpl.setNew(feed.isNew());
		feedImpl.setPrimaryKey(feed.getPrimaryKey());

		feedImpl.setFeedId(feed.getFeedId());
		feedImpl.setCompanyId(feed.getCompanyId());
		feedImpl.setUserId(feed.getUserId());
		feedImpl.setUserName(feed.getUserName());
		feedImpl.setCreateDate(feed.getCreateDate());
		feedImpl.setModifiedDate(feed.getModifiedDate());
		feedImpl.setTwitterUserId(feed.getTwitterUserId());
		feedImpl.setTwitterScreenName(feed.getTwitterScreenName());
		feedImpl.setLastStatusId(feed.getLastStatusId());

		return feedImpl;
	}

	/**
	 * Returns the feed with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the feed
	 * @return the feed
	 * @throws com.liferay.portal.NoSuchModelException if a feed with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Feed findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the feed with the primary key or throws a {@link com.liferay.twitter.NoSuchFeedException} if it could not be found.
	 *
	 * @param feedId the primary key of the feed
	 * @return the feed
	 * @throws com.liferay.twitter.NoSuchFeedException if a feed with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Feed findByPrimaryKey(long feedId)
		throws NoSuchFeedException, SystemException {
		Feed feed = fetchByPrimaryKey(feedId);

		if (feed == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + feedId);
			}

			throw new NoSuchFeedException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				feedId);
		}

		return feed;
	}

	/**
	 * Returns the feed with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the feed
	 * @return the feed, or <code>null</code> if a feed with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Feed fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the feed with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param feedId the primary key of the feed
	 * @return the feed, or <code>null</code> if a feed with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Feed fetchByPrimaryKey(long feedId) throws SystemException {
		Feed feed = (Feed)EntityCacheUtil.getResult(FeedModelImpl.ENTITY_CACHE_ENABLED,
				FeedImpl.class, feedId);

		if (feed == _nullFeed) {
			return null;
		}

		if (feed == null) {
			Session session = null;

			try {
				session = openSession();

				feed = (Feed)session.get(FeedImpl.class, Long.valueOf(feedId));

				if (feed != null) {
					cacheResult(feed);
				}
				else {
					EntityCacheUtil.putResult(FeedModelImpl.ENTITY_CACHE_ENABLED,
						FeedImpl.class, feedId, _nullFeed);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(FeedModelImpl.ENTITY_CACHE_ENABLED,
					FeedImpl.class, feedId);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return feed;
	}

	/**
	 * Returns all the feeds.
	 *
	 * @return the feeds
	 * @throws SystemException if a system exception occurred
	 */
	public List<Feed> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the feeds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. When orderByComparator is specified, the query will include the given ORDER BY logic. When orderByComparator is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), the query will include the default ORDER BY logic from FeedModelImpl. If both orderByComparator and pagination are absent, for performance reason, the query will not have a ORDER BY clause, on returning the result set will be sorted in portal side by PK ASC order.
	 * </p>
	 *
	 * @param start the lower bound of the range of feeds
	 * @param end the upper bound of the range of feeds (not inclusive)
	 * @return the range of feeds
	 * @throws SystemException if a system exception occurred
	 */
	public List<Feed> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the feeds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. When orderByComparator is specified, the query will include the given ORDER BY logic. When orderByComparator is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), the query will include the default ORDER BY logic from FeedModelImpl. If both orderByComparator and pagination are absent, for performance reason, the query will not have a ORDER BY clause, on returning the result set will be sorted in portal side by PK ASC order.
	 * </p>
	 *
	 * @param start the lower bound of the range of feeds
	 * @param end the upper bound of the range of feeds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of feeds
	 * @throws SystemException if a system exception occurred
	 */
	public List<Feed> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<Feed> list = (List<Feed>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_FEED);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_FEED;

				if (pagination) {
					sql = sql.concat(FeedModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<Feed>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList(list);
				}
				else {
					list = (List<Feed>)QueryUtil.list(q, getDialect(), start,
							end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the feeds from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (Feed feed : findAll()) {
			remove(feed);
		}
	}

	/**
	 * Returns the number of feeds.
	 *
	 * @return the number of feeds
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_FEED);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the feed persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.twitter.model.Feed")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Feed>> listenersList = new ArrayList<ModelListener<Feed>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Feed>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(FeedImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = FeedPersistence.class)
	protected FeedPersistence feedPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_FEED = "SELECT feed FROM Feed feed";
	private static final String _SQL_SELECT_FEED_WHERE = "SELECT feed FROM Feed feed WHERE ";
	private static final String _SQL_COUNT_FEED = "SELECT COUNT(feed) FROM Feed feed";
	private static final String _SQL_COUNT_FEED_WHERE = "SELECT COUNT(feed) FROM Feed feed WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "feed.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Feed exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Feed exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(FeedPersistenceImpl.class);
	private static Feed _nullFeed = new FeedImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Feed> toCacheModel() {
				return _nullFeedCacheModel;
			}
		};

	private static CacheModel<Feed> _nullFeedCacheModel = new CacheModel<Feed>() {
			public Feed toEntityModel() {
				return _nullFeed;
			}
		};
}
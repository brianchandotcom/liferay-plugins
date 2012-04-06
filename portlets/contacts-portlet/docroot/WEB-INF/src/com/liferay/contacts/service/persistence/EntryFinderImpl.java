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

package com.liferay.contacts.service.persistence;

import com.liferay.contacts.model.Entry;
import com.liferay.contacts.model.impl.EntryImpl;
import com.liferay.contacts.service.EntryLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class EntryFinderImpl extends BasePersistenceImpl<Entry>
	implements EntryFinder {

	public static final String COUNT_BY_C_U_FN_EA =
		EntryFinder.class.getName() + ".countByC_U_FN_EA";

	public static final String COUNT_BY_C_U_FN_MN_LN_SN_EA =
		EntryFinder.class.getName() + ".countByC_U_FN_MN_LN_SN_EA";

	public static final String FIND_BY_C_U_FN_EA =
		EntryFinder.class.getName() + ".findByC_U_FN_EA";

	public static final String FIND_BY_C_U_FN_MN_LN_SN_EA =
		EntryFinder.class.getName() + ".findByC_U_FN_MN_LN_SN_EA";

	public int countByC_U_FN_EA(long companyId, long userId, String keywords)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_C_U_FN_EA);

			String[] names = null;
			boolean andOperator = false;

			if (Validator.isNotNull(keywords)) {
				names = CustomSQLUtil.keywords(keywords);
			}
			else {
				andOperator = true;
			}

			names = CustomSQLUtil.keywords(names);

			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(Contacts_Entry.fullName)", StringPool.LIKE, true,
				names);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(Contacts_Entry.emailAddress)", StringPool.LIKE,
				true, names);

			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(userId);
			qPos.add(names, 4);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByC_U_FN_MN_LN_SN_EA(
			long companyId, long userId, String keywords)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_C_U_FN_MN_LN_SN_EA);

			String[] names = null;
			boolean andOperator = false;

			if (Validator.isNotNull(keywords)) {
				names = CustomSQLUtil.keywords(keywords);
			}
			else {
				andOperator = true;
			}

			names = CustomSQLUtil.keywords(names);

			sql = replaceKeywords(sql, names, andOperator);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(names, 10);
			qPos.add(companyId);
			qPos.add(userId);
			qPos.add(names, 4);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Entry> findByC_U_FN_EA(
			long companyId, long userId, String keywords, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_U_FN_EA);

			String[] names = null;
			boolean andOperator = false;

			if (Validator.isNotNull(keywords)) {
				names = CustomSQLUtil.keywords(keywords);
			}
			else {
				andOperator = true;
			}

			names = CustomSQLUtil.keywords(names);

			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(Contacts_Entry.fullName)", StringPool.LIKE, true,
				names);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(Contacts_Entry.emailAddress)", StringPool.LIKE,
				true, names);

			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Entry", EntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(userId);
			qPos.add(names, 4);

			return (List<Entry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findByC_U_FN_MN_LN_SN_EA(
			long companyId, long userId, String keywords, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_U_FN_MN_LN_SN_EA);

			String[] names = null;
			boolean andOperator = false;

			if (Validator.isNotNull(keywords)) {
				names = CustomSQLUtil.keywords(keywords);
			}
			else {
				andOperator = true;
			}

			names = CustomSQLUtil.keywords(names);

			sql = replaceKeywords(sql, names, andOperator);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar("id", Type.LONG);
			q.addScalar("name", Type.STRING);
			q.addScalar("registeredUser", Type.BOOLEAN);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(names, 10);
			qPos.add(companyId);
			qPos.add(userId);
			qPos.add(names, 4);

			List<Object> models = new ArrayList<Object>();

			Iterator<Object[]> itr = (Iterator<Object[]>)QueryUtil.iterate(
				q, getDialect(), start, end);

			while (itr.hasNext()) {
				Object[] array = itr.next();

				long id = (Long)array[0];
				String name = (String)array[1];
				boolean registeredUser = (Boolean)array[2];

				Object obj = null;

				if (registeredUser) {
					obj = UserLocalServiceUtil.getUser(id);
				}
				else {
					obj = EntryLocalServiceUtil.getEntry(id);
				}

				models.add(obj);
			}

			return models;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String replaceKeywords(
		String sql, String[] keywords, boolean andOperator) {

		sql = CustomSQLUtil.replaceKeywords(
			sql, "lower(User_.firstName)", StringPool.LIKE, false, keywords);
		sql = CustomSQLUtil.replaceKeywords(
			sql, "lower(User_.middleName)", StringPool.LIKE, false, keywords);
		sql = CustomSQLUtil.replaceKeywords(
			sql, "lower(User_.lastName)", StringPool.LIKE, false, keywords);
		sql = CustomSQLUtil.replaceKeywords(
			sql, "lower(User_.screenName)", StringPool.LIKE, false, keywords);
		sql = CustomSQLUtil.replaceKeywords(
			sql, "lower(User_.emailAddress)", StringPool.LIKE, true, keywords);
		sql = CustomSQLUtil.replaceKeywords(
			sql, "lower(Contacts_Entry.fullName)", StringPool.LIKE, true,
			keywords);
		sql = CustomSQLUtil.replaceKeywords(
			sql, "lower(Contacts_Entry.emailAddress)", StringPool.LIKE, true,
			keywords);

		sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

		return sql;
	}

}
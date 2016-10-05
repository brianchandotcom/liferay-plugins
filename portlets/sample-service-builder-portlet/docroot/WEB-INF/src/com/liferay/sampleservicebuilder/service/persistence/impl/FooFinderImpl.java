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

package com.liferay.sampleservicebuilder.service.persistence.impl;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.sampleservicebuilder.model.Foo;
import com.liferay.sampleservicebuilder.model.impl.FooImpl;
import com.liferay.sampleservicebuilder.service.persistence.FooFinder;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * @author Inácio Nery
 */
public class FooFinderImpl extends FooFinderBaseImpl implements FooFinder {

	public static final String COUNT_BY_FOOID =
		FooFinder.class.getName() + ".countByFooId";

	public static final String FIND_BY_FOOID =
		FooFinder.class.getName() + ".findByFooId";

	@Override
	public int countByFooIds(String[] fooIds) {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_FOOID);

			StringBundler sb = new StringBundler(fooIds.length * 2);

			if (ArrayUtil.isNotEmpty(fooIds)) {
				sb.append("WHERE ");

				for (int i = 0; i < fooIds.length; i++) {
					sb.append("fooId = ?");

					if ((i + 1) < fooIds.length) {
						sb.append(" OR ");
					}
				}
			}

			sql = StringUtil.replace(sql, "{WHERE_CLAUSE}", sb.toString());

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (ArrayUtil.isNotEmpty(fooIds)) {
				qPos.add(fooIds);
			}

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

	@Override
	public List<Foo> findByFooIds(String[] fooIds, int start, int end) {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_FOOID);

			StringBundler sb = new StringBundler(fooIds.length * 2);

			if (ArrayUtil.isNotEmpty(fooIds)) {
				sb.append("WHERE ");

				for (int i = 0; i < fooIds.length; i++) {
					sb.append("fooId = ?");

					if ((i + 1) < fooIds.length) {
						sb.append(" OR ");
					}
				}
			}

			sql = StringUtil.replace(sql, "{WHERE_CLAUSE}", sb.toString());

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("Foo", FooImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			if (ArrayUtil.isNotEmpty(fooIds)) {
				qPos.add(fooIds);
			}

			return (List<Foo>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}
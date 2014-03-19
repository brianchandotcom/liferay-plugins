/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.mentions.service.persistence.impl;

import com.liferay.mentions.service.persistence.UserFinder;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**
 * @author Sergio González
 */
public class UserFinderImpl implements UserFinder {

	public static final String FIND_BY_SOCIAL_RELATION_TYPES =
		UserFinder.class.getName() + ".findBySocialRelationTypes";

	public static final String FIND_BY_USERS_GROUPS =
		UserFinder.class.getName() + ".findByUsersGroups";

	@Override
	public List<User> findBySocialRelationTypes(
			String query, long userId, int[] types, int max)
		throws SystemException {

		if (types.length == 0) {
			return new ArrayList<User>();
		}

		DataSource dataSource = InfrastructureUtil.getDataSource();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = dataSource.getConnection();

			String sql = getFindBySocialRelationTypes_SQL(types);

			String[] keywords = CustomSQLUtil.keywords(query, true, 1);

			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(User_.firstName)", StringPool.LIKE, false,
				keywords);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(User_.middleName)", StringPool.LIKE, false,
				keywords);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(User_.lastName)", StringPool.LIKE, false, keywords);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(User_.screenName)", StringPool.LIKE, false,
				keywords);

			DB db = DBFactoryUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(sql);

			ps.setLong(1, userId);

			int i = 0;

			for (i = 2; i < types.length + 2; i++) {
				ps.setInt(i, types[i - 2]);
			}

			ps.setLong(i, userId);

			ps.setString(i + 1, keywords[0]);
			ps.setString(i + 2, keywords[0]);
			ps.setString(i + 3, keywords[0]);
			ps.setString(i + 4, keywords[0]);

			rs = ps.executeQuery();

			List<User> users = new ArrayList<User>();

			for (i = 0; (i < max) && rs.next(); i++) {
				User user = UserLocalServiceUtil.createUser(
					rs.getLong("userId"));

				user.setContactId(rs.getLong("contactId"));
				user.setScreenName(rs.getString("screenName"));
				user.setFirstName(rs.getString("firstName"));
				user.setMiddleName(rs.getString("middleName"));
				user.setLastName(rs.getString("lastName"));
				user.setPortraitId(rs.getLong("portraitId"));

				users.add(user);
			}

			return users;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	@Override
	public List<User> findByUsersGroups(
			String query, long userId, String[] groupNames, int max)
		throws SystemException {

		DataSource dataSource = InfrastructureUtil.getDataSource();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = dataSource.getConnection();

			String sql = getFindByUsersGroups_SQL(groupNames);

			String[] keywords = CustomSQLUtil.keywords(query, true, 1);

			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(User_.firstName)", StringPool.LIKE, false,
				keywords);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(User_.middleName)", StringPool.LIKE, false,
				keywords);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(User_.lastName)", StringPool.LIKE, false, keywords);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(User_.screenName)", StringPool.LIKE, false,
				keywords);

			DB db = DBFactoryUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(sql);

			ps.setLong(1, userId);

			int i = 0;

			for (i = 2; i < groupNames.length + 2; i++) {
				ps.setString(i, groupNames[i - 2]);
			}

			ps.setLong(i, userId);

			ps.setString(i + 1, keywords[0]);
			ps.setString(i + 2, keywords[0]);
			ps.setString(i + 3, keywords[0]);
			ps.setString(i + 4, keywords[0]);

			rs = ps.executeQuery();

			List<User> users = new ArrayList<User>();

			for (i = 0; (i < max) && rs.next(); i++) {
				User user = UserLocalServiceUtil.createUser(
					rs.getLong("userId"));

				user.setContactId(rs.getLong("contactId"));
				user.setScreenName(rs.getString("screenName"));
				user.setFirstName(rs.getString("firstName"));
				user.setMiddleName(rs.getString("middleName"));
				user.setLastName(rs.getString("lastName"));
				user.setPortraitId(rs.getLong("portraitId"));

				users.add(user);
			}

			return users;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected String getFindBySocialRelationTypes_SQL(int[] types) {
		String sql = CustomSQLUtil.get(FIND_BY_SOCIAL_RELATION_TYPES);

		if (types.length == 0) {
			return StringUtil.replace(
				sql, "[$SOCIAL_RELATION_TYPES$]", StringPool.BLANK);
		}

		StringBundler sb = new StringBundler(types.length * 2 - 1);

		for (int i = 0; i < types.length; i++) {
			sb.append(StringPool.QUESTION);

			if ((i + 1) < types.length) {
				sb.append(StringPool.COMMA);
			}
		}

		return StringUtil.replace(
			sql, "[$SOCIAL_RELATION_TYPES$]",
			"SocialRelation.type_ IN (" + sb.toString() + ") AND");
	}

	protected String getFindByUsersGroups_SQL(String[] groupNames) {
		String sql = CustomSQLUtil.get(FIND_BY_USERS_GROUPS);

		if (groupNames.length == 0) {
			return StringUtil.replace(
				sql,
				new String[] {
					"[$USERS_GROUPS_JOIN$]", "[$USERS_GROUPS_WHERE$]"
				},
				new String[] {StringPool.BLANK, StringPool.BLANK});
		}

		StringBundler sb = new StringBundler(groupNames.length * 2 - 1);

		for (int i = 0; i < groupNames.length; i++) {
			sb.append(StringPool.QUESTION);

			if ((i + 1) < groupNames.length) {
				sb.append(StringPool.COMMA);
			}
		}

		return StringUtil.replace(
			sql,
			new String[] {"[$USERS_GROUPS_JOIN$]", "[$USERS_GROUPS_WHERE$]"},
			new String[] {
				"INNER JOIN Group_ ON Group_.groupId = Users_Groups.groupId",
				"AND Group_.name NOT IN (" + sb.toString() + ")"
			});
	}

}
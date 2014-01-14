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

package com.liferay.sync.engine.service.persistence;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import com.liferay.sync.engine.util.SyncConstants;

import java.io.File;

import java.sql.SQLException;

import org.apache.commons.io.FileUtils;

/**
 * @author Shinn Lok
 */
public class BaseSyncDaoImpl<TT, TID> extends BaseDaoImpl<TT, TID>
	implements BaseSyncDao<TT, TID> {

	public BaseSyncDaoImpl(Class<TT> dataClass) throws SQLException {
		super(_getConnectionSource(), dataClass);
	}

	@Override
	public int createTable() throws SQLException {
		return TableUtils.createTable(connectionSource, dataClass);
	}

	private static ConnectionSource _getConnectionSource() throws SQLException {
		if (_connectionSource != null) {
			return _connectionSource;
		}

		StringBuilder sb = new StringBuilder(7);

		sb.append("jdbc:h2:");
		sb.append(FileUtils.getUserDirectoryPath());
		sb.append(File.separator);
		sb.append(SyncConstants.SYNC_CONFIG_FOLDER);
		sb.append(File.separator);
		sb.append(SyncConstants.SYNC_DB_NAME);
		sb.append(";AUTO_SERVER=TRUE");

		_connectionSource = new JdbcPooledConnectionSource(sb.toString());

		return _connectionSource;
	}

	private static ConnectionSource _connectionSource;

}
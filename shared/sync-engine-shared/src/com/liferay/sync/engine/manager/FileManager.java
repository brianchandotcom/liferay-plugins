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

package com.liferay.sync.engine.manager;

import com.liferay.sync.engine.dao.FileDao;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shinn Lok
 */
public class FileManager {

	public static FileDao getDao() {
		if (_fileDao != null) {
			return _fileDao;
		}

		try {
			_fileDao = new FileDao();
		}
		catch (SQLException sqle) {
			_logger.debug(sqle.getMessage(), sqle);
		}

		return _fileDao;
	}

	private static FileDao _fileDao = getDao();
	private static Logger _logger = LoggerFactory.getLogger(FileManager.class);

}
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

import com.liferay.sync.engine.dao.AccountDao;
import com.liferay.sync.engine.model.Account;
import com.liferay.sync.engine.util.Encryptor;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shinn Lok
 */
public class AccountManager {

	public static Account addAccount(String login, String password, String url)
		throws Exception {

		Account account = new Account();

		account.setLogin(login);
		account.setPassword(Encryptor.encrypt(password));
		account.setUrl(url);

		_accountDao.create(account);

		return account;
	}

	public static Account getAccount(long accountId) throws Exception {
		return _accountDao.queryForId(accountId);
	}

	public static AccountDao getDao() {
		if (_accountDao != null) {
			return _accountDao;
		}

		try {
			_accountDao = new AccountDao();
		}
		catch (SQLException sqle) {
			_logger.debug(sqle.getMessage(), sqle);
		}

		return _accountDao;
	}

	private static AccountDao _accountDao = getDao();
	private static Logger _logger = LoggerFactory.getLogger(
		AccountManager.class);

}
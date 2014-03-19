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

package com.liferay.mentions.util;

import com.liferay.mentions.service.persistence.UserFinder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.usersadmin.util.UsersAdminUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Sergio González
 */
public class DefaultMentionsUserFinderImpl implements MentionsUserFinder {

	@Override
	public List<User> getUsers(String query, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		PortletPreferences companyPortletPreferences =
			PrefsPropsUtil.getPreferences(themeDisplay.getCompanyId(), true);

		boolean mentionsAnyUser = GetterUtil.getBoolean(
			companyPortletPreferences.getValue("mentionsAnyUser", null));

		if (mentionsAnyUser) {
			return getUnfilteredUsers(query, themeDisplay);
		}

		boolean mentionsSocialRelationTypesEnabled = GetterUtil.getBoolean(
			companyPortletPreferences.getValue(
				"mentionsSocialRelationTypesEnabled", null));
		boolean mentionsSitesEnabled = GetterUtil.getBoolean(
			companyPortletPreferences.getValue("mentionsSitesEnabled", null));

		if (mentionsSitesEnabled && mentionsSocialRelationTypesEnabled) {
			List<User> groupUsers = getUsersGroups(query, themeDisplay);
			List<User> socialUsers = getUsersBySocialRelationTypes(
				query, themeDisplay);

			List<User> users = new ArrayList<User>(
				groupUsers.size() + socialUsers.size());

			users.addAll(groupUsers);

			for (User socialUser : socialUsers) {
				if (Collections.binarySearch(
						groupUsers, socialUser, null) < 0) {

					users.add(socialUser);
				}
			}

			return users;
		}
		else if (mentionsSitesEnabled) {
			return getUsersGroups(query, themeDisplay);
		}
		else if (mentionsSocialRelationTypesEnabled) {
			return getUsersBySocialRelationTypes(query, themeDisplay);
		}

		return new ArrayList<User>();
	}

	public void setUserFinder(UserFinder userFinder) {
		_userFinder = userFinder;
	}

	protected List<User> getUnfilteredUsers(
			String query, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		query = query.concat(StringPool.STAR);

		Hits hits = UserLocalServiceUtil.search(
			themeDisplay.getCompanyId(), query, query, query, query,
			StringPool.BLANK, WorkflowConstants.STATUS_APPROVED, null, false, 0,
			PortletPropsValues.MENTIONS_USERS_LIST_MAX_USERS, (Sort)null);

		return UsersAdminUtil.getUsers(hits);
	}

	protected List<User> getUsersBySocialRelationTypes(
			String query, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		PortletPreferences companyPortletPreferences =
			PrefsPropsUtil.getPreferences(themeDisplay.getCompanyId(), true);

		Map<String, Integer> allSocialRelationTypes = null;
		String[] socialRelationTypesArray = null;

		try {
			allSocialRelationTypes = MentionsUtil.getAllSocialRelationTypes();

			socialRelationTypesArray = MentionsUtil.getSocialRelationTypes(
				companyPortletPreferences);
		}
		catch (ClassNotFoundException cnfe) {
			throw new RuntimeException(cnfe);
		}
		catch (IllegalAccessException iae) {
			throw new RuntimeException(iae);
		}

		int[] types = new int[socialRelationTypesArray.length];

		for (int i = 0; i < socialRelationTypesArray.length; i++) {
			types[i] = allSocialRelationTypes.get(socialRelationTypesArray[i]);
		}

		return _userFinder.findBySocialRelationTypes(
			query, themeDisplay.getUserId(), types,
			PortletPropsValues.MENTIONS_USERS_LIST_MAX_USERS);
	}

	protected List<User> getUsersGroups(String query, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return _userFinder.findByUsersGroups(
			query, themeDisplay.getUserId(),
			PortletPropsValues.MENTIONS_USERS_LIST_SITE_EXCLUDES,
			PortletPropsValues.MENTIONS_USERS_LIST_MAX_USERS);
	}

	private UserFinder _userFinder;

}
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

package com.liferay.mentions.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portal.util.test.UserTestUtil;
import com.liferay.portlet.social.model.SocialRelationConstants;
import com.liferay.portlet.social.service.SocialRelationLocalServiceUtil;
import com.liferay.portlet.social.util.SocialInteractionsConfiguration.SocialInteractionsType;

import org.jboss.arquillian.junit.Arquillian;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class BlogsEntryMentionsNotifierTest {

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();
		_group2 = GroupTestUtil.addGroup();

		_user1 = UserTestUtil.addUser("sergio", _group1.getGroupId());
		_user2 = UserTestUtil.addUser("ana", _group1.getGroupId());
		_user3 = UserTestUtil.addUser("alberto", _group2.getGroupId());
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(_group1);
		GroupLocalServiceUtil.deleteGroup(_group2);

		UserLocalServiceUtil.deleteUser(_user1);
		UserLocalServiceUtil.deleteUser(_user2);
	}

	@Test
	public void testEmail() throws Exception {
		MentionsNotifier mentionsNotifier = new MentionsNotifier();

		String[] mentionedUsersScreenNames =
			mentionsNotifier.getMentionedUsersScreenNames(
				TestPropsValues.getUserId(),
				"Send an email to " +
					"<a href=\"mailto:ana@sergio.com\">ana@sergio.com</a>.");

		Assert.assertEquals(0, mentionedUsersScreenNames.length);
	}

	@Test
	public void testInexistentUser() throws Exception {
		MentionsNotifier mentionsNotifier = new MentionsNotifier();

		String[] mentionedUsersScreenNames =
			mentionsNotifier.getMentionedUsersScreenNames(
				TestPropsValues.getUserId(),
				"hey <a href=\"http://localhost:8080\">@fakeuser</a> " +
					"you are a ghost.");

		Assert.assertEquals(0, mentionedUsersScreenNames.length);
	}

	@Test
	public void testMultipleUsers() throws Exception {
		MentionsNotifier mentionsNotifier = new MentionsNotifier();

		String[] mentionedUsersScreenNames =
			mentionsNotifier.getMentionedUsersScreenNames(
				TestPropsValues.getUserId(),
				"hey <a href=\"http://localhost:8080\">@sergio</a> " +
					"<a href=\"http://localhost:8080\">@ana</a> " +
						"can you check this out?");

		Assert.assertEquals(2, mentionedUsersScreenNames.length);
		Assert.assertTrue(ArrayUtil.contains(mentionedUsersScreenNames, "ana"));
		Assert.assertTrue(
			ArrayUtil.contains(mentionedUsersScreenNames, "sergio"));
	}

	@Test
	public void
			testMultipleUserSocialInteractionsConfigurationFriendsRelation1()
		throws Exception {

		String socialInteractionsType =
			PrefsPropsUtil.getString(
				_group1.getCompanyId(),
				"socialInteractionsType" + PortletKeys.MENTIONS,
				SocialInteractionsType.ALL_USERS.toString());
		boolean socialInteractionsSitesEnabled = PrefsPropsUtil.getBoolean(
			_group1.getCompanyId(),
			"socialInteractionsSitesEnabled" + PortletKeys.MENTIONS, true);
		String socialInteractionsSocialRelationTypes =
			PrefsPropsUtil.getString(
				_group1.getCompanyId(),
				"socialInteractionsSocialRelationTypes" + PortletKeys.MENTIONS,
				StringPool.BLANK);
		boolean socialInteractionsSocialRelationTypesEnabled =
			PrefsPropsUtil.getBoolean(
				_group1.getCompanyId(),
				"socialInteractionsSocialRelationTypesEnabled" +
					PortletKeys.MENTIONS,
				true);

		SocialRelationLocalServiceUtil.addRelation(
			_user1.getUserId(), _user2.getUserId(),
			SocialRelationConstants.TYPE_BI_FRIEND);
		SocialRelationLocalServiceUtil.addRelation(
			_user1.getUserId(), _user3.getUserId(),
			SocialRelationConstants.TYPE_BI_COWORKER);

		try {
			updateMentionsSocialInteractionsConfiguration(
				SocialInteractionsType.SELECT_USERS.toString(), false,
				String.valueOf(SocialRelationConstants.TYPE_BI_FRIEND), true);

			MentionsNotifier mentionsNotifier = new MentionsNotifier();

			String[] mentionedUsersScreenNames =
				mentionsNotifier.getMentionedUsersScreenNames(
					_user1.getUserId(),
					"hey <a href=\"http://localhost:8080\">@alberto</a> " +
						"<a href=\"http://localhost:8080\">@ana</a> " +
							"can you check this out?");

			Assert.assertEquals(1, mentionedUsersScreenNames.length);
			Assert.assertEquals("ana", mentionedUsersScreenNames[0]);
		}
		finally {
			SocialRelationLocalServiceUtil.deleteRelation(
				_user1.getUserId(), _user2.getUserId(),
				SocialRelationConstants.TYPE_BI_FRIEND);
			SocialRelationLocalServiceUtil.deleteRelation(
				_user1.getUserId(), _user3.getUserId(),
				SocialRelationConstants.TYPE_BI_COWORKER);

			updateMentionsSocialInteractionsConfiguration(
				socialInteractionsType, socialInteractionsSitesEnabled,
				socialInteractionsSocialRelationTypes,
				socialInteractionsSocialRelationTypesEnabled);
		}
	}

	@Test
	public void
			testMultipleUserSocialInteractionsConfigurationFriendsRelation2()
		throws Exception {

		String socialInteractionsType =
			PrefsPropsUtil.getString(
				_group1.getCompanyId(),
				"socialInteractionsType" + PortletKeys.MENTIONS,
				SocialInteractionsType.ALL_USERS.toString());
		boolean socialInteractionsSitesEnabled = PrefsPropsUtil.getBoolean(
			_group1.getCompanyId(),
			"socialInteractionsSitesEnabled" + PortletKeys.MENTIONS, true);
		String socialInteractionsSocialRelationTypes =
			PrefsPropsUtil.getString(
				_group1.getCompanyId(),
				"socialInteractionsSocialRelationTypes" + PortletKeys.MENTIONS,
				StringPool.BLANK);
		boolean socialInteractionsSocialRelationTypesEnabled =
			PrefsPropsUtil.getBoolean(
				_group1.getCompanyId(),
				"socialInteractionsSocialRelationTypesEnabled" +
					PortletKeys.MENTIONS,
				true);

		SocialRelationLocalServiceUtil.addRelation(
			_user1.getUserId(), _user2.getUserId(),
			SocialRelationConstants.TYPE_BI_FRIEND);
		SocialRelationLocalServiceUtil.addRelation(
			_user1.getUserId(), _user3.getUserId(),
			SocialRelationConstants.TYPE_BI_COWORKER);

		try {
			updateMentionsSocialInteractionsConfiguration(
				SocialInteractionsType.SELECT_USERS.toString(), false,
				String.valueOf(SocialRelationConstants.TYPE_BI_FRIEND) + "," +
					String.valueOf(SocialRelationConstants.TYPE_BI_COWORKER),
				true);

			MentionsNotifier mentionsNotifier = new MentionsNotifier();

			String[] mentionedUsersScreenNames =
				mentionsNotifier.getMentionedUsersScreenNames(
					_user1.getUserId(),
					"hey <a href=\"http://localhost:8080\">@alberto</a> " +
						"<a href=\"http://localhost:8080\">@ana</a> " +
							"can you check this out?");

			Assert.assertEquals(2, mentionedUsersScreenNames.length);
			Assert.assertTrue(
				ArrayUtil.contains(mentionedUsersScreenNames, "ana"));
			Assert.assertTrue(
				ArrayUtil.contains(mentionedUsersScreenNames, "alberto"));
		}
		finally {
			SocialRelationLocalServiceUtil.deleteRelation(
				_user1.getUserId(), _user2.getUserId(),
				SocialRelationConstants.TYPE_BI_FRIEND);
			SocialRelationLocalServiceUtil.deleteRelation(
				_user1.getUserId(), _user3.getUserId(),
				SocialRelationConstants.TYPE_BI_COWORKER);

			updateMentionsSocialInteractionsConfiguration(
				socialInteractionsType, socialInteractionsSitesEnabled,
				socialInteractionsSocialRelationTypes,
				socialInteractionsSocialRelationTypesEnabled);
		}
	}

	@Test
	public void
			testMultipleUserSocialInteractionsConfigurationSitesEnabledAndFriendsRelation()
		throws Exception {

		String socialInteractionsType =
			PrefsPropsUtil.getString(
				_group1.getCompanyId(),
				"socialInteractionsType" + PortletKeys.MENTIONS,
				SocialInteractionsType.ALL_USERS.toString());
		boolean socialInteractionsSitesEnabled = PrefsPropsUtil.getBoolean(
			_group1.getCompanyId(),
			"socialInteractionsSitesEnabled" + PortletKeys.MENTIONS, true);
		String socialInteractionsSocialRelationTypes =
			PrefsPropsUtil.getString(
				_group1.getCompanyId(),
				"socialInteractionsSocialRelationTypes" + PortletKeys.MENTIONS,
				StringPool.BLANK);
		boolean socialInteractionsSocialRelationTypesEnabled =
			PrefsPropsUtil.getBoolean(
				_group1.getCompanyId(),
				"socialInteractionsSocialRelationTypesEnabled" +
					PortletKeys.MENTIONS,
				true);

		SocialRelationLocalServiceUtil.addRelation(
			_user1.getUserId(), _user3.getUserId(),
			SocialRelationConstants.TYPE_BI_FRIEND);

		try {
			updateMentionsSocialInteractionsConfiguration(
				SocialInteractionsType.SELECT_USERS.toString(), true,
				String.valueOf(SocialRelationConstants.TYPE_BI_FRIEND), true);

			MentionsNotifier mentionsNotifier = new MentionsNotifier();

			String[] mentionedUsersScreenNames =
				mentionsNotifier.getMentionedUsersScreenNames(
					_user1.getUserId(),
					"hey <a href=\"http://localhost:8080\">@alberto</a> " +
						"<a href=\"http://localhost:8080\">@ana</a> " +
							"can you check this out?");

			Assert.assertEquals(2, mentionedUsersScreenNames.length);
			Assert.assertTrue(
				ArrayUtil.contains(mentionedUsersScreenNames, "ana"));
			Assert.assertTrue(
				ArrayUtil.contains(mentionedUsersScreenNames, "alberto"));
		}
		finally {
			SocialRelationLocalServiceUtil.deleteRelation(
				_user1.getUserId(), _user3.getUserId(),
				SocialRelationConstants.TYPE_BI_FRIEND);

			updateMentionsSocialInteractionsConfiguration(
				socialInteractionsType, socialInteractionsSitesEnabled,
				socialInteractionsSocialRelationTypes,
				socialInteractionsSocialRelationTypesEnabled);
		}
	}

	@Test
	public void testMultipleUsersSeparatedByComma() throws Exception {
		MentionsNotifier mentionsNotifier = new MentionsNotifier();

		String[] mentionedUsersScreenNames =
			mentionsNotifier.getMentionedUsersScreenNames(
				TestPropsValues.getUserId(),
				"hey <a href=\"http://localhost:8080\">@ana</a>," +
					"<a href=\"http://localhost:8080\">@sergio</a> " +
						"can you check this out?");

		Assert.assertEquals(2, mentionedUsersScreenNames.length);
		Assert.assertTrue(ArrayUtil.contains(mentionedUsersScreenNames, "ana"));
		Assert.assertTrue(
			ArrayUtil.contains(mentionedUsersScreenNames, "sergio"));
	}

	@Test
	public void
			testMultipleUsersSocialInteractionsConfigurationSitesEnabledOnly()
		throws Exception {

		String socialInteractionsType =
			PrefsPropsUtil.getString(
				_group1.getCompanyId(),
				"socialInteractionsType" + PortletKeys.MENTIONS,
				SocialInteractionsType.ALL_USERS.toString());
		boolean socialInteractionsSitesEnabled = PrefsPropsUtil.getBoolean(
			_group1.getCompanyId(),
			"socialInteractionsSitesEnabled" + PortletKeys.MENTIONS, true);
		boolean socialInteractionsSocialRelationTypesEnabled =
			PrefsPropsUtil.getBoolean(
				_group1.getCompanyId(),
				"socialInteractionsSocialRelationTypesEnabled" +
					PortletKeys.MENTIONS,
				true);

		try {
			updateMentionsSocialInteractionsConfiguration(
				SocialInteractionsType.SELECT_USERS.toString(), true, null,
				false);

			MentionsNotifier mentionsNotifier = new MentionsNotifier();

			String[] mentionedUsersScreenNames =
				mentionsNotifier.getMentionedUsersScreenNames(
					_user1.getUserId(),
					"hey <a href=\"http://localhost:8080\">@ana</a> " +
						"<a href=\"http://localhost:8080\">@alberto</a> " +
							"can you check this out?");

			Assert.assertEquals(1, mentionedUsersScreenNames.length);
			Assert.assertEquals("ana", mentionedUsersScreenNames[0]);
		}
		finally {
			updateMentionsSocialInteractionsConfiguration(
				socialInteractionsType, socialInteractionsSitesEnabled, null,
				socialInteractionsSocialRelationTypesEnabled);
		}
	}

	@Test
	public void testSingleUser() throws Exception {
		MentionsNotifier mentionsNotifier = new MentionsNotifier();

		String[] mentionedUsersScreenNames =
			mentionsNotifier.getMentionedUsersScreenNames(
				TestPropsValues.getUserId(),
				"hey <a href=\"http://localhost:8080\">@sergio</a> " +
					"can you check this out?");

		Assert.assertEquals(1, mentionedUsersScreenNames.length);
		Assert.assertEquals("sergio", mentionedUsersScreenNames[0]);
	}

	@Test
	public void
			testSingleUserSocialInteractionsConfigurationFriendsRelation1()
		throws Exception {

		String socialInteractionsType =
			PrefsPropsUtil.getString(
				_group1.getCompanyId(),
				"socialInteractionsType" + PortletKeys.MENTIONS,
				SocialInteractionsType.ALL_USERS.toString());
		boolean socialInteractionsSitesEnabled = PrefsPropsUtil.getBoolean(
			_group1.getCompanyId(),
			"socialInteractionsSitesEnabled" + PortletKeys.MENTIONS, true);
		String socialInteractionsSocialRelationTypes =
			PrefsPropsUtil.getString(
				_group1.getCompanyId(),
				"socialInteractionsSocialRelationTypes" + PortletKeys.MENTIONS,
				StringPool.BLANK);
		boolean socialInteractionsSocialRelationTypesEnabled =
			PrefsPropsUtil.getBoolean(
				_group1.getCompanyId(),
				"socialInteractionsSocialRelationTypesEnabled" +
					PortletKeys.MENTIONS,
				true);

		try {
			updateMentionsSocialInteractionsConfiguration(
				SocialInteractionsType.SELECT_USERS.toString(), false,
				String.valueOf(SocialRelationConstants.TYPE_BI_FRIEND), true);

			MentionsNotifier mentionsNotifier = new MentionsNotifier();

			String[] mentionedUsersScreenNames =
				mentionsNotifier.getMentionedUsersScreenNames(
					_user1.getUserId(),
					"hey <a href=\"http://localhost:8080\">@alberto</a> " +
						"can you check this out?");

			Assert.assertEquals(0, mentionedUsersScreenNames.length);
		}
		finally {
			updateMentionsSocialInteractionsConfiguration(
				socialInteractionsType, socialInteractionsSitesEnabled,
				socialInteractionsSocialRelationTypes,
				socialInteractionsSocialRelationTypesEnabled);
		}
	}

	@Test
	public void
			testSingleUserSocialInteractionsConfigurationFriendsRelation2()
		throws Exception {

		String socialInteractionsType =
			PrefsPropsUtil.getString(
				_group1.getCompanyId(),
				"socialInteractionsType" + PortletKeys.MENTIONS,
				SocialInteractionsType.ALL_USERS.toString());
		boolean socialInteractionsSitesEnabled = PrefsPropsUtil.getBoolean(
			_group1.getCompanyId(),
			"socialInteractionsSitesEnabled" + PortletKeys.MENTIONS, true);
		String socialInteractionsSocialRelationTypes =
			PrefsPropsUtil.getString(
				_group1.getCompanyId(),
				"socialInteractionsSocialRelationTypes" + PortletKeys.MENTIONS,
				StringPool.BLANK);
		boolean socialInteractionsSocialRelationTypesEnabled =
			PrefsPropsUtil.getBoolean(
				_group1.getCompanyId(),
				"socialInteractionsSocialRelationTypesEnabled" +
					PortletKeys.MENTIONS,
				true);

		SocialRelationLocalServiceUtil.addRelation(
			_user1.getUserId(), _user3.getUserId(),
			SocialRelationConstants.TYPE_BI_FRIEND);

		try {
			updateMentionsSocialInteractionsConfiguration(
				SocialInteractionsType.SELECT_USERS.toString(), false,
				String.valueOf(SocialRelationConstants.TYPE_BI_FRIEND), true);

			MentionsNotifier mentionsNotifier = new MentionsNotifier();

			String[] mentionedUsersScreenNames =
				mentionsNotifier.getMentionedUsersScreenNames(
					_user1.getUserId(),
					"hey <a href=\"http://localhost:8080\">@alberto</a> " +
						"can you check this out?");

			Assert.assertEquals(1, mentionedUsersScreenNames.length);
			Assert.assertEquals("alberto", mentionedUsersScreenNames[0]);
		}
		finally {
			SocialRelationLocalServiceUtil.deleteRelation(
				_user1.getUserId(), _user3.getUserId(),
				SocialRelationConstants.TYPE_BI_FRIEND);

			updateMentionsSocialInteractionsConfiguration(
				socialInteractionsType, socialInteractionsSitesEnabled,
				socialInteractionsSocialRelationTypes,
				socialInteractionsSocialRelationTypesEnabled);
		}
	}

	@Test
	public void testSingleUserSocialInteractionsConfigurationSitesEnabled()
		throws Exception {

		String socialInteractionsType =
			PrefsPropsUtil.getString(
				_group1.getCompanyId(),
				"socialInteractionsType" + PortletKeys.MENTIONS,
				SocialInteractionsType.ALL_USERS.toString());
		boolean socialInteractionsSitesEnabled = PrefsPropsUtil.getBoolean(
			_group1.getCompanyId(),
			"socialInteractionsSitesEnabled" + PortletKeys.MENTIONS, true);
		boolean socialInteractionsSocialRelationTypesEnabled =
			PrefsPropsUtil.getBoolean(
				_group1.getCompanyId(),
				"socialInteractionsSocialRelationTypesEnabled" +
					PortletKeys.MENTIONS,
				true);

		try {
			updateMentionsSocialInteractionsConfiguration(
				SocialInteractionsType.SELECT_USERS.toString(), true, null,
				false);

			MentionsNotifier mentionsNotifier = new MentionsNotifier();

			String[] mentionedUsersScreenNames =
				mentionsNotifier.getMentionedUsersScreenNames(
					_user1.getUserId(),
					"hey <a href=\"http://localhost:8080\">@alberto</a> " +
						"can you check this out?");

			Assert.assertEquals(0, mentionedUsersScreenNames.length);
		}
		finally {
			updateMentionsSocialInteractionsConfiguration(
				socialInteractionsType, socialInteractionsSitesEnabled, null,
				socialInteractionsSocialRelationTypesEnabled);
		}
	}

	@Test
	public void testSingleUserWithComma() throws Exception {
		MentionsNotifier mentionsNotifier = new MentionsNotifier();

		String[] mentionedUsersScreenNames =
			mentionsNotifier.getMentionedUsersScreenNames(
				TestPropsValues.getUserId(),
				"hey <a href=\"http://localhost:8080\">@sergio</a>, " +
					"can you check this out?");

		Assert.assertEquals(1, mentionedUsersScreenNames.length);
		Assert.assertEquals("sergio", mentionedUsersScreenNames[0]);
	}

	@Test
	public void testSingleUserWithPeriod() throws Exception {
		MentionsNotifier mentionsNotifier = new MentionsNotifier();

		String[] mentionedUsersScreenNames =
			mentionsNotifier.getMentionedUsersScreenNames(
				TestPropsValues.getUserId(),
				"Hello <a href=\"http://localhost:8080\">@sergio</a>. " +
					"How are you?");

		Assert.assertEquals(1, mentionedUsersScreenNames.length);
		Assert.assertEquals("sergio", mentionedUsersScreenNames[0]);
	}

	protected void updateMentionsSocialInteractionsConfiguration(
			String socialInteractionsType,
			boolean socialInteractionsSitesEnabled,
			String socialInteractionsSocialRelationTypes,
			boolean socialInteractionsSocialRelationTypesEnabled)
		throws PortalException {

		UnicodeProperties properties = new UnicodeProperties(true);

		properties.setProperty(
			"socialInteractionsType" + PortletKeys.MENTIONS,
			socialInteractionsType);
		properties.setProperty(
			"socialInteractionsSitesEnabled" + PortletKeys.MENTIONS,
			String.valueOf(socialInteractionsSitesEnabled));

		if (socialInteractionsSocialRelationTypes != null) {
			properties.setProperty(
				"socialInteractionsSocialRelationTypes" + PortletKeys.MENTIONS,
				socialInteractionsSocialRelationTypes);
		}

		properties.setProperty(
			"socialInteractionsSocialRelationTypesEnabled" +
				PortletKeys.MENTIONS,
			String.valueOf(socialInteractionsSocialRelationTypesEnabled));

		CompanyLocalServiceUtil.updatePreferences(
			_group1.getCompanyId(), properties);
	}

	private Group _group1;
	private Group _group2;
	private User _user1;
	private User _user2;
	private User _user3;

}
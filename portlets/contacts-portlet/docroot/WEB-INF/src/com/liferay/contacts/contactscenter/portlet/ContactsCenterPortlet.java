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

package com.liferay.contacts.contactscenter.portlet;

import com.liferay.contacts.util.ContactsUtil;
import com.liferay.contacts.util.PortletKeys;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.notifications.ChannelHubManagerUtil;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.kernel.notifications.NotificationEventFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.comparator.UserLastNameComparator;
import com.liferay.portlet.social.NoSuchRelationException;
import com.liferay.portlet.social.model.SocialRelationConstants;
import com.liferay.portlet.social.model.SocialRequest;
import com.liferay.portlet.social.model.SocialRequestConstants;
import com.liferay.portlet.social.model.SocialRequestFeedEntry;
import com.liferay.portlet.social.service.SocialRelationLocalServiceUtil;
import com.liferay.portlet.social.service.SocialRequestInterpreterLocalServiceUtil;
import com.liferay.portlet.social.service.SocialRequestLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.io.IOException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ryan Park
 * @author Jonathan Lee
 */
public class ContactsCenterPortlet extends MVCPortlet {

	public void addSocialRelation(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] userIds = getUserIds(actionRequest);

		int type = ParamUtil.getInteger(actionRequest, "type");

		for (long userId : userIds) {
			if (userId == themeDisplay.getUserId()) {
				continue;
			}

			boolean blocked = SocialRelationLocalServiceUtil.hasRelation(
				userId, themeDisplay.getUserId(),
				SocialRelationConstants.TYPE_UNI_ENEMY);

			if (type == SocialRelationConstants.TYPE_UNI_ENEMY) {
				SocialRelationLocalServiceUtil.deleteRelations(
					themeDisplay.getUserId(), userId);
			}
			else if (blocked) {
				continue;
			}

			SocialRelationLocalServiceUtil.addRelation(
				themeDisplay.getUserId(), userId, type);

			if (blocked) {
				SocialRelationLocalServiceUtil.addRelation(
					userId, themeDisplay.getUserId(), type);
			}
		}
	}

	public void deleteSocialRelation(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] userIds = getUserIds(actionRequest);

		int type = ParamUtil.getInteger(actionRequest, "type");

		for (long userId : userIds) {
			if (userId == themeDisplay.getUserId()) {
				continue;
			}

			try {
				SocialRelationLocalServiceUtil.deleteRelation(
					themeDisplay.getUserId(), userId, type);
			}
			catch (NoSuchRelationException nsre) {
			}
		}
	}

	public void exportVCard(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long userId = ParamUtil.getLong(resourceRequest, "userId");

		User user = UserServiceUtil.getUserById(userId);

		String vCard = ContactsUtil.getVCard(user);

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			resourceRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			resourceResponse);

		ServletResponseUtil.sendFile(
			request, response, user.getFullName() + ".vcf",
			vCard.getBytes(StringPool.UTF8), "text/x-vcard; charset=UTF-8");
	}

	public void exportVCards(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long[] userIds = StringUtil.split(
			ParamUtil.getString(resourceRequest, "userIds"), 0L);

		List<User> users = new ArrayList<User>();

		for (long userId : userIds) {
			users.add(UserServiceUtil.getUserById(userId));
		}

		String vCards = ContactsUtil.getVCards(users);

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			resourceRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			resourceResponse);

		ServletResponseUtil.sendFile(
			request, response, "vcards.vcf", vCards.getBytes(StringPool.UTF8),
			"text/x-vcard; charset=UTF-8");
	}

	public void getContact(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long userId = ParamUtil.getLong(resourceRequest, "userId");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("success", true);

		JSONObject userJSONObject = JSONFactoryUtil.createJSONObject();

		User user = UserLocalServiceUtil.getUser(userId);

		boolean viewRelationActions = true;

		if (SocialRelationLocalServiceUtil.hasRelation(
				userId, themeDisplay.getUserId(),
				SocialRelationConstants.TYPE_UNI_ENEMY)) {

			viewRelationActions = false;
		}
		else if (SocialRelationLocalServiceUtil.hasRelation(
					themeDisplay.getUserId(), userId,
					SocialRelationConstants.TYPE_UNI_ENEMY)) {

			viewRelationActions = false;
		}

		boolean block = SocialRelationLocalServiceUtil.hasRelation(
			themeDisplay.getUserId(), userId,
			SocialRelationConstants.TYPE_UNI_ENEMY);

		userJSONObject.put("block", block);

		boolean connectionRequested =
			viewRelationActions &&
			SocialRequestLocalServiceUtil.hasRequest(
				themeDisplay.getUserId(), User.class.getName(),
				themeDisplay.getUserId(),
				SocialRelationConstants.TYPE_BI_CONNECTION, userId,
				SocialRequestConstants.STATUS_PENDING);

		userJSONObject.put("connectionRequested", connectionRequested);

		boolean connected =
			!connectionRequested &&
			viewRelationActions &&
			SocialRelationLocalServiceUtil.hasRelation(
				themeDisplay.getUserId(), userId,
				SocialRelationConstants.TYPE_BI_CONNECTION);

		userJSONObject.put("connected", connected);

		userJSONObject.put("emailAddress", user.getEmailAddress());
		userJSONObject.put("firstName", user.getFirstName());

		boolean following =
			viewRelationActions &&
			SocialRelationLocalServiceUtil.hasRelation(
				themeDisplay.getUserId(), userId,
				SocialRelationConstants.TYPE_UNI_FOLLOWER);

		userJSONObject.put("following", following);

		userJSONObject.put("fullName", user.getFullName());
		userJSONObject.put("jobTitle", user.getJobTitle());
		userJSONObject.put("lastName", user.getLastName());
		userJSONObject.put("portraitURL", user.getPortraitURL(themeDisplay));
		userJSONObject.put("userId", String.valueOf(user.getUserId()));

		LiferayPortletResponse liferayPortletResponse =
			(LiferayPortletResponse)resourceResponse;

		PortletURL viewSummaryURL = liferayPortletResponse.createRenderURL();

		viewSummaryURL.setWindowState(LiferayWindowState.EXCLUSIVE);

		viewSummaryURL.setParameter(
			"mvcPath", "/contacts_center/view_resources.jsp");
		viewSummaryURL.setParameter("userId", String.valueOf(user.getUserId()));

		userJSONObject.put("viewSummaryURL", viewSummaryURL.toString());

		jsonObject.put("user", userJSONObject);

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	public void getContacts(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String keywords = ParamUtil.getString(resourceRequest, "keywords");
		int socialRelationType = ParamUtil.getInteger(
			resourceRequest, "socialRelationType");
		int start = ParamUtil.getInteger(resourceRequest, "start");
		int end = ParamUtil.getInteger(resourceRequest, "end");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		JSONObject optionsJSONObject = JSONFactoryUtil.createJSONObject();

		optionsJSONObject.put("end", end);
		optionsJSONObject.put("keywords", keywords);
		optionsJSONObject.put("socialRelationType", socialRelationType);
		optionsJSONObject.put("start", start);

		jsonObject.put("options", optionsJSONObject);

		Group group = themeDisplay.getScopeGroup();
		Layout layout = themeDisplay.getLayout();

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		if (group.isUser() && layout.isPublicLayout()) {
			params.put("socialRelation", new Long[] {group.getClassPK()});
		}
		else if (socialRelationType != 0) {
			params.put(
				"socialRelationType",
				new Long[] {
					themeDisplay.getUserId(), new Long(socialRelationType)
				});
		}

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String portletName = portletDisplay.getPortletName();

		if (portletName.equals(PortletKeys.MEMBERS)) {
			params.put("usersGroups", new Long(group.getGroupId()));
		}

		List<User> users = UserLocalServiceUtil.search(
			themeDisplay.getCompanyId(), keywords,
			WorkflowConstants.STATUS_APPROVED, params, start, end,
			new UserLastNameComparator(true));

		int usersCount = UserLocalServiceUtil.searchCount(
			themeDisplay.getCompanyId(), keywords,
			WorkflowConstants.STATUS_APPROVED, params);

		jsonObject.put("count", usersCount);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (User user : users) {
			JSONObject userJSONObject = JSONFactoryUtil.createJSONObject();

			userJSONObject.put("emailAddress", user.getEmailAddress());
			userJSONObject.put("firstName", user.getFirstName());
			userJSONObject.put("fullName", user.getFullName());
			userJSONObject.put("jobTitle", user.getJobTitle());
			userJSONObject.put("lastName", user.getLastName());
			userJSONObject.put(
				"portraitURL", user.getPortraitURL(themeDisplay));
			userJSONObject.put("userId", String.valueOf(user.getUserId()));

			LiferayPortletResponse liferayPortletResponse =
				(LiferayPortletResponse)resourceResponse;

			PortletURL viewSummaryURL =
				liferayPortletResponse.createRenderURL();

			viewSummaryURL.setWindowState(LiferayWindowState.EXCLUSIVE);

			viewSummaryURL.setParameter(
				"mvcPath", "/contacts_center/view_resources.jsp");
			viewSummaryURL.setParameter(
				"userId", String.valueOf(user.getUserId()));

			userJSONObject.put("viewSummaryURL", viewSummaryURL.toString());

			jsonArray.put(userJSONObject);
		}

		jsonObject.put("users", jsonArray);

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		try {
			String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

			if (cmd.equals("addSocialRelation")) {
				addSocialRelation(actionRequest, actionResponse);
			}
			else if (cmd.equals("deleteSocialRelation")) {
				deleteSocialRelation(actionRequest, actionResponse);
			}
			else if (cmd.equals("requestSocialRelation")) {
				requestSocialRelation(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		sendRedirect(actionRequest, actionResponse);
	}

	public void requestSocialRelation(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] userIds = getUserIds(actionRequest);

		int type = ParamUtil.getInteger(actionRequest, "type");

		for (long userId : userIds) {
			if (userId == themeDisplay.getUserId()) {
				continue;
			}

			if (SocialRelationLocalServiceUtil.hasRelation(
					userId, themeDisplay.getUserId(),
					SocialRelationConstants.TYPE_UNI_ENEMY)) {

				continue;
			}

			SocialRequest socialRequest =
				SocialRequestLocalServiceUtil.addRequest(
					themeDisplay.getUserId(), 0, User.class.getName(),
					themeDisplay.getUserId(), type, StringPool.BLANK, userId);

			sendNotificationEvent(socialRequest, themeDisplay);
		}
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		try {
			String id = resourceRequest.getResourceID();

			if (id.equals("exportVCard")) {
				exportVCard(resourceRequest, resourceResponse);
			}
			else if (id.equals("exportVCards")) {
				exportVCards(resourceRequest, resourceResponse);
			}
			else if (id.equals("getContact")) {
				getContact(resourceRequest, resourceResponse);
			}
			else if (id.equals("getContacts")) {
				getContacts(resourceRequest, resourceResponse);
			}
			else {
				super.serveResource(resourceRequest, resourceResponse);
			}
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	public void updateSocialRequest(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long requestId = ParamUtil.getLong(actionRequest, "requestId");
		int status = ParamUtil.getInteger(actionRequest, "status");

		SocialRequest socialRequest =
			SocialRequestLocalServiceUtil.getSocialRequest(requestId);

		if (SocialRelationLocalServiceUtil.hasRelation(
				socialRequest.getReceiverUserId(), socialRequest.getUserId(),
				SocialRelationConstants.TYPE_UNI_ENEMY)) {

			status = SocialRequestConstants.STATUS_IGNORE;
		}

		SocialRequestLocalServiceUtil.updateRequest(
			requestId, status, themeDisplay);

		String notificationEventUuid = ParamUtil.getString(
			actionRequest, "notificationEventUuid");

		ChannelHubManagerUtil.confirmDelivery(
			themeDisplay.getCompanyId(), themeDisplay.getUserId(),
			notificationEventUuid, false);
	}

	protected long[] getUserIds(ActionRequest actionRequest) {
		long[] userIds;

		long userId = ParamUtil.getLong(actionRequest, "userId", 0);

		if (userId > 0) {
			userIds = new long[] {userId};
		}
		else {
			userIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "userIds"), 0L);
		}

		return userIds;
	}

	protected void sendNotificationEvent(
			SocialRequest socialRequest, ThemeDisplay themeDisplay)
		throws Exception {

		JSONObject notificationEventJSONObject =
			JSONFactoryUtil.createJSONObject();

		SocialRequestFeedEntry socialRequestFeedEntry =
			SocialRequestInterpreterLocalServiceUtil.interpret(
				socialRequest, themeDisplay);

		notificationEventJSONObject.put("portletId", "1_WAR_contactsportlet");
		notificationEventJSONObject.put(
			"requestId", socialRequest.getRequestId());
		notificationEventJSONObject.put(
			"title", socialRequestFeedEntry.getTitle());
		notificationEventJSONObject.put("userId", socialRequest.getUserId());

		NotificationEvent notificationEvent =
			NotificationEventFactoryUtil.createNotificationEvent(
				System.currentTimeMillis(), "6_WAR_soportlet",
				notificationEventJSONObject);

		notificationEvent.setDeliveryRequired(0);

		ChannelHubManagerUtil.sendNotificationEvent(
			socialRequest.getCompanyId(), socialRequest.getReceiverUserId(),
			notificationEvent);
	}

}
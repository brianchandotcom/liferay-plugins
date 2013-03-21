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

package com.liferay.knowledgebase.admin.social;

import com.liferay.knowledgebase.model.KBArticle;
import com.liferay.knowledgebase.model.KBComment;
import com.liferay.knowledgebase.model.KBTemplate;
import com.liferay.knowledgebase.service.KBArticleLocalServiceUtil;
import com.liferay.knowledgebase.service.KBCommentLocalServiceUtil;
import com.liferay.knowledgebase.service.KBTemplateLocalServiceUtil;
import com.liferay.knowledgebase.service.permission.KBArticlePermission;
import com.liferay.knowledgebase.service.permission.KBTemplatePermission;
import com.liferay.knowledgebase.util.ActionKeys;
import com.liferay.knowledgebase.util.KnowledgeBaseUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Peter Shin
 * @author Brian Wing Shun Chan
 */
public class AdminActivityInterpreter extends BaseSocialActivityInterpreter {

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected String getLink(
			SocialActivity activity, ServiceContext serviceContext)
		throws Exception {

		HttpServletRequest request = serviceContext.getRequest();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String className = activity.getClassName();

		if (className.equals(KBArticle.class.getName())) {
			return getLinkKBArticle(activity, themeDisplay);
		}
		else if (className.equals(KBComment.class.getName())) {
			return getLinkKBComment(activity, themeDisplay);
		}
		else if (className.equals(KBTemplate.class.getName())) {
			return getLinkKBTemplate(activity, themeDisplay);
		}

		return StringPool.BLANK;
	}

	protected String getLinkKBArticle(
			SocialActivity activity, ThemeDisplay themeDisplay)
		throws Exception {

		KBArticle kbArticle = KBArticleLocalServiceUtil.getLatestKBArticle(
			activity.getClassPK(), WorkflowConstants.STATUS_APPROVED);

		return KnowledgeBaseUtil.getKBArticleURL(
			themeDisplay.getPlid(), kbArticle.getResourcePrimKey(),
			kbArticle.getStatus(), themeDisplay.getPortalURL(), false);
	}

	protected String getLinkKBComment(
			SocialActivity activity, ThemeDisplay themeDisplay)
		throws Exception {

		KBComment kbComment = KBCommentLocalServiceUtil.getKBComment(
			activity.getClassPK());

		String className = kbComment.getClassName();

		if (!className.equals(KBArticle.class.getName())) {
			return StringPool.BLANK;
		}

		KBArticle kbArticle = KBArticleLocalServiceUtil.getLatestKBArticle(
			kbComment.getClassPK(), WorkflowConstants.STATUS_APPROVED);

		if (kbArticle == null) {
			return StringPool.BLANK;
		}

		return KnowledgeBaseUtil.getKBArticleURL(
			themeDisplay.getPlid(), kbArticle.getResourcePrimKey(),
			kbArticle.getStatus(), themeDisplay.getPortalURL(), false);
	}

	protected String getLinkKBTemplate(
			SocialActivity activity, ThemeDisplay themeDisplay)
		throws Exception {

		return StringPool.BLANK;
	}

	@Override
	protected Object[] getTitleArguments(
		String groupName, SocialActivity activity, String link, String title,
		ServiceContext serviceContext) {

		String className = activity.getClassName();

		if (className.equals(KBArticle.class.getName())) {
			return getTitleArgumentsKBArticle(
				groupName, activity, link, title, serviceContext);
		}
		else if (className.equals(KBComment.class.getName())) {
			return getTitleArgumentsKBComment(
				groupName, activity, link, title, serviceContext);
		}
		else if (className.equals(KBTemplate.class.getName())) {
			return getTitleArgumentsKBTemplate(
				groupName, activity, link, title, serviceContext);
		}

		return new Object[0];

	}

	protected Object[] getTitleArgumentsKBArticle(
		String groupName, SocialActivity activity, String link, String title,
		ServiceContext serviceContext) {

		try {
			HttpServletRequest request = serviceContext.getRequest();

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			String creatorUserName = getUserName(
				activity.getUserId(), themeDisplay);

			KBArticle kbArticle = KBArticleLocalServiceUtil.getLatestKBArticle(
				activity.getClassPK(), WorkflowConstants.STATUS_APPROVED);

			String articleTitle = getJSONValue(
				activity.getExtraData(), "title", kbArticle.getTitle());

			return new Object[] {
				creatorUserName, wrapLink(link, articleTitle), groupName
			};
		}
		catch (Exception e) {
			return new Object[0];
		}
	}

	protected Object[] getTitleArgumentsKBComment(
		String groupName, SocialActivity activity, String link, String title,
		ServiceContext serviceContext) {

		try {
			HttpServletRequest request = serviceContext.getRequest();

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			String creatorUserName = getUserName(
				activity.getUserId(), themeDisplay);

			KBComment kbComment = KBCommentLocalServiceUtil.getKBComment(
				activity.getClassPK());

			KBArticle kbArticle = null;
			KBTemplate kbTemplate = null;

			String className = kbComment.getClassName();

			if (className.equals(KBArticle.class.getName())) {
				kbArticle = KBArticleLocalServiceUtil.getLatestKBArticle(
					kbComment.getClassPK(), WorkflowConstants.STATUS_APPROVED);
			}
			else if (className.equals(KBTemplate.class.getName())) {
				kbTemplate = KBTemplateLocalServiceUtil.getKBTemplate(
					kbComment.getClassPK());
			}

			String entityTitle = null;

			if (kbArticle != null) {
				entityTitle = getJSONValue(
					activity.getExtraData(), "title", kbArticle.getTitle());
			}
			else if (kbTemplate != null) {
				entityTitle = getJSONValue(
					activity.getExtraData(), "title", kbTemplate.getTitle());
			}

			return new Object[] {
				creatorUserName, wrapLink(link, entityTitle), groupName
			};
		}
		catch (Exception e) {
			return new Object[0];
		}
	}

	protected Object[] getTitleArgumentsKBTemplate(
		String groupName, SocialActivity activity, String link, String title,
		ServiceContext serviceContext) {

		try {
			HttpServletRequest request = serviceContext.getRequest();

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			String creatorUserName = getUserName(
				activity.getUserId(), themeDisplay);

			KBTemplate kbTemplate = KBTemplateLocalServiceUtil.getKBTemplate(
				activity.getClassPK());

			String articleTitle = getJSONValue(
				activity.getExtraData(), "title", kbTemplate.getTitle());

			return new Object[] {creatorUserName, articleTitle, groupName};
		}
		catch (Exception e) {
			return new Object[0];
		}
	}

	@Override
	protected String getTitlePattern(
		String groupName, SocialActivity activity) {

		String className = activity.getClassName();

		if (className.equals(KBArticle.class.getName())) {
			return getTitlePatternKBArticle(groupName, activity);
		}
		else if (className.equals(KBComment.class.getName())) {
			return getTitlePatternKBComment(groupName, activity);
		}
		else if (className.equals(KBTemplate.class.getName())) {
			return getTitlePatternKBTemplate(groupName, activity);
		}

		return StringPool.BLANK;
	}

	protected String getTitlePatternKBArticle(
		String groupName, SocialActivity activity) {

		if (activity.getType() == AdminActivityKeys.ADD_KB_ARTICLE) {
			if (Validator.isNull(groupName)) {
				return "activity-knowledge-base-admin-add-kb-article";
			}
			else {
				return "activity-knowledge-base-admin-add-kb-article-in";
			}
		}
		else if (activity.getType() == AdminActivityKeys.MOVE_KB_ARTICLE) {
			if (Validator.isNull(groupName)) {
				return "activity-knowledge-base-admin-move-kb-article";
			}
			else {
				return "activity-knowledge-base-admin-move-kb-article-in";
			}
		}
		else if (activity.getType() == AdminActivityKeys.UPDATE_KB_ARTICLE) {
			if (Validator.isNull(groupName)) {
				return "activity-knowledge-base-admin-update-kb-article";
			}
			else {
				return "activity-knowledge-base-admin-update-kb-article-in";
			}
		}

		return StringPool.BLANK;
	}

	protected String getTitlePatternKBComment(
		String groupName, SocialActivity activity) {

		if (activity.getType() == AdminActivityKeys.ADD_KB_COMMENT) {
			if (Validator.isNull(groupName)) {
				return "activity-knowledge-base-admin-add-kb-comment";
			}
			else {
				return "activity-knowledge-base-admin-add-kb-comment-in";
			}
		}
		else if (activity.getType() ==
			AdminActivityKeys.UPDATE_KB_COMMENT) {

			if (Validator.isNull(groupName)) {
				return "activity-knowledge-base-admin-update-kb-comment";
			}
			else {
				return "activity-knowledge-base-admin-update-kb-comment-in";
			}
		}

		return StringPool.BLANK;
	}

	protected String getTitlePatternKBTemplate(
		String groupName, SocialActivity activity) {

		if (activity.getType() == AdminActivityKeys.ADD_KB_TEMPLATE) {
			if (Validator.isNull(groupName)) {
				return "activity-knowledge-base-admin-add-kb-template";
			}
			else {
				return "activity-knowledge-base-admin-add-kb-template-in";
			}
		}
		else if (activity.getType() == AdminActivityKeys.UPDATE_KB_TEMPLATE) {
			if (Validator.isNull(groupName)) {
				return "activity-knowledge-base-admin-update-kb-template";
			}
			else {
				return "activity-knowledge-base-admin-update-kb-template-in";
			}
		}

		return StringPool.BLANK;
	}

	@Override
	protected boolean hasPermissions(
			PermissionChecker permissionChecker, SocialActivity activity,
			String actionId, ThemeDisplay themeDisplay)
		throws Exception {

		String className = activity.getClassName();

		if (className.equals(KBArticle.class.getName())) {
			return hasPermissionsKBArticle(
				permissionChecker, activity, actionId, themeDisplay);
		}
		else if (className.equals(KBComment.class.getName())) {
			return hasPermissionsKBComment(
				permissionChecker, activity, actionId, themeDisplay);
		}
		else if (className.equals(KBTemplate.class.getName())) {
			return hasPermissionsKBTemplate(
				permissionChecker, activity, actionId, themeDisplay);
		}

		return false;
	}

	protected boolean hasPermissionsKBArticle(
			PermissionChecker permissionChecker, SocialActivity activity,
			String actionId, ThemeDisplay themeDisplay)
		throws Exception {

		KBArticle kbArticle = KBArticleLocalServiceUtil.getLatestKBArticle(
			activity.getClassPK(), WorkflowConstants.STATUS_APPROVED);

		return KBArticlePermission.contains(
			permissionChecker, kbArticle, ActionKeys.VIEW);
	}

	protected boolean hasPermissionsKBComment(
			PermissionChecker permissionChecker, SocialActivity activity,
			String actionId, ThemeDisplay themeDisplay)
		throws Exception {

		return true;
	}

	protected boolean hasPermissionsKBTemplate(
			PermissionChecker permissionChecker, SocialActivity activity,
			String actionId, ThemeDisplay themeDisplay)
		throws Exception {

		KBTemplate kbTemplate = KBTemplateLocalServiceUtil.getKBTemplate(
			activity.getClassPK());

		return KBTemplatePermission.contains(
			permissionChecker, kbTemplate, ActionKeys.VIEW);
	}

	private static final String[] _CLASS_NAMES = new String[] {
		KBArticle.class.getName(), KBComment.class.getName(),
		KBTemplate.class.getName()
	};

}
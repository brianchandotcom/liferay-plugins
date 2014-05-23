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

package com.liferay.defaultwebcontent.hook.upgrade.v1_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.journal.util.JournalConverterUtil;

import java.io.InputStream;

import java.util.Locale;

/**
 * @author Ryan Park
 */
public class UpgradeJournal extends UpgradeProcess {

	protected void addDDMStructures(DDMHelper ddmHelper) throws Exception {

		// Article

		String description =
			"This structure accommodates article title, both main, and " +
				"preview images, and the main article body.";

		String xsd = getDDMStructureXSDAsString("/structures/article.xml");

		ddmHelper.addStructure("ARTICLE", "Article", description, xsd);

		// Carousel

		description =
			"This is a simple carousel structure designed to handle other " +
				"necessary carousel configurations.";

		xsd = getDDMStructureXSDAsString(
			"/structures/multiple_item_carousel.xml");

		ddmHelper.addStructure(
			"MULTIPLE-ITEM-CAROUSEL", "Carousel", description, xsd);

		// Multiple Item

		description =
			"This is a simple structure with a single repeatable element " +
				"that includes an HTML field, and text-box for a title and " +
					"URL designation.";

		xsd = getDDMStructureXSDAsString("/structures/multiple_item.xml");

		ddmHelper.addStructure(
			"MULTIPLE-ITEM", "Multiple Item", description, xsd);
	}

	protected void addDDMTemplates(DDMHelper ddmHelper) throws Exception {

		// Regular Article Description

		String description =
			"This template only displays brief descriptions of web content";

		String script = getFileAsString("/templates/article_description.vm");

		ddmHelper.addTemplate(
			"ARTICLE-DESCRIPTION", "ARTICLE", "Regular Article Description",
			description, script);

		// Regular Article

		description =
			"This is the regular article template, it handles basic article " +
				"content like, titles, main image, body, and author " +
					"information.";

		script = getFileAsString("/templates/regular_article.vm");

		ddmHelper.addTemplate(
			"REGULAR-ARTICLE", "ARTICLE", "Regular Article", description,
			script);

		// Carousel

		description =
			"This is the carousel template that utilizes Alloy UI to display " +
				"repeatable content as a slideshow.";

		script = getFileAsString("/templates/multiple_item_carousel.vm");

		ddmHelper.addTemplate(
			"MULTIPLE-ITEM-CAROUSEL", "MULTIPLE-ITEM-CAROUSEL", "Carousel",
			description, script);

		// Featured Items

		description =
			"This is a template that utilizes the Multiple Item Structure, " +
				"and displays the data as Featured Items.";

		script = getFileAsString("/templates/multiple_item_feature.vm");

		ddmHelper.addTemplate(
			"MULTIPLE-ITEM-FEATURE", "MULTIPLE-ITEM", "Featured Items",
			description, script);
	}

	@Override
	protected void doUpgrade() throws Exception {
		long companyId = PortalUtil.getDefaultCompanyId();

		Group group = GroupLocalServiceUtil.getCompanyGroup(companyId);

		long groupId = group.getGroupId();
		long userId = UserLocalServiceUtil.getDefaultUserId(companyId);

		String languageId = UpgradeProcessUtil.getDefaultLanguageId(companyId);

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		DDMHelper ddmHelper = new DDMHelper(groupId, userId, locale);

		addDDMStructures(ddmHelper);
		addDDMTemplates(ddmHelper);
	}

	protected String getDDMStructureXSDAsString(String path) throws Exception {
		String xsd = getFileAsString(path);

		return JournalConverterUtil.getDDMXSD(xsd);
	}

	protected String getFileAsString(String path) throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();

		InputStream is = classLoader.getResourceAsStream("/resources" + path);

		return new String(FileUtil.getBytes(is));
	}

}
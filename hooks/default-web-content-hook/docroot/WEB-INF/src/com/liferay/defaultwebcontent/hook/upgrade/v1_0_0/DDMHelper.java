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

import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public class DDMHelper {

	public DDMHelper(long groupId, long userId, Locale locale) {
		_groupId = groupId;
		_userId = userId;
		_locale = locale;

		setUpClassNameIds();
		setUpServiceContext();
	}

	public void addStructure(
			String structureKey, String name, String description, String xsd)
		throws Exception {

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.addStructure(
			_userId, _groupId, 0, _journalArticleClassNameId, structureKey,
			getMap(name), getMap(description), xsd, "xml",
			DDMStructureConstants.TYPE_DEFAULT, _serviceContext);

		_ddmStructureIds.put(structureKey, ddmStructure.getStructureId());
	}

	public void addTemplate(
			String templateKey, String structureKey, String name,
			String description, String script)
		throws Exception {

		long classPK = _ddmStructureIds.get(structureKey);

		DDMTemplateLocalServiceUtil.addTemplate(
			_userId, _groupId, _ddmStructureClassNameId, classPK, templateKey,
			getMap(name), getMap(description),
			DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY,
			DDMTemplateConstants.TEMPLATE_MODE_CREATE,
			TemplateConstants.LANG_TYPE_VM, script, true, false,
			StringPool.BLANK, null, _serviceContext);
	}

	protected Map<Locale, String> getMap(String value) {
		Map<Locale, String> map = new HashMap<Locale, String>();

		map.put(_locale, value);

		return map;
	}

	protected void setUpClassNameIds() {
		_ddmStructureClassNameId = PortalUtil.getClassNameId(
			DDMStructure.class);
		_journalArticleClassNameId = PortalUtil.getClassNameId(
			JournalArticle.class);
	}

	protected void setUpServiceContext() {
		_serviceContext = new ServiceContext();

		_serviceContext.setAddGroupPermissions(true);
		_serviceContext.setAddGuestPermissions(true);
	}

	private long _ddmStructureClassNameId;
	private Map<String, Long> _ddmStructureIds = new HashMap<String, Long>();
	private long _groupId;
	private long _journalArticleClassNameId;
	private Locale _locale;
	private ServiceContext _serviceContext;
	private long _userId;

}
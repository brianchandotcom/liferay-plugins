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

package com.liferay.polls.lar;

import com.liferay.polls.NoSuchQuestionException;
import com.liferay.polls.model.PollsChoice;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.model.PollsVote;
import com.liferay.polls.service.persistence.PollsQuestionUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Marcellus Tavares
 */
public class PollsDisplayPortletDataHandler extends PollsPortletDataHandler {

	public PollsDisplayPortletDataHandler() {
		setDataPortletPreferences("pollsQuestionId");
		setPublishToLiveByDefault(true);
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletPreferences == null) {
			return portletPreferences;
		}

		portletPreferences.setValue("pollsQuestionId", StringPool.BLANK);

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		long pollsQuestionId = GetterUtil.getLong(
			portletPreferences.getValue("pollsQuestionId", StringPool.BLANK));

		if (pollsQuestionId <= 0) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No polls question id found in preferences of portlet " +
						portletId);
			}

			return StringPool.BLANK;
		}

		PollsQuestion pollsQuestion = null;

		try {
			pollsQuestion = PollsQuestionUtil.findByPrimaryKey(pollsQuestionId);
		}
		catch (NoSuchQuestionException nsqe) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsqe, nsqe);
			}

			return StringPool.BLANK;
		}

		portletDataContext.addPermissions(
			"com.liferay.polls", portletDataContext.getScopeGroupId());

		Element rootElement = addExportRootElement();

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		Element pollsQuestionsElement = rootElement.addElement(
			"pollsQuestions");
		Element pollsChoicesElement = rootElement.addElement("pollsChoices");
		Element pollsVotesElement = rootElement.addElement("pollsVotes");

		PollsPortletDataHandler.exportPollsQuestion(
			portletDataContext, pollsQuestionsElement, pollsChoicesElement,
			pollsVotesElement, pollsQuestion);

		return rootElement.formattedString();
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			"com.liferay.polls", portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		if (Validator.isNull(data)) {
			return null;
		}

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element pollsQuestionsElement = rootElement.element("pollsQuestions");

		for (Element pollsQuestionElement :
				pollsQuestionsElement.elements("pollsQuestion")) {

			String path = pollsQuestionsElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			PollsQuestion pollsQuestion =
				(PollsQuestion)portletDataContext.getZipEntryAsObject(path);

			PollsPortletDataHandler.importPollsQuestion(
				portletDataContext, pollsQuestionElement, pollsQuestion);
		}

		Element pollsChoicesElement = rootElement.element("pollsChoices");

		for (Element pollsChoiceElement :
				pollsChoicesElement.elements("pollsChoice")) {

			String path = pollsChoiceElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			PollsChoice pollsChoice =
				(PollsChoice)portletDataContext.getZipEntryAsObject(path);

			PollsPortletDataHandler.importPollsChoice(
				portletDataContext, pollsChoice);
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "pollsVotes")) {
			Element pollsVotesElement = rootElement.element("pollsVotes");

			for (Element pollsVoteElement :
					pollsVotesElement.elements("pollsVote")) {

				String path = pollsVoteElement.attributeValue("path");

				if (!portletDataContext.isPathNotProcessed(path)) {
					continue;
				}

				PollsVote pollsVote =
					(PollsVote)portletDataContext.getZipEntryAsObject(path);

				PollsPortletDataHandler.importPollsVote(
					portletDataContext, pollsVote);
			}
		}

		long pollsQuestionId = GetterUtil.getLong(
			portletPreferences.getValue("pollsQuestionId", StringPool.BLANK));

		if (pollsQuestionId > 0) {
			Map<Long, Long> pollsQuestionIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					PollsQuestion.class);

			pollsQuestionId = MapUtil.getLong(
				pollsQuestionIds, pollsQuestionId, pollsQuestionId);

			portletPreferences.setValue(
				"pollsQuestionId", String.valueOf(pollsQuestionId));
		}

		return portletPreferences;
	}

	private static Log _log = LogFactoryUtil.getLog(
		PollsDisplayPortletDataHandler.class);

}
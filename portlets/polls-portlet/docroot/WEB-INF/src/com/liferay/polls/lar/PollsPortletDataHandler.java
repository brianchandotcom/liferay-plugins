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

import com.liferay.polls.DuplicatePollsVoteException;
import com.liferay.polls.model.PollsChoice;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.model.PollsVote;
import com.liferay.polls.service.PollsChoiceLocalServiceUtil;
import com.liferay.polls.service.PollsQuestionLocalServiceUtil;
import com.liferay.polls.service.PollsVoteLocalServiceUtil;
import com.liferay.polls.service.persistence.PollsChoiceFinderUtil;
import com.liferay.polls.service.persistence.PollsChoiceUtil;
import com.liferay.polls.service.persistence.PollsQuestionUtil;
import com.liferay.polls.service.persistence.PollsVoteUtil;
import com.liferay.polls.util.PortletKeys;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceContext;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Bruno Farache
 * @author Marcellus Tavares
 * @author Juan Fernández
 */
public class PollsPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "polls";

	public PollsPortletDataHandler() {
		setAlwaysExportable(true);
		setDataLocalized(true);
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "questions", true, true),
			new PortletDataHandlerBoolean(NAMESPACE, "votes"));
	}

	protected static void exportPollsChoice(
			PortletDataContext portletDataContext,
			Element pollsQuestionsElement, PollsChoice pollsChoice)
		throws Exception {

		String path = getPollsChoicePath(portletDataContext, pollsChoice);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element choiceElement = pollsQuestionsElement.addElement("pollsChoice");

		portletDataContext.addClassedModel(
			choiceElement, path, pollsChoice, NAMESPACE);
	}

	protected static void exportPollsQuestion(
			PortletDataContext portletDataContext,
			Element pollsQuestionsElement, Element choicesElement,
			Element pollsVotesElement, PollsQuestion pollsQuestion)
		throws Exception {

		if (!portletDataContext.isWithinDateRange(
			pollsQuestion.getModifiedDate())) {

			return;
		}

		String path = getPollsQuestionPath(portletDataContext, pollsQuestion);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element pollsQuestionElement = pollsQuestionsElement.addElement(
			"pollsQuestion");

		List<PollsChoice> pollsChoices = PollsChoiceUtil.findByPollsQuestionId(
			pollsQuestion.getPollsQuestionId());

		for (PollsChoice pollsChoice : pollsChoices) {
			exportPollsChoice(portletDataContext, choicesElement, pollsChoice);
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "pollsVotes")) {
			List<PollsVote> pollsVotes = PollsVoteUtil.findByPollsQuestionId(
				pollsQuestion.getPollsQuestionId());

			for (PollsVote pollsVote : pollsVotes) {
				exportPollsVote(
					portletDataContext, pollsVotesElement, pollsVote);
			}
		}

		portletDataContext.addClassedModel(
			pollsQuestionElement, path, pollsQuestion, NAMESPACE);
	}

	protected static void exportPollsVote(
			PortletDataContext portletDataContext,
			Element pollsQuestionsElement, PollsVote pollsVote)
		throws Exception {

		String path = getPollsVotePath(portletDataContext, pollsVote);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element pollsVoteElement = pollsQuestionsElement.addElement(
			"pollsVote");

		portletDataContext.addClassedModel(
			pollsVoteElement, path, pollsVote, NAMESPACE);
	}

	protected static String getPollsChoicePath(
		PortletDataContext portletDataContext, PollsChoice pollsChoice) {

		StringBundler sb = new StringBundler(6);

		sb.append(portletDataContext.getPortletPath(PortletKeys.POLLS));
		sb.append("/pollsQuestions/");
		sb.append(pollsChoice.getPollsQuestionId());
		sb.append("/pollsChoices/");
		sb.append(pollsChoice.getPollsChoiceId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getPollsQuestionPath(
		PortletDataContext portletDataContext, PollsQuestion pollsQuestion) {

		StringBundler sb = new StringBundler(4);

		sb.append(portletDataContext.getPortletPath(PortletKeys.POLLS));
		sb.append("/pollsQuestions/");
		sb.append(pollsQuestion.getPollsQuestionId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getPollsVotePath(
		PortletDataContext portletDataContext, PollsVote pollsVote) {

		StringBundler sb = new StringBundler(6);

		sb.append(portletDataContext.getPortletPath(PortletKeys.POLLS));
		sb.append("/pollsQuestions/");
		sb.append(pollsVote.getPollsQuestionId());
		sb.append("/pollsVotes/");
		sb.append(pollsVote.getPollsVoteId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static void importPollsChoice(
			PortletDataContext portletDataContext, PollsChoice pollsChoice)
		throws Exception {

		Map<Long, Long> pollsQuestionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				PollsQuestion.class);

		long pollsQuestionId = MapUtil.getLong(
			pollsQuestionIds, pollsChoice.getPollsQuestionId(),
			pollsChoice.getPollsQuestionId());

		PollsChoice importedPollsChoice = null;

		if (portletDataContext.isDataStrategyMirror()) {
			PollsChoice existingPollsChoice =
				PollsChoiceFinderUtil.fetchByUUID_G(
					pollsChoice.getUuid(),
					portletDataContext.getScopeGroupId());

			if (existingPollsChoice == null) {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setUuid(pollsChoice.getUuid());

				importedPollsChoice =
					PollsChoiceLocalServiceUtil.addPollsChoice(
						pollsQuestionId, pollsChoice.getName(),
						pollsChoice.getDescription(), serviceContext);
			}
			else {
				importedPollsChoice =
					PollsChoiceLocalServiceUtil.updatePollsChoice(
						existingPollsChoice.getPollsChoiceId(), pollsQuestionId,
						pollsChoice.getName(), pollsChoice.getDescription());
			}
		}
		else {
			importedPollsChoice = PollsChoiceLocalServiceUtil.addPollsChoice(
				pollsQuestionId, pollsChoice.getName(),
				pollsChoice.getDescription(), new ServiceContext());
		}

		portletDataContext.importClassedModel(
			pollsChoice, importedPollsChoice, NAMESPACE);
	}

	protected static void importPollsQuestion(
			PortletDataContext portletDataContext, Element pollsQuestionElement,
			PollsQuestion pollsQuestion)
		throws Exception {

		long userId = portletDataContext.getUserId(pollsQuestion.getUserUuid());

		Date expirationDate = pollsQuestion.getExpirationDate();

		int expirationMonth = 0;
		int expirationDay = 0;
		int expirationYear = 0;
		int expirationHour = 0;
		int expirationMinute = 0;
		boolean neverExpire = true;

		if (expirationDate != null) {
			Calendar expirationCal = CalendarFactoryUtil.getCalendar();

			expirationCal.setTime(expirationDate);

			expirationMonth = expirationCal.get(Calendar.MONTH);
			expirationDay = expirationCal.get(Calendar.DATE);
			expirationYear = expirationCal.get(Calendar.YEAR);
			expirationHour = expirationCal.get(Calendar.HOUR);
			expirationMinute = expirationCal.get(Calendar.MINUTE);
			neverExpire = false;

			if (expirationCal.get(Calendar.AM_PM) == Calendar.PM) {
				expirationHour += 12;
			}
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			pollsQuestionElement, pollsQuestion, NAMESPACE);

		PollsQuestion importedPollsQuestion = null;

		if (portletDataContext.isDataStrategyMirror()) {
			PollsQuestion existingPollsQuestion =
				PollsQuestionUtil.fetchByUUID_G(
					pollsQuestion.getUuid(),
					portletDataContext.getScopeGroupId());

			if (existingPollsQuestion == null) {
				serviceContext.setUuid(pollsQuestion.getUuid());

				importedPollsQuestion =
					PollsQuestionLocalServiceUtil.addPollsQuestion(
						userId, pollsQuestion.getTitleMap(),
						pollsQuestion.getDescriptionMap(), expirationMonth,
						expirationDay, expirationYear, expirationHour,
						expirationMinute, neverExpire, null, serviceContext);
			}
			else {
				importedPollsQuestion =
					PollsQuestionLocalServiceUtil.updatePollsQuestion(
						userId, existingPollsQuestion.getPollsQuestionId(),
						pollsQuestion.getTitleMap(),
						pollsQuestion.getDescriptionMap(), expirationMonth,
						expirationDay, expirationYear, expirationHour,
						expirationMinute, neverExpire, null, serviceContext);
			}
		}
		else {
			importedPollsQuestion =
				PollsQuestionLocalServiceUtil.addPollsQuestion(
					userId, pollsQuestion.getTitleMap(),
					pollsQuestion.getDescriptionMap(), expirationMonth,
					expirationDay, expirationYear, expirationHour,
					expirationMinute, neverExpire, null, serviceContext);
		}

		portletDataContext.importClassedModel(
			pollsQuestion, importedPollsQuestion, NAMESPACE);
	}

	protected static void importPollsVote(
			PortletDataContext portletDataContext, PollsVote pollsVote)
		throws Exception {

		Map<Long, Long> pollsQuestionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				PollsQuestion.class);

		long pollsQuestionId = MapUtil.getLong(
			pollsQuestionIds, pollsVote.getPollsQuestionId(),
			pollsVote.getPollsQuestionId());

		Map<Long, Long> choiceIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				PollsChoice.class);

		long choiceId = MapUtil.getLong(
			choiceIds, pollsVote.getPollsChoiceId(),
			pollsVote.getPollsChoiceId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCreateDate(pollsVote.getVoteDate());

		try {
			PollsVoteLocalServiceUtil.addPollsVote(
				pollsVote.getUserId(), pollsQuestionId, choiceId,
				serviceContext);
		}
		catch (DuplicatePollsVoteException dve) {
		}
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				PollsPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		PollsQuestionLocalServiceUtil.deletePollsQuestions(
			portletDataContext.getScopeGroupId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			"com.liferay.polls", portletDataContext.getScopeGroupId());

		Element rootElement = addExportRootElement();

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		Element pollsQuestionsElement = rootElement.addElement(
			"pollsQuestions");
		Element choicesElement = rootElement.addElement("pollsChoices");
		Element pollsVotesElement = rootElement.addElement("pollsVotes");

		List<PollsQuestion> pollsQuestions = PollsQuestionUtil.findByGroupId(
			portletDataContext.getScopeGroupId());

		for (PollsQuestion pollsQuestion : pollsQuestions) {
			exportPollsQuestion(
				portletDataContext, pollsQuestionsElement, choicesElement,
				pollsVotesElement, pollsQuestion);
		}

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

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element pollsQuestionsElement = rootElement.element("pollsQuestions");

		for (Element pollsQuestionElement :
				pollsQuestionsElement.elements("pollsQuestion")) {
			String path = pollsQuestionElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			PollsQuestion pollsQuestion =
				(PollsQuestion)portletDataContext.getZipEntryAsObject(path);

			importPollsQuestion(
				portletDataContext, pollsQuestionElement, pollsQuestion);
		}

		Element choicesElement = rootElement.element("pollsChoices");

		for (Element choiceElement : choicesElement.elements("pollsChoice")) {
			String path = choiceElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			PollsChoice pollsChoice =
				(PollsChoice)portletDataContext.getZipEntryAsObject(path);

			importPollsChoice(portletDataContext, pollsChoice);
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

				importPollsVote(portletDataContext, pollsVote);
			}
		}

		return null;
	}

}
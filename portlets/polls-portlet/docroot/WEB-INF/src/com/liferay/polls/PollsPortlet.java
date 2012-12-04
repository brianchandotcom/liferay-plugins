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

package com.liferay.polls;

import com.liferay.polls.model.PollsChoice;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.service.PollsQuestionServiceUtil;
import com.liferay.polls.service.PollsVoteServiceUtil;
import com.liferay.polls.service.persistence.PollsChoiceUtil;
import com.liferay.polls.util.PollsKeys;
import com.liferay.polls.util.PollsUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.io.IOException;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.PieDataset;

/**
 * @author Juan Fernández
 */
public class PollsPortlet extends MVCPortlet {

	public void addAndStoreSelection(
			PortletConfig portletConfig, PortletRequest portletRequest,
			PollsQuestion question)
		throws Exception {

		String referringPortletResource = ParamUtil.getString(
			portletRequest, "referringPortletResource");

		if (Validator.isNull(referringPortletResource)) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = LayoutLocalServiceUtil.getLayout(
			themeDisplay.getRefererPlid());

		PortletPreferences preferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				layout, referringPortletResource, StringPool.BLANK);

		preferences.setValue(
			"questionId", String.valueOf(question.getQuestionId()));

		preferences.store();
	}

	public void deleteQuestion(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long questionId = ParamUtil.getLong(actionRequest, "questionId");

		PollsQuestionServiceUtil.deleteQuestion(questionId);
	}

	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException {

		try {
			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				resourceRequest);
			HttpServletResponse response = PortalUtil.getHttpServletResponse(
				resourceResponse);

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			long questionId = ParamUtil.getLong(request, "questionId");

			String chartType = ParamUtil.getString(request, "chartType", "pie");
			String chartName = themeDisplay.translate("vote-results");
			String xName = themeDisplay.translate("choice");
			String yName = themeDisplay.translate("votes");

			CategoryDataset categoryDataset = PollsUtil.getVotesDataset(
				questionId);

			JFreeChart jFreeChat = null;

			if (chartType.equals("area")) {
				jFreeChat = ChartFactory.createAreaChart(
					chartName, xName, yName, categoryDataset,
					PlotOrientation.VERTICAL, true, false, false);
			}
			else if (chartType.equals("horizontal_bar")) {
				jFreeChat = ChartFactory.createBarChart(
					chartName, xName, yName, categoryDataset,
					PlotOrientation.HORIZONTAL, true, false, false);
			}
			else if (chartType.equals("line")) {
				jFreeChat = ChartFactory.createLineChart(
					chartName, xName, yName, categoryDataset,
					PlotOrientation.VERTICAL, true, false, false);
			}
			else if (chartType.equals("vertical_bar")) {
				jFreeChat = ChartFactory.createBarChart(
					chartName, xName, yName, categoryDataset,
					PlotOrientation.VERTICAL, true, false, false);
			}
			else {
				PieDataset pieDataset = DatasetUtilities.createPieDatasetForRow(
					categoryDataset, 0);

				jFreeChat = ChartFactory.createPieChart(
					chartName, pieDataset, true, false, false);
			}

			response.setContentType(ContentTypes.IMAGE_JPEG);

			OutputStream outputStream = response.getOutputStream();

			ChartUtilities.writeChartAsJPEG(outputStream, jFreeChat, 400, 400);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateQuestion(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		PortletConfig portletConfig = (PortletConfig)actionRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		long questionId = ParamUtil.getLong(actionRequest, "questionId");

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		int expirationDateMonth = ParamUtil.getInteger(
			actionRequest, "expirationDateMonth");
		int expirationDateDay = ParamUtil.getInteger(
			actionRequest, "expirationDateDay");
		int expirationDateYear = ParamUtil.getInteger(
			actionRequest, "expirationDateYear");
		int expirationDateHour = ParamUtil.getInteger(
			actionRequest, "expirationDateHour");
		int expirationDateMinute = ParamUtil.getInteger(
			actionRequest, "expirationDateMinute");
		int expirationDateAmPm = ParamUtil.getInteger(
			actionRequest, "expirationDateAmPm");
		boolean neverExpire = ParamUtil.getBoolean(
			actionRequest, "neverExpire");

		if (expirationDateAmPm == Calendar.PM) {
			expirationDateHour += 12;
		}

		List<PollsChoice> choices = new ArrayList<PollsChoice>();

		Set<String> readParameters = new HashSet<String>();

		Enumeration<String> enu = actionRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String param = enu.nextElement();

			if (param.startsWith(PollsKeys.CHOICE_DESCRIPTION_PREFIX)) {
				try {
					String id = param.substring(
						PollsKeys.CHOICE_DESCRIPTION_PREFIX.length(),
						param.indexOf(CharPool.UNDERLINE));

					if (readParameters.contains(id)) {
						continue;
					}

					String choiceName = ParamUtil.getString(
						actionRequest, PollsKeys.CHOICE_NAME_PREFIX + id);

					Map<Locale, String> localeChoiceDescriptionMap =
						LocalizationUtil.getLocalizationMap(
							actionRequest,
								PollsKeys.CHOICE_DESCRIPTION_PREFIX + id);

					PollsChoice choice = PollsChoiceUtil.create(0);

					choice.setName(choiceName);
					choice.setDescriptionMap(localeChoiceDescriptionMap);

					choices.add(choice);

					readParameters.add(id);
				}
				catch (Exception e) {
				}
			}
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			BookmarksEntry.class.getName(), actionRequest);

		if (questionId <= 0) {

			// Add question

			PollsQuestion question = PollsQuestionServiceUtil.addQuestion(
				titleMap, descriptionMap, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, choices, serviceContext);

			// Poll display

			addAndStoreSelection(portletConfig, actionRequest, question);
		}
		else {

			// Update question

			PollsQuestionServiceUtil.updateQuestion(
				questionId, titleMap, descriptionMap, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, choices, serviceContext);
		}
	}

	public void voteQuestion(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long questionId = ParamUtil.getLong(actionRequest, "questionId");
		long choiceId = ParamUtil.getLong(actionRequest, "choiceId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		PollsVoteServiceUtil.addVote(questionId, choiceId, serviceContext);

		PollsUtil.saveVote(actionRequest, actionResponse, questionId);
	}

}
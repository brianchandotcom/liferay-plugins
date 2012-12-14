<%--
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
--%>

<%@ include file="/polls/init.jsp" %>

<aui:form method="post" name="fm">

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("jspPage", "/polls/view_question.jsp");
	%>

	<liferay-ui:search-container
		curParam="<%= SearchContainer.DEFAULT_CUR_PARAM %>"
		delta="<%= SearchContainer.DEFAULT_DELTA %>"
		deltaConfigurable="<%= false %>"
		headerNames="question,num-of-votes,last-vote-date,expiration-date, "
		iteratorURL="<%= portletURL %>"
	>

		<liferay-ui:search-container-results
			results="<%= PollsQuestionLocalServiceUtil.getQuestions(scopeGroupId, searchContainer.getStart(), searchContainer.getEnd()) %>"
			total="<%= PollsQuestionLocalServiceUtil.getQuestionsCount(scopeGroupId) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.polls.model.PollsQuestion"
			escapedModel="<%= true %>"
			keyProperty="questionId"
			modelVar="question"
		>
			<liferay-portlet:renderURL varImpl="rowURL">
				<portlet:param name="jspPage" value="/polls/view_question.jsp" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="questionId" value="<%= String.valueOf(question.getQuestionId()) %>" />
			</liferay-portlet:renderURL>

			<!-- Title -->

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="question"
				orderable="<%= false %>"
				value="<%= question.getTitle(locale) %>"
			/>

			<%

			// Number of votes

			int votesCount = PollsVoteLocalServiceUtil.getQuestionVotesCount(question.getQuestionId());
			%>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="num-of-votes"
				orderable="<%= false %>"
				value="<%= String.valueOf(votesCount) %>"
			/>

			<%

			// Last vote date

			String lastVoteDate = StringPool.BLANK;

			if (question.getLastVoteDate() == null) {
				lastVoteDate = LanguageUtil.get(pageContext, "never");
			}
			else {
				lastVoteDate = dateFormatDateTime.format(question.getLastVoteDate());
			}
			%>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="last-vote-date"
				orderable="<%= false %>"
				value="<%= lastVoteDate %>"
			/>

			<%

			// Expiration date

			String expirationDate = StringPool.BLANK;

			if (question.getExpirationDate() == null) {
				expirationDate = LanguageUtil.get(pageContext, "never");
			}
			else {
				expirationDate = dateFormatDateTime.format(question.getExpirationDate());
			}
			%>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="expiration-date"
				orderable="<%= false %>"
				value="<%= expirationDate %>"
			/>

			<!-- Action -->

			<liferay-ui:search-container-column-jsp
				align="right"
				path="/polls/question_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<%
		boolean showAddPollButton = PollsPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_QUESTION);
		boolean showPermissionsButton = PollsPermission.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS);
		%>

		<aui:fieldset>
			<c:if test="<%= showAddPollButton || showPermissionsButton %>">
				<aui:button-row>
					<c:if test="<%= showAddPollButton %>">
						<portlet:renderURL var="editQuestionURL">
							<portlet:param name="jspPage" value="/polls/edit_question.jsp" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
						</portlet:renderURL>

						<aui:button href="<%= editQuestionURL %>" value="add-question" />
					</c:if>

					<c:if test="<%= showPermissionsButton %>">
						<liferay-security:permissionsURL
							modelResource="com.liferay.polls"
							modelResourceDescription="<%= HtmlUtil.escape(themeDisplay.getScopeGroupName()) %>"
							resourcePrimKey="<%= String.valueOf(scopeGroupId) %>"
							var="permissionsURL"
						/>

						<aui:button href="<%= permissionsURL %>" value="permissions" />
					</c:if>
				</aui:button-row>
			</c:if>
		</aui:fieldset>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>
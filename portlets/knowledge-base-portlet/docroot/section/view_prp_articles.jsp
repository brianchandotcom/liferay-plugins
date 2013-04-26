<%--
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
--%>

<%@ include file="/section/init.jsp" %>

<%
long assetCategoryId = ParamUtil.getLong(request, "categoryId");
String assetTagName = ParamUtil.getString(request, "tag");

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType", "desc");
%>

<liferay-portlet:renderURL varImpl="iteratorURL">
	<portlet:param name="mvcPath" value="/section/view.jsp" />
	<portlet:param name="categoryId" value="<%= String.valueOf(assetCategoryId) %>" />
	<portlet:param name="tag" value="<%= assetTagName %>" />
</liferay-portlet:renderURL>

<liferay-ui:search-container
	iteratorURL="<%= iteratorURL %>"
	orderByCol="<%= orderByCol %>"
	orderByType="<%= orderByType %>"
>
	<liferay-ui:search-container-results>

		<%
		AssetEntryQuery assetEntryQuery = new AssetEntryQuery(KBArticle.class.getName(), searchContainer);

		pageContext.setAttribute("results", AssetEntryServiceUtil.getEntries(assetEntryQuery));
		pageContext.setAttribute("total", AssetEntryServiceUtil.getEntriesCount(assetEntryQuery));
		%>

	</liferay-ui:search-container-results>

	<c:if test="<%= (assetCategoryId > 0) || Validator.isNotNull(assetTagName) %>">
		<div class="portlet-msg-info">
			<c:choose>
				<c:when test="<%= assetCategoryId > 0 %>">

					<%
					AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getAssetCategory(assetCategoryId);

					assetCategory = assetCategory.toEscapedModel();

					AssetVocabulary assetVocabulary = AssetVocabularyLocalServiceUtil.getAssetVocabulary(assetCategory.getVocabularyId());

					assetVocabulary = assetVocabulary.toEscapedModel();
					%>

					<c:choose>
						<c:when test="<%= Validator.isNotNull(assetTagName) %>">
							<c:choose>
								<c:when test="<%= total > 0 %>">
									<%= LanguageUtil.format(pageContext, "articles-with-x-x-and-tag-x", new String[] {assetVocabulary.getTitle(locale), assetCategory.getTitle(locale), assetTagName}, false) %>
								</c:when>
								<c:otherwise>
									<%= LanguageUtil.format(pageContext, "there-are-no-articles-with-x-x-and-tag-x", new String[] {assetVocabulary.getTitle(locale), assetCategory.getTitle(locale), assetTagName}, false) %>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="<%= total > 0 %>">
									<%= LanguageUtil.format(pageContext, "articles-with-x-x", new String[] {assetVocabulary.getTitle(locale), assetCategory.getTitle(locale)}, false) %>
								</c:when>
								<c:otherwise>
									<%= LanguageUtil.format(pageContext, "there-are-no-articles-with-x-x", new String[] {assetVocabulary.getTitle(locale), assetCategory.getTitle(locale)}, false) %>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="<%= total > 0 %>">
							<%= LanguageUtil.format(pageContext, "articles-with-tag-x", assetTagName, false) %>
						</c:when>
						<c:otherwise>
							<%= LanguageUtil.format(pageContext, "there-are-no-articles-with-tag-x", assetTagName, false) %>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</div>
	</c:if>

	<div class="kb-articles">

		<%
		for (int i = 0; i < results.size(); i++) {
			AssetEntry kbEntry = (AssetEntry)results.get(i);

			KBArticle kbArticle = KBArticleServiceUtil.getLatestKBArticle(kbEntry.getClassPK(), WorkflowConstants.STATUS_APPROVED);
		%>

			<div class="<%= (i == 0) ? "kb-article-title kb-article-title-first" : "kb-article-title" %>">
				<portlet:renderURL var="viewKBArticleURL" windowState="<%= kbArticleWindowState %>">
					<portlet:param name="mvcPath" value="/section/view_article.jsp" />
					<portlet:param name="resourcePrimKey" value="<%= String.valueOf(kbArticle.getResourcePrimKey()) %>" />
				</portlet:renderURL>

				<liferay-ui:icon
					image="../trees/page"
					label="<%= true %>"
					message="<%= kbArticle.getTitle() %>"
					method="get"
					url="<%= viewKBArticleURL %>"
				/>
			</div>

			<c:if test='<%= !kbArticleDisplayStyle.equals("title") %>'>
				<div class="kb-article-content">
					<c:choose>
						<c:when test='<%= kbArticleDisplayStyle.equals("abstract") && Validator.isNotNull(kbArticle.getDescription()) %>'>
							<%= kbArticle.getDescription() %>
						</c:when>
						<c:when test='<%= kbArticleDisplayStyle.equals("abstract") %>'>
							<%= StringUtil.shorten(HtmlUtil.extractText(kbArticle.getContent()), 500) %>
						</c:when>
					</c:choose>
				</div>
			</c:if>

		<%
		}
		%>

	</div>

	<c:if test="<%= showKBArticlesPagination && (total > searchContainer.getDelta()) %>">
		<div class="taglib-search-iterator-page-iterator-bottom">
			<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
		</div>
	</c:if>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>
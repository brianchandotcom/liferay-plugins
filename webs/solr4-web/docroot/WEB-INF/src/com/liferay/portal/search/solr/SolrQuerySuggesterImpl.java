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

package com.liferay.portal.search.solr;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.QuerySuggester;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

/**
 * @author Michael C. Han
 */
public class SolrQuerySuggesterImpl implements QuerySuggester {

	public void setSolrServer(SolrServer solrServer) {
		_solrServer = solrServer;
	}

	public void setSpellCheckURLPrefix(String spellCheckURLPrefix) {
		_spellCheckURLPrefix = spellCheckURLPrefix;
	}

	public void setSuggesterURL(String suggesterURL) {
		_suggesterURL = suggesterURL;
	}

	public String spellCheckKeywords(SearchContext searchContext)
		throws SearchException {

		Map<String, String> additionalQueryParameters =
			new HashMap<String, String>();

		additionalQueryParameters.put("spellcheck.collate", "true");
		additionalQueryParameters.put("spellcheck.maxCollationTries", "0");
		additionalQueryParameters.put("spellcheck.maxCollations", "1");

		SolrQuery solrQuery = createSpellCheckQuery(
			searchContext, additionalQueryParameters);

		try {
			QueryResponse queryResponse = _solrServer.query(solrQuery);

			SpellCheckResponse spellCheckResponse =
				queryResponse.getSpellCheckResponse();

			return spellCheckResponse.getCollatedResult();
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to execute Solr query", e);
			}

			throw new SearchException(e.getMessage());
		}
	}

	public Map<String, List<String>> spellCheckKeywords(
			SearchContext searchContext, int max)
		throws SearchException {

		Map<String, String> additionalQueryParameters =
					new HashMap<String, String>();

		additionalQueryParameters.put(
			"spellcheck.count", Integer.toString(max));

		SolrQuery solrQuery = createSpellCheckQuery(
			searchContext, additionalQueryParameters);

		try {
			QueryResponse queryResponse = _solrServer.query(solrQuery);

			SpellCheckResponse spellCheckResponse =
				queryResponse.getSpellCheckResponse();

			List<SpellCheckResponse.Suggestion> suggestions =
				spellCheckResponse.getSuggestions();

			Map<String, List<String>> spellCheckResults =
				new HashMap<String, List<String>>();

			for (SpellCheckResponse.Suggestion suggestion : suggestions) {
				spellCheckResults.put(
					suggestion.getToken(), suggestion.getAlternatives());
			}

			return spellCheckResults;
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to execute Solr query", e);
			}

			throw new SearchException(e.getMessage());
		}
	}

	public String[] suggestKeywordQueries(SearchContext searchContext, int max)
		throws SearchException {

		SolrQuery solrQuery = new SolrQuery();

		StringBundler sb = new StringBundler(5);

		sb.append(Field.KEYWORD_SEARCH);
		sb.append(StringPool.COLON);
		sb.append(StringPool.QUOTE);
		sb.append(searchContext.getKeywords());
		sb.append(StringPool.QUOTE);

		solrQuery.setRequestHandler(_suggesterURL);
		solrQuery.setQuery(sb.toString());
		solrQuery.setRows(max);

		String companyIdFilterQuery = Field.COMPANY_ID.concat(
			StringPool.COLON).concat(
			Long.toString(searchContext.getCompanyId()));

		solrQuery.setFilterQueries(companyIdFilterQuery);

		try {
			QueryResponse queryResponse = _solrServer.query(solrQuery);

			SolrDocumentList solrDocumentList = queryResponse.getResults();

			int numDocuments = solrDocumentList.size();

			String[] results = new String[numDocuments];

			for (int i = 0; i < numDocuments; i++) {
				SolrDocument solrDocument = solrDocumentList.get(i);

				results[i] = (String)solrDocument.getFieldValue(
					Field.KEYWORD_SEARCH);
			}

			return results;
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to execute Solr query", e);
			}

			throw new SearchException(e.getMessage());
		}
	}

	protected SolrQuery createSpellCheckQuery(
		SearchContext searchContext,
		Map<String, String> additionalQueryParameters) {

		SolrQuery solrQuery = new SolrQuery();

		Locale locale = searchContext.getLocale();

		String requestHandler = _spellCheckURLPrefix.concat(
			StringPool.UNDERLINE).concat(locale.toString());

		solrQuery.setRequestHandler(requestHandler);
		solrQuery.setParam("spellcheck.q", searchContext.getKeywords());

		for (Map.Entry<String, String> additionalQueryParameter :
				additionalQueryParameters.entrySet()) {

			solrQuery.setParam(
				additionalQueryParameter.getKey(),
				additionalQueryParameter.getValue());
		}

		return solrQuery;
	}

	private static Log _log = LogFactoryUtil.getLog(SolrQuerySuggesterImpl.class);

	private SolrServer _solrServer;
	private String _spellCheckURLPrefix = "/liferay_spellCheck";
	private String _suggesterURL = "/select";

}
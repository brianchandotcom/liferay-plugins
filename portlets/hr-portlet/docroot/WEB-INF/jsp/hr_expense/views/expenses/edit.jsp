<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/WEB-INF/jsp/hr_expense/views/init.jsp" %>

<liferay-util:buffer var="removeIcon">
	<liferay-ui:icon
		image="unlink"
		label="<%= true %>"
		message="remove"
	/>
</liferay-util:buffer>

<portlet:renderURL var="viewHRExpensesURL">
	<portlet:param name="controller" value="expenses" />
	<portlet:param name="action" value="index" />
	<portlet:param name="format" value="html" />
</portlet:renderURL>

<aui:a href="${viewHRExpensesURL}">View Expenses</aui:a>

<br />

<portlet:actionURL var="saveHRExpenseURL">
	<portlet:param name="controller" value="expenses" />
	<portlet:param name="action" value="${cmd}" />
	<portlet:param name="format" value="html" />
</portlet:actionURL>

<aui:model-context bean="${hrExpense}" model="<%= HRExpense.class %>" />

<aui:form action='<%= saveHRExpenseURL + "&p_p_state=normal" %>' method="post" name="fm1">
	<aui:input name="redirect" type="hidden" value="${viewHRExpensesURL}" />
	<aui:input name="hrUserId" type="hidden" value="<%= themeDisplay.getUserId() %>" />
	<aui:input name="hrExpenseAccountId" type="hidden" value="${hrExpense.hrExpenseAccountId}" />
	<aui:input name="hrExpenseTypeId" type="hidden" value="${hrExpense.hrExpenseTypeId}" />
	<aui:input name="className" type="hidden" value="<%= HRExpense.class.getName() %>" />
	<aui:input name="classPK" type="hidden" value="${hrExpense.hrExpenseId}" />

	<c:choose>
		<c:when test="${hrExpense.hrExpenseId > 0}">
			<aui:input name="hrExpenseId" type="hidden" value="${hrExpense.hrExpenseId}" />
		</c:when>
		<c:otherwise>
			<aui:input name="new" type="hidden" value="1" />
		</c:otherwise>
	</c:choose>

	<aui:fieldset label="details">
		<aui:layout>
			<aui:column>
				<aui:input name="hrExpenseAccountName" type="text" value="${hrExpenseAccountName}" />
			</aui:column>
			<aui:column>
				<aui:input name="hrExpenseTypeName" type="text" value="${hrExpenseTypeName}" />
			</aui:column>
		</aui:layout>
	</aui:fieldset>

	<aui:fieldset label="expense">
		<aui:layout>
			<aui:column>
				<aui:input name="expenseDate" />
			</aui:column>
		</aui:layout>
		<aui:layout>
			<aui:column>
				<aui:select label="expense-currency-code" name="expenseHRExpenseCurrencyId" showEmptyOption="<%= true %>">
					<c:forEach items="${hrExpenseCurrencies}" var="hrExpenseCurrency">
						<aui:option label="${hrExpenseCurrency.code}" selected="${hrExpense.expenseHRExpenseCurrencyId == hrExpenseCurrency.hrExpenseCurrencyId}" value="${hrExpenseCurrency.hrExpenseCurrencyId}" />
					</c:forEach>
				</aui:select>
			</aui:column>
			<aui:column>
				<aui:input name="expenseAmount" />
			</aui:column>
			<aui:column>
				<aui:select label="reimbursement-currency-code" name="reimbursementHRExpenseCurrencyId" showEmptyOption="<%= true %>">
					<c:forEach items="${hrExpenseCurrencies}" var="hrExpenseCurrency">
						<aui:option label="${hrExpenseCurrency.code}" selected="${hrExpense.reimbursementHRExpenseCurrencyId == hrExpenseCurrency.hrExpenseCurrencyId}" value="${hrExpenseCurrency.hrExpenseCurrencyId}" />
					</c:forEach>
				</aui:select>
			</aui:column>
			<aui:column>
				<aui:input name="reimbursementAmount" disabled="<%= true %>" />
			</aui:column>
		</aui:layout>
		<aui:layout>
			<aui:column>
				<aui:button-row>
					<portlet:renderURL var="viewHRExpenseCurrencyConversionsURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
						<portlet:param name="controller" value="currency_conversions" />
						<portlet:param name="action" value="index" />
						<portlet:param name="format" value="html" />
					</portlet:renderURL>

					<aui:button name="viewCurrencyConversions" value="view-currency-conversions" />
				</aui:button-row>
			</aui:column>
		</aui:layout>
	</aui:fieldset>

	<c:if test="${hrExpense.hrExpenseId > 0}">
		<aui:fieldset label="files">
			<br />

			<div class="lfr-dynamic-uploader">
				<div class="lfr-upload-container" id="<portlet:namespace />fileUpload"></div>
			</div>

			<liferay-ui:search-container
				id='<%= renderResponse.getNamespace() + "filesSearchContainer" %>'
				emptyResultsMessage="there-are-no-files"
				headerNames="file-name"
			>

				<liferay-ui:search-container-results
					results="${existingFiles}"
					total="${fn:length(existingFiles)}"
				/>

				<liferay-ui:search-container-row
					className="java.lang.String"
					modelVar="existingFile"
					stringKey="<%= true %>"
				>

					<liferay-portlet:resourceURL var="documentURL" copyCurrentRenderParameters="<%= false %>" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
						<portlet:param name="controller" value="documents" />
						<portlet:param name="action" value="view" />
						<portlet:param name="className" value="hrexpense" />
						<portlet:param name="folderId" value="${hrExpense.hrExpenseId}" />
						<portlet:param name="fileName" value="${existingFile}" />
						<portlet:param name="p_p_resource_id" value="download" />
					</liferay-portlet:resourceURL>

					<liferay-ui:search-container-column-text
						href="${documentURL}"
						name="file-name"
						value="${existingFile}"
					/>

					<liferay-ui:search-container-column-text>
						<a class="modify-link" data-rowId="${existingFile}" href="javascript:;">${removeIcon}</a>
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator paginate="<%= false %>" />
			</liferay-ui:search-container>
		</aui:fieldset>
	</c:if>

	<aui:button-row>
		<aui:button type="submit" />
		<aui:button href="${viewHRExpensesURL}" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base,json-parse">
	var inputExpenseNode = A.one('#<portlet:namespace />expenseAmount');
	var selectExpenseNode = A.one('#<portlet:namespace />expenseHRExpenseCurrencyId');
	var selectReimbursementNode = A.one('#<portlet:namespace />reimbursementHRExpenseCurrencyId');
	var resultNode = A.one('#<portlet:namespace />reimbursementAmount');

	var onChangeFn = function(event) {
		var inputExpenseValue = inputExpenseNode.get('value');
		var selectExpenseId = selectExpenseNode.get('value');
		var selectReimbursementId = selectReimbursementNode.get('value');

		var data = A.JSON.parse('${hrExpenseCurrencyConversionsJSON}');

		for (var i in data) {
			var conversion = data[i];

			var fromCurrency = conversion.fromHRExpenseCurrencyId;
			var toCurrency = conversion.toHRExpenseCurrencyId;
			var conversionValue = conversion.conversionValue;

			if ((selectExpenseId == fromCurrency) && (selectReimbursementId == toCurrency)) {
				var resultValue = inputExpenseValue * conversionValue;

				resultNode.set('value', roundNumber(resultValue, 2));
			}
		}
	};

	inputExpenseNode.on('keyup', onChangeFn);
	selectExpenseNode.on('change', onChangeFn);
	selectReimbursementNode.on('change', onChangeFn);

	function roundNumber(num, dec) {
		var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
		return result;
	}
</aui:script>

<aui:script use="aui-base">
	var viewCurrencyConversionsButton = A.one('#<portlet:namespace />viewCurrencyConversions');

	viewCurrencyConversionsButton.on(
		'click',
		function(event) {
			Liferay.HR.displayPopup('${viewHRExpenseCurrencyConversionsURL}', '<liferay-ui:message key="view-currency-conversions" />');
		}
	);
</aui:script>

<aui:script>
	AUI({}).use(
		"aui-base",
		"autocomplete",
		"autocomplete-filters",
		"autocomplete-highlighters",
		"json-parse",
		function(A) {
			var accountNode = A.one('#<portlet:namespace />hrExpenseAccountName').plug(A.Plugin.AutoComplete, {
				resultFilters: 'startsWith',
				resultHighlighter: 'startsWith',
				resultTextLocator: 'name',
				source: ${hrExpenseAccountsJSON}
			});

			var currentHit = accountNode.ac.get('value');
			var lastValue = accountNode.ac.get('value');

			var hrExpenseAccountId = A.one('#<portlet:namespace />hrExpenseAccountId');

			accountNode.ac.after('clear', function(event) {
				currentHit = '';
				lastValue = '';
			});

			accountNode.ac.after('select', function(event) {
				currentHit = setPrimaryKey(event.result);

				lastValue = event.result.text;
			});

			accountNode.ac.on('results', function(event) {
				if (event.results.length) {
					currentHit = setPrimaryKey(event.results[0]);

					lastValue = accountNode.ac.get('value');
				}
				else {
					accountNode.set('value', lastValue);
				}
			});

			accountNode.on('change', function(event) {
				lastValue = currentHit;

				accountNode.set('value', currentHit);
			});

			function setPrimaryKey(result) {
				if (result == null) {
					return 0;
				}

				var hrExpenseAccount = result.raw;

				hrExpenseAccountId.set('value', hrExpenseAccount.hrExpenseAccountId);

				return hrExpenseAccount.name;
			}
		}
	);
</aui:script>

<aui:script>
	AUI({}).use(
		"aui-base",
		"autocomplete",
		"autocomplete-filters",
		"autocomplete-highlighters",
		"json-parse",
		function(A) {
			var typeNode = A.one('#<portlet:namespace />hrExpenseTypeName').plug(A.Plugin.AutoComplete, {
				resultFilters: 'startsWith',
				resultHighlighter: 'startsWith',
				resultTextLocator: 'name',
				source: ${hrExpenseTypesJSON}
			});

			var currentHit = typeNode.ac.get('value');
			var lastValue = typeNode.ac.get('value');

			var hrExpenseTypeId = A.one('#<portlet:namespace />hrExpenseTypeId');

			typeNode.ac.after('clear', function(event) {
				currentHit = '';
				lastValue = '';
			});

			typeNode.ac.after('select', function(event) {
				currentHit = setPrimaryKey(event.result);

				lastValue = event.result.text;
			});

			typeNode.ac.on('results', function(event) {
				if (event.results.length) {
					currentHit = setPrimaryKey(event.results[0]);

					lastValue = typeNode.ac.get('value');
				}
				else {
					typeNode.set('value', lastValue);
				}
			});

			typeNode.on('change', function(event) {
				lastValue = currentHit;

				typeNode.set('value', currentHit);
			});

			function setPrimaryKey(result) {
				if (result == null) {
					return 0;
				}

				var hrExpenseType = result.raw;

				hrExpenseTypeId.set('value', hrExpenseType.hrExpenseTypeId);

				return hrExpenseType.name;
			}
		}
	);
</aui:script>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />filesSearchContainer');

	searchContainer.get('contentBox').delegate(
		'click',
		function(event) {
			var link = event.currentTarget;
			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));
		},
		'.modify-link'
	);
</aui:script>

<portlet:actionURL var="addHRExpenseTempDocumentURL">
	<portlet:param name="controller" value="expenses" />
	<portlet:param name="action" value="docs" />
	<portlet:param name="format" value="html" />
	<portlet:param name="classPK" value="${hrExpense.hrExpenseId}" />
	<portlet:param name="uploadMethod" value="<%= Constants.ADD_TEMP %>" />
	<portlet:param name="userId" value="<%= String.valueOf(themeDisplay.getUserId()) %>" />
</portlet:actionURL>

<portlet:actionURL var="deleteHRExpenseTempDocumentURL">
	<portlet:param name="controller" value="expenses" />
	<portlet:param name="action" value="docs" />
	<portlet:param name="format" value="html" />
	<portlet:param name="classPK" value="${hrExpense.hrExpenseId}" />
	<portlet:param name="uploadMethod" value="<%= Constants.DELETE_TEMP %>" />
	<portlet:param name="userId" value="<%= String.valueOf(themeDisplay.getUserId()) %>" />
</portlet:actionURL>

<portlet:actionURL var="addHRExpenseMultipleDocumentsURL">
	<portlet:param name="controller" value="expenses" />
	<portlet:param name="action" value="docs" />
	<portlet:param name="format" value="html" />
	<portlet:param name="classPK" value="${hrExpense.hrExpenseId}" />
	<portlet:param name="uploadMethod" value="<%= Constants.ADD_MULTIPLE %>" />
</portlet:actionURL>

<aui:form action="<%= addHRExpenseMultipleDocumentsURL %>" method="post" name="fm2" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "updateMultiplePageAttachments();" %>'>
	<span id="<portlet:namespace />selectedFileNameContainer"></span>

	<div class="aui-helper-hidden" id="<portlet:namespace />metadataExplanationContainer"></div>

	<div class="aui-helper-hidden selected" id="<portlet:namespace />selectedFileNameMetadataContainer">
		<aui:button type="submit" />
	</div>
</aui:form>

<aui:script use="liferay-upload">
	new Liferay.Upload(
		{
			allowedFileTypes: '<%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA)) %>',
			container: '#<portlet:namespace />fileUpload',
			deleteFile: '${deleteHRExpenseTempDocumentURL}<liferay-ui:input-permissions-params modelName="<%= HRExpense.class.getName() %>" />',
			fileDescription: '<%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA)) %>',
			maxFileSize: <%= GetterUtil.getLong(PrefsPropsUtil.getString(PropsKeys.DL_FILE_MAX_SIZE)) %> / 1024,
			metadataContainer: '#<portlet:namespace />selectedFileNameMetadataContainer',
			metadataExplanationContainer: '#<portlet:namespace />metadataExplanationContainer',
			namespace: '<portlet:namespace />',
			service: {
				method: Liferay.Service.HR.HRExpense.getTempDocumentNames,
				params: {
					userId: <%= themeDisplay.getUserId() %>,
					classPK: ${hrExpense.hrExpenseId},
					tempFolderName: 'com.liferay.portlet.hrexpense.action.docs'
				}
			},
			uploadFile: '${addHRExpenseTempDocumentURL}<liferay-ui:input-permissions-params modelName="<%= HRExpense.class.getName() %>" />'
		}
	);
</aui:script>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />updateMultiplePageAttachments',
		function() {
			var A = AUI();
			var Lang = A.Lang;

			var selectedFileNameContainer = A.one('#<portlet:namespace />selectedFileNameContainer');

			var inputTpl = '<input id="<portlet:namespace />selectedFileName{0}" name="<portlet:namespace />selectedFileName" type="hidden" value="{1}" />';

			var values = A.all('input[name=<portlet:namespace />selectUploadedFileCheckbox]:checked').val();

			var buffer = [];
			var dataBuffer = [];
			var length = values.length;

			for (var i = 0; i < length; i++) {
				dataBuffer[0] = i;
				dataBuffer[1] = values[i];

				buffer[i] = Lang.sub(inputTpl, dataBuffer);
			}

			selectedFileNameContainer.html(buffer.join(''));

			A.io.request(
				document.<portlet:namespace />fm2.action,
				{
					dataType: 'json',
					form: {
						id: document.<portlet:namespace />fm2
					},
					after: {
						success: function(event, id, obj) {
							var jsonArray = this.get('responseData');

							for (var i = 0; i < jsonArray.length; i++) {
								var item = jsonArray[i];

								var checkBox = A.one('input[data-fileName="' + item.fileName + '"]');

								checkBox.attr('checked', false);
								checkBox.hide();

								var li = checkBox.ancestor();

								li.removeClass('selectable').removeClass('selected');

								var cssClass = null;
								var childHTML = null;

								if (item.added) {
									cssClass = 'file-saved';

									childHTML = '<span class="success-message">successfully-saved</span>';

									updateFilesSearchContainer(item);
								}
								else {
									cssClass = 'upload-error';

									childHTML = '<span class="error-message">' + item.errorMessage + '</span>';
								}

								li.addClass(cssClass);
								li.append(childHTML);
							}
						}
					}
				}
			);
		},
		['aui-base']
	);

	var updateFilesSearchContainer = function(item) {
		var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />filesSearchContainer');

		var fileName = item.fileName;

		var href = '<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="controller" value="documents" /><portlet:param name="action" value="view" /><portlet:param name="className" value="hrexpense" /><portlet:param name="folderId" value="${hrExpense.hrExpenseId}" /><portlet:param name="fileName" value="REPLACE_FILE_NAME" /><portlet:param name="p_p_resource_id" value="download" /></liferay-portlet:resourceURL>';

		var href = href.replace('REPLACE_FILE_NAME', fileName);

		var fileNameLink = '<a href="' + href + '">' + fileName + '</a>';

		var rowColumns = [];

		rowColumns.push(fileNameLink);

		rowColumns.push('<a class="modify-link" data-rowId="' + fileName + '" href="javascript:;"><%= UnicodeFormatter.toString(removeIcon) %></a>');

		searchContainer.addRow(rowColumns, fileName);
		searchContainer.updateDataStore();
	};
</aui:script>
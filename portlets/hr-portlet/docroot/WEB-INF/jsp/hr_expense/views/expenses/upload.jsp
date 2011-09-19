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

<div class="lfr-dynamic-uploader">
	<div class="lfr-upload-container" id="<portlet:namespace />fileUpload"></div>
</div>

<portlet:actionURL var="addTempDocumentURL">
	<portlet:param name="controller" value="${controller}" />
	<portlet:param name="action" value="docs" />
	<portlet:param name="format" value="html" />
	<portlet:param name="classPK" value="${classPK}" />
	<portlet:param name="uploadMethod" value="<%= Constants.ADD_TEMP %>" />
	<portlet:param name="userId" value="<%= String.valueOf(themeDisplay.getUserId()) %>" />
</portlet:actionURL>

<portlet:actionURL var="deleteTempDocumentURL">
	<portlet:param name="controller" value="${controller}" />
	<portlet:param name="action" value="docs" />
	<portlet:param name="format" value="html" />
	<portlet:param name="classPK" value="${classPK}" />
	<portlet:param name="uploadMethod" value="<%= Constants.DELETE_TEMP %>" />
	<portlet:param name="userId" value="<%= String.valueOf(themeDisplay.getUserId()) %>" />
</portlet:actionURL>

<portlet:actionURL var="addMultipleDocumentsURL">
	<portlet:param name="controller" value="${controller}" />
	<portlet:param name="action" value="docs" />
	<portlet:param name="format" value="html" />
	<portlet:param name="classPK" value="${classPK}" />
	<portlet:param name="uploadMethod" value="<%= Constants.ADD_MULTIPLE %>" />
</portlet:actionURL>

<aui:form action="<%= addMultipleDocumentsURL %>" method="post" name="fm2" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "updateMultiplePageAttachments();" %>'>
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
			deleteFile: '${deleteTempDocumentURL}<liferay-ui:input-permissions-params modelName="${className}" />',
			fileDescription: '<%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA)) %>',
			maxFileSize: <%= GetterUtil.getLong(PrefsPropsUtil.getString(PropsKeys.DL_FILE_MAX_SIZE)) %> / 1024,
			metadataContainer: '#<portlet:namespace />selectedFileNameMetadataContainer',
			metadataExplanationContainer: '#<portlet:namespace />metadataExplanationContainer',
			namespace: '<portlet:namespace />',
			service: {
				method: ${uploadMethod},
				params: {
					userId: <%= themeDisplay.getUserId() %>,
					classPK: ${classPK},
					tempFolderName: '${uploadTempFolderName}'
				}
			},
			uploadFile: '${addTempDocumentURL}<liferay-ui:input-permissions-params modelName="${className}" />'
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
</aui:script>
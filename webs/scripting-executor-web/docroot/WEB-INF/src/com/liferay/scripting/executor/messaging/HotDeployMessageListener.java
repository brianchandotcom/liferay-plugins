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

package com.liferay.scripting.executor.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scripting.ScriptingUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.scripting.executor.util.ClassLoaderUtil;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class HotDeployMessageListener extends BaseMessageListener {

	public void setSupportedLanguages(Set<String> supportedLanguages) {
		_supportedLanguages = supportedLanguages;
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		String command = message.getString("command");

		if (!command.equals("undeploy") && !command.equals("deploy")) {
			return;
		}

		String servletContextName = message.getString("servletContextName");

		ServletContext servletContext = ServletContextPool.get(
			servletContextName);

		URL scriptsDirURL = servletContext.getResource(_SCRIPTS_DIR);

		if (scriptsDirURL == null) {
			return;
		}

		Properties pluginPackageProperties = getPluginPackageProperties(
			servletContext);

		String scriptLanguage = pluginPackageProperties.getProperty(
			"script-language");

		if (!_supportedLanguages.contains(scriptLanguage)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Script Executor does not support language: " +
					scriptLanguage + ". Supported languages are: " +
					StringUtil.merge(_supportedLanguages, StringPool.COMMA));
			}

			return;
		}

		if (command.equals("deploy")) {
			doDeploy(servletContext, pluginPackageProperties, scriptLanguage);
		}
		else {
			doUnDeploy(servletContext, scriptLanguage);
		}
	}

	private void doUnDeploy(
			ServletContext servletContext, String scriptLanguage)
		throws Exception {

		ScriptingUtil.clearCache(scriptLanguage);
	}

	protected void doDeploy(
			ServletContext servletContext, Properties pluginPackageProperties,
			String scriptLanguage)
		throws Exception {


		String requiredDeploymentContexts = pluginPackageProperties.getProperty(
			_REQUIRED_DEPLOYMENT_CONTEXTS);

		if (Validator.isNull(requiredDeploymentContexts)) {
			return;
		}

		ClassLoader aggregatedClassLoader =
			ClassLoaderUtil.getAggregatedPluginsClassLoader(
				StringUtil.split(requiredDeploymentContexts), false);

		executeScripts(servletContext, scriptLanguage, aggregatedClassLoader);
	}

	protected void executeScripts(
			ServletContext servletContext, String scriptLanguage,
			ClassLoader aggregatedClassLoader) {

		Map<String, Object> inputObjects = new HashMap<String, Object>();

		Set<String> scriptFilePaths =
			servletContext.getResourcePaths(_SCRIPTS_DIR);

		InputStream in = null;

		for (String scriptFilePath : scriptFilePaths) {

			try {
				in = servletContext.getResourceAsStream(scriptFilePath);

				ScriptingUtil.exec(
					null, inputObjects, scriptLanguage, StringUtil.read(in),
					aggregatedClassLoader);
			}
			catch (Exception e) {
				if (_log.isErrorEnabled()) {
					_log.error(
						"Unable to execute script: " + scriptFilePath, e);
				}
			}
			finally {
				StreamUtil.cleanUp(in);
			}
		}
	}

	protected Properties getPluginPackageProperties(
		ServletContext servletContext) {

		Properties properties = new Properties();

		try {
			String propertiesString = StringUtil.read(
				servletContext.getResourceAsStream(
					"/WEB-INF/liferay-plugin-package.properties"));

			if (propertiesString != null) {
				String contextPath = servletContext.getRealPath(
					StringPool.SLASH);

				contextPath = StringUtil.replace(
					contextPath, StringPool.BACK_SLASH, StringPool.SLASH);

				propertiesString = propertiesString.replace(
					"${context.path}", contextPath);

				PropertiesUtil.load(properties, propertiesString);
			}
		}
		catch (IOException e) {
			_log.error(e, e);
		}

		return properties;
	}

	private static final String _REQUIRED_DEPLOYMENT_CONTEXTS =
		"required-deployment-contexts";

	private static final String _SCRIPTS_DIR = "/WEB-INF/classes/scripts/";

	private static Log _log = LogFactoryUtil.getLog(
		HotDeployMessageListener.class);

	private Set<String> _supportedLanguages = new HashSet<String>();

}
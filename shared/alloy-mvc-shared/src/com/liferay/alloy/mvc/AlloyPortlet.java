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

package com.liferay.alloy.mvc;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.Router;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class AlloyPortlet extends GenericPortlet {

	@Override
	public void destroy() {
		for (BaseAlloyControllerImpl baseAlloyControllerImpl :
				_alloyControllers.values()) {

			Indexer indexer = baseAlloyControllerImpl.indexer;

			if (indexer != null) {
				IndexerRegistryUtil.unregister(indexer);
			}

			MessageListener controllerMessageListener =
				baseAlloyControllerImpl.controllerMessageListener;

			if (controllerMessageListener != null) {
				MessageBusUtil.removeDestination(
					baseAlloyControllerImpl.getControllerDestinationName());
			}

			MessageListener schedulerMessageListener =
				baseAlloyControllerImpl.schedulerMessageListener;

			if (schedulerMessageListener != null) {
				try {
					SchedulerEngineHelperUtil.unschedule(
						baseAlloyControllerImpl.getSchedulerJobName(),
						baseAlloyControllerImpl.getMessageListenerGroupName(),
						StorageType.MEMORY_CLUSTERED);

					MessageBusUtil.removeDestination(
						baseAlloyControllerImpl.getSchedulerDestinationName());
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}
		}
	}

	@Override
	public void init(PortletConfig portletConfig) throws PortletException {
		super.init(portletConfig);

		LiferayPortletConfig liferayPortletConfig =
			(LiferayPortletConfig)portletConfig;

		Portlet portlet = liferayPortletConfig.getPortlet();

		FriendlyURLMapper friendlyURLMapper =
			portlet.getFriendlyURLMapperInstance();

		Router router = friendlyURLMapper.getRouter();

		router.urlToParameters(HttpMethods.GET, _defaultRouteParameters);
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		String actionName = ParamUtil.getString(
			actionRequest, ActionRequest.ACTION_NAME);

		if (actionName.equals("serveIOResponse")) {
			try {
				serveIOResponse(actionRequest, actionResponse);
			}
			catch (Exception e) {
				throw new IOException(e);
			}

			return;
		}

		String path = getPath(actionRequest);

		include(path, actionRequest, actionResponse);
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		String path = getPath(renderRequest);

		include(path, renderRequest, renderResponse);
	}

	public void serveIOResponse(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String jsonString = null;

		String controller = ParamUtil.getString(actionRequest, "controller");

		BaseAlloyControllerImpl baseAlloyControllerImpl = _alloyControllers.get(
			controller);

		if (baseAlloyControllerImpl == null) {
			return;
		}

		String action = ParamUtil.getString(actionRequest, "action");

		try {
			if (action.equals("custom")) {
				jsonString = baseAlloyControllerImpl.serveIOResponse(
					actionRequest);
			}
			else if (action.equals("dynamicQuery")) {
				jsonString = serveDynamicQuery(
					baseAlloyControllerImpl, actionRequest);
			}
			else if (action.equals("search")) {
				jsonString = serveSearch(
					baseAlloyControllerImpl, actionRequest);
			}
		}
		catch (Exception e) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("error", "An unexpected exception occurred.");

			jsonString = jsonObject.toString();
		}

		if (Validator.isNotNull(jsonString)) {
			writeJSON(actionRequest, actionResponse, jsonString);
		}
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		String path = getPath(resourceRequest);

		include(path, resourceRequest, resourceResponse);
	}

	protected Map<String, String> getDefaultRouteParameters() {
		/*Map<String, String> defaultRouteParameters =
			new HashMap<String, String[]>();

		defaultRouteParameters.put("controller", new String[] {"assets"});
		defaultRouteParameters.put("action", new String[] {"index"});

		return defaultRouteParameters;*/

		return _defaultRouteParameters;
	}

	protected String getPath(PortletRequest portletRequest) {
		LiferayPortletConfig liferayPortletConfig =
			(LiferayPortletConfig)portletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		Portlet portlet = liferayPortletConfig.getPortlet();

		String controllerPath = ParamUtil.getString(
			portletRequest, "controller");

		if (Validator.isNull(controllerPath)) {
			Map<String, String> defaultRouteParameters =
				getDefaultRouteParameters();

			controllerPath = defaultRouteParameters.get("controller");
		}

		StringBundler sb = new StringBundler(5);

		sb.append("/WEB-INF/jsp/");
		sb.append(portlet.getFriendlyURLMapping());
		sb.append("/controllers/");
		sb.append(controllerPath);
		sb.append("_controller.jsp");

		return sb.toString();
	}

	protected void include(
			String path, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws IOException, PortletException {

		PortletContext portletContext = getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(path);

		if (portletRequestDispatcher == null) {
			_log.error(path + " is not a valid include");
		}
		else {
			portletRequestDispatcher.include(portletRequest, portletResponse);
		}
	}

	protected void registerAlloyController(AlloyController alloyController) {
		BaseAlloyControllerImpl baseAlloyControllerImpl =
			(BaseAlloyControllerImpl)alloyController;

		String controllerPath = baseAlloyControllerImpl.controllerPath;

		_alloyControllers.put(controllerPath, baseAlloyControllerImpl);
	}

	protected String serveDynamicQuery(
			BaseAlloyControllerImpl baseAlloyControllerImpl,
			ActionRequest actionRequest)
		throws Exception {

		if (baseAlloyControllerImpl.permissioned) {
			ThemeDisplay themeDisplay = baseAlloyControllerImpl.themeDisplay;
			Portlet portlet = baseAlloyControllerImpl.portlet;

			AlloyPermission.check(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), portlet.getRootPortletId(),
				baseAlloyControllerImpl.controllerPath, "index");
		}

		AlloyServiceInvoker alloyServiceInvoker =
			baseAlloyControllerImpl.alloyServiceInvoker;

		String propertiesParam = ParamUtil.getString(
			actionRequest, "properties");

		List<Object> properties = JSONFactoryUtil.looseDeserialize(
			propertiesParam, ArrayList.class);

		int start = ParamUtil.getInteger(
			actionRequest, "start", QueryUtil.ALL_POS);

		int end = ParamUtil.getInteger(actionRequest, "end", QueryUtil.ALL_POS);

		List<BaseModel<?>> baseModels = alloyServiceInvoker.executeDynamicQuery(
			properties.toArray(), start, end);

		return JSONFactoryUtil.looseSerialize(baseModels);
	}

	protected String serveSearch(
			BaseAlloyControllerImpl baseAlloyControllerImpl,
			ActionRequest actionRequest)
		throws Exception {

		if (baseAlloyControllerImpl.permissioned) {
			ThemeDisplay themeDisplay = baseAlloyControllerImpl.themeDisplay;
			Portlet portlet = baseAlloyControllerImpl.portlet;

			AlloyPermission.check(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), portlet.getRootPortletId(),
				baseAlloyControllerImpl.controllerPath, "index");
		}

		Map<String, Serializable> attributes = null;

		String attributesParam = ParamUtil.getString(
			actionRequest, "attributes");

		if (Validator.isNotNull(attributesParam)) {
			attributes =
				(Map<String, Serializable>)JSONFactoryUtil.looseDeserialize(
					attributesParam, HashMap.class);
		}

		String keywords = ParamUtil.getString(actionRequest, "keywords");

		Sort[] sorts = null;

		String sortsParam = ParamUtil.getString(actionRequest, "sorts");

		if (Validator.isNotNull(sortsParam)) {
			Map<String, Boolean> sortsMap =
				(Map<String, Boolean>)JSONFactoryUtil.looseDeserialize(
					sortsParam, LinkedHashMap.class);

			sorts = new Sort[sortsMap.size()];

			int i = 0;

			for (Map.Entry<String, Boolean> sort : sortsMap.entrySet()) {
				sorts[i++] = new Sort(sort.getKey(), sort.getValue());
			}
		}

		baseAlloyControllerImpl.portletRequest = actionRequest;
		baseAlloyControllerImpl.request = PortalUtil.getHttpServletRequest(
			actionRequest);

		AlloySearchResult alloySearchResult = baseAlloyControllerImpl.search(
			attributes, keywords, sorts);

		List<BaseModel<?>> baseModels = alloySearchResult.getBaseModels();

		return JSONFactoryUtil.looseSerialize(baseModels);
	}

	protected void writeJSON(
			PortletRequest portletRequest, ActionResponse actionResponse,
			Object json)
		throws IOException {

		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			actionResponse);

		response.setContentType(ContentTypes.APPLICATION_JSON);

		ServletResponseUtil.write(response, json.toString());

		response.flushBuffer();
	}

	private static Log _log = LogFactoryUtil.getLog(AlloyPortlet.class);

	private Map<String, BaseAlloyControllerImpl> _alloyControllers =
		new HashMap<String, BaseAlloyControllerImpl>();
	private Map<String, String> _defaultRouteParameters =
		new HashMap<String, String>();

}
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

package com.liferay.portal.osgi;

import com.liferay.portal.AdaptorException;
import com.liferay.portal.kernel.adaptor.Adaptor;
import com.liferay.portal.kernel.adaptor.AdaptorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.portal.osgi.listener.ServiceListener;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.UniqueList;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.io.IOException;
import java.io.Serializable;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

import org.springframework.aop.SpringProxy;
import org.springframework.aop.framework.Advised;
import org.springframework.context.ApplicationContext;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author Raymond Augé
 */
public class OSGiAdaptorImpl implements Adaptor {

	public void init(
			ServletContext servletContext, Object applicationContext)
		throws AdaptorException {

		Iterator<FrameworkFactory> frameworkFactoryItr = ServiceLoader.load(
			FrameworkFactory.class).iterator();

		if (frameworkFactoryItr.hasNext()) {
			_servletContext = servletContext;

			Map<String,String> properties = buildProperties();

			_framework = frameworkFactoryItr.next().newFramework(properties);

			try {
				_framework.init();

				BundleContext bundleContext = _framework.getBundleContext();

				bundleContext.addServiceListener(
					new ServiceListener(bundleContext), _PORTAL_SERVICE_FILTER);
			}
			catch (Exception e) {
				throw new AdaptorException(e);
			}
		}
	}

	public void registerApplicationContext(
			ApplicationContext applicationContext)
		throws AdaptorException {

		BundleContext bundleContext = _framework.getBundleContext();

		for (String beanName : applicationContext.getBeanDefinitionNames()) {
			Object bean = null;

			try {
				bean = applicationContext.getBean(beanName);
			}
			catch (Exception e) {
				_log.error(e.getMessage());
			}

			if (bean == null) {
				continue;
			}

			Class<?> beanClazz = bean.getClass();

			List<Class<?>> interfaces = new ArrayList<Class<?>>();

			interfaces.addAll(ListUtil.fromArray(beanClazz.getInterfaces()));

			while (beanClazz.getSuperclass() != null) {
				beanClazz = beanClazz.getSuperclass();

				interfaces.addAll(
					ListUtil.fromArray(beanClazz.getInterfaces()));
			}

			List<String> names = new ArrayList<String>();

			for (Class<?> _interface : interfaces) {
				String name = _interface.getName();

				if (name.equals(MethodInterceptor.class.getName()) ||
					name.equals(Serializable.class.getName()) ||
					name.equals(SpringProxy.class.getName()) ||
					name.equals(Advised.class.getName())) {

					continue;
				}

				names.add(name);
			}

			if (names.size() > 0) {
				Hashtable<String,Object> properties =
					new Hashtable<String,Object>();

				properties.put(OSGiConstants.PORTAL_SERVICE, Boolean.TRUE);
				properties.put(OSGiConstants.PORTAL_SERVICE_CORE, Boolean.TRUE);
				properties.put(
					OSGiConstants.PORTAL_SERVICE_BEAN_NAME, beanName);

				properties.put(Constants.SERVICE_RANKING, 1000);
				properties.put(
					Constants.SERVICE_VENDOR, ReleaseInfo.getVendor());

				bundleContext.registerService(
					names.toArray(new String[names.size()]), bean,
					properties);
			}
		}
	}

	public Object registerService(
			String serviceName, Object service,
			Dictionary<String, Object> dictionary)
		throws AdaptorException {

		BundleContext bundleContext = _framework.getBundleContext();

		return bundleContext.registerService(
			serviceName, service, dictionary);
	}

	public void start() throws AdaptorException {
		try {
			_framework.start();
		}
		catch (Exception e) {
			throw new AdaptorException(e);
		}
	}

	public void stop() throws AdaptorException {
		try {
			_framework.waitForStop(0);
		}
		catch (Exception e) {
			throw new AdaptorException(e);
		}
	}

	public void unRegisterService(Object object)
		throws AdaptorException {

		if (object instanceof ServiceRegistration) {
			ServiceRegistration serviceRegistration =
				(ServiceRegistration)object;

			serviceRegistration.unregister();
		}
	}

	protected static Map<String,String> buildProperties() {
		Map<String,String> propertyMap = new HashMap<String,String>();

		propertyMap.put(Constants.BUNDLE_NAME, ReleaseInfo.getName());
		propertyMap.put(Constants.BUNDLE_VENDOR, ReleaseInfo.getVendor());
		propertyMap.put(Constants.BUNDLE_VERSION, ReleaseInfo.getVersion());
		propertyMap.put(
			Constants.BUNDLE_DESCRIPTION, ReleaseInfo.getReleaseInfo());

		propertyMap.put(
			Constants.FRAMEWORK_BUNDLE_PARENT,
			Constants.FRAMEWORK_BUNDLE_PARENT_APP);
		propertyMap.put(
			Constants.FRAMEWORK_STORAGE, _OSGI_FRAMEWORK_STORAGE);

		UniqueList<String> packages = new UniqueList<String>();

		getBundleExportPackages(
			CompanyImpl.class, "com.liferay.portal.impl", packages);
		getBundleExportPackages(
			Company.class, "com.liferay.portal.service", packages);
		getBundleExportPackages(
			MVCPortlet.class, "com.liferay.util.bridges", packages);
		getBundleExportPackages(
			UniqueList.class, "com.liferay.util.java", packages);

		try {
			getBundleExportPackages(
				Class.forName("com.liferay.taglib.util.ThemeUtil"),
				"com.liferay.util.taglib", packages);
		}
		catch (ClassNotFoundException cnfe) {
			_log.error(cnfe, cnfe);
		}

		packages.addAll(Arrays.asList(_OSGI_SYSTEM_PACKAGES_EXTRA));

		Collections.sort(packages);

		propertyMap.put(
			Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA,
			StringUtil.merge(packages));

		return propertyMap;
	}

	protected static Manifest getBundleManifest(
		Class clazz, String bundleSymbolicName) {

		Manifest manifest = null;

		try {
			Enumeration<URL> resources =
				clazz.getClassLoader().getResources(
					"META-INF/MANIFEST.MF");

			while (resources.hasMoreElements()) {
				manifest = new Manifest(resources.nextElement().openStream());

				Attributes attributes = manifest.getMainAttributes();

				String bsn = attributes.getValue(Constants.BUNDLE_SYMBOLICNAME);

				if (Validator.isNotNull(bsn) &&
					bsn.startsWith(bundleSymbolicName)) {

					return manifest;
				}
			}
		}
		catch (IOException e) {
			_log.error(e, e);
		}

		return null;
	}

	protected static void getBundleExportPackages(
		Class clazz, String bundleSymbolicName, List<String> packages) {

		Manifest manifest = getBundleManifest(clazz, bundleSymbolicName);

		if (manifest != null) {
			Attributes attributes = manifest.getMainAttributes();

			String value = attributes.getValue(Constants.EXPORT_PACKAGE);

			String[] values = StringUtil.split(value);

			packages.addAll(Arrays.asList(values));
		}
	}

	public static final String _OSGI_FRAMEWORK_STORAGE = PropsUtil.get(
		"osgi.framework.storage");
	public static final String[] _OSGI_SYSTEM_PACKAGES_EXTRA =
		PropsUtil.getArray("osgi.system.packages.extra");
	private final static String _PORTAL_SERVICE_FILTER =
		"(&(portal.service=*)(!(portal.service.core=*))" +
			"(!(portal.service.previous=*)))";

	private static Log _log = LogFactoryUtil.getLog(AdaptorUtil.class);

	private static Framework _framework;
	private static ServletContext _servletContext;

}
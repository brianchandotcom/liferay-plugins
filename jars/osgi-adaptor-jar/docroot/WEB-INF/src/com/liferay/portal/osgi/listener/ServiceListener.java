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

package com.liferay.portal.osgi.listener;

import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.osgi.OSGiConstants;
import com.liferay.portal.osgi.ServiceWrapperUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.target.SingletonTargetSource;

/**
 * @author Raymond Augé
 */
public class ServiceListener implements org.osgi.framework.ServiceListener {

	public ServiceListener(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	public void serviceChanged(ServiceEvent event) {
		try {
			if (event.getType() == ServiceEvent.REGISTERED) {
				doEventRegistered(event);
			}
			else if (event.getType() == ServiceEvent.UNREGISTERING) {
				doEventUnregistering(event);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void doEventRegistered(ServiceEvent event) throws Exception {
		ServiceReference serviceReference = event.getServiceReference();

		boolean portalServiceWrapper = (Boolean)serviceReference.getProperty(
			OSGiConstants.PORTAL_SERVICE_WRAPPER);

		if (Boolean.valueOf(portalServiceWrapper).booleanValue()) {
			doRegisterServiceWrapper(serviceReference);
		}
	}

	protected void doEventUnregistering(ServiceEvent event) throws Exception {
		ServiceReference serviceReference = event.getServiceReference();

		boolean portalServiceWrapper = (Boolean)serviceReference.getProperty(
			OSGiConstants.PORTAL_SERVICE_WRAPPER);

		if (Boolean.valueOf(portalServiceWrapper).booleanValue()) {
			doUnregisterServiceUnwrapper(serviceReference);
		}
	}

	protected void doRegisterServiceWrapper(ServiceReference serviceReference)
		throws Exception {

		Object nextService = _bundleContext.getService(serviceReference);

		String serviceType = (String)serviceReference.getProperty(
			OSGiConstants.PORTAL_SERVICE_TYPE);

		Class<?> serviceTypeClass = serviceReference.getBundle().loadClass(
			serviceType);
		Class<?> serviceImplClass = nextService.getClass();

		Method serviceImplWrapperMethod = null;

		try {
			serviceImplWrapperMethod = serviceImplClass.getMethod(
				"setWrapped".concat(serviceTypeClass.getSimpleName()),
				serviceTypeClass);
		}
		catch (NoSuchMethodException nsme) {
			throw new IllegalArgumentException(
				"Implementation must extend " + serviceTypeClass.getName() +
					"Wrapper",
				nsme);
		}

		Object serviceProxy = PortalBeanLocatorUtil.locate(serviceType);

		if (!(serviceProxy instanceof Advised)) {
			throw new IllegalArgumentException("Service must be Advised");
		}

		if (Proxy.isProxyClass(serviceProxy.getClass())) {
			doRegisterServiceWrapper(
				serviceReference, serviceType, serviceTypeClass,
				serviceImplWrapperMethod, nextService, serviceProxy);
		}
	}

	protected void doRegisterServiceWrapper(
			ServiceReference serviceReference, String serviceType,
			Class<?> serviceTypeClass, Method serviceImplWrapperMethod,
			Object nextService, Object serviceProxy)
		throws Exception {

		AdvisedSupport advisedSupport = ServiceWrapperUtil.getAdvisedSupport(
			serviceProxy);

		Object previousService = ServiceWrapperUtil.getTarget(advisedSupport);

		ClassLoader classLoader = serviceTypeClass.getClassLoader();

		serviceImplWrapperMethod.invoke(nextService, previousService);

		Object nextTarget = Proxy.newProxyInstance(
			classLoader, new Class<?>[] {serviceTypeClass},
			new ClassLoaderBeanHandler(nextService, classLoader));

		TargetSource nextTargetSource = new SingletonTargetSource(nextTarget);

		advisedSupport.setTargetSource(nextTargetSource);

		Hashtable<String,Object> serviceProperties =
			new Hashtable<String,Object>();

		serviceProperties.put(OSGiConstants.PORTAL_SERVICE, Boolean.TRUE);
		serviceProperties.put(
			OSGiConstants.PORTAL_SERVICE_PREVIOUS, Boolean.TRUE);
		serviceProperties.put(Constants.SERVICE_RANKING, -1);

		ServiceRegistration registeredService =
			_bundleContext.registerService(
				serviceType, previousService, serviceProperties);

		_registrationBag.put(
			registeredService.getReference(), registeredService);
	}

	protected void doUnregisterServiceUnwrapper(ServiceReference serviceReference)
		throws Exception {

		String serviceType = (String)serviceReference.getProperty(
			OSGiConstants.PORTAL_SERVICE_TYPE);

		ServiceReference[] previousServiceReferences =
			_bundleContext.getServiceReferences(
				serviceType, _PREVIOUS_SERVICE_FILTER);

		if ((previousServiceReferences == null) ||
			(previousServiceReferences.length == 0)) {

			return;
		}

		ServiceReference previousServiceReference =
			previousServiceReferences[0];

		Object previousService = _bundleContext.getService(
			previousServiceReference);

		Object serviceProxy = PortalBeanLocatorUtil.locate(serviceType);

		AdvisedSupport advisedSupport = ServiceWrapperUtil.getAdvisedSupport(
			serviceProxy);

		TargetSource previousTargetSource = new SingletonTargetSource(
			previousService);

		advisedSupport.setTargetSource(previousTargetSource);

		ServiceRegistration serviceRegistration = _registrationBag.get(
			previousServiceReference);

		serviceRegistration.unregister();

		_registrationBag.remove(previousServiceReference);
	}

	private static final String _PREVIOUS_SERVICE_FILTER =
		"(&(portal.service=*)(portal.service.previous=*)(service.ranking=-1))";

	private static Log _log = LogFactoryUtil.getLog(ServiceListener.class);

	private final BundleContext _bundleContext;
	private Map<ServiceReference,ServiceRegistration> _registrationBag =
		new HashMap<ServiceReference,ServiceRegistration>();

}
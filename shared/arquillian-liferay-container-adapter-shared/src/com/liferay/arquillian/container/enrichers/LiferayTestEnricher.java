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

package com.liferay.arquillian.container.enrichers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.jboss.arquillian.test.spi.TestEnricher;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleReference;

/**
 * This class is called to enrich the test case instance with @ServiceReference.
 * The annotated fields or parameters will be resolved from the OSGi bundle
 * context.
 *
 * @author Carlos Sierra Andrés
 */
public class LiferayTestEnricher implements TestEnricher {

	public boolean contains(
		Annotation[] annotations, Class<?> annotationClass) {

		for (Annotation current : annotations) {
			if (annotationClass.isAssignableFrom(current.annotationType())) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void enrich(Object testCase) {
		Class<?> testClass = testCase.getClass();

		Field[] declaredFields = testClass.getDeclaredFields();

		for (Field declaredField : declaredFields) {
			if (declaredField.isAnnotationPresent(ServiceReference.class)) {

				_injectField(declaredField, testCase);
			}
		}
	}

	@Override
	public Object[] resolve(Method method) {
		Class<?>[] parameterTypes = method.getParameterTypes();

		Annotation[][] parametersAnnotations = method.getParameterAnnotations();

		Object[] parameters = new Object[parameterTypes.length];

		for (int i = 0; i < parameterTypes.length; i++) {
			Annotation[] parameterAnnotations = parametersAnnotations[i];

			if (contains(parameterAnnotations, ServiceReference.class)) {
				parameters[i] = _resolve(
					parameterTypes[i], method.getDeclaringClass());
			}
		}

		return parameters;
	}

	private Bundle _getBundle(Class<?> testCaseClass) {
		ClassLoader classLoader = testCaseClass.getClassLoader();

		if (classLoader instanceof BundleReference) {
			return ((BundleReference)classLoader).getBundle();
		}

		throw new RuntimeException(
			"Test is not running inside BundleContext " + classLoader);
	}

	private void _injectField(Field declaredField, Object testCase) {
		Class<?> componentClass = declaredField.getType();

		Object service = _resolve(componentClass, testCase.getClass());

		_setField(declaredField, testCase, service);
	}

	private Object _resolve(Class<?> componentClass, Class<?> testCaseClass) {
		/* We should check for the type of the injection point and act in a
		different way when we are asked for a collection instead of a single
		service.*/

		Bundle bundle = _getBundle(testCaseClass);

		BundleContext bundleContext = bundle.getBundleContext();

		org.osgi.framework.ServiceReference<?> serviceReference =
			bundleContext.getServiceReference(componentClass);

		return bundleContext.getService(serviceReference);
	}

	private void _setField(
		Field declaredField, Object testCase, Object service) {

		boolean accessible = declaredField.isAccessible();

		declaredField.setAccessible(true);

		try {
			declaredField.set(testCase, service);
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		declaredField.setAccessible(accessible);
	}

}
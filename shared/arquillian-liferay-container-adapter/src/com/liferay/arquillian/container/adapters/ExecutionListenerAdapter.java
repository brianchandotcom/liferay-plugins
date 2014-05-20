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

package com.liferay.arquillian.container.adapters;

import com.liferay.portal.kernel.test.ExecutionTestListener;
import com.liferay.portal.kernel.test.TestContext;

import java.util.Collection;

import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.ServiceLoader;
import org.jboss.arquillian.test.spi.event.suite.After;
import org.jboss.arquillian.test.spi.event.suite.AfterClass;
import org.jboss.arquillian.test.spi.event.suite.Before;
import org.jboss.arquillian.test.spi.event.suite.BeforeClass;

/**
 * This class adapts Liferay's ExecutionTestListeners to Arquillian test
 * lifecycle events. See
 * {@com.liferay.arquillian.container.enrichers.LiferayEnricherRemoteExtension}
 *
 * @author Carlos Sierra Andrés
 */
public class ExecutionListenerAdapter {

	public void after(@Observes After event) {
		for (
			ExecutionTestListener executionTestListener :
				getExecutionTestListeners()) {

			executionTestListener.runAfterTest(
				new TestContext(
					event.getTestInstance(), event.getTestMethod()));
		}
	}

	public void afterClass(@Observes AfterClass event) {
		for (
			ExecutionTestListener executionTestListener :
				getExecutionTestListeners()) {

			executionTestListener.runAfterClass(
				new TestContext(event.getTestClass().getJavaClass()));
		}
	}

	public void before(@Observes Before event) {
		for (
			ExecutionTestListener executionTestListener :
				getExecutionTestListeners()) {

			executionTestListener.runBeforeTest(
				new TestContext(
					event.getTestInstance(), event.getTestMethod()));
		}
	}

	public void beforeClass(@Observes BeforeClass event) {
		for (
			ExecutionTestListener executionTestListener :
				getExecutionTestListeners()) {

			executionTestListener.runBeforeClass(
				new TestContext(event.getTestClass().getJavaClass()));
		}
	}

	private Collection<ExecutionTestListener> getExecutionTestListeners() {
		if (_executionTestListeners == null) {
			_executionTestListeners = _serviceLoaderInstance.get().all(
				ExecutionTestListener.class);
		}

		return _executionTestListeners;
	}

	private Collection<ExecutionTestListener> _executionTestListeners;

	@Inject
	private Instance<ServiceLoader> _serviceLoaderInstance;

}
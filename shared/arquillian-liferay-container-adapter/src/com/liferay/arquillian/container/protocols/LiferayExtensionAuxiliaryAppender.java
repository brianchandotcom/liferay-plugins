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

package com.liferay.arquillian.container.protocols;

import com.liferay.arquillian.container.adapters.ExecutionListenerAdapter;

import com.liferay.arquillian.container.enrichers.LiferayTestEnricher;
import com.liferay.arquillian.container.enrichers.ServiceReference;
import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

/**
 * @author Carlos Sierra Andrés
 */
public class LiferayExtensionAuxiliaryAppender
	implements AuxiliaryArchiveAppender {

	@Override
	public Archive<?> createAuxiliaryArchive() {
		JavaArchive archive = ShrinkWrap.create(
			JavaArchive.class, "arquillian-extension-liferay-osgi.jar");

		archive.addAsServiceProvider(
			RemoteLoadableExtension.class,
			LiferayProtocolRemoteExtension.class);

		archive.addClasses(
			ServiceReference.class, LiferayTestEnricher.class,
			LiferayProtocolRemoteExtension.class,
			ExecutionListenerAdapter.class);

		return archive;
	}
}
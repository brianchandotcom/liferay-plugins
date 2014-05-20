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

package com.liferay.arquillian.processor;

import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Jar;

import java.io.ByteArrayOutputStream;
import java.io.File;

import java.util.Collection;
import java.util.jar.Manifest;

import org.jboss.arquillian.container.spi.client.deployment.Validate;
import org.jboss.arquillian.container.test.spi.TestDeployment;
import org.jboss.arquillian.container.test.spi.client.deployment.ProtocolArchiveProcessor;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

/**
 * @author Carlos Sierra Andrés
 */
public class LiferayOSGiProtocolProcessor implements ProtocolArchiveProcessor {

	public static final String MANIFEST_PATH = "META-INF/MANIFEST.MF";

	@Override
	public void process(
		TestDeployment testDeployment, Archive<?> protocolArchive) {

		Archive<?> applicationArchive = testDeployment.getApplicationArchive();

		Manifest manifest = OsgiManifestUtil.findOrCreateManifest(
			applicationArchive);

		OsgiManifestUtil.ensureProperManifest(
			manifest, testDeployment.getDeploymentName());

		OsgiManifestUtil osgiManifestUtil = OsgiManifestUtil.create(manifest);

		Collection<Archive<?>> auxiliaryArchives =
			testDeployment.getAuxiliaryArchives();

		osgiManifestUtil.appendToClassPath("WEB-INF/classes");

		for (Archive<?> auxiliaryArchive : auxiliaryArchives) {
			if (Validate.isArchiveOfType(JavaArchive.class, auxiliaryArchive)) {
				osgiManifestUtil.appendToClassPath(
					"WEB-INF/lib/" + auxiliaryArchive.getName());
			}
		}

		osgiManifestUtil.appendToImport("javax.servlet");
		osgiManifestUtil.appendToImport("javax.servlet.http");

		osgiManifestUtil.appendToImport("org.osgi.framework");

		osgiManifestUtil.appendToImport("com.liferay.portal.kernel.test");

		osgiManifestUtil.appendToImport("!org.jboss.shrinkwrap.*");
		osgiManifestUtil.appendToImport("*");

		Analyzer analyzer = new Analyzer();

		try {
			analyzer.mergeManifest(osgiManifestUtil.getManifest());

			ZipExporter zipExporter = applicationArchive.as(ZipExporter.class);

			Jar jar = new Jar(
				applicationArchive.getName(),
					zipExporter.exportAsInputStream());

			analyzer.setJar(jar);

			manifest = analyzer.calcManifest();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			manifest.write(baos);

			ByteArrayAsset byteArrayAsset = new ByteArrayAsset(
				baos.toByteArray());

			replaceManifest(applicationArchive, byteArrayAsset);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			analyzer.close();
		}
	}

	private void replaceManifest(
		Archive<?> archive, ByteArrayAsset byteArrayAsset) {

		archive.delete(MANIFEST_PATH);

		archive.add(byteArrayAsset, MANIFEST_PATH);
	}

}
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Node;

/**
 * @author Carlos Sierra Andrés
 */
public class OsgiManifestUtil {

	public static final String BUNDLE_CLASS_PATH = "Bundle-ClassPath";
	public static final String IMPORT_PACKAGE = "Import-Package";

	public static OsgiManifestUtil create(Manifest manifest) {
		if (!isOSGiManifest(manifest)) {
			throw new RuntimeException("Not an OSGi manifest");
		}

		return new OsgiManifestUtil(manifest);
	}

	public static void ensureProperManifest(
		Manifest manifest, String qualifiedName) {

		Attributes mainAttributes = manifest.getMainAttributes();

		putIfAbsent(mainAttributes, "Manifest-Version", "1.0");

		putIfAbsent(mainAttributes, "Bundle-Name", qualifiedName);

		putIfAbsent(mainAttributes, "Web-ContextPath", "/" + qualifiedName);
	}

	public static Manifest findOrCreateManifest(Archive<?> archive) {
		Node manifestNode = archive.get("META-INF/MANIFEST.MF");

		if (manifestNode == null) {
			return new Manifest();
		}

		InputStream inputStream = manifestNode.getAsset().openStream();

		try {
			Manifest manifest = new Manifest(inputStream);

			inputStream.close();

			return manifest;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean isOSGiManifest(Manifest manifest) {
		Attributes mainAttributes = manifest.getMainAttributes();

		return mainAttributes.containsKey(new Attributes.Name("Bundle-Name"));
	}

	public static void putIfAbsent(
		Attributes mainAttributes, String key, String value) {

		if (!mainAttributes.containsKey(key)) {
			mainAttributes.putValue(key, value);
		}
	}

	public void appendToClassPath(String newPath) {
		appendToKey(BUNDLE_CLASS_PATH, newPath, false);
	}

	public void appendToImport(String newPackage) {
		appendToKey(IMPORT_PACKAGE, newPackage, false);
	}

	public void appendToKey(String key, String value, boolean allowDuplicates) {
		Attributes mainAttributes = _manifest.getMainAttributes();

		String existingValue = mainAttributes.getValue(key);

		if (existingValue == null) {
			existingValue = value;
		}
		else {
			if (allowDuplicates) {
				existingValue = existingValue + ',' + value;
			}
			else {
				LinkedHashSet<String> existingValues =
					new LinkedHashSet<String>(Arrays.asList(
						existingValue.split(",")));

				existingValues.add(value);

				existingValue = join(",", existingValues);
			}
		}

		mainAttributes.putValue(key, existingValue);
	}

	public String getBundleClasspath() {
		Attributes mainAttributes = _manifest.getMainAttributes();

		return mainAttributes.getValue(BUNDLE_CLASS_PATH);
	}

	public Manifest getManifest() {
		return _manifest;
	}

	public void writeTo(OutputStream outputStream) throws IOException {
		_manifest.write(outputStream);
	}

	private OsgiManifestUtil(Manifest manifest) {
		_manifest = manifest;
	}

	private String join(String delim, Collection<String> existingValues) {
		StringBuilder stringBuilder = new StringBuilder();

		for (String each : existingValues) {
			stringBuilder.append(each);
			stringBuilder.append(delim);
		}

		stringBuilder.deleteCharAt(stringBuilder.length()-1);

		return stringBuilder.toString();
	}

	private Manifest _manifest;
}
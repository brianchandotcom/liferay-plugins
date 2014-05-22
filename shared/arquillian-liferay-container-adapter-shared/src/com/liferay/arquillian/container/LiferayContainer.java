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

package com.liferay.arquillian.container;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import java.net.MalformedURLException;

import java.util.Scanner;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.HttpClientBuilder;

import org.jboss.arquillian.container.spi.client.container.DeployableContainer;
import org.jboss.arquillian.container.spi.client.container.DeploymentException;
import org.jboss.arquillian.container.spi.client.container.LifecycleException;
import org.jboss.arquillian.container.spi.client.protocol.ProtocolDescription;
import org.jboss.arquillian.container.spi.client.protocol.metadata.HTTPContext;
import org.jboss.arquillian.container.spi.client.protocol.metadata.ProtocolMetaData;
import org.jboss.arquillian.container.spi.client.protocol.metadata.Servlet;
import org.jboss.arquillian.protocol.servlet.ServletMethodExecutor;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.descriptor.api.Descriptor;

/**
 * This class implements a Liferay Container for Arquillian.
 *
 * Upon request this LiferayContainer will deploy or undeploy a bundled test
 * into a running liferay instance. It will return all the details about
 * the deployment for Arquillian runner to execute the tests and collect the
 * results.
 *
 * This container adapter needs arquillian-plugin-deployer deployed in the
 * server for the communication.
 *
 * @author Carlos Sierra Andrés
 */
public class LiferayContainer
	implements DeployableContainer<LiferayContainerConfiguration> {

	public static final String SERVLET_2_5 = "Servlet 2.5";
	public static final String ARQUILLIAN_DEPLOY = "/arquillian-deploy";

	/**
	 * Deploys the especified archive into the container.
	 * It expects a header "Bundle-Context-Path" containing the actual
	 * deployment context of the test at hand.
	 *
	 * @param archive
	 * @return The information about the deployment for arquillian to find
	 * the test infrastructure
	 *
	 * @throws DeploymentException
	 */
	@Override
	public ProtocolMetaData deploy(Archive<?> archive)
		throws DeploymentException {

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		HttpClient httpClient = httpClientBuilder.build();

		try {
			String deploymentUrl = _buildDeploymentUrl();

			HttpPost httpPost = new HttpPost(deploymentUrl);

			MultipartEntityBuilder multipartEntityBuilder =
				_createMultipartEntity(archive);

			httpPost.setEntity(multipartEntityBuilder.build());

			HttpResponse response = httpClient.execute(httpPost);

			_checkErrors(response);

			Header contextPath = response.getFirstHeader("Bundle-Context-Path");

			ProtocolMetaData protocolMetaData = _createProtocolMetadata(
				contextPath);

			return protocolMetaData;
		}
		catch (MalformedURLException e) {
			throw new DeploymentException("Invalid URL for portal", e);
		}
		catch (ClientProtocolException e) {
			throw new DeploymentException("Invalid URL for portal", e);
		}
		catch (IOException e) {
			throw new DeploymentException("Invalid URL for portal", e);
		}
	}

	@Override
	public void deploy(Descriptor descriptor) throws DeploymentException {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public Class<LiferayContainerConfiguration> getConfigurationClass() {
		return LiferayContainerConfiguration.class;
	}

	@Override
	public ProtocolDescription getDefaultProtocol() {
		return new ProtocolDescription(SERVLET_2_5);
	}

	@Override
	public void setup(
		LiferayContainerConfiguration liferayContainerConfiguration) {

		_liferayContainerConfiguration = liferayContainerConfiguration;
	}

	/**
	 * This method is only needed in the "managed" approach. We don't implement
	 * it for the moment.
	 *
	 * @throws LifecycleException
	 */
	@Override
	public void start() throws LifecycleException {

		// NOOP

	}

	/**
	 * This method is only needed in the "managed" approach. We don't implement
	 * it for the moment.
	 *
	 * @throws LifecycleException
	 */
	@Override
	public void stop() throws LifecycleException {

		// NOOP

	}

	/**
	 * Undeploys the test bundle from liferay instance using
	 * arquillian-plugin-deployer.
	 *
	 * @param archive
	 * @throws DeploymentException
	 */
	@Override
	public void undeploy(Archive<?> archive) throws DeploymentException {
		String deploymentUrl = _buildDeploymentUrl();

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		HttpClient httpClient = httpClientBuilder.build();

		HttpDelete httpDelete = new HttpDelete(deploymentUrl);

		try {
			httpClient.execute(httpDelete);
		}
		catch (IOException e) {
			throw new DeploymentException("Error undeploying", e);
		}
	}

	@Override
	public void undeploy(Descriptor descriptor) throws DeploymentException {
		throw new UnsupportedOperationException("Not implemented");
	}

	private String _buildDeploymentUrl() {
		String arquillianDeployerContext =
			_liferayContainerConfiguration.getArquillianDeployerContext();

		String host = _liferayContainerConfiguration.getHost();

		int port = _liferayContainerConfiguration.getPort();

		String moduleFrameworkContext =
			_liferayContainerConfiguration.getModuleFrameworkContext();

		String portalContextRoot =
			_liferayContainerConfiguration.getPortalContextRoot();

		String protocol = _liferayContainerConfiguration.getProtocol();

		return protocol + "://" + host + ":" + port + "/" + portalContextRoot +
			"/" + moduleFrameworkContext + "/" + arquillianDeployerContext +
			ARQUILLIAN_DEPLOY;
	}

	private MultipartEntityBuilder _createMultipartEntity(Archive<?> archive) {
		MultipartEntityBuilder multipartEntityBuilder =
			MultipartEntityBuilder.create();

		ZipExporter zipView = archive.as(ZipExporter.class);

		InputStream inputStream = zipView.exportAsInputStream();

		multipartEntityBuilder.addPart(
			archive.getName(),
			new InputStreamBody(inputStream, archive.getName()));

		return multipartEntityBuilder;
	}

	private ProtocolMetaData _createProtocolMetadata(Header contextPath) {
		ProtocolMetaData protocolMetaData = new ProtocolMetaData();

		HTTPContext httpContext = new HTTPContext(
			_liferayContainerConfiguration.getHost(),
			_liferayContainerConfiguration.getPort());

		httpContext.add(new Servlet(
			ServletMethodExecutor.ARQUILLIAN_SERVLET_NAME,
			contextPath.getValue()));

		protocolMetaData.addContext(httpContext);

		return protocolMetaData;
	}

	private void _checkErrors(HttpResponse response)
		throws DeploymentException, IOException {

		StatusLine statusLine = response.getStatusLine();

		int statusCode = statusLine.getStatusCode();

		if (statusCode != HttpStatus.SC_OK) {
			final String stackTrace = _getBodyAsString(response);

			throw new DeploymentException(stackTrace) {

				@Override
				public void printStackTrace(PrintWriter printWriter) {
					printWriter.println("REMOTE: " + stackTrace);
				}

				@Override
				public void printStackTrace(PrintStream printStream) {
					printStream.println("REMOTE: " + stackTrace);
				}

				@Override
				public void printStackTrace() {
					System.err.println("REMOTE: " + stackTrace);
				}

				@Override
				public synchronized Throwable fillInStackTrace() {
					return this;
				}
			};
		}
	}

	private String _getBodyAsString(HttpResponse response) throws IOException {
		HttpEntity responseEntity = response.getEntity();

		InputStream content = responseEntity.getContent();

		Header contentEncoding = responseEntity.getContentEncoding();

		String encoding =
			contentEncoding != null ? contentEncoding.getValue() : "ISO-8859-1";

		Scanner scanner = new Scanner(content, encoding).useDelimiter("\\A");

		try {
			return scanner.hasNext() ? scanner.next() : "";
		}
		finally {
			scanner.close();
		}
	}

	private LiferayContainerConfiguration _liferayContainerConfiguration;

}
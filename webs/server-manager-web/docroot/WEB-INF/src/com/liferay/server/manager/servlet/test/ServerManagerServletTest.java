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

package com.liferay.server.manager.servlet.test;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jonathan Potter
 */
public class ServerManagerServletTest {

	@Before
	public void setUp() {
		_host = "localhost";
		_pluginName = "server-manager-web";
		_port = "8080";

		_urlPrefix = "http://" + _host + ":" + _port + "/" + _pluginName;
	}

	@After
	public void tearDown() {
	}

	@Test
	public void isAlive() throws Exception {
		doRequest(
			new HttpGet(),
			new URI(_urlPrefix + "/is-alive"));
	}

	@Test
	public void deployHook() throws Exception {
		doRequest(
			new HttpPost(),
			new URI(_urlPrefix + "/plugins"),
			new File(
				"/Users/liferay/Data/application-data/eclipse/zTest",
				"sample-wrapper-hook-6.1.0.1.war"));

	}

	@Test
	public void deployLayout() throws Exception {
		doRequest(
			new HttpPost(),
			new URI(_urlPrefix + "/plugins"),
			new File(
				"/Users/liferay/Data/application-data/eclipse/zTest",
				"1-2-1-columns-layouttpl-6.1.0.1.war"));
	}

	@Test
	public void deployPortlet() throws Exception {
		doRequest(
			new HttpPost(),
			new URI(_urlPrefix + "/plugins"),
			new File(
				"/Users/liferay/Data/application-data/eclipse/zTest",
				"remote-fox-portlet.war"));
	}

	@Test
	public void deployTheme() throws Exception {
		doRequest(
			new HttpPost(),
			new URI(_urlPrefix + "/plugins"),
			new File(
				"/Users/liferay/Data/application-data/eclipse/zTest",
				"beautiful-day-theme-6.1.0.1.war"));
	}

	@Test
	public void deployWeb() throws Exception {
		doRequest(
			new HttpPost(),
			new URI(_urlPrefix + "/plugins"),
			new File(
				"/Users/liferay/Data/application-data/eclipse/zTest",
				"vldap-web-6.1.0.1.war"));
	}

	@Test
	public void deployUpdate() throws Exception {
		doRequest(
			new HttpPut(),
			new URI(_urlPrefix + "/plugins/remote-fox-portlet"),
			new File(
				"/Users/liferay/Data/application-data/eclipse/zTest",
				"remote-fox-portlet-delta.war"));
	}

	@Test
	public void undeploy() throws Exception {
		doRequest(
			new HttpDelete(), new URI(_urlPrefix + "/plugins/vldap-web"));
	}

	@Test
	public void logLiferay() throws Exception {
		doRequest(new HttpGet(), new URI(_urlPrefix + "/log/sysout"));
	}

	@Test
	public void logAppServer() throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet();

		int offset = 0;

		// Loop forever and read the log, this will need to be terminated
		// manually
		while (true) {
			request.setURI(new URI(_urlPrefix + "/log/syserr/" + offset + "?format=raw"));
			HttpResponse response = httpClient.execute(request);

			Reader reader =
				new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			char[] buffer = new char[1024 * 4];
			int numRead = 0;

			while ((numRead = reader.read(buffer)) != -1) {
				String s = new String(buffer, 0, numRead);

				System.out.print(s);

				offset += numRead;
			}

			Thread.sleep(1 * 1000);
		}

//		IOUtils.copy(reader, System.out);
//		printResponse(response);

	}

	@Test
	public void debugPort() throws Exception {
		doRequest(new HttpGet(), new URI(_urlPrefix + "/debug-port"));
	}

	public void doRequest(HttpRequestBase request, URI uri) {
		HttpClient httpClient = new DefaultHttpClient();

		request.setURI(uri);

		try {
			HttpResponse response = httpClient.execute(request);
			printResponse(response);
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	public void doRequest(
		HttpEntityEnclosingRequestBase request, URI uri, File file) {

		HttpClient httpClient = new DefaultHttpClient();

		request.setURI(uri);

		if (file != null) {
			MultipartEntity entity = new MultipartEntity();
			FileBody contentBody = new FileBody(file);
			entity.addPart("payload", contentBody);
			request.setEntity(entity);
		}

		try {
			HttpResponse response = httpClient.execute(request);
			printResponse(response);
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	public Reader getReader(HttpResponse response) throws Exception {
		return new BufferedReader(new InputStreamReader(
			response.getEntity().getContent()));
	}

	public void printResponse(HttpResponse response) throws Exception {
		Reader reader = getReader(response);
		IOUtils.copy(reader, System.out);
	}

	protected String _host;
	protected String _pluginName;
	protected String _port;
	protected String _urlPrefix;
	protected enum RequestType {DELETE, GET, POST, PUT};

	private static Log _log =
		LogFactoryUtil.getLog(ServerManagerServletTest.class);

}

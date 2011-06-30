package com.liferay.server.manager.servlet.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ServerManagerServletTest {

	@Before
	public void setUp() {
		_host = "localhost";
		_port = "8080";
		_pluginName = "server-manager-web";
	}

	@After
	public void tearDown() {
	}

	@Test
	public void isAlive () throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet();

		request.setURI(new URI("http://" + _host + ":" + _port + "/" + _pluginName + "/is-alive"));

		HttpResponse response = httpClient.execute(request);

		printResponse(response);
	}

	@Test
	public void deploy () throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost request = new HttpPost();

		request.setURI(new URI("http://" + _host + ":" + _port + "/" + _pluginName + "/deploy/vldap-web"));

		MultipartEntity entity = new MultipartEntity();
		FileBody contentBody = new FileBody(new File("vldap-web-6.1.0.1.war"));
		entity.addPart("payload", contentBody);
		request.setEntity(entity);

		HttpResponse response = httpClient.execute(request);

		printResponse(response);
	}

	@Test
	public void deployUpdate () throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPut request = new HttpPut();

		request.setURI(new URI("http://" + _host + ":" + _port + "/" + _pluginName + "/deploy/vldap-web"));

		MultipartEntity entity = new MultipartEntity();
		FileBody contentBody = new FileBody(new File("vldap-web-6.1.0.2.war"));
		entity.addPart("payload", contentBody);
		request.setEntity(entity);

		HttpResponse response = httpClient.execute(request);

		printResponse(response);
	}

	@Test
	public void undeploy () throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		HttpDelete request = new HttpDelete();

		request.setURI(new URI("http://" + _host + ":" + _port + "/" + _pluginName + "/undeploy/vldap-web"));

		HttpResponse response = httpClient.execute(request);

		printResponse(response);
	}

	@Test
	public void log () throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet();

		request.setURI(new URI("http://" + _host + ":" + _port + "/" + _pluginName + "/log"));

		HttpResponse response = httpClient.execute(request);

		printResponse(response);
	}

	@Test
	public void debugPort () throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet();

		request.setURI(new URI("http://" + _host + ":" + _port + "/" + _pluginName + "/debug-port"));

		HttpResponse response = httpClient.execute(request);

		printResponse(response);
	}

	public void printResponse (HttpResponse response) throws Exception {
		HttpEntity entity = response.getEntity();

		BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));

		while (true) {
			String line = reader.readLine();

			if (line == null) {
				break;
			}

			System.out.println(line);
		}
	}

	protected String _host;
	protected String _port;
	protected String _pluginName;

}

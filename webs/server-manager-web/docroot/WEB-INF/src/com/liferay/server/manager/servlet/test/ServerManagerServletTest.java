package com.liferay.server.manager.servlet.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;

import org.apache.commons.io.IOUtils;
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
		FileBody contentBody = new FileBody(new File("/Users/liferay/Data/application-data/eclipse/zTest", "vldap-web-6.1.0.1.war"));
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
		FileBody contentBody = new FileBody(new File("/Users/liferay/Data/application-data/eclipse/zTest", "vldap-web-6.1.0.2.war"));
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
	public void logLiferay () throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet();

		request.setURI(new URI("http://" + _host + ":" + _port + "/" + _pluginName + "/log/sysout"));

		HttpResponse response = httpClient.execute(request);

		printResponse(response);
	}

	@Test
	public void logAppServer () throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet();

		int offset = 0;

		// Loop forever and read the log, this will need to be terminated
		// manually
		while (true) {
			request.setURI(new URI("http://" + _host + ":" + _port + "/" + _pluginName + "/log/syserr/" + offset));
			HttpResponse response = httpClient.execute(request);

			Reader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

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
	public void debugPort () throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet();

		request.setURI(new URI("http://" + _host + ":" + _port + "/" + _pluginName + "/debug-port"));

		HttpResponse response = httpClient.execute(request);

		printResponse(response);
	}

	public Reader getReader(HttpResponse response) throws Exception {
		return new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	}

	public void printResponse (HttpResponse response) throws Exception {
		Reader reader = getReader(response);
		IOUtils.copy(reader, System.out);
	}

	protected String _host;
	protected String _port;
	protected String _pluginName;

}

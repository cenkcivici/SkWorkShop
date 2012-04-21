package com.sk.util.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.message.BasicNameValuePair;

public class HttpConnection {

	private HttpClient httpClient;
	private HttpPost httpPost;
	private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(0);

	public HttpConnection(HttpClient httpClient, HttpPost httpPost) {
		this.httpClient = httpClient;
		this.httpPost = httpPost;
	}

	public void addParameter(String paramName, Object value) {

		nameValuePairs.add(new BasicNameValuePair(paramName, value.toString()));
	}

	public HttpResult execute() throws ClientProtocolException, IOException {
		if (!nameValuePairs.isEmpty()) {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
		}
		
		HttpResponse httpResponse = httpClient.execute(httpPost);

		HttpResult httpResult = new HttpResult();
		httpResult.setHttpEntity(new BufferedHttpEntity(httpResponse.getEntity()));
		httpResult.setHttpStatus(httpResponse.getStatusLine().getStatusCode());

		return httpResult;
	}

}

package com.sk.util.http;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Component;

@Component
public class HttpConnectionFactory {

	public HttpConnection createConnection(String uri) {
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpRequestBase =  new HttpPost(uri);
		
		return new HttpConnection(httpClient, httpRequestBase);
	}

}

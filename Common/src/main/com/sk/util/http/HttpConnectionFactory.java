package com.sk.util.http;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HttpConnectionFactory {
	
	@Value("${vpos.connection.timeout}")
	private Integer connectionTimeoutMillis;
	
	@Value("${vpos.socket.timeout}")
	private Integer socketTimeoutMillis; 

	public HttpConnection createConnection(String uri) {
		
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, connectionTimeoutMillis);
		HttpConnectionParams.setSoTimeout(httpParams, socketTimeoutMillis);
		
		HttpClient httpClient = new DefaultHttpClient(httpParams);
//		httpClient = WebClientWrapper.wrapClient(httpClient);
		HttpPost httpPost =  new HttpPost(uri);
		
		return new HttpConnection(httpClient, httpPost);
	}

}

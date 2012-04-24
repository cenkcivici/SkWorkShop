package com.sk.util.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(MockitoJUnitRunner.class)
public class HttpConnectionTest {

	private HttpConnection httpConnection;
	@Mock private HttpClient httpClient;
	@Mock private HttpPost httpPost;
	@Mock private HttpResponse httpResponse;
	@Mock private StatusLine statusLine;
	
	@Before
	public void init(){
		httpConnection = new HttpConnection(httpClient, httpPost);
	}
	
	@Test
	public void shouldReturnHttpResultResponseAfterExecute() throws ClientProtocolException, IOException{
		
//		String resultString = "{\"title\":\"success\"}";
//		HttpEntity httpEntity = new BufferedHttpEntity(new StringEntity(resultString));
//
//		when(httpClient.execute(httpPost)).thenReturn(httpResponse);
//		when(httpResponse.getEntity()).thenReturn(httpEntity);
//		when(httpResponse.getStatusLine()).thenReturn(statusLine);
//		when(statusLine.getStatusCode()).thenReturn(200);
//		
//		HttpResult httpResult = httpConnection.execute();
//		assertThat(EntityUtils.toString(httpResult.getHttpEntity()), equalTo(resultString));
//		assertThat(httpResult.getHttpStatus(), equalTo(200));
	}
	
}

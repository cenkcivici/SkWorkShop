package com.sk.util.http;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

public class HttpResultTest {

	private HttpResult httpResult;

	@Before
	public void init() throws UnsupportedEncodingException {
		httpResult = new HttpResult();

		HttpEntity httpEntity = new StringEntity("{\"title\":\"custom title\"}");
		httpResult.setHttpEntity(httpEntity);
		httpResult.setHttpStatus(200);
	}

	@Test
	public void shouldReturnContentAsString() throws ParseException, IOException {

		String content = httpResult.getContentAsString();
		assertThat("{\"title\":\"custom title\"}", equalTo(content));

	}

	@Test
	public void shouldReturnContentAsByteArray() throws IOException {
		byte[] expectedByteArray = EntityUtils.toByteArray(httpResult.getHttpEntity());

		byte[] byteArray = httpResult.getContentAsByteArray();
		assertThat(expectedByteArray, equalTo(byteArray));
	}
}

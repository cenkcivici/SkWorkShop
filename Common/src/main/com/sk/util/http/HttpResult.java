package com.sk.util.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

public class HttpResult {

	private HttpEntity httpEntity;
	private int httpStatus;

	public String getContentAsString() throws ParseException, IOException {
		return EntityUtils.toString(httpEntity, "UTF-8");
	}
	
	public byte[] getContentAsByteArray() throws IOException {
		return EntityUtils.toByteArray(httpEntity);
	}
	
	public HttpEntity getHttpEntity() {
		return httpEntity;
	}
	public void setHttpEntity(HttpEntity httpEntity) {
		this.httpEntity = httpEntity;
	}
	public int getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}
	
}

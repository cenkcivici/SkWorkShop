package com.sk.frontend.web.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

import javax.servlet.http.Cookie;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class CookieUtilsTest {

	private MockHttpServletRequest request = new MockHttpServletRequest();
	private MockHttpServletResponse response = new MockHttpServletResponse();

	@Test
	public void shouldGetCookieFromRequest() {
		request.setCookies(new Cookie("cookie", "value"));

		Cookie fromRequest = CookieUtils.getCookieByName(request, "cookie");

		assertThat(fromRequest.getValue(), equalTo("value"));
	}

	@Test
	public void shouldBeNullIfNoCookieExist() {
		Cookie fromRequest = CookieUtils.getCookieByName(request, "cookie");
		assertThat(fromRequest, nullValue());
	}
	
	@Test
	public void shouldAddCookieToResponse() {
		CookieUtils.addCookie(response, "cookie", "value", 1000);
		
		Cookie cookie = response.getCookie("cookie");
		
		assertThat(cookie.getValue(), equalTo("value"));
		assertThat(cookie.getMaxAge(), equalTo(1000));
	}

}

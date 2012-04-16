package com.sk.frontend.web.util;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

	private CookieUtils(){}
	
	public static Cookie getCookieByName(ServletRequest request, String cookieName) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		Cookie[] cookies = httpRequest.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookieName.equalsIgnoreCase(cookie.getName())) {
					return cookie;
				}
			}
		}

		return null;
	}

	public static void addCookie(HttpServletResponse response, String name, String value, int expiry) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(expiry);
		response.addCookie(cookie);
	}

}

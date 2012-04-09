package com.sk.admin.web.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;

public class PrimeAdsOpenSessionInViewFilter extends OpenSessionInViewFilter {

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return request.getRequestURI().contains("javax.faces.resource");
	}

}

package com.sk.service;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriUtils;

import com.sk.service.exception.ServiceException;

@Service
public class UrlGeneratorService {

	public String generateUrlFrom(String text) {
		String url = text.toLowerCase(Locale.ENGLISH);
		
		url = StringUtils.replace(url, " ", "-");
		try {
			url = UriUtils.encodeUri(url, "UTF8");
		} catch (UnsupportedEncodingException e) {
			throw new ServiceException(e);
		}
		
		return url;
	}
	
	
	
	

}

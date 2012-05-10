package com.sk.service;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UrlGeneratorService {

	public String generateUrlFrom(String text) {
		String url = text.toLowerCase(Locale.ENGLISH);

		url = StringUtils.replace(url, " ", "-");
		url = unAccentString(url);
		url = url.replaceAll("[^a-z 0-9 \\-]*", "");
		return url;
	}

	private String unAccentString(String url) {
		url = url.replaceAll("[ı]", "i");
		url = Normalizer.normalize(url, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		url = pattern.matcher(url).replaceAll("");
		return url;
	}

}

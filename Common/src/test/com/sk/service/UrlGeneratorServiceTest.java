package com.sk.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class UrlGeneratorServiceTest {
	
	private UrlGeneratorService service = new UrlGeneratorService();
	
	@Test
	public void shouldLowerCaseText() {
		String url = service.generateUrlFrom("UPPERCASETEXT");
		
		assertThat(url, equalTo("uppercasetext"));
	}
	
	@Test
	public void shouldBeAsIsWhenLowerCaseAndNoSpaces() {
		String url = service.generateUrlFrom("validurl");
		
		assertThat(url, equalTo("validurl"));
	}
	
	@Test
	public void shouldReplaceSpacesWithDash() {
		String url = service.generateUrlFrom("text with spaces");
		
		assertThat(url, equalTo("text-with-spaces"));
	}
	
	@Test
	public void shouldUrlEncodeString() {
		String url = service.generateUrlFrom("title with illegal characters like $%^");
		
		assertThat(url, equalTo("title-with-illegal-characters-like-$%25%5E"));
	}
}


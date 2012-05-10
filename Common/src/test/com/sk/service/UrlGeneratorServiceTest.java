package com.sk.service;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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
	public void shouldRemoveMeaninglessNonAsciiCharactersFromString() {
		String url = service.generateUrlFrom("!'^+%istanbul");

		assertThat(url, equalTo("istanbul"));
	}
	
	@Test
	public void shouldReplaceMeaningfulNonAsciiCharactersFromString(){
		String url = service.generateUrlFrom("èéêëûùüïîıàâğşçö");
		
		assertThat(url, equalTo("eeeeuuuiiiaagsco"));
	}

	@Test
	public void shouldRemoveMeaninglessAsciiChars_ReplaceSpacesWithDash_ReplaceMeaningFullNonAsciiCharsFromString() {
		String url = service.generateUrlFrom("üğiçğ üŞÜdü!");

		assertThat(url, equalTo("ugicg-usudu"));
	}
	
	@Test
	public void shouldHandleUpperCaseTurkishCharacters(){
		String url = service.generateUrlFrom("İĞÜŞÇÖ");
		
		assertThat(url, equalTo("igusco"));
	}
}

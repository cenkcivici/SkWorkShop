package com.sk.frontend.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;


public class UniqueIdGeneratorServiceTest {
	
	private UniqueIdGeneratorService service = new UniqueIdGeneratorService();
	
	@Test
	public void shouldGenerateAUniqueId() {
		
		String newUniqueId = service.newUniqueId();
		
		assertThat(newUniqueId, notNullValue());
		
	}

}

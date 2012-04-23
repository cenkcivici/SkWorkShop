package com.sk.frontend;

import static junit.framework.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class IndexIT {

	private WebDriver webDriver;
	
	@Before
	public void init(){
		webDriver = new FirefoxDriver();
	}
	
	@After
	public void destroy(){
		webDriver.close();
	}
	
	@Test
	public void shouldShowCategoryForm(){
		
		webDriver.get("http://localhost:8080/Frontend/app/index");
		assertEquals("Frontend", webDriver.getTitle());
	}
}

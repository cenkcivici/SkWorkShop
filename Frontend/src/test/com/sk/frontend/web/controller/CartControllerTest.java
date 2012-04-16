package com.sk.frontend.web.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;


@RunWith(MockitoJUnitRunner.class)
public class CartControllerTest {

	private MockHttpServletRequest request = new MockHttpServletRequest();
	
	private CartController controller;
	
	@Before
	public void before() {
		controller = new CartController();
	}
	
	

	
	

}

package com.sk.frontend.web.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.sk.domain.ShoppingCart;
import com.sk.service.CreditCardProfileService;
import com.sk.util.builder.ShoppingCartBuilder;


@RunWith(MockitoJUnitRunner.class)
public class InstallmentPlanControllerTest {

	@Mock
	private CreditCardProfileService creditCardProfileService;
	
	private InstallmentPlanController controller;
	
	private MockHttpServletRequest request = new MockHttpServletRequest();
	
	@Before
	public void before() {
		controller = new InstallmentPlanController(creditCardProfileService);
	}
	
	@Test
	public void shouldDisplayPaymentPlanEntryForCreditCard() {
		ShoppingCart cart = new ShoppingCartBuilder().build();
		request.setAttribute("cart", cart);

		ModelAndView mav = controller.show("111111", request);
		
		assertThat(mav.getModelMap(), hasKey("paymentEntry"));
	}
	
	
	
	
	

}

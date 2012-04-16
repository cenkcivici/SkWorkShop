package com.sk.frontend.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(MockitoJUnitRunner.class)
public class PaymentControllerTest {

	private PaymentController controller;

	@Before
	public void before() {
		controller = new PaymentController();
	}

	@Test
	public void shouldAddCreditCardPaymentMethod() {

		ModelAndView mav = controller.show();

		assertThat(mav.getModelMap().containsKey("payment"), equalTo(true));
	}

}

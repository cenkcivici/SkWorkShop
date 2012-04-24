package com.sk.frontend.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import com.sk.domain.CreditCard;
import com.sk.domain.CreditCardPaymentMethod;
import com.sk.domain.CreditCardType;
import com.sk.domain.Shopper;
import com.sk.domain.ShoppingCart;
import com.sk.frontend.web.helper.CreditCardPopulatorHelper;
import com.sk.service.OrderService;
import com.sk.service.ShopperService;
import com.sk.service.payment.ResponseStatus;
import com.sk.service.payment.VPOSResponse;
import com.sk.service.payment.garanti.GarantiVPOSService;
import com.sk.util.builder.CreditCardBuilder;
import com.sk.util.builder.CreditCardPaymentMethodBuilder;
import com.sk.util.builder.ShopperBuilder;
import com.sk.util.builder.ShoppingCartBuilder;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(MockitoJUnitRunner.class)
public class PaymentControllerTest {

	private PaymentController controller;

	@Mock
	private OrderService orderService;
	@Mock
	private BindingResult bindingResult;
	@Mock
	private ShopperService shopperService;
	@Mock
	private GarantiVPOSService garantiVPOSService;
	@Mock
	private CreditCardPopulatorHelper cardPopulatorHelper;

	@Before
	public void before() {
		controller = new PaymentController(orderService, shopperService, garantiVPOSService,cardPopulatorHelper);
	}

	@Test
	public void shouldCreateModelForPayment() {

		ModelAndView mav = controller.getPaymentMAV(null);
		assertThat(mav.getModelMap().containsKey("payment"), equalTo(true));
		assertThat(mav.getModelMap().containsKey("years"), equalTo(true));
		assertThat(mav.getModelMap().containsKey("months"), equalTo(true));
		assertThat(mav.getModelMap().containsKey("creditCardTypes"), equalTo(true));
		
		verify(cardPopulatorHelper).getYears();
		verify(cardPopulatorHelper).getMonths();
		verify(cardPopulatorHelper).getCreditCardTypes();

	}

	@Test
	public void shouldSetCardInfoIfExists() {
		CreditCard card = new CreditCardBuilder().owner("Shopper").cardNumber("12341234").cvc("003").month("06").year("12").cardType(CreditCardType.VISA).build();
		CreditCard encryptedCard = new CreditCardBuilder().owner("ZAQXSW").cardNumber("ABCDABCD").cvc("ZXC").month("CVB").year("VBN").cardType(CreditCardType.VISA).build();
		Shopper shopper = new ShopperBuilder().creditCard(encryptedCard).build();

		when(shopperService.getStubShopper()).thenReturn(shopper);
		when(shopperService.decryptCreditCardInfo(encryptedCard)).thenReturn(card);

		ModelAndView mav = controller.show();
		CreditCardPaymentMethod payment = (CreditCardPaymentMethod) mav.getModelMap().get("payment");

		assertThat(payment.getCreditCard().getOwner(), equalTo("Shopper"));
		assertThat(payment.getCreditCard().getCardNumber(), equalTo("12341234"));
		assertThat(payment.getCreditCard().getCvc(), equalTo("003"));
		assertThat(payment.getCreditCard().getMonth(), equalTo("06"));
		assertThat(payment.getCreditCard().getYear(), equalTo("12"));
		assertThat(payment.getCreditCard().getCreditCardType(), equalTo(CreditCardType.VISA));
		assertFalse((Boolean) mav.getModelMap().get("showSaveCheck"));
	}

	@Test
	public void shouldNotSetCardInfoIfNotExists() {
		Shopper shopper = new ShopperBuilder().build();
		when(shopperService.getStubShopper()).thenReturn(shopper);

		ModelAndView mav = controller.show();
		CreditCardPaymentMethod payment = (CreditCardPaymentMethod) mav.getModelMap().get("payment");
		assertNull(payment.getCreditCard());
		assertTrue((Boolean) mav.getModelMap().get("showSaveCheck"));
	}

	@Test
	public void shouldNotCallOrderServiceIfCreditCardIsNotValid() {
		when(bindingResult.hasErrors()).thenReturn(true);
		controller.submit(null, bindingResult, null);
		verifyZeroInteractions(orderService);
		verify(bindingResult).hasErrors();
	}

	@Test
	public void shoulCallOrderServiceIfCardIsValid() {
		when(bindingResult.hasErrors()).thenReturn(false);

		CreditCardPaymentMethod cardPaymentMethod = new CreditCardPaymentMethodBuilder().build();
		ShoppingCart shoppingCart = new ShoppingCartBuilder().build();
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setAttribute("cart", shoppingCart);

		VPOSResponse response = new VPOSResponse(ResponseStatus.SUCCESS);
		when(garantiVPOSService.makePayment(cardPaymentMethod, shoppingCart.getTotalCost())).thenReturn(response);

		ModelAndView mav = controller.submit(cardPaymentMethod, bindingResult, request);

		verify(orderService).createOrder(shoppingCart, cardPaymentMethod);
		verify(bindingResult).hasErrors();
		assertThat(mav.getViewName(), equalTo("confirm"));
	}

	@Test
	public void shouldSaveCreditCardInfoIfSaveChecked() {

		CreditCardPaymentMethod cardPaymentMethod = new CreditCardPaymentMethodBuilder().build();
		MockHttpServletRequest request = new MockHttpServletRequest();
		ShoppingCart shoppingCart = new ShoppingCartBuilder().build();
		request.setAttribute("cart", shoppingCart);
		request.setParameter("saveCardInfo", "1");

		Shopper shopper = new ShopperBuilder().build();
		CreditCard card = new CreditCardBuilder().owner(cardPaymentMethod.getCreditCard().getOwner()).cardNumber(cardPaymentMethod.getCreditCard().getCardNumber()).cvc(cardPaymentMethod.getCreditCard().getCvc()).month(cardPaymentMethod.getCreditCard().getMonth())
				.year(cardPaymentMethod.getCreditCard().getYear()).cardType(cardPaymentMethod.getCreditCard().getCreditCardType()).build();
		VPOSResponse response = new VPOSResponse(ResponseStatus.SUCCESS);

		when(garantiVPOSService.makePayment(cardPaymentMethod, shoppingCart.getTotalCost())).thenReturn(response);
		when(bindingResult.hasErrors()).thenReturn(false);
		when(shopperService.getStubShopper()).thenReturn(shopper);

		controller.submit(cardPaymentMethod, bindingResult, request);

		verify(shopperService).encryptAndsaveCardInfo(shopper, card);
	}
}

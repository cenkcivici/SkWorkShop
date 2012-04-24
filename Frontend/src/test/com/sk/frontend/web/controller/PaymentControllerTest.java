package com.sk.frontend.web.controller;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

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
import com.sk.service.OrderService;
import com.sk.service.ShopperService;
import com.sk.service.payment.ResponseStatus;
import com.sk.service.payment.VPOSResponse;
import com.sk.service.payment.garanti.GarantiVPOSService;
import com.sk.util.builder.CreditCardBuilder;
import com.sk.util.builder.CreditCardPaymentMethodBuilder;
import com.sk.util.builder.ShopperBuilder;
import com.sk.util.builder.ShoppingCartBuilder;

@RunWith(MockitoJUnitRunner.class)
public class PaymentControllerTest {

	private PaymentController controller;

	@Mock private OrderService orderService;
	@Mock private BindingResult bindingResult;
	@Mock private ShopperService shopperService;
	@Mock private GarantiVPOSService garantiVPOSService;

	@Before
	public void before() {
		controller = new PaymentController(orderService, shopperService, garantiVPOSService);
	}

	@Test
	public void shouldCreateModelForPayment() {

		ModelAndView mav = controller.getPaymentMAV(null);
		assertThat(mav.getModelMap().containsKey("payment"), equalTo(true));
		assertThat(mav.getModelMap().containsKey("years"), equalTo(true));
		assertThat(mav.getModelMap().containsKey("months"), equalTo(true));
		assertThat(mav.getModelMap().containsKey("creditCardTypes"), equalTo(true));

	}
	
	@Test
	public void shouldSetCardInfoIfExists(){
		CreditCard card = new CreditCardBuilder().owner("Shopper").cardNumber("12341234").cvc("003").month("06").year("12").cardType(CreditCardType.VISA).build();
		CreditCard encryptedCard = new CreditCardBuilder().owner("ZAQXSW").cardNumber("ABCDABCD").cvc("ZXC").month("CVB").year("VBN").cardType(CreditCardType.VISA).build();
		Shopper shopper = new ShopperBuilder().creditCard(encryptedCard).build();
		
		when(shopperService.getStubShopper()).thenReturn(shopper);
		when(shopperService.decryptCreditCardInfo(encryptedCard)).thenReturn(card);
		
		ModelAndView mav = controller.show();
		CreditCardPaymentMethod payment = (CreditCardPaymentMethod)mav.getModelMap().get("payment");

		assertThat(payment.getOwner(), equalTo("Shopper"));
		assertThat(payment.getCardNumber(), equalTo("12341234"));
		assertThat(payment.getCvc(), equalTo("003"));
		assertThat(payment.getMonth(), equalTo("06"));
		assertThat(payment.getYear(), equalTo("12"));
		assertThat(payment.getCreditCardType(), equalTo(CreditCardType.VISA));
		assertFalse((Boolean)mav.getModelMap().get("showSaveCheck"));
	}
	
	@Test
	public void shouldNotSetCardInfoIfNotExists(){
		Shopper shopper = new ShopperBuilder().build();
		when(shopperService.getStubShopper()).thenReturn(shopper);
		
		ModelAndView mav = controller.show();
		CreditCardPaymentMethod payment = (CreditCardPaymentMethod)mav.getModelMap().get("payment");
		payment.setCardNumber(null); //TODO for easier testing'mis
		
		assertTrue((Boolean)mav.getModelMap().get("showSaveCheck"));
		assertNull(payment.getOwner());
		assertNull(payment.getCardNumber());
		assertNull(payment.getCvc());
		assertNull(payment.getMonth());
		assertNull(payment.getYear());
	}
	
	@Test
	public void shouldCreateCreditCardTypes() {

		Map<String, String> creditCardTypes = controller.getCreditCardTypes();
		assertThat(creditCardTypes.keySet().contains(CreditCardType.VISA.name()), equalTo(true));
		assertThat(creditCardTypes.keySet().contains(CreditCardType.MASTERCARD.name()), equalTo(true));
		assertThat(creditCardTypes.keySet().size(), equalTo(2));

	}

	@Test
	public void shouldCreateYears() {

		TreeMap<String, String> years = (TreeMap<String, String>) controller.getYears();
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);

		assertThat(years.keySet().size(), equalTo(12));
		assertThat(years.firstKey(), equalTo("" + currentYear));
		assertThat(years.lastKey(), equalTo("" + (currentYear + 11)));

	}

	@Test
	public void shouldCreateMonths() {

		TreeMap<String, String> manths = (TreeMap<String, String>) controller.getMonths();

		assertThat(manths.keySet().size(), equalTo(12));
		assertThat(manths.firstKey(), equalTo("01"));
		assertThat(manths.lastKey(), equalTo("12"));

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
		when(garantiVPOSService.makePayment(cardPaymentMethod)).thenReturn(response);
		
		ModelAndView mav = controller.submit(cardPaymentMethod, bindingResult, request);

		verify(orderService).createOrder(shoppingCart, cardPaymentMethod);
		verify(bindingResult).hasErrors();
		assertThat(mav.getViewName(), equalTo("confirm"));
	}

	@Test
	public void shouldSaveCreditCardInfoIfSaveChecked(){
		
		CreditCardPaymentMethod cardPaymentMethod = new CreditCardPaymentMethodBuilder().build();
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("saveCardInfo", "1");
		
		Shopper shopper = new ShopperBuilder().build();
		CreditCard card = new CreditCardBuilder().owner(cardPaymentMethod.getOwner()).cardNumber(cardPaymentMethod.getCardNumber())
							   .cvc(cardPaymentMethod.getCvc()).month(cardPaymentMethod.getMonth()).year(cardPaymentMethod.getYear())
							   .cardType(cardPaymentMethod.getCreditCardType()).build();
		VPOSResponse response = new VPOSResponse(ResponseStatus.SUCCESS);

		when(garantiVPOSService.makePayment(cardPaymentMethod)).thenReturn(response);
		when(bindingResult.hasErrors()).thenReturn(false);
		when(shopperService.getStubShopper()).thenReturn(shopper);
		
		controller.submit(cardPaymentMethod, bindingResult, request);
		
		verify(shopperService).encryptAndsaveCardInfo(shopper, card);
	}
}

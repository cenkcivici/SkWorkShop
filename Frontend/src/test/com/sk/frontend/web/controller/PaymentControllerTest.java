package com.sk.frontend.web.controller;

<<<<<<< HEAD
=======
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import com.sk.domain.CreditCard;
import com.sk.domain.CreditCardPaymentMethod;
import com.sk.domain.CreditCardType;
import com.sk.domain.Order;
import com.sk.domain.PaymentMethod;
import com.sk.domain.ProductWithQuantity;
import com.sk.domain.Shopper;
import com.sk.domain.ShoppingCart;
import com.sk.domain.coupon.ShopperCoupon;
import com.sk.frontend.service.ShoppingCartService;
import com.sk.frontend.web.helper.CreditCardPopulatorHelper;
import com.sk.frontend.web.interceptor.ShoppingCartInterceptor;
import com.sk.service.CouponService;
import com.sk.service.CreditCardProfileService;
import com.sk.service.OrderService;
import com.sk.service.ShopperService;
import com.sk.service.payment.ResponseStatus;
import com.sk.service.payment.VPOSResponse;
import com.sk.service.payment.garanti.GarantiVPOSService;
import com.sk.util.builder.CreditCardBuilder;
import com.sk.util.builder.CreditCardPaymentMethodBuilder;
import com.sk.util.builder.OrderBuilder;
import com.sk.util.builder.ProductBuilder;
import com.sk.util.builder.ProductWithQuantityBuilder;
import com.sk.util.builder.ShopperBuilder;
import com.sk.util.builder.ShopperCouponBuilder;
import com.sk.util.builder.ShoppingCartBuilder;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

@RunWith(MockitoJUnitRunner.class)
public class PaymentControllerTest {

	private PaymentController controller;

	@Mock private OrderService orderService;
	@Mock private BindingResult bindingResult;
	@Mock private ShopperService shopperService;
	@Mock private GarantiVPOSService garantiVPOSService;
	@Mock private CreditCardPopulatorHelper cardPopulatorHelper;
	@Mock private CreditCardProfileService creditCardProfileService;
	@Mock private ShoppingCartService cartService;
	@Mock private CouponService couponService;
	
	private MockHttpServletRequest request = new MockHttpServletRequest();
	private MockHttpServletResponse response = new MockHttpServletResponse();

	private ShoppingCart cart = new ShoppingCartBuilder().build();

	@Before
	public void before() {
		request.setAttribute(ShoppingCartInterceptor.CART, cart);
		controller = new PaymentController(orderService, shopperService, garantiVPOSService,cardPopulatorHelper,creditCardProfileService,cartService,couponService);
	}

	@Test
	public void shouldCreateModelForPayment() {

		ModelAndView mav = controller.getPaymentMAV(null,request);
		assertThat(mav.getModelMap(),hasKey("payment"));
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldSetYearsWhileCreatingModel(){
		Map<String,String> years = new HashMap<String,String>();
		years.put("2012", "2012");
		years.put("2013", "2013");
		
		when(cardPopulatorHelper.getYears()).thenReturn(years);
		ModelAndView mav = controller.getPaymentMAV(null,request);
		assertThat(mav.getModelMap(),hasKey("years"));
		Map<String,Object> fromModel = (Map<String, Object>) mav.getModelMap().get("years");
		assertThat(fromModel,hasKey("2012"));
		assertThat(fromModel,hasKey("2013"));
		assertThat(fromModel.keySet().size(), equalTo(2));
		
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldSetMonthsWhileCreatingModel(){
		Map<String,String> months = new HashMap<String,String>();
		months.put("01", "01");
		months.put("02", "02");
		
		when(cardPopulatorHelper.getMonths()).thenReturn(months);
		
		ModelAndView mav = controller.getPaymentMAV(null,request);
		assertThat(mav.getModelMap(),hasKey("months"));
		Map<String,Object> fromModel = (Map<String, Object>) mav.getModelMap().get("months");
		assertThat(fromModel,hasKey("01"));
		assertThat(fromModel,hasKey("02"));
		assertThat(fromModel.keySet().size(), equalTo(2));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldSetCreditCardTypesWhileCreatingModel(){
		Map<String,String> creditCardTypes = new HashMap<String,String>();
		creditCardTypes.put(CreditCardType.MASTERCARD.name(), CreditCardType.MASTERCARD.name());
		creditCardTypes.put(CreditCardType.VISA.name(), CreditCardType.VISA.name());
		
		when(cardPopulatorHelper.getCreditCardTypes()).thenReturn(creditCardTypes);
		ModelAndView mav = controller.getPaymentMAV(null,request);
		assertThat(mav.getModelMap(),hasKey("creditCardTypes"));
		Map<String,Object> fromModel = (Map<String, Object>) mav.getModelMap().get("creditCardTypes");
		assertThat(fromModel,hasKey(CreditCardType.MASTERCARD.name()));
		assertThat(fromModel,hasKey(CreditCardType.VISA.name()));
		assertThat(fromModel.keySet().size(), equalTo(2));

	}
	
	@Test
	public void shouldSetCardInfoIfExists() {
		CreditCard card = new CreditCardBuilder().owner("Shopper").cardNumber("12341234").cvc("003").month("06").year("12").cardType(CreditCardType.VISA).build();
		CreditCard encryptedCard = new CreditCardBuilder().owner("ZAQXSW").cardNumber("ABCDABCD").cvc("ZXC").month("CVB").year("VBN").cardType(CreditCardType.VISA).build();
		Shopper shopper = new ShopperBuilder().creditCard(encryptedCard).build();

		when(shopperService.getStubShopper()).thenReturn(shopper);
		when(shopperService.decryptCreditCardInfo(encryptedCard)).thenReturn(card);

		ModelAndView mav = controller.show(request);
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

		ModelAndView mav = controller.show(request);
		CreditCardPaymentMethod payment = (CreditCardPaymentMethod) mav.getModelMap().get("payment");
		assertNull(payment.getCreditCard());
		assertTrue((Boolean) mav.getModelMap().get("showSaveCheck"));
	}

	@Test
	public void shouldNotCallOrderServiceIfCreditCardIsNotValid() {
		when(bindingResult.hasErrors()).thenReturn(true);
		controller.submit(null, bindingResult, request,response);
		verifyZeroInteractions(orderService);
		verify(bindingResult).hasErrors();
	}

	@Test
	public void shouldAddBonusInfoIfUserWantsToUseBonus() {
		when(bindingResult.hasErrors()).thenReturn(false);

		CreditCardPaymentMethod cardPaymentMethod = new CreditCardPaymentMethodBuilder().build();
		ShoppingCart shoppingCart = new ShoppingCartBuilder().build();
		request.setAttribute("cart", shoppingCart);
		request.setParameter("useBonus","10.5");

		VPOSResponse vposResponse = new VPOSResponse(ResponseStatus.SUCCESS);
		
		Order order = new OrderBuilder().paymentMethod(cardPaymentMethod).shoppingCart(shoppingCart).build();
		when(orderService.createOrder(shoppingCart,cardPaymentMethod)).thenReturn(order);
		when(garantiVPOSService.makePayment(order)).thenReturn(vposResponse);
		
		ModelAndView mav = controller.submit(cardPaymentMethod, bindingResult, request,response);

		verify(orderService).createOrder(shoppingCart, cardPaymentMethod);
		verify(bindingResult).hasErrors();
		assertThat(mav.getViewName(), equalTo("redirect:orderSuccess"));
		assertThat(cardPaymentMethod.getBonusUsed(),equalTo(10.5d));
	}
	
	@Test
	public void shouldCallOrderServiceIfCardIsValid() {
		when(bindingResult.hasErrors()).thenReturn(false);
		
		CreditCardPaymentMethod cardPaymentMethod = new CreditCardPaymentMethodBuilder().build();
		ShoppingCart shoppingCart = new ShoppingCartBuilder().build();
		request.setAttribute("cart", shoppingCart);
		
		VPOSResponse vposResponse = new VPOSResponse(ResponseStatus.SUCCESS);
		
		Order order = new OrderBuilder().paymentMethod(cardPaymentMethod).shoppingCart(shoppingCart).build();
		when(orderService.createOrder(shoppingCart,cardPaymentMethod)).thenReturn(order);
		when(garantiVPOSService.makePayment(order)).thenReturn(vposResponse);
		
		ModelAndView mav = controller.submit(cardPaymentMethod, bindingResult, request,response);
		
		verify(orderService).createOrder(shoppingCart, cardPaymentMethod);
		verify(bindingResult).hasErrors();
		assertThat(mav.getViewName(), equalTo("redirect:orderSuccess"));
	}

	@Test
	public void shouldSaveCreditCardInfoIfSaveChecked() {

		CreditCard card = new CreditCardBuilder().build();
		Shopper shopper = new ShopperBuilder().creditCard(card).build();
		CreditCardPaymentMethod cardPaymentMethod = new CreditCardPaymentMethodBuilder().creditCard(card).build();
		ShoppingCart shoppingCart = new ShoppingCartBuilder().build();
		request.setAttribute("cart", shoppingCart);
		request.setParameter("saveCardInfo", "1");

		VPOSResponse vposResponse = new VPOSResponse(ResponseStatus.SUCCESS);

		Order order = new OrderBuilder().paymentMethod(cardPaymentMethod).shoppingCart(shoppingCart).build();
		when(orderService.createOrder(shoppingCart,cardPaymentMethod)).thenReturn(order);
		when(garantiVPOSService.makePayment(order)).thenReturn(vposResponse);
		when(bindingResult.hasErrors()).thenReturn(false);
		when(shopperService.getStubShopper()).thenReturn(shopper);

		controller.submit(cardPaymentMethod, bindingResult, request,response);

		verify(shopperService).encryptAndsaveCardInfo(shopper, card);
	}
	
	@Test
	public void shouldSaveOrderIfExistWhenSubmit(){
		request.setParameter("couponStringParam", "ABCabcZXCc");
		
		Order order = new OrderBuilder().build();
		when(orderService.createOrder(any(ShoppingCart.class), any(PaymentMethod.class))).thenReturn(order);
		
		VPOSResponse vposResponse = new VPOSResponse(ResponseStatus.SUCCESS);
		when(garantiVPOSService.makePayment(order)).thenReturn(vposResponse);
		
		CreditCardPaymentMethod payment = new CreditCardPaymentMethodBuilder().build();
		controller.submit(payment, bindingResult, request, response);
		
		verify(couponService).useCouponIfAvailable("ABCabcZXCc", order);
		verify(orderService).save(order);
	}
	
	@Test
	public void shouldReturnCouponStatusAsValidIfCouponIsAvailable(){
		Shopper shopper = new ShopperBuilder().build();
		ShopperCoupon shopperCoupon = new ShopperCouponBuilder().discount(10D).build();
		when(couponService.getUnusedCoupon(eq("ABCabcZXCz"), any(Order.class))).thenReturn(shopperCoupon);
		when(shopperService.getStubShopper()).thenReturn(shopper);
		
		ProductWithQuantity productWithQuantity = new ProductWithQuantityBuilder().product(new ProductBuilder().price(25).build()).build();
		ShoppingCart shoppingCart = new ShoppingCartBuilder().items(productWithQuantity).build();
		request.setAttribute("cart", shoppingCart);
		
		ModelAndView mav = controller.useCoupon("ABCabcZXCz", request);
		
		assertThat(mav.getViewName(), equalTo("couponStatus"));
		assertThat((String)mav.getModelMap().get("status"), equalTo("Valid"));
		assertThat((Double)mav.getModelMap().get("discountedAmount"), equalTo(15D));
	}
	
	@Test
	public void shouldReturnCouponStatusAsInvalidIfCouponIsNotAvailable(){
		Shopper shopper = new ShopperBuilder().build();
		when(couponService.getUnusedCoupon(eq("ABCabcZXCz"), any(Order.class))).thenReturn(null);
		when(shopperService.getStubShopper()).thenReturn(shopper);
		
		ProductWithQuantity productWithQuantity = new ProductWithQuantityBuilder().product(new ProductBuilder().price(25).build()).build();
		ShoppingCart shoppingCart = new ShoppingCartBuilder().items(productWithQuantity).build();
		request.setAttribute("cart", shoppingCart);
		
		ModelAndView mav = controller.useCoupon("ABCabcZXCz", request);
		
		assertThat(mav.getViewName(), equalTo("couponStatus"));
		assertThat((String)mav.getModelMap().get("status"), equalTo("Invalid"));
		assertThat((Double)mav.getModelMap().get("discountedAmount"), equalTo(25D));
	}
}

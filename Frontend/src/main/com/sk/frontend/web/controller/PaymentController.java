package com.sk.frontend.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sk.domain.CreditCard;
import com.sk.domain.CreditCardPaymentMethod;
import com.sk.domain.Shopper;
import com.sk.domain.ShoppingCart;
import com.sk.frontend.web.helper.CreditCardPopulatorHelper;
import com.sk.frontend.web.interceptor.ShoppingCartInterceptor;
import com.sk.frontend.web.validator.CreditCardValidator;
import com.sk.service.CreditCardProfileService;
import com.sk.service.OrderService;
import com.sk.service.ShopperService;
import com.sk.service.payment.VPOSResponse;
import com.sk.service.payment.garanti.GarantiVPOSService;

@Controller
@RequestMapping("/payment")
public class PaymentController {

	@InitBinder
	public void init(WebDataBinder binder) {
		binder.setValidator(new CreditCardValidator());
	}

	private OrderService orderService;
	private GarantiVPOSService garantiVPOSService;
	private ShopperService shopperService;
	private CreditCardPopulatorHelper cardPopulatorHelper;
	private CreditCardProfileService creditCardProfileService;

	@Autowired
	public PaymentController(OrderService orderService, ShopperService shopperService, GarantiVPOSService garantiVPOSService, CreditCardPopulatorHelper cardPopulatorHelper,CreditCardProfileService creditCardProfileService) {
		this.orderService = orderService;
		this.shopperService = shopperService;
		this.garantiVPOSService = garantiVPOSService;
		this.cardPopulatorHelper = cardPopulatorHelper;
		this.creditCardProfileService = creditCardProfileService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView show(HttpServletRequest request) {

		Shopper shopper = shopperService.getStubShopper();
		CreditCardPaymentMethod payment = new CreditCardPaymentMethod();

		Boolean showSaveCheck = Boolean.TRUE;
		if (shopper.hasAnyCreditCardInfo()) {
			showSaveCheck = Boolean.FALSE;

			CreditCard encryptedCard = shopper.getCreditCardList().iterator().next();
			CreditCard card = shopperService.decryptCreditCardInfo(encryptedCard);

			payment.setCreditCard(card);
		}

		ModelAndView mav = getPaymentMAV(payment,request);
		mav.addObject("showSaveCheck", showSaveCheck);
		return mav;
	}

	protected ModelAndView getPaymentMAV(CreditCardPaymentMethod creditCardPaymentMethod,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("payment");

		mav.addObject("payment", creditCardPaymentMethod);
		mav.addObject("creditCardTypes", cardPopulatorHelper.getCreditCardTypes());
		mav.addObject("months", cardPopulatorHelper.getMonths());
		mav.addObject("years", cardPopulatorHelper.getYears());
		
		ShoppingCart cart = (ShoppingCart) request.getAttribute(ShoppingCartInterceptor.CART);
		mav.addObject("paymentPlan",creditCardProfileService.paymentsFor(cart));

		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView submit(@Validated @ModelAttribute("payment") CreditCardPaymentMethod payment, BindingResult binder, HttpServletRequest request) {
		if (binder.hasErrors()) {
			ModelAndView mav = getPaymentMAV(payment,request);
			mav.addObject("showSaveCheck", true);
			return mav;
		}

		ShoppingCart cart = getShoppingCart(request);
		VPOSResponse response = garantiVPOSService.makePayment(payment, cart.getTotalCost());
		if (response.isSuccessful()) {
			return createOrder(payment, request);
		} else {
			return showError(payment,request);
		}

	}

	protected ModelAndView showError(CreditCardPaymentMethod payment,HttpServletRequest request) {
		ModelAndView modelAndView = getPaymentMAV(payment,request);
		modelAndView.addObject("paymentFailed", true);
		return modelAndView;
	}

	protected ModelAndView createOrder(CreditCardPaymentMethod payment, HttpServletRequest request) {
		if (StringUtils.equals(request.getParameter("saveCardInfo"), "1") ) {
			Shopper shopper = shopperService.getStubShopper();

			CreditCard card = payment.getCreditCard();
			shopperService.encryptAndsaveCardInfo(shopper, card);
		}

		ShoppingCart shoppingCart = getShoppingCart(request);
		orderService.createOrder(shoppingCart, payment);
		return new ModelAndView("confirm");
	}

	protected ShoppingCart getShoppingCart(HttpServletRequest request) {
		return (ShoppingCart) request.getAttribute("cart");
	}


}

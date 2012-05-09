package com.sk.frontend.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.sk.domain.InstallmentPlan;
import com.sk.domain.Order;
import com.sk.domain.Shopper;
import com.sk.domain.ShoppingCart;
import com.sk.frontend.service.ShoppingCartService;
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

	private OrderService orderService;
	private GarantiVPOSService garantiVPOSService;
	private ShopperService shopperService;
	private CreditCardPopulatorHelper cardPopulatorHelper;
	private CreditCardProfileService creditCardProfileService;
	private ShoppingCartService shoppingCartService;

	@InitBinder
	public void init(WebDataBinder binder) {
		binder.setValidator(new CreditCardValidator());
	}

	@Autowired
	public PaymentController(OrderService orderService, ShopperService shopperService, GarantiVPOSService garantiVPOSService, CreditCardPopulatorHelper cardPopulatorHelper, CreditCardProfileService creditCardProfileService, ShoppingCartService shoppingCartService) {
		this.orderService = orderService;
		this.shopperService = shopperService;
		this.garantiVPOSService = garantiVPOSService;
		this.cardPopulatorHelper = cardPopulatorHelper;
		this.creditCardProfileService = creditCardProfileService;
		this.shoppingCartService = shoppingCartService;
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

		ModelAndView mav = getPaymentMAV(payment, request);
		mav.addObject("showSaveCheck", showSaveCheck);
		return mav;
	}

	protected ModelAndView getPaymentMAV(CreditCardPaymentMethod creditCardPaymentMethod, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("payment");

		mav.addObject("payment", creditCardPaymentMethod);
		mav.addObject("creditCardTypes", cardPopulatorHelper.getCreditCardTypes());
		mav.addObject("months", cardPopulatorHelper.getMonths());
		mav.addObject("years", cardPopulatorHelper.getYears());

		ShoppingCart cart = (ShoppingCart) request.getAttribute(ShoppingCartInterceptor.CART);
		mav.addObject("paymentPlan", creditCardProfileService.paymentsFor(cart));

		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView submit(@Validated @ModelAttribute("payment") CreditCardPaymentMethod payment, BindingResult binder, HttpServletRequest request, HttpServletResponse response) {
		if (binder.hasErrors()) {
			ModelAndView mav = getPaymentMAV(payment, request);
			mav.addObject("showSaveCheck", true);
			return mav;
		}

		initializeInstallmentPlan(payment, request);
		initializeBonusPoints(payment, request);
		
		ShoppingCart cart = getShoppingCart(request);
		Order order = orderService.createOrder(cart, payment);
		VPOSResponse vposResponse = garantiVPOSService.makePayment(order);
		if (vposResponse.isSuccessful()) {
			return saveOrder(order, request, response);
		} else {
			return showError(payment, vposResponse, request);
		}

	}

	private void initializeBonusPoints(CreditCardPaymentMethod payment, HttpServletRequest request) {
		String useBonusValue = request.getParameter("useBonus");
		if( StringUtils.isNotBlank(useBonusValue)){
			Double bonus = Double.parseDouble(useBonusValue);
			if( bonus != null ){
				payment.setBonusUsed(bonus); 
			}
		}
	}
	
	private void initializeInstallmentPlan(CreditCardPaymentMethod payment, HttpServletRequest request) {
		String selectPlan = request.getParameter("selectPlan");
		if (StringUtils.isNotEmpty(selectPlan) && StringUtils.isNumeric(selectPlan)) {
			InstallmentPlan installmentPlan = creditCardProfileService.findInstallmentById(Long.parseLong(selectPlan));
			payment.setInstallmentPlan(installmentPlan);
		}
	}

	protected ModelAndView showError(CreditCardPaymentMethod payment, VPOSResponse response, HttpServletRequest request) {
		ModelAndView modelAndView = getPaymentMAV(payment, request);
		modelAndView.addObject("errorOccured", true);
		modelAndView.addObject("errorMessage", response.getInfoText());
		modelAndView.addObject("errorMessageDetail", response.getDetailMessage());
		return modelAndView;
	}

	protected ModelAndView saveOrder(Order order, HttpServletRequest request, HttpServletResponse response) {
		CreditCardPaymentMethod payment = (CreditCardPaymentMethod) order.getPaymentMethod();
		if (StringUtils.equals(request.getParameter("saveCardInfo"), "1")) {
			Shopper shopper = shopperService.getStubShopper();

			CreditCard card = payment.getCreditCard();
			shopperService.encryptAndsaveCardInfo(shopper, card);
		}

		shopperService.encryptCreditCardInfo(payment);
		orderService.save(order);

		shoppingCartService.deleteShoppingCart(request, response);

		return new ModelAndView("redirect:orderSuccess");
	}

	protected ShoppingCart getShoppingCart(HttpServletRequest request) {
		return (ShoppingCart) request.getAttribute("cart");
	}

}

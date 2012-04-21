package com.sk.frontend.web.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

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

import com.sk.domain.CreditCardPaymentMethod;
import com.sk.domain.CreditCardType;
import com.sk.domain.ShoppingCart;
import com.sk.frontend.web.validator.CreditCardValidator;
import com.sk.service.OrderService;
import com.sk.service.payment.VPOSResponse;
import com.sk.service.payment.garanti.GarantiVPOSService;

@Controller
@RequestMapping("/payment")
public class PaymentController {

	@InitBinder
	public void init(WebDataBinder binder) {
		binder.setValidator(new CreditCardValidator());
	}

	@Autowired
	private OrderService orderService;

	@Autowired
	private GarantiVPOSService garantiVPOSService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView show() {
		return getPaymentMAV(new CreditCardPaymentMethod());
	}

	protected ModelAndView getPaymentMAV(CreditCardPaymentMethod creditCardPaymentMethod) {
		ModelAndView mav = new ModelAndView("payment");

		mav.addObject("payment", creditCardPaymentMethod);
		mav.addObject("creditCardTypes", getCreditCardTypes());
		mav.addObject("months", getMonths());
		mav.addObject("years", getYears());

		return mav;
	}

	protected Map<String, String> getCreditCardTypes() {
		Map<String, String> creditCardTypes = new HashMap<String, String>();
		for (CreditCardType creditCardType : CreditCardType.values()) {
			creditCardTypes.put(creditCardType.name(), creditCardType.getName());
		}
		return creditCardTypes;
	}

	protected Map<String, String> getMonths() {
		Map<String, String> months = new TreeMap<String, String>();
		for (int i = 1; i < 13; i++) {
			if (i < 10) {
				months.put("0" + i, "0" + i);
			} else {
				months.put("" + i, "" + i);
			}
		}
		return months;
	}

	protected Map<String, String> getYears() {
		Map<String, String> years = new TreeMap<String, String>();
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		for (int year = currentYear; year < currentYear + 12; year++) {
			years.put("" + year, "" + year);
		}
		return years;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView submit(@Validated @ModelAttribute("payment") CreditCardPaymentMethod payment, BindingResult binder, HttpServletRequest request) {
		if (binder.hasErrors()) {
			return getPaymentMAV(payment);
		}

		VPOSResponse response = garantiVPOSService.makePayment(payment);
		if (response.isSuccessful()) {
			ShoppingCart shoppingCart = (ShoppingCart) request.getAttribute("cart");
			orderService.createOrder(shoppingCart, payment);
			return new ModelAndView("confirm");
		} else {
			ModelAndView modelAndView = getPaymentMAV(payment);
			modelAndView.addObject("paymentFailed", true);
			return modelAndView;
		}

	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

}

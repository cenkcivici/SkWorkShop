package com.sk.frontend.web.controller;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sk.domain.CreditCardProfile;
import com.sk.domain.InstallmentPlan;
import com.sk.domain.ShoppingCart;
import com.sk.frontend.web.interceptor.ShoppingCartInterceptor;
import com.sk.service.CreditCardProfileService;

@Controller
@RequestMapping("/installmentplan")
public class InstallmentPlanController {
	
	private CreditCardProfileService creditCardProfileService;

	@Autowired
	public InstallmentPlanController(CreditCardProfileService creditCardProfileService) {
		this.creditCardProfileService = creditCardProfileService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.POST)
	public ModelAndView show(@RequestParam("creditCardNumber") String creditcardnumber,HttpServletRequest request)  {
		ModelAndView mav = new ModelAndView("installmentplanselection");
		
		ShoppingCart cart = (ShoppingCart) request.getAttribute(ShoppingCartInterceptor.CART);
		
		Entry<CreditCardProfile,Map<InstallmentPlan, Double>> paymentEntry = creditCardProfileService.availablePlanFor(creditcardnumber,cart);
		mav.addObject("paymentEntry",paymentEntry);
		return mav;
	}

	
	

}

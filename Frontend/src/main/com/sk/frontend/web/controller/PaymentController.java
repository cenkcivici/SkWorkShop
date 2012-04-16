package com.sk.frontend.web.controller;

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
import com.sk.frontend.web.validator.CreditCardValidator;

@Controller
@RequestMapping("/payment")
public class PaymentController {

	@InitBinder
	public void init(WebDataBinder binder) {
		binder.setValidator(new CreditCardValidator());
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView("payment");

		mav.addObject("payment", new CreditCardPaymentMethod());

		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView submit(@Validated @ModelAttribute("payment") CreditCardPaymentMethod payment, BindingResult binder) {
		if (binder.hasErrors()) {
			ModelAndView mav = new ModelAndView("payment");
			mav.addObject("payment", payment);
			return mav;
		}

		ModelAndView mav = new ModelAndView("confirm");
		return mav;
	}

}

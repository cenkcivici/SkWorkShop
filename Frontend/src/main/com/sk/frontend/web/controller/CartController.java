package com.sk.frontend.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sk.domain.ShoppingCart;

@Controller
@RequestMapping("/cart")
public class CartController {

	@RequestMapping(value = "/{show}", method = RequestMethod.GET)
	public ModelAndView show(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("cartDetail");
		ShoppingCart cart = (ShoppingCart) request.getAttribute("cart");
		
		mav.addObject("cart",cart);
		
		return mav;
	}

}

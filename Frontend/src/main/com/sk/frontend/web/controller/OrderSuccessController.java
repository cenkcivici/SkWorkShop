package com.sk.frontend.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/orderSuccess")
public class OrderSuccessController {
	
	@RequestMapping(value = "/orderSuccess", method = RequestMethod.GET)
	public ModelAndView orderSuccess() {
		return new ModelAndView();

	}
	
	

}

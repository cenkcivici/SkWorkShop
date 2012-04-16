package com.sk.frontend.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.sk.domain.Product;
import com.sk.domain.ShoppingCart;
import com.sk.service.ProductService;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Value("#{app.root}")
	private String appRoot;
	
	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("cartDetail");
		ShoppingCart cart = (ShoppingCart) request.getAttribute("cart");
		
		mav.addObject("cart",cart);
		
		return mav;
	}
	
	@RequestMapping(value = "/add/{productUrl}", method = RequestMethod.POST)
	public ModelAndView addToCart(@PathVariable String productUrl,HttpServletRequest request) {
		Product foundProduct = productService.findByUrl(productUrl);
		
		ShoppingCart cart = (ShoppingCart) request.getAttribute("cart");

		cart.addProduct(foundProduct);
		
		RedirectView view = new RedirectView(appRoot + "/show");
		return new ModelAndView(view);
	}
	

}

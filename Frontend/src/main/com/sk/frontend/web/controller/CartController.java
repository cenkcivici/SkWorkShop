package com.sk.frontend.web.controller;

import javax.servlet.http.Cookie;
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
import com.sk.frontend.web.interceptor.ShoppingCartInterceptor;
import com.sk.frontend.web.util.CookieUtils;
import com.sk.service.CacheService;
import com.sk.service.ProductService;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	
	private static final String CART = "cart";

	@Value("${app.root}")
	private String appRoot;
	
	@Autowired private ProductService productService;
	@Autowired private CacheService cacheService;

	public CartController() {}
	
	public CartController(ProductService productService, CacheService cacheService) {
		this.productService = productService;
		this.cacheService = cacheService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("cartDetail");
		ShoppingCart cart = (ShoppingCart) request.getAttribute(CART);
		
		mav.addObject(CART, cart);
		
		return mav;
	}
	
	@RequestMapping(value = "/add/{productUrl}", method = RequestMethod.POST)
	public ModelAndView addToCart(@PathVariable String productUrl,HttpServletRequest request) {
		Product foundProduct = productService.findByUrl(productUrl);
		
		ShoppingCart cart = (ShoppingCart) request.getAttribute(CART);

		cart.addProduct(foundProduct);
		Cookie cookie = CookieUtils.getCookieByName(request, CART);
		cacheService.put(cookie.getValue(), cart, ShoppingCartInterceptor.SEVENDAYS);

		
		RedirectView view = new RedirectView(appRoot + "/cart/show");
		return new ModelAndView(view);
	}

	public void setAppRoot(String appRoot) {
		this.appRoot = appRoot;
	}
	

}

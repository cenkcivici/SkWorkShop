package com.sk.frontend.web.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sk.domain.ShoppingCart;
import com.sk.frontend.service.UniqueIdGeneratorService;
import com.sk.frontend.web.util.CookieUtils;
import com.sk.service.CacheService;

public class ShoppingCartInterceptor extends HandlerInterceptorAdapter {

	public static final int SEVENDAYS = 60*60*24*7;

	@Autowired
	private CacheService cacheService;
	@Autowired
	private UniqueIdGeneratorService uniqueIdGeneratorService;

	public ShoppingCartInterceptor() {
	}

	public ShoppingCartInterceptor(CacheService cacheService, UniqueIdGeneratorService uniqueIdGeneratorService) {
		this.cacheService = cacheService;
		this.uniqueIdGeneratorService = uniqueIdGeneratorService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Cookie cookie = CookieUtils.getCookieByName(request, "cart");
		String shoppingCartId = null;
		ShoppingCart shoppingCart = null;
		
		if (cookie != null) {
			shoppingCartId = cookie.getValue();
			shoppingCart = (ShoppingCart) cacheService.get(shoppingCartId);
		}
		
		if (shoppingCart == null) {
			shoppingCart = new ShoppingCart();
			shoppingCartId = uniqueIdGeneratorService.newUniqueId();
			cacheService.put(shoppingCartId, shoppingCart, SEVENDAYS);
			CookieUtils.addCookie(response,"cart",shoppingCartId,SEVENDAYS/1000);
		}
		
		request.setAttribute("cart", shoppingCart);

		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		ShoppingCart cart = (ShoppingCart) request.getAttribute("cart");
		modelAndView.addObject("cart", cart);
	}

}

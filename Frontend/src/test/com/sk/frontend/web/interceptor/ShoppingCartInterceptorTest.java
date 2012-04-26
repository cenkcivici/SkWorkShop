package com.sk.frontend.web.interceptor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.Cookie;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import com.sk.domain.ShoppingCart;
import com.sk.frontend.service.UniqueIdGeneratorService;
import com.sk.service.CacheService;
import com.sk.util.builder.ShoppingCartBuilder;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartInterceptorTest {

	@Mock
	private CacheService cacheService;

	@Mock
	private UniqueIdGeneratorService uniqueIdGeneratorService;

	private MockHttpServletRequest request = new MockHttpServletRequest();
	private MockHttpServletResponse response = new MockHttpServletResponse();

	private ShoppingCartInterceptor interceptor;

	@Before
	public void before() {
		interceptor = new ShoppingCartInterceptor(cacheService, uniqueIdGeneratorService);
	}

	@Test
	public void shouldRetrieveCartFromCookie() throws Exception {
		ShoppingCart cart = new ShoppingCartBuilder().build();

		request.setCookies(new Cookie("cart", "123456"));
		when(cacheService.get("123456")).thenReturn(cart);

		interceptor.preHandle(request, response, null);

		ShoppingCart fromRequest = (ShoppingCart) request.getAttribute("cart");

		assertThat(fromRequest, sameInstance(cart));
	}

	@Test
	public void shouldCreateANewCartAndAddCookieWhenCacheHasExpired() throws Exception {
		request.setCookies(new Cookie("cart", "123456"));
		when(cacheService.get("123456")).thenReturn(null);
		when(uniqueIdGeneratorService.newUniqueId()).thenReturn("UNIQUE");

		interceptor.preHandle(request, response, null);

		ShoppingCart newCart = (ShoppingCart) request.getAttribute("cart");

		assertThat(newCart.getItems().size(), equalTo(0));
		verify(cacheService).put("UNIQUE", newCart, ShoppingCartInterceptor.SEVENDAYS);
	}

	@Test
	public void shouldAddNewCartIdToResponse() throws Exception {
		request.setCookies(new Cookie("cart", "123456"));
		when(cacheService.get("123456")).thenReturn(null);
		when(uniqueIdGeneratorService.newUniqueId()).thenReturn("UNIQUE");

		interceptor.preHandle(request, response, null);

		Cookie cookie = response.getCookie("cart");
		assertThat(cookie.getValue(), equalTo("UNIQUE"));
		assertThat(cookie.getMaxAge(), equalTo(ShoppingCartInterceptor.SEVENDAYS ));
	}

	@Test
	public void shouldCreateANewCartForNewVisitors() throws Exception {
		when(uniqueIdGeneratorService.newUniqueId()).thenReturn("UNIQUE");

		interceptor.preHandle(request, response, null);

		ShoppingCart newCart = (ShoppingCart) request.getAttribute("cart");

		assertThat(newCart.getItems().size(), equalTo(0));
		verify(cacheService).put("UNIQUE", newCart, ShoppingCartInterceptor.SEVENDAYS);
	}

	@Test
	public void shouldAddNewCartIdToResponseForNewVisitors() throws Exception {

		when(uniqueIdGeneratorService.newUniqueId()).thenReturn("UNIQUE");

		interceptor.preHandle(request, response, null);

		Cookie cookie = response.getCookie("cart");
		assertThat(cookie.getValue(), equalTo("UNIQUE"));
		assertThat(cookie.getMaxAge(), equalTo(ShoppingCartInterceptor.SEVENDAYS));
	}

	@Test
	public void shouldPopulateModelWithCart() throws Exception {
		ShoppingCart cart = new ShoppingCartBuilder().build();
		request.setAttribute("cart", cart);

		ModelAndView mav = new ModelAndView();

		interceptor.postHandle(request, response, null, mav);

		assertThat(mav.getModel(), hasEntry("cart", (Object) cart));
	}

}

package com.sk.service.coupon;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.sk.domain.Category;
import com.sk.domain.Order;
import com.sk.domain.Product;
import com.sk.domain.ProductWithQuantity;
import com.sk.domain.ShoppingCart;
import com.sk.util.builder.CategoryBuilder;
import com.sk.util.builder.OrderBuilder;
import com.sk.util.builder.ProductBuilder;
import com.sk.util.builder.ProductWithQuantityBuilder;
import com.sk.util.builder.ShoppingCartBuilder;


public class CategoryCouponCheckerTest {

	private CategoryCouponChecker couponChecker;
	private Category category;
	
	@Before
	public void init(){
		category = new CategoryBuilder().build();
		couponChecker = new CategoryCouponChecker(category);
	}
	
	@Test
	public void shouldReturnTrueIfCouponOwnerIsCategory(){
		
		Product product = new ProductBuilder().category(category).build();
		ProductWithQuantity productWithQuantity = new ProductWithQuantityBuilder().product(product).build();
		ShoppingCart shoppingCart = new ShoppingCartBuilder().items(productWithQuantity).build();
		Order order = new OrderBuilder().shoppingCart(shoppingCart).build();
		
		Boolean canUseCoupon = couponChecker.canUseCoupon(order);
		
		assertTrue(canUseCoupon);
	}
	
	@Test
	public void shouldReturnFalseIfCouponOwnerIsNotCategory(){
		Product product = new ProductBuilder().build();
		ProductWithQuantity productWithQuantity = new ProductWithQuantityBuilder().product(product).build();
		ShoppingCart shoppingCart = new ShoppingCartBuilder().items(productWithQuantity).build();
		Order order = new OrderBuilder().shoppingCart(shoppingCart).build();
		
		Boolean canUseCoupon = couponChecker.canUseCoupon(order);
		
		assertFalse(canUseCoupon);
	}
	
}

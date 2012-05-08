package com.sk.service.coupon;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.sk.domain.Order;
import com.sk.domain.Product;
import com.sk.domain.ProductWithQuantity;
import com.sk.domain.ShoppingCart;
import com.sk.util.builder.OrderBuilder;
import com.sk.util.builder.ProductBuilder;
import com.sk.util.builder.ProductWithQuantityBuilder;
import com.sk.util.builder.ShoppingCartBuilder;


public class ProductCouponCheckerTest {

	private ProductCouponChecker couponChecker;
	private Product product;
	
	@Before
	public void init(){
		product = new ProductBuilder().build();
		couponChecker = new ProductCouponChecker(product);
	}
	
	@Test
	public void shouldReturnTrueIfCouponOwnerIsProduct(){
		
		ProductWithQuantity productWithQuantity = new ProductWithQuantityBuilder().product(product).build();
		ShoppingCart shoppingCart = new ShoppingCartBuilder().items(productWithQuantity).build();
		Order order = new OrderBuilder().shoppingCart(shoppingCart).build();
		
		Boolean canUseCoupon = couponChecker.canUseCoupon(order);
		
		assertTrue(canUseCoupon);
	}
	
	@Test
	public void shouldReturnFalseIfCouponOwnerIsNotProduct(){
		
		Product differentProduct = new ProductBuilder().build();
		ProductWithQuantity productWithQuantity = new ProductWithQuantityBuilder().product(differentProduct).build();
		ShoppingCart shoppingCart = new ShoppingCartBuilder().items(productWithQuantity).build();
		Order order = new OrderBuilder().shoppingCart(shoppingCart).build();
		
		Boolean canUseCoupon = couponChecker.canUseCoupon(order);
		
		assertFalse(canUseCoupon);
	}
}

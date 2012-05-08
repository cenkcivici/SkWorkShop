package com.sk.service.coupon;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.sk.domain.Order;
import com.sk.domain.Shopper;
import com.sk.util.builder.OrderBuilder;
import com.sk.util.builder.ShopperBuilder;


public class ShopperCouponCheckerTest {

	private ShopperCouponChecker couponChecker;
	private Shopper shopper;
	
	@Before
	public void init(){
		shopper = new ShopperBuilder().build();
		couponChecker = new ShopperCouponChecker(shopper);
	}
	
	@Test
	public void shouldReturnTrueIfCouponOwnerIsShopper(){
		Order order = new OrderBuilder().shopper(shopper).build();
		
		Boolean canUseCoupon = couponChecker.canUseCoupon(order);
		assertTrue(canUseCoupon);
	}
	
	@Test
	public void shouldReturnFalseIfCouponOwnerIsNotShopper(){
		Order order = new OrderBuilder().build();
		
		Boolean canUseCoupon = couponChecker.canUseCoupon(order);
		assertFalse(canUseCoupon);
	}
}

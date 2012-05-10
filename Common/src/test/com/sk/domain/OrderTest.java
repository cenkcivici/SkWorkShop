package com.sk.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Before;
import org.junit.Test;

import com.sk.domain.coupon.ShopperCoupon;
import com.sk.util.builder.OrderBuilder;
import com.sk.util.builder.ProductBuilder;
import com.sk.util.builder.ProductWithQuantityBuilder;
import com.sk.util.builder.ShopperCouponBuilder;
import com.sk.util.builder.ShoppingCartBuilder;


public class OrderTest {

	private Order order;
	
	@Before
	public void init(){
		ProductWithQuantity productWithQuantity = new ProductWithQuantityBuilder().product(new ProductBuilder().price(20).build()).build();
		order = new OrderBuilder().shoppingCart(new ShoppingCartBuilder().items(productWithQuantity).build()).build();
	}
	
	@Test
	public void shouldReturnDiscountedTotalAmountIfHasCoupon(){
		ShopperCoupon coupon = new ShopperCouponBuilder().discount(5D).build();
		order.setCoupon(coupon);
		
		assertThat(order.getTotalAmount(), equalTo(15D));
	}
	
	@Test
	public void shouldReturnTotalAmountIfHasNoCoupon(){
		assertThat(order.getTotalAmount(), equalTo(20D));
	}
	
	@Test
	public void shouldReturnZeroIfCouponDiscountMaxThanTotalAmount(){
		ShopperCoupon coupon = new ShopperCouponBuilder().discount(25D).build();
		order.setCoupon(coupon);
		
		assertThat(order.getTotalAmount(), equalTo(0D));
	}
}

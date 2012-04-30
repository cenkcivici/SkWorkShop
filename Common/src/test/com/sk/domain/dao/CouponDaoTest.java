package com.sk.domain.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sk.domain.Shopper;
import com.sk.domain.coupon.Coupon;
import com.sk.util.BaseIntegration;
import com.sk.util.builder.ShopperBuilder;
import com.sk.util.builder.ShopperCouponBuilder;


public class CouponDaoTest extends BaseIntegration{

	@Autowired private CouponDao couponDao;
	
	@Test
	public void shouldFindCouponIfCouponStringExist(){
		Shopper shopper = new ShopperBuilder().persist(getSession());
		new ShopperCouponBuilder().couponString("ASDFASDFAS").shopper(shopper).persist(getSession());
		
		Coupon existedCoupon = couponDao.findByCouponString("ASDFASDFAS");
		assertThat(existedCoupon, notNullValue());
	}
}

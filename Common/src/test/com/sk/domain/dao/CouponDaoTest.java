package com.sk.domain.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sk.domain.Shopper;
import com.sk.domain.coupon.Coupon;
import com.sk.domain.coupon.ShopperCoupon;
import com.sk.util.BaseIntegration;
import com.sk.util.builder.ShopperBuilder;
import com.sk.util.builder.ShopperCouponBuilder;


public class CouponDaoTest extends BaseIntegration{

	@Autowired private CouponDao couponDao;
	
	@Test
	public void shouldFindCouponIfCouponStringExist(){
		Shopper shopper = new ShopperBuilder().persist(getSession());
		new ShopperCouponBuilder().couponString("ASDFASDFAS").shopper(shopper).persist(getSession());
		
		Coupon existedCoupon = couponDao.findByCouponString("ASDFASDFAS", ShopperCoupon.class);
		assertThat(existedCoupon, notNullValue());
	}
	
	@Test
	public void shouldReturnGetAllShopperCoupons(){
		Shopper shopper = new ShopperBuilder().persist(getSession());
		ShopperCoupon coupon1 = new ShopperCouponBuilder().couponString("ASDFASDFAS").shopper(shopper).persist(getSession());
		ShopperCoupon coupon2 = new ShopperCouponBuilder().couponString("ZXCVZXCVZX").shopper(shopper).persist(getSession());
		
		List<ShopperCoupon> allCoupons = couponDao.getAllCoupons(ShopperCoupon.class);
		assertThat(allCoupons, hasItem(coupon1));
		assertThat(allCoupons, hasItem(coupon2));
	}
}

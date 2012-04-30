package com.sk.util.builder;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

import com.sk.domain.BaseEntity;
import com.sk.domain.Shopper;
import com.sk.domain.coupon.ShopperCoupon;

public class ShopperCouponBuilder extends BaseBuilder<BaseEntity, ShopperCouponBuilder>{

	private String couponString = RandomStringUtils.randomAlphabetic(10);
	private Double discountAmount = RandomUtils.nextDouble();
	private Shopper shopper = new ShopperBuilder().build();
	
	public ShopperCouponBuilder couponString(String couponString){
		this.couponString = couponString;
		return this;
	}
	
	public ShopperCouponBuilder shopper(Shopper shopper){
		this.shopper = shopper;
		return this;
	}
	
	@Override
	protected BaseEntity doBuild() {
		ShopperCoupon coupon = new ShopperCoupon();
		coupon.setCouponString(couponString);
		coupon.setDiscount(discountAmount);
		coupon.setShopper(shopper);
		coupon.setUsed(Boolean.FALSE);
		return coupon;
	}

}

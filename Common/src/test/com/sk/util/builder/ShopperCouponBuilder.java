package com.sk.util.builder;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

import com.sk.domain.Shopper;
import com.sk.domain.coupon.ShopperCoupon;

public class ShopperCouponBuilder extends BaseBuilder<ShopperCoupon, ShopperCouponBuilder>{

	private String couponString = RandomStringUtils.randomAlphabetic(10);
	private Double discountAmount = RandomUtils.nextDouble();
	private Boolean used = false;
	private Shopper shopper = new ShopperBuilder().build();
	
	public ShopperCouponBuilder couponString(String couponString){
		this.couponString = couponString;
		return this;
	}
	
	public ShopperCouponBuilder used(Boolean used){
		this.used = used;
		return this;
	}
	
	public ShopperCouponBuilder shopper(Shopper shopper){
		this.shopper = shopper;
		return this;
	}
	
	@Override
	protected ShopperCoupon doBuild() {
		ShopperCoupon coupon = new ShopperCoupon();
		coupon.setCouponString(couponString);
		coupon.setDiscount(discountAmount);
		coupon.setCouponHolder(shopper);
		coupon.setUsed(used);
		return coupon;
	}

}

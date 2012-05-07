package com.sk.domain.coupon;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.sk.domain.Shopper;

@Entity
@DiscriminatorValue("ShopperCoupon")
public class ShopperCoupon extends Coupon {

	private static final long serialVersionUID = -8960776463556210413L;

	@ManyToOne
	private Shopper shopper;

	@Override
	public CouponHolder getCouponHolder() {
		return shopper;
	}
	
	@Override
	public void setCouponHolder(CouponHolder couponHolder) {
		shopper = (Shopper)couponHolder;
	}

}

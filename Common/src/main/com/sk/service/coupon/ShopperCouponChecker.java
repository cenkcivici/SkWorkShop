package com.sk.service.coupon;

import com.sk.domain.Order;
import com.sk.domain.Shopper;

public class ShopperCouponChecker implements CouponChecker {

	private Shopper shopper;

	public ShopperCouponChecker(Shopper shopper) {
		this.shopper = shopper;
	}

	@Override
	public Boolean canUseCoupon(Order order) {
		if (order.getShopper().equals(shopper)) {
			return true;
		}

		return false;
	}

}

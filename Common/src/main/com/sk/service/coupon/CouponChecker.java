package com.sk.service.coupon;

import com.sk.domain.Order;

public interface CouponChecker {

	Boolean canUseCoupon(Order order);
}

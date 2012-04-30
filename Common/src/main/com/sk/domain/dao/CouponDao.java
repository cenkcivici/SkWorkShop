package com.sk.domain.dao;

import org.springframework.stereotype.Repository;

import com.sk.domain.coupon.Coupon;

@Repository
public class CouponDao extends GenericDao<Coupon>{

	public CouponDao() {
		super(Coupon.class);
	}

	public Coupon findByCouponString(String couponString) {
		// TODO Auto-generated method stub
		return null;
	}

}

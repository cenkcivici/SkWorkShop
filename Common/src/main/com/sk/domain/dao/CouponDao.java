package com.sk.domain.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sk.domain.coupon.Coupon;

@Repository
public class CouponDao extends GenericDao<Coupon>{

	public CouponDao() {
		super(Coupon.class);
	}

	public Coupon findByCouponString(String couponString) {
		Criteria criteria = getSession().createCriteria(Coupon.class);
		return (Coupon)criteria.add(Restrictions.eq("couponString", couponString)).uniqueResult();
	}

}

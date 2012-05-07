package com.sk.domain.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sk.domain.coupon.Coupon;

@Repository
public class CouponDao extends GenericDao<Coupon>{

	public CouponDao() {
		super(Coupon.class);
	}

	@SuppressWarnings("unchecked")
	public <T extends Coupon> T findByCouponString(String couponString, Class<T> couponClass) {
		Criteria criteria = getSession().createCriteria(couponClass);
		return (T) criteria.add(Restrictions.eq("couponString", couponString)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public <T extends Coupon> List<T> getAllCoupons(Class<T> couponClass) {
		return getSession().createCriteria(couponClass).list();
	}

}

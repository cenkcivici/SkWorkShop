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
	public <T extends Coupon> T findByCouponString(String couponString) {
		Criteria criteria = getSession().createCriteria(Coupon.class);
		return (T) criteria.add(Restrictions.eq("couponString", couponString)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public <T extends Coupon> List<T> getAllCoupons(Class<T> couponClass) {
		return getSession().createCriteria(couponClass).list();
	}

	public Coupon findUnusedByCouponString(String couponString) {
		Coupon coupon = findByCouponString(couponString);
		if(coupon == null || coupon.isUsed())
			return null;
		else
			return coupon;
	}

}

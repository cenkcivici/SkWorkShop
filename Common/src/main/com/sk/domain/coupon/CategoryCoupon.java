package com.sk.domain.coupon;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.sk.domain.Category;

@Entity
@DiscriminatorValue("CategoryCoupon")
public class CategoryCoupon extends Coupon {

	private static final long serialVersionUID = -4580681695558831088L;

	@ManyToOne
	private Category category;

	@Override
	public CouponHolder getCouponHolder() {
		return category;
	}
	
	@Override
	public void setCouponHolder(CouponHolder couponHolder) {
		category = (Category)couponHolder;
	}
	
}

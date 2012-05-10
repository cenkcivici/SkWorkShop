package com.sk.domain.coupon;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.sk.domain.Order;
import com.sk.domain.Product;
import com.sk.service.coupon.ProductCouponChecker;

@Entity
@DiscriminatorValue("ProductCoupon")
public class ProductCoupon extends Coupon {

	private static final long serialVersionUID = -4742847698199616572L;

	@ManyToOne
	private Product product;
	
	@Override
	public void setCouponHolder(CouponHolder couponHolder) {
		product = (Product)couponHolder;
	}

	@Override
	public CouponHolder getCouponHolder() {
		return product;
	}

	@Override
	public Boolean canUseCoupon(Order order) {
		return new ProductCouponChecker(product).canUseCoupon(order);
	}

}

package com.sk.admin.web.view.coupons;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import com.sk.admin.web.view.BaseView;
import com.sk.domain.coupon.Coupon;
import com.sk.domain.coupon.CouponHolder;
import com.sk.service.CouponService;

public abstract class BaseCouponViewBean extends BaseView {

	private static final long serialVersionUID = 9115998140490109014L;
	
	public abstract List<? extends Coupon> getCoupons();
	public abstract void createCoupon();
	public abstract List<? extends CouponHolder> getCouponHolders();
	
	private Double discountAmount;
	private Integer numberOfCoupons;
	private List<? extends Coupon> coupons;
	
	@ManagedProperty("#{couponService}")
	private transient CouponService couponService;
	
	public void createCoupon(Class<? extends Coupon> couponClass, CouponHolder couponHolder){
		couponService.createCoupon(couponClass, couponHolder, discountAmount, numberOfCoupons);
		discountAmount = null;
		numberOfCoupons = null;
		coupons = null;
	}
	
	public void delete(Coupon coupon) {
		couponService.deleteCoupon(coupon);
		setCoupons(null);
	}
	
	public List<? extends Coupon> getCoupons(Class<? extends Coupon> couponClass) {
		if(coupons == null){
			coupons = couponService.getAllCouponsFor(couponClass);
		}
		return coupons;
	}

	public void setCoupons(List<? extends Coupon> coupons) {
		this.coupons = coupons;
	}
	
	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Integer getNumberOfCoupons() {
		return numberOfCoupons;
	}

	public void setNumberOfCoupons(Integer numberOfCoupons) {
		this.numberOfCoupons = numberOfCoupons;
	}
	
	public void setCouponService(CouponService couponService) {
		this.couponService = couponService;
	}
	
}

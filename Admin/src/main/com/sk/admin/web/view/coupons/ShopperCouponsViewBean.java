package com.sk.admin.web.view.coupons;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.sk.admin.web.view.BaseView;
import com.sk.domain.Shopper;
import com.sk.domain.coupon.ShopperCoupon;
import com.sk.service.CouponService;
import com.sk.service.ShopperService;

@ManagedBean
@ViewScoped
public class ShopperCouponsViewBean extends BaseView{

	private static final long serialVersionUID = -2218714912704661066L;

	@ManagedProperty("#{couponService}")
	private transient CouponService couponService;
	
	@ManagedProperty("#{shopperService}")
	private transient ShopperService shopperService;
	
	private List<ShopperCoupon> coupons;
	private List<Shopper> shoppers;
	
	private Shopper selectedShopper;
	private Double discountAmount;
	private Integer numberOfCoupons;

	public void createCoupon(){
		couponService.createCouponForShopper(selectedShopper, discountAmount, numberOfCoupons);
		selectedShopper = null;
		discountAmount = null;
		numberOfCoupons = null;
		coupons = null;
	}
	
	public void delete(ShopperCoupon coupon) {
		couponService.deleteCoupon(coupon);
		coupons = null;
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

	public List<ShopperCoupon> getCoupons() {
		if(coupons == null){
			coupons = couponService.getAllShopperCoupons();
		}
		return coupons;
	}

	public void setCoupons(List<ShopperCoupon> coupons) {
		this.coupons = coupons;
	}

	public List<Shopper> getShoppers() {
		if(shoppers == null){
			shoppers = shopperService.getAllShoppers();
		}
		return shoppers;
	}

	public void setShoppers(List<Shopper> shoppers) {
		this.shoppers = shoppers;
	}

	public Shopper getSelectedShopper() {
		return selectedShopper;
	}

	public void setSelectedShopper(Shopper selectedShopper) {
		this.selectedShopper = selectedShopper;
	}

	public void setCouponService(CouponService couponService) {
		this.couponService = couponService;
	}

	public void setShopperService(ShopperService shopperService) {
		this.shopperService = shopperService;
	}

}

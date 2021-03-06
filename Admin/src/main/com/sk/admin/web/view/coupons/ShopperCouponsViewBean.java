package com.sk.admin.web.view.coupons;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.sk.domain.Shopper;
import com.sk.domain.coupon.Coupon;
import com.sk.domain.coupon.CouponHolder;
import com.sk.domain.coupon.ShopperCoupon;
import com.sk.service.ShopperService;

@ManagedBean
@ViewScoped
public class ShopperCouponsViewBean extends BaseCouponViewBean{

	private static final long serialVersionUID = -2218714912704661066L;
	private static final Class<ShopperCoupon> COUPON_CLASS = ShopperCoupon.class;

	@ManagedProperty("#{shopperService}")
	private transient ShopperService shopperService;
	
	private List<Shopper> shoppers;
	private Shopper selectedShopper;

	@Override
	public void createCoupon() {
		createCoupon(COUPON_CLASS, selectedShopper);
		selectedShopper = null;
	}
	
	@Override
	public List<? extends Coupon> getCoupons() {
		return getCoupons(COUPON_CLASS);
	}

	@Override
	public List<? extends CouponHolder> getCouponHolders() {
		if(shoppers == null){
			shoppers = shopperService.getAllShoppers();
		}
		return shoppers;
	}
	
	public void setShopperService(ShopperService shopperService) {
		this.shopperService = shopperService;
	}
	
	public Shopper getSelectedShopper() {
		return selectedShopper;
	}

	public void setSelectedShopper(Shopper selectedShopper) {
		this.selectedShopper = selectedShopper;
	}

}

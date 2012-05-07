package com.sk.admin.web.view.coupons;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.sk.domain.Category;
import com.sk.domain.coupon.CategoryCoupon;
import com.sk.domain.coupon.Coupon;
import com.sk.domain.coupon.CouponHolder;
import com.sk.service.CategoryService;

@ManagedBean
@ViewScoped
public class CategoryCouponsViewBean extends BaseCouponViewBean{

	private static final long serialVersionUID = -1803243458890317349L;
	private static final Class<CategoryCoupon> COUPON_CLASS = CategoryCoupon.class;

	@ManagedProperty("#{categoryService}")
	private transient CategoryService categoryService;
	
	private List<Category> categories;
	private Category selectedCategory;
	
	@Override
	public List<? extends Coupon> getCoupons() {
		return getCoupons(COUPON_CLASS);
	}

	@Override
	public void createCoupon() {
		createCoupon(COUPON_CLASS, selectedCategory);
		selectedCategory = null;
	}
	
	@Override
	public List<? extends CouponHolder> getCouponHolders() {
		if(categories == null){
			categories = categoryService.getAll();
		}
		return categories;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	public Category getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(Category selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

}

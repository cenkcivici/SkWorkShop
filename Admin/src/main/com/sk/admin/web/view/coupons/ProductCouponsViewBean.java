package com.sk.admin.web.view.coupons;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.sk.domain.Product;
import com.sk.domain.coupon.Coupon;
import com.sk.domain.coupon.CouponHolder;
import com.sk.domain.coupon.ProductCoupon;
import com.sk.service.ProductService;

@ManagedBean
@ViewScoped
public class ProductCouponsViewBean extends BaseCouponViewBean {

	private static final long serialVersionUID = -407614641526914111L;
	private static final Class<ProductCoupon> COUPON_CLASS = ProductCoupon.class;

	private List<Product> products;
	private Product selectedProduct;
	
	@ManagedProperty("#{productService}")
	private transient ProductService productService;
	
	@Override
	public List<? extends Coupon> getCoupons() {
		return super.getCoupons(COUPON_CLASS);
	}

	@Override
	public void createCoupon() {
		super.createCoupon(COUPON_CLASS, getSelectedProduct());
		setSelectedProduct(null);
	}

	@Override
	public List<? extends CouponHolder> getCouponHolders() {
		if(products == null){
			products = productService.getAll();
		}
		return products;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public Product getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(Product selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

}

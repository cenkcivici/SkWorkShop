package com.sk.service.coupon;

import java.util.Set;

import com.sk.domain.Order;
import com.sk.domain.Product;
import com.sk.domain.ProductWithQuantity;

public class ProductCouponChecker implements CouponChecker {

	private Product product;

	public ProductCouponChecker(Product product) {
		this.product = product;
	}

	@Override
	public Boolean canUseCoupon(Order order) {
		
		Set<ProductWithQuantity> items = order.getShoppingCart().getItems();
		for (ProductWithQuantity productWithQuantity : items) {
			if(productWithQuantity.getProduct().equals(product))
				return true;
		}
		
		return false;
	}

}

package com.sk.service.coupon;

import java.util.Set;

import com.sk.domain.Category;
import com.sk.domain.Order;
import com.sk.domain.ProductWithQuantity;

public class CategoryCouponChecker implements CouponChecker {

	private Category category;

	public CategoryCouponChecker(Category category) {
		this.category = category;
	}

	@Override
	public Boolean canUseCoupon(Order order) {
		Set<ProductWithQuantity> items = order.getShoppingCart().getItems();
		for (ProductWithQuantity productWithQuantity : items) {
			if (productWithQuantity.getProduct().getCategory().equals(category)) {
				return true;
			}
		}
		return false;
	}

}

package com.sk.util.builder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.sk.domain.ProductWithQuantity;
import com.sk.domain.ShoppingCart;

public class ShoppingCartBuilder {

	private Set<ProductWithQuantity> items = new HashSet<ProductWithQuantity>();

	public ShoppingCart build() {
		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setItems(items);
		return shoppingCart;
	}

	public ShoppingCartBuilder items(ProductWithQuantity... items) {
		this.items.addAll(Arrays.asList(items));
		return this;
	}

}

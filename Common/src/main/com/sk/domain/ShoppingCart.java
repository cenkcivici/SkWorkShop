package com.sk.domain;

import java.util.HashSet;
import java.util.Set;

public class ShoppingCart {

	private Set<Product> products = new HashSet<Product>();

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

}

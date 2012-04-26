package com.sk.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "cart_items")
	private Set<ProductWithQuantity> items = new HashSet<ProductWithQuantity>();

	public Set<ProductWithQuantity> getItems() {
		return items;
	}

	public void setItems(Set<ProductWithQuantity> items) {
		this.items = items;
	}

	public Double getTotalCost() {
		double totalCost = 0.0;
		for (ProductWithQuantity eachProductWithQuantity : items) {
			totalCost += eachProductWithQuantity.getCost();
		}

		return totalCost;
	}

	public int getCount() {
		return items.size();
	}
	
	private ProductWithQuantity getItemForProduct(Product product) {
		for (ProductWithQuantity eachItem : items) {
			if (eachItem.getProduct().equals(product)) {
				return eachItem;
			}
		}
		
		return null;
		
	}

	public void addProduct(Product product) {
		ProductWithQuantity itemForProduct = getItemForProduct(product);
		if (itemForProduct != null) {
			itemForProduct.incrementQuantity();
		} else {
			ProductWithQuantity newItem = new ProductWithQuantity(product, 1);
			items.add(newItem);
		}
	}

	public void removeProduct(Product product) {
		ProductWithQuantity itemForProduct = getItemForProduct(product);
		
		if (itemForProduct != null) {
			items.remove(itemForProduct);
		}
	}

}

package com.sk.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "shoppingCart")
public class ShoppingCart extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Set<ProductWithQuantity> items = new HashSet<ProductWithQuantity>();

	public Set<ProductWithQuantity> getItems() {
		return items;
	}

	public void setItems(Set<ProductWithQuantity> items) {
		this.items = items;
	}

	

}

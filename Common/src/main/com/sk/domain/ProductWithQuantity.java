package com.sk.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ProductWithQuantity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Product product;

	@Basic
	private int quantity;

	public ProductWithQuantity(){}
	
	public ProductWithQuantity(Product product, int quantity) {
		super();
		this.product = product;
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + quantity;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (!super.equals(obj)){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		
		ProductWithQuantity other = (ProductWithQuantity) obj;
		if (product == null) {
			if (other.product != null){
				return false;
			}
		} else if (!product.equals(other.product)){
			return false;
		}
		if (quantity != other.quantity){
			return false;
		}
		return true;
	}

	public double getCost() {
		return quantity * product.getPrice();
	}
	
	

}

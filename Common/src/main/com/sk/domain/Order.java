package com.sk.domain;import java.util.Date;import javax.persistence.CascadeType;import javax.persistence.Entity;import javax.persistence.JoinColumn;import javax.persistence.OneToOne;import javax.persistence.Table;import javax.persistence.Temporal;import javax.persistence.TemporalType;@Entity@Table(name = "user_order")public class Order extends BaseEntity {	private static final long serialVersionUID = -1070749922133859098L;	@OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)	@JoinColumn(name = "shopping_cart_id")	private ShoppingCart shoppingCart;	@Temporal(TemporalType.TIMESTAMP)	private Date orderDate = new Date();	public ShoppingCart getShoppingCart() {		return shoppingCart;	}	public void setShoppingCart(ShoppingCart shoppingCart) {		this.shoppingCart = shoppingCart;	}	public Date getOrderDate() {		return orderDate;	}	public void setOrderDate(Date orderDate) {		this.orderDate = orderDate;	}}
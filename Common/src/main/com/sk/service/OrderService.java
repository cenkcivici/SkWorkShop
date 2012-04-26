package com.sk.service;import java.util.List;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import com.sk.domain.Order;import com.sk.domain.OrderStatus;import com.sk.domain.PaymentMethod;import com.sk.domain.ShoppingCart;import com.sk.domain.dao.OrderDao;@Servicepublic class OrderService {	@Autowired	private OrderDao orderDao;	@Transactional	public Order createOrder(ShoppingCart shoppingCart, PaymentMethod paymentMethod) {		Order order = new Order();		order.setPaymentMethod(paymentMethod);		order.setShoppingCart(shoppingCart);		order.setOrderStatus(OrderStatus.UNDERREVIEW);		return orderDao.persist(order);	}		public Order save(Order order){		return orderDao.persist(order);	}	public List<Order> getAll() {		return orderDao.getAll();	}	public void setOrderDao(OrderDao orderDao) {		this.orderDao = orderDao;	}}
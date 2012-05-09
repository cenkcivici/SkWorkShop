package com.sk.service;import java.util.List;import org.apache.commons.lang.RandomStringUtils;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import com.sk.domain.Order;import com.sk.domain.OrderStatus;import com.sk.domain.PaymentMethod;import com.sk.domain.Shopper;import com.sk.domain.ShoppingCart;import com.sk.domain.dao.OrderDao;import com.sk.service.exception.ServiceException;import com.sk.service.payment.VPOSResponse;import com.sk.service.payment.garanti.GarantiVPOSService;@Servicepublic class OrderService {	private OrderDao orderDao;	private ShopperService shopperService;	private GarantiVPOSService garantiVPOSService;	public OrderService() {	}	@Autowired	public OrderService(OrderDao orderDao, ShopperService shopperService, GarantiVPOSService garantiVPOSService) {		this.orderDao = orderDao;		this.shopperService = shopperService;		this.garantiVPOSService = garantiVPOSService;	}	@Transactional	public Order createOrder(ShoppingCart shoppingCart, PaymentMethod paymentMethod) {		Order order = new Order();		order.setPaymentMethod(paymentMethod);		order.setShoppingCart(shoppingCart);		order.setOrderStatus(OrderStatus.UNDERREVIEW);		order.setShopper(shopperService.getStubShopper());		order.setOrderID(RandomStringUtils.randomAlphanumeric(30));		return order;	}	public List<Order> getAll() {		return orderDao.getAll();	}	public List<Order> getAllByShopper(Shopper shopper) {		return orderDao.findByShopper(shopper);	}	public void setOrderDao(OrderDao orderDao) {		this.orderDao = orderDao;	}	public Order save(Order order) {		return orderDao.persist(order);	}	public Order findById(Long id) {		return orderDao.get(id);	}	@Transactional	public void approveRefundRequest(Order order) throws ServiceException {		rejectAndRefundOrder(order, OrderStatus.REFUND_ACCEPTED);	}	@Transactional	public void rejectOrder(Order order) throws ServiceException {		rejectAndRefundOrder(order, OrderStatus.REJECTED);	}	private void rejectAndRefundOrder(Order order, OrderStatus status) {		VPOSResponse response = garantiVPOSService.refundOrder(order);		if (response.isSuccessful()) {			order.setOrderStatus(status);			save(order);		} else {			throw new ServiceException(response.getInfoText());		}	}}
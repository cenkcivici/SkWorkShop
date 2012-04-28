package com.sk.frontend.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sk.domain.Order;
import com.sk.domain.OrderStatus;
import com.sk.service.OrderService;
import com.sk.service.ShopperService;

@Controller
@RequestMapping("/orders")
public class OrdersController {

	private OrderService orderService;

	private ShopperService shopperService;
	
	@Autowired
	public OrdersController(OrderService orderService, ShopperService shopperService) {
		this.orderService = orderService;
		this.shopperService = shopperService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView orders() {

		ModelAndView mav = new ModelAndView("orders");
		mav.addObject("orders", getOrdersByShopper());
		return mav;
	}

	private List<Order> getOrdersByShopper() {
		return orderService.getAllByShopper(shopperService.getStubShopper());
	}

	@RequestMapping(value = "/reject/{id}", method = RequestMethod.POST)
	public ModelAndView rejectOrder(@PathVariable Long id) {
		Order foundOrder = orderService.findById(id);

		foundOrder.setOrderStatus(OrderStatus.REJECT_REQUESTED);
		orderService.save(foundOrder);

		ModelAndView mav = new ModelAndView("ordersContent");
		mav.addObject("orders", getOrdersByShopper());
		mav.addObject("orderRejectionMessage", "Your order reject request is sent!");
		return mav;
	}

}

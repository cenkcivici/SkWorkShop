package com.sk.admin.web.view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sk.domain.Order;
import com.sk.domain.OrderStatus;
import com.sk.service.OrderService;
import com.sk.service.exception.ServiceException;
import com.sk.util.builder.OrderBuilder;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.Matchers.anyString;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(MockitoJUnitRunner.class)
public class OrdersViewTest {

	private OrdersView ordersView;
	@Mock private OrderService orderService;
	@Mock private FacesHelper facesHelper;
	
	@Before
	public void init(){
		ordersView = new OrdersView();
		ordersView.setOrderService(orderService);
		ordersView.setFacesHelper(facesHelper);
	}
	
	@Test
	public void shouldRejectOrder(){
		Order order = new OrderBuilder().orderStatus(OrderStatus.UNDERREVIEW).build();

		ordersView.reject(order);

		verify(orderService).rejectOrder(order);
		verify(facesHelper).addInfoMessage(anyString());
	}
	
	@Test
	public void shouldRejectOrderAddsErrorMessageIfThereIsException(){
		Order order = new OrderBuilder().orderStatus(OrderStatus.UNDERREVIEW).build();
		doThrow(ServiceException.class).when(orderService).rejectOrder(order);
		ordersView.reject(order);
		
		verify(orderService).rejectOrder(order);
		verify(facesHelper).addErrorMessage(anyString());
	}
	
	
	@Test
	public void shouldApproveRefundRequest(){
		Order order = new OrderBuilder().orderStatus(OrderStatus.UNDERREVIEW).build();
		
		ordersView.approveRefundRequest(order);
		
		verify(orderService).approveRefundRequest(order);
		verify(facesHelper).addInfoMessage(anyString());
	}
	

	@Test
	public void shouldApproveRefundOrderAddsErrorMessageIfThereIsException(){
		Order order = new OrderBuilder().orderStatus(OrderStatus.UNDERREVIEW).build();
		doThrow(ServiceException.class).when(orderService).approveRefundRequest(order);
		ordersView.approveRefundRequest(order);
		
		verify(orderService).approveRefundRequest(order);
		verify(facesHelper).addErrorMessage(anyString());
	}
	
	
	@Test
	public void shouldCompleteOrder(){
		Order order = new OrderBuilder().orderStatus(OrderStatus.UNDERREVIEW).build();
		
		ordersView.complete(order);

		ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
		verify(orderService).save(captor.capture());
		assertThat(captor.getValue().getOrderStatus(), equalTo(OrderStatus.COMPLETED));
		assertThat(order.getOrderStatus(), equalTo(OrderStatus.COMPLETED));
	}
	
	@Test
	public void shouldGetOrderStatusAsSelectItemList(){
		List<SelectItem> orderStatusList = new ArrayList<SelectItem>();
		orderStatusList.add(new SelectItem(OrderStatus.COMPLETED, OrderStatus.COMPLETED.name()));
		
		when(facesHelper.toSelectItemListFromEnum(OrderStatus.class)).thenReturn(orderStatusList);
		List<SelectItem> fetchedOrderStatusList = ordersView.getOrderStatus();
		
		assertThat(fetchedOrderStatusList.get(0).getLabel(), equalTo("Select"));
	}
	
}

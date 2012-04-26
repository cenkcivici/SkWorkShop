package com.sk.admin.web.view;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import javax.faces.model.SelectItem;

import org.junit.Before;
import org.junit.Test;

import com.sk.domain.OrderStatus;


public class FacesHelperTest {

	private FacesHelper facesHelper;
	
	@Before
	public void init(){
		facesHelper = new FacesHelper();
	}
	@Test
	public void shouldReturnOrderStatusAsSelectItem(){
		List<SelectItem> statusSelectItem = facesHelper.toSelectItemListFromEnum(OrderStatus.class);
		assertThat((OrderStatus)statusSelectItem.get(0).getValue(), equalTo(OrderStatus.values()[0]));
	}
}

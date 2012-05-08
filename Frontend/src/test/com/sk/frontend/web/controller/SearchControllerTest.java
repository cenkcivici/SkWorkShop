package com.sk.frontend.web.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import com.sk.domain.Product;
import com.sk.service.ProductService;

@RunWith(MockitoJUnitRunner.class)
public class SearchControllerTest {

	private SearchController controller;
	@Mock private ProductService productService;
	
	@Before
	public void init(){
		controller = new SearchController(productService);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldSearchProductsWithKeyword(){
		List<Product> products = new ArrayList<Product>();
		when(productService.searchProduct("dene")).thenReturn(products);
		
		ModelAndView mav = controller.searchProduct("dene");
		
		assertThat(mav.getViewName(), equalTo("productResults"));
		assertThat((List<Product>)mav.getModelMap().get("productResults"), equalTo(products));
	}
}

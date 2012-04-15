package com.sk.frontend.web.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import com.sk.domain.Product;
import com.sk.service.ProductService;
import com.sk.util.builder.ProductBuilder;


@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {
	
	@Mock
	private ProductService productService;
	
	private ProductController controller;
	
	@Before
	public void before() {
		controller = new ProductController(productService);
	}
	
	@Test
	public void shouldPopulateModelWithProductByUrl() {
		Product product = new ProductBuilder().build();
		
		when(productService.findByUrl("url")).thenReturn(product);
		
		ModelAndView mav = controller.productsDetail("url");
		
		assertThat(mav.getModelMap(), hasEntry("product", (Object)product));
		
	}
	
	

}

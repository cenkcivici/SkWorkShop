package com.sk.frontend.web.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import com.sk.domain.Category;
import com.sk.domain.Product;
import com.sk.service.CategoryService;
import com.sk.service.ProductService;
import com.sk.util.builder.CategoryBuilder;

@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTest {
	
	@Mock
	private CategoryService categoryService;
	
	@Mock
	private ProductService productService;
	
	private CategoryController controller;
	
	@Before
	public void before() {
		controller = new CategoryController(categoryService, productService);
	}
	
	@Test
	public void shouldPopulateModelWithCategoryInUrlAndProductsInThatCategory() {
		Category category = new CategoryBuilder().build();
		List<Product> products = new ArrayList<Product>();
		
		when(categoryService.findByUrl("url")).thenReturn(category);
		when(productService.findByCategory(category)).thenReturn(products);
		
		ModelAndView mav = controller.productsByCategory("url");
		
		assertThat(mav.getModelMap(),hasEntry("products",(Object) products));
		assertThat(mav.getModelMap(),hasEntry("category",(Object) category));
	}
	

}

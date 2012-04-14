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
import com.sk.service.CategoryService;


@RunWith(MockitoJUnitRunner.class)
public class IndexControllerTest {

	@Mock
	private CategoryService categoryService;
	
	private IndexController controller;
	
	@Before
	public void before() {
		controller = new IndexController(categoryService);
	}
	
	
	@Test
	public void shouldPopulateModelWithCategories() {
		List<Category> categories = new ArrayList<Category>();
		
		when(categoryService.getAll()).thenReturn(categories);
		
		ModelAndView mav = controller.index(null, null);
		
		assertThat(mav.getModel(),hasEntry("categories", (Object) categories));
	}

}

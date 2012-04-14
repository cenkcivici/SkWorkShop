package com.sk.admin.web.view;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sk.domain.Category;
import com.sk.service.CategoryService;
import com.sk.util.builder.CategoryBuilder;


@RunWith(MockitoJUnitRunner.class)
public class CategoriesViewTest {
	
	@Mock
	private CategoryService categoryService;
	
	@Mock
	private FacesHelper facesHelper;
	
	private CategoriesView view;
	
	
	@Before
	public void before() {
		view = new CategoriesView();
		view.setCategoryService(categoryService);
		view.setFacesHelper(facesHelper);
	}
	
	@Test
	public void shouldDeleteCategory() {
		Category category = new CategoryBuilder().name("electronics").build();
		
		view.deleteCategory(category);
		
		verify(categoryService).delete(category);
		verify(facesHelper).addInfoMessage("category electronics is deleted");
	}

}

package com.sk.admin.web.view;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sk.service.CategoryService;
import com.sk.util.builder.CategoryBuilder;


@RunWith(MockitoJUnitRunner.class)
public class CategoryFormViewTest {
	
	@Mock
	private FacesHelper facesHelper;
	
	@Mock
	private CategoryService categoryService;
	
	private CategoryFormView view;
	
	@Before
	public void before() {
		view = new CategoryFormView();
		view.setFacesHelper(facesHelper);
		view.setCategoryService(categoryService);
	}
	
	@Test
	public void shouldCreateANewCategoryWhenOpenedFromMenu() {
		
		when(facesHelper.flashGet("category")).thenReturn(null);
		
		view.init();
		
		assertThat(view.getCategory().getId(),nullValue());
	}
	
	@Test
	public void shouldUseCategoryPassedInIfExists() {
		when(facesHelper.flashGet("category")).thenReturn(new CategoryBuilder().id(100L).build());
		
		view.init();

		assertThat(view.getCategory().getId(),equalTo(100L));
		verify(facesHelper).flashClear("category");
	}
	
	@Test
	public void shouldSaveAndAddSuccessMessage() {
		view.init();
		when(categoryService.save(view.getCategory())).thenReturn(view.getCategory());
		view.save();
		
		verify(categoryService).save(view.getCategory());
		verify(facesHelper).addInfoMessage("category saved");
	}
	
	@Test
	public void shouldRedirectToCategories() {
		view.init();
		
		view.save();
		
		verify(facesHelper).redirectFaces("categories");
	}
	
	@Test
	public void shoukdStayOnTheSamePage() {
		view.init();
		
		when(categoryService.save(view.getCategory())).thenReturn(view.getCategory());
		
		view.apply();
		
		verify(categoryService).save(view.getCategory());
		verify(facesHelper,never()).redirectFaces("categories");
	}

}

package com.sk.service;

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

import com.sk.domain.Category;
import com.sk.domain.dao.CategoryDao;


@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {
	
	@Mock
	private CategoryDao categoryDao;
	
	private CategoryService service;
	
	@Before
	public void before() {
		service = new CategoryService(categoryDao);
	}
	
	
	@Test
	public void shouldRetrieveAllCategories() {
		List<Category> categories = new ArrayList<Category>();
		
		when(categoryDao.getAll()).thenReturn(categories);
		
		List<Category> fromService = service.getAll();
		
		assertThat(fromService, equalTo(categories));
	}

}

package com.sk.domain.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sk.domain.Category;
import com.sk.util.BaseIntegration;
import com.sk.util.builder.CategoryBuilder;


public class CategoryDaoTest extends BaseIntegration {
	
	@Autowired
	private CategoryDao categoryDao;
	
	
	@Test
	public void shouldPersistCategory() {
		Category toPersist = new CategoryBuilder().name("Electronics").url("url").description("desc").build();
		
		toPersist = categoryDao.persist(toPersist);
		
		flushAndClear();
		
		Category fromDb = reget(toPersist);
		
		assertThat(fromDb.getName(), equalTo("Electronics"));
		assertThat(fromDb.getUrl(),equalTo("url"));
		assertThat(fromDb.getDescription(), equalTo("desc"));
	}
	

}

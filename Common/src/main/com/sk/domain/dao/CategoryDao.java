package com.sk.domain.dao;

import org.springframework.stereotype.Repository;

import com.sk.domain.Category;

@Repository
public class CategoryDao extends GenericDao<Category> {

	public CategoryDao() {
		super(Category.class);
	}

}

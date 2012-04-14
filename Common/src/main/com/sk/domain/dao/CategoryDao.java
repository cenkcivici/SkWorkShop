package com.sk.domain.dao;

import org.springframework.stereotype.Repository;

import com.sk.domain.Category;

@Repository
public class CategoryDao extends GenericDao<Category> {

	public CategoryDao() {
		super(Category.class);
	}

	public Category findByUrl(String url) {
		return (Category) getSession()
						.createQuery("from Category where url=:url")
						.setParameter("url", url).uniqueResult();
	}

}

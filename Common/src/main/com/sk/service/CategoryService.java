package com.sk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sk.domain.Category;
import com.sk.domain.dao.CategoryDao;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryDao categoryDao;

	public CategoryService() {}

	public CategoryService(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	public List<Category> getAll() {
		return categoryDao.getAll();
	}

}

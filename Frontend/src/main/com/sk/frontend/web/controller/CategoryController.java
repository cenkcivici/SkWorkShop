package com.sk.frontend.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sk.domain.Category;
import com.sk.domain.Product;
import com.sk.service.CategoryService;
import com.sk.service.ProductService;


@Controller
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	public CategoryController() {}
	
	public CategoryController(CategoryService categoryService, ProductService productService) {
		super();
		this.categoryService = categoryService;
		this.productService = productService;
	}

	@RequestMapping(value = "/{categoryUrl}", method = RequestMethod.GET)
	public ModelAndView productsByCategory(@PathVariable String categoryUrl) {
		ModelAndView mav = new ModelAndView("categoryFront");
		
		Category category = categoryService.findByUrl(categoryUrl);
		List<Product> products = productService.findByCategory(category);
		
		mav.addObject("category",category);
		mav.addObject("products",products);
		
		return mav;

	}
	
	

}

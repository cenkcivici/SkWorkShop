package com.sk.frontend.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sk.service.CategoryService;
import com.sk.service.ProductService;

@Controller
@RequestMapping("/index")
public class IndexController {
	
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private  ProductService productService;
	
	public IndexController() {}
	
	public IndexController(CategoryService categoryService,ProductService productService) {
		this.categoryService = categoryService;
		this.productService = productService;
	}



	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response)  {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("categories", categoryService.getAll());
		mav.addObject("featuredProducts",productService.getFeaturedProducts());
		return mav;
	}

}

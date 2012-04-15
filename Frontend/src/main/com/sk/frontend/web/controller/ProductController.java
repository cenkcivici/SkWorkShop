package com.sk.frontend.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sk.domain.Product;
import com.sk.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	public ProductController() {}
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@RequestMapping(value = "/{productUrl}", method = RequestMethod.GET)
	public ModelAndView productsDetail(@PathVariable String productUrl) {
		ModelAndView mav = new ModelAndView("productDetail");
		
		Product product = productService.findByUrl(productUrl);
		
		mav.addObject("product", product);
		
		return mav;
		
	}


	

}

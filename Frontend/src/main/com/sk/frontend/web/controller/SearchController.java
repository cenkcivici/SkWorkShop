package com.sk.frontend.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sk.domain.Product;
import com.sk.service.ProductService;

@Controller
@RequestMapping("/search")
public class SearchController {

	private final ProductService productService;

	@Autowired
	public SearchController(ProductService productService) {
		this.productService = productService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView searchProduct(@RequestParam String keyword) {
		
		List<Product> productResults = productService.searchProduct(keyword);
		ModelAndView mav = new ModelAndView("productResults");
		mav.addObject("productResults", productResults);
		return mav;
	}

}

package com.sk.service;import java.util.List;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import com.sk.domain.Product;import com.sk.domain.dao.ProductDao;@Servicepublic class ProductService {	@Autowired	private ProductDao productDao;		@Autowired	private UrlGeneratorService urlGeneratorService;		public ProductService() {}	public ProductService(ProductDao productDao, UrlGeneratorService urlGeneratorService) {		super();		this.productDao = productDao;		this.urlGeneratorService = urlGeneratorService;	}		@Transactional	public Product save(Product product) {		return productDao.persist(product);	}	@Transactional	public void delete(Product product) {		productDao.delete(product);	}	public List<Product> getAll() {		return productDao.getAll();	}	public void createURLFromTitle(Product product)  {		String url = urlGeneratorService.generateUrlFrom(product.getTitle());		product.setUrl(url);	}		public List<Product> getFeaturedProducts() {		return productDao.findByFeatured(true);	}}
package com.sk.service;import java.util.List;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import com.sk.domain.Product;import com.sk.domain.dao.ProductDao;@Servicepublic class ProductService {	@Autowired	private ProductDao productDao;	public void setProductDao(ProductDao productDao) {		this.productDao = productDao;	}	@Transactional	public Product save(Product product) {		return productDao.persist(product);	}		@Transactional	public void delete(Product product) {		productDao.delete(product);	}	public List<Product> getAll() {		return productDao.getAll();	}}
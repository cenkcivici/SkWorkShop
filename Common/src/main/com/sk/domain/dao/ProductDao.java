package com.sk.domain.dao;import java.util.List;import org.springframework.stereotype.Repository;import com.sk.domain.Product;@Repositorypublic class ProductDao extends GenericDao<Product> {	public ProductDao() {		super(Product.class);	}	@SuppressWarnings("unchecked")	public List<Product> findByFeatured(boolean featured) {		return getSession()				.createQuery("from Product where featured =:featured")				.setParameter("featured", featured)				.list();	}}
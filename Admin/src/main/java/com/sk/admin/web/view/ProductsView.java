package com.sk.admin.web.view;import java.util.List;import javax.faces.bean.ManagedBean;import javax.faces.bean.ManagedProperty;import javax.faces.bean.ViewScoped;import com.sk.domain.Product;import com.sk.service.ProductService;@ViewScoped@ManagedBeanpublic class ProductsView extends BaseView {	private static final long serialVersionUID = -7590244051767457979L;	@ManagedProperty("#{productService}")	private ProductService productService;	public void setProductService(ProductService productService) {		this.productService = productService;	}	public List<Product> getAll() {		return productService.getAll();	}}
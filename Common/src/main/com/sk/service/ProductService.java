package com.sk.service;import java.util.List;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import com.sk.domain.Category;import com.sk.domain.Photo;import com.sk.domain.Product;import com.sk.domain.dao.ProductDao;@Servicepublic class ProductService {	@Autowired	private ProductDao productDao;		@Autowired	private UrlGeneratorService urlGeneratorService;		public ProductService() {}	public ProductService(ProductDao productDao, UrlGeneratorService urlGeneratorService) {		super();		this.productDao = productDao;		this.urlGeneratorService = urlGeneratorService;	}		@Transactional	public Product save(Product product) {		if (product.getPhotos().isEmpty()) {			Photo photo = new Photo();			photo.setFileName("http://www.megawatthydro.com/ecom_theme/img/no_picture/product_default.gif");			product.getPhotos().add(photo);		}				return productDao.persist(product);	}	@Transactional	public void delete(Product product) {		productDao.delete(product);	}	public List<Product> getAll() {		return productDao.getAll();	}	public void createURLFromTitle(Product product)  {		String url = urlGeneratorService.generateUrlFrom(product.getTitle());		product.setUrl(url);	}		public List<Product> getFeaturedProducts() {		return productDao.findByFeatured(true);	}		public List<Product> findByCategory(Category category) {		return productDao.findByCategory(category);	}		public Product findByUrl(String productUrl) {		return productDao.findByUrl(productUrl);	}}
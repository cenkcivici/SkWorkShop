package com.sk.admin.web.view;import java.io.UnsupportedEncodingException;import java.util.List;import javax.annotation.PostConstruct;import javax.faces.bean.ManagedBean;import javax.faces.bean.ManagedProperty;import javax.faces.bean.ViewScoped;import com.sk.domain.Category;import com.sk.domain.Photo;import com.sk.domain.Product;import com.sk.service.CategoryService;import com.sk.service.ProductService;@ViewScoped@ManagedBeanpublic class ProductFormView extends BaseView {	private static final long serialVersionUID = -2165907826552573988L;	private Product product;		private Photo photo = new Photo();	@ManagedProperty("#{productService}")	private transient ProductService productService;		@ManagedProperty("#{categoryService}")	private transient CategoryService categoryService;	@PostConstruct	public void init() {		product = (Product) facesHelper.flashGet("product");		if (product == null) {			product = new Product();		} else {			facesHelper.flashClear("product");		}	}	public void createURIFromTitle() throws UnsupportedEncodingException {		productService.createURLFromTitle(getProduct());	}	public void setProductService(ProductService productService) {		this.productService = productService;	}	public Product getProduct() {		return product;	}	public void setProduct(Product product) {		this.product = product;	}	public Photo getPhoto() {		return photo;	}	public void setPhoto(Photo photo) {		this.photo = photo;	}	public String addPhoto() {		product.addPhoto(photo);		photo = new Photo();		return null;	}	public String deletePhoto(Photo photoToDelete) {		if (photoToDelete != null) {			product.deletePhoto(photoToDelete);		}		return null;	}	private void saveProduct() {		product = productService.save(product);		facesHelper.addInfoMessage("product saved");	}	public String save() {		saveProduct();		return facesHelper.redirectFaces("products");	}	public String apply() {		saveProduct();		return null;	}		public List<Category> getAllCategories() {		return categoryService.getAll();	}		public void setCategoryService(CategoryService categoryService) {		this.categoryService = categoryService;	}}
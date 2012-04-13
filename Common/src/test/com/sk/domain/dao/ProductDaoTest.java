package com.sk.domain.dao;import org.junit.Test;import org.springframework.beans.factory.annotation.Autowired;import com.sk.domain.Category;import com.sk.domain.Photo;import com.sk.domain.Product;import com.sk.util.BaseIntegration;import com.sk.util.builder.CategoryBuilder;import com.sk.util.builder.PhotoBuilder;import com.sk.util.builder.ProductBuilder;import static org.hamcrest.Matchers.equalTo;import static org.hamcrest.MatcherAssert.assertThat;public class ProductDaoTest extends BaseIntegration{	@Autowired	private ProductDao productDao;		@Test	public void shouldPersistProduct() {		Category category = new CategoryBuilder().persist(getSession());		Photo photo = new PhotoBuilder().fileName("photo1.png").build();				Product product = new ProductBuilder().title("ps3").url("url").description("PlayStation 3")					.price(300d).category(category).photos(photo).build();				Product persisted = productDao.persist(product);		flushAndClear();				Product fromDb = (Product) getSession().get(Product.class, persisted.getId());				assertThat(fromDb.getCategory(), equalTo(category));		assertThat(fromDb.getTitle(),equalTo("ps3"));		assertThat(fromDb.getUrl(),equalTo("url"));		assertThat(fromDb.getDescription(),equalTo("PlayStation 3"));		assertThat(fromDb.getPrice(),equalTo(300d));		assertThat(fromDb.getPhotos().size(),equalTo(1));		assertThat(fromDb.getPhotos().get(0).getFileName(),equalTo("photo1.png"));			}}
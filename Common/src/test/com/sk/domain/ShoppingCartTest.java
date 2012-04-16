package com.sk.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;

import org.junit.Test;

import com.sk.util.builder.ProductBuilder;
import com.sk.util.builder.ProductWithQuantityBuilder;
import com.sk.util.builder.ShoppingCartBuilder;


public class ShoppingCartTest {
	
	@Test
	public void shouldCalculateTotalCost() {
		Product productA = new ProductBuilder().price(100d).build();
		Product productB = new ProductBuilder().price(200d).build();
		
		ProductWithQuantity productWithQuantityA = new ProductWithQuantityBuilder().product(productA).quantity(2).build();
		ProductWithQuantity productWithQuantityB = new ProductWithQuantityBuilder().product(productB).quantity(3).build();
		
		ShoppingCart cart = new ShoppingCartBuilder().items(productWithQuantityA,productWithQuantityB).build();
		
		assertThat(cart.getTotalCost(), equalTo(800d));
	}
	
	@Test
	public void shouldGetNumberOfItems() {
		Product productA = new ProductBuilder().price(100d).build();
		Product productB = new ProductBuilder().price(200d).build();
		
		ProductWithQuantity productWithQuantityA = new ProductWithQuantityBuilder().product(productA).quantity(2).build();
		ProductWithQuantity productWithQuantityB = new ProductWithQuantityBuilder().product(productB).quantity(3).build();
		
		ShoppingCart cart = new ShoppingCartBuilder().items(productWithQuantityA,productWithQuantityB).build();
	
		assertThat(cart.getCount(),equalTo(2));
	}
	
	@Test
	public void shouldAddProductWithQuantityOneIfNotExistsBefore() {
		ShoppingCart cart = new ShoppingCartBuilder().build();
		Product product = new ProductBuilder().price(100d).build();
		
		cart.addProduct(product);

		
		ProductWithQuantity item = cart.getItems().iterator().next();
		assertThat(item.getProduct(), sameInstance(product));
		assertThat(item.getQuantity(), equalTo(1));
	}
	
	@Test
	public void shouldIncrementQuantityIfExistsBefore() {
		ShoppingCart cart = new ShoppingCartBuilder().build();
		Product product = new ProductBuilder().price(100d).build();
		
		cart.addProduct(product);
		cart.addProduct(product);

		ProductWithQuantity item = cart.getItems().iterator().next();
		
		assertThat(cart.getItems().size(), equalTo(1));
		assertThat(item.getProduct(), sameInstance(product));
		assertThat(item.getQuantity(), equalTo(2));
	}
	

}

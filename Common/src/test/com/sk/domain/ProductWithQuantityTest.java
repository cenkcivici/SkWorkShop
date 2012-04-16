package com.sk.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import com.sk.util.builder.ProductBuilder;
import com.sk.util.builder.ProductWithQuantityBuilder;


public class ProductWithQuantityTest {
	
	@Test
	public void shouldCalculateCost() {
		Product productA = new ProductBuilder().price(100d).build();
		
		ProductWithQuantity productWithQuantityA = new ProductWithQuantityBuilder().product(productA).quantity(2).build();
		
		assertThat(productWithQuantityA.getCost(),equalTo(200d));
	}

}

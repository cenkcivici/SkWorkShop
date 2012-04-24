package com.sk.domain;

import static junit.framework.Assert.assertTrue;

import org.junit.Test;

import com.sk.util.builder.CreditCardBuilder;
import com.sk.util.builder.ShopperBuilder;


public class ShopperTest {

	@Test
	public void shouldCheckIfShopperHasCreditCardInfo(){
		Shopper shopper = new ShopperBuilder().creditCard(new CreditCardBuilder().build()).build();
		assertTrue(shopper.hasAnyCreditCardInfo());
	}
}

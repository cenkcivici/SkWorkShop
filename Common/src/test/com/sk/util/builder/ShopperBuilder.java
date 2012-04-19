package com.sk.util.builder;

import org.apache.commons.lang.RandomStringUtils;

import com.sk.domain.Shopper;

public class ShopperBuilder extends BaseBuilder<Shopper, ShopperBuilder>{

	private String email = RandomStringUtils.random(10);
	
	public ShopperBuilder email(String email){
		this.email = email;
		return this;
	}
	
	@Override
	public Shopper doBuild(){
		Shopper shopper = new Shopper();
		shopper.setEmail(email);
		
		return shopper;
	}
}

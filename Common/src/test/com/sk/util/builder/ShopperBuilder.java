package com.sk.util.builder;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;

import com.sk.domain.CreditCard;
import com.sk.domain.Shopper;

public class ShopperBuilder extends BaseBuilder<Shopper, ShopperBuilder>{

	private String email = RandomStringUtils.randomAlphabetic(10);
	private String name = "default";
	private Set<CreditCard> creditCardList = new HashSet<CreditCard>();
	
	public ShopperBuilder email(String email){
		this.email = email;
		return this;
	}
	
	public ShopperBuilder name(String name){
		this.name = name;
		return this;
	}
	
	public ShopperBuilder creditCard(CreditCard card){
		this.creditCardList.add(card);
		return this;
	}
	
	@Override
	public Shopper doBuild(){
		Shopper shopper = new Shopper();
		shopper.setEmail(email);
		shopper.setName(name);
		shopper.setCreditCardList(creditCardList);
		
		return shopper;
	}
}

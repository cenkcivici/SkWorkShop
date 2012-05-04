package com.sk.util.builder;

import org.apache.commons.lang.RandomStringUtils;

import com.sk.domain.CreditCard;
import com.sk.domain.CreditCardType;

public class CreditCardBuilder extends BaseBuilder<CreditCard, CreditCardBuilder>{

	private String owner = RandomStringUtils.randomAlphabetic(10);
	private String cardNumber = RandomStringUtils.randomNumeric(16);
	private String cvc = RandomStringUtils.randomNumeric(3);
	private String month = RandomStringUtils.randomNumeric(2);
	private String year = RandomStringUtils.randomNumeric(2);
	private CreditCardType creditCardType = RandomEnumBuilder.randomEnum(CreditCardType.class);
	
	public CreditCardBuilder owner(String owner){
		this.owner = owner;
		return this;
	}
	
	public CreditCardBuilder cardNumber(String cardNumber){
		this.cardNumber = cardNumber;
		return this;
	}
	
	public CreditCardBuilder cvc(String cvc){
		this.cvc = cvc;
		return this;
	}
	
	public CreditCardBuilder month(String month){
		this.month = month;
		return this;
	}
	
	public CreditCardBuilder year(String year){
		this.year = year;
		return this;
	}
	
	public CreditCardBuilder cardType(CreditCardType cardType){
		this.creditCardType = cardType;
		return this;
	}
	
	@Override
	protected CreditCard doBuild() {
		CreditCard creditCard = new CreditCard();
		creditCard.setOwner(owner);
		creditCard.setCardNumber(cardNumber);
		creditCard.setCvc(cvc);
		creditCard.setMonth(month);
		creditCard.setYear(year);
		creditCard.setCreditCardType(creditCardType);
		return creditCard;
	}
	
}

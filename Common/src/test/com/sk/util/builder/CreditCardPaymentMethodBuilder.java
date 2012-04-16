package com.sk.util.builder;import org.apache.commons.lang.RandomStringUtils;import com.sk.domain.CreditCardPaymentMethod;import com.sk.domain.CreditCardType;public class CreditCardPaymentMethodBuilder extends BaseBuilder<CreditCardPaymentMethod, CreditCardPaymentMethodBuilder> {	private String creditCardNumber = RandomStringUtils.randomNumeric(16);	private String owner = RandomStringUtils.randomAlphabetic(6) + " " + RandomStringUtils.randomAlphabetic(6);	private String cvc = RandomStringUtils.randomNumeric(3);	private String month = "04";	private String year = "2015";	private CreditCardType creditCardType = CreditCardType.VISA;	@Override	protected CreditCardPaymentMethod doBuild() {		CreditCardPaymentMethod cardPaymentMethod = new CreditCardPaymentMethod();		cardPaymentMethod.setCardNumber(creditCardNumber);		cardPaymentMethod.setOwner(owner);		cardPaymentMethod.setCvc(cvc);		cardPaymentMethod.setMonth(month);		cardPaymentMethod.setYear(year);		cardPaymentMethod.setCreditCardType(creditCardType);		return cardPaymentMethod;	}	public CreditCardPaymentMethodBuilder creditCardNumber(String creditCardNumber) {		this.creditCardNumber = creditCardNumber;		return this;	}	public CreditCardPaymentMethodBuilder owner(String owner) {		this.owner = owner;		return this;	}	public CreditCardPaymentMethodBuilder cvc(String cvc) {		this.cvc = cvc;		return this;	}	public CreditCardPaymentMethodBuilder month(String month) {		this.month = month;		return this;	}	public CreditCardPaymentMethodBuilder year(String year) {		this.year = year;		return this;	}	public CreditCardPaymentMethodBuilder creditCardType(CreditCardType creditCardType) {		this.creditCardType = creditCardType;		return this;	}}
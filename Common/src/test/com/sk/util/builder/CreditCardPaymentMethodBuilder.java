package com.sk.util.builder;import com.sk.domain.CreditCard;import com.sk.domain.CreditCardPaymentMethod;import com.sk.domain.InstallmentPlan;public class CreditCardPaymentMethodBuilder extends BaseBuilder<CreditCardPaymentMethod, CreditCardPaymentMethodBuilder> {	private CreditCard creditCard = new CreditCardBuilder().build();	private InstallmentPlan installmentPlan;	private Double bonusUsed = 0d;	@Override	protected CreditCardPaymentMethod doBuild() {		CreditCardPaymentMethod cardPaymentMethod = new CreditCardPaymentMethod();		cardPaymentMethod.setCreditCard(creditCard);		cardPaymentMethod.setInstallmentPlan(installmentPlan);		cardPaymentMethod.setBonusUsed(bonusUsed);		return cardPaymentMethod;	}	public CreditCardPaymentMethodBuilder creditCard(CreditCard creditCard) {		this.creditCard = creditCard;		return this;	}	public CreditCardPaymentMethodBuilder installmentPlan(InstallmentPlan installmentPlan) {		this.installmentPlan = installmentPlan;		return this;	}		public CreditCardPaymentMethodBuilder bonusUsed(Double bonusUsed){		this.bonusUsed = bonusUsed;		return this;	}}
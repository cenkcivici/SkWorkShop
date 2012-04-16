package com.sk.frontend.web.validator;import org.apache.commons.lang.StringUtils;import org.springframework.validation.Errors;import org.springframework.validation.Validator;import com.sk.domain.CreditCardPaymentMethod;public class CreditCardValidator implements Validator {	@Override	public boolean supports(Class<?> clazz) {		return CreditCardPaymentMethod.class.equals(clazz);	}	@Override	public void validate(Object paymentMethod, Errors errors) {		CreditCardPaymentMethod creditCardPaymentMethod = (CreditCardPaymentMethod) paymentMethod;		if (StringUtils.isBlank(creditCardPaymentMethod.getCardNumber())) {			errors.rejectValue("cardNumber", "creditCard.empty");		} else if (!StringUtils.isNumeric(creditCardPaymentMethod.getCardNumber())) {			errors.rejectValue("cardNumber", "creditCard.numeric");		} else if (creditCardPaymentMethod.getCardNumber().length() != 16) {			errors.rejectValue("cardNumber", "creditCard.length");		}	}}
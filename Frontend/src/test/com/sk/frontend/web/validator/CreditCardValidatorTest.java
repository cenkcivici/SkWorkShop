package com.sk.frontend.web.validator;import org.junit.Before;import org.junit.Test;import org.junit.runner.RunWith;import org.mockito.Mock;import org.mockito.runners.MockitoJUnitRunner;import org.springframework.validation.Errors;import com.sk.domain.CreditCard;import com.sk.domain.CreditCardPaymentMethod;import com.sk.util.builder.CreditCardBuilder;import com.sk.util.builder.CreditCardPaymentMethodBuilder;import static org.mockito.Mockito.verify;import static org.mockito.Mockito.verifyNoMoreInteractions;import static org.hamcrest.MatcherAssert.assertThat;import static org.hamcrest.Matchers.equalTo;@RunWith(MockitoJUnitRunner.class)public class CreditCardValidatorTest {	private CreditCardValidator creditCardValidator;	@Mock	private Errors errors;	@Before	public void init() {		creditCardValidator = new CreditCardValidator();	}	@Test	public void shouldSupportCreditCardClass() {		assertThat(creditCardValidator.supports(CreditCardPaymentMethod.class), equalTo(true));	}	@Test	public void shouldAddErrorIfCreditCardNumberIsEmptyOrBlank() {		CreditCard creditCard = new CreditCardBuilder().cardNumber(null).build();		CreditCardPaymentMethod paymentMethod = new CreditCardPaymentMethodBuilder().creditCard(creditCard).build();		creditCardValidator.validate(paymentMethod, errors);		verify(errors).rejectValue("cardNumber", "creditCard.cardNumber.empty");	}	@Test	public void shouldAddErrorIfCreditCardNumberIsBlank() {		CreditCard creditCard = new CreditCardBuilder().cardNumber("           ").build();		CreditCardPaymentMethod paymentMethod = new CreditCardPaymentMethodBuilder().creditCard(creditCard).build();		creditCardValidator.validate(paymentMethod, errors);		verify(errors).rejectValue("cardNumber", "creditCard.cardNumber.empty");	}	@Test	public void shouldAddErrorIfCreditCardNumberIsNotNumeric() {		CreditCard creditCard = new CreditCardBuilder().cardNumber("1234567asd123456").build();		CreditCardPaymentMethod paymentMethod = new CreditCardPaymentMethodBuilder().creditCard(creditCard).build();		creditCardValidator.validate(paymentMethod, errors);		verify(errors).rejectValue("cardNumber", "creditCard.cardNumber.numeric");	}	@Test	public void shouldAddErrorIfCreditCardNumberIsNot16Characters() {		CreditCard creditCard = new CreditCardBuilder().cardNumber("123456789012345").build();		CreditCardPaymentMethod paymentMethod = new CreditCardPaymentMethodBuilder().creditCard(creditCard).build();		creditCardValidator.validate(paymentMethod, errors);		verify(errors).rejectValue("cardNumber", "creditCard.cardNumber.length");	}		@Test	public void shouldAddErrorIfCreditCardNumberIsNotValid() {		CreditCard creditCard = new CreditCardBuilder().cardNumber("1234567890123456").build();		CreditCardPaymentMethod paymentMethod = new CreditCardPaymentMethodBuilder().creditCard(creditCard).build();				creditCardValidator.validate(paymentMethod, errors);		verify(errors).rejectValue("cardNumber", "creditCard.cardNumber.not.valid");	}	@Test	public void shouldAddErrorIfOwnerIsEmpty() {		CreditCard creditCard = new CreditCardBuilder().owner(null).build();		CreditCardPaymentMethod paymentMethod = new CreditCardPaymentMethodBuilder().creditCard(creditCard).build();		creditCardValidator.validate(paymentMethod, errors);		verify(errors).rejectValue("owner", "creditCard.owner.empty");	}		@Test	public void shouldAddErrorIfOwnerIsNotAlphebatic() {		CreditCard creditCard = new CreditCardBuilder().owner("some name1").build();		CreditCardPaymentMethod paymentMethod = new CreditCardPaymentMethodBuilder().creditCard(creditCard).build();				creditCardValidator.validate(paymentMethod, errors);		verify(errors).rejectValue("owner", "creditCard.owner.alphabetic");	}	@Test	public void shouldAddErrorIfOwnerIsBlank() {		CreditCard creditCard = new CreditCardBuilder().owner("    ").build();		CreditCardPaymentMethod paymentMethod = new CreditCardPaymentMethodBuilder().creditCard(creditCard).build();		creditCardValidator.validate(paymentMethod, errors);		verify(errors).rejectValue("owner", "creditCard.owner.empty");	}		@Test	public void shouldAddErrorIfCVCIsEmpty() {		CreditCard creditCard = new CreditCardBuilder().cvc(null).build();		CreditCardPaymentMethod paymentMethod = new CreditCardPaymentMethodBuilder().creditCard(creditCard).build();		creditCardValidator.validate(paymentMethod, errors);		verify(errors).rejectValue("cvc", "creditCard.cvc.empty");	}	@Test	public void shouldAddErrorIfCVCIsBlank() {		CreditCard creditCard = new CreditCardBuilder().cvc("       ").build();		CreditCardPaymentMethod paymentMethod = new CreditCardPaymentMethodBuilder().creditCard(creditCard).build();		creditCardValidator.validate(paymentMethod, errors);		verify(errors).rejectValue("cvc", "creditCard.cvc.empty");	}		@Test	public void shouldAddErrorIfCVCIsNotNumeric() {		CreditCard creditCard = new CreditCardBuilder().cvc("1A3").build();		CreditCardPaymentMethod paymentMethod = new CreditCardPaymentMethodBuilder().creditCard(creditCard).build();				creditCardValidator.validate(paymentMethod, errors);		verify(errors).rejectValue("cvc", "creditCard.cvc.numeric");	}		@Test	public void shouldAddErrorIfCVCIsNot3Characters() {		CreditCard creditCard = new CreditCardBuilder().cvc("1234").build();		CreditCardPaymentMethod paymentMethod = new CreditCardPaymentMethodBuilder().creditCard(creditCard).build();				creditCardValidator.validate(paymentMethod, errors);		verify(errors).rejectValue("cvc", "creditCard.cvc.length");	}		@Test	public void shouldAddErrorIfCreditCardTypeIsNull() {		CreditCard creditCard = new CreditCardBuilder().cardType(null).build();		CreditCardPaymentMethod paymentMethod = new CreditCardPaymentMethodBuilder().creditCard(creditCard).build();				creditCardValidator.validate(paymentMethod, errors);		verify(errors).rejectValue("creditCardType", "creditCard.creditCardType.empty");	}		@Test	public void shouldAddErrorIfCardMonthIsNull() {		CreditCard creditCard = new CreditCardBuilder().month(null).build();		CreditCardPaymentMethod paymentMethod = new CreditCardPaymentMethodBuilder().creditCard(creditCard).build();				creditCardValidator.validate(paymentMethod, errors);		verify(errors).rejectValue("month", "creditCard.month.empty");	}	@Test	public void shouldAddErrorIfCardMonthIsNotNumeric() {		CreditCard creditCard = new CreditCardBuilder().month("a1").build();		CreditCardPaymentMethod paymentMethod = new CreditCardPaymentMethodBuilder().creditCard(creditCard).build();				creditCardValidator.validate(paymentMethod, errors);		verify(errors).rejectValue("month", "creditCard.month.numeric");	}		@Test	public void shouldAddErrorIfCardMonthIsNotLowerThan1() {		CreditCard creditCard = new CreditCardBuilder().month("00").build();		CreditCardPaymentMethod paymentMethod = new CreditCardPaymentMethodBuilder().creditCard(creditCard).build();				creditCardValidator.validate(paymentMethod, errors);		verify(errors).rejectValue("month", "creditCard.month.not.valid");			}		@Test	public void shouldAddErrorIfCardMonthIsHigherThan12() {		CreditCard creditCard = new CreditCardBuilder().month("13").build();		CreditCardPaymentMethod paymentMethod = new CreditCardPaymentMethodBuilder().creditCard(creditCard).build();						creditCardValidator.validate(paymentMethod, errors);		verify(errors).rejectValue("month", "creditCard.month.not.valid");			}		@Test	public void shouldAddErrorIfCardYearIsNull() {		CreditCard creditCard = new CreditCardBuilder().year(null).build();		CreditCardPaymentMethod paymentMethod = new CreditCardPaymentMethodBuilder().creditCard(creditCard).build();				creditCardValidator.validate(paymentMethod, errors);		verify(errors).rejectValue("year", "creditCard.year.empty");	}		@Test	public void shouldAddErrorIfCardYearIsNotNumeric() {		CreditCard creditCard = new CreditCardBuilder().year("2-12").build();		CreditCardPaymentMethod paymentMethod = new CreditCardPaymentMethodBuilder().creditCard(creditCard).build();				creditCardValidator.validate(paymentMethod, errors);		verify(errors).rejectValue("year", "creditCard.year.numeric");	}			@Test	public void shouldAddErrorIfCardMonthIsNotLowerThanThisYear() {		CreditCard creditCard = new CreditCardBuilder().year("2011").build();		CreditCardPaymentMethod paymentMethod = new CreditCardPaymentMethodBuilder().creditCard(creditCard).build();				creditCardValidator.validate(paymentMethod, errors);		verify(errors).rejectValue("year", "creditCard.year.not.valid");			}	@Test	public void shouldAddErrorIfCardMonthIsNotLowerMoreThis12YearsLater() {		CreditCard creditCard = new CreditCardBuilder().year("2025").build();		CreditCardPaymentMethod paymentMethod = new CreditCardPaymentMethodBuilder().creditCard(creditCard).build();				creditCardValidator.validate(paymentMethod, errors);		verify(errors).rejectValue("year", "creditCard.year.not.valid");	}		@Test	public void shouldValidateIfCreditCardIsValid() {		CreditCard creditCard = new CreditCardBuilder().cardNumber("4539992043491562").month("11").year("2015").build();		CreditCardPaymentMethod paymentMethod = new CreditCardPaymentMethodBuilder().creditCard(creditCard).build();		creditCardValidator.validate(paymentMethod, errors);		verifyNoMoreInteractions(errors);	}}
package com.sk.domain.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sk.domain.Category;
import com.sk.domain.CreditCard;
import com.sk.domain.CreditCardPaymentMethod;
import com.sk.domain.InstallmentPlan;
import com.sk.domain.Order;
import com.sk.domain.PaymentMethod;
import com.sk.domain.Product;
import com.sk.domain.ProductWithQuantity;
import com.sk.domain.ShoppingCart;
import com.sk.util.BaseIntegration;
import com.sk.util.builder.CategoryBuilder;
import com.sk.util.builder.CreditCardBuilder;
import com.sk.util.builder.CreditCardPaymentMethodBuilder;
import com.sk.util.builder.CreditCardProfileBuilder;
import com.sk.util.builder.InstallmentPlanBuilder;
import com.sk.util.builder.OrderBuilder;
import com.sk.util.builder.ProductBuilder;
import com.sk.util.builder.ProductWithQuantityBuilder;
import com.sk.util.builder.ShoppingCartBuilder;

public class OrderDaoTest extends BaseIntegration {

	@Autowired
	private OrderDao orderDao;

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm ss");

	@Test
	public void shouldPersistOrder() {
		Date now = new Date();

		Category category = new CategoryBuilder().persist(getSession());
		Product product1 = new ProductBuilder().category(category).persist(getSession());
		Product product2 = new ProductBuilder().category(category).persist(getSession());
		ProductWithQuantity productWithQuantity1 = new ProductWithQuantityBuilder().product(product1).quantity(1).build();
		ProductWithQuantity productWithQuantity2 = new ProductWithQuantityBuilder().product(product2).quantity(2).build();
		ShoppingCart shoppingCart = new ShoppingCartBuilder().items(productWithQuantity1, productWithQuantity2).build();
		CreditCard creditCard = new CreditCardBuilder().cardNumber("1234567890123456").build();
		
		InstallmentPlan installmentPlan = new InstallmentPlanBuilder().months(2).interestRate(5d).persist(getSession());
		new CreditCardProfileBuilder().vendor("Vendor").installmentPlans(installmentPlan).bin("121212").persist(getSession());
		
		
		PaymentMethod creditCardPaymentMethod = new CreditCardPaymentMethodBuilder().creditCard(creditCard).installmentPlan(installmentPlan).build(); 

		Order toPersist = new OrderBuilder().shoppingCart(shoppingCart).paymentMethod(creditCardPaymentMethod).orderDate(now).build();

		toPersist = orderDao.persist(toPersist);

		flushAndClear();

		Order fromDb = reget(toPersist);

		assertThat(dateFormat.format(fromDb.getOrderDate()), equalTo(dateFormat.format(now)));

		assertThat(fromDb.getShoppingCart().getItems().size(), equalTo(2));
		assertThat(fromDb.getShoppingCart().getItems(), equalTo(toPersist.getShoppingCart().getItems()));
		CreditCardPaymentMethod paymentMethod = (CreditCardPaymentMethod)fromDb.getPaymentMethod();
		assertThat(paymentMethod.getCreditCard().getCardNumber(), equalTo("1234567890123456"));
		assertThat(paymentMethod.getInstallmentPlan(), equalTo(installmentPlan));

	}

}

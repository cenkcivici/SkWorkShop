package com.sk.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sk.domain.CreditCardProfile;
import com.sk.domain.InstallmentPlan;
import com.sk.domain.Product;
import com.sk.domain.ProductWithQuantity;
import com.sk.domain.ShoppingCart;
import com.sk.domain.dao.CreditCardProfileDao;
import com.sk.domain.dao.InstallmentPlanDao;
import com.sk.util.builder.CreditCardProfileBuilder;
import com.sk.util.builder.InstallmentPlanBuilder;
import com.sk.util.builder.ProductBuilder;
import com.sk.util.builder.ProductWithQuantityBuilder;
import com.sk.util.builder.ShoppingCartBuilder;


@RunWith(MockitoJUnitRunner.class)
public class CreditCardProfileServiceTest {
	
	@Mock
	private CreditCardProfileDao creditCardProfileDao;
	
	@Mock
	private InstallmentPlanDao installmentPlanDao;

	private CreditCardProfileService service;
	
	@Before
	public void before() {
		service = new CreditCardProfileService(creditCardProfileDao, installmentPlanDao);
	}
	
	@Test
	public void shouldCalculatePaymentsForShoppingCart() {
		InstallmentPlan installmentPlan = new InstallmentPlanBuilder().build();
		CreditCardProfile cardProfile = new CreditCardProfileBuilder().installmentPlans(installmentPlan).build();
		
		List<CreditCardProfile> creditCardProfiles = new ArrayList<CreditCardProfile>();
		creditCardProfiles.add(cardProfile);
		
		when(creditCardProfileDao.getAll()).thenReturn(creditCardProfiles);
		Product productA = new ProductBuilder().price(100d).build();
		Product productB = new ProductBuilder().price(200d).build();
		
		ProductWithQuantity productWithQuantityA = new ProductWithQuantityBuilder().product(productA).quantity(2).build();
		ProductWithQuantity productWithQuantityB = new ProductWithQuantityBuilder().product(productB).quantity(3).build();
		
		ShoppingCart cart = new ShoppingCartBuilder().items(productWithQuantityA,productWithQuantityB).build();
		
		Map<CreditCardProfile, Map<InstallmentPlan, Double>> paymentsFor = service.paymentsFor(cart);
		
		assertThat(paymentsFor.keySet(),hasItem(cardProfile));
		Map<InstallmentPlan, Double> map = paymentsFor.get(cardProfile);
		
		assertThat(map,hasEntry(installmentPlan, 808d));
	}
	
	@Test
	public void shouldGetAvailablePlansForCreditCardNo() {
		InstallmentPlan installmentPlan = new InstallmentPlanBuilder().build();
		CreditCardProfile cardProfile = new CreditCardProfileBuilder().installmentPlans(installmentPlan).bin("11111").build();
		
		List<CreditCardProfile> creditCardProfiles = new ArrayList<CreditCardProfile>();
		creditCardProfiles.add(cardProfile);
		
		when(creditCardProfileDao.getAll()).thenReturn(creditCardProfiles);
		
		Product productA = new ProductBuilder().price(100d).build();
		Product productB = new ProductBuilder().price(200d).build();
		
		ProductWithQuantity productWithQuantityA = new ProductWithQuantityBuilder().product(productA).quantity(2).build();
		ProductWithQuantity productWithQuantityB = new ProductWithQuantityBuilder().product(productB).quantity(3).build();
		
		ShoppingCart cart = new ShoppingCartBuilder().items(productWithQuantityA,productWithQuantityB).build();
		
		Entry<CreditCardProfile, Map<InstallmentPlan, Double>> availablePlansEntry = service.availablePlanFor("11111", cart);

		assertThat(availablePlansEntry.getKey(),equalTo(cardProfile));
		assertThat(availablePlansEntry.getValue(),hasEntry(installmentPlan, 808d));
	}
	
	

}

package com.sk.domain.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sk.domain.CreditCardProfile;
import com.sk.domain.InstallmentPlan;
import com.sk.util.BaseIntegration;
import com.sk.util.builder.CreditCardProfileBuilder;
import com.sk.util.builder.InstallmentPlanBuilder;

public class CreditCardProfileDaoTest extends BaseIntegration {
	
	@Autowired
	private CreditCardProfileDao dao;
	
	@Test
	public void shouldPersist() {
		InstallmentPlan installmentPlan = new InstallmentPlanBuilder().months(2).interestRate(5d).build();
		CreditCardProfile creditCardProfile = new CreditCardProfileBuilder().vendor("Vendor").installmentPlans(installmentPlan).build();
		
		CreditCardProfile persisted = dao.persist(creditCardProfile);
		
		flushAndClear();
		
		CreditCardProfile fromDb = reget(persisted);
		
		assertThat(fromDb.getVendor(), equalTo("Vendor"));
		assertThat(fromDb.getInstallmentPlans().size(),equalTo(1));
		
		InstallmentPlan plan = fromDb.getInstallmentPlans().iterator().next();
		
		assertThat(plan.getInterestRate(),equalTo(5.0d));
		assertThat(plan.getMonths(),equalTo(2));
	}

}

package com.sk.util.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.sk.domain.CreditCardProfile;
import com.sk.domain.InstallmentPlan;

public class CreditCardProfileBuilder extends BaseBuilder<CreditCardProfile,CreditCardProfileBuilder> {
	
	private String vendor = RandomStringUtils.randomAlphabetic(10);
	private List<InstallmentPlan> installmentPlans = new ArrayList<InstallmentPlan>();
	
	public CreditCardProfileBuilder vendor(String vendor) {
		this.vendor = vendor;
		return this;
	}

	public CreditCardProfileBuilder installmentPlans(InstallmentPlan... installmentPlans) {
		this.installmentPlans.addAll(Arrays.asList(installmentPlans));
		return this;
	}


	@Override
	protected CreditCardProfile doBuild() {
		CreditCardProfile profile = new CreditCardProfile();
		profile.setVendor(vendor);
		profile.setInstallmentPlans(installmentPlans);
		return profile;
	}

}

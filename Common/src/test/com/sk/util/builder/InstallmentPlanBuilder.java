package com.sk.util.builder;

import com.sk.domain.InstallmentPlan;

public class InstallmentPlanBuilder extends BaseBuilder<InstallmentPlan, InstallmentPlanBuilder>{
	
	private int months = 1;
	private double interestRate = 1d;

	public InstallmentPlanBuilder months(int months) {
		this.months = months;
		return this;
	}

	public InstallmentPlanBuilder interestRate(double interestRate) {
		this.interestRate = interestRate;
		return this;
	}

	@Override
	protected InstallmentPlan doBuild() {
		InstallmentPlan plan = new InstallmentPlan();
		plan.setMonths(months);
		plan.setInterestRate(interestRate);
		return plan;
	}

}

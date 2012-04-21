package com.sk.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="creditCardProfile")
public class CreditCardProfile extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Basic
	private String vendor;
	
	@OneToMany(cascade={CascadeType.ALL,CascadeType.MERGE})
	private List<InstallmentPlan> installmentPlans = new ArrayList<InstallmentPlan>();

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public List<InstallmentPlan> getInstallmentPlans() {
		return installmentPlans;
	}

	public void setInstallmentPlans(List<InstallmentPlan> installmentPlans) {
		this.installmentPlans = installmentPlans;
	}
	
	public void addInstallmentPlan(InstallmentPlan installmentPlan) {
		installmentPlans.add(installmentPlan);
	}
	
	public void deleteInstallmentPlan(InstallmentPlan installmentPlan) {
		installmentPlans.remove(installmentPlan);
	}

	public double monthlyPaymentOf(double amount, int months) {
		InstallmentPlan planToUse = findInstallmentPlanFor(months);
		
		if (planToUse == null) {
			throw new IllegalArgumentException("Missing installment plan for" + months);
		} 
		
		amount += amount * (planToUse.getInterestRate() / 100);
		double payment =  amount / (months * 1d);
		return (Math.round(payment*100))/100d;

	}

	private InstallmentPlan findInstallmentPlanFor(int months) {
		for (InstallmentPlan eachPlan : installmentPlans) {
			if (eachPlan.getMonths() == months) {
				return eachPlan;
			}
		}
		return null;
	}
	

}

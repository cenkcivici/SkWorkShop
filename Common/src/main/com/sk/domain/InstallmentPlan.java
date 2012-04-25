package com.sk.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.Hibernate;

@Entity
@Table(name = "installment_plan")
public class InstallmentPlan extends BaseEntity implements Comparable<InstallmentPlan> {

	private static final long serialVersionUID = 1L;

	@Basic
	private int months;

	@Basic
	private double interestRate;

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(interestRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + months;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (Hibernate.getClass(this) != Hibernate.getClass(obj))
			return false;
		InstallmentPlan other = (InstallmentPlan) obj;
		if (Double.doubleToLongBits(interestRate) != Double.doubleToLongBits(other.interestRate))
			return false;
		if (months != other.months)
			return false;
		return true;
	}

	@Override
	public int compareTo(InstallmentPlan toCompare) {
		if (getMonths() == toCompare.getMonths()) {
			return 0;
		}
		return getMonths() > toCompare.getMonths() ? 1 : -1;
	}
}

package com.sk.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="credit_card")
public class CreditCard extends BaseEntity{

	private static final long serialVersionUID = 3918351093213337323L;

	@Column(length=255)
	private String owner;
	
	@Column(length=60)
	private String cardNumber = "4539992043491562"; // for testing
	
	@Column(length=25)
	private String cvc;
	
	@Column(length=25)
	private String month;
	
	@Column(length=25)
	private String year;

	@Enumerated(EnumType.STRING)
	private CreditCardType creditCardType;

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getCvc() {
		return cvc;
	}

	public void setCvc(String cvc) {
		this.cvc = cvc;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public CreditCardType getCreditCardType() {
		return creditCardType;
	}

	public void setCreditCardType(CreditCardType creditCardType) {
		this.creditCardType = creditCardType;
	}
	
	public String getExpireDate() {
		return getMonth() + getYear();
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
					.appendSuper(super.toString())
					.append("owner", owner)
					.append("cardNumber", cardNumber)
					.append("cvc", cvc)
					.append("month", month)
					.append("year", year).toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((cardNumber == null) ? 0 : cardNumber.hashCode());
		result = prime * result
				+ ((creditCardType == null) ? 0 : creditCardType.hashCode());
		result = prime * result + ((cvc == null) ? 0 : cvc.hashCode());
		result = prime * result + ((month == null) ? 0 : month.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		CreditCard other = (CreditCard) obj;
		if (cardNumber == null) {
			if (other.cardNumber != null){
				return false;
			}
		} else if (!cardNumber.equals(other.cardNumber)){
			return false;
		}
		if (creditCardType != other.creditCardType){
			return false;
		}
		if (cvc == null) {
			if (other.cvc != null){
				return false;
			}
		} else if (!cvc.equals(other.cvc)){
			return false;
		}
		if (month == null) {
			if (other.month != null){
				return false;
			}
		} else if (!month.equals(other.month)){
			return false;
		}
		if (owner == null) {
			if (other.owner != null){
				return false;
			}
		} else if (!owner.equals(other.owner)){
			return false;
		}
		if (year == null) {
			if (other.year != null){
				return false;
			}
		} else if (!year.equals(other.year)){
			return false;
		}
		return true;
	}
	
}

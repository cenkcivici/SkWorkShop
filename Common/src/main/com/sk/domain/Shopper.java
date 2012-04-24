package com.sk.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="Shopper")
public class Shopper extends BaseEntity{

	private static final long serialVersionUID = 2109249821813990338L;

	@Column(length=150)
	private String email;

	@Column(length=255)
	private String name;
	
	@OneToMany(cascade={CascadeType.ALL,CascadeType.MERGE})
	private Set<CreditCard> creditCardList = new HashSet<CreditCard>();
	
	public boolean hasAnyCreditCardInfo() {
		return !creditCardList.isEmpty();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (!super.equals(obj)){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		Shopper other = (Shopper) obj;
		if (email == null) {
			if (other.email != null){
				return false;
			}
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
					.appendSuper(super.toString()).append("email", email).toString();
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<CreditCard> getCreditCardList() {
		return creditCardList;
	}
	
	public void setCreditCardList(Set<CreditCard> creditCardList) {
		this.creditCardList = creditCardList;
	}

	public void addCreditCard(CreditCard creditCard) {
		creditCardList.add(creditCard);
	}

}

package com.sk.domain;public enum CreditCardType {	VISA("Visa"),	MASTERCARD("Mastercard");		private final String name;	private CreditCardType(String name){		this.name = name;	}	public String getName() {		return name;	}	}
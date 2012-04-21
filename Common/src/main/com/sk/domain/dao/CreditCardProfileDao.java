package com.sk.domain.dao;

import org.springframework.stereotype.Repository;

import com.sk.domain.CreditCardProfile;

@Repository
public class CreditCardProfileDao extends GenericDao<CreditCardProfile>{

	public CreditCardProfileDao() {
		super(CreditCardProfile.class);
	}

}

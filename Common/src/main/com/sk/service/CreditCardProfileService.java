package com.sk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sk.domain.CreditCardProfile;
import com.sk.domain.dao.CreditCardProfileDao;

@Service
public class CreditCardProfileService {

	@Autowired
	private CreditCardProfileDao creditCardProfileDao;
	
	
	public List<CreditCardProfile> getAll() {
		return creditCardProfileDao.getAll();
	}

	@Transactional
	public void delete(CreditCardProfile creditCardProfile) {
		creditCardProfileDao.delete(creditCardProfile);
	}

	@Transactional
	public void save(CreditCardProfile creditCardProfile) {
		creditCardProfileDao.persist(creditCardProfile);
	}

	public CreditCardProfile attach(CreditCardProfile creditCardProfile) {
		return creditCardProfileDao.get(creditCardProfile.getId());
	}

}

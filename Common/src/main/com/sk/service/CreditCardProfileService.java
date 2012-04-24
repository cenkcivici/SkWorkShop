package com.sk.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sk.domain.CreditCardProfile;
import com.sk.domain.InstallmentPlan;
import com.sk.domain.ShoppingCart;
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

	public Map<CreditCardProfile, Map<InstallmentPlan, Double>> paymentsFor(ShoppingCart cart) {
		double total = cart.getTotalCost();
		
		List<CreditCardProfile> allProfiles = creditCardProfileDao.getAll();
		Map<CreditCardProfile,Map<InstallmentPlan, Double>> paymentsMap = new HashMap<CreditCardProfile, Map<InstallmentPlan,Double>>();
		
		for (CreditCardProfile eachCardProfile : allProfiles) {
			Map<InstallmentPlan,Double> planMap = new TreeMap<InstallmentPlan, Double>();
			paymentsMap.put(eachCardProfile, planMap);
			for (InstallmentPlan eachInstallmentPlan : eachCardProfile.getInstallmentPlans()) {
				double monthlyPayment = eachCardProfile.monthlyPaymentOf(total, eachInstallmentPlan.getMonths());
				planMap.put(eachInstallmentPlan, monthlyPayment);
			}
		}
		
		return paymentsMap;
	}
	
	

}

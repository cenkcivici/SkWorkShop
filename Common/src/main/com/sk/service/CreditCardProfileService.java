package com.sk.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
		Map<CreditCardProfile, Map<InstallmentPlan, Double>> paymentsMap = new TreeMap<CreditCardProfile, Map<InstallmentPlan, Double>>();
		List<CreditCardProfile> allProfiles = creditCardProfileDao.getAll();

		for (CreditCardProfile eachCardProfile : allProfiles) {
			populatePlansFor(cart.getTotalCost(), paymentsMap, eachCardProfile);
		}

		return paymentsMap;
	}

	public Entry<CreditCardProfile, Map<InstallmentPlan, Double>> availablePlanFor(String creditCardNo, ShoppingCart cart) {
		
		Map<CreditCardProfile, Map<InstallmentPlan, Double>> paymentsMap = paymentsFor(cart);
		
		for (Entry<CreditCardProfile,Map<InstallmentPlan, Double>> eachEntry : paymentsMap.entrySet()) {
			if (eachEntry.getKey().issuerOf(creditCardNo)) {
				return eachEntry;
			}
		}
		
		return null;
	}

	private void populatePlansFor(double total, Map<CreditCardProfile, Map<InstallmentPlan, Double>> availablePaymentsMap, CreditCardProfile creditCardProfile) {
		Map<InstallmentPlan, Double> planMap = new TreeMap<InstallmentPlan, Double>();
		for (InstallmentPlan eachInstallmentPlan : creditCardProfile.getInstallmentPlans()) {
			double monthlyPayment = creditCardProfile.monthlyPaymentOf(total, eachInstallmentPlan.getMonths());
			planMap.put(eachInstallmentPlan, monthlyPayment);
		}
		availablePaymentsMap.put(creditCardProfile, planMap);
	}

}

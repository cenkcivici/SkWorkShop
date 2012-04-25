package com.sk.domain.dao;

import org.springframework.stereotype.Repository;

import com.sk.domain.InstallmentPlan;

@Repository
public class InstallmentPlanDao extends GenericDao<InstallmentPlan> {

	public InstallmentPlanDao() {
		super(InstallmentPlan.class);
	}
	
	
	

}

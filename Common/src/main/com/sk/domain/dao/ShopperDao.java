package com.sk.domain.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sk.domain.Shopper;

@Repository
public class ShopperDao extends GenericDao<Shopper>{

	public ShopperDao() {
		super(Shopper.class);
	}

	public Shopper findByEmail(String email) {
		
		Criteria criteria = getSession().createCriteria(Shopper.class);
		criteria.add(Restrictions.eq("email", email));
		return (Shopper)criteria.uniqueResult();
	}

}

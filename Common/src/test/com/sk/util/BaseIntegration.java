package com.sk.util;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.sk.domain.BaseEntity;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager")
@ContextConfiguration(locations = { "classpath:/resources/applicationContext-common.xml" })
public class BaseIntegration {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> T reget(T entity) {
		return (T) getSession().get(Hibernate.getClass(entity), entity.getId());
	}

	public void flushAndClear() {
		getSession().flush();
		getSession().clear();
	}

	@Test
	public void shouldHaveAtLeastOneTest() {
		
	}

}

package com.sk.service.memcache;

import net.spy.memcached.MemcachedClient;

import org.springframework.beans.factory.FactoryBean;

public class MemcachedClientFactoryBean implements FactoryBean<MemcachedClient> {

	private MemcachedClient memcachedClient;
	
	public void setMemcachedClientFactoryBean(MemcachedClient memcachedClient) throws Exception {
		this.memcachedClient = memcachedClient;
	}

	@Override
	public MemcachedClient getObject() throws Exception {
		return memcachedClient;
	}

	@Override
	public Class<?> getObjectType() {
		return MemcachedClient.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
	
	public void destroy() {
		memcachedClient.shutdown();
	}

}

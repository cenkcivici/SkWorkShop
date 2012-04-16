package com.sk.service;

import net.spy.memcached.MemcachedClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
	
	@Autowired
	private MemcachedClient memcachedClient;

}

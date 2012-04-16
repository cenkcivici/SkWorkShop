package com.sk.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.when;
import net.spy.memcached.MemcachedClient;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CacheServiceTest {
	@Mock
	private MemcachedClient cacheClient;

	private CacheService service;

	@Before
	public void before() {
		service = new CacheService(cacheClient);
	}

	@Test
	public void shouldSendToMemcached() {
		service.put("data", "value", 100);
	}

	@Test
	public void shouldGetFromMemcached() {
		String element = "CCC";
		when(cacheClient.get("key")).thenReturn(element);

		Object cachedElement = service.get("key");

		assertThat(cachedElement, sameInstance((Object) element));
	}

}

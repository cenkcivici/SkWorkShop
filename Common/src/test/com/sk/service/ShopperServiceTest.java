package com.sk.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sk.domain.Shopper;
import com.sk.domain.dao.ShopperDao;
import com.sk.service.encryption.EncryptionService;
import com.sk.util.builder.ShopperBuilder;

@RunWith(MockitoJUnitRunner.class)
public class ShopperServiceTest {

	private ShopperService shopperService;
	@Mock private EncryptionService encryptionService;
	@Mock private ShopperDao shopperDao;
	
	@Before
	public void init(){
		shopperService = new ShopperService();
		shopperService.setEncryptionService(encryptionService);
		shopperService.setShopperDao(shopperDao);
	}
	
	@Test
	public void shouldSaveCreditCardInfo(){
		
		Shopper shopper = new ShopperBuilder().build();
		when(encryptionService.encrypt("12341234")).thenReturn("ABCDABCD");
		when(encryptionService.encrypt("003")).thenReturn("ZXC");
		
		shopperService.encryptAndsaveCardInfo(shopper, "12341234", "003");

		assertThat(shopper.getEncryptedCardNo(), equalTo("ABCDABCD"));
		assertThat(shopper.getEncryptedCVC(), equalTo("ZXC"));
		verify(shopperDao).persist(shopper);
	}
	
	@Test
	public void shouldReturnStubShopperIfExist(){
		
		Shopper stubShopper = new ShopperBuilder().email("default@default.com").build();
		when(shopperDao.findByEmail("default@default.com")).thenReturn(stubShopper);
		
		Shopper shopper = shopperService.getStubShopper();

		assertThat(shopper, equalTo(stubShopper));
	}
	
	@Test
	public void shouldCreateStubShopperIfNotExists(){
		when(shopperDao.findByEmail("default@default.com")).thenReturn(null);
		
		shopperService.getStubShopper();
		
		ArgumentCaptor<Shopper> argument = ArgumentCaptor.forClass(Shopper.class);
		verify(shopperDao).persist(argument.capture());
		assertThat(argument.getValue().getEmail(), equalTo("default@default.com"));
		assertThat(argument.getValue().getName(), equalTo("Default Shopper"));
	}
}

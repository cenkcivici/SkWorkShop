package com.sk.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sk.domain.CreditCard;
import com.sk.domain.CreditCardType;
import com.sk.domain.Shopper;
import com.sk.domain.dao.ShopperDao;
import com.sk.service.encryption.EncryptionService;
import com.sk.util.builder.CreditCardBuilder;
import com.sk.util.builder.ShopperBuilder;

@RunWith(MockitoJUnitRunner.class)
public class ShopperServiceTest {

	private ShopperService shopperService;
	@Mock private EncryptionService encryptionService;
	@Mock private ShopperDao shopperDao;
	
	@Before
	public void init(){
		shopperService = new ShopperService(encryptionService, shopperDao);
	}
	
	@Test
	public void shouldSaveCreditCardInfo(){
		
		Shopper shopper = new ShopperBuilder().build();
		CreditCard card = new CreditCardBuilder().owner("Shopper").cardNumber("12341234").cvc("003").month("06").year("12").cardType(CreditCardType.VISA).build();
		CreditCard encryptedCard = new CreditCardBuilder().owner("ZAQXSW").cardNumber("ABCDABCD").cvc("ZXC").month("CVB").year("VBN").cardType(CreditCardType.VISA).build();
		
		when(encryptionService.encrypt("Shopper")).thenReturn("ZAQXSW");
		when(encryptionService.encrypt("12341234")).thenReturn("ABCDABCD");
		when(encryptionService.encrypt("003")).thenReturn("ZXC");
		when(encryptionService.encrypt("06")).thenReturn("CVB");
		when(encryptionService.encrypt("12")).thenReturn("VBN");
		
		shopperService.encryptAndsaveCardInfo(shopper, card);

		assertThat(shopper.getCreditCardList(), hasItem(encryptedCard));
		verify(shopperDao).persist(shopper);
	}
	
	@Test
	public void shouldDecryptCreditCardInfo(){
		CreditCard card = new CreditCardBuilder().owner("Shopper").cardNumber("12341234").cvc("003").month("06").year("12").cardType(CreditCardType.VISA).build();
		CreditCard encryptedCard = new CreditCardBuilder().owner("ZAQXSW").cardNumber("ABCDABCD").cvc("ZXC").month("CVB").year("VBN").cardType(CreditCardType.VISA).build();
		
		when(encryptionService.decrypt("ZAQXSW")).thenReturn("Shopper");
		when(encryptionService.decrypt("ABCDABCD")).thenReturn("12341234");
		when(encryptionService.decrypt("ZXC")).thenReturn("003");
		when(encryptionService.decrypt("CVB")).thenReturn("06");
		when(encryptionService.decrypt("VBN")).thenReturn("12");
		
		CreditCard decryptCard = shopperService.decryptCreditCardInfo(encryptedCard);
		
		assertThat(decryptCard, equalTo(card));
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

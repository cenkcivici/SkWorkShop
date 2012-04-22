package com.sk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sk.domain.CreditCard;
import com.sk.domain.Shopper;
import com.sk.domain.dao.ShopperDao;
import com.sk.service.encryption.EncryptionService;

@Service
public class ShopperService {

	@Autowired private EncryptionService encryptionService;
	@Autowired private ShopperDao shopperDao;

	public void encryptAndsaveCardInfo(Shopper shopper, CreditCard card) {
		
		CreditCard encryptCard = new CreditCard();
		encryptCard.setOwner(encryptionService.encrypt(card.getOwner()));
		encryptCard.setCardNumber(encryptionService.encrypt(card.getCardNumber()));
		encryptCard.setCvc(encryptionService.encrypt(card.getCvc()));
		encryptCard.setMonth(encryptionService.encrypt(card.getMonth()));
		encryptCard.setYear(encryptionService.encrypt(card.getYear()));
		encryptCard.setCreditCardType(card.getCreditCardType());
		
		shopper.addCreditCard(encryptCard);
		shopperDao.persist(shopper);
	}

	public void setEncryptionService(EncryptionService encryptionService) {
		this.encryptionService = encryptionService;
	}

	public void setShopperDao(ShopperDao shopperDao) {
		this.shopperDao = shopperDao;
	}

	public Shopper getStubShopper() {
		Shopper stubShopper = shopperDao.findByEmail("default@default.com");
		if(stubShopper == null){
			Shopper shopper = new Shopper();
			shopper.setEmail("default@default.com");
			shopper.setName("Default Shopper");
			stubShopper = shopperDao.persist(shopper);
		}
		return stubShopper;
	}

}

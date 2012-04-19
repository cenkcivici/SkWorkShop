package com.sk.service;

import com.sk.domain.Shopper;
import com.sk.domain.dao.ShopperDao;
import com.sk.service.encryption.EncryptionService;

public class ShopperService {

	private EncryptionService encryptionService;
	private ShopperDao shopperDao;

	public void encryptAndsaveCardInfo(Shopper shopper, String cardNo, String cvc) {
		
		String encryptedCardNo = encryptionService.encrypt(cardNo);
		String encryptedCVC = encryptionService.encrypt(cvc);
		
		shopper.setEncryptedCardNo(encryptedCardNo);
		shopper.setEncryptedCVC(encryptedCVC);
		
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
			stubShopper = shopperDao.persist(shopper);
		}
		return stubShopper;
	}

}

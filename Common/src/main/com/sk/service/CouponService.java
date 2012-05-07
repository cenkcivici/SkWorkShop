package com.sk.service;

import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sk.domain.coupon.Coupon;
import com.sk.domain.coupon.CouponHolder;
import com.sk.domain.dao.CouponDao;

@Service
public class CouponService {

	@Autowired
	private CouponDao couponDao;
	
	public CouponService(){}
	
	public CouponService(CouponDao couponDao) {
		this.couponDao = couponDao;
	}

	public <T extends Coupon> List<T> getAllCouponsFor(Class<T> couponClass){
		return couponDao.getAllCoupons(couponClass);
	}
	
	public <T extends Coupon> void createCoupon(Class<T> couponClass, CouponHolder couponHolder, double discountAmount, int numberOfCoupons){
		
		try{
			for (int i = 0; i < numberOfCoupons; i++) {
				T coupon = couponClass.newInstance();
				coupon.setDiscount(discountAmount);
				coupon.setCouponHolder(couponHolder);
				coupon.setUsed(Boolean.FALSE);
	
				String couponString = prepareCouponString(couponClass);
				
				coupon.setCouponString(couponString);
				couponDao.persist(coupon);
			}
		}catch(IllegalAccessException e){
			throw new RuntimeException(e);
		}catch(InstantiationException e){
			throw new RuntimeException(e);
		}
	}
	
	public void deleteCoupon(Coupon coupon) {
		couponDao.delete(coupon);
	}

	private String prepareCouponString(Class<? extends Coupon> couponClass) {
		String couponString;
		Coupon existingCoupon;
		do{
			couponString = RandomStringUtils.randomAlphabetic(10);
			existingCoupon = couponDao.findByCouponString(couponString, couponClass);
		}while(existingCoupon != null);
		return couponString;
	}

}

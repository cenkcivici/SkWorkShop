package com.sk.service;

import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sk.domain.Order;
import com.sk.domain.coupon.Coupon;
import com.sk.domain.coupon.CouponHolder;
import com.sk.domain.dao.CouponDao;
import com.sk.service.exception.ServiceException;

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
	
	public Coupon getCoupon(String couponString){
		return couponDao.findByCouponString(couponString);
	}
	
	public Coupon persist(Coupon coupon){
		return couponDao.persist(coupon);
	}
	
	public <T extends Coupon> void createCoupon(Class<T> couponClass, CouponHolder couponHolder, double discountAmount, int numberOfCoupons){
		
		try{
			for (int i = 0; i < numberOfCoupons; i++) {
				T coupon = couponClass.newInstance();
				coupon.setDiscount(discountAmount);
				coupon.setCouponHolder(couponHolder);
				coupon.setUsed(Boolean.FALSE);
	
				String couponString = prepareCouponString();
				
				coupon.setCouponString(couponString);
				couponDao.persist(coupon);
			}
		}catch(IllegalAccessException e){
			throw new ServiceException(e);
		}catch(InstantiationException e){
			throw new ServiceException(e);
		}
	}
	
	public void deleteCoupon(Coupon coupon) {
		couponDao.delete(coupon);
	}

	private String prepareCouponString() {
		String couponString;
		Coupon existingCoupon;
		
		do{
			couponString = RandomStringUtils.randomAlphabetic(10);
			existingCoupon = couponDao.findByCouponString(couponString);
		}while(existingCoupon != null);
		
		return couponString;
	}

	public Coupon getUnusedCoupon(String couponString, Order order) {
		
		Coupon coupon = couponDao.findUnusedByCouponString(couponString);
		if(coupon != null && coupon.canUseCoupon(order)){
			return coupon;
		}
		
		return null;
	}

	public void useCouponIfAvailable(String couponString, Order order) {
		Coupon coupon = getUnusedCoupon(couponString, order);
		if(coupon != null){
			order.useCoupon(coupon);
		}
	}

}

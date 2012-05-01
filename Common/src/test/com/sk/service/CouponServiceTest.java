package com.sk.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.sk.domain.Shopper;
import com.sk.domain.coupon.ShopperCoupon;
import com.sk.domain.dao.CouponDao;
import com.sk.util.builder.ShopperBuilder;

@RunWith(MockitoJUnitRunner.class)
public class CouponServiceTest {

	private CouponService couponService;
	@Mock private CouponDao couponDao;
	
	private String couponString;
	private Integer callCount = 0;
	
	@Before
	public void init(){
		couponService = new CouponService(couponDao);
	}
	
	@Test
	public void shouldCreateCouponForShopper(){
		Shopper shopper = new ShopperBuilder().build();
		couponService.createCouponForShopper(shopper, 10, 2);
		
		ArgumentCaptor<ShopperCoupon> captor = ArgumentCaptor.forClass(ShopperCoupon.class);
		verify(couponDao, times(2)).persist(captor.capture());
		
		List<ShopperCoupon> shopperCoupons = captor.getAllValues();
		assertThat(shopperCoupons.get(0).getShopper(), equalTo(shopper));
		assertThat(shopperCoupons.get(1).getDiscount(), equalTo(10D));
		assertThat(shopperCoupons.get(0).getCouponString().length(), equalTo(10));
		assertThat(shopperCoupons.get(1).isUsed(), equalTo(false));
	}
	
	@Test
	public void shouldBeUniqueShopperCoupon(){
		Shopper shopper = new ShopperBuilder().build();
		final ShopperCoupon existedShopperCoupon = new ShopperCoupon();
		
		when(couponDao.findByCouponString(anyString())).thenAnswer(new Answer<ShopperCoupon>() {

			@Override
			public ShopperCoupon answer(InvocationOnMock invocation) throws Throwable {
				if(callCount == 1){
					return null;
				}else{
					couponString = (String)invocation.getArguments()[0];
					callCount++;
					return existedShopperCoupon;
				}
			}
		
		});
		
		couponService.createCouponForShopper(shopper, 10, 1);
		
		ArgumentCaptor<ShopperCoupon> captor = ArgumentCaptor.forClass(ShopperCoupon.class);
		verify(couponDao, times(2)).findByCouponString(anyString());
		verify(couponDao).persist(captor.capture());
		
		List<ShopperCoupon> shopperCoupons = captor.getAllValues();
		assertThat(shopperCoupons.get(0).getCouponString(), not(equalTo(couponString)));
		
	}
	
	@Test
	public void shouldReturnAllShopperCoupons(){
		couponService.getAllShopperCoupons();
		verify(couponDao).getAllCoupons(ShopperCoupon.class);
	}
	
}

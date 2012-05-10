package com.sk.service;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
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

import com.sk.domain.Order;
import com.sk.domain.Shopper;
import com.sk.domain.coupon.CategoryCoupon;
import com.sk.domain.coupon.Coupon;
import com.sk.domain.coupon.CouponHolder;
import com.sk.domain.coupon.ShopperCoupon;
import com.sk.domain.dao.CouponDao;
import com.sk.util.builder.CategoryBuilder;
import com.sk.util.builder.OrderBuilder;
import com.sk.util.builder.ShopperBuilder;
import com.sk.util.builder.ShopperCouponBuilder;

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
		CouponHolder shopper = new ShopperBuilder().build();
		couponService.createCoupon(ShopperCoupon.class, shopper, 10, 2);
		
		ArgumentCaptor<ShopperCoupon> captor = ArgumentCaptor.forClass(ShopperCoupon.class);
		verify(couponDao, times(2)).persist(captor.capture());
		
		List<ShopperCoupon> shopperCoupons = captor.getAllValues();
		assertThat(shopperCoupons.get(0).getCouponHolder(), equalTo(shopper));
		assertThat(shopperCoupons.get(1).getDiscount(), equalTo(10D));
		assertThat(shopperCoupons.get(0).getCouponString().length(), equalTo(10));
		assertThat(shopperCoupons.get(1).isUsed(), equalTo(false));
	}
	
	@Test
	public void shouldCreateCouponForCategory(){
		CouponHolder category = new CategoryBuilder().build();
		couponService.createCoupon(CategoryCoupon.class, category, 10, 2);
		
		ArgumentCaptor<CategoryCoupon> captor = ArgumentCaptor.forClass(CategoryCoupon.class);
		verify(couponDao, times(2)).persist(captor.capture());
		
		List<CategoryCoupon> categoryCoupons = captor.getAllValues();
		assertThat(categoryCoupons.get(0).getCouponHolder(), equalTo(category));
	}
	
	@Test
	public void shouldBeUniqueCoupon(){
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
		
		couponService.createCoupon(ShopperCoupon.class, shopper, 10, 1);
		
		ArgumentCaptor<ShopperCoupon> captor = ArgumentCaptor.forClass(ShopperCoupon.class);
		verify(couponDao, times(2)).findByCouponString(anyString());
		verify(couponDao).persist(captor.capture());
		
		List<ShopperCoupon> shopperCoupons = captor.getAllValues();
		assertThat(shopperCoupons.get(0).getCouponString(), not(equalTo(couponString)));
		
	}
	
	@Test
	public void shouldReturnAllCouponsAccordingToType(){
		couponService.getAllCouponsFor(ShopperCoupon.class);
		verify(couponDao).getAllCoupons(ShopperCoupon.class);
	}
	
	@Test
	public void shouldReturnUnusedCouponIfAvailable(){
		
		Shopper shopper = new ShopperBuilder().build();
		ShopperCoupon shopperCoupon = new ShopperCouponBuilder().shopper(shopper).build();
		Order order = new OrderBuilder().shopper(shopper).build();
		when(couponDao.findUnusedByCouponString("ABCabcZXCc")).thenReturn(shopperCoupon);
		
		Coupon coupon = couponService.getUnusedCoupon("ABCabcZXCc", order);

		assertThat((ShopperCoupon)coupon, equalTo(shopperCoupon));
	}
	
	@Test
	public void shouldReturnNullIfUnusedCouponNotExist(){
		Order order = new OrderBuilder().build();
		when(couponDao.findUnusedByCouponString("ABCabcZXCc")).thenReturn(null);
		
		Coupon coupon = couponService.getUnusedCoupon("ABCabcZXCc", order);

		assertThat(coupon, nullValue());
	}
	
	@Test
	public void shouldReturnNullIfUnusedCouponOwnerDifferent(){
		
		Shopper shopper = new ShopperBuilder().build();
		ShopperCoupon shopperCoupon = new ShopperCouponBuilder().shopper(shopper).build();
		
		Shopper differentShopper = new ShopperBuilder().build();
		Order order = new OrderBuilder().shopper(differentShopper).build();
		when(couponDao.findUnusedByCouponString("ABCabcZXCc")).thenReturn(shopperCoupon);
		
		Coupon coupon = couponService.getUnusedCoupon("ABCabcZXCc", order);

		assertThat(coupon, nullValue());
	}
	
	@Test
	public void shouldSetCouponStatusToUsedIfAvaiable(){
		
		Shopper shopper = new ShopperBuilder().build();
		Order order = new OrderBuilder().shopper(shopper).build();
		Coupon coupon = new ShopperCouponBuilder().shopper(shopper).build();
		when(couponDao.findUnusedByCouponString("ABCabcZXCc")).thenReturn(coupon);
		
		couponService.useCouponIfAvailable("ABCabcZXCc", order);
		
		assertThat(order.getCoupon(), equalTo(coupon));
		assertTrue(coupon.isUsed());
		
	}
}

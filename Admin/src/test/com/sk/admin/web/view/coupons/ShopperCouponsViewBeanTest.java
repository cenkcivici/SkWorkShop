package com.sk.admin.web.view.coupons;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sk.domain.Shopper;
import com.sk.domain.coupon.ShopperCoupon;
import com.sk.service.CouponService;
import com.sk.util.builder.ShopperBuilder;

@RunWith(MockitoJUnitRunner.class)
public class ShopperCouponsViewBeanTest {

	private ShopperCouponsViewBean viewBean;
	@Mock private CouponService couponService;
	
	@Before
	public void init(){
		viewBean = new ShopperCouponsViewBean();
		viewBean.setCouponService(couponService);
	}
	
	@Test
	public void shouldCreateCoupons(){
		Shopper shopper = new ShopperBuilder().build();
		viewBean.setSelectedShopper(shopper);
		viewBean.setDiscountAmount(10D);
		viewBean.setNumberOfCoupons(1);
		
		viewBean.createCoupon();

		verify(couponService).createCoupon(ShopperCoupon.class, shopper, 10D, 1); 
		assertThat(viewBean.getSelectedShopper(), nullValue());
		assertThat(viewBean.getDiscountAmount(), nullValue());
		assertThat(viewBean.getNumberOfCoupons(), nullValue());
	}
	
}

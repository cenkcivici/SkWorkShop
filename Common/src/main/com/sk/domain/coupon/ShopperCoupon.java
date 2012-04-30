package com.sk.domain.coupon;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.sk.domain.Shopper;

@Entity
@DiscriminatorValue("ShopperCoupon")
public class ShopperCoupon extends Coupon {

	private static final long serialVersionUID = -8960776463556210413L;

	@ManyToOne
	private Shopper shopper;

	public Shopper getShopper() {
		return shopper;
	}

	public void setShopper(Shopper shopper) {
		this.shopper = shopper;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		return true;
	}
	
	
}

package com.sk.frontend.web.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sk.domain.CreditCard;
import com.sk.domain.CreditCardPaymentMethod;
import com.sk.domain.CreditCardType;
import com.sk.domain.Shopper;
import com.sk.domain.ShoppingCart;
import com.sk.frontend.web.validator.CreditCardValidator;
import com.sk.service.OrderService;
import com.sk.service.payment.VPOSResponse;
import com.sk.service.payment.garanti.GarantiVPOSService;
import com.sk.service.ShopperService;
import com.sk.service.encryption.EncryptionService;

@Controller
@RequestMapping("/payment")
public class PaymentController {

	@InitBinder
	public void init(WebDataBinder binder) {
		binder.setValidator(new CreditCardValidator());
	}

	@Autowired
	private OrderService orderService;

	@Autowired
	private GarantiVPOSService garantiVPOSService;
	@Autowired
	private ShopperService shopperService;
	@Autowired
	private EncryptionService encryptionService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView show() {

		Shopper shopper = shopperService.getStubShopper();
		CreditCardPaymentMethod payment = new CreditCardPaymentMethod();

		Boolean showSaveCheck = Boolean.TRUE;
		if (!shopper.getCreditCardList().isEmpty()) {
			showSaveCheck = Boolean.FALSE;
			
			CreditCard encryptCard = shopper.getCreditCardList().iterator().next();
			payment.setOwner(encryptionService.decrypt(encryptCard.getOwner()));
			payment.setCardNumber(encryptionService.decrypt(encryptCard.getCardNumber()));
			payment.setCvc(encryptionService.decrypt(encryptCard.getCvc()));
			payment.setMonth(encryptionService.decrypt(encryptCard.getMonth()));
			payment.setYear(encryptionService.decrypt(encryptCard.getYear()));
			payment.setCreditCardType(encryptCard.getCreditCardType());
		}

		ModelAndView mav = getPaymentMAV(payment);
		mav.addObject("showSaveCheck", showSaveCheck);
		return mav;
	}

	protected ModelAndView getPaymentMAV(CreditCardPaymentMethod creditCardPaymentMethod) {
		ModelAndView mav = new ModelAndView("payment");

		mav.addObject("payment", creditCardPaymentMethod);
		mav.addObject("creditCardTypes", getCreditCardTypes());
		mav.addObject("months", getMonths());
		mav.addObject("years", getYears());

		return mav;
	}

	protected Map<String, String> getCreditCardTypes() {
		Map<String, String> creditCardTypes = new HashMap<String, String>();
		for (CreditCardType creditCardType : CreditCardType.values()) {
			creditCardTypes.put(creditCardType.name(), creditCardType.getName());
		}
		return creditCardTypes;
	}

	protected Map<String, String> getMonths() {
		Map<String, String> months = new TreeMap<String, String>();
		for (int i = 1; i < 13; i++) {
			if (i < 10) {
				months.put("0" + i, "0" + i);
			} else {
				months.put("" + i, "" + i);
			}
		}
		return months;
	}

	protected Map<String, String> getYears() {
		Map<String, String> years = new TreeMap<String, String>();
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		for (int year = currentYear; year < currentYear + 12; year++) {
			years.put("" + year, "" + year);
		}
		return years;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView submit(@Validated @ModelAttribute("payment") CreditCardPaymentMethod payment, BindingResult binder, HttpServletRequest request) {
		if (binder.hasErrors()) {
			ModelAndView mav = getPaymentMAV(payment);
			mav.addObject("showSaveCheck", true);
			return mav;
		}

		ShoppingCart cart = getShoppingCart(request);
		VPOSResponse response = garantiVPOSService.makePayment(payment,cart.getTotalCost());
		if (response.isSuccessful()) {
			return createOrder(payment, request);
		} else {
			return showError(payment);
		}

	}

	protected ModelAndView showError(CreditCardPaymentMethod payment) {
		ModelAndView modelAndView = getPaymentMAV(payment);
		modelAndView.addObject("paymentFailed", true);
		return modelAndView;
	}

	protected ModelAndView createOrder(CreditCardPaymentMethod payment, HttpServletRequest request) {
		if (request.getParameter("saveCardInfo") != null && request.getParameter("saveCardInfo").equals("1")) {
			Shopper shopper = shopperService.getStubShopper();
			
			CreditCard card = new CreditCard();
			card.setOwner(payment.getOwner());
			card.setCardNumber(payment.getCardNumber());
			card.setCvc(payment.getCvc());
			card.setMonth(payment.getMonth());
			card.setYear(payment.getYear());
			card.setCreditCardType(payment.getCreditCardType());
			shopperService.encryptAndsaveCardInfo(shopper, card);
		}

		ShoppingCart shoppingCart = getShoppingCart(request);
		orderService.createOrder(shoppingCart, payment);
		return new ModelAndView("confirm");
	}

	protected ShoppingCart getShoppingCart(HttpServletRequest request) {
		return (ShoppingCart) request.getAttribute("cart");
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public void setShopperService(ShopperService shopperService) {
		this.shopperService = shopperService;
	}

	public void setEncryptionService(EncryptionService encryptionService) {
		this.encryptionService = encryptionService;
	}

	public void setGarantiVPOSService(GarantiVPOSService garantiVPOSService) {
		this.garantiVPOSService = garantiVPOSService;
	}

}

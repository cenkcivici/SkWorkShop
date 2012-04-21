package com.sk.admin.web.view;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.sk.domain.CreditCardProfile;
import com.sk.service.CreditCardProfileService;

@ViewScoped
@ManagedBean
public class CreditCardProfilesView extends BaseView {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{creditCardProfileService}")
	private transient CreditCardProfileService cardProfileService;

	public void setCardProfileService(CreditCardProfileService cardProfileService) {
		this.cardProfileService = cardProfileService;
	}
	
	public List<CreditCardProfile> getAll() {
		return cardProfileService.getAll();
	}
	
	public void deleteCreditCardProfile(CreditCardProfile creditCardProfile) {
		cardProfileService.delete(creditCardProfile);
	}
	

}

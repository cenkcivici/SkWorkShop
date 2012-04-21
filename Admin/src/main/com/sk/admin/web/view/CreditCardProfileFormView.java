package com.sk.admin.web.view;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.sk.domain.CreditCardProfile;
import com.sk.domain.InstallmentPlan;
import com.sk.service.CreditCardProfileService;

@ManagedBean
@ViewScoped
public class CreditCardProfileFormView extends BaseView {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{creditCardProfileService}")
	private transient CreditCardProfileService creditCardProfileService;

	private CreditCardProfile creditCardProfile;
	
	private InstallmentPlan installmentPlan = new InstallmentPlan();

	@PostConstruct
	public void init() {
		creditCardProfile = (CreditCardProfile) facesHelper.flashGet("creditCardProfile");
		if (creditCardProfile == null) {
			creditCardProfile = new CreditCardProfile();
		} else {
			creditCardProfile = creditCardProfileService.attach(creditCardProfile);
			facesHelper.flashClear("creditCardProfile");
		}
	}
	
	public String save() {
		creditCardProfileService.save(creditCardProfile);
		facesHelper.addInfoMessage("cart profile saved");
		return facesHelper.redirectFaces("creditCardProfiles");
		
	}
	
	public String apply() {
		save();
		return null;
	}

	public void setCreditCardProfileService(CreditCardProfileService creditCardProfileService) {
		this.creditCardProfileService = creditCardProfileService;
	}
	
	public InstallmentPlan getInstallmentPlan() {
		return installmentPlan;
	}

	public void setInstallmentPlan(InstallmentPlan installmentPlan) {
		this.installmentPlan = installmentPlan;
	}

	public CreditCardProfile getCreditCardProfile() {
		return creditCardProfile;
	}

	public void setCreditCardProfile(CreditCardProfile creditCardProfile) {
		this.creditCardProfile = creditCardProfile;
	}
	
	
	public void addInstallmentPlan() {
		creditCardProfile.addInstallmentPlan(installmentPlan);
		installmentPlan = new InstallmentPlan();
		facesHelper.executeJS("installmentPlanDialog.hide()");
	}

}

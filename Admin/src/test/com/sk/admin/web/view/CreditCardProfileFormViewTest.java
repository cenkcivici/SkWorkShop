package com.sk.admin.web.view;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sk.domain.CreditCardProfile;
import com.sk.domain.InstallmentPlan;
import com.sk.service.CreditCardProfileService;
import com.sk.util.builder.CreditCardProfileBuilder;


@RunWith(MockitoJUnitRunner.class)
public class CreditCardProfileFormViewTest {
	
	@Mock
	private CreditCardProfileService service;
	
	@Mock
	private FacesHelper facesHelper;
	
	
	private CreditCardProfileFormView view;
	
	@Before
	public void init() {
		view = new CreditCardProfileFormView();
		view.setCreditCardProfileService(service);
		view.setFacesHelper(facesHelper);
	}
	
	@Test
	public void shouldCreateANewCertificateProfileForNewProfile() {
		when(facesHelper.flashGet("creditCardProfile")).thenReturn(null);
		
		view.init();
		
		assertThat(view.getCreditCardProfile().getId(), nullValue());
	}
	
	@Test
	public void shouldRetrieveCertificateProfileFromFlash() {
		CreditCardProfile creditCardProfile = new CreditCardProfileBuilder().id(10L).build();
		when(service.attach(creditCardProfile)).thenReturn(creditCardProfile);
		
		when(facesHelper.flashGet("creditCardProfile")).thenReturn(creditCardProfile);
		
		view.init();
		
		assertThat(view.getCreditCardProfile(), equalTo(creditCardProfile));
		verify(facesHelper).flashClear("creditCardProfile");
	}
	
	@Test
	public void shouldAddInstallmentPlan() {
		view.init();
		InstallmentPlan installmentPlan = view.getInstallmentPlan();
		view.addInstallmentPlan();

		assertThat(view.getCreditCardProfile().getInstallmentPlans().size(), equalTo(1));
		assertThat(view.getInstallmentPlan(),not(equalTo(installmentPlan)));
		verify(facesHelper).executeJS("installmentPlanDialog.hide()");
	}

}

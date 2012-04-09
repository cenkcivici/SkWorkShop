package com.sk.admin.web.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

@ManagedBean(name = "facesHelper")
@ApplicationScoped
public class FacesHelper implements Serializable {

	private static final long serialVersionUID = -4502251921541100055L;

	private FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	private RequestContext getRequestContext() {
		return RequestContext.getCurrentInstance();
	}

	public Flash getFlash() {
		return getFacesContext().getExternalContext().getFlash();
	}

	public Object flashGet(String parameter) {
		return getFlash().get(parameter);
	}

	public void flashPutNow(String parameter, Object value) {
		getFlash().putNow(parameter, value);
	}

	public void flashPut(String parameter, Object value) {
		getFlash().put(parameter, value);
	}

	public ExternalContext getExternalContext() {
		return getFacesContext().getExternalContext();
	}

	public HttpServletRequest getRequest() {
		return (HttpServletRequest) getExternalContext().getRequest();
	}

	public HttpServletResponse getResponse() {
		return (HttpServletResponse) getExternalContext().getResponse();
	}

	public void forward(String path) {
		try {
			RequestDispatcher dispatcher = getRequest().getRequestDispatcher(
					path);
			dispatcher.forward(getRequest(), getResponse());
			getFacesContext().responseComplete();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void redirect(String path) {
		try {
			getExternalContext().redirect(path);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void performNavigation(String to) {
		ConfigurableNavigationHandler navHandler = (ConfigurableNavigationHandler) getFacesContext()
				.getApplication().getNavigationHandler();
		navHandler.performNavigation(to);
	}

	public Locale getCurrentLocale() {
		Locale locale = null;
		UIViewRoot viewRoot = getFacesContext().getViewRoot();
		if (viewRoot != null) {
			locale = viewRoot.getLocale();
		}
		if (locale == null) {
			locale = Locale.getDefault();
		}
		return locale;
	}

	public void flashClear(String parameter) {
		flashPutNow(parameter, null);
	}

	public String getRequestParameter(String parameterName) {
		return getExternalContext().getRequestParameterMap().get(parameterName);
	}

	public void addPartialUpdateTarget(String target) {
		getRequestContext().addPartialUpdateTarget(target);
	}

	public void executeJS(String js) {
		getRequestContext().execute(js);
	}

	public String getCurrentViewName() {
		return getFacesContext().getViewRoot().getViewId();
	}

	public HttpSession getSession() {
		return (HttpSession) getFacesContext().getExternalContext().getSession(
				false);
	}

	public String getCurrentPageUrl() {
		return getExternalContext().getRequestContextPath()
				+ getCurrentViewName();
	}

	public ServletContext getServletContext() {
		return (ServletContext) getExternalContext().getContext();
	}

	public void addInfoMessage(String messageText) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				null, messageText);
		getFacesContext().addMessage("", message);
	}

	public String getServletContextPath() {
		ServletContext servletContext = (ServletContext) getExternalContext().getContext();
		return servletContext.getRealPath("/");
	}

}
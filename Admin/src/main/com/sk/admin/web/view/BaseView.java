package com.sk.admin.web.view;

import java.io.Serializable;

import javax.faces.bean.ManagedProperty;

public abstract class BaseView implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{facesHelper}")
    protected transient FacesHelper facesHelper;

	public void setFacesHelper(FacesHelper facesHelper) {
		this.facesHelper = facesHelper;
	}
}

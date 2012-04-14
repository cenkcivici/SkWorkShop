package com.sk.admin.web.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class FaceletUtils {

	public static int size(Collection<?> collection) {
		if (collection == null) {
			return 0;
		}
		return collection.size();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List toList(Set<?> set) {
		if (set == null || set.size() == 0) {
			return new ArrayList();
		}
		return new ArrayList(set);

	}

	public static String contextPath() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

}

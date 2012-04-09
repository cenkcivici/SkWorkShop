package com.sk.admin.web.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.jsf.FacesContextUtils;

import com.sk.domain.BaseEntity;
import com.sk.domain.dao.BaseEntityDao;

public class EntityConverter implements Converter {

	private BaseEntityDao baseEntityDao;

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		}

		try {
			int underscoreIndex = value.indexOf("_");
			int lastUnderscoreIndex = value.lastIndexOf("_");

			String className = value.substring(0, underscoreIndex);
			String id = value.substring(lastUnderscoreIndex + 1, value.length());

			Class<?> clazz = Class.forName(className);
			initializeIfBaseEntityDaoIsNotInitialized();
			return baseEntityDao.get(clazz, Long.parseLong(id));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return null;
		}

		BaseEntity baseEntity = (BaseEntity) value;
		return baseEntity.getClass().getName() + "_" + baseEntity.getId();
	}

	public void initializeIfBaseEntityDaoIsNotInitialized() {
		if (baseEntityDao == null) {
			baseEntityDao = (BaseEntityDao) FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance()).getBean("baseEntityDao");
		}

	}

}
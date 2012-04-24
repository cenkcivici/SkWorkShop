package com.sk.service.template;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineFactory;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.sk.service.exception.ServiceException;

@Component
public class VelocityHelper {

	@Autowired
	private VelocityEngineFactory velocityVPOSEngine;
	
	private VelocityEngine velocityEngine;

	@PostConstruct
	void init() throws IOException {
		velocityEngine = velocityVPOSEngine.createVelocityEngine();
	}

	public String getProcessed(String templateLocation, Map<String, Object> dataModel) throws ServiceException {
		StringWriter writer = new StringWriter();
		VelocityEngineUtils.mergeTemplate(velocityEngine, templateLocation, dataModel,writer);
		return writer.toString();
	}
}

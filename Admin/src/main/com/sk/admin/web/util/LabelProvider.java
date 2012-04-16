package com.sk.admin.web.util;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

public class LabelProvider implements SystemEventListener {

	@Override
	public boolean isListenerForSource(Object source) {
		return source instanceof HtmlOutputLabel;
	}

	@Override
	public void processEvent(SystemEvent event) throws AbortProcessingException {
		HtmlOutputLabel outputLabel = (HtmlOutputLabel) event.getSource();
		if (outputLabel.getFor() == null){
			return;
		}

		UIComponent target = outputLabel.findComponent(outputLabel.getFor());

		if (target != null && target.getAttributes().get("label") == null) {
			target.getAttributes().put("label", outputLabel.getValue());
		}
	}
}

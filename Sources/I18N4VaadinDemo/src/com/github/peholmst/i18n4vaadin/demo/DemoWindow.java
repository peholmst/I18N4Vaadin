package com.github.peholmst.i18n4vaadin.demo;

import com.github.peholmst.i18n4vaadin.I18N;
import com.github.peholmst.i18n4vaadin.support.I18NWindow;

public class DemoWindow extends I18NWindow {

	private static final long serialVersionUID = 1545155799774362389L;

	public DemoWindow(I18N i18n) {
		super("I18N4Vaadin Demo Application", i18n);
		initComponents();
	}
	
	protected void initComponents() {
		setSizeFull();
		setContent(new DemoLayout());
	}

}

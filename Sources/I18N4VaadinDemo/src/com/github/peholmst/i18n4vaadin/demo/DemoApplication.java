package com.github.peholmst.i18n4vaadin.demo;

import java.util.Locale;

import com.github.peholmst.i18n4vaadin.I18N;
import com.github.peholmst.i18n4vaadin.ResourceBundleI18N;
import com.vaadin.Application;
import com.vaadin.ui.Window;

public class DemoApplication extends Application {

	private static final long serialVersionUID = 6854263166726531152L;

	@Override
	public void init() {
		I18N i18n = new ResourceBundleI18N(
				"com/github/peholmst/i18n4vaadin/demo/i18n/messages",
				Locale.ENGLISH, new Locale("fi"), new Locale("sv"));
		i18n.setCurrentLocale(Locale.ENGLISH);
		Window mainWindow = new DemoWindow(i18n);
		setMainWindow(mainWindow);
	}

}

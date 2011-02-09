package com.github.peholmst.i18n4vaadin.demo;

import java.util.Date;
import java.util.Locale;

import com.github.peholmst.i18n4vaadin.I18N;
import com.github.peholmst.i18n4vaadin.I18NComponent;
import com.github.peholmst.i18n4vaadin.I18NListener;
import com.github.peholmst.i18n4vaadin.support.I18NComponentSupport;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class GreetingTab extends VerticalLayout implements I18NComponent,
		I18NListener {

	private static final long serialVersionUID = -8002840737683037206L;
	private final I18NComponentSupport support = new I18NComponentSupport(this);

	public GreetingTab() {
		initComponents();
	}

	@Override
	public void setI18N(I18N i18n) {
		support.setI18N(i18n);
	}

	@Override
	public I18N getI18N() {
		return support.getI18N();
	}

	@Override
	public void attach() {
		super.attach();
		getI18N().addListener(this);
		updateLabels();
	}

	@Override
	public void detach() {
		getI18N().removeListener(this);
		super.detach();
	}

	@Override
	public void localeChanged(I18N sender, Locale oldLocale, Locale newLocale) {
		updateLabels();
	}

	private void updateLabels() {
		nameField.setCaption(getI18N()
				.getMessage("greetingTab.nameField.label"));
		helloButton.setCaption(getI18N().getMessage(
				"greetingTab.helloButton.label"));
	}

	private TextField nameField;

	private Button helloButton;

	@SuppressWarnings("serial")
	private void initComponents() {
		setMargin(true);
		setSpacing(true);
		
		nameField = new TextField();
		addComponent(nameField);

		helloButton = new Button();
		helloButton.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				Window greetingWindow = new Window(getI18N().getMessage(
						"greetingWindow.title"));
				greetingWindow.setModal(true);
				greetingWindow.addComponent(new Label(getI18N().getMessage(
						"greetingWindow.message", nameField.getValue(),
						new Date())));
				getWindow().addWindow(greetingWindow);
			}
		});
		addComponent(helloButton);
	}

}

package com.github.peholmst.i18n4vaadin.demo;

import java.util.Locale;

import com.github.peholmst.i18n4vaadin.I18N;
import com.github.peholmst.i18n4vaadin.I18NComponent;
import com.github.peholmst.i18n4vaadin.I18NListener;
import com.github.peholmst.i18n4vaadin.support.I18NComponentSupport;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

public class DemoLayout extends VerticalLayout implements I18NComponent,
		I18NListener {

	private static final long serialVersionUID = 5467379085652632215L;

	private final I18NComponentSupport support = new I18NComponentSupport(this);

	public DemoLayout() {
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

	private Label selectLanguageLbl;

	private Tab greetingTab;

	@SuppressWarnings("serial")
	private void initComponents() {
		setMargin(true);
		setSizeFull();

		HorizontalLayout languageButtons = new HorizontalLayout();
		languageButtons.setSpacing(true);
		selectLanguageLbl = new Label();
		languageButtons.addComponent(selectLanguageLbl);
		languageButtons.setComponentAlignment(selectLanguageLbl,
				Alignment.MIDDLE_LEFT);

		Button enButton = new Button("English", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				getI18N().setCurrentLocale(Locale.ENGLISH);
			}
		});
		enButton.addStyleName(BaseTheme.BUTTON_LINK);
		languageButtons.addComponent(enButton);
		languageButtons.setComponentAlignment(enButton, Alignment.MIDDLE_LEFT);

		Button svButton = new Button("Svenska", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				getI18N().setCurrentLocale(new Locale("sv"));
			}
		});
		svButton.addStyleName(BaseTheme.BUTTON_LINK);
		languageButtons.addComponent(svButton);
		languageButtons.setComponentAlignment(svButton, Alignment.MIDDLE_LEFT);

		Button fiButton = new Button("Suomi", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				getI18N().setCurrentLocale(new Locale("fi"));
			}
		});
		fiButton.addStyleName(BaseTheme.BUTTON_LINK);
		languageButtons.addComponent(fiButton);
		languageButtons.setComponentAlignment(fiButton, Alignment.MIDDLE_LEFT);

		addComponent(languageButtons);

		TabSheet tabs = new TabSheet();
		tabs.setSizeFull();
		addComponent(tabs);
		setExpandRatio(tabs, 1.0f);

		greetingTab = tabs.addTab(new GreetingTab());
	}

	private void updateLabels() {
		selectLanguageLbl
				.setValue(getI18N().getMessage("selectLanguage.label"));
		greetingTab.setCaption(getI18N().getMessage("greetingTab.label"));
	}

}

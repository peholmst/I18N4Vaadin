/*
 * Copyright (c) 2013 Petter Holmström
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.peholmst.i18n4vaadin.simple.demo;

import com.github.peholmst.i18n4vaadin.I18N;
import com.github.peholmst.i18n4vaadin.LocaleChangedEvent;
import com.github.peholmst.i18n4vaadin.LocaleChangedListener;
import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.github.peholmst.i18n4vaadin.annotations.Messages;
import com.github.peholmst.i18n4vaadin.simple.I18NProvidingUIStrategy;
import com.github.peholmst.i18n4vaadin.simple.SimpleI18N;
import com.github.peholmst.i18n4vaadin.util.I18NHolder;
import com.github.peholmst.i18n4vaadin.util.I18NProvider;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.util.Arrays;
import java.util.Locale;

/**
 * Main entry point for the demo application.
 *
 * @author Petter Holmström
 */
public class DemoUI extends UI implements LocaleChangedListener, I18NProvider {

    private I18N i18n = new SimpleI18N(Arrays.asList(new Locale("en"), new Locale("sv"), new Locale("fi")));
    private DemoUIBundle bundle = new DemoUIBundle();
    private ComboBox languageChanger;

    static {
        I18NHolder.setStrategy(new I18NProvidingUIStrategy());
    }

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout content = new VerticalLayout();
        content.setMargin(true);
        content.setSpacing(true);
        content.setSizeFull();
        setContent(content);

        languageChanger = new ComboBox();
        languageChanger.setContainerDataSource(new BeanItemContainer<java.util.Locale>(java.util.Locale.class, i18n.getSupportedLocales()));
        languageChanger.setImmediate(true);
        languageChanger.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                if (languageChanger.getValue() != null) {
                    i18n.setLocale((java.util.Locale) languageChanger.getValue());
                }
            }
        });
        content.addComponent(languageChanger);

        Panel viewContent = new Panel();
        viewContent.setSizeFull();
        content.addComponent(viewContent);
        content.setExpandRatio(viewContent, 1);

        Navigator navigator = new Navigator(this, viewContent);
        navigator.addView("demo", DemoView.class);
        navigator.navigateTo("demo");
        setNavigator(navigator);

        updateStrings();
    }

    @Override
    public void attach() {
        super.attach();
        i18n.addLocaleChangedListener(this);
    }

    @Override
    public void detach() {
        i18n.removeLocaleChangedListener(this);
        super.detach();
    }

    @Messages({
        @Message(key = "application.title", value = "I18N4Vaadin CDI Demo Application"),
        @Message(key = "languageChanger.caption", value = "Change language")
    })
    private void updateStrings() {
        getPage().setTitle(bundle.application_title());
        languageChanger.setCaption(bundle.languageChanger_caption());
        languageChanger.setValue(i18n.getLocale());
    }

    @Override
    public void localeChanged(LocaleChangedEvent event) {
        updateStrings();
    }

    @Override
    public I18N getI18N() {
        return i18n;
    }
}

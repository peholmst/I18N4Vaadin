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
package com.github.peholmst.i18n4vaadin.cdi.demo;

import com.github.peholmst.i18n4vaadin.I18N;
import com.github.peholmst.i18n4vaadin.LocaleChangedEvent;
import com.github.peholmst.i18n4vaadin.LocaleChangedListener;
import com.github.peholmst.i18n4vaadin.annotations.Locale;
import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.github.peholmst.i18n4vaadin.annotations.Messages;
import com.github.peholmst.i18n4vaadin.cdi.annotations.I18nSupportedLocales;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.cdi.Root;
import com.vaadin.cdi.CDIUI;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import javax.inject.Inject;

/**
 * Main entry point for the demo application.
 *
 * @author Petter Holmström
 */
@CDIUI
@Root
public class DemoUI extends UI implements LocaleChangedListener {

    @I18nSupportedLocales({
        @Locale(language = "en"),
        @Locale(language = "sv"),
        @Locale(language = "fi")
    })
    @Inject
    private I18N i18n;
    @Inject
    private DemoUIBundle bundle;
    @Inject
    private CDIViewProvider viewProvider;
    private ComboBox languageChanger;

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
        navigator.addProvider(viewProvider);
        navigator.navigateTo("demo");
        setNavigator(navigator);

        updateStrings();
    }

    @Override
    public void attach() {
        super.attach();
        // We cannot use CDI events due to the way Vaadin CDI is currently implemented
        i18n.addLocaleChangedListener(this);
    }

    @Override
    public void detach() {
        // Remember to clean up afterwards, otherwise we will get memory leaks (the I18N instance is session scoped)
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
}

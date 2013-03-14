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
import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Example view
 *
 * @author Petter Holmström
 */
@CDIView(value = "demo")
public class DemoView extends VerticalLayout implements View, LocaleChangedListener {

    @Message(key = "name.caption", value = "What is your name?")
    private TextField name;
    @Message(key = "sayHello.caption", value = "Say Hello!")
    private Button sayHello;
    @Inject
    private DemoViewBundle bundle;
    @Inject
    private I18N i18n;

    @PostConstruct
    protected void init() {
        setSizeUndefined();
        setSpacing(true);
        setMargin(true);
        name = new TextField();
        addComponent(name);

        sayHello = new Button();
        sayHello.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                sayHello();
            }
        });
        addComponent(sayHello);

        updateStrings();
    }

    @Override
    public void attach() {
        super.attach();
        // We cannot use CDI events since the scope of DemoView is "dependent"
        i18n.addLocaleChangedListener(this);
    }

    @Override
    public void detach() {
        // Remember to clean up afterwards, otherwise we will get memory leaks (the I18N instance is session scoped)
        i18n.removeLocaleChangedListener(this);
        super.detach();
    }

    @Message(key = "greeting", value = "Hello {0}! How is it going?")
    private void sayHello() {
        Notification.show(bundle.greeting(name.getValue()));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    private void updateStrings() {
        name.setCaption(bundle.name_caption());
        sayHello.setCaption(bundle.sayHello_caption());
    }

    @Override
    public void localeChanged(LocaleChangedEvent event) {
        updateStrings();
    }
}

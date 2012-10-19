/*
 * Copyright (c) 2012 Petter Holmström
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
package com.github.peholmst.i18n4vaadin.demo;

import com.github.peholmst.i18n4vaadin.I18N;
import com.github.peholmst.i18n4vaadin.annotations.Locale;
import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.github.peholmst.i18n4vaadin.cdi.annotations.I18nBindCaption;
import com.github.peholmst.i18n4vaadin.cdi.annotations.I18nBindLocale;
import com.github.peholmst.i18n4vaadin.cdi.annotations.I18nSupportedLocales;
import com.vaadin.cdi.VaadinUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import javax.inject.Inject;

/**
 *
 * @author Petter Holmström
 */
@VaadinUI
public class DemoUI extends UI {

    @Inject
    @I18nSupportedLocales({
        @Locale(language = "en"),
        @Locale(language = "sv"),
        @Locale(language = "fi")})
    I18N i18n;
    
    @Message(key = "firstName.caption", value = "First Name") 
    @I18nBindCaption(Bundle.Keys.firstName_caption)
    @Inject
    TextField firstName;
    
    @Message(key = "lastName.caption", value = "Last Name")   
    @I18nBindCaption(Bundle.Keys.lastName_caption)
    @Inject
    TextField lastName;
    
    @Message(key = "birthDate.caption", value = "Birth Date")   
    @I18nBindCaption(Bundle.Keys.birthDate_caption)
    @I18nBindLocale
    @Inject
    DateField birthDate;
    
    @Message(key = "sex.caption", value = "Sex")
    @I18nBindCaption(Bundle.Keys.sex_caption)
    @Inject
    ComboBox sex;

    @Override
    protected void init(VaadinRequest request) {
        final FormLayout layout = new FormLayout();
        setContent(layout);
        
        layout.addComponent(firstName);
        layout.addComponent(lastName);
        layout.addComponent(birthDate);
        layout.addComponent(sex);
    }
}

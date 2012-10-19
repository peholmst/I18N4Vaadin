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

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;
import javax.enterprise.inject.Produces;

/**
 * CDI producer for creating Vaadin components, since they cannot be directly
 * injected due to the lack of
 * <code>beans.xml</code> in the Vaadin jars.
 *
 * @author Petter Holmström
 */
public class ComponentProducer {

    @Produces
    public TextField createTextField() {
        return new TextField();
    }

    @Produces
    public ComboBox createComboBox() {
        return new ComboBox();
    }

    @Produces
    public DateField createDateField() {
        return new DateField();
    }
}

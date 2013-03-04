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
package com.github.peholmst.i18n4vaadin.simple;

import com.github.peholmst.i18n4vaadin.I18N;
import com.github.peholmst.i18n4vaadin.util.I18NHolder;
import com.github.peholmst.i18n4vaadin.util.I18NProvider;
import com.vaadin.ui.UI;

/**
 * This is an implementation of {@link I18NHolder.Strategy} that retrieves the
 * I18N instance from the current UI {@link UI}, assuming the UI implements the
 * {@link I18NProvider} interface.
 *
 * @author Petter Holmström
 */
public class I18NProvidingUIStrategy implements I18NHolder.Strategy {

    @Override
    public <T extends I18N> T get(Class<T> clazz) {
        if (UI.getCurrent() instanceof I18NProvider) {
            return clazz.cast(((I18NProvider) UI.getCurrent()).getI18N());
        } else {
            throw new IllegalStateException("Current UI is null or does not implement I18NProvider");
        }
    }

    @Override
    public void set(I18N i18n) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("The I18N instance cannot be explicitly set to the current UI");
    }
}

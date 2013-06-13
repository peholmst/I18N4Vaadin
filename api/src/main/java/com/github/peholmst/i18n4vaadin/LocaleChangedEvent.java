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
package com.github.peholmst.i18n4vaadin;

import java.util.Locale;

/**
 * Event fired by {@link I18N}-implementations when the current locale is
 * changed.
 *
 * @see I18N#setLocale(java.util.Locale)
 *
 * @author Petter Holmström
 */
public class LocaleChangedEvent {

    private final I18N sender;
    private final Locale newLocale;

    public LocaleChangedEvent(I18N sender, Locale newLocale) {
        this.sender = sender;
        this.newLocale = newLocale;
    }

    /**
     * Returns the new locale.
     */
    public Locale getNewLocale() {
        return newLocale;
    }

    /**
     * Returns the I18N instance whose locale was changed.
     */
    public I18N getSender() {
        return sender;
    }
}

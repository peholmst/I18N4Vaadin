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

import java.util.Collection;
import java.util.Locale;

/**
 * The I18N instance maintains information about the supported locales and the
 * currently selected locale. <p> Clients can use CDI to access the current
 * I18N. The scope of the I18N instance is implementation-specific. <p> When the
 * locale changes, the I18N must post a {@link LocaleChangedEvent} using CDI.
 * The scope of the event is implementation-specific. <p> Implementations for
 * environments without CDI are also allowed. In this case, clients need to be
 * able to retrieve the I18N instance and listen for the events in another way.
 *
 * @author Petter Holmström
 */
public interface I18N extends java.io.Serializable {

    /**
     * Gets the current locale.
     *
     * @return the current locale, never {@code null}.
     */
    Locale getLocale();

    /**
     * Changes the current locale.
     *
     * @param locale the locale to set, must not be {@code null}.
     * @throws IllegalArgumentException if {@code locale} is {@code null} or not
     * supported.
     */
    void setLocale(Locale locale) throws IllegalArgumentException;

    /**
     * Gets a collection of supported locales. By default, this collection
     * contains only {@link Locale#getDefault() }.
     *
     * @return a collection containing at least one locale, never {@code null}.
     */
    Collection<Locale> getSupportedLocales();

    /**
     * Sets the supported locales. If the current locale is {@code null}, or is
     * not in the collection of supported locales, it is reset to the first
     * locale returned by the iterator of the {@link supportedLocales}
     * collection.
     *
     * @param supportedLocales a collection containing at least one locale, must
     * not be {@code null}.
     * @throws IllegalArgumentException if {@code supportedLocales} is
     * {@code null} or empty.
     */
    void setSupportedLocales(Collection<Locale> supportedLocales);
}

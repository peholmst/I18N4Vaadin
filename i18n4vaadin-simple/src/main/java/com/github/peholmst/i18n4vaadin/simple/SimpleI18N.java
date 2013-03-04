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
import com.github.peholmst.i18n4vaadin.LocaleChangedEvent;
import com.github.peholmst.i18n4vaadin.LocaleChangedListener;
import com.github.peholmst.i18n4vaadin.util.I18NHolder;
import com.github.peholmst.i18n4vaadin.util.I18NProvider;
import com.vaadin.ui.UI;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of {@link I18N} that is intended to be instantiated by your
 * {@link UI}. The instance should then be made available to the
 * {@link I18NHolder}. The easiest way is to have your UI implement
 * {@link I18NProvider} and then use {@link I18NProvidingUIStrategy}.
 *
 * @author Petter Holmström
 */
public class SimpleI18N implements I18N {

    private static final Logger log = Logger.getLogger(SimpleI18N.class.getName());
    private Set<Locale> supportedLocales;
    private Locale locale;
    private List<LocaleChangedListener> listeners = new LinkedList<LocaleChangedListener>();

    /**
     * Creates a new {@code SimpleI18N} instance with the default locale as the
     * current and only supported locale.
     *
     * @see Locale#getDefault()
     */
    public SimpleI18N() {
        supportedLocales = new HashSet<Locale>();
        locale = Locale.getDefault();
        supportedLocales.add(locale);
    }

    /**
     * Creates a new {@code SimpleI18N} instance with the specified supported
     * locales (must contain at least one locale). The current locale will be
     * set to the first locale returned by the iterator of the supported locales
     * collection.
     */
    public SimpleI18N(final Collection<Locale> supportedLocales) {
        if (supportedLocales == null) {
            throw new IllegalArgumentException("supportedLocales must not be null");
        } else if (supportedLocales.isEmpty()) {
            throw new IllegalArgumentException("At least one supported locale must be specified");
        }
        this.supportedLocales = new HashSet<Locale>(supportedLocales);
        locale = supportedLocales.iterator().next();
    }

    @Override
    public Locale getLocale() {
        if (locale == null) {
            throw new IllegalStateException("No locales have been configured yet");
        }
        return locale;
    }

    @Override
    public void setLocale(final Locale locale) throws IllegalArgumentException {
        if (!supportedLocales.contains(locale)) {
            throw new IllegalArgumentException(locale + " is not supported");
        }
        doSetLocale(locale);
    }

    private void doSetLocale(final Locale locale) {
        log.log(Level.FINE, "Setting locale to {0} and firing LocaleChangedEvent", locale);
        this.locale = locale;
        final LocaleChangedEvent event = new LocaleChangedEvent(this, locale);
        for (LocaleChangedListener listener : new LinkedList<LocaleChangedListener>(listeners)) {
            listener.localeChanged(event);
        }
    }

    @Override
    public Collection<Locale> getSupportedLocales() {
        return Collections.unmodifiableCollection(supportedLocales);
    }

    @Override
    public void setSupportedLocales(final Collection<Locale> supportedLocales) throws IllegalArgumentException {
        if (supportedLocales == null) {
            throw new IllegalArgumentException("supportedLocales must not be null");
        } else if (supportedLocales.isEmpty()) {
            throw new IllegalArgumentException("At least one supported locale must be specified");
        }
        if (this.supportedLocales == null
                || this.supportedLocales.size() != supportedLocales.size()
                || !this.supportedLocales.containsAll(supportedLocales)) {
            this.supportedLocales = new HashSet<Locale>(supportedLocales);
            log.log(Level.FINE, "Setting supported locales to {0}", this.supportedLocales);
            if (!supportedLocales.contains(locale)) {
                doSetLocale(supportedLocales.iterator().next());
            }
        }
    }

    @Override
    public void addLocaleChangedListener(LocaleChangedListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeLocaleChangedListener(LocaleChangedListener listener) {
        listeners.remove(listener);
    }

    /**
     * Returns the current I18N instance, or {@code null} if none is available.
     */
    public static I18N getCurrent() {
        return I18NHolder.get();
    }
}

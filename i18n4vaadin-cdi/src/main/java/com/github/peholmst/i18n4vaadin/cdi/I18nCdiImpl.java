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
package com.github.peholmst.i18n4vaadin.cdi;

import com.github.peholmst.i18n4vaadin.I18N;
import com.github.peholmst.i18n4vaadin.LocaleChangedEvent;
import com.github.peholmst.i18n4vaadin.LocaleChangedListener;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * A session scoped implementation of {@link I18N} designed to be injected into
 * clients using CDI like this:
 * <code>&#064;Inject I18N i18n</code>. Clients should never need to invoke this
 * class directly.
 *
 * @author Petter Holmström
 */
@SessionScoped
public class I18nCdiImpl implements I18N {

    private static final Logger log = Logger.getLogger(I18nCdiImpl.class.getName());
    private Set<Locale> supportedLocales;
    private Locale locale;
    @Inject Event<LocaleChangedEvent> localeChangedEvent;
    private List<LocaleChangedListener> listeners = new LinkedList<LocaleChangedListener>();

    public I18nCdiImpl() {
        supportedLocales = new HashSet<Locale>();
        locale = Locale.getDefault();
        supportedLocales.add(locale);
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
        log.log(Utils.logLevel, "Setting locale to {0} and firing LocaleChangedEvent", locale);
        this.locale = locale;
        final LocaleChangedEvent event = new LocaleChangedEvent(this, locale);
        localeChangedEvent.fire(event);
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
            log.log(Utils.logLevel, "Setting supported locales to {0}", this.supportedLocales);
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
}

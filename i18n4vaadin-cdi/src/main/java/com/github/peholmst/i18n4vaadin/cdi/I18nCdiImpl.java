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
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
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

    private Set<Locale> supportedLocales;
    private Locale locale;
    @Inject
    @SessionScoped
    Event<LocaleChangedEvent> localeChangedEvent;

    public I18nCdiImpl() {
        supportedLocales = new HashSet<Locale>();
        locale = Locale.getDefault();
        supportedLocales.add(locale);
    }

    @Override
    public Locale getLocale() {
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
        this.locale = locale;
        localeChangedEvent.fire(new LocaleChangedEvent(this, locale));
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
        this.supportedLocales = new HashSet<Locale>(supportedLocales);
        if (!supportedLocales.contains(locale)) {
            doSetLocale(supportedLocales.iterator().next());
        }
    }
}

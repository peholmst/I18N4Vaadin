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
import com.github.peholmst.i18n4vaadin.cdi.annotations.I18nSupportedLocales;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedType;

/**
 * Configurer that looks for {@link I18N} fields annotated with
 * {@link SupportedLocales} and configures the {@link I18N} instance to support
 * the specified locales.
 *
 * @see I18N#setSupportedLocales(java.util.Collection)
 *
 * @author Petter Holmström
 */
@ApplicationScoped
class SupportedLocalesConfigurer implements I18nConfigurer {

    private static final Logger log = Logger.getLogger(SupportedLocalesConfigurer.class.getName());
    private static final Level logLevel = Level.INFO;

    @Override
    public <T> void configure(AnnotatedType<T> annotatedType, T instance) {
        for (AnnotatedField<? super T> field : annotatedType.getFields()) {
            if (field.isAnnotationPresent(I18nSupportedLocales.class) && I18N.class.isAssignableFrom(field.getJavaMember().getType())) {
                log.log(logLevel, "Configuring supported locales on field {0}", field.getJavaMember().toString());
                final I18N i18n = Utils.getFieldValue(I18N.class, field.getJavaMember(), instance);
                final List<Locale> supportedLocales = new LinkedList<Locale>();
                for (com.github.peholmst.i18n4vaadin.annotations.Locale localeAnnotation : field.getAnnotation(I18nSupportedLocales.class).value()) {
                    final Locale locale = new Locale(localeAnnotation.language(), localeAnnotation.country(), localeAnnotation.variant());
                    log.log(logLevel, "Found supported locale {0}", locale);
                    supportedLocales.add(locale);
                }
                i18n.setSupportedLocales(supportedLocales);
            }
        }
    }
}

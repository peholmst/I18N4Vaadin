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
package com.github.peholmst.i18n4vaadin.cdi.annotations;

import com.github.peholmst.i18n4vaadin.annotations.Locale;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used on {@link com.github.peholmst.i18n4vaadin.I18N}
 * injection points to configure the supported locales of the injected
 * {@code I18N} instance.
 *
 * @see
 * com.github.peholmst.i18n4vaadin.I18N#setSupportedLocales(java.util.Collection)
 *
 * @author Petter Holmström
 */
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface I18nSupportedLocales {

    /**
     * The supported locales, must contain at least one element.
     */
    Locale[] value();
}

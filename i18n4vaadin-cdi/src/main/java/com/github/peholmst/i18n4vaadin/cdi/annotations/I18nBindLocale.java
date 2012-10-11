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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to bind the {@code locale} JavaBean property of the
 * annotated element to the current locale. If the annotated element is a class,
 * it's {@code locale} property will be automatically updated when the current
 * locale is changed, provided that the instance of the class is managed by
 * CDI.<p> If the annotated element is a field, the {@code locale} property of
 * the field value will be automatically updated when the current locale is
 * changed, provided that the field value is injected using CDI.
 *
 * @see com.github.peholmst.i18n4vaadin.I18N#getLocale()
 *
 * @author Petter Holmström
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface I18nBindLocale {
}

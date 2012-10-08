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
package com.github.peholmst.i18n4vaadin.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO Document me!
 * 
 * @author Petter Holmström
 */
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD,
    ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PACKAGE,
    ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Message {

    /**
     *
     * @return
     */
    String key();
    
    /**
     *
     * @return
     */
    String value();

    /**
     *
     * @return
     */
    Locale locale() default @Locale(language = "");
}

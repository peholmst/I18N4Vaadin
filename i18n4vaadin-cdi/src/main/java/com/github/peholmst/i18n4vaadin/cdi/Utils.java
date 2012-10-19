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

import java.lang.reflect.Field;
import java.util.logging.Level;

/**
 * Collection of utility methods for internal use.
 * 
 * @author Petter Holmström
 */
final class Utils {
    
    private Utils() {}
    
    static final Level logLevel = Level.INFO;    
    
    static <T> T getFieldValue(Class<T> type, Field field, Object instance) {
        field.setAccessible(true);
        try {
            return type.cast(field.get(instance));
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Could not access field " + field.getName(), e);
        }
    }    
}

/*
 * Copyright (c) 2013 Petter Holmström
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
package com.github.peholmst.i18n4vaadin.util;

import com.github.peholmst.i18n4vaadin.I18N;

/**
 * Interface to be implemented by classes that provide an {@link I18N} instance.
 *
 * @author Petter Holmström
 */
public interface I18NProvider extends java.io.Serializable {

    /**
     * Returns the I18N instance, or {@code null} if none is available.
     */
    I18N getI18N();
}

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
package com.github.peholmst.i18n4vaadin.test;

import com.github.peholmst.i18n4vaadin.annotations.Locale;
import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.github.peholmst.i18n4vaadin.annotations.Messages;

/**
 * @author Petter Holmström
 */
@Messages({
    @Message(key = "root_class", value = "Root class default locale"),
    @Message(key = "root_class", value = "Root class English", locale = @Locale(language = "en")),
    @Message(key = "root_class", value = "Root class Finnish", locale = @Locale(language = "fi"))
})
public class RootPackageClass {

    @Message(key = "root_field", value = "Root field default locale")
    String aField;

    @Message(key = "root_constructor", value = "Root constructor default locale")
    public RootPackageClass() {
    }

    @Message(key = "root_field", value = "Root field default locale")
    void aMethod() {
    }

    void anotherMethod(@Message(key = "root_parameter", value = "Root parameter default locale") String aParameter) {
        @Message(key = "local_variable", value = "Root local variable default locale")
        String aLocalVariable;
    }
}

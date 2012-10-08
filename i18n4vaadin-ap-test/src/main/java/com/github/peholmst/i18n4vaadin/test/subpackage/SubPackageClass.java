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
package com.github.peholmst.i18n4vaadin.test.subpackage;

import com.github.peholmst.i18n4vaadin.annotations.Locale;
import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.github.peholmst.i18n4vaadin.annotations.Messages;

/**
 * @author Petter Holmström
 */
@Messages({
    @Message(key = "sub_class", value = "Sub class default locale"),
    @Message(key = "sub_class", value = "Sub class English", locale = @Locale(language = "en")),
    @Message(key = "sub_class", value = "Sub class Finnish", locale = @Locale(language = "fi"))
})
public class SubPackageClass {

    @Message(key = "sub_field", value = "Sub field default locale")
    String aField;

    @Message(key = "sub_constructor", value = "Sub constructor default locale")
    public SubPackageClass() {
    }

    @Message(key = "sub_field", value = "Sub field default locale")
    void aMethod() {
    }

    void anotherMethod(@Message(key = "sub_parameter", value = "Sub parameter default locale") String aParameter) {
        @Message(key = "sub_variable", value = "Sub local variable default locale")
        String aLocalVariable;
    }
}

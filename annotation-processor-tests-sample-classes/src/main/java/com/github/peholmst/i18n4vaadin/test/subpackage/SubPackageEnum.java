/*
 * Copyright (c) 2012, 2013 Petter Holmström
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
    @Message(key = "sub_enum", value = "Sub enum default locale"),
    @Message(key = "sub_enum", value = "Sub enum English", locale = @Locale(language = "en")),
    @Message(key = "sub_enum", value = "Sub enum Finnish", locale = @Locale(language = "fi"))
})
public enum SubPackageEnum {

    @Message(key = "sub_enum_constant", value = "Sub enum constant default locale")
    MY_ENUM_CONSTANT,

    @Message(key = "sub_other_enum_constant", value = "Sub other enum constant default locale")
    MY_OTHER_ENUM_CONSTANT
}

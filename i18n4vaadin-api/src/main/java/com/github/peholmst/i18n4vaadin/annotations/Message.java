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
 * This annotation defines a single message in a message bundle. An annotation
 * processor will automatically generate a bundle file (named
 * {@code messages.properties}) and a bundle class (named {@code Bundle}) for
 * each package that this annotation is used in. The bundle class contains
 * static getter methods for retrieving the messages. <p> For example, let's say
 * you have a
 * <code>&#064;Message</code> annotation on a class in the {@code foo.bar}
 * package, like this:
 * <code>&#064;Message(key="mykey", value="my value")</code>. The annotation
 * processor will create a {@code message.properties} file in the
 * {@code foo.bar} package containing the following: {@code mykey=my value} (if
 * there are more annotations in the same package, these strings will also be
 * included). Clients can then use {@code Bundle.mykey()} to actually retrieve
 * the message string. <p> If you also specify a locale, a separate bundle file
 * containing the messages for that particular locale will be created.
 *
 * @see Messages
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
     * The key can be used to identify the message in the bundle.
     */
    String key();

    /**
     * The value of the message to be stored in the bundle.
     */
    String value();

    /**
     * The locale of the bundle. By default, the default bundle without any
     * locale information will be used (i.e. {@code messages.properties}).
     */
    Locale locale() default @Locale(language = "");
}

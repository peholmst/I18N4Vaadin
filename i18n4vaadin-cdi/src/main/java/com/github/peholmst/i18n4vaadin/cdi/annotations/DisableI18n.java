package com.github.peholmst.i18n4vaadin.cdi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Placing this annotation on a class will disable all I18n-annotations present
 * on members of the class in question.
 *
 * @author Petter Holmström
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DisableI18n {
}

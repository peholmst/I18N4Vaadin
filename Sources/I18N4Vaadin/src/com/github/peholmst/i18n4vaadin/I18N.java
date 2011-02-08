/*
 * Copyright (c) 2011 Petter Holmström
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
package com.github.peholmst.i18n4vaadin;

import java.util.Collection;
import java.util.Locale;

/**
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public interface I18N extends java.io.Serializable {

	/**
	 * Gets the current locale, if any.
	 * 
	 * @return the current locale, or <code>null</code> if none has been set.
	 */
	Locale getCurrentLocale();

	/**
	 * Sets the current locale, informing any {@link I18NListener}s of the
	 * change.
	 * 
	 * @see #getSupportedLocales()
	 * @param locale
	 *            the locale to set (may be <code>null</code>).
	 * @throws IllegalArgumentException
	 *             if the locale is not among the supported locales.
	 */
	void setCurrentLocale(Locale locale) throws IllegalArgumentException;

	/**
	 * Gets a collection of all supported locales. The
	 * {@link #setCurrentLocale(Locale) current locale} can only be set to one
	 * of these locales.
	 * 
	 * @return a collection of locales (never <code>null</code>).
	 */
	Collection<Locale> getSupportedLocales();

	/**
	 * Tries to resolve the message using the locale returned by
	 * {@link #getCurrentLocale()Locale()}. If the message cannot be found, a standard
	 * implementation specific string is returned instead (usually an empty string).
	 * 
	 * @param code
	 *            the code to look up (must not be <code>null</code>).
	 * @param args
	 *            array of arguments that will be filled in for params within
	 *            the message (params look like "{0}", "{1,date}", "{2,time}"),
	 *            or <code>null</code> if there are none.
	 * @return the resolved message (never <code>null</code>).
	 */	
	String getMessage(String code, Object... args) throws IllegalStateException;

	/**
	 * Registers a listener to be notified when the current locale changes. A listener can
	 * be registered several times and will be notified once for each
	 * registration. If the listener is <code>null</code>, nothing happens.
	 * 
	 * @param listener
	 *            the listener to add.
	 */
	void addListener(I18NListener listener);

	/**
	 * Unregisters a listener previously registered using
	 * {@link #addListener(I18NListener)}. If the listener was registered
	 * multiple times, it will be notified one time less after this method
	 * invocation. If the listener is <code>null</code> or was never added,
	 * nothing happens.
	 * 
	 * @param listener
	 *            the listener to remove.
	 */
	void removeListener(I18NListener listener);

}

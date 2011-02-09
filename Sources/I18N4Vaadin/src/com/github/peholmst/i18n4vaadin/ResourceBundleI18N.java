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

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * This is an {@link I18N}-implementation that reads the message strings from a
 * Java {@link ResourceBundle}.
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public class ResourceBundleI18N extends AbstractI18N {

	private static final long serialVersionUID = 783545870292525859L;

	private ResourceBundle currentBundle;

	private String baseName;

	private ClassLoader classLoader;

	private Collection<Locale> supportedLocales;

	/**
	 * Creates a new <code>ResourceBundleI18N</code>.
	 * 
	 * @param baseName
	 *            the base name to pass to the resource bundle (must not be
	 *            <code>null</code>).
	 * @param supportedLocales
	 *            an array of supported locales (must contain at least one
	 *            element).
	 */
	public ResourceBundleI18N(String baseName, Locale... supportedLocales) {
		this(baseName, null, supportedLocales);
	}

	/**
	 * Creates a new <code>ResourceBundleI18N</code>.
	 * 
	 * @param baseName
	 *            the base name to pass to the resource bundle (must not be
	 *            <code>null</code>).
	 * @param classLoader
	 *            the class loader to use when loading the resource bundle (or
	 *            be <code>null</code> to use the current class loader)
	 * @param supportedLocales
	 *            an array of supported locales (must contain at least one
	 *            element).
	 */
	public ResourceBundleI18N(String baseName, ClassLoader classLoader,
			Locale... supportedLocales) {
		if (baseName == null) {
			throw new IllegalArgumentException("null baseName");
		}
		if (supportedLocales == null || supportedLocales.length == 0) {
			throw new IllegalArgumentException("null or empty supportedLocales");
		}
		HashSet<Locale> localeSet = new HashSet<Locale>();
		for (Locale l : supportedLocales) {
			localeSet.add(l);
		}
		this.supportedLocales = Collections.unmodifiableSet(localeSet);
		this.baseName = baseName;
		this.classLoader = classLoader;
	}

	@Override
	public void setCurrentLocale(Locale locale) throws IllegalArgumentException {
		currentBundle = null; // Will cause it to be recreated when accessed the
								// next time
		super.setCurrentLocale(locale);
	}

	/**
	 * Gets the resource bundle for the current locale.
	 * 
	 * @return the resource bundle (never <code>null</code>).
	 * @throws MissingResourceException
	 *             if the resource bundle could not be located.
	 * @throws IllegalStateException
	 *             if no current locale has been set.
	 */
	protected ResourceBundle getCurrentBundle()
			throws MissingResourceException, IllegalStateException {
		if (getCurrentLocale() == null) {
			throw new IllegalStateException("null currentLocale");
		}
		if (currentBundle == null) {
			if (classLoader == null) {
				currentBundle = ResourceBundle.getBundle(baseName,
						getCurrentLocale());
			} else {
				currentBundle = ResourceBundle.getBundle(baseName,
						getCurrentLocale(), classLoader);
			}
		}
		return currentBundle;
	}

	@Override
	public Collection<Locale> getSupportedLocales() {
		return supportedLocales;
	}

	@Override
	public String getMessage(String code, Object... args) {
		try {
			MessageFormat mf = new MessageFormat(getCurrentBundle().getString(
					code), getCurrentLocale());
			return mf.format(args, new StringBuffer(), null).toString();
		} catch (MissingResourceException e) {
			return "";
		}
	}

}

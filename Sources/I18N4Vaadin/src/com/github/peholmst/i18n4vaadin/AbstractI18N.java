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

import java.util.LinkedList;
import java.util.Locale;

/**
 * This is an abstract base class for {@link I18N}-implementations that provide
 * support for adding/removing listeners and setting/getting the current locale.
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public abstract class AbstractI18N implements I18N {

	private static final long serialVersionUID = -2502505781343501310L;

	private Locale currentLocale = null;

	private LinkedList<I18NListener> listenerList = new LinkedList<I18NListener>();

	@Override
	public void addListener(I18NListener listener) {
		if (listener != null) {
			listenerList.add(listener);
		}
	}

	@Override
	public void removeListener(I18NListener listener) {
		if (listener != null) {
			listenerList.remove(listener);
		}
	}

	@Override
	public Locale getCurrentLocale() {
		return currentLocale;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setCurrentLocale(Locale locale) throws IllegalArgumentException {
		Locale oldLocale = currentLocale;
		if (locale != null && !getSupportedLocales().contains(locale)) {
			throw new IllegalArgumentException("unsupported locale");
		}
		currentLocale = locale;
		LinkedList<I18NListener> clonedList = (LinkedList<I18NListener>) listenerList
				.clone();
		for (I18NListener l : clonedList) {
			l.localeChanged(this, oldLocale, currentLocale);
		}
	}
}

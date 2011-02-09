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

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link AbstractI18N}.
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public class AbstractI18NTest {

	@SuppressWarnings("serial")
	private static class I18NImpl extends AbstractI18N {

		Collection<Locale> locales = Arrays.asList(Locale.US, Locale.UK);

		@Override
		public Collection<Locale> getSupportedLocales() {
			return locales;
		}

		@Override
		public String getMessage(String code, Object... args) {
			throw new UnsupportedOperationException();
		}

	}

	private I18NImpl i18n;
	private I18NListener listener;

	@Before
	public void setUp() {
		listener = createMock(I18NListener.class);
		i18n = new I18NImpl();
	}

	@Test
	public void testInitialState() {
		assertNull(i18n.getCurrentLocale());
	}

	@Test
	public void testSetCurrentLocale_Supported() {
		// Instruct mocks
		listener.localeChanged(i18n, null, Locale.UK);
		replay(listener);

		// Add listener
		i18n.addListener(listener);

		// Run test
		i18n.setCurrentLocale(Locale.UK);

		// Verify results
		assertEquals(Locale.UK, i18n.getCurrentLocale());
		verify(listener);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetCurrentLocale_NotSupported() {
		i18n.setCurrentLocale(Locale.CHINA);
	}

	@Test
	public void testSetCurrentLocale_Null() {
		i18n.setCurrentLocale(Locale.UK);

		// Instruct mocks
		listener.localeChanged(i18n, Locale.UK, null);
		replay(listener);

		// Add listener
		i18n.addListener(listener);

		// Run test
		i18n.setCurrentLocale(null);

		// Verify results
		assertNull(i18n.getCurrentLocale());
		verify(listener);
	}

	@Test
	public void testRemoveListener() {
		// Instruct mocks
		listener.localeChanged(i18n, null, Locale.UK);
		replay(listener);

		// Add listener
		i18n.addListener(listener);

		// Run test
		i18n.setCurrentLocale(Locale.UK);
		i18n.removeListener(listener);
		i18n.setCurrentLocale(Locale.US);

		// Verify results
		assertEquals(Locale.US, i18n.getCurrentLocale());
		verify(listener);
	}
}

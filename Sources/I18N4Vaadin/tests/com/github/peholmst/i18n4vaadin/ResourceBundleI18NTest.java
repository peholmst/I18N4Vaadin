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

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link ResourceBundleI18N}.
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public class ResourceBundleI18NTest {

	private I18N i18n;

	@Before
	public void setUp() {
		i18n = new ResourceBundleI18N(
				"com/github/peholmst/i18n4vaadin/messages", Locale.US);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNullBaseName() {
		i18n = new ResourceBundleI18N(null, Locale.US);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNullSupportedLocales() {
		i18n = new ResourceBundleI18N("basename", (Locale[]) null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorEmptySupportedLocales() {
		i18n = new ResourceBundleI18N("basename", new Locale[] {});
	}

	@Test(expected = IllegalStateException.class)
	public void testGetMessage_NoCurrentLocale() {
		i18n.getMessage("hello");
	}

	@Test
	public void testGetMessage_WithArgs() {
		i18n.setCurrentLocale(Locale.US);
		assertEquals("Hello Joe, how are you?",
				i18n.getMessage("greeting", "Joe"));
	}

	@Test
	public void testGetMessage_WithoutArgs() {
		i18n.setCurrentLocale(Locale.US);
		assertEquals("world", i18n.getMessage("hello"));
	}

	@Test
	public void testGetMessage_InvalidCode() {
		i18n.setCurrentLocale(Locale.US);
		assertEquals("", i18n.getMessage("nonexistent"));
	}

}

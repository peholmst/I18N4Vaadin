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
package com.github.peholmst.i18n4vaadin.support;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.github.peholmst.i18n4vaadin.I18N;
import com.github.peholmst.i18n4vaadin.I18NComponent;
import com.vaadin.ui.Component;

/**
 * Test case for {@link I18NComponentSupport}.
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public class I18NComponentSupportTest {

	private Component parent;
	
	private I18NComponent grandparent;
	
	private I18N i18n;
	
	@Before
	public void setUp() {
		parent = createMock(Component.class);		
		i18n = createMock(I18N.class);
		grandparent = createMock(I18NComponent.class);
	}
	
	@Test
	public void testInitialState() {
		expect(parent.getParent()).andReturn(null);
		replay(parent);
		I18NComponentSupport support = new I18NComponentSupport(parent);
		assertNull(support.getI18N());
		verify(parent);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithNullOwner() {
		new I18NComponentSupport(null);
	}
	
	@Test
	public void testGetI18NFromParent() {
		expect(parent.getParent()).andReturn(grandparent);
		replay(parent);
		expect(grandparent.getI18N()).andReturn(i18n);
		replay(grandparent);

		I18NComponentSupport support = new I18NComponentSupport(parent);
		assertSame(i18n, support.getI18N());
		
		verify(parent);
		verify(grandparent);
	}
	
	@Test
	public void testGetI18NFromGrandParent() {
		expect(parent.getParent()).andReturn(parent);
		expect(parent.getParent()).andReturn(grandparent);
		replay(parent);
		expect(grandparent.getI18N()).andReturn(i18n);
		replay(grandparent);

		I18NComponentSupport support = new I18NComponentSupport(parent);
		assertSame(i18n, support.getI18N());
		
		verify(parent);
		verify(grandparent);
	}	
	
	@Test
	public void testGetOwnI18N() {
		replay(i18n);
		replay(parent);
		
		I18NComponentSupport support = new I18NComponentSupport(parent);
		support.setI18N(i18n);
		assertSame(i18n, support.getI18N());
		
		verify(i18n);
		verify(parent);
	}
}

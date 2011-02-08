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

import com.github.peholmst.i18n4vaadin.I18N;
import com.github.peholmst.i18n4vaadin.I18NComponent;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Window;

/**
 * This is a convenience class that extends the standard Vaadin Window class and
 * adds I18N-support to it by implementing {@link I18NComponent}. Thus, all
 * components that are added to this window (either directly or through nesting)
 * and use {@link I18NComponentSupport} will automatically have access to the
 * window's {@link I18N}-instance through the
 * {@link I18NComponentSupport#getI18N()} method.
 * 
 * @see I18NComponent
 * @see I18NComponentSupport
 * @see I18N
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public class I18NWindow extends Window implements I18NComponent {

	private static final long serialVersionUID = -743406977553539377L;

	private final I18NComponentSupport i18nSupport = new I18NComponentSupport(
			this);

	/**
	 * Creates a new unnamed window with a default layout.
	 * 
	 * @param i18n
	 *            the I18N instance to use (may be <code>null</code>).
	 */
	public I18NWindow(I18N i18n) {
		super();
		setI18N(i18n);
	}

	/**
	 * Creates a new unnamed window with the given content and title.
	 * 
	 * @param caption
	 *            the title of the window.
	 * @param content
	 *            the contents of the window
	 * @param i18n
	 *            the I18N instance to use (may be <code>null</code>).
	 */
	public I18NWindow(String caption, ComponentContainer content, I18N i18n) {
		super(caption, content);
		setI18N(i18n);
	}

	/**
	 * Creates a new unnamed window with a default layout and given title.
	 * 
	 * @param caption
	 *            the title of the window.
	 * @param i18n
	 *            the I18N instance to use (may be <code>null</code>).
	 */
	public I18NWindow(String caption, I18N i18n) {
		super(caption);
		setI18N(i18n);
	}

	@Override
	public void setI18N(I18N i18n) {
		i18nSupport.setI18N(i18n);
	}

	@Override
	public I18N getI18N() {
		return i18nSupport.getI18N();
	}

}

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

import com.vaadin.ui.Component;

/**
 * An extended version of the Vaadin {@link Component}-interface that adds a
 * getter and setter method for an {@link I18N}-instance. Vaadin-components that
 * require internationalization should implement this interface.
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public interface I18NComponent extends Component {

	/**
	 * Sets the <code>I18N</code> instance to use.
	 * 
	 * @param i18n
	 *            the <code>I18N</code> instance (may be <code>null</code>).
	 */
	void setI18N(I18N i18n);

	/**
	 * Gets the <code>I18N</code> instance to use.
	 * 
	 * @return the <code>I18N</code> instance, or <code>null</code> if none is
	 *         available.
	 */
	I18N getI18N();
}

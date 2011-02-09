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
import com.vaadin.ui.Component;

/**
 * This is a convenience class for Vaadin components that wish to implement the
 * {@link I18NComponent} interface, e.g. like this:
 * 
 * <pre>
 * <code>
 * public class MyComponent extends VerticalLayout implements I18NComponent {
 * 
 *   private I18NComponentSupport support = new I18NComponentSupport(this);
 *   
 *   public I18N getI18N() {
 *     return support.getI18N();
 *   }
 *   
 *   public void setI18N(I18N i18n) {
 *     support.setI18N(i18n);
 *   }
 *   
 *   ...
 * }
 * </code>
 * </pre>
 * <p>
 * <code>I18NComponentSupport</code> is designed to be used in the same manner
 * as {@link java.beans.PropertyChangeSupport}.
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public class I18NComponentSupport implements java.io.Serializable {

	private static final long serialVersionUID = -227676373092748282L;

	private final Component owner;

	private I18N i18n;

	/**
	 * Creates a new <code>I18NComponentSupport</code>.
	 * 
	 * @param owner
	 *            the owning component (must not be <code>null</code>).
	 */
	public I18NComponentSupport(Component owner) {
		if (owner == null) {
			throw new IllegalArgumentException("null owner");
		}
		this.owner = owner;
	}

	/**
	 * Gets the <code>I18N</code>-instance to use. If an instance has been set
	 * using {@link #setI18N(I18N)}, it will be returned. Otherwise, this method
	 * will traverse the owning component's parent chain looking for a component
	 * that implements {@link I18NComponent}. If one is found, that component's
	 * <code>I18N</code>-instance will be returned.
	 * 
	 * @return the <code>I18N</code> instance, or <code>null</code> if none
	 *         could be found.
	 */
	public I18N getI18N() {
		if (i18n != null) {
			return i18n;
		} else {
			Component c = owner.getParent();
			while (c != null) {
				if (c instanceof I18NComponent) {
					return ((I18NComponent) c).getI18N();
				}
				c = c.getParent();
			}
		}
		return null;
	}

	/**
	 * Sets the <code>I18N</code>-instance to use.
	 * 
	 * @param i18n
	 *            the <code>I18N</code> instance, may be <code>null</code>.
	 */
	public void setI18N(I18N i18n) {
		this.i18n = i18n;
	}
}

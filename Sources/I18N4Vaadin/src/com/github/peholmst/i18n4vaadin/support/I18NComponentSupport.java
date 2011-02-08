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
 * TODO Document me!
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public class I18NComponentSupport implements java.io.Serializable {

	private static final long serialVersionUID = -227676373092748282L;

	private final Component owner;
	
	private I18N i18n;
	
	/**
	 * 
	 * @param owner
	 */
	public I18NComponentSupport(Component owner) {
		if (owner == null) {
			throw new IllegalArgumentException("null owner");
		}
		this.owner = owner;
	}
	
	/**
	 * 
	 * @return
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
	 * 
	 * @param i18n
	 */
	public void setI18N(I18N i18n) {
		this.i18n = i18n;
	}
}

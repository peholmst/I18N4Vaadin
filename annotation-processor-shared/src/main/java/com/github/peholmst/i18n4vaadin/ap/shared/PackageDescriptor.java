/*
 * Copyright (c) 2012, 2013 Petter Holmström
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
package com.github.peholmst.i18n4vaadin.ap.shared;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

/**
 *
 * @author Petter Holmström
 */
public class PackageDescriptor extends MessageOwner {

    private final Map<String, TypeDescriptor> typeMap = new HashMap<String,TypeDescriptor>();
    private final PackageElement element;

    PackageDescriptor(PackageElement element) {
        this.element = element;
    }

    public Collection<TypeDescriptor> getTypes() {
        return Collections.unmodifiableCollection(typeMap.values());
    }

    TypeDescriptor getType(TypeElement element) {
        TypeDescriptor descr = typeMap.get(element.getQualifiedName().toString());
        if (descr == null) {
            descr = new TypeDescriptor(element);
            descr.setPackage(this);
            typeMap.put(element.getQualifiedName().toString(), descr);
        }
        return descr;
    }

    public PackageElement getElement() {
        return element;
    }

    public String getName() {
        return element.getQualifiedName().toString();
    }

    public MessageCollection getOwnMessagesAndTypeMessages() {
        MessageCollection.Builder msgsBuilder = new MessageCollection.Builder().withMessages(getMessages());
        for (TypeDescriptor type : typeMap.values()) {
            msgsBuilder.withMessages(type.getMessages());
        }
        return msgsBuilder.build();
    }
}

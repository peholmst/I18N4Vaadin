/*
 * Copyright (c) 2012 Petter Holmström
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
package com.github.peholmst.i18n4vaadin.ap;

import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.github.peholmst.i18n4vaadin.annotations.Messages;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

/**
 * Class containing information about the messages declared in one specific Java
 * package. <p><b>This class is internal and should never be used by
 * clients.</b>
 *
 * @author Petter Holmström
 */
final class PackageInfo {

//    private final Map<Locale, List<Message>> messageMap = new HashMap<Locale, List<Message>>();
//    private final Set<String> messageKeys = new HashSet<String>();
    private final PackageMap owner;
    private final PackageElement pkg;
    private final Map<Element, ElementInfo> elements = new HashMap<Element, ElementInfo>();

    private static class ElementInfo {

        final Element element;
        final Set<String> messageKeys = new HashSet<String>();
        final Map<Locale, List<Message>> messageMap = new HashMap<Locale, List<Message>>();

        ElementInfo(Element element) {
            this.element = element;
        }

        void addMessage(Message message, Locale locale) {
            List<Message> messageList = messageMap.get(locale);
            if (messageList == null) {
                messageList = new LinkedList<Message>();
                messageMap.put(locale, messageList);
            }
            messageList.add(message);
            messageKeys.add(message.key());
        }
    }

    PackageInfo(PackageMap owner, PackageElement pkg) {
        this.owner = owner;
        this.pkg = pkg;
    }

    String getName() {
        return pkg.getQualifiedName().toString();
    }

    PackageMap getOwner() {
        return owner;
    }

    PackageElement getElement() {
        return pkg;
    }

    Set<Locale> getLocales() {
        Set<Locale> locales = new HashSet<Locale>();
        for (ElementInfo elementInfo : elements.values()) {
            locales.addAll(elementInfo.messageMap.keySet());
        }
        return locales;
    }

    Set<Locale> getLocales(Element element) {
        return Collections.unmodifiableSet(getElementInfo(element).messageMap.keySet());
    }

    Set<Element> getAnnotatedElements() {
        return Collections.unmodifiableSet(elements.keySet());
    }
    
    Set<TypeElement> getAnnotatedTypeElements() {
        Set<TypeElement> typeElements = new HashSet<TypeElement>();
        for (Element element : elements.keySet()) {
            typeElements.add(Utils.getType(element));
        }
        return typeElements;
    }

    void addMessage(final Message message, final Element declaringElement) {
        final Locale locale = Utils.convertLocaleAnnotation(message.locale());
        getElementInfo(declaringElement).addMessage(message, locale);
    }

    private ElementInfo getElementInfo(Element element) {
        ElementInfo info = elements.get(element);
        if (info == null) {
            info = new ElementInfo(element);
            elements.put(element, info);
        }
        return info;
    }

    void addMessages(final Messages messages, final Element declaringElement) {
        for (final Message msg : messages.value()) {
            addMessage(msg, declaringElement);
        }
    }

    List<Message> getMessages(final Locale locale) {
        final List<Message> messageList = new LinkedList<Message>();
        for (Element element : elements.keySet()) {
            messageList.addAll(getMessages(locale, element));
        }
        return messageList;
    }

    List<Message> getMessages(final Locale locale, Element element) {
        final List<Message> messageList = getElementInfo(element).messageMap.get(locale);
        if (messageList == null) {
            return Collections.emptyList();
        } else {
            return Collections.unmodifiableList(messageList);
        }
    }
    
    List<Message> getMessagesForType(final Locale locale, TypeElement type) {
        final List<Message> messageList = new LinkedList<Message>();
        for (ElementInfo elementInfo : elements.values()) {
            if (Utils.getType(elementInfo.element) == type) {
                final List<Message> elementMessages = elementInfo.messageMap.get(locale);
                if (elementMessages != null) {
                    messageList.addAll(elementMessages);
                }
            }
        }        
        return messageList;
    }

    Set<String> getMessageKeys() {
        Set<String> messageKeys = new HashSet<String>();
        for (Element element : elements.keySet()) {
            messageKeys.addAll(getMessageKeys(element));
        }
        return messageKeys;
    }

    Set<String> getMessageKeys(Element element) {
        return Collections.unmodifiableSet(getElementInfo(element).messageKeys);
    }

    Set<String> getMessageKeysForType(TypeElement type) { 
       Set<String> messageKeys = new HashSet<String>();
       for (Element element : elements.keySet()) {
            final TypeElement typeElement = Utils.getType(element);
            if (typeElement == type) {
                messageKeys.addAll(getMessageKeys(element));
            }
        }
       return messageKeys;
    }
    
    PackageInfo getParent() {
        String packageName = pkg.getQualifiedName().toString();
        while (Utils.hasParentPackage(packageName)) {
            packageName = Utils.getParentPackage(packageName);
            final PackageInfo pkgInfo = owner.getPackageByName(packageName);
            if (pkgInfo != null) {
                return pkgInfo;
            }
        }
        return null;
    }

    Set<PackageInfo> getSubPackages() {
        return owner.getSubPackages(this);
    }

    boolean isRoot() {
        return getParent() == null;
    }
}

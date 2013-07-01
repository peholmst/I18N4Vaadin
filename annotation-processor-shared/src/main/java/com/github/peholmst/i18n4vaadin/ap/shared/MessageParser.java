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

import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.github.peholmst.i18n4vaadin.annotations.Messages;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

/**
 *
 * @author Petter Holmström
 */
public class MessageParser {

    private final RoundEnvironment roundEnv;
    private static final Logger LOG = Logger.getLogger(MessageParser.class.getCanonicalName());
    private final Map<String, PackageDescriptor> packageMap = new HashMap<String, PackageDescriptor>();

    public MessageParser(RoundEnvironment roundEnv) {
        this.roundEnv = roundEnv;

        for (final Element element : roundEnv.getElementsAnnotatedWith(Message.class)) {
            addMessage(element.getAnnotation(Message.class), element);
        }
        for (final Element element : roundEnv.getElementsAnnotatedWith(Messages.class)) {
            Messages msgs = element.getAnnotation(Messages.class);
            if (msgs != null) {
                for (Message msg : msgs.value()) {
                    addMessage(msg, element);
                }
            }
        }
    }

    private void addMessage(Message message, Element declaringElement) {
        if (message == null) {
            LOG.log(Level.WARNING, "Tried to add null Message annotation, ignoring");
            return;
        }
        LOG.log(Level.INFO, "Adding message {0} declared by {1}", new Object[]{message, declaringElement});
        getMessageOwnerForElement(declaringElement).addMessage(new MessageDescriptor(message));
    }

    private MessageOwner getMessageOwnerForElement(Element element) {
        final PackageElement pkgElement = Utils.getPackage(element);
        if (pkgElement == null) {
            LOG.log(Level.SEVERE, "Could not determine package for element {0}", element);
            throw new IllegalStateException("Found annotated element that is not or does not belong to a package");
        }
        final PackageDescriptor pkgDescr = getPackageDescription(pkgElement);

        final TypeElement typeElement = Utils.getType(element);
        if (typeElement == null) {
            LOG.log(Level.INFO, "Could not determine type for element {0}, storing the message in the package instead", element);
            return pkgDescr;
        } else {
            return pkgDescr.getType(typeElement);
        }
    }

    private PackageDescriptor getPackageDescription(PackageElement element) {
        PackageDescriptor descr = packageMap.get(element.getQualifiedName().toString());
        if (descr == null) {
            descr = new PackageDescriptor(element);
            packageMap.put(element.getQualifiedName().toString(), descr);
        }
        return descr;
    }

    public Collection<PackageDescriptor> getPackages() {
        return Collections.unmodifiableCollection(packageMap.values());
    }
}

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
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.PackageElement;

/**
 * Class containing information about the messages declared in one specific Java
 * package. <p><b>This class is internal and should never be used by
 * clients.</b>
 *
 * @author Petter Holmström
 */
final class PackageInfo {

    private final Map<Locale, List<Message>> messageMap = new HashMap<Locale, List<Message>>();
    private final PackageMap owner;
    private final PackageElement pkg;

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
        return Collections.unmodifiableSet(messageMap.keySet());
    }

    void addMessage(final Message message) {
        final Locale locale = Utils.convertLocaleAnnotation(message.locale());
        List<Message> messageList = messageMap.get(locale);
        if (messageList == null) {
            messageList = new LinkedList<Message>();
            messageMap.put(locale, messageList);
        }
        messageList.add(message);
    }

    void addMessages(final Messages messages) {
        for (final Message msg : messages.value()) {
            addMessage(msg);
        }
    }

    List<Message> getMessages(final Locale locale) {
        final List<Message> messageList = messageMap.get(locale);
        if (messageList == null) {
            return Collections.emptyList();
        } else {
            return Collections.unmodifiableList(messageList);
        }
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

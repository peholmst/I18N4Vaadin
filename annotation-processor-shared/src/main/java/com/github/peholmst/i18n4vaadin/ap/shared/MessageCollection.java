/*
 * Copyright (c) 2013 Petter Holmström
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
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Petter Holmström
 */
public class MessageCollection {
    
    private final Map<Locale, Collection<MessageDescriptor>> messagesMap = new HashMap<Locale, Collection<MessageDescriptor>>();

    private final Set<MessageDescriptor> allMessages = new HashSet<MessageDescriptor>();

    private final Set<String> allMessageKeys = new HashSet<String>();

    public MessageCollection() {
    }

    public Collection<MessageDescriptor> getAllMessages() {
        return Collections.unmodifiableCollection(allMessages);
    }

    public Collection<String> getAllMessageKeys() {
        return Collections.unmodifiableCollection(allMessageKeys);
    }

    public Collection<MessageDescriptor> getMessagesByLocale(Locale locale) {
        return Collections.unmodifiableCollection(messagesMap.get(locale));
    }

    public Collection<Locale> getLocales() {
        return Collections.unmodifiableCollection(messagesMap.keySet());
    }

    private Collection<MessageDescriptor> getOrCreateMessageCollectionForLocale(Locale locale) {
        Collection<MessageDescriptor> messages = messagesMap.get(locale);
        if (messages == null) {
            messages = new HashSet<MessageDescriptor>();
            messagesMap.put(locale, messages);
        }
        return messages;
    }

    private void addMessage(MessageDescriptor message) {
        getOrCreateMessageCollectionForLocale(message.getLocale()).add(message);
        allMessages.add(message);
        allMessageKeys.add(message.getKey());
    }

    private void addMessages(Collection<MessageDescriptor> messages) {
        for (MessageDescriptor msg : messages) {
            addMessage(msg);
        }
    }

    private void addMessages(MessageCollection messages) {
        addMessages(messages.getAllMessages());
    }

    public static class Builder {

        private MessageCollection collection = new MessageCollection();

        public Builder withMessage(MessageDescriptor message) {
            collection.addMessage(message);
            return this;
        }

        public Builder withMessages(MessageCollection messages) {
            collection.addMessages(messages);
            return this;
        }

        public Builder withMessages(Collection<MessageDescriptor> messages) {
            collection.addMessages(messages);
            return this;
        }

        public MessageCollection build() {
            return collection;
        }

    };
}

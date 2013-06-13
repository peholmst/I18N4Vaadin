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

/**
 *
 * @author Petter Holmström
 */
public abstract class MessageOwner {

    private final MessageCollection.Builder messagesBuilder = new MessageCollection.Builder();
    private final MessageCollection messages = messagesBuilder.build();

    public MessageCollection getMessages() {
        return messages;
    }

    void addMessage(MessageDescriptor message) {
        messagesBuilder.withMessage(message);
        message.setOwner(this);
    }
}

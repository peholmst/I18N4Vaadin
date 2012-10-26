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
package com.github.peholmst.i18n4vaadin.util;

import com.github.peholmst.i18n4vaadin.I18N;

/**
 * TODO Document me!
 *
 * @author Petter Holmström
 */
public final class I18NHolder {

    private static Strategy strategy = new ThreadLocalStrategy();

    private I18NHolder() {
    }

    public static I18N get() {
        return get(I18N.class);
    }

    public static <T extends I18N> T get(Class<T> clazz) {
        return getStrategy().get(clazz);
    }

    public static void setStrategy(Strategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Cannot set strategy to null");
        }
        synchronized (I18NHolder.class) {
            I18NHolder.strategy = strategy;
        }
    }

    public static Strategy getStrategy() {
        synchronized (I18NHolder.class) {
            return strategy;
        }
    }

    /**
     *
     */
    public interface Strategy {

        <T extends I18N> T get(Class<T> clazz);

        void set(I18N i18n) throws UnsupportedOperationException;
    }

    /**
     *
     */
    public static class ThreadLocalStrategy implements Strategy {

        private final InheritableThreadLocal<I18N> instance = new InheritableThreadLocal<I18N>();

        @Override
        public <T extends I18N> T get(Class<T> clazz) {
            return clazz.cast(instance.get());
        }

        @Override
        public void set(I18N i18n) throws UnsupportedOperationException {
            if (i18n == null) {
                instance.remove();
            } else {
                instance.set(i18n);
            }
        }
    }
}

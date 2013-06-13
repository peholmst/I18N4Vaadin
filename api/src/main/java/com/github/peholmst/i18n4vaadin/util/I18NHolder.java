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
package com.github.peholmst.i18n4vaadin.util;

import com.github.peholmst.i18n4vaadin.I18N;

/**
 * Utility class for keeping track of the current I18N instance. This way, you
 * don't have to pass the I18N class as a parameter or property to every class
 * that needs it. Instead, they can just invoke {@link #get() }.
 * <p>
 * You will probably not need this class if you are using CDI, since that will
 * make it possible to inject the current I18N instance into the objects that
 * need it.
 *
 * @author Petter Holmström
 */
public final class I18NHolder {

    private static Strategy strategy = new ThreadLocalStrategy();

    private I18NHolder() {
    }

    /**
     * Returns the current I18N instance, or {@code null} if none is available.
     */
    public static I18N get() {
        return get(I18N.class);
    }

    /**
     * Returns the current I18N instance cast to the specified class, or
     * {@code null} if none is available.
     */
    public static <T extends I18N> T get(Class<T> clazz) {
        return getStrategy().get(clazz);
    }

    /**
     * Sets the strategy to use for storing the current I18N instance.
     *
     * @param strategy the strategy, must not be {@code null}.
     */
    public static void setStrategy(Strategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Cannot set strategy to null");
        }
        synchronized (I18NHolder.class) {
            I18NHolder.strategy = strategy;
        }
    }

    /**
     * Returns the strategy used for storing the current I18N instance (never
     * {@code null}). By default, {@link ThreadLocalStrategy} is used.
     */
    public static Strategy getStrategy() {
        synchronized (I18NHolder.class) {
            return strategy;
        }
    }

    /**
     * Interface defining the strategy that {@link I18NHolder} should use to
     * store the current I18N instance.
     */
    public interface Strategy {

        /**
         * Returns the current I18N instance cast to the specified class.
         *
         * @return the current I18N instance, or {@code null} if none is
         * available.
         */
        <T extends I18N> T get(Class<T> clazz);

        /**
         * Sets the current I18N instance.
         *
         * @throws UnsupportedOperationException if the I18N instance cannot be
         * explicitly set using this strategy.
         */
        void set(I18N i18n) throws UnsupportedOperationException;
    }

    /**
     * This is an implementation of {@link Strategy} that stores the current
     * instance in a thread local variable.
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

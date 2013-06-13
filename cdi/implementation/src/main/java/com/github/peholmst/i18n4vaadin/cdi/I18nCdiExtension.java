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
package com.github.peholmst.i18n4vaadin.cdi;

import com.github.peholmst.i18n4vaadin.cdi.annotations.DisableI18n;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.ProcessBean;
import javax.enterprise.inject.spi.ProcessInjectionTarget;

/**
 * TODO Document me!
 *
 * @author Petter Holmström
 */
public class I18nCdiExtension implements Extension {

    <X> void processInjectionTarget(@Observes ProcessInjectionTarget<X> pit, BeanManager beanManager) {
        if (!pit.getAnnotatedType().isAnnotationPresent(DisableI18n.class)) {
            pit.setInjectionTarget(new I18nInjectionTarget<X>(pit.getInjectionTarget(), pit.getAnnotatedType(), beanManager));
        }
    }

    <X> void processBean(@Observes ProcessBean<X> pb) {
    }

    <X> void processAnnotatedType(@Observes ProcessAnnotatedType<X> pat) {
    }

    private class I18nInjectionTarget<T> implements InjectionTarget<T> {

        private final InjectionTarget<T> delegate;
        private final BeanManager beanManager;
        private final AnnotatedType<T> at;
        
        public I18nInjectionTarget(InjectionTarget<T> delegate, AnnotatedType<T> at, BeanManager beanManager) {
            this.delegate = delegate;
            this.beanManager = beanManager;
            this.at = at;
        }

        @Override
        public void inject(T instance, CreationalContext<T> ctx) {
            delegate.inject(instance, ctx);
            if (!I18nConfigurer.class.isAssignableFrom(instance.getClass())) {
                for (Bean<?> configurerBean : beanManager.getBeans(I18nConfigurer.class)) {
                    I18nConfigurer configurer = (I18nConfigurer) beanManager.getReference(configurerBean,
                            I18nConfigurer.class, beanManager.createCreationalContext(configurerBean));
                    configurer.configure(at, instance);
                }
            }
        }

        @Override
        public void postConstruct(T instance) {
            delegate.postConstruct(instance);
        }

        @Override
        public void preDestroy(T instance) {
            delegate.preDestroy(instance);
        }

        @Override
        public T produce(CreationalContext<T> ctx) {
            return delegate.produce(ctx);
        }

        @Override
        public void dispose(T instance) {
            delegate.dispose(instance);
        }

        @Override
        public Set<InjectionPoint> getInjectionPoints() {
            return delegate.getInjectionPoints();
        }
    }
}

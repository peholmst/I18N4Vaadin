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

import com.github.peholmst.i18n4vaadin.annotations.BundleGeneration;
import com.github.peholmst.i18n4vaadin.annotations.BundleStrategy;
import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.github.peholmst.i18n4vaadin.annotations.Messages;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

/**
 * Annotation processor for generating bundles and accessor classes based on the
 * {@link Message}, {@link Messages} and {@link BundleGeneration} annotations.
 * <p> <b>Note!</b> The entire Annotation Processor package is to be considered
 * as highly unstable and should never be used directly by clients. The
 * implementation can change dramatically and the future and no backwards
 * compatibility whatsoever is guaranteed.
 *
 * @author Petter Holmström
 */
@SupportedAnnotationTypes({AnnotationProcessor.ANNOTATION_PACKAGE + ".Message",
    AnnotationProcessor.ANNOTATION_PACKAGE + ".Messages",
    AnnotationProcessor.ANNOTATION_PACKAGE + ".BundleGeneration"})
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class AnnotationProcessor extends AbstractProcessor {

    static final String ANNOTATION_PACKAGE = "com.github.peholmst.i18n4vaadin.annotations";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        final PackageMap packageMap = buildPackageMap(roundEnv);
        for (final PackageInfo rootPackage : packageMap.getRootPackages()) {
            getStrategy(rootPackage.getBundleGeneration()).process(rootPackage);
        }
        return true;
    }

    private PackageMap buildPackageMap(final RoundEnvironment roundEnv) {
        final PackageMap map = new PackageMap();

        for (final Element element : roundEnv.getElementsAnnotatedWith(BundleGeneration.class)) {
            // @BundleGeneration can only be placed on packages, so no need to check the kind
            map.addPackage((PackageElement) element);
        }
        for (final Element element : roundEnv.getElementsAnnotatedWith(Message.class)) {
            map.getPackage(Utils.getPackage(element)).addMessage(element.getAnnotation(Message.class));
        }
        for (final Element element : roundEnv.getElementsAnnotatedWith(Messages.class)) {
            map.getPackage(Utils.getPackage(element)).addMessages(element.getAnnotation(Messages.class));
        }

        return map;
    }

    private AbstractBundleStrategy getStrategy(final BundleGeneration bundleGeneration) {
        if (bundleGeneration.strategy() == BundleStrategy.INHERITING_PACKAGE_BUNDLES) {
            return new InheritingPackageStrategy(processingEnv);
        } else if (bundleGeneration.strategy() == BundleStrategy.SINGLE_ROOT_BUNDLE) {
            return new SingleRootBundleStrategy(processingEnv);
        } else if (bundleGeneration.strategy() == BundleStrategy.STANDALONE_PACKAGE_BUNDLES) {
            return new StandalonePackageStrategy(processingEnv);
        } else {
            throw new UnsupportedOperationException("Unknown bundle strategy");
        }
    }
}

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

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;

/**
 * @author Petter Holmström
 */
public abstract class AbstractAnnotationProcessor extends AbstractProcessor {

    public static final String ANNOTATION_PACKAGE = "com.github.peholmst.i18n4vaadin.annotations";
    private static final Logger LOG = Logger.getLogger(AbstractAnnotationProcessor.class.getCanonicalName());
    private VelocityEngine engine;

    protected abstract Template getJavaClassTemplate(VelocityEngine engine);

    protected VelocityEngine createEngine() {
        final URL velocityPropertyBundle = getClass().getClassLoader().getResource("com/github/peholmst/i18n4vaadin/ap/shared/velocity.properties");
        final Properties velocityProperties = new Properties();
        try {
            velocityProperties.load(velocityPropertyBundle.openStream());
        } catch (final IOException e) {
            throw new RuntimeException("Could not load velocity properties", e);
        }
        return new VelocityEngine(velocityProperties);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        final MessageParser parser = new MessageParser(roundEnv);
        engine = createEngine();
        final boolean generateOneBundlePerClass = Utils.stringToBoolean(processingEnv.getOptions().get("bundleperclass"));
        if (generateOneBundlePerClass) {
            generateFilesPerClass(parser.getPackages());
        } else {
            generateFilesPerPackage(parser.getPackages());
        }
        return true;
    }

    private void generateFilesPerPackage(Collection<PackageDescriptor> packages) {
        for (PackageDescriptor pkg : packages) {
            createJavaFileGeneratorForPackageBundle(pkg).createFile();
        }
    }

    private void generateFilesPerClass(Collection<PackageDescriptor> packages) {
        for (PackageDescriptor pkg : packages) {
            for (TypeDescriptor type : pkg.getTypes()) {
                createJavaFileGeneratorForClassBundle(type).createFile();
            }
        }
    }

    protected JavaFileGenerator createJavaFileGeneratorForPackageBundle(PackageDescriptor pkg) {
        return new JavaFileGenerator(processingEnv, getJavaClassTemplate(engine), pkg, "Bundle", "messages", pkg.getOwnMessagesAndTypeMessages());
    }

    protected JavaFileGenerator createJavaFileGeneratorForClassBundle(TypeDescriptor type) {
        return new JavaFileGenerator(processingEnv, getJavaClassTemplate(engine), type.getPackage(), type.getSimpleName() + "Bundle", type.getSimpleName() + "Messages", type.getMessages());
    }
}

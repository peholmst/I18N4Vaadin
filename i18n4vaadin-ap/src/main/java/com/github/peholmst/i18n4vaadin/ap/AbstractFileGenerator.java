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

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import javax.annotation.processing.ProcessingEnvironment;
import org.apache.velocity.app.VelocityEngine;

/**
 * Base class for file generators that actually generate the files based on the
 * annotations. <p><b>This class is internal and should never be used by
 * clients.</b>
 *
 * @author Petter Holmström
 */
abstract class AbstractFileGenerator {

    final ProcessingEnvironment processingEnv;
    final VelocityEngine velocityEngine;
    final static String PACKAGE_PATH = "com/github/peholmst/i18n4vaadin/ap";

    AbstractFileGenerator(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
        final URL velocityPropertyBundle = getClass().getClassLoader().getResource(PACKAGE_PATH + "/velocity.properties");
        final Properties velocityProperties = new Properties();
        try {
            velocityProperties.load(velocityPropertyBundle.openStream());
        } catch (final IOException e) {
            throw new RuntimeException("Could not load velocity properties", e);
        }
        velocityEngine = new VelocityEngine(velocityProperties);
    }

    abstract void process(PackageMap packageMap);
}

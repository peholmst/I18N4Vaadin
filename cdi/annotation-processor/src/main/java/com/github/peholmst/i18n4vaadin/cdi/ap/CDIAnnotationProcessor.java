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
package com.github.peholmst.i18n4vaadin.cdi.ap;

import com.github.peholmst.i18n4vaadin.ap.shared.AbstractAnnotationProcessor;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;

/**
 * @author Petter Holmström
 */
@SupportedAnnotationTypes({
    CDIAnnotationProcessor.ANNOTATION_PACKAGE + ".Message",
    CDIAnnotationProcessor.ANNOTATION_PACKAGE + ".Messages"})
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class CDIAnnotationProcessor extends AbstractAnnotationProcessor {

    private Template template;

    @Override
    protected Template getJavaClassTemplate(VelocityEngine engine) {
        if (template == null) {
            template = engine.getTemplate("com/github/peholmst/i18n4vaadin/cdi/ap/JavaClassTemplate.vm");
        }
        return template;
    }
}

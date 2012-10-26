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
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

/**
 * Class for generating the classes that are used to access the bundle files.
 * <p><b>This class is internal and should never be used by clients.</b>
 *
 * @author Petter Holmström
 */
class JavaFileGenerator extends AbstractFileGenerator {

    final Template cdiTemplate;

    JavaFileGenerator(ProcessingEnvironment processingEnv) {
        super(processingEnv);
        if (processingEnv.getOptions().containsKey("cdisupport")) {
            cdiTemplate = velocityEngine.getTemplate(PACKAGE_PATH + "/BundleWithCDI.vm");
        } else {
            cdiTemplate = velocityEngine.getTemplate(PACKAGE_PATH + "/BundleWithoutCDI.vm");            
        }
    }

    @Override
    void process(PackageMap packageMap) {
        for (final PackageInfo pkg : packageMap.getAllPackages()) {
            processPackage(pkg, cdiTemplate);
        }
    }

    void processPackage(final PackageInfo packageInfo, final Template template) {
        final String packageName = packageInfo.getName();
        final VelocityContext vc = new VelocityContext();
        vc.put("packageName", packageName);
        vc.put("generator", getClass().getName());
        vc.put("generationDate", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(new Date()));
        vc.put("properties", packageInfo.getMessageKeys());
        try {
            JavaFileObject jfo = processingEnv.getFiler().createSourceFile(packageName + ".Bundle");
            Writer writer = jfo.openWriter();
            template.merge(vc, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Could not create source file", e);
        }
    }
}

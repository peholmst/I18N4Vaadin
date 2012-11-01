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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
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

    final Template template;
    boolean generateOneBundlePerClass;

    JavaFileGenerator(ProcessingEnvironment processingEnv) {
        super(processingEnv);
        if (processingEnv.getOptions().containsKey("cdisupport")) {
            template = velocityEngine.getTemplate(PACKAGE_PATH + "/BundleWithCDI.vm");
        } else {
            template = velocityEngine.getTemplate(PACKAGE_PATH + "/BundleWithoutCDI.vm");
        }
        generateOneBundlePerClass = processingEnv.getOptions().containsKey("bundleperclass");
    }

    @Override
    void process(PackageMap packageMap) {
        for (final PackageInfo pkg : packageMap.getAllPackages()) {
            if (generateOneBundlePerClass) {
                processPackageMultipleBundles(pkg, template);
            } else {
                processPackageSingleBundle(pkg, template);
            }
        }
    }

    void processPackageSingleBundle(final PackageInfo packageInfo, final Template template) {
        final String packageName = packageInfo.getName();
        final VelocityContext vc = new VelocityContext();
        vc.put("packageName", packageName);
        vc.put("generator", getClass().getName());
        vc.put("generationDate", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(new Date()));
        vc.put("properties", packageInfo.getMessageKeys());
        vc.put("classNamePrefix", "");
        try {
            JavaFileObject jfo = processingEnv.getFiler().createSourceFile(packageName + ".Bundle");
            Writer writer = jfo.openWriter();
            template.merge(vc, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Could not create source file", e);
        }
    }

    void processPackageMultipleBundles(final PackageInfo packageInfo, final Template template) {
        final String packageName = packageInfo.getName();
        final VelocityContext vc = new VelocityContext();
        vc.put("packageName", packageName);
        vc.put("generator", getClass().getName());
        vc.put("generationDate", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(new Date()));

        final Map<TypeElement, List<String>> messageKeyMap = new HashMap<TypeElement, List<String>>();
        for (Element element : packageInfo.getAnnotatedElements()) {
            final TypeElement typeElement = Utils.getType(element);
            List<String> messageKeys = messageKeyMap.get(typeElement);
            if (messageKeys == null) {
                messageKeys = new LinkedList<String>();
                messageKeyMap.put(typeElement, messageKeys);
            }
            messageKeys.addAll(packageInfo.getMessageKeys(element));
        }

        for (Map.Entry<TypeElement, List<String>> entry : messageKeyMap.entrySet()) {
            String className = entry.getKey().getSimpleName().toString();
            vc.put("classNamePrefix", className);
            vc.put("properties", entry.getValue());
            try {
                JavaFileObject jfo = processingEnv.getFiler().createSourceFile(packageName + "." + className + "Bundle");
                Writer writer = jfo.openWriter();
                template.merge(vc, writer);
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException("Could not create source file", e);
            }
        }
    }
}

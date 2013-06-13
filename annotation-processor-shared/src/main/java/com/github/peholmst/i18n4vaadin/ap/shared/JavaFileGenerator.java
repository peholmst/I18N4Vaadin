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
package com.github.peholmst.i18n4vaadin.ap.shared;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class JavaFileGenerator {

    private static final Logger LOG = Logger.getLogger(JavaFileGenerator.class.getCanonicalName());
    protected final Template template;
    protected final ProcessingEnvironment processingEnv;
    protected final PackageDescriptor destinationPackage;
    protected final String className;
    protected final String bundleName;
    protected final MessageCollection messages;

    public JavaFileGenerator(ProcessingEnvironment processingEnv, Template template, PackageDescriptor destinationPackage, String className, String bundleName, MessageCollection messages) {
        this.template = template;
        this.processingEnv = processingEnv;
        this.className = className;
        this.bundleName = bundleName;
        this.destinationPackage = destinationPackage;
        this.messages = messages;
    }

    public void createFile() {
        final VelocityContext vc = createAndInitVelocityContext();
        final String fullClassName = destinationPackage.getName() + "." + className;
        LOG.log(Level.INFO, "Creating Java file {0}", fullClassName);
        try {
            JavaFileObject jfo = processingEnv.getFiler().createSourceFile(fullClassName);
            Writer writer = jfo.openWriter();
            template.merge(vc, writer);
            writer.close();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Could not create source file " + fullClassName, e);
            throw new RuntimeException(e);
        }
    }

    protected VelocityContext createAndInitVelocityContext() {
        final VelocityContext vc = new VelocityContext();
        vc.put("generator", getClass().getName());
        vc.put("generationDate", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(new Date()));
        vc.put("packageName", destinationPackage.getName());
        vc.put("className", className);
        vc.put("bundleName", bundleName);
        vc.put("messages", messages);
        return vc;
    }
}

/**
 *
 * Copyright 2006-2009, David Blevins, Andrew Bayer
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.kenai.issuezilla2jira;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;

import org.codehaus.swizzle.jirareport.FederatedResourceLoader;
import org.codehaus.swizzle.jirareport.MissingParamsException;
import org.codehaus.swizzle.jirareport.ArraysUtil;
import org.codehaus.swizzle.jirareport.CollectionsUtil;
import org.codehaus.swizzle.jirareport.DateUtil;
import org.codehaus.swizzle.jirareport.ParamsUtil;
import org.codehaus.swizzle.jirareport.StringsUtil;
import org.codehaus.swizzle.jirareport.Param;

import org.apache.commons.io.filefilter.WildcardFileFilter;

import com.kenai.issuezilla2jira.translator.*;
import com.kenai.issuezilla2jira.parser.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @version $Revision$ $Date$
 */
public class Main {
    public static void main(String[] args) throws Throwable {
        List newargs = new ArrayList();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.startsWith("-D")) {
                String prop = arg.substring(arg.indexOf("-D") + 2, arg.indexOf("="));
                String val = arg.substring(arg.indexOf("=") + 1);

                System.setProperty(prop, val);
            } else {
                newargs.add(arg);
            }
        }

        args = (String[]) newargs.toArray(new String[] {});

        String originDir = args[0];

        String templateName = "jira-jelly.vm";

        try {
            generate(templateName, System.out, parseIssueXml(originDir));
        } catch (InvocationTargetException e) {
            throw e.getCause();
        } catch (MethodInvocationException e) {
            Throwable wrappedThrowable = e.getWrappedThrowable();
            if (wrappedThrowable instanceof MissingParamsException) {
                MissingParamsException missingParamsException = (MissingParamsException) wrappedThrowable;
                Param[] missingArgs = missingParamsException.getParams();

                System.err.println("Invalid or missing arguments.");

                for (int i = 0; i < missingArgs.length; i++) {
                    Param param = (Param) missingArgs[i];
                    System.err.print("  [");
                    System.err.print(param.getStatus());
                    System.err.print("]    ");
                    System.err.print(param.getName());
                    System.err.print("    : ");
                    System.err.print(param.getDescription());
                    System.err.print(". Must Match Pattern '");
                    System.err.print(param.getRegex());
                    System.err.println("'");
                }
                System.exit(1);
            } else {
                throw e;
            }
        }
    }

    public static List<JiraIssue> parseIssueXml(String originDir) throws Exception {
        File[] xmlFiles = new File(originDir).listFiles((FileFilter)new WildcardFileFilter("*.xml"));
        String projectKey = System.getProperty("projectKey");
        
        List<JiraIssue> jiraIssues = new ArrayList<JiraIssue>();
        
        IssueZillaParser parser = new IssueZillaParser();
        for (File xmlFile : xmlFiles) {
            IssueZillaIssue origIssue = parser.parse(xmlFile);
            if (origIssue!=null) {
                JiraIssue jiraIssue = new JiraIssue(origIssue, projectKey);
                jiraIssues.add(jiraIssue);
            }
        }

        return jiraIssues;
    }
    
    public static void generate(String templateName, PrintStream result, List<JiraIssue> issues) throws Exception {
        VelocityContext context = new VelocityContext();
        generate(context, templateName, result, issues);
    }

    public static void generate(VelocityContext context, String templateName, PrintStream result,
                                List<JiraIssue> issues) throws Exception {
        List keys = Arrays.asList(context.getKeys());

        for (Iterator iterator = System.getProperties().entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String name = (String) entry.getKey();
            String value = (String) entry.getValue();

            // Don't overwrite anything explicitly added to the context.
            if (!keys.contains(name)) {
                context.put(name, value);
            }
        }
        context.put("issues", issues);
        context.put("arrays", new ArraysUtil());
        context.put("collections", new CollectionsUtil());
        context.put("strings", new StringsUtil());
        context.put("utils", new UtilsUtil());
        context.put("params", new ParamsUtil(context));
        context.put("date", new DateUtil(System.getProperty("dateFormat", "yyyy-MM-dd")));
        VelocityEngine velocity = new VelocityEngine();
        velocity.setProperty(Velocity.RESOURCE_LOADER, "all");
        velocity.setProperty("all.resource.loader.class", FederatedResourceLoader.class.getName());
        velocity.init();

        Template template = velocity.getTemplate(templateName);

        PrintWriter writer = new PrintWriter(result);
        template.merge(context, writer);
        writer.flush();
    }

    public static class UtilsUtil {
        public Object load(String classname) throws Exception {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            Class clazz = cl.loadClass(classname);
            return clazz.newInstance();
        }
    }

}

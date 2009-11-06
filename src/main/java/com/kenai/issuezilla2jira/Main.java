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
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

        String mainOutput = System.getProperty("output");
        String projOutput = System.getProperty("projOutput");
        String watchOutput = System.getProperty("watchOutput");
        PrintStream mainOut = null;
        PrintStream projOut = null;
        PrintStream watchOut = null;
        try {
            mainOut = new PrintStream(new FileOutputStream(new File(mainOutput)));
            projOut = new PrintStream(new FileOutputStream(new File(projOutput)));
            watchOut = new PrintStream(new FileOutputStream(new File(watchOutput)));
            
        } catch (IOException e) {
            throw e;
        }

        try {
            generate(templateName, mainOut, projOut, watchOut, originDir);
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
    
    public static void generate(String templateName, PrintStream result,
                                PrintStream projResult, PrintStream watchResult, String originDir) throws Exception {
        VelocityContext context = new VelocityContext();
        generate(context, templateName, result, projResult, watchResult, originDir);
    }

    public static void generate(VelocityContext context, String templateName, PrintStream result,
                                PrintStream projResult, PrintStream watchResult, String originDir) throws Exception {
        File[] xmlFiles = new File(originDir).listFiles((FileFilter)new WildcardFileFilter("*.xml"));
        String projectKey = System.getProperty("projectKey");
        
        TreeMap<Integer,JiraIssue> issuesMap = new TreeMap<Integer,JiraIssue>();
        List<String> components = new ArrayList<String>();
        List<String> users = new ArrayList<String>();
        List<String> versions = new ArrayList<String>();
        
        IssueZillaParser parser = new IssueZillaParser();
        for (File xmlFile : xmlFiles) {
            IssueZillaIssue origIssue = parser.parse(xmlFile);
            if (origIssue!=null) {
                JiraIssue jiraIssue = new JiraIssue(origIssue, projectKey);
                issuesMap.put(new Integer(jiraIssue.getIssueId()),jiraIssue);
                if (!components.contains(origIssue.getSubComponent())) {
                    components.add(origIssue.getSubComponent());
                }
                if (!versions.contains(origIssue.getVersion())) {
                    versions.add(origIssue.getVersion());
                }
                for (String user : jiraIssue.getUsersForIssue()) {
                    if (!users.contains(user) && (!user.equals("-1"))) {
                        users.add(user);
                    }
                }
            }
        }

        
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
        List<JiraIssue> issues = new ArrayList<JiraIssue>();
        for (Map.Entry<Integer,JiraIssue> e : issuesMap.entrySet()) {
            issues.add(e.getValue());
        }
        
        context.put("versions", versions);
        context.put("components", components);
        context.put("users", users);
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
        Template projTemp = velocity.getTemplate("jira-create-project.vm");
        PrintWriter projWriter = new PrintWriter(projResult);
        projTemp.merge(context, projWriter);
        projWriter.flush();
        Template watchTemp = velocity.getTemplate("jira-watchers.vm");
        PrintWriter watchWriter = new PrintWriter(watchResult);
        watchTemp.merge(context, watchWriter);
        watchWriter.flush();
    }

    public static class UtilsUtil {
        public Object load(String classname) throws Exception {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            Class clazz = cl.loadClass(classname);
            return clazz.newInstance();
        }
    }

}

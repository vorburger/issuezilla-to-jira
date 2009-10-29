/**
 *
 * Copyright 2006-2009, David Blevins, Andrew Bayer, Michael Vorburger
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

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.codehaus.swizzle.jirareport.ArraysUtil;
import org.codehaus.swizzle.jirareport.CollectionsUtil;
import org.codehaus.swizzle.jirareport.DateUtil;
import org.codehaus.swizzle.jirareport.FederatedResourceLoader;
import org.codehaus.swizzle.jirareport.MissingParamsException;
import org.codehaus.swizzle.jirareport.Param;
import org.codehaus.swizzle.jirareport.ParamsUtil;
import org.codehaus.swizzle.jirareport.StringsUtil;

import com.kenai.issuezilla2jira.javanet.JavaNetIssueZillaExporter;
import com.kenai.issuezilla2jira.parser.IssueZillaIssue;
import com.kenai.issuezilla2jira.parser.IssueZillaParser;
import com.kenai.issuezilla2jira.translator.JiraIssue;
import com.kenai.issuezilla2jira.translator.JiraProject;

/**
 * Main.
 */
public class Main {
	
	private static final String ISSUEZILLA_XML = "IssuezillaXML";
	private static final String JIRAJELLY_XML_DIR = "JIRA-Jelly";

	public static void main(String[] args) throws Throwable {
		if (args.length == 0)
			usage();
		else if ("downloadXML".equalsIgnoreCase(args[0]) && args.length == 6) {
			String workingDir = addSlash(args[1]);
			String javaNetProjectName = args[2].toLowerCase();
			String javaNetUid = args[3];
			String javaNetPwd = args[4];
			String javaNetCgi = args[5];
			
			System.setProperty("java.net.xml.cgi", javaNetCgi);
			File dir = new File(workingDir, ISSUEZILLA_XML);
			JavaNetIssueZillaExporter.dumpAll(javaNetUid, javaNetPwd, javaNetProjectName, dir);

		} else if ("downloadAttachments".equalsIgnoreCase(args[0]) && args.length == 2) {
			String workingDir = addSlash(args[1]);
			AttachmentsMain.fetchAndSaveAllAttachments(workingDir + ISSUEZILLA_XML, workingDir + JIRAJELLY_XML_DIR);
			
		} else if ("jelly".equalsIgnoreCase(args[0]) && args.length == 5) {
			String workingDir = addSlash(args[1]);
			String jiraProjectName = args[2];
			String jiraAdmin = args[3];
			String jiraProjectLead = args[4];
			
			JiraProject jiraProject = new JiraProject();
			jiraProject.projectKey = jiraProjectName.toUpperCase();
			jiraProject.projectName = jiraProjectName;
			jiraProject.jiraAdmin = jiraAdmin;
			jiraProject.projectLead = jiraProjectLead;
			
			run(workingDir + ISSUEZILLA_XML, jiraProject, workingDir + JIRAJELLY_XML_DIR);
			
		} else {
			usage();
		}
	}
	
	private static String addSlash(String string) {
		if (!string.endsWith("/")) {
			return string + "/";
		} else {
			return string;
		}
	}

	private static void usage() {
		System.out.println("USAGE: java -jar jelly-generator-*.jar downloadXML | downloadAttachments | jelly ...");
		System.out.println("\t\tdownloadXML <working-dir> <java.net-projectName> <java.net-uid> <java.net-pwd> <java.net-cgi>");
		System.out.println("\t\tdownloadAttachments <working-dir>");
		System.out.println("\t\tjelly <working-dir> <jira-projectName> <jira-admin> <jira-project-lead>");
		System.out.println("\t<working-dir> = local directory where IssueZilla XML dumps, attachments, and generated Jelly will be saved");
		System.out.println("\t...");
	}
	
	public static void run(String originDir, JiraProject jiraProject, String mainOutputDir) throws Throwable {
        File mainOutputDirFile = new File(mainOutputDir);
        mainOutputDirFile.mkdirs();

        try {
			generate(mainOutputDirFile, originDir, jiraProject);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        } catch (MethodInvocationException e) {
            Throwable wrappedThrowable = e.getWrappedThrowable();
            if (wrappedThrowable instanceof MissingParamsException) {
                MissingParamsException missingParamsException = (MissingParamsException) wrappedThrowable;
                Param[] missingArgs = missingParamsException.getParams();

                StringBuilder sb = new StringBuilder("Invalid or missing arguments.");
                for (int i = 0; i < missingArgs.length; i++) {
                    Param param = (Param) missingArgs[i];
                    sb.append("  [");
                    sb.append(param.getStatus());
                    sb.append("]    ");
                    sb.append(param.getName());
                    sb.append("    : ");
                    sb.append(param.getDescription());
                    sb.append(". Must Match Pattern '");
                    sb.append(param.getRegex());
                    sb.append("'\n");
                }
                throw new Exception(sb.toString());
            } else {
                throw e;
            }
        }
        System.out.println("Successfully wrote some *-jelly.xml to " + mainOutputDir);
        System.out.println("Now import this into JIRA; see http://kenai.com/projects/issuezilla-to-jira/pages/JIRASetup ... ");
	}
    
    public static void generate(File outDir, String originDir, JiraProject jiraProject) throws Exception {
        VelocityContext context = new VelocityContext();
        final String projectKey = jiraProject.projectKey;
		context.put("projectKey", projectKey);
        context.put("lowkey", jiraProject.projectKey.toLowerCase());
        context.put("projectName", jiraProject.projectName);
        context.put("projectLead", jiraProject.projectLead); 
        context.put("jiraAdmin", jiraProject.jiraAdmin); 
        generate(context, outDir, originDir, projectKey);
    }

    public static void generate(VelocityContext context, File outDir, String originDir, String projectKey) throws Exception {
        File[] xmlFiles = getXmlFiles(new File(originDir));

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
        context.put("attachmentsDir", new File(outDir, "attachments"));
        VelocityEngine velocity = new VelocityEngine();
        velocity.setProperty(Velocity.RESOURCE_LOADER, "all");
        velocity.setProperty("all.resource.loader.class", FederatedResourceLoader.class.getName());
        velocity.init();

        runTemplate(context, velocity, "jira-create-users-and-groups.vm", outDir, "jira-users-and-groups-jelly.xml");
        runTemplate(context, velocity, "jira-create-project.vm", outDir, "jira-newproject-jelly.xml");
        runTemplate(context, velocity, "jira-jelly.vm", outDir, "jira-issues-jelly.xml");
        runTemplate(context, velocity, "jira-remove-users-and-groups.vm", outDir, "jira-remove-users-and-groups-jelly.xml");
        runTemplate(context, velocity, "jira-attachments.vm", outDir, "jira-attachments-jelly.xml");
    }

	static File[] getXmlFiles(File originDir) throws Exception {
		if (!originDir.exists() || !originDir.isDirectory() || !originDir.canRead()) {
			throw new Exception(originDir + " does not exist or is not a directory or can not be read?!");
		}
		File[] xmlFiles = originDir.listFiles((FileFilter)new WildcardFileFilter("*.xml"));
		if (xmlFiles == null) {
			throw new Exception("Uh, why did File.listFiles() return null although we made sure it's an existing directory?");
		}
        if (xmlFiles.length == 0)
        	throw new Exception("No *.xml files found in " + originDir);
		return xmlFiles;
	}

	private static void runTemplate(VelocityContext context, VelocityEngine velocity,
			String templateFilename, File outDir, String outputFilename) throws Exception 
	{
		PrintStream issuesFilePW = new PrintStream(new FileOutputStream(new File(outDir, outputFilename)));
        Template template = velocity.getTemplate(templateFilename);
        PrintWriter writer = new PrintWriter(issuesFilePW);
        template.merge(context, writer);
        writer.flush();
	}

    public static class UtilsUtil {
        public Object load(String classname) throws Exception {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            Class<?> clazz = cl.loadClass(classname);
            return clazz.newInstance();
        }
    }

}

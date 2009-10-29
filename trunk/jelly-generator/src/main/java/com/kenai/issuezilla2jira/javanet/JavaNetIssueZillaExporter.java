/**
 * 
 * Copyright 2009 Michael Vorburger
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
package com.kenai.issuezilla2jira.javanet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.kohsuke.jnt.JNIssue;
import org.kohsuke.jnt.JNIssueTracker;
import org.kohsuke.jnt.JNProject;
import org.kohsuke.jnt.JavaNet;
import org.kohsuke.jnt.ProcessingException;

/**
 * Exports issues from a Java.Net IssueZilla.
 * 
 * @author Michael Vorburger
 */
public class JavaNetIssueZillaExporter {

    private static final int BULK_SIZE = 100;

	/**
	 * Dumps a Java.Net IssueZilla issue to a local XML file.
	 *  
	 * @param uid java.net login
	 * @param pwd java.net password
	 * @param project java.net project name
	 * @param directory local target directory
	 * @param issueNumber number of Java.Net issue
	 * 
	 * @throws Exception if something went wrong 
	 */
	public static void dumpOne(String uid, String pwd, String projectName, File directory, int issueNumber) throws Exception {
		JNIssueTracker it = getIssueTracker(uid, pwd, projectName, directory);
        JNIssue i = it.get(issueNumber);
        saveIssue(directory, i);
	}

	/**
	 * Dumps all IssueZilla issues of a Java.Net to local XML files.
	 *  
	 * @param uid java.net login
	 * @param pwd java.net password
	 * @param project java.net project name
	 * @param directory local target directory
	 * @param issueNumber number of Java.Net issue
	 * 
	 * @throws Exception if something went wrong 
	 */
	public static void dumpAll(String uid, String pwd, String projectName, File directory) throws Exception {
		JNIssueTracker it = getIssueTracker(uid, pwd, projectName, directory);

		for (int i = 0;; i += BULK_SIZE) {
			System.out.println("Reading " + i + ".." + (i + BULK_SIZE) + " ..."); // TODO Logger?
			Map<Integer, JNIssue> batch = it.getRange(i, i + BULK_SIZE);
			if (batch.isEmpty())
				return;
			System.out.println("Saving " + i + ".." + (i + BULK_SIZE) + " ..."); // TODO Logger?
			for (JNIssue issue : batch.values()) {
				System.out.println("Saving " + issue.getId());
				saveIssue(directory, issue);
			}
		}

	}

	private static JNIssueTracker getIssueTracker(String uid, String pwd, String projectName, File directory) throws ProcessingException {
		if (!directory.exists())
			directory.mkdirs();
		if (!directory.isDirectory())
			throw new IllegalArgumentException("Not a directory: " + directory.toString());
		
        JavaNet con = JavaNet.connect(uid, pwd);
        JNProject project = con.getProject(projectName);
        JNIssueTracker it = project.getIssueTracker();
		return it;
	}

	private static void saveIssue(File directory, JNIssue issue) throws FileNotFoundException, IOException {
		File file = new File(directory, Integer.toString(issue.getId()) + ".xml");
		OutputStream out = new FileOutputStream(file);
        issue.save(out);
        out.close();
	}
}

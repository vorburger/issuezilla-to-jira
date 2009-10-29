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
import java.util.List;

import com.kenai.issuezilla2jira.parser.Attachment;
import com.kenai.issuezilla2jira.parser.IssueZillaIssue;
import com.kenai.issuezilla2jira.parser.IssueZillaParser;
import com.kenai.issuezilla2jira.translator.AttachmentsUtil;

/**
 * Main Entry Point for the Attachments Tool.
 * 
 * @author michael.vorburger
 */
public class AttachmentsMain {

	/**
	 * Download attachments given in issuezilla XML dumps.
	 * 
	 * @param issuezillaXmlOriginDir directory with IssueZilla XML files
	 * @param attachmentsDir directory where downloaded attachments will be saved
	 * @throws Throwable some problem occured
	 */
	public static void fetchAndSaveAllAttachments(File issuezillaXmlOriginDir, File attachmentsDir) throws Throwable {
		assert issuezillaXmlOriginDir.isDirectory();
		assert attachmentsDir.mkdirs();
		assert attachmentsDir.isDirectory();
		attachmentsDir.mkdirs();
		
		AttachmentsUtil util = new AttachmentsUtil();
		util.setAttachmentsDir(attachmentsDir);
		
        File[] xmlFiles = Main.getXmlFiles(issuezillaXmlOriginDir);
        say("Found " + xmlFiles.length + " issuezilla *.xml dumps in " + issuezillaXmlOriginDir);
        IssueZillaParser parser = new IssueZillaParser();
        for (File xmlFile : xmlFiles) {
            IssueZillaIssue issue = parser.parse(xmlFile);
            List<Attachment> attachments = issue.getAttachments();
            if (!attachments.isEmpty()) {
            	say("Issue #" + issue.getIssueId() + " has attachments, will now be downloaded: " + attachments.size());
            	for (Attachment attachment : attachments) {
					util.saveAttachment(attachment);
					say("\t" + attachment.getFilename() + " saved from " + attachment.getAttachUrl());
				}
            } else {
            	say("Issue #" + issue.getIssueId() + " has no attachments");
            }
        }
        say("DONE; all attachments are saved in " + attachmentsDir);
	}

	public static void fetchAndSaveAllAttachments(String issuezillaXmlOriginDir, String mainOutputDir) throws Throwable {
		fetchAndSaveAllAttachments(new File(issuezillaXmlOriginDir), new File(mainOutputDir, "attachments"));
		
	}

	private static void say(String string) {
		System.out.println(string);
	}

}

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
package com.kenai.issuezilla2jira;

import org.junit.Test;

import com.kenai.issuezilla2jira.translator.JiraProject;


/**
 * Tests Main.
 * 
 * @author Michael Vorburger
 */
public class AttachmentsAndJellyGenerationTest {

	@Test
	public void testAttachments() throws Throwable {
		AttachmentsMain.fetchAndSaveAllAttachments("src/test/resources", "target/test/");
	}

	@Test
	public void testCreateSomeMifosIssues() throws Throwable {
		JiraProject jiraProject = new JiraProject();
		jiraProject.projectKey = "TEST";
		jiraProject.projectName = "Test";
		jiraProject.jiraAdmin = "admin";
		jiraProject.projectLead = "admin";
		Main.run("src/test/resources", jiraProject, "target/test/");
	}
	
}

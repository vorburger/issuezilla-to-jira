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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests exporting issuezilla issues from Java.Net.
 * 
 * @author Michael Vorburger
 */
public class JavaNetIssueZillaExporterTest {

	private static final String DIR = "target/test/javanetImportIssueZillaXML";

	@Test
	@Ignore // Unfortunately this test cannot be run without a java.net uid/pwd/java.net.xml.cgi ...
	public void testGetMifosIssues() throws Exception {
		String uid = System.getProperty("uid");
		assertNotNull("uid system property must be set to java.net login", uid);
		String pwd = System.getProperty("pwd");
		assertNotNull("pwd system property must be set to java.net password", uid);
		String cgi = System.getProperty("java.net.xml.cgi");
		assertNotNull("java.net.xml.cgi system property must be set to java.net CGI", cgi);
		
		File dir = new File(DIR);
		JavaNetIssueZillaExporter.dumpAll(uid, pwd, "mifos", dir);

		assertTrue(dir.list().length > 0);
	}
	
}

/**
 *
 * Copyright 2006-9 David Blevins, Andrew Bayer
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
package com.kenai.issuezilla2jira.translator;

import com.kenai.issuezilla2jira.parser.Attachment;

public class JellyUtil {
    public String idRef(Attachment attachment) {
        return "att" + attachment.getAttachId();
    }

    public String idRef(JiraIssue issue) {
        return "id" + issue.getKey().replaceFirst(".*-", "");
    }

    public String keyRef(JiraIssue issue) {
        return issue.getKey().replaceFirst("-", "");
    }

    public String idRef(String issueId) {
        return "id" + issueId.replaceFirst(".*-", "");
    }

    public String keyRef(String issueKey) {
        return issueKey.replaceFirst("-", "");
    }
    
}

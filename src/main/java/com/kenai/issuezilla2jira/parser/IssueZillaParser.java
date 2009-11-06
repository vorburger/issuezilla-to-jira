/**
 *
 * Copyright 2009 Andrew Bayer
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

package com.kenai.issuezilla2jira.parser;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

/**
 * A parser for IssueZilla XML issue dumps.
 *
 * @author Andrew Bayer
 */

public class IssueZillaParser {

    /**
     * Creates a new instance of {@link IssueZillaParser}.
     */
    public IssueZillaParser() {
    }

    public IssueZillaIssue parse(final File file) throws InvocationTargetException {
        try {
            Digester digester = new Digester();
            digester.setValidating(false);
            digester.setClassLoader(IssueZillaParser.class.getClassLoader());

            digester.addObjectCreate("issue", IssueZillaIssue.class);
            digester.addBeanPropertySetter("issue/issue_id", "issueId");
            digester.addBeanPropertySetter("issue/issue_status", "issueStatus");
            digester.addBeanPropertySetter("issue/priority", "priority");
            digester.addBeanPropertySetter("issue/resolution", "resolution");
            digester.addBeanPropertySetter("issue/component", "component");
            digester.addBeanPropertySetter("issue/version", "version");
            digester.addBeanPropertySetter("issue/rep_platform", "platform");
            digester.addBeanPropertySetter("issue/assigned_to", "assignedTo");
            digester.addBeanPropertySetter("issue/delta_ts", "updatedTime");
            digester.addBeanPropertySetter("issue/subcomponent", "subComponent");
            digester.addBeanPropertySetter("issue/reporter", "reporter");
            digester.addBeanPropertySetter("issue/target_milestone", "targetMilestone");
            digester.addBeanPropertySetter("issue/issue_type", "issueType");
            digester.addBeanPropertySetter("issue/creation_ts", "createdTime");
            digester.addBeanPropertySetter("issue/qa_contact", "qaContact");
            digester.addBeanPropertySetter("issue/status_whiteboard", "whiteboard");
            digester.addBeanPropertySetter("issue/issue_file_loc", "issueRefUrl");
            digester.addBeanPropertySetter("issue/votes", "votes");
            digester.addBeanPropertySetter("issue/op_sys", "os");
            digester.addBeanPropertySetter("issue/short_desc", "summary");
            digester.addBeanPropertySetter("issue/keywords", "keywords");
            digester.addCallMethod("issue/cc", "addCc", 1);
            digester.addCallParam("issue/cc", 0);
            
            digester.addObjectCreate("issue/long_desc", Comment.class);
            digester.addBeanPropertySetter("issue/long_desc/who", "commenter");
            digester.addBeanPropertySetter("issue/long_desc/issue_when", "commentTime");
            digester.addBeanPropertySetter("issue/long_desc/thetext", "commentText");
            digester.addSetNext("issue/long_desc", "addComment", Comment.class.getName());

            digester.addObjectCreate("issue/activity", Activity.class);
            digester.addBeanPropertySetter("issue/activity/user");
            digester.addBeanPropertySetter("issue/activity/when");
            digester.addBeanPropertySetter("issue/activity/field_name", "fieldName");
            digester.addBeanPropertySetter("issue/activity/field_desc", "fieldDesc");
            digester.addBeanPropertySetter("issue/activity/oldvalue", "oldValue");
            digester.addBeanPropertySetter("issue/activity/newvalue", "newValue");
            digester.addSetNext("issue/activity", "addActivity", Activity.class.getName());

            digester.addObjectCreate("issue/has_duplicates", HasDuplicate.class);
            digester.addBeanPropertySetter("issue/has_duplicates/issue_id", "issueId");
            digester.addBeanPropertySetter("issue/has_duplicates/who");
            digester.addBeanPropertySetter("issue/has_duplicates/when");
            digester.addSetNext("issue/has_duplicates", "addHasDuplicate", HasDuplicate.class.getName());

            digester.addObjectCreate("issue/is_duplicate", IsDuplicate.class);
            digester.addBeanPropertySetter("issue/is_duplicate/issue_id", "issueId");
            digester.addBeanPropertySetter("issue/is_duplicate/who");
            digester.addBeanPropertySetter("issue/is_duplicate/when");
            digester.addSetNext("issue/is_duplicate", "addIsDuplicate", IsDuplicate.class.getName());

            digester.addObjectCreate("issue/blocks", Blocking.class);
            digester.addBeanPropertySetter("issue/blocks/issue_id", "issueId");
            digester.addBeanPropertySetter("issue/blocks/who");
            digester.addBeanPropertySetter("issue/blocks/when");
            digester.addSetNext("issue/blocks", "addBlocking", Blocking.class.getName());

            digester.addObjectCreate("issue/dependson", DependsOn.class);
            digester.addBeanPropertySetter("issue/dependson/issue_id", "issueId");
            digester.addBeanPropertySetter("issue/dependson/who");
            digester.addBeanPropertySetter("issue/dependson/when");
            digester.addSetNext("issue/dependson", "addDependsOn", DependsOn.class.getName());

            digester.addObjectCreate("issue/attachment", Attachment.class);
            digester.addBeanPropertySetter("issue/attachment/encoding");
            digester.addBeanPropertySetter("issue/attachment/mimetype", "mimeType");
            digester.addBeanPropertySetter("issue/attachment/attachid", "attachId");
            digester.addBeanPropertySetter("issue/attachment/date");
            digester.addBeanPropertySetter("issue/attachment/desc");
            digester.addBeanPropertySetter("issue/attachment/ispatch", "isPatch");
            digester.addBeanPropertySetter("issue/attachment/filename");
            digester.addBeanPropertySetter("issue/attachment/submitter_id", "submitterId");
            digester.addBeanPropertySetter("issue/attachment/submitting_username", "submitterName");
            digester.addBeanPropertySetter("issue/attachment/data");
            digester.addBeanPropertySetter("issue/attachment/attachment_iz_url", "attachUrl");
            digester.addSetNext("issue/attachment", "addAttachment", Attachment.class.getName());
            
            return (IssueZillaIssue)digester.parse(file);
            
        } 
        catch (IOException exception) {
            throw new InvocationTargetException(exception);
        }
        catch (SAXException exception) {
            throw new InvocationTargetException(exception);
        }
    }
}
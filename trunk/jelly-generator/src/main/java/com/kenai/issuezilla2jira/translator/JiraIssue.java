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

package com.kenai.issuezilla2jira.translator;

import com.kenai.issuezilla2jira.parser.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;

/**
 * Given an IssueZilla issue, translates it to JIRA form.
 *
 * @author Andrew Bayer
 */

public class JiraIssue {
    private IssueZillaIssue originalIssue;
    private String projectKey;
    
    public JiraIssue(IssueZillaIssue originalIssue, String projectKey) {
        this.originalIssue = originalIssue;
        this.projectKey = projectKey;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public String getKey() {
        return getProjectKey() + "-" + getIssueId();
    }
    
    public String getIssueType() {
        // Default to Bug, just in case.
        String jiraType = "Bug";

        String issueType = originalIssue.getIssueType();
        if (issueType.equals("DEFECT")) {
            jiraType = "Bug";
        } else if (issueType.equals("ENHANCEMENT")) {
            jiraType = "Improvement";
        }
        else if (issueType.equals("TASK")) {
            jiraType = "Task";
        }
        else if (issueType.equals("FEATURE")) {
            jiraType = "New Feature";
        }
        else if (issueType.equals("PATCH")) {
            jiraType = "Patch";
        }

        return jiraType;
    }

    public String getResolution() {
        // Default to Fixed, just in case.
        String jiraRes = "Fixed";

        String origRes = originalIssue.getResolution();
        if (origRes.equals("FIXED")) {
            jiraRes = "Fixed";
        }
        else if (origRes.equals("DUPLICATE")) {
            jiraRes = "Duplicate";
        }
        else if (origRes.equals("WONTFIX")) {
            jiraRes = "Won't Fix";
        }
        else if (origRes.equals("WORKSFORME")) {
            jiraRes = "Can't Reproduce";
        }
        else if (origRes.equals("INVALID")) {
            jiraRes = "Incomplete";
        }
        else if (origRes.equals("LATER")) {
            jiraRes = "Postponed";
        }
        else if (origRes.equals("REMIND")) {
            jiraRes = "Postponed";
        }

        return jiraRes;
    }

    public String getStatus() {
        // Default to New, just in case.
        String jiraStatus = "New";

        String origStatus = originalIssue.getIssueStatus();
        if (origStatus.equals("NEW")) {
            jiraStatus = "New";
        }
        else if (origStatus.equals("RESOLVED")) {
            jiraStatus = "Resolved";
        }
        else if (origStatus.equals("STARTED")) {
            jiraStatus = "In Progress";
        }
        else if (origStatus.equals("REOPENED")) {
            jiraStatus = "Reopened";
        }
        else if (origStatus.equals("CLOSED")) {
            jiraStatus = "Closed";
        }
        else if (origStatus.equals("VERIFIED")) {
            jiraStatus = "Verified";
        }

        return jiraStatus;
    }

    public String getPriority() {
        // Default to Major, just in case.
        String jiraPri = "Major";

        String origPri = originalIssue.getPriority();
        if (origPri.equals("P1")) {
            jiraPri = "Blocker";
        }
        else if (origPri.equals("P2")) {
            jiraPri = "Critical";
        }
        else if (origPri.equals("P3")) {
            jiraPri = "Major";
        }
        else if (origPri.equals("P4")) {
            jiraPri = "Minor";
        }
        else if (origPri.equals("P5")) {
            jiraPri = "Trivial";
        }

        return jiraPri;
    }

    public int getIssueId() {
        return originalIssue.getIssueId();
    }

    public String getComponent() {
        return originalIssue.getSubComponent();
    }

    public String getEnvironment() {
        return "Platform: " + originalIssue.getPlatform()
            + ", OS: " + originalIssue.getOs();
    }

    public String getUpdatedTime() {
        String origTime = originalIssue.getUpdatedTime();

        return origTime.substring(0,4) + "-"
            + origTime.substring(4,6) + "-"
            + origTime.substring(6,8) + " "
            + origTime.substring(8,10) + ":"
            + origTime.substring(10,12) + ":"
            + origTime.substring(12);
    }

    public String getVersion() {
        return originalIssue.getVersion();
    }

    public String getAssignedTo() {
        return originalIssue.getAssignedTo();
    }

    public String getReporter() {
        return originalIssue.getReporter();
    }

    public String getCreatedTime() {
        return originalIssue.getCreatedTime();
    }

    public String getIssueRefUrl() {
        return originalIssue.getIssueRefUrl();
    }

    public String getSummary() {
        return originalIssue.getSummary();
    }

    public String getDescription() {
        List<Comment> comments = originalIssue.getComments();
        if (comments.size() > 0) {
            return comments.get(0).getCommentText();
        }
        else {
            return "";
        }
    }

    public List<Comment> getComments() {
        List<Comment> descAndComments = originalIssue.getComments();
        List<Comment> justComments = new ArrayList<Comment>();

        if (descAndComments.size() > 1) {
            for (Comment c : descAndComments) {
                if (!c.getCommentText().equals(getDescription())) 
                    justComments.add(c);
            }
        }

        return justComments;
    }

    public List<WorkflowTransition> getWorkflowTransitions() {
        List<Activity> allActivities = originalIssue.getActivities();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        
        Map resolutions = new HashMap();
        TreeMap<Date,Activity> statusChanges = new TreeMap<Date,Activity>();
        TreeMap<Date,Activity> assignments = new TreeMap<Date,Activity>();

        for (Activity act : allActivities) {
            try {
                Date when = formatter.parse(act.getWhen());
                
                if (act.getFieldName().equals("issue_status")) {
                    statusChanges.put(when, act);
                }
                else if (act.getFieldName().equals("resolution")) {
                    resolutions.put(when, act);
                }
                else if (act.getFieldName().equals("assigned_to")) {
                    assignments.put(when, act);
                }
            }
            catch (ParseException e) {
                System.out.println("<!-- ParseException for an activity -->");
                System.err.println("ParseException on a date: " + e.getStackTrace());
            }

        }

        List<WorkflowTransition> transitions = new ArrayList<WorkflowTransition>();
        
        for (Map.Entry<Date,Activity> e : statusChanges.entrySet()) {
            Activity statusChange = e.getValue();
            WorkflowTransition wft = new WorkflowTransition();
            wft.setUser(statusChange.getUser());
            
            if (statusChange.getOldValue().equals("NEW")) {
                if (statusChange.getNewValue().equals("STARTED")) {
                    wft.setWorkflowName("Start Progress");
                }
                else if (statusChange.getNewValue().equals("RESOLVED")) {
                    wft.setWorkflowName("Resolve Issue");
                    wft.setResolution(getResForStatusChange(e.getKey(), resolutions));
                }
            }
            else if (statusChange.getOldValue().equals("STARTED")) {
                if (statusChange.getNewValue().equals("NEW")) {
                    wft.setWorkflowName("Stop Progress");
                }
                else if (statusChange.getNewValue().equals("RESOLVED")) {
                    wft.setWorkflowName("Resolve Issue");
                    wft.setResolution(getResForStatusChange(e.getKey(), resolutions));
                }
            }
            else if (statusChange.getOldValue().equals("RESOLVED")) {
                if (statusChange.getNewValue().equals("REOPENED")) {
                    wft.setWorkflowName("Reopen Issue");
                    wft.setResolution(getResForStatusChange(e.getKey(), resolutions));
                }
                else if (statusChange.getNewValue().equals("CLOSED")) {
                    wft.setWorkflowName("Close Issue");
                }
                else if (statusChange.getNewValue().equals("VERIFIED")) {
                    wft.setWorkflowName("Verify Issue");
                }
            }
            else if (statusChange.getOldValue().equals("REOPENED")) {
                if (statusChange.getNewValue().equals("RESOLVED")) {
                    wft.setWorkflowName("Resolve Issue");
                    wft.setResolution(getResForStatusChange(e.getKey(), resolutions));
                }
                else if (statusChange.getNewValue().equals("NEW")) {
                    wft.setWorkflowName("Stop Progress");
                }
                else if (statusChange.getNewValue().equals("STARTED")) {
                    wft.setWorkflowName("Start Progress");
                }
            }
            else if (statusChange.getOldValue().equals("VERIFIED")) {
                if (statusChange.getNewValue().equals("CLOSED")) {
                    wft.setWorkflowName("Close Issue");
                }
                else if (statusChange.getNewValue().equals("REOPENED")) {
                    wft.setWorkflowName("Reopen Issue");
                    wft.setResolution(getResForStatusChange(e.getKey(), resolutions));
                }
            }
            else if (statusChange.getOldValue().equals("CLOSED")) {
                if (statusChange.getNewValue().equals("REOPENED")) {
                    wft.setWorkflowName("Reopen Issue");
                    wft.setResolution(getResForStatusChange(e.getKey(), resolutions));
                }
            }

            transitions.add(wft);
        }

        return transitions;
                
    }

    public List<Attachment> getAttachments() {
        return originalIssue.getAttachments();
    }

    public List<JiraLink> getDuplicatesOf() {
        List<JiraLink> duplicates = new ArrayList<JiraLink>();

        List<HasDuplicate> origDups = originalIssue.getHasDuplicates();

        for (HasDuplicate dup : origDups) {
            JiraLink jiraLink = new JiraLink();
            jiraLink.setFromId(projectKey + "-" + originalIssue.getIssueId());
            jiraLink.setToId(projectKey + "-" + dup.getIssueId());
            jiraLink.setLinkDesc("duplicates");

            duplicates.add(jiraLink);
        }

        return duplicates;
    }

    public List<JiraLink> getDependsOn() {
        List<JiraLink> depends = new ArrayList<JiraLink>();

        List<DependsOn> origDeps = originalIssue.getDependsOns();

        for (DependsOn dep : origDeps) {
            JiraLink jiraLink = new JiraLink();
            jiraLink.setFromId(projectKey + "-" + originalIssue.getIssueId());
            jiraLink.setToId(projectKey + "-" + dep.getIssueId());
            jiraLink.setLinkDesc("depends on");

            depends.add(jiraLink);
        }

        return depends;
    }
    
    private String getResForStatusChange(Date statusDate, Map resolutions) {
        String res = "";

        if (resolutions.containsKey(statusDate)) {
            Activity resAct = (Activity) resolutions.get(statusDate);
            res = resAct.getNewValue();
        }

        return res;
    }
}
                


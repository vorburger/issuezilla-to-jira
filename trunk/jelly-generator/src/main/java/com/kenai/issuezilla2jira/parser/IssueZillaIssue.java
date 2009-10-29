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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Bean for IssueZilla XML issue dump.
 *
 * @author Andrew Bayer
 */

public class IssueZillaIssue {
    private int issueId;
    private String issueStatus;
    private String priority;
    private String resolution;
    private String component;
    private String version;
    private String platform;
    private String assignedTo;
    private String updatedTime;
    private String subComponent;
    private String reporter;
    private String targetMilestone;
    private String issueType;
    private String createdTime;
    private String qaContact;
    private String whiteboard;
    private String issueRefUrl;
    private int votes;
    private String os;
    private String summary;
    private String keywords;

    private final List<String> ccs = new ArrayList<String>();
    private final List<Comment> comments = new ArrayList<Comment>();
    private final List<Activity> activities = new ArrayList<Activity>();
    private final List<HasDuplicate> hasDuplicates = new ArrayList<HasDuplicate>();
    private final List<IsDuplicate> isDuplicates = new ArrayList<IsDuplicate>();
    private final List<DependsOn> dependsOns = new ArrayList<DependsOn>();
    private final List<Blocking> blockings = new ArrayList<Blocking>();
    private final List<Attachment> attachments = new ArrayList<Attachment>();
    
    /**
     * Adds a new activity.
     *
     * @param activity The new activity
     */
    public void addActivity(final Activity activity) {
        activities.add(activity);
    }

    /**
     * Returns all activities for this issue. The returned collection is read-only.
     *
     * @return All activities for this issue.
     */
    public List<Activity> getActivities() {
        return Collections.unmodifiableList(activities);
    }

    /**
     * Adds a new hasDuplicate.
     *
     * @param hasDuplicate The new hasDuplicate
     */
    public void addHasDuplicate(final HasDuplicate hasDuplicate) {
        hasDuplicates.add(hasDuplicate);
    }

    /**
     * Returns all hasDuplicates for this issue. The returned collection is read-only.
     *
     * @return All hasDuplicates for this issue.
     */
    public List<HasDuplicate> getHasDuplicates() {
        return Collections.unmodifiableList(hasDuplicates);
    }

    /**
     * Adds a new isDuplicate.
     *
     * @param isDuplicate The new isDuplicate
     */
    public void addIsDuplicate(final IsDuplicate isDuplicate) {
        isDuplicates.add(isDuplicate);
    }

    /**
     * Returns all isDuplicates for this issue. The returned collection is read-only.
     *
     * @return All isDuplicates for this issue.
     */
    public List<IsDuplicate> getIsDuplicates() {
        return Collections.unmodifiableList(isDuplicates);
    }

    /**
     * Adds a new blocking.
     *
     * @param blocking The new blocking
     */
    public void addBlocking(final Blocking blocking) {
        blockings.add(blocking);
    }

    /**
     * Returns all blockings for this issue. The returned collection is read-only.
     *
     * @return All blockings for this issue.
     */
    public List<Blocking> getBlockings() {
        return Collections.unmodifiableList(blockings);
    }

    /**
     * Adds a new dependsOn.
     *
     * @param dependsOn The new dependsOn
     */
    public void addDependsOn(final DependsOn dependsOn) {
        dependsOns.add(dependsOn);
    }

    /**
     * Returns all dependsOns for this issue. The returned collection is read-only.
     *
     * @return All dependsOns for this issue.
     */
    public List<DependsOn> getDependsOns() {
        return Collections.unmodifiableList(dependsOns);
    }

    /**
     * Adds a new attachment.
     *
     * @param attachment The new attachment
     */
    public void addAttachment(final Attachment attachment) {
        attachments.add(attachment);
    }

    /**
     * Returns all attachments for this issue. The returned collection is read-only.
     *
     * @return All attachments for this issue.
     */
    public List<Attachment> getAttachments() {
        return Collections.unmodifiableList(attachments);
    }

    /**
     * Adds a new comment.
     *
     * @param comment The new comment
     */
    public void addComment(final Comment comment) {
        comments.add(comment);
    }

    /**
     * Returns all comments for this issue. The returned collection is read-only.
     *
     * @return All comments for this issue.
     */
    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }

    /**
     * Gets the value of issueId
     *
     * @return the value of issueId
     */
    public int getIssueId() {
        return this.issueId;
    }

    /**
     * Sets the value of issueId
     *
     * @param argIssueId Value to assign to this.issueId
     */
    public void setIssueId(int argIssueId) {
        this.issueId = argIssueId;
    }

    /**
     * Gets the value of issueStatus
     *
     * @return the value of issueStatus
     */
    public String getIssueStatus() {
        return this.issueStatus;
    }

    /**
     * Sets the value of issueStatus
     *
     * @param argIssueStatus Value to assign to this.issueStatus
     */
    public void setIssueStatus(String argIssueStatus) {
        this.issueStatus = argIssueStatus;
    }

    /**
     * Gets the value of priority
     *
     * @return the value of priority
     */
    public String getPriority() {
        return this.priority;
    }

    /**
     * Sets the value of priority
     *
     * @param argPriority Value to assign to this.priority
     */
    public void setPriority(String argPriority) {
        this.priority = argPriority;
    }

    /**
     * Gets the value of resolution
     *
     * @return the value of resolution
     */
    public String getResolution() {
        return this.resolution;
    }

    /**
     * Sets the value of resolution
     *
     * @param argResolution Value to assign to this.resolution
     */
    public void setResolution(String argResolution) {
        this.resolution = argResolution;
    }

    /**
     * Gets the value of component
     *
     * @return the value of component
     */
    public String getComponent() {
        return this.component;
    }

    /**
     * Sets the value of component
     *
     * @param argComponent Value to assign to this.component
     */
    public void setComponent(String argComponent) {
        this.component = argComponent;
    }

    /**
     * Gets the value of version
     *
     * @return the value of version
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * Sets the value of version
     *
     * @param argVersion Value to assign to this.version
     */
    public void setVersion(String argVersion) {
        this.version = argVersion;
    }

    /**
     * Gets the value of platform
     *
     * @return the value of platform
     */
    public String getPlatform() {
        return this.platform;
    }

    /**
     * Sets the value of platform
     *
     * @param argPlatform Value to assign to this.platform
     */
    public void setPlatform(String argPlatform) {
        this.platform = argPlatform;
    }

    /**
     * Gets the value of assignedTo
     *
     * @return the value of assignedTo
     */
    public String getAssignedTo() {
        return this.assignedTo;
    }

    /**
     * Sets the value of assignedTo
     *
     * @param argAssignedTo Value to assign to this.assignedTo
     */
    public void setAssignedTo(String argAssignedTo) {
        this.assignedTo = argAssignedTo;
    }

    /**
     * Gets the value of updatedTime
     *
     * @return the value of updatedTime
     */
    public String getUpdatedTime() {
        return this.updatedTime;
    }

    /**
     * Sets the value of updatedTime
     *
     * @param argUpdatedTime Value to assign to this.updatedTime
     */
    public void setUpdatedTime(String argUpdatedTime) {
        this.updatedTime = argUpdatedTime;
    }

    /**
     * Gets the value of subComponent
     *
     * @return the value of subComponent
     */
    public String getSubComponent() {
        return this.subComponent;
    }

    /**
     * Sets the value of subComponent
     *
     * @param argSubComponent Value to assign to this.subComponent
     */
    public void setSubComponent(String argSubComponent) {
        this.subComponent = argSubComponent;
    }

    /**
     * Gets the value of reporter
     *
     * @return the value of reporter
     */
    public String getReporter() {
        return this.reporter;
    }

    /**
     * Sets the value of reporter
     *
     * @param argReporter Value to assign to this.reporter
     */
    public void setReporter(String argReporter) {
        this.reporter = argReporter;
    }

    /**
     * Gets the value of targetMilestone
     *
     * @return the value of targetMilestone
     */
    public String getTargetMilestone() {
        return this.targetMilestone;
    }

    /**
     * Sets the value of targetMilestone
     *
     * @param argTargetMilestone Value to assign to this.targetMilestone
     */
    public void setTargetMilestone(String argTargetMilestone) {
        this.targetMilestone = argTargetMilestone;
    }

    /**
     * Gets the value of issueType
     *
     * @return the value of issueType
     */
    public String getIssueType() {
        return this.issueType;
    }

    /**
     * Sets the value of issueType
     *
     * @param argIssueType Value to assign to this.issueType
     */
    public void setIssueType(String argIssueType) {
        this.issueType = argIssueType;
    }

    /**
     * Gets the value of createdTime
     *
     * @return the value of createdTime
     */
    public String getCreatedTime() {
        return this.createdTime;
    }

    /**
     * Sets the value of createdTime
     *
     * @param argCreatedTime Value to assign to this.createdTime
     */
    public void setCreatedTime(String argCreatedTime) {
        this.createdTime = argCreatedTime;
    }

    /**
     * Gets the value of qaContact
     *
     * @return the value of qaContact
     */
    public String getQaContact() {
        return this.qaContact;
    }

    /**
     * Sets the value of qaContact
     *
     * @param argQaContact Value to assign to this.qaContact
     */
    public void setQaContact(String argQaContact) {
        this.qaContact = argQaContact;
    }

    /**
     * Gets the value of whiteboard
     *
     * @return the value of whiteboard
     */
    public String getWhiteboard() {
        return this.whiteboard;
    }

    /**
     * Sets the value of whiteboard
     *
     * @param argWhiteboard Value to assign to this.whiteboard
     */
    public void setWhiteboard(String argWhiteboard) {
        this.whiteboard = argWhiteboard;
    }

    /**
     * Gets the value of issueRefUrl
     *
     * @return the value of issueRefUrl
     */
    public String getIssueRefUrl() {
        return this.issueRefUrl;
    }

    /**
     * Sets the value of issueRefUrl
     *
     * @param argIssueRefUrl Value to assign to this.issueRefUrl
     */
    public void setIssueRefUrl(String argIssueRefUrl) {
        this.issueRefUrl = argIssueRefUrl;
    }

    /**
     * Gets the value of votes
     *
     * @return the value of votes
     */
    public int getVotes() {
        return this.votes;
    }

    /**
     * Sets the value of votes
     *
     * @param argVotes Value to assign to this.votes
     */
    public void setVotes(int argVotes) {
        this.votes = argVotes;
    }

    /**
     * Gets the value of os
     *
     * @return the value of os
     */
    public String getOs() {
        return this.os;
    }

    /**
     * Sets the value of os
     *
     * @param argOs Value to assign to this.os
     */
    public void setOs(String argOs) {
        this.os = argOs;
    }

    /**
     * Gets the value of summary
     *
     * @return the value of summary
     */
    public String getSummary() {
        return this.summary;
    }

    /**
     * Sets the value of summary
     *
     * @param argSummary Value to assign to this.summary
     */
    public void setSummary(String argSummary) {
        this.summary = argSummary;
    }

    /**
     * Gets the value of keywords
     *
     * @return the value of keywords
     */
    public String getKeywords() {
        return this.keywords;
    }

    /**
     * Sets the value of keywords
     *
     * @param argKeywords Value to assign to this.keywords
     */
    public void setKeywords(String argKeywords) {
        this.keywords = argKeywords;
    }

    /**
     * Gets the value of cc
     *
     * @return the value of cc
     */
    public List<String> getCcs() {
        return Collections.unmodifiableList(ccs);
    }

    /**
     * Sets the value of cc
     *
     * @param argCc Value to assign to this.cc
     */
    public void addCc(String argCc) {
        ccs.add(argCc);
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("Issue ID: " + issueId + "\n");
        buf.append("Status: " + issueStatus + "\n");
        buf.append("Priority: " + priority + "\n");
        buf.append("Resolution: " + resolution + "\n");
        buf.append("Component: " + component + "\n");
        buf.append("Version: " + version + "\n");
        buf.append("Platform: " + platform + "\n");
        buf.append("Assigned To: " + assignedTo + "\n");
        buf.append("Updated Time: " + updatedTime + "\n");
        buf.append("Subcomponent: " + subComponent + "\n");
        buf.append("Reporter: " + reporter + "\n");
        buf.append("Milestone: " + targetMilestone + "\n");
        buf.append("Issue Type: " + issueType + "\n");
        buf.append("Created Time: " + createdTime + "\n");
        buf.append("QA Contact: " + qaContact + "\n");
        buf.append("Whiteboard: " + whiteboard + "\n");
        buf.append("Issue Ref URL: " + issueRefUrl + "\n");
        buf.append("Votes: " + votes + "\n");
        buf.append("OS: " + os + "\n");
        buf.append("Summary: " + summary + "\n");
        buf.append("Keywords: " + keywords + "\n");

        buf.append("-- CC --\n");
        for (String cc : ccs) {
            buf.append("\t" + cc.toString());
        }
        
        buf.append("-- Comments --\n");
        for (Comment comment : comments) {
            buf.append(comment.toString());
        }
        buf.append("-- Activities --\n");
        for (Activity activity : activities) {
            buf.append(activity.toString());
        }
        buf.append("-- Has Duplicates --\n");
        for (HasDuplicate hasDup : hasDuplicates) {
            buf.append(hasDup.toString());
        }
        buf.append("-- Is Duplicate Of --\n");
        for (IsDuplicate isDup : isDuplicates) {
            buf.append(isDup.toString());
        }
        buf.append("-- Depends On --\n");
        for (DependsOn dep : dependsOns) {
            buf.append(dep.toString());
        }
        buf.append("-- Blocks --\n");
        for (Blocking block : blockings) {
            buf.append(block.toString());
        }
        buf.append("-- Attachments --\n");
        for (Attachment attach : attachments) {
            buf.append(attach.toString());
        }

        return buf.toString();
    }
}    
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

/**
 * Bean for representing IssueZilla activity of status changes and user assignments 
 * as JIRA workflow transitions.
 *
 * @author Andrew Bayer
 */
public class WorkflowTransition {
    private String workflowName;
    private String resolution;
    private String user;
    private String assignee;

    /**
     * Gets the value of workflowName
     *
     * @return the value of workflowName
     */
    public String getWorkflowName() {
        return this.workflowName;
    }

    /**
     * Sets the value of workflowName
     *
     * @param argWorkflowName Value to assign to this.workflowName
     */
    public void setWorkflowName(String argWorkflowName) {
        this.workflowName = argWorkflowName;
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
     * Gets the value of user
     *
     * @return the value of user
     */
    public String getUser() {
        return this.user;
    }

    /**
     * Sets the value of user
     *
     * @param argUser Value to assign to this.user
     */
    public void setUser(String argUser) {
        this.user = argUser;
    }

    /**
     * Gets the value of assignee
     *
     * @return the value of assignee
     */
    public String getAssignee() {
        return this.assignee;
    }

    /**
     * Sets the value of assignee
     *
     * @param argAssignee Value to assign to this.assignee
     */
    public void setAssignee(String argAssignee) {
        this.assignee = argAssignee;
    }

}
    
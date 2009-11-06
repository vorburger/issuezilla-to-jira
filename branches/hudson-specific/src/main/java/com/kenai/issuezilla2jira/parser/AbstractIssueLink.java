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

/**
 * Bean for IssueZilla XML issue dump.
 *
 * @author Andrew Bayer
 */

public abstract class AbstractIssueLink {
    private String issueId;
    private String who;
    private String when;

    public abstract String getLinkType();
    
    /**
     * Gets the value of issueId
     *
     * @return the value of issueId
     */
    public String getIssueId() {
        return this.issueId;
    }

    /**
     * Sets the value of issueId
     *
     * @param argIssueId Value to assign to this.issueId
     */
    public void setIssueId(String argIssueId) {
        this.issueId = argIssueId;
    }

    /**
     * Gets the value of who
     *
     * @return the value of who
     */
    public String getWho() {
        return this.who;
    }

    /**
     * Sets the value of who
     *
     * @param argWho Value to assign to this.who
     */
    public void setWho(String argWho) {
        this.who = argWho;
    }

    /**
     * Gets the value of when
     *
     * @return the value of when
     */
    public String getWhen() {
        return this.when;
    }

    /**
     * Sets the value of when
     *
     * @param argWhen Value to assign to this.when
     */
    public void setWhen(String argWhen) {
        this.when = argWhen;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("\tIssue ID: " + issueId + "\n");
        buf.append("\tWho: " + who + "\n");
        buf.append("\tWhen: " + when + "\n");

        return buf.toString();
    }
}

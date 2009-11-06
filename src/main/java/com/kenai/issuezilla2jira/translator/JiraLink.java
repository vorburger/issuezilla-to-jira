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
 * Represents the linkage between two issues.
 */

public class JiraLink {
    private String fromId;
    private String toId;
    private String linkDesc;

    /**
     * Gets the value of fromId
     *
     * @return the value of fromId
     */
    public String getFromId() {
        return this.fromId;
    }

    /**
     * Sets the value of fromId
     *
     * @param argFromId Value to assign to this.fromId
     */
    public void setFromId(String argFromId) {
        this.fromId = argFromId;
    }

    /**
     * Gets the value of toId
     *
     * @return the value of toId
     */
    public String getToId() {
        return this.toId;
    }

    /**
     * Sets the value of toId
     *
     * @param argToId Value to assign to this.toId
     */
    public void setToId(String argToId) {
        this.toId = argToId;
    }

    /**
     * Gets the value of linkDesc
     *
     * @return the value of linkDesc
     */
    public String getLinkDesc() {
        return this.linkDesc;
    }

    /**
     * Sets the value of linkDesc
     *
     * @param argLinkDesc Value to assign to this.linkDesc
     */
    public void setLinkDesc(String argLinkDesc) {
        this.linkDesc = argLinkDesc;
    }

}
    
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

public class Attachment {
    private String encoding;
    private String mimeType;
    private String attachId;
    private String date;
    private String desc;
    private int isPatch;
    private String filename;
    private String submitterId;
    private String submitterName;
    private String data;
    private String attachUrl;

    
    /**
     * Gets the value of encoding
     *
     * @return the value of encoding
     */
    public String getEncoding() {
        return this.encoding;
    }

    /**
     * Sets the value of encoding
     *
     * @param argEncoding Value to assign to this.encoding
     */
    public void setEncoding(String argEncoding) {
        this.encoding = argEncoding;
    }

    /**
     * Gets the value of mimeType
     *
     * @return the value of mimeType
     */
    public String getMimeType() {
        return this.mimeType;
    }

    /**
     * Sets the value of mimeType
     *
     * @param argMimeType Value to assign to this.mimeType
     */
    public void setMimeType(String argMimeType) {
        this.mimeType = argMimeType;
    }

    /**
     * Gets the value of attachId
     *
     * @return the value of attachId
     */
    public String getAttachId() {
        return this.attachId;
    }

    /**
     * Sets the value of attachId
     *
     * @param argAttachId Value to assign to this.attachId
     */
    public void setAttachId(String argAttachId) {
        this.attachId = argAttachId;
    }

    /**
     * Gets the value of date
     *
     * @return the value of date
     */
    public String getDate() {
        return this.date;
    }

    /**
     * Sets the value of date
     *
     * @param argDate Value to assign to this.date
     */
    public void setDate(String argDate) {
        this.date = argDate;
    }

    /**
     * Gets the value of desc
     *
     * @return the value of desc
     */
    public String getDesc() {
        return this.desc;
    }

    /**
     * Sets the value of desc
     *
     * @param argDesc Value to assign to this.desc
     */
    public void setDesc(String argDesc) {
        this.desc = argDesc;
    }

    /**
     * Gets the value of isPatch
     *
     * @return the value of isPatch
     */
    public int getIsPatch() {
        return this.isPatch;
    }

    /**
     * Sets the value of isPatch
     *
     * @param argIsPatch Value to assign to this.isPatch
     */
    public void setIsPatch(int argIsPatch) {
        this.isPatch = argIsPatch;
    }

    /**
     * Gets the value of filename
     *
     * @return the value of filename
     */
    public String getFilename() {
        return this.filename;
    }

    /**
     * Sets the value of filename
     *
     * @param argFilename Value to assign to this.filename
     */
    public void setFilename(String argFilename) {
        this.filename = argFilename;
    }

    /**
     * Gets the value of submitterId
     *
     * @return the value of submitterId
     */
    public String getSubmitterId() {
        return this.submitterId;
    }

    /**
     * Sets the value of submitterId
     *
     * @param argSubmitterId Value to assign to this.submitterId
     */
    public void setSubmitterId(String argSubmitterId) {
        this.submitterId = argSubmitterId;
    }

    /**
     * Gets the value of submitterName
     *
     * @return the value of submitterName
     */
    public String getSubmitterName() {
        return this.submitterName;
    }

    /**
     * Sets the value of submitterName
     *
     * @param argSubmitterName Value to assign to this.submitterName
     */
    public void setSubmitterName(String argSubmitterName) {
        this.submitterName = argSubmitterName;
    }

    /**
     * Gets the value of data
     *
     * @return the value of data
     */
    public String getData() {
        return this.data;
    }

    /**
     * Sets the value of data
     *
     * @param argData Value to assign to this.data
     */
    public void setData(String argData) {
        this.data = argData;
    }

    /**
     * Gets the value of attachUrl
     *
     * @return the value of attachUrl
     */
    public String getAttachUrl() {
        return this.attachUrl;
    }

    /**
     * Sets the value of attachUrl
     *
     * @param argAttachUrl Value to assign to this.attachUrl
     */
    public void setAttachUrl(String argAttachUrl) {
        this.attachUrl = argAttachUrl;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("\tEncoding: " + encoding + "\n");
        buf.append("\tMIME Type: " + mimeType + "\n");
        buf.append("\tAttachment ID: " + attachId + "\n");
        buf.append("\tDate: " + date + "\n");
        buf.append("\tDescription: " + desc + "\n");
        buf.append("\tIs Patch: " + isPatch + "\n");
        buf.append("\tFilename: " + filename + "\n");
        buf.append("\tSubmitter ID: " + submitterId + "\n");
        buf.append("\tSubmitter Name: " + submitterName + "\n");
        buf.append("\tData: " + data + "\n");
        buf.append("\tAttachment URL: " + attachUrl + "\n");

        return buf.toString();
    }
}
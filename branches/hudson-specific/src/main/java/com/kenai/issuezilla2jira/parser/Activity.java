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

public class Activity {
    private String user;
    private String when;
    private String fieldName;
    private String fieldDesc;
    private String oldValue;
    private String newValue;

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

    /**
     * Gets the value of fieldName
     *
     * @return the value of fieldName
     */
    public String getFieldName() {
        return this.fieldName;
    }

    /**
     * Sets the value of fieldName
     *
     * @param argFieldName Value to assign to this.fieldName
     */
    public void setFieldName(String argFieldName) {
        this.fieldName = argFieldName;
    }

    /**
     * Gets the value of fieldDesc
     *
     * @return the value of fieldDesc
     */
    public String getFieldDesc() {
        return this.fieldDesc;
    }

    /**
     * Sets the value of fieldDesc
     *
     * @param argFieldDesc Value to assign to this.fieldDesc
     */
    public void setFieldDesc(String argFieldDesc) {
        this.fieldDesc = argFieldDesc;
    }

    /**
     * Gets the value of oldValue
     *
     * @return the value of oldValue
     */
    public String getOldValue() {
        return this.oldValue;
    }

    /**
     * Sets the value of oldValue
     *
     * @param argOldValue Value to assign to this.oldValue
     */
    public void setOldValue(String argOldValue) {
        this.oldValue = argOldValue;
    }

    /**
     * Gets the value of newValue
     *
     * @return the value of newValue
     */
    public String getNewValue() {
        return this.newValue;
    }

    /**
     * Sets the value of newValue
     *
     * @param argNewValue Value to assign to this.newValue
     */
    public void setNewValue(String argNewValue) {
        this.newValue = argNewValue;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("\tUser: " + user + "\n");
        buf.append("\tWhen: " + when + "\n");
        buf.append("\tField Name: " + fieldName + "\n");
        buf.append("\tField Desc: " + fieldDesc + "\n");
        buf.append("\tOld Value: " + oldValue + "\n");
        buf.append("\tNew Value: " + newValue + "\n");

        return buf.toString();
    }
}
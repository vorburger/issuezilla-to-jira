package com.kenai.issuezilla2jira.parser;

/**
 * Bean for IssueZilla XML issue dump.
 *
 * @author Andrew Bayer
 */

public class Blocking extends AbstractIssueLink {
    public Blocking() {
        super();
    }
    
    public String getLinkType() {
        return "Is Blocking";
    }

}

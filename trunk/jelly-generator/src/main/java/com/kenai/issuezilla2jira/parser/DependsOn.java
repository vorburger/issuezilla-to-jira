package com.kenai.issuezilla2jira.parser;

/**
 * Bean for IssueZilla XML issue dump.
 *
 * @author Andrew Bayer
 */

public class DependsOn extends AbstractIssueLink {
    public DependsOn() {
        super();
    }
    
    public String getLinkType() {
        return "Depends On";
    }

}

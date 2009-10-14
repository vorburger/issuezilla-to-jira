package com.kenai.issuezilla2jira.parser;

/**
 * Bean for IssueZilla XML issue dump.
 *
 * @author Andrew Bayer
 */

public class IsDuplicate extends AbstractIssueLink {
    public IsDuplicate() {
        super();
    }
    
    public String getLinkType() {
        return "Is a Duplicate Of";
    }

}

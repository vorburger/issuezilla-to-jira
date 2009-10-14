package com.kenai.issuezilla2jira.parser;

/**
 * Bean for IssueZilla XML issue dump.
 *
 * @author Andrew Bayer
 */

public class HasDuplicate extends AbstractIssueLink {
    public HasDuplicate() {
        super();
    }
    
    public String getLinkType() {
        return "Is Duplicated By";
    }

}

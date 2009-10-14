package com.kenai.issuezilla2jira.parser;

/**
 * Bean for IssueZilla XML issue dump.
 *
 * @author Andrew Bayer
 */

public class Comment {
    private String commenter;
    private String commentTime;
    private String commentText;

    /**
     * Gets the value of commenter
     *
     * @return the value of commenter
     */
    public String getCommenter() {
        return this.commenter;
    }

    /**
     * Sets the value of commenter
     *
     * @param argCommenter Value to assign to this.commenter
     */
    public void setCommenter(String argCommenter) {
        this.commenter = argCommenter;
    }

    /**
     * Gets the value of commentTime
     *
     * @return the value of commentTime
     */
    public String getCommentTime() {
        return this.commentTime;
    }

    /**
     * Sets the value of commentTime
     *
     * @param argCommentTime Value to assign to this.commentTime
     */
    public void setCommentTime(String argCommentTime) {
        this.commentTime = argCommentTime;
    }

    /**
     * Gets the value of commentText
     *
     * @return the value of commentText
     */
    public String getCommentText() {
        return this.commentText;
    }

    /**
     * Sets the value of commentText
     *
     * @param argCommentText Value to assign to this.commentText
     */
    public void setCommentText(String argCommentText) {
        this.commentText = argCommentText;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("\tCommenter: " + commenter + "\n");
        buf.append("\tComment Time: " + commentTime + "\n");
        buf.append("\tComment Text: " + commentText + "\n");

        return buf.toString();
    }
}
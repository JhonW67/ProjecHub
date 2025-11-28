package com.ProjectHub.dto;

import java.util.UUID;

public class FeedbackDTO {
    private UUID id;
    private String authorName;
    private String comment;

    public FeedbackDTO() {}

    public FeedbackDTO(UUID id, String authorName, String comment) {
        this.id = id;
        this.authorName = authorName;
        this.comment = comment;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}

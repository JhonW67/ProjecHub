package com.ProjectHub.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class FeedbackDTO {
    private UUID id;
    private String authorName;
    private String comment;
    private Integer rating;

    public FeedbackDTO(UUID id, String authorName, String comment, Integer rating) {
        this.id = id;
        this.authorName = authorName;
        this.comment = comment;
        this.rating = rating;
    }
    public FeedbackDTO() {}
}

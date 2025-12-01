package com.ProjectHub.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class FeedbackCreateDTO {
    private String comment;
    private Integer rating;
    private UUID userId;
}

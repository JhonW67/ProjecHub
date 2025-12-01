package com.ProjectHub.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ProjectListDTO {
    private UUID projectId;
    private String title;
    private String description;
    private String status;
    private String imageUrl;
    private String groupName;
    private String createdAt;
}

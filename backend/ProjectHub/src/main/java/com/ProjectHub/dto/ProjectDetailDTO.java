package com.ProjectHub.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class ProjectDetailDTO {
    private UUID projectId;
    private String title;
    private String description;
    private String groupName;
    private LocalDate createdAt;
    private String imageUrl;
    private List<UserDTO> members;
    private List<DocumentDTO> documents;
    private List<FeedbackDTO> feedbacks;
    private EvaluationDTO evaluation;
    private String qrCodeUrl;
    private Boolean canEdit;

    public ProjectDetailDTO() {

    }
}

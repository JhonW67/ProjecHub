package com.ProjectHub.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class EvaluationCreateDTO {
    private Integer grade;
    private String comment;
    private Boolean visibleToStudents;
    private UUID userId;  // professor que está avaliando
}

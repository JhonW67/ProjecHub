package com.ProjectHub.interfaces.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectCreateRequest {
    @NotBlank @Size(min = 2, max = 140)
    private String name;
    private String description;
}
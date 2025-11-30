package com.ProjectHub.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ProjectBasicUpdateDTO {
    private String imageUrl;
    private String groupName;
    private List<UUID> memberIds; // usuários integrantes
}

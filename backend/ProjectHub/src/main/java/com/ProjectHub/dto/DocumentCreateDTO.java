package com.ProjectHub.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class DocumentCreateDTO {
    private UUID id;
    private String name;
    private String type;
    private String url;

    public DocumentCreateDTO() {}

    public DocumentCreateDTO(UUID id, String name, String type, String url) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.url = url;
    }

}

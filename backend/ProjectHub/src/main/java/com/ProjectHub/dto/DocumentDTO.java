package com.ProjectHub.dto;

import java.util.UUID;

public class DocumentDTO {
    private UUID id;
    private String name;
    private String type;
    private String url;

    public DocumentDTO() {}

    public DocumentDTO(UUID id, String name, String type, String url) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.url = url;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}

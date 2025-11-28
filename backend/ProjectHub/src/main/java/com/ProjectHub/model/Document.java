package com.ProjectHub.model;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "documents")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class  Document {

    @Id
    @GeneratedValue
    @Column(name = "document_id")
    private UUID documentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploader_id")
    private User uploader;

    @Column(length = 255)
    private String filename;

    @Column(length = 1024)
    private String filepath;

    private Integer version;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column
    private String type;

    @Column
    private String url;

    public UUID getId() { return documentId; }
    public String getName() { return filename; }
    public String getType() { return type; }
    public String getUrl() { return url; }
}


package com.ProjectHub.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "projects")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue
    @Column(name = "project_id")
    private UUID projectId;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @Column(length = 20)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    @JsonIgnoreProperties("projects")
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Getter
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<ProjectMember> members;

    @Getter
    @Column(name = "image_url")
    private String imageUrl;

    // Grupo pode ser associação real, ou só um nome se quiser simplificar
    @Getter
    @Column(name = "group_name")
    private String groupName;

    @Getter
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<Document> documents;

    @Getter
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<Feedback> feedbacks;

    @Getter
    @Column(name="qr_code_url")
    private String qrCodeUrl;

    @Getter
    @OneToOne(mappedBy = "project", fetch = FetchType.LAZY)
    private Evaluation evaluation;

}
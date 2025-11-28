package com.ProjectHub.model;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "feedbacks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue
    @Column(name = "feedback_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(columnDefinition = "text", nullable = false)
    private String comment;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    // Getters (se não usar lombok getter/setter)
    public UUID getId() { return id; }
    public Project getProject() { return project; }
    public User getAuthor() { return author; }
    public String getComment() { return comment; }
    public Timestamp getCreatedAt() { return createdAt; }

    // Setters (caso não use @Data do lombok)
    public void setId(UUID id) { this.id = id; }
    public void setProject(Project project) { this.project = project; }
    public void setAuthor(User author) { this.author = author; }
    public void setComment(String comment) { this.comment = comment; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}

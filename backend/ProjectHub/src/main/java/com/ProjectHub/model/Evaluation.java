package com.ProjectHub.model;

import com.ProjectHub.dto.DocumentDTO;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "evaluations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Evaluation {

    @Id
    @GeneratedValue
    @Column(name = "evaluation_id")
    private UUID evaluationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluator_id")
    private User evaluator;

    @Column(precision = 3, scale = 2)
    private BigDecimal score;

    @Column(columnDefinition = "text")
    private String comments;

    @Column(name = "visible_to_students")
    private boolean visibleToStudents;

    @Column(name = "created_at")
    private Timestamp createdAt;

    public UUID getId() { return evaluationId; }
    public User getAuthor() { return evaluator; }
    public String getComment() { return comments; }

    public BigDecimal getGrade() { return score;
    }

    public User getProfessor() { return  evaluator;
    }
}


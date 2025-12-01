package com.ProjectHub.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluator_id", nullable = false)
    private User professor;                 // nome alinhado com o resto

    @Column(name = "score")
    private Integer grade;              // nota

    @Column(name = "comments", columnDefinition = "text")
    private String comment;                // comentário

    @Column(name = "visible_to_students")
    private boolean visibleToStudents;

    @Column(name = "created_at")
    private Timestamp createdAt;

}

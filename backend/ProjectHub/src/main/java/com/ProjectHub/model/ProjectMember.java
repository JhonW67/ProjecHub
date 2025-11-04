package com.ProjectHub.model;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "project_members")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMember {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 30)
    private String role;

    @Column(name = "joined_at")
    private Timestamp joinedAt;
}

package com.ProjectHub.model;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private UUID userId;

    @Column(length = 150, nullable = false)
    private String name;

    @Column(length = 255, unique = true, nullable = false)
    private String email;

    @Column(name = "password_hash", length = 255, nullable = false)
    private String passwordHash;

    @Column(length = 20, nullable = false)
    private String role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id")
    private Semester semester;

    @Column(length = 50, unique = true)
    private String registration;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(nullable = false)
    private boolean active;
}
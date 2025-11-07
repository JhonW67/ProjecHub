package com.ProjectHub.model;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.List;

@Entity
@Table(name = "courses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue
    @Column(name = "course_id")
    private UUID courseId;

    @Column(length = 100, unique = true, nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;
}
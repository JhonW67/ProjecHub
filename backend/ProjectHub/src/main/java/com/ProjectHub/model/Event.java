package com.ProjectHub.model;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.List;

@Entity
@Table(name = "events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue
    @Column(name = "event_id")
    private UUID eventId;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "start_at")
    private Timestamp startAt;

    @Column(name = "end_at")
    private Timestamp endAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Project> projects;
}


package com.ProjectHub.model;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.List;

@Entity
@Table(name = "semesters")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Semester {

    @Id
    @GeneratedValue
    @Column(name = "semester_id")
    private UUID semesterId;

    @Column(nullable = false)
    private short number;

    @Column(nullable = false)
    private short year;

    @Column(name = "start_date")
    private Timestamp startDate;

    @Column(name = "end_date")
    private Timestamp endDate;

    @OneToMany(mappedBy = "semester", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;
}
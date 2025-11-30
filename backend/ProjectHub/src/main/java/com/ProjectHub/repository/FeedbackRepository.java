package com.ProjectHub.repository;

import com.ProjectHub.model.Feedback;
import com.ProjectHub.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {
    List<Feedback> findByProject(Project project);
}

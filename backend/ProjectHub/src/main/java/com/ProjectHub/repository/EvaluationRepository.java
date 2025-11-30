package com.ProjectHub.repository;

import com.ProjectHub.model.Evaluation;
import com.ProjectHub.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EvaluationRepository extends JpaRepository<Evaluation, UUID> {
    Optional<Evaluation> findByProject(Project project);
}

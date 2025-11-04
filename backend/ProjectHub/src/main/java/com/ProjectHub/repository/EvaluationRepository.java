package com.ProjectHub.repository;

import com.ProjectHub.model.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EvaluationRepository extends JpaRepository<Evaluation, UUID> { }

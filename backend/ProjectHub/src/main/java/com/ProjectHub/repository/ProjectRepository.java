package com.ProjectHub.repository;

import com.ProjectHub.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.members WHERE p.projectId = :id")
    Optional<Project> findByIdFetchMembers(@Param("id") UUID id);
}
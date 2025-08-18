package com.ProjectHub.infrastructure.repository;

import com.ProjectHub.domain.IdModel;
import com.ProjectHub.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, IdModel> {
    // Aqui você pode adicionar métodos personalizados de consulta, se necessário

    Project findByName(String name);

    Optional<Object> findById(UUID id);

    boolean existsById(UUID id);

    boolean existsByName(String name);

}

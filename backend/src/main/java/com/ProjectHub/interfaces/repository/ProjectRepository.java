package com.ProjectHub.interfaces.repository;

import com.ProjectHub.domain.IdModel;
import com.ProjectHub.domain.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, IdModel> {
    // Aqui você pode adicionar métodos personalizados de consulta, se necessário

    Project findByName(String name);
}

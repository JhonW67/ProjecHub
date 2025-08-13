package com.ProjectHub.interfaces;

import com.ProjectHub.domain.IdModel;
import com.ProjectHub.domain.ProjectModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectModel, IdModel> {
    // Aqui você pode adicionar métodos personalizados de consulta, se necessário

    ProjectModel findByName(String name);
}
